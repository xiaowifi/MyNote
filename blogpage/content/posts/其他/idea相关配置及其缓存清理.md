+++
date = "2021-01-06"
title = "idea相关配置及其缓存清理"
description = "idea相关配置及其缓存清理"
tags = [ "idea"]
categories = [
    "技术类"
]
featured = true
+++
> 主要针对mac   idea.vmoptions 进行设置

# 配置
通过idea 会根据电脑 自动设置一个 缓存之类下设置。

### 默认（灰色标识）

JetBrains 提供的默认设置：

```
-Xms128m
-Xmx750m
-XX:MaxPermSize=350m
-XX:ReservedCodeCacheSize=240m
-XX:+UseCompressedOops
```

### Big（大）（红色标识）

给 Xmx 配 4096MB， ReservedCodeCacheSize 设置 1024MB，这已经是相当多的内存了：

```
-Xms1024m
-Xmx4096m
-XX:ReservedCodeCacheSize=1024m
-XX:+UseCompressedOops
```

### Balanced(平衡的)（蓝色标识）

Xmx 和 Xms 都分配 2GB ，这是相当平衡的内存消耗：

```
-Xms2g
-Xmx2g
-XX:ReservedCodeCacheSize=1024m
-XX:+UseCompressedOops
```

### Sophisticated（复杂的）（橘色标识）

和上面一样， Xmx 和 Xms 都分配2GB，但是给 GC 和内存管理指定不同的垃圾回收器和许多不同的标志：

```
-server
-Xms2g-Xmx2g
-XX:NewRatio=3-Xss16m
-XX:+UseConcMarkSweepGC
-XX:+CMSParallelRemarkEnabled
-XX:ConcGCThreads=4
-XX:ReservedCodeCacheSize=240m
-XX:+AlwaysPreTouch
-XX:+TieredCompilation
-XX:+UseCompressedOops
-XX:SoftRefLRUPolicyMSPerMB=50
-Dsun.io.useCanonCaches=false
-Djava.net.preferIPv4Stack=true
-Djsse.enableSNIExtension=false
-ea
```
### idea.vmoptions
比如Android的idea.vmoptions这个文件，一般都会在根目录。如果根目录没有。
> 状态栏->help->edit custom vm option... 就可以打开本地idea的idea.vmoptions

# 清理缓存或者完全卸载
如果只是电脑运行idea 感觉卡顿，或者git进行代码比对的时候卡断，直接增加上面的内存就好。每个项目都可以独立自己清理缓存。
## 完成卸载
cd Users/xxx/Library/ 
上面的xxx对应你的用户名，然后输入 
```
rm -rf Logs/IntelliJIdeaxxx/     
rm -rf Preferences/IntelliJIdeaxxx/   这个是插件
rm -rf Application\ Support/IntelliJIdeaxxx/ 
rm -rf Caches/IntelliJIdeaxxx 这个应该缓存吧
```
这个可能和包的版本还是有关系的，比如说2020.1 idea就被移动到JetBrains 包下面。
上面的对应xxx对应不同的版本号，注意开头是 IntelliJIdea就行
把～/下的.idea/也删掉

### 其他
mac 下好多东西都是通过隐藏文件的形式防止用户误删除，所以也可以直接通过找到文件夹然后删除的，当然通过命令行更加快捷。
>  ⌘⇧.(Command + Shift + .) 此快捷键可以控制隐藏文件的显示。
> 上面要使用的东西，一般在 资源库（Library）中。
### 清除缓存
我通常是删除 Caches 下的关于 idea的文件就好。log一般比较少就20多m。我的caches 一般都是2个多G. 