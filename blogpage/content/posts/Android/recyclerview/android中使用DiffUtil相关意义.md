+++
date = "2020-10-01"
title = "android中使用DiffUtil的意义"
description = "android中使用DiffUtil的意义"
categories = [
"android基础"
]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 最近在进行项目的技术性优化改造。比如post请求添加缓存，刷新优化等等，刷新机制不再使用无脑的刷新整个item了，需要做到局部刷新，而缓存机制的添加，使得本身就体现不怎么良好的刷新情景雪上
> 霜，毕竟没有缓存，闪一次。有了缓存，同样的刷新机制，会闪两次，手动刷新还是一样，将老数据替换为新数据。这么一想，除了会闪两次以外好像没有什么问题。
> 但是，当我们的fragment 的生成又服务器控制的时候，似乎又不能这个样子，在自己写了一一大截的刷新机制的时候。
## 结束


