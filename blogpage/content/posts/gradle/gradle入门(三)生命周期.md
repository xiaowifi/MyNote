# 前言

## 资料
* [gradle 了解构建的生命周期](https://docs.gradle.org/current/userguide/build_lifecycle.html)
# 正文 
gradle构建脚本 大致分为3个阶段。我们理解知晓生命周期，对于我们自定义的任务，插件的执行，参数的获取等等都是一些底层的逻辑。
比如ext参数的定义与获取、gradle.properties参数的获取、自定义task插入到某个task之后等。
## 生命周期
### 初始化
Gradle 支持单项目和多项目构建。在初始化阶段，Gradle 确定哪些项目将参与构建，并为每个项目创建一个Project实例。
### 配置
在此阶段配置项目对象。执行作为构建一部分的所有项目的构建脚本。
### 执行
Gradle 确定在配置阶段创建和配置的要执行的任务子集。该子集由传递给gradle命令和当前目录的任务名称参数确定。Gradle 然后执行每个选定的任务。