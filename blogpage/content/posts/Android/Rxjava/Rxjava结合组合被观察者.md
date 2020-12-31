+++
date = "2020-12-31"
title = "Rxjava组合结合被观察者Observable"
description = "Rxjava组合被观察者Observable"
tags = [ "android","Rxjava"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
thumbnail = "https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201230114639.png"
+++
# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>

# 正文 
## Observable
### And/ Then/When -联合组由两个或更多发射观测量项借助于Pattern与Plan中介
### CombineLatest —当两个Observable之一发射一个项目时，通过指定的函数合并每个Observable发射的最新项目，并根据此函数的结果发射项目
### Join —在根据另一个可观察对象发出的项目定义的时间窗口中，只要发射了一个可观察对象的项目，则合并两个可观察对象发出的项目
### Merge -通过合并排放量将多个可观测值合并为一个
### StartWith —在开始从源中发出项目之前，发出指定的项目序列
### Switch —将发出Observable的Observable转换为发出可观察到的最新事物的单个Observable
### Zip —通过指定的函数将多个可观测对象的发射合并在一起，并根据此函数的结果为每个组合发射单个项目

# Rxjava所有方法
[可观察算子的字母顺序列表](http://reactivex.io/documentation/operators.html)