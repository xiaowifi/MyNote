> 一个用于读取、写入和创建 MP4 文件的 Java API
> 适用于所有基于 ISO 14496 的文件（MP4、Quicktime、DCF、PDCF...）的通用解析器和编写器
## 资料
* [gitHub 地址](https://github.com/sannies/mp4parser)
* [maven主页地址](https://search.maven.org/artifact/com.googlecode.mp4parser/isoparser)
* [主页地址](https://code.google.com/archive/p/mp4parser/)
# 正文
> 这个调调的Demo 在[k4l-video-trimmer](https://github.com/titansgroup/k4l-video-trimmer) 和[VideoTrimmer](https://github.com/AndroidDeveloperLB/VideoTrimmer) 
> 中被使用，用于裁剪视频。
## 设计
### androidtest
> 没有看懂，主要导入了‘org.mp4parser:1.0.4.2’
### isoparser
### muxer
### streaming
### examples
#### 导包
* org.mp4parser.isoparser
* org.mp4parser.muxer
* org.mp4parser.streaming
* xom.xom
* commons-codec.commons-codec
* commons-io.commons-io
* commons-lang.commons-lang
* org.eclipse.jetty.jetty-server
* commons-collections.commons-collections
#### 目录描述
##### java
* com.google.code.mp4parser.example.GetHeight 获取视频的宽高
* PrintStructure 打印文件的结构信息
* AacExample aac和H264合并成一个mp4
* Ac3Example ac3 合并成一个mp4
* BillH264Example H264和一个aac 合并成一个mp4
* DTSMuxExample dtshd 合并成一个mp4 
* Ec3Example EC3TrackImpl的使用
* H264Example H264TrackImpl的使用
* MjpegTest  感觉像是合并封面 
* MuxAacH264SMPTE 这个调调没有看懂 TtmlTrackImpl的使用 
* Avc1ToAvc3Example 
* ChangeInplaceExample
* DavidAppend 将多个视频合并在一起
* DumpAmf0TrackToPropertyFile 
* MetaDataTool 
* ReadExample 
##### resources
> 资源文件
## API调用视频裁剪
* Movie 逻辑上所有的视频源都需要转换成这个对象
* FileDataSourceViaHeapImpl 这个调调作为解析文件的载体，可以通过某些方式转换为   Movie
* MovieCreator   通过FileDataSourceViaHeapImpl对象生成 Movie
* Track 承载轨道的载体   
* CroppedTrack  裁剪轨道