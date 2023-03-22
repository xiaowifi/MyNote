## 资料
* [javapoet java文件生成帮助类](https://github.com/square/javapoet)
* [javapoet详解](https://blog.csdn.net/qq_34681580/article/details/121483450)
# 正文
## 官方简单Demo
````aidl
 MethodSpec.Builder main = MethodSpec.methodBuilder("main");
        MethodSpec methodSpec = main.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(methodSpec)
                .build();
        JavaFile build = JavaFile.builder("com.luoye.code", helloWorld)
                .build();
        build.writeTo(new File("D:\\"));
````
这个Demo是在D盘的根目录下面，创建一个HelloWorld.java 文件。

![image-20230322101354958](assets/image-20230322101354958.png)

文件内容是：

````
package com.luoye.code;

import java.lang.String;
import java.lang.System;

public final class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello, JavaPoet!");
  }
}

````



### 解析
通过上面的结果我们尝试分析，如果手敲一遍之后，可以大致分析出整个Demo 分为3个部分。都是建造者模式去生成对象。

#### MethodSpec 

这个调调控制函数，比如函数的入参，返参，函数体等等。函数上能控制的这玩意都能控制。

#### TypeSpec

这个是class的相关信息，比如class的名称，属性等等。设置class中的函数等。

#### JavaFile

这个决定了包名，然后输出的文件的路径，他会自动将包名解析成文件夹，所以只需要是module的根目录就行。

## 进阶解析

通过上面的Demo，我们对javapoet 有了一个大致的了解。但是我们业务诉求上可能就不是一个单纯的写一个helloWord，比如需要注解，比如接口，比如静态常量，构造函数等等。