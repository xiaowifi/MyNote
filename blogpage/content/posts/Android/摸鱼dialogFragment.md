
## 前言

话说dialogfragment。这个调调，我用的还是蛮多的。像popwindow,loading，或者一些dialog如果能够用dialogfragment 实现，我一般会选择用这个调调。毕竟有生命周期，感觉和写fragment没有区别。这就导致我基于dialog 实现自定义dialog的能力比较差，主要还是主题设置，设置好了还是感觉差不多的。当然了popwindow的优点还是dialog 或者dialogfragment 无法比拟的。还有一个原因，我比较喜欢将一些值存储到activity的intent中(这么写，还是要提供完整的注释的，要不然不习惯这种写法的会看得很难受)，感觉dialog 中获取activity 没有fragment中那么方便。但是我看好多UI库都是自己写了一个dialog(比如说QMUI) ?搞得我有点懵逼。但是我看别人的代码，很少用dialogfragment ![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201020091854.png)

为啥我看到的代码里面，大家都用dialog 而不是dialogfragment ?这个以后探讨吧。

顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 

## 正文

话说这个调调DialogFragment和其他控件差不多，也是从28以后移出出去了。看来把代码适配更新到Android X是需要提上日程了。关于基础用法这个网络上很多大佬写过博客的，我觉得没有重复的必要的，和fragment 差不多，还是重要的一点，配置style会影响dialogfragment的显示效果的，状态栏也可以按照需求配置的。

### 主题

我所使用的主题。通常是全屏的。这个需要通过自己的需求配置

```
<style name="Dialog.FullScreen" parent="AppTheme">
    <item name="android:windowNoTitle">true</item>
    <item name="android:windowBackground">@color/transparent</item>
    <item name="android:windowIsFloating">false</item>
    <item name="android:textColor">@color/color_333333</item>
    <item name="android:textSize">14sp</item>
    <item name="android:backgroundDimEnabled">true</item>
</style>

<style name="DialogNoDim" parent="Dialog.FullScreen">
        <item name="android:backgroundDimEnabled">false</item>
    </style>
```

使用:

```
@Override
public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
}
```

其他设置：

```
@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    getDialog().setCancelable(false);
    getDialog().setCanceledOnTouchOutside(false);
}
```

### 显示

比如说我基于dialogFragment 或者其子类写了一个 NormalDialog，那么显示大概就是这个样子的。

```
NormalDialog dialog = new NormalDialog();
dialog.show(getSupportFragmentManager(), "NormalDialog");
```

###  关闭

通常是根据需求，通过dialogfragment.dismiss();如果宿主activity 被关闭了，再调用dismiss() 会抛错。

### 与activity互动

通过上面的笔记可以知道，这个显示和fragment加载还是有点区别的，其他这个调调代码少了很多，且是浮到activity上面的感觉，而fragment是包含在activity里面的。但是又是基于FragmentManager 显示的。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png) 我觉得，activity 可以通过FragmentManager控制整个dialogfragment.这么一想，我fragment里面 是不是也可以控制当前fragment显示与否，而不是通过回调或者通知让activity控制显示与否(这个需求有点少，除非你用fragment去实现多状态布局(比如说：正常逻辑层，数据错误层，空界面，登录过期等等，但是这种逻辑怕是用fragment要被锤爆脑阔吧))。



ok 我们还是把话题拉回来，我想知道，我们通过FragmentManager 显示了一个dialogfragment,可以通过activity控制显示与否吗？按照道理将应该是这个样子的。显示后通过FragmentManager可以获取到添加的dialogfragment,而diaologfragment 执行dismiss()后，fragmentmanager 却获取不到这个dialogfragment .这说明dialogFragment 被移出出去。我们知道同一个clss 生成的不同的对象往fragmentmanager 中添加的时候是不会抛错的，所以这个dialogfragment 显示的时候连接调用两次也会出现两个dialog 哦。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 

