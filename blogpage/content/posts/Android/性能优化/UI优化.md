

* GPU 听令与CPU 进行图像处理
* ![image-20220506214259817](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20220506214259.png)

![image-20220506214514295](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20220506214514.png)



*  Android 每隔16毫秒就会发送一次VSYNC信号

* 如何减少时间

  * CPU 减少xml转化成对象的时间
  * 减少 GPU 对于重复内容的绘制

* 过度绘制

  * 用户看不到的区域会被绘制---布局层级太深
  * 自定义控件中onDraw 做了过多的绘制

  