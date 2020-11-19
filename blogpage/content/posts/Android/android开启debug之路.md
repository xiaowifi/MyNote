+++
date = "2020-10-01"
title = "Android开启Debug"
description = "Android开启Debug"
tags = [ "android","debug"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

电脑不是太好。现在用的也仅仅是mac  air 1.8 GHz 双核Intel Core i5 8 GB 1600 MHz DDR3。电脑情况不是太好，需要经常清理缓存啊，运行代码量稍微大一点的项目就感觉慢。打算明年换一个好一点的。所以我本人是比较倾向于插件化和aar的使用的，fragment 使用比重也很高，毕竟只要写好了模板，debug功能还是比activity 简单些。

说回正题。Android debug 模式也不多，常用的一般分为：

- 打印log
- 打断点
- 测试代码
- 让测试复现bug 等 

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

## 正文

### 打印log

​	这种情况，一般是检查参数或者接口的时候，如果电脑不怎么行，打log 电脑也不怎么卡，打断点可能就卡了。

但是呢，string 虽说够长，但是log 的打印是有长度限制的。万一log体长度超出了怎么办？只能代码截取分log 打印了。像这种网络上还是蛮多的。

### 打断点

本篇我们也就主要是介绍下打断点。可能出现的问题。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923110129.png)

先介绍下图标1是run（运行，如果手机上没有安装当前app 这个应该是三角形）.2是debug,3是attach debugger to  android  process。

- debug 版本运行不到手机上。重新编译项目或者删除主项目下 build文件（不要删除错了），还有重新导入编译项目，一般就可以运行到手机上了。

- idea 左边栏有一个 Build variants 工具（可能在其他位置）可以切马甲包配置或者release，debug等。![](https://i.loli.net/2020/09/22/SJHqoW5upjYnTPK.png)

  同一个APP 下版本应该是一致的。切成同一个版本就好了。

- release 版本是无法运行debug的。 如果使用release 版本debug 会提示:

  > 	Error running 'sqliteroom'
  > 			Cannot debug application from module sqliteroom on device huawei-lld_al00-MKJDU17C08001071.
  > 			This application does not have the debuggable attribute enabled in its manifest.
  > 			If you have manually set it in the manifest, then remove it and let the IDE automatically assign it.
  > 			If you are using Gradle, make sure that your current variant is debuggable.

  所以切到debug版本就好。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921174309.png)主要使用的工具。attach debugger to  android  process。现在打断点分为两种：

- 点打好断点直接运行到断点位置
- 直接运行debug 版本，然后在需要进入debug 位置之前打断点，开启上面工具。

个人还是比较推荐后面这种的。

#### 直接打断点运行

比如我写一个for 循环，像这个样子。

```
private void debug() {
        int k=0;
        for (int i=0;i<100;i++){
           k++;
           k--;
           k=k+1;
           k=k-1;
           k=5;
        }
    }
```

然后我直接代码上打断点。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923100311.png)

---

通过debug 直接运行。![](https://i.loli.net/2020/09/22/vZwCp7H6e4cbqDf.png)

当运行到断点的代码块的时候就会进入断点。可以在底部的工具栏debug 中找到类似下面的图片。

![](https://i.loli.net/2020/09/22/l12U4tsxvEPJoSq.png)

下面我们对上面的按钮进行简单的介绍：用鼠标悬浮到按钮上面会提示英文的，我就再重复下。

1. step over 在上面的代码中，它是逐行运行的。
2.  step into   这个也是逐行运行的。
3.  Force step into  这个也是逐行运行的。
4.  Step out  运行到下一个断点位置。
5.  Run to cursor  直接运行到下一次断点位置。
6.   打断点代码对象内容等。
7.  resume program   直接运行到断点位置。
8.  停止断点

因为只是一个简单的for 循环，没有涉及到其他对象。上面的就很粗暴了。很多详细的东西就没法描述了，这些功能看起来差不多，但是在多对象调用中还是有很大的区别的，这个还是自己体会比较好(<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923103450.png" style="zoom:50%;" />)。

##### 中途打断点

比如说，我刚刚通过debug 运行了已经打好断点的程序，我想继续打断点是否可以。是可以的。

那我不打断点，直接运行debug 可以吗？也可以的，只是需要在运行到需要打断点的代码前，打好断点就行。

---

**总结:等于说，只要运行模式为debug,且运行版本为debug,只要在运行代码之前打断点都可以进入断点**

---



#### 通过正常运行打断点

通过上面的我们可以知道，release版本是无法运行debug的。那么当我们的版为release通过Run可以进行断点吗？运行成功后点击 **attach debugger to  android  process**

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923105652.png)

通过这张图说明是不行的，如果可以进入断点，那么这个地方应该有包名。那我们切成debug版本，直接运行，当APP启动后，运行到需要打断点之前。**点击attach debugger to  android  process**

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923110454.png)



就会直接打开debug底部工具，这个时候就和直接打断点运行一样了。没有多少区别。主要是区别在于一个是一致处于debug 状态直到关闭debug ,一种是需要的时候开启debug直到debug结束。这个和电脑情况有关，我的开启debug明显感觉到idea 有点卡断掉帧的感觉。

#### 其他配置release

有的时候，我们是在app下的build.gradle 中配置 signingConfigs

```
signingConfigs {
    release {
        storeFile file('jks文件路径')
        storePassword 'storeFile密码'
        keyAlias 'key0'
        keyPassword 'keyAlias 密码'
    }
}
```

在buildTypes 的release中增加:

```
signingConfig signingConfigs.release
```

有一个问题哦，这个配置和JAVA语言不一样，应该是一种脚本，所以signingConfigs 配置应该放在buildTypes配置之前，否则它找不到signingConfigs。

完整的：

```
signingConfigs {
    release {
        storeFile file('jks文件路径')
        storePassword 'storeFile密码'
        keyAlias 'key0'
        keyPassword 'keyAlias 密码'
    }
}
buildTypes {
    release {
        minifyEnabled false
        signingConfig signingConfigs.release
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

这个应该包含在Android 的配置中。

[贴一个大佬写的很全的Android断点博客](https://www.jianshu.com/p/e31e38dc4eb5)

### 测试代码

- [ ] Android 是提供了这个的，容我学习一下。这个要等一段时间才能更新。

  

### 让测试复现

不敢，能提供截图就已经是万幸了，如果能够提供log 什么的简直是一个完美的测试。如果测试能提供数据，测试视频，log等那就是我大哥大姐了。



## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923112428.png)

今天也可以是元气满满的一天哦。

