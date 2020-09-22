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

- debug 版本运行不到手机上。重新编译项目或者删除主项目下 build文件（不要删除错了），还有重新导入编译项目，一般就可以运行到手机上了。
- idea 左边栏有一个 Build variants 工具（可能在其他位置）可以切马甲包配置或者release，debug等。![](https://i.loli.net/2020/09/22/SJHqoW5upjYnTPK.png)
- release 版本是无法运行debug的。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921174309.png)主要使用的工具。attach debugger to  android  process。现在打断点分为两种：

- 点打好断点直接运行到断点位置
- 直接运行debug 版本，然后在需要进入debug 位置之前打断点，开启上面工具。

个人还是比较推荐后面这种的。





### 测试代码

- [ ] Android 是提供了这个的，容我学习一下。

  

### 让测试复现

不敢，能提供截图就已经是万幸了，如果能够提供log 什么的简直是一个完美的测试。如果测试能提供数据，测试视频，log等那就是我大哥大姐了。



## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。