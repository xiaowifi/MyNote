# 前言

Hilt 是 Android 的依赖项注入库，可减少在项目中执行手动依赖项注入的样板代码。执行[手动依赖项注入](https://developer.android.com/training/dependency-injection/manual) 要求您手动构造每个类及其依赖项，并借助容器重复使用和管理依赖项。

Hilt 通过为项目中的每个 Android 类提供容器并自动管理其生命周期，提供了一种在应用中使用 DI（依赖项注入）的标准方法。Hilt 在热门 DI 库 [Dagger](https://developer.android.com/training/dependency-injection/dagger-basics)  的基础上构建而成，因而能够受益于 Dagger 的编译时正确性、运行时性能、可伸缩性和 [Android Studio 支持](https://medium.com/androiddevelopers/dagger-navigation-support-in-android-studio-49aa5d149ec9)。如需了解详情，请参阅 [Hilt 和 Dagger](https://developer.android.com/training/dependency-injection/hilt-android#hilt-and-dagger)。

本指南介绍了 Hilt 的基本概念及其生成的容器，，还演示了如何开始在现有应用中使用 Hilt。

## 资料

* [android 依赖注入](https://developer.android.com/training/dependency-injection)
* [依赖注入-维基百科](https://en.wikipedia.org/wiki/Dependency_injection)
* [google hilt-android](https://developer.android.com/training/dependency-injection/hilt-android)
* [将 Hilt 和其他 Jetpack 库一起使用 ](https://developer.android.com/training/dependency-injection/hilt-jetpack)

### 导包

```java
// 导入插件
classpath 'com.google.dagger:hilt-android-gradle-plugin:2.38.1'
// 使用插件
plugins {
  id 'kotlin-kapt'
  id 'dagger.hilt.android.plugin'
}
// 导入包
dependencies {
    implementation "com.google.dagger:hilt-android:2.38.1"
    kapt "com.google.dagger:hilt-compiler:2.38.1"
}
```

# 正文

## 隔离层设计

主要是防止APP 直接调用某些可能发生变化的提供者，然后通过某些手段去中转一次，用于实现使用者不关系具体实现的逻辑。

隔离层的设计的实现方案：

* 代理模式（静态代理+动态代理）
  * 比较轻量，耦合度较高
* Hilt注入
  * 使用第3方框架，比较重，耦合度高。
  * hint 一般使用在APP中，对于提供给第3方使用的内容，一般不使用hint 
* SPI机制 
  * 标准的0耦合机制，内存性能和上面两种更高 

### 静态代理

### 动态代理

## hint 的使用

 
