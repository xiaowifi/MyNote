# 前言
> java代码----(1)---->class---(2)----->dex----(3)---->apk
> javassist 主要是操作（2）阶段。所以不能在原工程上进行更改。
> (2)阶段 也叫transform。
所以需要在原工程以外新建一个工程。通常的操作就是在插件中生成编译时需要的工程。
## 资料
* [javassist github](https://github.com/jboss-javassist/javassist)
* [javassist demo](https://www.w3cschool.cn/article/35230124.html)
* [javassist 教程](https://www.javassist.org/tutorial/tutorial.html)
* [javassist API地址](http://www.javassist.org/html/index.html)
````html
implementation 'org.javassist:javassist:3.20.0-GA'
````
# 正文
这个调调和APT的处理机制是不一样的，APT是创建一个JAVA类。而这个调调就是修改已经编译成class文件。通过JAVAssist去修改添加代码。
比如插入一个if语句：
````groovy
 method.insertAfter("if(true){}")
````
处于JAVA编译到class的阶段，可能出现后期添加JAVA 的过程，所以在class到dex 就能够保证 修改的逻辑处于逻辑意义上的
完整代码。 
我们需要改变class文件，就需要对于编译打包的流程有一定的了解，Android 打包是通过build tools 中很多个 transform 
完成的，不同的transform 包含一个输入和一个输出，同时处理不同的逻辑。我们想要改变class文件，就需要找到
对于的transform，然后处理自己的业务，Android AGP 允许自己添加节点，这也是javassist 可以被使用的前提。
所以我们需要创建一个gradle 插件，然后拿到对接的输入文件，然后处理完成后输出。
## 创建gradle plugin 插件
gradle 是支持groovy和Kotlin的，而groovy 可以直接调用JAVA 代码。所以创建工程类型是JAVA，groovy或者Kotlin是没有多少区别的。
而且buildSrc 就是一个插件，是否需要使用Module 看需求，如果项目中包含了BuildSrc插件就不需要重新创建一个module 了，但是呢？建议使用groovy module，毕竟很多特性可以直接用。
用JAVA 或者Kotlin 就需要写很多人家帮忙写好的代码了。
[gradle 插件创建笔记](../../../gradle/plugin/创建一个插件.md)
## 在插件中创建一个transform
我们需要自定义一个transform,这个transform 属于Android build api里面的内容。所以我们需要遵循他提供的规则。
* 定义名称，这个名称是这个transform的工作空间，会在build 下的transform中创建一个这个目录。
* 定义需要处理输入的文件的类型
* 定义数据来源的类型
* 是否开启增量更新
### 输入输出关联
如果只是注册了transform。APG就会把你需要的输入类型通过输入给你，但是你没有处理输出，所以导致了apk 里面就没有需要的文件，就会安装失败。
所以必须处理输入和输出，而处理这个的函数就是 transform.
回顾上面我们定义的transform 我们限制了输入类型，来源类型，所以输出的位置也需要通过他定义是输入类型和来源类型进行判断，获得输入和输出后，我就可以把处理好的文件放到输出目录下了。
````groovy
 @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        project.android.bootClasspath.each {
            pool.appendClassPath(it.absolutePath)
        }
//       1 拿到输入
        transformInvocation.inputs.each {
//            class 1     ---> 文件夹     jar 可能 1  不可能2  N
            it.directoryInputs.each {
                def preFileName =    it.file.absolutePath
                pool.insertClassPath(preFileName)
                //       2 查询输出的文件夹    目的地
                def dest =   transformInvocation.outputProvider.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.DIRECTORY
                )

                //       3  文件copy  ---》 下一个环节
                FileUtils.copyDirectory(it.file, dest)
            }
            it.jarInputs.each {
                    def dest = transformInvocation.outputProvider.getContentLocation(it.name
                            , it.contentTypes, it.scopes, Format.JAR)
                    FileUtils.copyFile(it.file, dest)
            }
        }
    }
````
所以我们对于class的处理是在拷贝到输出目录之前处理的。
### 完整代码
````groovy
package com.javassist

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.apache.commons.io.FileUtils
import org.gradle.api.Project;

public class ModifyTransform extends Transform {
    def project
//    内存   windown   1  android 2
    def pool = ClassPool.default
//查找类
    ModifyTransform(Project project) {
        this.project = project
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        project.android.bootClasspath.each {
            pool.appendClassPath(it.absolutePath)
        }
//       1 拿到输入
        transformInvocation.inputs.each {
//            class 1     ---> 文件夹     jar 可能 1  不可能2  N
            it.directoryInputs.each {

                def preFileName =    it.file.absolutePath
                pool.insertClassPath(preFileName)

                println "========directoryInputs======== " + preFileName
                findTarget(it.file, preFileName)
//  it.file
                //       2 查询输出的文件夹    目的地
                def dest =   transformInvocation.outputProvider.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.DIRECTORY
                )

                //       3  文件copy  ---》 下一个环节
                FileUtils.copyDirectory(it.file, dest)
            }
            it.jarInputs.each {
                    def dest = transformInvocation.outputProvider.getContentLocation(it.name
                            , it.contentTypes, it.scopes, Format.JAR)
//                    去哪里
                    FileUtils.copyFile(it.file, dest)
            }
//            修改class   不是修改 jar
        }

//       2 查询输出的文件夹    目的地

//       3  文件copy  ---》 下一个环节
//        想干嘛干嘛
    }

//    fileName C:\Users\maniu\Downloads\ManiuJavaSsit\app\build\intermediates\javac\debug\classes
    private void findTarget(File dir, String fileName) {
        if (dir.isDirectory()) {
            dir.listFiles().each {
                findTarget(it, fileName)
            }
        }else {
            def filePath = dir.absolutePath
            if (filePath.endsWith(".class")) {

//                修改文件

                modify(filePath, fileName)
            }
        }

    }
    private void modify(def filePath, String fileName) {
        if (filePath.contains('R$') || filePath.contains('R.class')
                || filePath.contains("BuildConfig.class")) {
            return
        }

// 基于javassit  ----》
        def className =  filePath.replace(fileName, "").replace("\\", ".")  .replace("/", ".")

        def name = className.replace(".class", "").substring(1)
        println "========name======== " + name
//        json 文件   ----》 javabean-- 修改---》 fastjson ----》回写到  json文件
        CtClass ctClass=  pool.get(name)
        addCode(ctClass, fileName)
    }
    private void addCode(CtClass ctClass ,String fileName) {
//        捡出来
        ctClass.defrost()
        CtMethod[] methods = ctClass.getDeclaredMethods()
        for (method in methods) {

            println "method "+method.getName()+"参数个数  "+method.getParameterTypes().length
            method.insertAfter("if(true){}")
            if (method.getParameterTypes().length == 1) {
                method.insertBefore("{ System.out.println(\$1);}")
            }
            if (method.getParameterTypes().length == 2) {
                method.insertBefore("{ System.out.println(\$1); System.out.println(\$2);}")
            }
            if (method.getParameterTypes().length == 3) {
                method.insertBefore("{ System.out.println(\$1);System.out.println(\$2);System.out.println(\\\$3);}")
            }
        }

        ctClass.writeFile(fileName)
        ctClass.detach()
    }
    @Override
    String getName() {
        return "david"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }
}

````
## 注册transform 
```groovy
 project.android.registerTransform(new ModifyTransform(project))
```
java 代码的注册和Groovy注册不同。
````java
 BaseExtension ext = project.getExtensions().findByType(BaseExtension.class);
        ext.registerTransform(new MyJavaSsistTransfrom());
````

