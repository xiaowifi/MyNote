> 一个用于读取、写入和创建 MP4 文件的 Java API
> 适用于所有基于 ISO 14496 的文件（MP4、Quicktime、DCF、PDCF...）的通用解析器和编写器
## 资料
* [gitHub mp4parser](https://github.com/sannies/mp4parser)
* [maven主页地址](https://search.maven.org/artifact/com.googlecode.mp4parser/isoparser)
* [主页地址](https://code.google.com/archive/p/mp4parser/)
* [examples gradle 格式化包装](https://gitee.com/lalalaxiaowifi/java-mp4-parser-demo)
# 正文
> 这个调调的Demo 在[k4l-video-trimmer](https://github.com/titansgroup/k4l-video-trimmer) 和[VideoTrimmer](https://github.com/AndroidDeveloperLB/VideoTrimmer) 
> 中被使用，用于裁剪视频。
## 导包
````aidl
    implementation 'org.mp4parser:isoparser:1.9.56'
    implementation 'org.mp4parser:muxer:1.9.56'
    implementation 'org.mp4parser:streaming:1.9.56'
````
## API 
* Movie 逻辑上所有的视频源都需要转换成这个对象
* FileDataSourceViaHeapImpl 这个调调作为解析文件的载体，可以通过某些方式转换为   Movie
* MovieCreator   通过FileDataSourceViaHeapImpl对象生成 Movie
### Track
> Track 是所有轨道的父类。很多功能性行轨道继承于AbstractTrack。然而WrappingTrack 则是继承于Track 
* Track 承载轨道的载体   
* CroppedTrack  裁剪轨道 低版本没有这个调调
* ClippedTrack 这个也是裁剪高版本才有
* AppendTrack 多个轨道进行拼接，没有试不同轨道是否能够拼接，同时没有试不同的大小的都视频轨道进行拼接，
* Mp4TrackImpl 这个Demo 是合并m4s的，但是报错了，排期看
* AACTrackImpl demo 上是将指定的aac 文件转换成这个调调。
* H264TrackImpl demo 上直接加载H264 
* AC3TrackImpl 一种音频格式 
* AbstractH26XTrack H264TrackImpl和H263TrackImpl,H265TrackImpl的父类
* Amf0Track 
* DTSTrackImpl 
* EC3TrackImpl
* MP3TrackImpl
* ReplaceSampleTrack
* TextTrackImpl
* CencDecryptingTrackImpl
* OneJpegPerIframe 
* TtmlTrackImpl
* WebVttTrack
### 其他api
#### Container 
> 可能包含其他箱子的所有ISO箱子的接口。
## 知识点
###  m4s
> 什么是一 .m4s 文件？
> M4S 文件是使用 MPEG-DASH 视频流技术流式传输的视频片段。它包含表示视频片段的二进制数据。作为视频第一段的 M4S 文件还包含初始化数据，允许媒体播放器识别并开始播放视频。
> MPEG-DASH 是一种自适应比特率流技术，它允许用户使用HTTP在 Internet 上流式传输高质量视频。使用 MPEG-DASH 流式传输的视频被分成多个片段，这些片段以各种不同的比特率提供。通过将视频分成多个片段并以不同的比特率提供这些片段，Web 客户端可以流畅地流式传输长视频，而不会停止或重新缓冲。
> 当用户下载使用 MPEG-DASH 流式传输的视频时，该视频将保存为一系列 M4S 文件。每个 M4S 文件都包含流式视频的特定片段。例如，使用 youtube-dl 下载流式 YouTube 视频的用户可能会看到保存为一系列 M4S 文件的视频。
> 注意： M4S 文件通常不包含音频数据。
### ac3 一种音频格式 
### Amf0 
### RandomAccessFile 
[RandomAccessFile的使用](../../../../java/io/RandomAccessFile的使用.md)
### FileChannel 
[FileChannel的使用](../../../../java/io/FileChannel的使用.md)