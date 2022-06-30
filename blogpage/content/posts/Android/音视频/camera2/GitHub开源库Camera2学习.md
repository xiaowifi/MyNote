
## 资料
* [项目地址 ](https://github.com/SereneGuest/Camera2)
* [google camera2 官方地址](https://developer.android.google.cn/reference/android/hardware/camera2/package-summary)
* [google camera2 gitHub Demo地址](https://github.com/android/camera-samples)
## 前言
> camera2 Google 提供的新版本相机API,使用较为复杂。当前主要的对于Camera2 这个库进行描述，很多东西都值得学习。无论是API 调用还是设计模式。
# 正文
当前主要是对于 Camera2 开源库进行类的描述。通过简单的类的描述。去理解整体的设计思路，同时对于功能的整体的了解。毕竟每个人都有自己的理解。
## maven 
````aidl
    debugImplementation 'com.squareup.leakcanary:leakcanary-android:1.5.4'
    releaseImplementation 'com.squareup.leakcanary:leakcanary-android-no-op:1.5.4'
````
> 果然Camera2 是系统api,都不需要导入maven库的，回头一想，调用硬件与系统API的竟然想要搞maven，就离谱，编码解码什么怎么能是JAVA 代码呢？
> leakcanary 这个调调是自动检测内存泄露工具。贼猛。内存相关的blog 还没有开始写，flag立了快一个月了，不过相机相关的立了快半年了，快了，快了。
> 但愿我再次温习这个调调的时候，[我的blog](https://gitee.com/lalalaxiaowifi/pictures/tree/bolg/blogpage/content/posts/Android) 中已经把内存相关的写好了吧。
> 最近在听费曼学习法，这个调调也贼猛，可惜我的笔记依旧还没有开始写。但愿[我的读书笔记](https://gitee.com/lalalaxiaowifi/pictures/tree/bolg/blogpage/content/posts/%E8%AF%BB%E4%B9%A6)
> 可以得以完善吧。毕竟他还没有开始动，还只是一个计划，遥不可及。这里不得不感叹人是有限的。
## 包分析 
* CameraApp 这个调调主要是初始化性能检测工具的。
* Config 配置
* CameraActivity 唯一的activity，主要是Demo 内容也不多，只需要一个activity 显示不同功能的fragment就行。
### callback
* CameraUiEvent 相机UI事件
    * onPreviewUiReady 相机预览准备就绪。
    * onPreviewUiDestroy 相机预览被销毁
    * onTouchToFocus 点击区域，主要是做聚焦 
    * resetTouchToFocus 重置聚焦 
    * onSettingChange 配置更改 
    * onAction 事件 
> 个人理解，定义这个调调主要是预览画布的是随着view的大小会重新创建的。而解码器是需要宽高的，所以和预览画布的绑定在一起也合理，同时作为唯一JAVA 层的view，设置点击触摸事件也合理是吧，
> 这个Demo 提供了相机录制，拍照等等功能，定义一个接口也合理是吧。
* MenuInfo 摄像头或者配置回调 
    * getCameraIdList 获取相机id 列表 
    * getCurrentCameraId 获取当前选中的相机
    * getCurrentValue 这个好像是获取配置 
> 这个调调和相机API就比较契合了，获取摄像头信息。视频录制和拍照走不同的逻辑，这个合理是吧。    
* RequestCallback 发起事件的回调
    * onDataBack 当数据回来，这个要做数据分析还是图片处理哦。
    * onRequestComplete 请求完成 
    * onViewChange 这个是宽高发生改变吗 
    * onAFStateChanged AF是:自动对焦
    * onRecordStarted 相机开始录制
    * onRecordStopped 相机结束录制
> 相机的调用都讲究回调，怎么去理解呢，调用硬件了，人家总要给你一个反馈是吧。但是他这个封装，是基于Demo已有的功能的。
### data
* CamListPreference 偏好设置？没有看懂
* CamMenuPreference 是CamListPreference的子类，maven的偏好设置
* PreferenceGroup 装CamListPreference的容器 
* PrefListAdapter  recyclerView 的adapter  
* SubPrefListAdapter  recyclerView 的adapter  
>  这些好像都是 PreferenceFragment 使用的。是吧，又是一个TODO  
### exif
### manager
### module 
### UI 
### utils
