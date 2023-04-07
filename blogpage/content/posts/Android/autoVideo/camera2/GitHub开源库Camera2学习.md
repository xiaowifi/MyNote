
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
## 类关系图
> 再次感谢 IDEA 插件 Diagram。毕竟我PlantUML 的使用是一个垃圾。
![](https://s2.loli.net/2022/06/30/XYBqbSHQewlVWjt.png)
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
> 这个没有看懂，我觉得我需要一张类关系图。算了，类图搞出来也不行，关系太绕了，sorry啊
* ByteBufferInputStream InputStream子类
* CountedDataInputStream FilterInputStream 子类 
* ExifData 
* ExifInterface
* ExifInvalidFormatException
* ExifModifier
* ExifOutputStream
* ExifParser
* ExifReader
* ExifTag
* IfdData
* IfdId
* JpegHeader
* OrderedDataOutputStream
* Rational 
### manager 
* CameraSession 
* CameraSettings
* CameraToolKit
* Controller
* DeviceManager
* DualDeviceManager
* FocusOverlayManager
* ModuleManager
* RequestManager
* Session 
* SingleDeviceManager
* StateManager
* VideoSession
### module 
* CameraFragment 真正控制显示的fragment。
* SettingFragment PreferenceFragment的子类，做设置用的。
* CameraModule 其他module的父类，主要是定义统一的功能
* DualCameraModule 双摄像头模式 CameraModule子类 
* PhotoModule 拍照模式 CameraModule子类
* ProfessionalModule 专业模式 CameraModule子类
* VideoModule 视频录制 CameraModule子类
### UI 
* AppBaseUI
* CameraBaseMenu
* CameraBaseUI
* CameraMenu
* CameraSubMenu
* CameraSubMenu
* CoverView
* DualCameraUI
* FocusView
* GestureTextureView
* IndicatorView
* PhotoUI
* ProfessionalUI
* ShutterButton
* VideoUI
### utils
* CameraDialog  基于 DialogFragment+ AlertDialog的dialog，第一次见这种写法 
  ````
   @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getTitle());
        builder.setMessage(getMessage());
        builder.setCancelable(false);
        if (getOKButtonMsg() != null) {
            builder.setPositiveButton(getOKButtonMsg(), this);
        }
        if (getNoButtonMsg() != null) {
            builder.setNegativeButton(getNoButtonMsg(), this);
        }
        return builder.create();
    }
  ````
* CameraUtil 
    * sortCamera2Size 对于相机支持尺寸进行排序。
    * getDefaultPictureSize 获取支持的默认的图片预览大小 
    * getPreviewSizeByRatio 通过比例获取支持的预览大小 
    * getDefaultVideoSize 获取支持的视频大小 
    * getPictureSizeList 获取相机支持的图片大小列表。
    * getPreviewSizeList 获取相机支持的预览的大小 
    * getVideoSizeList 获取视频大小列表
    * getPreviewUiSize
    * getJpgRotation
    * getDisplaySize
    * getVirtualKeyHeight 获取虚拟键高度
    * getBottomBarHeight
    * getDefaultPreviewSizeIndex
    * getOutputFormat
    * hardwareLevel2Sting
    * capabilities2String
    * format2String 将ImageFormat定义的转换为人类语言。
* CoordinateTransformer 屏幕坐标和摄像头坐标相互转换。
* FileSaver  文件存储工具类
* JobExecutor 线程池 
* MediaFunc 
    * getOutputMediaFile 获取输出的视频文件，没有创建 
    * getThumb 获取封面？
  ````aidl

   public static Bitmap getThumb(Context context) {
        String selection = MediaStore.Images.Media.DATA + " like ?";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getPath() + "/" + SAVE_PATH;
        String[] selectionArgs = {path + "%"};
        Uri originalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(originalUri, null, selection, selectionArgs,
                MediaStore.Images.Media.DATE_TAKEN + " desc");
        Bitmap bitmap = null;
        if (cursor != null && cursor.moveToFirst()) {
            long thumbNailsId = cursor.getLong(cursor.getColumnIndex("_ID"));
            //generate uri
            mCurrentUri = Uri.parse("content://media/external/images/media/");
            mCurrentUri = ContentUris.withAppendedId(mCurrentUri, thumbNailsId);
            bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr,
                    thumbNailsId, MediaStore.Images.Thumbnails.MICRO_KIND, null);
        }
        cursor.close();
        return bitmap;
    }
  
  ````
* Permission 通过 ActivityCompat.requestPermissions() 申请权限
* PermissionDialog 申请权限的弹窗 
* Storage 将图片文件或者视频文件存储到Android 系统图库中 
* SupportInfoDialog 弹窗 
* XmlInflater PreferenceFragment内容的解析 
