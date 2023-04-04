# 前言

## 资料
* [android 依赖注入](https://developer.android.com/training/dependency-injection)
* [依赖注入-维基百科](https://en.wikipedia.org/wiki/Dependency_injection)
* [google hilt-android](https://developer.android.com/training/dependency-injection/hilt-android)
* [将 Hilt 和其他 Jetpack 库一起使用 ](https://developer.android.com/training/dependency-injection/hilt-jetpack)
* [dagger2 github 地址](https://github.com/google/dagger)
* [dagger2 文档](https://dagger.dev/dev-guide/)
* [hilt的使用笔记](../../Android/Android基础/Jetpack/Hilt的使用.md)
* [移动端注入神器dagger2](../../Android/Android基础/Jetpack/移动端注入神器Dagger2使用.md)
# 正文
我们知道导入一个插件的方式是：
```java
classpath 'com.google.dagger:hilt-android-gradle-plugin:2.38.1'
annotationProcessor "com.google.dagger:hilt-compiler:2.38.1"
```
为了查看源码，我们可以将这个调调通过：
````java
   implementation 'com.google.dagger:hilt-android-gradle-plugin:2.38.1'
   implementation "com.google.dagger:hilt-compiler:2.38.1"
````
导入到项目中。我们想要解析hilt的话，就需要了解到这个调调是如何使用的。 首先，这个这个调调基于dagger2,所以dagger2有点apt技术，他依旧有。
那我我们需要着重看什么呢？hilt 主要是使用了两种编译时技术，一种是dagger2的apt,一种是基于gradle plugin 插件的
## apt技术
## gradle plugin  

