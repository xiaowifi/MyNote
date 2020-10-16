+++
date = "2020-10-01"
title = "picgo+gitee配置图床404"
description = "picgo+gitee配置图床404"
tags = ["日常搬砖", "图床","gitee","picgo"]
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

之前逛掘金沸点的时候。得到了某些大佬的指点，然后知道了一个叫图床的神器，反正都是写笔记，搞一个图床也没有啥问题，表示非常喜欢md 的笔记风格。

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

## 正文

我是通过 picgo+ gitee  图床的。这个教程很多，都写的蛮好的，就不贴了。今天准备认真工作<img src="https://i.loli.net/2020/09/22/RMG8JalA42dPnos.png" style="zoom:33%;" />的时候，发现图床上传不了图片，抛了一个404。具体log 稍后再贴。原因也很简单。gitee 的图床插件有两个。![](https://i.loli.net/2020/09/22/Fk4L7ejxmNXDIqz.png)

我最开始用的是第一个，也就是![](https://i.loli.net/2020/09/22/iCdSZHzjOxBGK3E.png) 第一天使用的时候是没有问题的。

但是昨天404，我换成了另外一个就好了，今天又变成了第一个了？于是我把第一个禁用了，换成了第2个。

### 图片上传404 解决方案

**如果安装的插件是第一个出现上传图片404，我遇到的解决方式就是先把第一个禁用了，然后安装第2个。**

我也搜索了不少博客，404 通常是配置的时候没有配置对，像这种插件导致的懵逼问题好像没有，但愿你也没有遇到。拿我的图床举例。![](https://i.loli.net/2020/09/22/tq3yYj4duwpS2Va.png)

我的名字和链接地址上是不一样的，我项目的名字是luoye.yangfan/日常搬砖文档，而链接地址是/lalalaxiaowifi/pictures。在picgo的图床配置中 需要使用的是链接地址中的值。![](https://i.loli.net/2020/09/22/IMpm7oKSa6sO9QV.png)

还有token 一定要填正确，一定要有master 分支等等。我还是建议用![](https://i.loli.net/2020/09/22/UkycnpJ8j6SNlWQ.png) 这个插件。如果还是404那就换一个或者配置有问题。图床项目好像是要求公开的（我没有试私有）。



### 404 log

> {"name":"StatusCodeError","statusCode":404,"message":"404 - \"<!DOCTYPE html>\\n<html>\\n\\n<head>\\n  <meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=UTF-8\\\" />\\n  <title>你所访问的页面不存在 (404)</title>}

因为那个404 有点长就不贴完了。这就是页面没有找到，如果配置没有问题，那就是插件的问题了。

### picgo 如何查看log

查看log 就很简单了，因为mac 通知无法复制，所以我找404log 都找了一会，终于在![](https://i.loli.net/2020/09/22/KtIhjmRlpvg5kxb.png) 



![](https://i.loli.net/2020/09/22/ujpdUkwNXtzFSys.png)



打开就好了。不知道Windows 版本是长什么样，应该也差不多吧。

### 重点

如果不设置为默认图床，那么会上传到一个其他图床https://i.loli.net上面去，我的就上传到这个上面去了，设置为默认的就会直接上传到自己的图床(如果配置没有问题的话);



## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。