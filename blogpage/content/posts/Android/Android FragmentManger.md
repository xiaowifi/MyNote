+++
date = "2020-10-20"
title = "摸鱼Android Fragmentmanger"
description = "Android Fragmentmanger"
tags = [ "fragment","fragmentManger"]
categories = [
    "android基础"
]
series = ["fragment"]
featured = true

+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

话说，搬砖多年，我们的好朋友fragment已经需要适配到AndroidX了，毕竟我之前老是喜欢用V4包下的fragment。但是这个笔记却不是关于更新到Android X的,毕竟如果要整Android X，要改的地方应该有点多，还不知道 Android X和support 包可以共存吗？感觉没法共存，毕竟从activity 到view好像都变了，毕竟第3方包如果没有适配Android X就没法改。自己整是不可能自己整的，毕竟老板没有要求，那我们就永远不适配AndroidX的代码。感觉有点和Android 最新时代已经脱轨了。

好了，回归正文，我们主要是整fragmentmanager。

顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 

## 正文

显示fragment吧，这个老朋友了，不需要介绍，app 下的fragment和v4下的fragment调用对应的fragmentmanager就好了。而显示fragment 一般有几种:

- 通过ViewPager 加载
- 通过FrameLayout
- 通过dialogFragment 显示 

这个地方主要整:基于FrameLayout 显示fragment。

### 显示fragment

代码大概是需要显示成这个样子。

```
public  Fragment showFragment(String className, FragmentManager fragmentManager, int id) {
    Fragment fragment = null;
    try {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏所有的那个啥。
        for (Fragment fra : fragmentManager.getFragments()) {
            fragmentTransaction.hide(fra);
        }
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(className);
        if (fragmentByTag != null) {
            fragmentTransaction.show(fragmentByTag);
        } else {
            fragment = (Fragment) Class.forName(className).newInstance();
            fragmentTransaction.add(id, fragment, className);
        }
        //添加到返回栈中
        //  fragmentTransaction.addToBackStack(className);
        fragmentTransaction.commitAllowingStateLoss();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    return fragment;
}
```

每个人的写法不一样，我还是相对喜欢通过Class.forName(className).newInstance() ,所以没有将fragment对象传进来。有过骚想法的同学，可能尝试过通过recycleview 加载fragment，默认情况看起来只是加载出一个的，而且是最后一个。逻辑好像是fragmentTransaction.add(id, fragment, className); 绑定的id 有关，因为每个item 对应的id 是一样的，所以他就加载到那个FrameLayout里面了。但是问题是，如果我连续调用两次showFragment，且传入的参数一致，那么他就会有两个fragment一模一样的fragment。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201020091854.png)

别问，问就是不知道。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923150707.png)

虽然道理都懂，但是我还是决定认真探查下。

#### recyleview的fragment

这个是骚想法，不推荐写，写view 或者布局不香吗？非要通过recyleview 加载fragment。还别说，如果解决相同id导致加载到同一个FrameLayout里面的问题，这个感觉还行（但是个人还是不推荐，关键是这么写图什么？）。至于iD问题，最简单的方式就是通过设置item不复用实现，但是图什么啊。

#### 相同的fragment被添加两次

这个问题，我好像只有通过 fragment = (Fragment) Class.forName(className).newInstance(); 生成的fragment才会有，如果通过传入对象添加，会抛出当前fragment已经被添加java.lang.IllegalStateException: Fragment already added，也是基于传入两次的对象是同一个对象才行。虽然我们可以从代码逻辑上处理掉这种情况，使得逻辑更加缜密。但是如果代码可以直接控制这种逻辑，岂不是不用费脑子(通过代码去整，可能导致同事理不清逻辑)。但是还是可以分析出来一些的。我们在代码中通过:

```
Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragment.getClass().getName());
if (fragmentByTag != null) {
    fragmentTransaction.show(fragmentByTag);
} else {
    fragmentTransaction.add(id, fragment, fragment.getClass().getName());
}
```

这个逻辑就是通过tag 从fragmentmanager中判断是否有这个fragment，如果有显示，如果没有就添加。运行这个逻辑之前还将所有已经添加的fragment隐藏了：

```
//隐藏所有的那个啥。
for (Fragment fra : fragmentManager.getFragments()) {
    fragmentTransaction.hide(fra);
}
```

但是调用两次显示同一个fragment的方法之后，却显示了两个fragment。且tag 相同，而且显示成功后通过fragmentmanager获取到的fragment 也有两个，且tag相同，那是否可以说明，fragment添加是非同步更新的呢？关键是他两个fragment都显示出来了。

```
fragmentManager.findFragmentByTag(tag);
```

问题是，因为tag两个是一样的。那么通过上面获取到的是第几个？结果是最后添加的那个。

因为FragmentTransaction 是使用前重新获取，所以没法对FragmentTransaction 进行加锁。我通过对getSupportFragmentManager 进行加锁，发现还是无法解决这个问题，看来 相同的两个fragment 只能通过逻辑去解决这个情况了。

### Activity能否获取显示的fragment中的子fragment？

emmmm？是不行的哦，比如说A 是activity，B,C,D,是fragment。B放置到A中，C放置到B中，D放置到C中。而通过fragmentmanager 获取到的fragment ,A中只有B,B中只有C,C中只有D.

但是BCD通过getActivity获取到的activity是同一个activity。这个也就可以大致的说明一个问题，activity中的fragmentManager和子类中的fragmentManager是不同的，且同一个activity下的fragment中的fragmentManager 也是不同的，所以正常的操作下是没办法直接操作间接层的fragment的。但是,山不转水转，还是有办法实现的嘛。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010144637.png)

我们既然可以获取到下一层的fragment，那么只要我们循环写的好，那么不管他有多少层都还是拿的到想要层的fragment的。但是说回来了，何必呢？

还有一个问题，还是上面的这种情况。比如说A 是activity，B,C,D,是fragment。B放置到A中，C放置到B中，D放置到C中.我设置的FrameLayout的id是一样的，且布局一样，可以显示吗？我记得好像是不能显示的，但是又说回来，为啥要一样啊，你这么写是难为谁啊？不同功能的FrameLayout id 设置为不一样不好理解吗？都叫frame_child，我怕你被打死。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。

[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)  

