+++
date = "2020-10-01"
title = "Android并发"
description = "Android并发"
categories = [
    "android基础"
]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> android 上常用的耗时操作。
* 文件下载
* 网络获取
* 图片加载
* 数据库读写
* 业务逻辑


* 单核CPU 一次只能处理1线程
* 从物理角度上讲，系统执行都是但线程串行。
* 多核之间存在并行。
* 1核-> 处理资源->时间片的轮转机制
* linux 下单核最大是1000个线程，windows 最大2000线程
* JMM 是JAVA 实际的内存架构
* JVM运行时数据区 


JMM内存模型 8大原子操作
* 从主内存中读取
* 
## 结束


