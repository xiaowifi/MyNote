+++
date = "2020-10-01"
title = "Android时间相关view"
description = "Android时间相关view"
categories = [
    "Android基础"
]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 话说，Android 上整时间相关的情况也是蛮多的，比如时间选择器，日期选择器，显示一个时钟，显示一个详细时间，验证码倒计时（计时器）等等。
>选择器相关的就不多描述了，一般都是通过wheelVew 做滚动联动或者就是列表做联动，或者就是第3方的控件拖进来，当前属于笔记就不整这些了。
> 搬砖多年，突然看到基本Android基础书籍，才发现自己好多都忘记了。
## 正文
### 模拟时钟
AnalogClock 
### 详细时间
DigitalClock
### 计时器
这个就比较骚了，很少有想到有基础控件的，我看过很多都是handle 去实现，或者梦想点就是Rxjava,区别在于有些大佬杀界面重新进去会复活之前已经刷新过的倒计时，有些大佬不会。

Chronometer 

## 结束


