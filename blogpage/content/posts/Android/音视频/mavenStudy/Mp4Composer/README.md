> https://github.com/MasayukiSuda/Mp4Composer-android
## 资料
* [gitHub 地址](https://github.com/MasayukiSuda/Mp4Composer-android)
# 正文
这个库的整体实现逻辑大概是：
* 先获取到音视频轨数据。
* 通过解码器对于视频轨进行解码，然后存放到Surface中。
* 然后整一个编码器，同时也匹配一个Surface。
* 将解码成功后的数据根据业务诉求再进行编码。
* 通过MediaMuxer将编码好的视频轨合并成一个mp4

因为视频文件的特殊性，他存在压缩，对内容进行更改的时候，需要解码成原始数据，然后再次编码成视频。常见对于视频文件进行更改的操作有:
* 屏幕旋转
* 尺寸变更
* 填充模式
* 滤镜
* 比特率变更
* 水印
* 视频拼接
* 字幕按压到视频轨中
* 翻转
* 视频mini type 的更改，如avc 变成其他的。
* 倍数播放 
* 裁剪(个人感觉裁剪好像不需要转码) 需要研究下这个库[gitHub mp4parser](https://github.com/sannies/mp4parser) 这个库就是单纯的裁剪的。

因为他提供的能力中很大一部分都需要解码后再编码的，所以他将整个流程整合到了一起。

## 解读学习
通过大致的了解，我们可以很清晰的知道，他主要是基于MediaMuxer和MediaExtractor 进行音视频的拆分与组合。所以，我们将学习的难易程度进行分级学习。
* [视频静音]((一)视频静音实现.md) 这个功能相对简单，主要是将视频中的音频轨丢弃，然后将老视频的视频轨通过IO流复制到新的视频的视频轨中。MediaMuxer负责生成mp4文件




## 问题
````aidl
{track-id=1, level=2048, mime=video/avc, frame-count=441, profile=8, language=```, color-standard=1, display-width=1920, csd-1=java.nio.HeapByteBuffer[pos=0 lim=8 cap=8], color-transfer=3, durationUs=14939866, display-height=1080, width=1920, color-range=2, rotation-degrees=90, max-input-size=275773, frame-rate=30, height=1080, csd-0=java.nio.HeapByteBuffer[pos=0 lim=20 cap=20]}
{max-bitrate=192000, sample-rate=48000, track-id=2, durationUs=14912000, mime=audio/mp4a-latm, profile=2, channel-count=2, bitrate=192000, language=```, aac-profile=2, max-input-size=577, csd-0=java.nio.HeapByteBuffer[pos=0 lim=2 cap=2]}

````

| 方法             | 描述                                                         |
| ---------------- | ------------------------------------------------------------ |
| 回转             | 影片的旋转，默认Rotation.NORMAL                              |
| 尺寸             | 电影的分辨率，默认与 src 电影的分辨率相同。如果您指定 MediaCodec 不支持的分辨率，则会出现错误。 |
| 填充模式         | 用于缩放电影边界的选项。PRESERVE_ASPECT_FIT 是适合的中心。PRESERVE_ASPECT_CROP 是中心裁剪，默认 PRESERVE_ASPECT_FIT。 FILLMODE_CUSTOM 用于裁剪视频。检查[此](https://github.com/MasayukiSuda/Mp4Composer-android/blob/master/art/fillmode_custom.gif)行为。示例源代码是[这样](https://github.com/MasayukiSuda/Mp4Composer-android/blob/master/sample/src/main/java/com/daasuu/sample/FillModeCustomActivity.java)的。 |
| 滤镜             | 此滤镜是 OpenGL 着色器，用于对视频应用效果。可以通过继承[GlFilter.java](https://github.com/MasayukiSuda/Mp4Composer-android/blob/master/mp4compose/src/main/java/com/daasuu/mp4compose/filter/GlFilter.java)来创建自定义过滤器。，默认GlFilter（无过滤器）。过滤器在[这里](https://github.com/MasayukiSuda/Mp4Composer-android/tree/master/mp4compose/src/main/java/com/daasuu/mp4compose/filter)。 |
| 视频比特率       | 设置视频码率，默认视频码率为 0.25 * 30 * outputWidth * outputHeight |
| 静音           | 在导出的视频上静音音轨。默认`mute = false`。                 |
| 修剪             | 将音频和视频轨道修剪到提供的开始和结束时间，包括在内。默认不会从头或尾修剪任何内容。 |
| 垂直翻转         | 在导出的视频上垂直翻转。默认`flipVertical = false`。         |
| 翻转水平         | 在导出的视频上水平翻转。默认`flipHorizontal = false`。       |
| 视频格式MimeType | 导出视频的视频格式的 mime 类型。默认自动。支持 HEVC、AVC、MPEG4、H263。检查[这个](https://github.com/MasayukiSuda/Mp4Composer-android/blob/master/mp4compose/src/main/java/com/daasuu/mp4compose/VideoFormatMimeType.java)。 |
| 倍数        | 设置时间刻度。默认值为 1f。应在 0.125 (-8X) 到 8.0 (8X) 范围内 |

