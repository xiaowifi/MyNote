## 资料
* [一个简单的基于ffmpeg 的封装工具](https://github.com/yangjie10930/EpMedia)
  > (很久没有维护了) 关于 基于FFmpmp的上色处理，体积小，便于用户快速操作。包含以下功能：片段，开发功能的视频，应用，图标，添加LOGO，应用程序处理，Android应用程序处理，Android应用程序处理，添加背景音乐，加速减速视频，倒放音视频。在Android上开发的基于FFmpeg的视频处理框架，简单易用，体积小，帮助用户快速实现视频处理功能。包含以下功能：编辑、裁剪、旋转、镜像……
# 正文
## module 分析
主要是在找，如何集成并且调用 ffmpeg 执行命令。
通过他的readme.md 文件。可以知道。EpVideo是他的调用程序。EpEditor是他的执行程序。最终通过FFmpegCmd.exec()执行代码。
