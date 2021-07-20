+++
date = "2020-10-01"
title = "Android行高和文本显示于设计冲突"
description = "Android行高和文本显示于设计冲突."
categories = [
"android基础"
]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 话说，Android textview 应该算是最常用的控件了。但是现在的设计文本大多数都是内容居中对齐。
> 但是 textview 却搞事情的有一个内间距。
## 正文
````aidl
    <item name="android:includeFontPadding">false</item>
````
将上面代码放到 全局的style中就可以达到效果了。
然后是行距，这个就需要手动算了。
android:lineSpacingExtra="3dp"
## 结束


