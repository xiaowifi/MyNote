## 前言
前面我们了解了如何通过groovy DSL转换为KTS，我也在尝试的证明可以看到源码和有代码提示对于入门的重要性。2022年11月12日，我发现最新的idea 有gradle的代码提示，点击也可以看到源码。学习Gradle还是建议整一个最新版本的IDEA。 
那么我们这次主要就是通过一些简单的gradle 工程去尝试理解gradle工程。

# 正文
我们可以直接打开[gradle 官网](https://docs.gradle.org/current/userguide/userguide.html)，官网直接包含了我们需要学习的所有gradle 基础。
当然Android 中使用gradle 还需要另外一个网站[gradle Android](https://docs.gradle.org/current/samples/sample_building_android_apps.html)和[andorid gradle-api ](https://developer.android.com/reference/tools/gradle-api)。要完全理解他们是一个大工程，而业务开发中往往是不要先全部学习的，就和我们从事Android一样，第一份工作的时候往往不是什么都会，那时候可能只会写一些业务，找一些第三方的maven等等，同时这也是这个系列的目的，不是全部要会，我们只需要了解基础然后根据自身需要再去扩展学习。

闲话说了这么久，让我们回到正题上，gradle到底为了我们做了什么？

我们回顾一下自身的诉求，我们编辑的任何代码或者文件，我们想要把它编译成可以被执行的文件，比如apk,比如我们写的Java 文件转换为class，然后再执行。当初上大学的时候，是这么学习Java的：

* 老师先写一个Java 类。
* 然后通过环境变量中配置的javahome在cmd 中使用 Javac 把java文件转换为class
* 然后在cmd 中通过全路径把转换成功的class运行起来。

当我看到控制台打印出刚刚老师写的hello word的时候，有震惊，有放弃，这太复杂了，我这脑子毕业了估计还是工地搬砖吧。后面使用了编辑器以后这种放弃的想法才慢慢的减少，毕竟谁不喜欢写完代码点击运行后可以看到结果的那种满足感呢？

而grade 就是一种构建脚本，它将一系列的检查、编译流程和打包流程串联在一起了，我们只需要点一下运行，要不编译运行成功，要不就直接失败。

## 简单的Java项目

我们先通过idea创建一个使用gralde构建的Java 项目。如果点开project的话，你就会发现，idea会优先创建一个叫build.gradle 的文件，但是创建进度条还没有到底部，当创建完成后，就会多了很多文件。

![image-20221112124438095](Gradle%E5%85%A5%E9%97%A8(%E4%BA%8C)%E5%B0%9D%E8%AF%95%E7%90%86%E8%A7%A3gralde%E7%BC%96%E8%AF%91%E9%A1%B9%E7%9B%AE.assets/image-20221112124438095.png)

同时项目配置中也设置了代码的源码位置：

![image-20221112124631999](Gradle%E5%85%A5%E9%97%A8(%E4%BA%8C)%E5%B0%9D%E8%AF%95%E7%90%86%E8%A7%A3gralde%E7%BC%96%E8%AF%91%E9%A1%B9%E7%9B%AE.assets/image-20221112124631999.png)

编译文件的输出位置：

![image-20221112124724946](Gradle%E5%85%A5%E9%97%A8(%E4%BA%8C)%E5%B0%9D%E8%AF%95%E7%90%86%E8%A7%A3gralde%E7%BC%96%E8%AF%91%E9%A1%B9%E7%9B%AE.assets/image-20221112124724946.png)

这也就说明 build.gradle这个文件是整套编译流程的关键所在。

那么我们尝试去了解一下这个简单Java 工程的build.gradle

```
plugins {
    id 'java'
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

这个文件的内容很简单，主要是导入了Java plugin，然后设置了maven 源地址，然后导入了 junit.那么我们就可以尝试删除一些我们不需要的内容。

```
plugins {
    id 'java'
}
```

只有上面代码的时候。Java demo依旧可以运行的。

![image-20221112125351390](Gradle%E5%85%A5%E9%97%A8(%E4%BA%8C)%E5%B0%9D%E8%AF%95%E7%90%86%E8%A7%A3gralde%E7%BC%96%E8%AF%91%E9%A1%B9%E7%9B%AE.assets/image-20221112125351390.png)

当我们移除掉Java plugin的时候，hello 文件就会提示无法运行。那么是否可以佐证一个事实，gralde 构建系统的识别是通过plugin去完成的，而plugin则是构建的关键。

当我们点击dependencies的时候，跳转到源码。可以发现。他其实是 org.gradle.api.Project的一个函数。

![image-20221112150536025](Gradle%E5%85%A5%E9%97%A8(%E4%BA%8C)%E5%B0%9D%E8%AF%95%E7%90%86%E8%A7%A3gralde%E7%BC%96%E8%AF%91%E9%A1%B9%E7%9B%AE.assets/image-20221112150536025.png)

```kotlin
public interface Project extends java.lang.Comparable<org.gradle.api.Project>, org.gradle.api.plugins.ExtensionAware, org.gradle.api.plugins.PluginAware {}
```

点击plugins的时候 

![image-20221112150831612](Gradle%E5%85%A5%E9%97%A8(%E4%BA%8C)%E5%B0%9D%E8%AF%95%E7%90%86%E8%A7%A3gralde%E7%BC%96%E8%AF%91%E9%A1%B9%E7%9B%AE.assets/image-20221112150831612.png)

因为project继承了PluginAware，所以plugin也是添加到project中的。这也说明为啥 apply plugin:'java' 也是可以的。

但是kts的 gradle则是KotlinBuildScript:ProjectDelegate。ProjectDelegate继承于Project。

## 回归到Android 工程

我们发现Android新创建的工程中在根目录有一个build.gradle、setting.gradle 、gradle.properties。

既然可以直接看源码，我就直接点进源码去看。

### settings.gradle 

这个是settings.gradle 对应的类。

```
org.gradle.api.initialization.Settings
public interface Settings extends org.gradle.api.plugins.PluginAware, org.gradle.api.plugins.ExtensionAware {}
```

通常而已，我们创建Android 项目的时候，一开始创建的gradle文件就包含TA了。那我再看默认的内容：

```
rootProject.name = "GradleDemo"
include(":app")
```

不同的gradle版本中代码可能不一样，最新的Android studio中 settings.gradle有其他的配置。我们结合代码就可以发现，include 其实是一个类似于相对位置的东西。如果我APP目录上面还有一个目录呢？就可以写成

```
include 'demos:demo1'
```

Demos是一个目录结构。那么settings.gralde 帮助我们做了什么吗？

![image-20221112180544378](Gradle%E5%85%A5%E9%97%A8(%E4%BA%8C)%E5%B0%9D%E8%AF%95%E7%90%86%E8%A7%A3gralde%E7%BC%96%E8%AF%91%E9%A1%B9%E7%9B%AE.assets/image-20221112180544378.png)

看到这张图了吗，我们上面也有一张这样的图。gradle 就是通过这个文件将我们需要编译的项目添加到他的构建项目中去的，哪怕你的项目没有一丝丝的关联，所以没有必要参与编译的项目就没有必要添加进来。

### 根目录下的build.gradle 

可以肯定的是这个一定是project，因为自己创建空的工程的时候，手动添加settings.gradle的时候写成了setting.gradle,然后发现他竟然是project，怀疑了下人生，后面发现单词打错了。

直接上代码：

```
buildscript {
    repositories {
        maven {//本地Maven仓库地址
            url uri('/Users/nuoye/AndroidStudioProjects/GradleDemo/repo')
        }
        google()
        mavenCentral()

    }
    dependencies {
        classpath "com.android.tools.build:gradle:4.2.2"
        
    }
}

allprojects {
    repositories {
        maven {//本地Maven仓库地址
            url uri('/Users/nuoye/AndroidStudioProjects/GradleDemo/repo')
        }
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon

    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

有过自定义ext的同学就知道，我们ext一般要写到buildscript 里面，写到其他地方在导入classpath的时候会报找不到，这个其实是和这个project的运行周期有关，这个运行周期下一次分享，这次我们只是单纯的了解这些闭包的含义。

#### buildscript

这个相对来说是最先执行的，同时如果你要添加一个classpth，那么他的maven库就得写到这个闭包下的repositories 里面。否则就会报classpath内容拉取不到。

dependencies 主要作用是导入我们gradle运行需要的代码，比如我们Android的 build-tools。因为我们使用的

```
plugins {
    id 'com.android.application'
}
android{

}
```

这些都是我们build-tools里面的内容。

#### allprojects

这个就对应着我们的module的配置了，repositories 中定义着那些我们module中需要导入的maven仓库地址。当然也可以添加其他配置。目前来说，编译时技术被用的越来越普遍，所以通常而言，两个都要写。

#### clean task 

这个调调是一个清除项目编译缓存的一个task任务。

```
task clean(type: Delete) {
    delete rootProject.buildDir
}
```

delete 删除的是一个目录，所以也可以指向自己希望指向的目录。

### gradle.properties

这个调调熟悉配置的同学就比较清楚。properties 是一种键值存储的配置文件，他的获取也是有生命周期限制的，但是在settings.gradle 和buildscript 中均可获取到。所以如果这也是一种ext的思路。当然了这个值整个项目都可以拿的到，仔细回想以下，ext 写了也可以拿到，只是没有这么简单。

我们可以这么定义一些自己需要的值。

```
luoyeAdd=true
luoye=luoye
luoyeInt=1
```

然后使用的时候可以转换成需要的类型，也可以不转换，看诉求。他是键值存储的，所以不能写赋值。

比如 :

````
a=5
b=a
````

我们拿到的b就会是字符串a,而不是5.

### APP 目录的build.gradle 

结合上面的知识点，我们知道，需要导入一个插件，让gradle清楚我们需要用什么去编译我们的代码。

```
plugins {
    id 'com.android.application'
}
```

所以这个调调是必需的。然后就是Android 闭包了的内容了。

# 总结

我们发现Java 默认的工程并没有根目录的下的build.gradle，同时也没有clear。那么我们可以自己写一个吗？

答案是肯定的，但是添加的时候却提示：

 Cannot add task 'clean' as a task with that name already exists.

说clean已经存在了，可以换一个名字不是吗？

```
task cl(type: Delete) {
    delete project.buildDir
}
```

反正都是删除文件，我们想删除哪里就删除哪里，甚至你可以指向自己的代码目录【一键坐牢】。

还有一个问题，当我们导入的插件只想在某一个module中使用，可以吗？这个好像不行，无论根目录下的build.gradle 还是module的build.gradle 都是project，但是buildscript 是导入gradle执行需要的代码，你导入的plugin 必需得存在于可以运行的gradle中，好像不支持动态加载。