# 前言
> java代码----(1)---->class---(2)----->dex----(3)---->apk
> javassist 主要是操作（2）阶段。所以不能在原工程上进行更改。
> (2)阶段 也叫transform。
所以需要在原工程以外新建一个工程。通常的操作就是在插件中生成编译时需要的工程。
## 资料
* [javassist github](https://github.com/jboss-javassist/javassist)
* [javassist demo](https://www.w3cschool.cn/article/35230124.html)
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
[gradle 插件创建笔记](../../../gradle/创建一个插件.md)
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
## 注册transform 
```groovy
 project.android.registerTransform(new ModifyTransform(project))
```


