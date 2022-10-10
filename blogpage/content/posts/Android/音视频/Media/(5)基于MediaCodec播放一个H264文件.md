## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)
* [google MediaCodec ](https://developer.android.com/reference/android/media/MediaCodec)
# 正文
通过前面的代码，我们知道可以通过 MediaExtractor读取到视频的某一帧，然后解码出来渲染到屏幕上。而MediaExtractor无法解析一个单独的H264文件。
那我们应该如何播放一个单纯的H264文件呢？这个就需要了解H264文件的结构了。<br>
整体思路是:
* 通过流将一个H264文件逐步或者完整的读取到内存中。以byte数组存储
* 通过H264文件格式，拆分出一帧的数据。
* 将这一帧数据丢给MediaCodec 解码，然后渲染到屏幕上。

##

## 总结
