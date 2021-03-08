+++
date = "2021-3-8"
title = "Databinding笔记整理呀"
description = "Databinding笔记整理呀"
categories = [
"android基础"
]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
>顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 
### 前言
> 由于现在jetpack的流行，这个还是蛮香的，之前databinding才出来的时候，简单了解过。那个时候还没有写本地entity的习惯，数据都是依赖于服务器模型。现在好了，都是本地模型和服务器模型互转。就没有这个问题了，
> 这个调调还是可以省很多时间精力的。但是这个就得考验设计能力了，不像之前随便搬砖什么的。

### 正文
#### 开启databinding的使用能力
> 有一段时间迷糊了，以为需要手动导入包什么的。其实只需要配置一下，gradle就帮忙导入了，不需要在dependencies声明。比如像这个样子:

````aidl
//    启用databinding 放到android 下面。
   android {
    dataBinding {
        enabled = true
    }
}
````
#### xml使用
> 这个调调主要是在xml 中配置数据绑定，事件处理什么的，所以都是操作xml.emmmmmm? 那我viewmodel 不就处理数据了。奈斯.png。还有一个问题，事件还是应该写到界面层吧，但是我决定viewmodel 也可以处理事件来着。
> 比如说，mvp 的事件就是在p层，但是p层我处理网络请求了，结果事件写到view层，然后事件真实处理就到了界面层。有点晕，M层是啥。
> 容我捋一捋。mvc中，m是业务逻辑，v是视图也就是界面层，c是中间层进行m和v的联动。mvp中m是业务逻辑数据，v还是视图和界面层，p是数据处理，emmmmm?和C莫得区别吧。百度上说”在MVP中View并不直接使用Model，它们之间的通信是通过Presenter (MVC中的Controller)来进行的，所有的交互都发生在Presenter内部，而在MVC中View会直接从Model中读取数据而不是通过 Controller。“
> 我肯定是没有睡醒，现在C和P傻傻分不清楚了。算了，待会开一个笔记整理一波。
