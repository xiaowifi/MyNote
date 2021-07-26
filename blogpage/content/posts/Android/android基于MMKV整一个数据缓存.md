+++
date = "2021-7-20"
title = "android基于MMKV整一个数据缓存"
description = "android基于MMKV整一个数据缓存"
tags = [ "mmkv"]
categories = [
"android基础"
]

featured = true
draft = false 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 这是一个巨骚的想法，从某些意义上讲，是可以使用的，但是这个主要是作为一种骚想法去实现。
> mmkv介绍上说的是 比SharePreferences 性能更好，SharePreferences支持多xml 存储，他也支持。SharePreferences初始化后就存储到内存中了，他不会也是这个样子吧，他本身就是用来替换这个的。
> 所以不建议使用 mmkv 做接口数据缓存。但是数据库又不想写，骚操作嘛，那就无所谓了。
> 自从我知道 Java 可以通过某些手段获取到其实现子类之后，骚想法是一个接着一个，但是这个调调尤为突出。
> 但是真的不建议 这个用来存储接口数据，嗯，真的不建议。
## 结束


