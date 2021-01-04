+++
date = "2021-01-04"
title = "关于JAVA中使用测试"
description = "关于JAVA中使用测试"
tags = [ "junit"]
categories = [
    "技术类"
]
featured = true
+++
# 前言
> 最近认真工作(摸鱼)的时候，突然想起来之前立的诸多flag，其中就有
````aidl
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
````
这个调调还是蛮熟悉的，虽然没有怎么用过，每次创建应用的时候都会默认添加，虽然版本不一样。最近看熟悉Rxjava,然后JAVA版本的Demo就是 在test包下。
test包在src 下面。

# 正文
上面的那个是Android的，但是Rxjava的却不一样。
* [JUnit 4 文档地址](https://junit.org/junit4/) 
* [junit 4github 地址](https://github.com/junit-team/junit4) Java的面向程序员的测试框架。
* [mockito github 地址](https://github.com/mockito/mockito) 最受欢迎的用Java编写的用于单元测试的Mocking框架
* [testng ](https://testng.org/doc/) TestNG是一个受JUnit和NUnit启发的测试框架，但引入了一些使其更强大且更易于使用的新功能
* [guava ](https://github.com/google/guava) 