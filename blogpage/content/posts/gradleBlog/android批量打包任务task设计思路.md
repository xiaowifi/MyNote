# 前言
主要是慢慢的习惯了一个逻辑，人都会犯错的，哪怕再简单的问题。如果可以通过流程去控制，那么就可以减少这种失误。
这种情况在马甲包或者多环境，多渠道上存在十分的明显。然后一个签名文件包含多个别名。鬼一样，麻了。当一个工程网络环境需要手动切换，签名需要手动切换，配置需要
手动切换到时候，就可能把简单的容错率整到最低。麻了。因为签名的切换，导致了也没法记住密码，我电脑记住密码自动整进去的密码竟然是错误的。
目前30个包左右，一次性打4个，心累。基于这种情况，便想着创建一个task去执行批量打包的任务。
## 资料
这个不需要资料，需要的太多了。
# 正文
我们知道，APP的渠道和签名都是在AppExtension中配置，我们通过project可以获取到这个对象。那么我们就可以获取到所有的ProductFlavor。同时project可以获取到task.
那么我们就有两种实现方案。
## 方案1：JAVA 调用gradle 命令行执行代码
例如：
```aidl
gradlew assembleALiRelease 
```
这个就可以直接在build 目录下生成对应的输出文件。直接assemble 则是打出来所有的包。
知道如何打一个包了，那么我们就需要知道如何执行多个task 任务。
````aidl
gradlew assembleAli assembHuawei
````
这样很单纯，是吧。所以说，这个应该是可以的。直接执行 assemb 会打所有的buildType和ProductFlavor。但是直接打所有，电脑扛不住。
那么还有一个问题，那就是如何确定输出目录位置。这个也有几种思路。
* 思路1，就放到build 目录下，打包完成，自己复制或者copy 到其他目录下。
* 思路2，看脚本命令中 应该如何指定输出目录
* 思路3，更改productFlavor 里面的输出目录及其名称。
### 具体实现
新增控制逻辑，然后再：
````aidl
 variant.getPackageApplicationProvider().get().outputDirectory = new File("../out_apk")
````
逻辑上：
````aidl
cmd /c gradlew assembleAli 
````
然后设置gradlew 所在位置。
````aidl
 Process exec = runtime.exec(script +"",null,new File("E:\\StudyDemo\\MyApplication"));
````
这个调调，会重新配置工程。 
## 方案2，JAVA 代码直接找到task,然后执行task 
网络上说的基于这个对象创建：GradleConnector，这个对象去执行task.有点懵。但是这种方案不会重新配置项目。
````aidl
 ProjectConnection connection = GradleConnector.newConnector()
                .useInstallation(project.getGradle().getGradleHomeDir())
                .forProjectDirectory(project.getRootDir())
                .connect();
// 执行task                 
connection.newBuild().forTasks("assemble").setStandardOutput(System.out).run(); 
````
useBuildDistribution函数使用默认配置。maven库需要选择等等。
````aidl
 connection = GradleConnector.newConnector()
                .useInstallation(appProject.getGradle().getGradleHomeDir())
                .forProjectDirectory(appProject.getRootDir())
                .useGradleVersion(appProject.getGradle().getGradleVersion())
                .useGradleUserHomeDir(appProject.getGradle().getGradleUserHomeDir())
                .useBuildDistribution()
                .connect();
````