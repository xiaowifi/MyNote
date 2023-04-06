
## 前言
> 话说，最近在封装adapter。发现了smart刷新中也包含了空，loading，和尾部。我人没了。感觉造了一个假轮子。
> 于是，痛定思痛，adapter 添加一个刷新头不就好了吗？我真是一个小机灵鬼。
> 在打算复制smart代码都时候，第一行就是 Scroller ？emmmmm? 到处都是知识点盲区。
###  Scroller 简单介绍
> 下面这句话是抄的。

[android Scroller 官网](https://developer.android.com/reference/android/widget/Scroller)  
[OverScroller](https://developer.android.com/reference/android/widget/OverScroller)
Scroller是一个专门用于处理滚动效果的工具类，可能在大多数情况下，我们直接使用Scroller的场景并不多，但是很多大家所熟知的控件在内部都是使用Scroller来实现的，如ViewPager、ListView等。而如果能够把Scroller的用法熟练掌握的话，我们自己也可以轻松实现出类似于ViewPager这样的功能。那么首先新建一个ScrollerTest项目，今天就让我们通过例子来学习一下吧。
先撇开Scroller类不谈。
其实任何一个控件都是可以滚动的，因为在View类当中有scrollTo()和scrollBy()这两个方法。
这个类封装了滚动。您可以使用滚动条（Scroller 或OverScroller）来收集生成滚动动画所需的数据——例如，
响应一个滑动手势。Scrollers 会随着时间的推移为您跟踪滚动偏移，但它们不会自动将这些位置应用于您的视图。您有责任以可以使滚动动画看起来平滑的速度获取和应用新坐标。
## 正文
> 既然所有的可以可以滚动到view都有这个方法？我有点不一样的想法。



