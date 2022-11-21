# 前言
## 资料
* [android sdk 命令行工具](https://developer.android.com/studio/command-line)

# 正文
## Android sdk 命令行工具
> 位置：android sdk/cmdline-tools/version/bin/
### apkanalyzer
用于在构建流程完成后，深入分析您的apk的组成。
### avdmanger 
用于从命令行创建和管理Android 虚拟设备
### lint 
用于扫描代码，帮助您识别和纠正代码结构质量方面的问题。
### retrace 
对于由R8 编译的应用，retrace 会解码经过混淆处理的堆栈轨迹，该堆栈轨迹会映射回你的原始源码。
### sdkmangger
用于查看，安装，更新和卸载 Android sdk的软件包 
## android sdk 构建工具 
### aapt2
解析Android 资源，为其编码索引，然后将其编译为针对Android 平台优化过的二进制格式，然后将编译后的
资源打包到单个输出中。
### apksigner
为apk 签名，并检查签名能否在给定apk 支持的所有平台版本上成功通过验证。
### zipalign 
确保所有未压缩数据等开头均相对于文件开头部分执行特定等对齐，从而优化apk 文件。
## android sdk 平台工具 
### adb 
管理Android 设备，还可以安装apk.
### etc1tool 
一种命令行实用程序，将png编码为ETC1压缩标准格式，并将ETC1解码为PNG.
### fashboot 
将平台或其他系统映象刷写到设备上。
### logcat 
可以通过ADB调试，用于查看应用和系统日志。
## Android 模拟器
### emulator
基于EQMU的设备模拟工具，可用于在实际的Android 运行时环境中调试和测试应用。
### mksdcard 
可帮助您创建与模拟器一起使用的磁盘映像，以模拟在外部存储器的情景。
# 总结