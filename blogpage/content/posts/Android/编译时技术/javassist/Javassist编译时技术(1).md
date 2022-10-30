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
这个调调和APT的处理机制是不一样的，APT是创建一个JAVA类。而这个调调就是修改已经编译成class文件。
处于JAVA编译到class的阶段，可能出现后期添加JAVA 的过程，所以在class到dex 就能够保证 修改的逻辑处于逻辑意义上的
完整代码。 
我们需要改变class文件，就需要对于编译打包的流程有一定的了解，Android 打包是通过build tools 中很多个 transform 
完成的，不同的transform 包含一个输入和一个输出，同时处理不同的逻辑。我们想要改变class文件，就需要找到
对于的transform，然后处理自己的业务，Android AGP 允许自己添加节点，这也是javassist 可以被使用的前提。
所以我们需要创建一个gradle 插件，然后拿到对接的输入文件，然后处理完成后输出。
