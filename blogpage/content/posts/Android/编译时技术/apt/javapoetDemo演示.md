## 资料
* [javapoet java文件生成帮助类](https://github.com/square/javapoet)
* [javapoet详解](https://blog.csdn.net/qq_34681580/article/details/121483450)
# 正文
## 创建一个java文件
````groovy
 TypeSpec helloWorld = TypeSpec.classBuilder(simpleName+"JavaPoetBinding")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .build();
        JavaFile.builder(packageName, helloWorld).build().writeTo(filer);
````
## 创建一个函数
````groovy

````