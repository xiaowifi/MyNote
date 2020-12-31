+++
date = "2020-12-31"
title = "Rxjava过滤被观察者Observable"
description = "Rxjava过滤被观察者Observable"
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
主要是过滤被观察者Observable
### Debounce —仅在经过特定时间跨度时才从Observable发出一项，而不发出另一项
### Distinct -抑制可观察对象发出的重复项
### ElementAt—仅发射可观察对象发射的项目n
### Filter —仅从可观察对象中发出通过谓词测试的项
### First —仅从Observable发射第一项或满足条件的第一项
### IgnoreElements —不要从Observable发出任何项目，而是镜像其终止通知
### Last —只发射可观察对象发射的最后一个项目
### Sample —定期发射Observable发射的最新项目
### Skip—抑制Observable发出的前n个项目
### SkipLast—抑制Observable发出的最后n个项目
### Take—仅发射可观察对象发射的前n个项目
### TakeLast—只发射可观察对象发射的最后n个项目

# Rxjava所有方法
[可观察算子的字母顺序列表](http://reactivex.io/documentation/operators.html)