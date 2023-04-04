## 资料
* [android studio build.gradle](https://developer.android.google.cn/studio/build/dependencies) 
* [gradle 官网](https://docs.gradle.org/current/userguide/userguide.html)
* [gradle Android](https://docs.gradle.org/current/samples/sample_building_android_apps.html)  
* [andorid gradle-api ](https://developer.android.com/reference/tools/gradle-api)  
* [task与transform](https://juejin.cn/post/6875141808825991181)  
* [gradle transform](https://docs.gradle.org/current/userguide/artifact_transforms.html)  
* [gradle TASK](https://docs.gradle.org/current/dsl/org.gradle.api.Task.html#org.gradle.api.Task)

* 如何查看源码，使用kotlin 或者将插件包导入到项目如 compileOnly 'com.android.tools.build:gradle:4.0.0'
## 内容
* gradle 是一款基于apache的ant和maven 概念的项目自动化开源构建工具，gradle的核心是基于JAVA 实现的，可以把他当做一个轻量级的JAVA 应用程序。
    * 有类
    * 有生命周期
    * 有闭包
* gradle 使用groovy，java,kotlin 等语言编写自定义脚本，取代ant和maven 使用xml方式，简化了项目配置和灵活了配置。
* gradle 使用范围
    * 组件化
    * 插件化
    * 热修复
    * 构建系统
    * 编译时技术
* 每个项目都有build.gradle 
* settings.gradle 主要作用是做 module导包。默认是同级目录。如果是下级目录需要写相对路径。如”:lib:myDemo“
    * 对应的是org.gradle.api.initialization.Settings.java 
    * 可以获取根project
### gradle 生命周期
> 每一个module都有一个build.gradle,rootProject 就是根目录的build.gradle,project就是当前build.gradle 本身
> gradle 钩子 在 settings.java 和 project 中都包含。
> * gradle 的钩子会对当前工程及其子工程都生效。
> * project 的钩子会对当前工程生效。
> * rootProject 表示是根工程的钩子。
````aidl
// 可以获取到子工程的 生命周期。不建议用。
rootProject.getSubprojects().each {
    it.afterEvaluate {
        
    }
    it.beforeEvaluate {
        
    }
}
````

#### 钩子gradle.buildStarted()
> 这个钩子在setting.gradle 中回调不到。在build.gradle 也回调不到。
#### initialization
> gradle 支持单项目和多项目构建。gradle 通过settings.gradle 确定需要参与构建的项目并且为他生成project实例。
org.gradle.api.Project.java 
##### 执行settings.gradle
> 在settings.gradle 中写的代码方法调用会优先执行，
##### 钩子 gradle.settingsEvaluated()
````aidl
gradle.settingsEvaluated {
    println("gradle.settingsEvaluated ")
}
````
##### 钩子 gradle.projectsLoaded()
````aidl
gradle.projectsLoaded {
    println("gradle.projectsLoaded")
}
````
#### configuration
> 配置阶段，解析每个工程的build.gradle 文件，创建要执行的任务子集和确定任务之间的关系，并且对任务的做一些初始化。
> 那等于说 可以写方法 在配置阶段执行。
##### 钩子 gradle.beforeProject()
##### 钩子 project.beforeEvaluate()
> 当前在root 中不生效。
##### 执行 build.gradle 文件
> 确定任务子集和配置 task
##### 钩子 gradle.afterProject()
##### 钩子 project.afterEvaluate()
##### 钩子 gradle.projectsEvaluated()
##### 钩子 gradle.taskGraph.whenReady()
#### execution
> 运行阶段，gradle 根据配置阶段创建和配置都要执行的任务子集，执行任务。
##### 钩子 gradle.taskGraph.beforeTask()
##### task 中都actions
##### gradle.taskGraph.afterTask()
##### 钩子 gradle.buildFinish()
##### gradle 添加监听
> 因为 gradle 生命周期是整个项目，所以 能监听到project的生命周期不过分吧。
`````aidl
gradle.addBuildListener(new BuildListener() {
    @Override
    void buildStarted(Gradle gradle) {

    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsLoaded(Gradle gradle) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {

    }

    @Override
    void buildFinished(BuildResult result) {

    }
})
//     * <li>{@link org.gradle.BuildListener}
//     * <li>{@link org.gradle.api.execution.TaskExecutionGraphListener}
//     * <li>{@link org.gradle.api.ProjectEvaluationListener}
//     * <li>{@link org.gradle.api.execution.TaskExecutionListener}
//     * <li>{@link org.gradle.api.execution.TaskActionListener}
//     * <li>{@link org.gradle.api.logging.StandardOutputListener}
//     * <li>{@link org.gradle.api.tasks.testing.TestListener}
//     * <li>{@link org.gradle.api.tasks.testing.TestOutputListener}
//     * <li>{@link org.gradle.api.artifacts.DependencyResolutionListener}
gradle.addListener()
`````
### task 任务
* 定义task 任务的时候  @TaskAction 用于标记 执行task 时候执行的方法，当然可以定义多个方法，当多个方法同时包含 @TaskAction 注解的时候，无法指定其执行顺序。
* @intput 用于标记输入
* @outputFile 表示任务的输出文件
* intputs,outputs 表示默认的输入输出
* group 定义 自定义task 的group
* @optional 表示输入输出项为可选。
#### 简单的task 任务
````aidl
task taskDemo{
    // 配置代码
    doFirst {
        //task 执行的方法
    }
    doLast {
        // task 执行的方法
    }
}
````
##### task任务依赖
> dependsOn 用于标记在什么任务后执行。如果是在多个任务后都执行的话。传递数组。数组执行顺序 在没有指定依赖顺序的时候是按照名称顺序去执行的。
> 因为task DefaultTask ,DefaultTask是 AbstractTask的子类 所以task 中很多方法 也可以直接使用 比如 taskDemo.mustRunAfter(taskDemo2) 也可以指定依赖关系。
> task.finalizedBy task 用于指定某个任务执行完成后 再执行什么任务。  
> 因为task 包含优化，当输入输出没有发生变更的时候，将不会执行task,所以可以对输入输出进行设置为每次都执行。如：outputs.upToDateWhen{false}
````aidl
task taskDemo{
    // 配置代码
    doFirst {
        //task 执行的方法
    }
    doLast {
        // task 执行的方法
    }
}

task taskDemo2(dependsOn:[taskDemo]){
    // taskDemo 执行完成后 再执行taskDemo2
    doFirst {
        
    }
    doLast {
        
    }
}
````
#### 自定义task class
````aidl
class MyTask extends DefaultTask{
    MyTask() {
        group("自定义task group")
    }

    @TaskAction
    void myAction(){
        // 这里就相当于 task 需要执行的地方了，TaskAction注解 有多个就执行多个
    }
}
// 创建task 
tasks.create("mytask",MyTask){
    
}
// 这个也是创建task
task mytask(type:MyTask){
    
}
````
#### 钩子函数和task
> 根据build.gradle 的生命周期 规则。想要将一个task 依赖某个别人的task，成为别人的输入或者输出。就需要在对应的节点上挂钩子。
> 结合上面的的task 依赖会更好。通过 tasks.getByName() 在对应的生命周期以后才不会为null 
````aidl
// 比如说 这个是压缩 文件 将packageDebug task 的输出压缩成一个zip 
afterEvaluate {
    tasks.getByName("packageDebug")
    task zip(type:Zip){
        archiveName("output.zip")
        destinationDir file("${buildDir}/luoye")
        from tasks.getByName("packageDebug").outputs.files
    }
}
````
### Project
* 根目录的project 在子project中就是rootProject,project 默认就是当前build.gradle
* buildscript 是针对 project 的配置。所以这个里面的方法先执行。
````aidl
// 针对 APP module 进行配置。
project(':app'){

}
// 针对所有project 进行配置
project.allprojects {

}
//配置此项目的所有子项目。
project.subprojects {

}
// 拿到所有的子project
project.subprojects.each {

}
````
#### 属性扩展
```aidl
// 默认给project 进行属性扩展
project.ext{
    username="q"
    emal="qqqq.ccc"
}
ext.www="www"

project.ext.username
project.username

```
### 插件
#### 脚本插件
````aidl
apply from:'一个本地相对路径或者绝对路径，或者一个网络文件地址'
````
#### 定义class 
* 需要实现 Plugin<project>。通过 apply 获取到当前的project对象。通过在监听中创建task 
### 依赖管理
> gradlew:app:dependencies --configuration releaseRuntimeClasspath 查看依赖管理，当配置风味之后，似乎需要变更一下风味内容。<br>
> gradlew:app:dependencies 查看所有依赖项。<br>
> repositories <br>
> org.gradle.api.artifacts.dsl.RepositoryHandler 
* google() Google 的官方仓库
* mavenLocal() 本地的仓库
* maven(){} 从某个地址上获取maven 
* ivy{} 和maven 类似
#### 依赖项配置
* [Google Android 依赖管理](https://developer.android.google.cn/studio/build/dependencies)
> dependencies <br>
> org.gradle.api.artifacts.dsl.DependencyHandler
* configurations 可以自定义依赖项的配置。 类似于implementation 的功能
* implementation 导入到maven 不会进行传递。当前使用了某些功能，当别的module 使用的时候无法找到当前内容。当APP 大量使用AIP的时候构建效率非常低。
* api 会对依赖项进行检查，然后疯狂向下传递。
* compileOnly 不会进行打包进入apk 
* androidx.legacy:legacy-support-v4:1.0.0 (*) 这种表示依赖已经有了，将不会再重复依赖。
* com.squareup.okio:okio:1.7.0 -> 2.9.0 表示1.7.0 被2.9.0 所替代。
* jar gradle 不能排除。
* 在导入maven 设置 transitive 设置依赖项是是否传递。true 传递，FALSE 不传递依赖。
* 在导入maven 中设置 exclude 进行排除依赖。
* 在导入maven 中设置 farce true 强行指定版本。
* 在build.gradle 中配置 configuration 可以对排除项进行全局配置。
* configurations.all{ resolutionStorategy{force 强行指定某个版本}}
### android productFlavors
* compileSdkVersion 编译时候使用的版本
* minSdkVersion 最小 apk 支持版本   
* targetSdkVersion 开发的时候使用的sdk  
* applicationIdSuffix 添加后缀。
* buildToolsVersion　编译工具的版本
* manifestPlaceholders=["key":"v"]
#### ProductFlavors
> 这个必须要有一个或者多个 flavorDimensions “channel”,"维度“
* dimension 指定 flavorDimensions
* minifyEnabled  是否启用混淆 
* shrinkResources false 清理无效资源。
    * 这个是清理 没有被使用的资源文件 
* initWith 
* zipAlignEnabled 
* 
* 
* 
* 
* 
* 
### maven 发布
### gradlew 执行脚本  
* gradlew wrapper 配置版本统一管理，会生成几个文件和目录。
    * gradlew 使用gradlew 就是使用的是这个文件，如果配置了gradle 环境就直接使用gradle 了。
    * gradlew.bat 和gradlew 一样，就是使用的系统不一样。
    * gradle 目录 版本配置统一管理
* gradlew -?/-h/-help 使用帮助
* gradlew tasks 查看当前项目所有的task 任务
* gradlew --refresh-dependencies assemble 强制刷新依赖。
* gradlew cBC 等价与执行 Task cleanBuildCache (清除编译缓存)， 这种通过缩写名快速执行任务。
* gradlew :app:dependencies 查找 app 工程依赖树。
* 
* 
* 
* 
* 
* 
* 
* 
*  
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 













