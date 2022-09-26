## 资料
* [菜鸟教程本地maven ](http://mvnbook.com/index.html)
# 正文
## 打包到本地Maven
````html
uploadArchives {
    repositories {
        mavenDeployer {
            //提交到远程服务器：
           // repository(url: "http://www.xxx.com/repos") {
            //    authentication(userName: "admin", password: "admin")
           // }
           //本地的Maven地址设置为D:/repos
            repository(url: uri('D:/repos'))
        }
    }
}
````
## 使用
````html
 repositories {
        maven {//本地Maven仓库地址
            url uri('D:/repos')
        }
    }
````
## 开发只针对当前项目的Gradle插件

前面我们讲了如何自定义gradle插件并且打包出去，可能步骤比较多。有时候，你可能并不需要打包出去，只是在这一个项目中使用而已，那么你无需打包这个过程。

只是针对当前项目开发的Gradle插件相对较简单。步骤之前所提到的很类似，只是有几点需要注意：

> 1. 新建的Module名称必须为BuildSrc
> 2. 无需resources目录

目录结构如下所示：

![针对当前项目的gradle插件目录](https://img-blog.csdn.net/20160702135323958)

其中，build.gradle内容为：

```
apply plugin: 'groovy'

dependencies {
    compile gradleApi()//gradle sdk
    compile localGroovy()//groovy sdk
}

repositories {
    jcenter()
}

```

SecondPlugin.groovy内容为：

```
package  com.hc.second

import org.gradle.api.Plugin
import org.gradle.api.Project

public class SecondPlugin implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("========================");
        System.out.println("这是第二个插件!");
        System.out.println("========================");
    }
}
 
```



在app这个Module中如何使用呢？直接在app的build.gradle下加入

> apply plugin: com.hc.second.SecondPlugin


clean一下，再make project，messages窗口信息如下：

![打印信息](https://img-blog.csdn.net/20160702135750329)

由于之前我们自定义的插件我没有在app的build.gradle中删除，所以hello gradle plugin这条信息还会