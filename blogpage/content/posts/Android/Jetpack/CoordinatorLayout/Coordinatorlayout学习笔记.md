+++
date = "2021-5-11"
title = "Coordinatorlayout 学习笔记"
description = "Coordinatorlayout 学习笔记"
tags = [ "Coordinatorlayout"]
categories = [
    "android基础"
]
featured = true
+++

## 正文
 参考资料。
> [google 官网](https://developer.android.google.cn/reference/androidx/coordinatorlayout/widget/package-summary?hl=zh-cn)
> 通过官网可以知道，CoordinatorLayout 是一个超级强大的 FrameLayout。
> * 作为顶级应用程序装饰或镀铬布局 
> * 作为与一个或多个子视图进行特定交互的容器  
    通过指定Behaviors CoordinatorLayout 的子视图，您可以在单个父视图中提供许多不同的交互，并且这些视图也可以相互交互。视图类可以在用作使用CoordinatorLayout.DefaultBehavior注释的 CoordinatorLayout 的子项时指定默认行为 。
行为可用于实现各种交互和额外的布局修改，范围从滑动抽屉和面板到滑动可关闭元素和按钮，这些元素在移动和动画时会粘在其他元素上。
<br>
CoordinatorLayout主要提供了三种方式来实现child之间的互动:

* 通过anchor实现 这个通过相对某个view 定位
* 通过insetEdge实现 
* 通过Behaviors实现 这个实现滚动联动


> 整体来说。CoordinatorLayout其实就是提供了一个可以操作各种Behavior的容器，如果不适用Behavior，那么他就是一个单纯的FrameLayout。
> 系统为我们提供了几个默认的联动Behavior:
* com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 

其他属性:
    * keylines keyline是MaterialDesign中一个非常好的布局解决方案，具体的定义可以参阅Keylines and spacing和Ratio keylines [Keylines and spacing和Ratio keylines](https://material.io/guidelines/layout/metrics-keylines.html#metrics-keylines-keylines-spacing)
    * layout_keyline 
    * statusBarBackground
    * layout_anchor 此视图应相对于的定位点视图的id。
    * layout_anchorGravity 相对定位的 定位方式
    * layout_insetEdge
    * layout_dodgeInsetEdges

## 结束


