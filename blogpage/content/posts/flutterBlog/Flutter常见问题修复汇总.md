+++
date = "2021-6-10"
title = "flutter常见问题汇总"
description = "flutter常见问题汇总"
tags = [ "问题汇总"]
categories = [
    "flutter"
]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 当前问题用于记录flutter 常见问题汇总
## 正文
### Waiting for another flutter command to release the startup lock...
关闭Android Studio
打开flutter安装目录/bin/cache
删除lockfile文件
此时可在命令行再执行flutter相关命令，完美解决

