+++
date = "2021-5-11"
title = "android滚动事件相关-1"
description = "android滚动事件相关-1"
categories = [
"android基础"
]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 现在第3方的或者android自己的写的太好了。导致大多数情况下，滑动处理都不需要自己处理了，比如说吸顶效果。比如说两个滚动view联动。
[参考资料](https://www.jianshu.com/p/5ffb37226e72/)
## 正文
> 比如说吸顶效果的实现方式有多种，感觉最完美的CoordinatorLayout。


> CoordinatorLayout是android support design推出的新布局，主要用于作为视图根布局以及协调子控件的行为，而Behavior就是用于直接子控件来协调自身CoordinatorLayout以及和其他子控件的关系，使用Behavior的控件必须是直接从属于CoordinatorLayout。
 在传统的事件分发流程中，在子控件处理事件过程中，父控件是可以进行拦截的，但一旦父控件进行拦截，那么这次事件只能由父控件处理，而不能再由子控件处理了。

> 在android5.0之后新的嵌套滑动机制中，引入了：NestScrollChild和NestedScrollingParent两个接口，用于协调子父控件滑动状态，而CoordinatorLayout实现了NestedScrollingParent接口，在实现了NestScrollChild这个接口的子控件在滑动时会调用NestedScrollingParent接口的相关方法，将事件发给父控件，由父控件决定是否消费当前事件，在CoordinatorLayout实现的NestedScrollingParent相关方法中会调用Behavior内部的方法。
 我们实现Behavior的方法，就可以嵌入整个CoordinatorLayout所构造的嵌套滑动机制中，可以获取到两个方面的内容：


> 1、某个view监听另一个view的状态变化，例如大小、位置、显示状态等
需要重写layoutDependsOn和onDependentViewChanged方法


> 2、某个view监听CoordinatorLayout内NestedScrollingChild的接口实现类的滑动状态
重写onStartNestedScroll和onNestedPreScroll方法。注意：是监听实现了NestedScrollingChild的接口实现类的滑动状态，这就可以解释为什么不能用ScrollView而用NestScrollView来滑动了。

所以可以定义两种。
* NestScrollChild和NestedScrollingParent两个接口，用于协调子父控件滑动状态
* 在CoordinatorLayout中实现Behavior 

## 结束 


