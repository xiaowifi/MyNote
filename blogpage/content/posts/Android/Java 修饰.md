+++
date = "2020-10-01"
title = "java部分修饰符"
description = "java部分修饰符"
tags = [ "java","修饰符"]
categories = [
    "技术类"
]
series = ["Java基础"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

明明刚刚在写另外一篇博客的，然后准备资料的时候发现了一些懵逼的东西。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png" style="zoom:25%;" />

这个明显触及到了我的知识点盲区。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921150627.png" style="zoom:33%;" />主要是有一种很乱的感觉，没错是我菜，见识短浅啊。

直接上教程[runoob Java  教程](https://www.runoob.com/java/java-tutorial.html)。本篇主要介绍两个修饰符 final，volatile。当然了[runoob java  修饰符](https://www.runoob.com/java/java-modifier-types.html) 这个教程把修饰符都讲完了。为啥写，主要是缺点笔记<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/064F9061-FFED-438A-8749-54415D223028%E7%9A%84%E5%89%AF%E6%9C%AC2.png" style="zoom:50%;" />。

还有是为了认真工作嘛<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/990C3AA4-F9B4-40EF-A7C7-066228D585E1.png" style="zoom:33%;" />。

如果领导路过不打招呼又在摸鱼影响不好，反正都是在认真工作的样子<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E4%B8%80%E7%AF%87%E5%8D%9A%E5%AE%A2%E5%86%99%E4%B8%80%E5%A4%A9.png" style="zoom: 25%;" />。。



### 那么我们就开始罗列知识点:（具体使用请参考 上面教程）

- **final 变量：**

  final 表示"最后的、最终的"含义，变量一旦赋值后，不能被重新赋值。被 final 修饰的实例变量必须显式指定初始值。

  final 修饰符通常和 static 修饰符一起使用来创建类常量。

- **final 方法** 

  父类中的 final 方法可以被子类继承，但是不能被子类重写。

  声明 final 方法的主要目的是防止该方法的内容被修改。

- **final 类**

  final 类不能被继承，没有类能够继承 final 类的任何特性。

- **volatile 修饰符**

  volatile 修饰的成员变量在每次被线程访问时，都强制从共享内存中重新读取该成员变量的值。而且，当成员变量发生变化时，会强制线程将变化值回写到共享内存。这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 。

## 正文

<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png" style="zoom:33%;" /> 我去，上面总结完了，现在正文写啥？

先说**final** 吧，修饰常量，Android 可以定义在xml 中 通过Context的getResources() 获取string或者int 值。常量是存储在内存中的，那么和Android 从xml 中获取到底谁的性能更加优秀？

然后是在单例中使用，Android 中使用单例的机会也不是太多，大多数和生命周期绑定使用，只有哪些初始化成本比较高，使用较为频繁的功能才可能考虑单例。比如数据库的db对象和SharedPreferences.都是创建成本高，而且对象方法加锁执行，可能导致线程阻塞，如果是异步处理**volatile** 从逻辑上讲更好啊，

final 修饰方法，这个主要是用于功能设计的底层逻辑编写。

final 修饰类，类不能被继承，核心功能类设计吧，通常和包限制共同使用。

volatile修饰变量，主要用于多线程，比如说上面的db对象和SharedPreferences ，db 对象有用final修饰的也有volatile 修饰的，而SharedPreferences 通常使用 final 修饰。

<span  style="color: #5bdaed; ">比个小心心❤️</span> 

