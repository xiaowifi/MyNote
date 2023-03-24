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
### 添加注解
我们很多需求是需要在类上函数上变量上添加注解的。这就和编译时技术挂钩了。
#### 无参数注解

简单的注释很容易：

```
MethodSpec toString = MethodSpec.methodBuilder("toString")
    .addAnnotation(Override.class)
    .returns(String.class)
    .addModifiers(Modifier.PUBLIC)
    .addStatement("return $S", "Hoverboard")
    .build();
```

它使用`@Override`注释生成此方法：

```
  @Override
  public String toString() {
    return "Hoverboard";
  }
```

#### 带参数注解 

用于`AnnotationSpec.builder()`设置注释的属性：

```
MethodSpec logRecord = MethodSpec.methodBuilder("recordEvent")
    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
    .addAnnotation(AnnotationSpec.builder(Headers.class)
        .addMember("accept", "$S", "application/json; charset=utf-8")
        .addMember("userAgent", "$S", "Square Cash")
        .build())
    .addParameter(LogRecord.class, "logRecord")
    .returns(LogReceipt.class)
    .build();
```

`accept`它使用和属性生成此注释`userAgent`：

```
@Headers(
    accept = "application/json; charset=utf-8",
    userAgent = "Square Cash"
)
LogReceipt recordEvent(LogRecord logRecord);
```

#### 注解加注解 

当您喜欢时，注释值本身可以是注释。用于`$L`嵌入式注释：

```
MethodSpec logRecord = MethodSpec.methodBuilder("recordEvent")
    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
    .addAnnotation(AnnotationSpec.builder(HeaderList.class)
        .addMember("value", "$L", AnnotationSpec.builder(Header.class)
            .addMember("name", "$S", "Accept")
            .addMember("value", "$S", "application/json; charset=utf-8")
            .build())
        .addMember("value", "$L", AnnotationSpec.builder(Header.class)
            .addMember("name", "$S", "User-Agent")
            .addMember("value", "$S", "Square Cash")
            .build())
        .build())
    .addParameter(LogRecord.class, "logRecord")
    .returns(LogReceipt.class)
    .build();
```

哪个生成这个：

```
@HeaderList({
    @Header(name = "Accept", value = "application/json; charset=utf-8"),
    @Header(name = "User-Agent", value = "Square Cash")
})
LogReceipt recordEvent(LogRecord logRecord);
```

请注意，您可以使用`addMember()`相同的属性名称多次调用以填充该属性的值列表。

## 定义变量

与参数一样，可以使用构建器或使用方便的辅助方法来创建字段：

```
FieldSpec android = FieldSpec.builder(String.class, "android")
    .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
    .build();

TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
    .addModifiers(Modifier.PUBLIC)
    .addField(android)
    .addField(String.class, "robot", Modifier.PRIVATE, Modifier.FINAL)
    .build();
```

其中产生：

```
public class HelloWorld {
  private final String android;

  private final String robot;
}
```

`Builder`当字段具有 Javadoc、注释或字段初始值设定项时，扩展形式是必需的。字段初始值设定项使用[`String.format()`](https://developer.android.com/reference/java/util/Formatter.html)与上述代码块相同的 -like 语法：

```
FieldSpec android = FieldSpec.builder(String.class, "android")
    .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
    .initializer("$S + $L", "Lollipop v.", 5.0d)
    .build();
```

其中产生：

```
private final String android = "Lollipop v." + 5.0;
```



