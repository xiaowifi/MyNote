
## 前言
> 当前问题用于记录flutter 常见问题汇总
## 正文
### Waiting for another flutter command to release the startup lock...
关闭Android Studio
打开flutter安装目录/bin/cache
删除lockfile文件
此时可在命令行再执行flutter相关命令，完美解决

