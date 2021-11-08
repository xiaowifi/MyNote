+++
date = "2021-3-2"
title = "Mpaas阿里系列Android使用目录"
description = "Mpaas阿里系列Android使用目录"
slug = "dgg"
series = ["Mpaas"]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
## 前言
顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 正文
> 现在公司决定使用这个调调，然后呢，就是我们需要学习使用这个调调。
> [Mpaas 阿里文档](https://help.aliyun.com/document_detail/49549.html?spm=a2c4g.11186623.6.541.62cf6d2atEV31n)
> mpaas 提供Android studio 插件，在插件中选择使用。但是还是会存在一些使用问题，比如说不知道这个包是干啥的。所以就是这个笔记存在的意义。

* MicroApplicationContextImpl 打开的实现类。
* sourceAppId 传递的null.
* targetAppId 是需要打开的小城的ID
* startParams 启动传递参数。
* sceneParams 传递的null 
  * 当sceneParams 等于null ,new 了一个bundle 
* fragmentActivity 传递null

> Class.forName("com.alipay.mobile.framework.service.common.impl.StartAppReflectModel");
*  导致 q 对象为null
* 但是执行到方法:a 
* startParamsCopy 是startParams 复制出来的。
* args 数组 存放：sourceAppId(null), targetAppId(小程序id), startParamsCopy（小程序启动参数）, fragmentActivity(null), sceneParams(new的一个非null的bundle)
* newStartParams =startParamsCopy 
* 第二次启动 aroundResult =null 
* newStartParams 添加 REALLY_STARTAPP=true
* 创建Runnable =b 
* sourceAppIds=赋值 ig_instantStartApp，第2次启动没有找到 赋值为null  
* 执行到 doStartApp
* 之后执行到。onStartAppReject