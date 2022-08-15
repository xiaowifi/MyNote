## 资料
* [ffmpeg-kit](https://github.com/tanersener/ffmpeg-kit)
  > 说的是移动端 ffmpeg 统一的解决方案
* [ffmpeg-kit-test](https://github.com/tanersener/ffmpeg-kit-test/tree/main/android)
  > ffmpeg-kit 的Demo
  
# 正文
> 主要是对于这个调调进行描述。Android的代码在[android](https://github.com/tanersener/ffmpeg-kit/tree/main/android) 目录下。
调用 ffmpeg的方法是：FFmpegKit下的方法。
## ffmpeg-kit项目分析
目标：找到他是如何执行ffmpeg 命令的？
一致条件：FFmpegKit