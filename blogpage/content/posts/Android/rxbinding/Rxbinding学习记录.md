+++
date = "2021-3-11"
title = "Rxbinding学习笔记"
description = "Rxbinding学习笔记"

tags = [ "android","Rxjava"]
categories = [
"技术类"
]
series = ["android基础"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> [RxBinding GitHub地址](https://github.com/JakeWharton/RxBinding)
## 正文
> 主要是自己定义了一个Observable，然后调用Rx相关的api.因为Observable消息的发送就是在Observable中，所以可以自定义Observable 逻辑达到自己想要的效果。
### 事件防止重复
> 因为android 上的pop或者dialog并不是第一时间加载出来的。所以点击事件在逻辑上可能出现点击了很多次，然后弹窗才出现，如果点击事件没有弹窗遮挡事件，那么就可能导致同一个方法或者网络请求被请求了多次，这个就很骚了。
> 所以点击事件防止重复点击还是蛮重要的。

现阶段，android都在国内搬砖多年了，各种成熟的方法都有。[这篇博客就写的很细《Android处理按钮重复点击事件》](https://blog.csdn.net/freak_csh/article/details/89477388) 。
* 记录调用时间，在某个时间段内可以return。
* 封装上一个逻辑，然后统一调用。
* 基于Rxjava的延时等过滤操作符自己实现Observable，然后设置操作符。
* Aspectj 这个没有用过。
#### Rxbinding Rxjava
> 这个调调主要是提供了一个各种view的Observable。

* debounce 
## 结束
>

