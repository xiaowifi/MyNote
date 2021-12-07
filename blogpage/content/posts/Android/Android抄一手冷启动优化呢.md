+++
date = "2021-08-08"
title = "Android抄一手冷启动优化呢"
description = "Android抄一手冷启动优化呢"
categories = [
"android基础"
]
featured = true

+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> Android 启动优化 还是蛮重要的呀，常见的耗时操作一般在于onCreat里面，或者设置界面的layout的时候，或者就是一些初始化SDK,一些IO操作。
> 现在应用市场开始检测 给予权限前是否有对应的初始化了，这个也促使了一部分启动优化的加速吧。

参考资料：
* [中国大学 MOOC Android 性能优化：冷启动优化总结](https://juejin.cn/post/6992762826385260558?utm_source=gold_browser_extension)
* [应用宝隐私审核规范](https://wiki.open.qq.com/index.php?title=%E9%9A%90%E7%A7%81%E6%9D%83%E9%99%90%E5%AE%A1%E6%A0%B8%E8%A7%84%E8%8C%83)
    * 违规使用个人信息。重点整治APP、SDK未向用户告知且未经用户同意，私自使用个人信息，将用户个人信息用于其提供服务之外的目的，特别是私自向其他应用或服务器发送、共享用户个人信息的行为。
## 正文
app 启动优化大致可以分为下面几个方面。
* 复杂界面view异步加载，androidx.asynclayoutinflater:asynclayoutinflater进行异步加载 xml 文件。
* 对于不需要一开始就初始化的内容，在需要的时候进行初始化。
* 能放到子线程中初始化的内容，尽量放到子线程中初始化。
* 开始严苛模式（StrictMode）
* 使用ConstraintLayout 减少布局渲染
* IdleHandler 需要在当前线程中处理耗时任务，并且并不需要马上执行的话，可以使用IdleHandler这样该任务可以消息队列空闲时，被处理。
* 减少代码量。kotlin 可以减少代码量（这个感觉有点迷幻，code 代码过多启动也会变慢，那资源文件过多呢？）
* 减少JAVA 静态方法和静态变量？这个感觉是一种思路。
* 减少xml SharedPreferences 这个调调一开始就会加载当前xml 所以内容。
* 图片加载采用压缩图片，不要加载原图，网络图片也是如此，第3方文件服务器有压缩裁剪服务的。
> 主要还是前面几条，后面几条感觉和启动优化没有啥关系。
### 如何获取应用方法启动耗时？
* 常见的问题就是在方法前后打log。
* CPU Profile 或者 Debug.startMethodTracing 进行监控并导出 trace 文件进行分析。不管哪种方式，采集堆栈信息都有两种模式：采样模式和追踪模式。追踪模式会一直抓取数据，对设备性能要求较高。
* 利用Transform + ASM字节码修改技术动态插入代码
* Hook Instrumentation
* Hook Handler或者Looper的Printer
* 系统log  activityManger 可以打印相关时间。
* adb 获取启动时间。
* debug.startMethodTrcing 方法耗时 监听
* 为啥Android APP启动会创建一个默认窗口，为了更好的交互，给用户点击效果。
* 温启动，就是一直返回到第一个界面，但是进程还在的那个时间段，UI没有了。
### 黑白屏 优化
* 在style 中对启动界面中的取消预览(空白窗口) 设置为true    android:windowDisablePreview 
* 第2种，设置窗体为透明色：windowIsTranslucent true 
* 设置window 设置为某种图片
* 在清单文件先设置样式主题，然后在onCreate 中重新设置主题。
* 预览界面替换。
## 结束


