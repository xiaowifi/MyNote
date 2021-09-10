+++
date = "2020-10-01"
title = "JAVA反射基础（2）"
description = "JAVA反射基础（2）"

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
###  为啥JAVA 要设计 class与object 
 * 如果没有class，每个对象都会存在类信息，类信息属于同类型信息。
 * 如果没有object，节省了内存，会导致类信息不安全，改一个类就会改变其他对象。
 * 类信息和object为了防止关联，解决上面问题，方法区存放类信息，根据class生成的对象，放到堆区。
 * 方法区是JAVA独有的，其他语言都是代码段
 * 反射速度低于new 对象速度。
 * 虚拟机 定义的方法区和堆区 便于GC
 * JAVA 虚拟机是字节码为基础单位，Android 是dex指令 
 * JAVA-class-dex-方法的dex指令
 * 方法区和堆区存放到主存（内存条指向的内存）中。
 * CPU调用高速缓存（栈区）（3级缓存，），由高速缓存调用主存。
 * 单例对象需要使用volatile，用于解决单例在多线程中的处理。
## 结束


