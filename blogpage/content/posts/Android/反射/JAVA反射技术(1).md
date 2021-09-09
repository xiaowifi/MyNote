+++
date = "2020-10-01"
title = "JAVA反射基础（1）"
description = "JAVA反射基础（1）"

categories = [
    "android基础"
]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 反射是通过class 进行反射的。通过反射技术可以拿到对应的class，创建对应对象或者
> 通过hook技术 调用某些方法。
> 反射是java的查看、检测、修改自身的一种行为。
> 在编译阶段，编译器将我们编写的java文件编译成.class文件。而在运行期，jvm又将.class文件通过类加载器ClassLoader加载一个类对应的Class对象到内存当中。通过修改Class对象，达到我们查看、检测、修改自身的行为。
# 正文
* 获取class 对象的3种方式
    * Class.forName("包名.类名")
    * new User().getClass();
    * User.class;
## 结束


