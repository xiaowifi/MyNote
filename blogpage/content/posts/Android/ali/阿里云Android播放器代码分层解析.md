+++
date = "2021-6-5"
title = "阿里云Android播放器控制层代码分层解析"
description = "阿里云Android播放器控制层代码分层解析"
tags = [ "阿里相关"]
categories = [
    "android基础"
]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 虽然搬砖多年，代码复制了很多。然后尝试去理解别人代码，但是整理相关代码设计逻辑还是大姑娘上花轿头一回。<br>
> 这次项目基于AndroidX。所以和Android 有一些区别，在拖代码的过程中，就存在需要理解代码了。<br>
[阿里云点播服务文档](https://help.aliyun.com/document_detail/124714.html?spm=a2c4g.11186623.6.1163.346f7c44IaXRhR) <br>
[阿里云点播demo下载](https://help.aliyun.com/document_detail/94328.htm?spm=a2c4g.11186623.2.11.4ebb10e4lpM3WG#multiTask3266) <br>
## 正文 
当前笔记基于阿里云点播Android  播放器 AliyunVodPlayerView 。
### 分层
> 基于RelativeLayout 添加view。按照功能继续view的分解。
* 控制层，用于实现播放器控制效果，包含，全屏，小屏，播放，暂停，进度条，拖拽条，截屏，标题，返回键，倍速，分辨率等功能文本显示。
* 手势层，用于进行手势分发，比如切换明暗度，声音，进度条拖拽，点击，双击。
* 视频画面层。这个就是视频播放画面，单纯的视频播放。
* 引导层，用于第一次操作时候的引导。
* 播放器封面 
* 提示层，比如网络提示，播放完成，播放错误等等。这个提示层 内部又进行了分发封装。
* 播放内核内核+视频画布层。
### 监听回调 
> 这个比较多，大体就是在分层上进行处理。比如说返回事件，在控制层，提示层中都有，而且提示层中，包含错误，播放完成，网络提示，这些都有占位view，所以，实现方式大概就是。
> 错误层的返回事件回调到提示层的监听中，提示层的监听是单独的接口，播放器实现提示的监听接口，去调用播放器中的返回事件。<br>
> 整体思路就是，每一层都有自己的实现，自己的实现去调用上一层的实现。最终同一个功能会归集到一个实现方法上面。
### view的添加
> 添加view 就很简单，初始化的时候直接添加到view中
````aidl
 private void addSubView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //添加到布局中
        addView(view, params);
    }
````
### 基础控件的隐藏
>  这个主要是控制层的几秒后自动隐藏，这个内部实现handler，然后自己调用 隐藏方法。
## 结束


