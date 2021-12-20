显示大图核心
* 分块加载
* 内存复用 
## 实现
* 通过rect 确定存储显示范围。
* 通过bitmap options 图片信息
    * options.inJustDecodeBounds=true // 设置可以不读取到内存中获取图片宽高。用完了之后要设置为false
    * options.inMutable=true 开启内存复用。
    * options.inPreferredConfig=bitmap.config.* 设置图片的组成模式。
* 通过 gestureDetector 获取手势
* 通过Scroller 做滑动处理。
* 通过 onTouch 实现手势等信息的判断，进行滚动等。
* Matrix bitmap 进行缩放。
* 通过inputStream 设置图片 输入流。这个扩展性更高，因为要做内存复用和分块加载。
    * 获取屏幕宽高 options.outWidth/outHeight
    * 获取区域解码：BitmapRegionDecoder decoder
    * requestLayout() 重新绘制。
    * onMeasure() 计算 需显示的宽高。和缩放比例。
    * onDraw() 进行绘制
    * 内存复用 options.inBitmap=mBitmap;
    * 


## 图片压缩算法
> 鲁班压缩 
* libjpeg-tubo C库
* 哈夫曼算法--是一种变长编码算法，广泛用于数据压缩。
* Android 原生提供压缩 bitmap.compress(bitmap.compressFormat.JPEG,10,ByteArrayOutPutStream),0到10.
* bitmap 主要是做2D 图像显示。
* Android 图形处理库是skia 
* skia 基于 jpeg 
* bitmap 图片内存在native 层
* 颜色指 转化为 RGBA  ![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20211220212231.png)
* 
* 
* 
* 
* 
* 
* 
* 
* 




