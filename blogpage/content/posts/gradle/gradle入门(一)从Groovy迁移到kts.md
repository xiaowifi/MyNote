# 前言
> kts是指Kotlin脚本，这是gradle 在构建配置文件中使用的一种Kotlin语言形式，kotlin 脚本是可以从命令行运行的Kotlin代码。
> kotlin DSL 主要是指Android gradle 插件kotlin DSL ，有时候也指底层的gradle kotlin DSL。用Kotlin 编写的gradle build 文件使用
> .gradle.kts 作为文件的扩展名。
## 资料
* [kotlin_dsl入门](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
* [kotlin_dsl API](https://gradle.github.io/kotlin-dsl-docs/api/)  
* [groovy_dsl](https://docs.gradle.org/current/userguide/groovy_build_script_primer.html)
* [groovy_dsl参考](https://docs.gradle.org/current/dsl/index.html)
* [从 Groovy 迁移到 Kotlin](https://docs.gradle.org/current/userguide/migrating_from_groovy_to_kotlin_dsl.html)
# 正文 
大致修改步骤:
* 创建同名不同后缀的gradle 文件 
* 删除掉Groovy版本的文件或者拷贝到其他位置。
* 编写Kotlin版本的代码 
* 同步 
## 简单修改
### settings.gradle 
这个是项目最开始识别的文件，同时也是最简单的文件。
* 项目根目录下创建一个settings.gradle.kts 文件
* 复制Groovy版本的代码到kts上
* 删除Groovy版本的文件
* 基于kts修改文件
````kotlin
rootProject.name = "StartDebug"
include(":app")
````
重要一点，就是基于Kotlin语法。只有两行代码。还是有明显的区别的：
````kotlin
rootProject.name = "StartDebug"
````
这个代码调用的是rootProject.setName() 函数。引导组件也变成了函数的调用。
### 项目的build.gradle
这个是相对复杂的，因为涉及到导入插件，设置Android 闭包属性。导入第三方库，编译配置等等。因为插件是根目录下的build.gradle 配置的。所以说，建议优先改这个，要不然根目录下的build.gradle 出问题了，会提示这里面的插件找不到。
人都麻了。
* 基于build.gradle 复制一个，修改为：build.gradle.kts
* 将kts 中的Groovy代码注释掉。(也可以不注释，直接运行，他会提示报错的位置，然后基于报错的位置进行修改就行)
* 修改代码
````kotlin
plugins {
    id("com.android.application")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")
    defaultConfig {
        applicationId="com.nuoye.myrouter"
        setMinSdkVersion(16)
        setTargetSdkVersion(30)
        versionCode =1
        versionName ="1.0"

        testInstrumentationRunner="androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release"){
            setMinifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility =JavaVersion.VERSION_1_8
        targetCompatibility =JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("com.google.android.material:material:1.2.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation(project(":bindview"))
    testImplementation ("junit:junit:4.+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
}
````
通过上面可运行代码和Groovy版本进行对比。我们发现buildType 改动较大。其他都是基于Kotlin语法进行更改。
### 根目录的build.gradle 
这个相对而言较为地复制一点，因为他里面的脚本代码较多。
* 基于build.gradle 复制一个，修改为：build.gradle.kts
* 将kts 中的Groovy代码注释掉。(也可以不注释，直接运行，他会提示报错的位置，然后基于报错的位置进行修改就行)
* 修改代码
````kotlin

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

tasks{
    register("clean",Delete::class){
        setDelete(buildDir)
    }
}
````
上面是修改完成后的代码，通过上面的代码和原来Groovy代码进行对比。我们其实只是修改了几个位置：
* classpath 导入插件的方式，通过函数调用了，而且Kotlin的导入也和其他时候不同，不需要设置包名了。
* maven 的也是调用了函数和闭包，和Groovy也明显不同。
* 最大的差异在 clean 任务，在kts 中需要通过上述代码注册进去。
## 其他函数
### ext的定义和获取
在根目录下:
```kotlin
ext{
    set("luoye","nuoye")
    set("luoye1","nuoye")
    set("luoye2","nuoye")
}
```
获取：
````kotlin
rootProject.ext.get("luoye")
````
### 定义产品风味
````kotlin
flavorDimensionList.apply {
        add("channel")
        add("cpu")
    }
    productFlavors {
        create("baidu") {
            dimension="channel"
        }
        create ("huawei"){
            dimension="cpu"
        }
    }
````
### 引入外部文件 
// todo 
