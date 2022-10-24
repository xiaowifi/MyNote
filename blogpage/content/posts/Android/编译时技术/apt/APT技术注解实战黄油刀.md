
# 前言
> APT即为Annotation Processing Tool，它是javac的一个工具，中文意思为编译时注解处理器。APT可以用来在编译时扫描和处理注解。通过APT可以获取到注解和被注解对象的相关信息，在拿到这些信息后我们可以根据需求来自动的生成一些代码，省去了手动编写。
> 注解 APT技术，就是编译时技术，通过注解的方式，在编译的时候生成对应的想要的模板代码，用于便于开发。划重点(只能创建模板类，创建)
>或者在某些情景中限制用户输入。增加框架的兼容性。
> [java 8中移出apt相关](http://openjdk.java.net/jeps/117)
>  新版中使用 插件化注解处理(Pluggable Annotation Processing)APIJSR 269 
## 资料
* [auto.service apt服务](https://github.com/google/auto/tree/master/service)
* [javapoet java文件生成帮助类](https://github.com/square/javapoet)
# 正文
## 创建对应的module
我们知道注解是包含位置和生命周期的。而在注解处理器的开发过程中，需要申明当前注解处理器需要处理什么注解。
为了在注解处理器和代码调用上均可访问到相同的注解处理器。我们需要创建几个module 
### 注解module
这个module 很单纯，主要是提供注解。所以这个只需要是是一个JAVA lib 工程即可。
### 注解处理器的module
注册一个注解处理器，然后通过注解去生成代码。所以这个也只是一个JAVA lib 工程。
#### 导包
````java
    annotationProcessor 'com.google.auto.service:auto-service:1.0-rc4'
    compileOnly 'com.google.auto.service:auto-service:1.0-rc4'
        implementation "com.squareup:javapoet:1.13.0"
````
#### 日志打印
因为他是处于编译时，所以Android 的log是无法使用的，如何打印呢？
````java

````
### 使用 
在需要使用的地方使用。
### 导包
````java
    implementation project(path: ':bindView')
    annotationProcessor project(path: ':bindViewAnnotation')
````
## 结束


