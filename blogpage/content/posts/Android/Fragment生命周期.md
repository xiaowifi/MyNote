+++
date = "2020-12-28"
title = "fragment生命周期导致数据初始化问题"
description = "fragment生命周期导致数据初始化问题"
tags = [ "fragment"]
categories = [
    "android基础"
]
series = ["fragment"]
featured = true

+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
# 前言
话说，activity 和fragment 都是有生命周期的，而且加载也并不是new完对象就加载出来了的，new 一个activity并不多见，但是基于activity new 一个fragment还是很常见的，但是很少有需要在activity中调用fragment方法的时候，但是存在可能性。
因为fragment加载的特性，所以像loading层保证唯一，这样子的就不能直接判断fragmentmanger 中是否包含了，需要一个其他值去判断。而且传参方面，也是这个样子。
比如说我的createPresenter() 方法是当fragment 走oncreate() 的时候创建调用的，但是我上一行代码创建完fragment就直接调用presenter 就会抛空指针异常。
# 解决思路
## 思路1 绑定生命周期
我们知道 fragment.getLifecycle().addObserver(observer) 这个可以添加生命周期监听，那么我们对fragment 添加了监听，等走到对应的生命周期的方法才执行对应的代码就好。这样也就避免了，activity 不知道fragment什么时候加载完成导致调用问题了。
但是这种情况下，如果我fragment中再嵌套一个fragment呢？内部的fragment 生命周期万一也关联到外部的activity调用呢？用多个observer去调出来，就是有点怪怪的。那么只能从代码设计上更改了。
## 思路2 更改设计模式
### 修改绑定生命周期部分
这个思路肯定是不行的，绑定生命周期就是为了优化内存使用的，而且绑定生命周期也对view的加载显示控制提供了生命范围，防止错误的时间，错误的地点导致操作崩溃。
### 那就修改功能对应的界面的设计
> 先说功能吧，对某个数据表进行 折线图，饼图，柱状图等图表的显示，这些图表支持 日周月年的时间切换。先进入某个功能点，确定数据来源，然后选择图表类型，进入下一个界面，最后是选择时间切换。默认加载天的数据。
> 我就一想，既然每个图表需求显示的view是固定的，那么activity只需要加载一个fragment，由fragment 内部加载不同的图表fragment，图表fragment 只需要显示fragment传入的数据就行。fragment进行数据获取和数据转换，activity进行时间更改转化，然后传入进去。
> 那么问题就来了，因为activity控制时间，所以第一次的时间需要由activity传入到fragment中，然而，activity并不知道fragment是否加载完成，所以activity需要知道fragment的生命周期，fragment拿到数据知道，也不知道图表fragment是否加载完成，所以也需要拿到生命周期，如果不做延时处理，那么activity就需要拿到两层fragment的生命周期，再传入值。
> 但是做线程延时，判断生命周期调用，也需要先将 我传入的值存储到两个fragment里面，等到需要的生命周期再显示，这个和我直接通过对象方法传参没有任何区别吧。所以还是生命周期传参导致的问题。
> 那么就优化设计逻辑，activity 只持有一层图表fragment，图表fragment进行数据拉取和处理显示就好，只是图表层的presenter 需要将所有的功能点的数据全部拉取一遍罢了。当然进行时间切换的时候，也需要判断当前fragment是否加载出来。

# 总结
感觉，mvp和mvvm 其实也是一种代码规范，感觉通过activity 向fragment对象方法传参就有点违背了这种规范的初衷吧。所以像eventbus 这种传参，就很实用,完全不需要考虑生命周期，当然Android 提供的广播也是可以的。
<br>[EventBus GitHub](https://github.com/greenrobot/EventBus)<br>
activity中注册后，在fragment中就不用再注册监听了，提供方法就直接会调用。因为fragment属于延时加载，所以尽量使用postSticky 。接收：
````  
      @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
      public void onChangeData(ChartDetailsMessage message){
          getPresenter().getDatas(message);
      }
````