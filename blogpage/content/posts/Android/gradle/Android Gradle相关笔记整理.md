## 资料
* [android studio build.gradle](https://developer.android.google.cn/studio/build/dependencies) 
* [gradle 官网](https://docs.gradle.org/current/userguide/userguide.html)
* [gradle Android](https://docs.gradle.org/current/samples/sample_building_android_apps.html)  
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

### task 任务
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













