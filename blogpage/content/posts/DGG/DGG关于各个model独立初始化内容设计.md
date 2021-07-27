+++
date = "2020-10-01"
title = "DGG关于各个model独立初始化内容设计"
description = "DGG关于各个model独立初始化内容设计"
series = ["顶呱呱"]
featured = true
draft = false
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 一个项目通常会存在多个module，不同的model可能出现不同的初始化内容，为了防止各个模块相互引用或者不同内容初始化糅合到一起不便于后期维护。所以大佬提供了一种基于Java接口的不同module的初始化设计。

参考内容:
* IModuleInit 提供接口，其他模块初始化内容实现当前接口 (或许不需要单独初始化的内容可能不需要实现这个调调吧)
* ModuleLifecycleReflexs  静态类，用于存放其他模块初始化内容的实现类地址。
* BaseModuleLifecycleConfig 基于ModuleLifecycleReflexs提供实现类生成 IModuleInit对象，初始化内容。
* ModuleLifecycleConfig  继承BaseModuleLifecycleConfig，便于初始化其他公共内容。

## 调用
ModuleLifecycleConfig.getInstance().initModuleAhead(this);
## 已实现模块
* ![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210727142455.png)
## 实现
````aidl
 for (String moduleName : initModuleNames()) {
            try {
                Class<?> clazz = Class.forName(moduleName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                init.onInitAhead(application);
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
````
## 结束


