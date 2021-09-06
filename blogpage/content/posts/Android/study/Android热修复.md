+++
date = "2020-10-01"
title = "Android热修复"
description = "Android热修复"

categories = [
    "Android进阶"
]
featured = false
draft = true 
+++
## 前言
> 热修复 基于hook技术。基于类加载机制，classLoader 加载。
>
> 补丁包其实就是一个dex 或者包含dex的jar包，需要通过dexClassLoader加载，而加载一个elements数组。插件化和热修复的原理类似，都是动态加载classLoader。
>
> 
### 目前支持的热修复解决方案

![image-20210903203531299](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/image-20210903203531299.png)

## 结束

