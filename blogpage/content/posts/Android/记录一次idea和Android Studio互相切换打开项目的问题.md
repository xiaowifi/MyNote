
## 前言
* [参考资料：gradle](https://gradle.org/)
* [参考资料：android build gradle](https://developer.android.com/studio/releases/gradle-plugin?hl=zh-cn)
> 最近因为经常导Demo的原因，然后Android studio 对有些第三方aar的源码预览不怎么提现良好，所以经常是idea和Android studio 互相切换，
> 然后就发现，idea 经常让我升级一些gradle 的版本号，不升级就运行不起，但是我在Android studio 中就可以运行呀。
> 但是根据idea 提示的gradle 版本升级了又不一定下载下来，或者版本太高了。Android 的build tools 的版本不支持这么高的版本号。
> 于是便有了记录下 build tools 和gradle 对应版本的想法。 主要还是抄 Android build  gradle。



## 对照表 [参考资料：android build gradle](https://developer.android.com/studio/releases/gradle-plugin?hl=zh-cn)

| 插件版本      | 所需的 Gradle 版本 |
| :------------ | :----------------- |
| 1.0.0 - 1.1.3 | 2.2.1 - 2.3        |
| 1.2.0 - 1.3.1 | 2.2.1 - 2.9        |
| 1.5.0         | 2.2.1 - 2.13       |
| 2.0.0 - 2.1.2 | 2.10 - 2.13        |
| 2.1.3 - 2.2.3 | 2.14.1 - 3.5       |
| 2.3.0+        | 3.3+               |
| 3.0.0+        | 4.1+               |
| 3.1.0+        | 4.4+               |
| 3.2.0 - 3.2.1 | 4.6+               |
| 3.3.0 - 3.3.3 | 4.10.1+            |
| 3.4.0 - 3.4.3 | 5.1.1+             |
| 3.5.0 - 3.5.4 | 5.4.1+             |
| 3.6.0 - 3.6.4 | 5.6.4+             |
| 4.0.0+        | 6.1.1+             |
| 4.1.0+        | 6.5+               |
| 4.2.0+        | 6.7.1+             |

## 手动下载gradle

> * [参考资料：gradle](https://gradle.org/) <br>
> * [csdn gradle-wrapper.properties 字段含义](https://blog.csdn.net/u013553529/article/details/55011602)

> 这种情况经常出现在下载Demo的时候，然后使用本地已有的gradle 版本无法满足当前Demo的情况下，或者就懒。

首先，我们要先明确我们需要下载的文件需要手动放到什么位置。针对Android gradle 主要是存放到：gradle-wrapper.properties 文件中。
````aidl
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-4.8.1-bin.zip

````

### distributionUrl
distributionUrl是要下载的gradle的地址，使用哪个版本的gradle，就在这里修改。

gradle的3种版本：

* gradle-xx-all.zip是完整版，包含了各种二进制文件，源代码文件，和离线的文档。例如，https://services.gradle.org/distributions/gradle-3.1-all.zip

* gradle-xx-bin.zip是二进制版，只包含了二进制文件（可执行文件），没有文档和源代码。例如，https://services.gradle.org/distributions/gradle-3.1-bin.zip

* gradle-xx-src.zip是源码版，只包含了Gradle源代码，不能用来编译你的工程。例如，https://services.gradle.org/distributions/gradle-3.1-src.zip

如果只是为了编译，可以不用完整版，只需要二进制版即可，例如，gradle-3.1-bin.zip。

### 其他4个属性

* zipStoreBase和zipStorePath组合在一起，是下载的gradle.zip所存放的位置。
* zipStorePath是zipStoreBase指定的目录下的子目录。

* distributionBase和distributionPath组合在一起，是解压gradle.zip之后的文件的存放位置。
* distributionPath是distributionBase指定的目录下的子目录。

下载位置可以和解压位置不一样。

zipStoreBase和distributionBase有两种取值：GRADLE_USER_HOME和PROJECT。

其中，GRADLE_USER_HOME表示用户目录。
在windows下是%USERPROFILE%/.gradle，例如C:\Users\<user_name>\.gradle\。
在linux下是$HOME/.gradle，例如~/.gradle。

PROJECT表示工程的当前目录，即gradlew所在的目录。



### 所以

上面的配置的意思就是：

*  去 https\://services.gradle.org/distributions/gradle-4.8.1-bin.zip 下载zip

*  存放到\.gradle\wrapper\dists目录中。
  注：具体还有2级目录，即全路径为C:\Users\<user_name>\.gradle\wrapper\dists\gradle-3.1-bin\<url-hash>\，gradle-3.1-bin目录是根据下载的gradle的文件名来定的，<url-hash>目录名是根据distribution url路径字符串计算md5值得来的，

*  解压gradle-3.1-bin.zip，将解压后的文件存放到C:\Users\<user_name>\.gradle\wrapper\dists\gradle-3.1-bin\<url-hash>\中。

### 最后

* 通常来讲，手动下载的gradle.zip只有在下载失败才会去做，那么url的hash之就不需要自己写了，编辑器已经创建好文件夹了，只是文件下载失败了。如果不想等下载失败的时间，点一下下载，然后取消，文件夹也创建好了。
* 然后把下载下来没有解压的文件放到hash值目录下，解压出来。
* 删除.part文件
* 项目中直接刷新编译。会生成一个.ok 文件，讲道理，到这一步，就可以了。
