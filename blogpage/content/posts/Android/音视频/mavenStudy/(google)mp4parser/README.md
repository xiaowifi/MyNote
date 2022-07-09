> 一个用于读取、写入和创建 MP4 文件的 Java API
> 适用于所有基于 ISO 14496 的文件（MP4、Quicktime、DCF、PDCF...）的通用解析器和编写器
## 资料
* [gitHub 地址](https://github.com/sannies/mp4parser)
* [maven主页地址](https://search.maven.org/artifact/com.googlecode.mp4parser/isoparser)
* [主页地址](https://code.google.com/archive/p/mp4parser/)
# 正文
> 这个调调的Demo 在[k4l-video-trimmer](https://github.com/titansgroup/k4l-video-trimmer) 和[VideoTrimmer](https://github.com/AndroidDeveloperLB/VideoTrimmer) 
> 中被使用，用于裁剪视频。
## API调用视频裁剪
* Movie 逻辑上所有的视频源都需要转换成这个对象
* FileDataSourceViaHeapImpl 这个调调作为解析文件的载体，可以通过某些方式转换为   Movie
* MovieCreator   通过FileDataSourceViaHeapImpl对象生成 Movie
* Track 承载轨道的载体   
* CroppedTrack  裁剪轨道 