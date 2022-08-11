## 资料
* [android NDK工具常用汇总.md](../android NDK工具常用汇总.md)
# 正文
## Native工程
### 在build.gradle 
Android 闭包中添加:
````java
externalNativeBuild{
  cmake{
      path file('src/main/cpp/cMakeLists.txt')
      version '3.18.1'  
  }   
}
````
path file 中的文件路径一定要正确。没有的话要创建。<br>

在defaultConfig中添加:
````java
externalNativeBuild{
    cmake{
        cppFlags ‘’   
    }    
}
````
### cMakeLists 内容描述
* cmake_minimum_required(VERSION 3.18.1) :ndk 版本号
* project(“so文件的文件名”)
* add_library() 添加源文件 
### .cpp 文件描述
* JNI是C代码，但是是C++工程，所以需要添加 extern "C"
* C语言的编译器是gcc
* C++的编译时C++
* 如果定义native 方法有返回值，但是Native 函数没有返回值，编译不会报错，但是运行会报错。
* C不支持重载，C++ 支持重载
* 方法宏定义包含两个值，default和hidden，hidden表示方法不可见。
* jnicall