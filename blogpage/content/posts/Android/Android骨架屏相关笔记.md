> Android 骨架屏 在实现上基本上都是 View 层做动画实现。当数据回来之后，会移除骨架屏幕，同时关闭动画。

[facebook view动画](https://github.com/facebook/shimmer-android)
这个动画做骨架屏幕非常合适。导包。
````aidl
 implementation 'com.facebook.shimmer:shimmer:0.5.0'
````
### ShimmerFrameLayout
 这个是必须存在的根布局。提供下列能力。
* 是否显示骨架屏动画
* 开启骨架屏动画
* 关闭骨架屏动画
* 是否处于动画中
### Shimmer
动画的属性存储类。主要提供两个 Builder
* AlphaHighlightBuilder 
* ColorHighlightBuilder 

基础属性：
* 形状 shape 线性和形状。
* 方向 direction 
    * 左到右
    * 上到下 
    * 右到左
    * 下到上 
* fixedWidth  设置固定宽度 
* fixedHeight 固定高度 
* widthRatio 宽比例 
* heightRatio 高比例 
* intensity 动画强度，越大强度越强 
* dropoff 变换速度 
* tilt 倾斜角度 
* alpha 透明度 0到1 
* setClipToChildren 是否显示到子view 
* setAutoStart 动画是否自动启动 
* setRepeatCount 设置重复频率 默认-1 
* setRepeatMode 重复方式 
* setRepeatDelay 重复等待时间
* setDuration 动画时间 

### ShimmerDrawable
动画的真正实现类 