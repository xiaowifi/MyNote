# 前言
## 资料
* [maven发布插件](https://docs.gradle.org/current/userguide/publishing_maven.html#header)
* https://developer.android.com/studio/build/maven-publish-plugin
# 正文
至于为啥要将maven改变为maven-publish，是因为maven的传递方式需要编译这个项目，rootProject 中包含的所有project。
但是 maven-publish 只会编译当前的project.所以性能较好。
## maven 
````java
apply plugin: 'maven'
uploadArchives{
//    打包成一个jar    引用jar  生成这个三个信息
        repositories.mavenDeployer {
        pom.groupId = 'com.javassist'
        pom.artifactId = 'modify'
        pom.version = '1.0.0'
        repository(url: uri('../repo'))
    }
}
````
## maven-publish
````java
apply plugin: 'maven-publish'

ext.artifactID = project.name.toLowerCase()

task androidSourcesJar(type: Jar) {
    archiveClassifier.set('sources')
    from android.sourceSets.main.java.srcDirs
}

afterEvaluate {
    publishing {
        publications {
            module(MavenPublication) {
                from components.release
                groupId project.group
                artifactId "$artifactID"
                version project.version
                artifact androidSourcesJar
            }
        }
        repositories {
            maven {
                def releasesRepoUrl = 'http://192.168.254.62:8081/nexus/content/repositories/releases/'
                def snapshotsRepoUrl = 'http://192.168.254.62:8081/nexus/content/repositories/snapshots/'
                url = version.endsWith('SNAPSHOT') ? snapshotsRepoUrl : releasesRepoUrl
                credentials {
                    username = "deployment"
                    password = "deployment123"
                }
            }
        }
    }
}
````
## 实战
### 需要发版的maven中导入maven 
这个诉求很简单，我们创建一个JAVA lib 然后导入gson，然后发版。
````java
dependencies {
    implementation('com.google.code.gson:gson:2.8.5')
}
````
在APP中使用发版的maven。然后执行命令查看maven结构：
````java
gradlew app:dependencies --configuration releaseRuntimeClasspath
````
通过结果：
````java
+--- com.demo.maven1:maven1:1.0.0
|    \--- com.google.code.gson:gson:2.8.5
````
我们可以知道，发版中的maven中包含了导入的maven。
### 需要发版的maven中导入一个module
既然我们知道maven会自动带着，那么module 呢？
````java
dependencies {
    implementation project(path: ':baseLib')
    implementation('com.google.code.gson:gson:2.8.5')
}
````
当我们APP 没有导入 ':baseLib'的时候。运行的时候却报错了：
````java
Execution failed for task ':app:mergeDebugResources'.
> Could not resolve all files for configuration ':app:debugRuntimeClasspath'.
   > Could not find MavenPlayer:baseLib:unspecified.
     Searched in the following locations:
       - file:/E:/StudyDemo/MavenPlayer/repos/MavenPlayer/baseLib/unspecified/baseLib-unspecified.pom
       - https://dl.google.com/dl/android/maven2/MavenPlayer/baseLib/unspecified/baseLib-unspecified.pom
       - https://repo.maven.apache.org/maven2/MavenPlayer/baseLib/unspecified/baseLib-unspecified.pom
       - https://jcenter.bintray.com/MavenPlayer/baseLib/unspecified/baseLib-unspecified.pom
     Required by:
         project :app > com.demo.maven1:maven1:1.0.0

````
上面的意思是没有找到baseLib 这个module。那么转念一想，我缓存API是不会没有这个问题？
````java
dependencies {
    api project(path: ':baseLib')
    implementation('com.google.code.gson:gson:2.8.5')
}
````
好吧，天真的我。人家的意思是找不到这个module 和导入方式没有一点点关系。
我们直接看当前maven生成的 pom 文件：
````java
<?xml version="1.0" encoding="UTF-8"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.demo.maven1</groupId>
  <artifactId>maven1</artifactId>
  <version>1.0.0</version>
  <dependencies>
    <dependency>
      <groupId>MavenPlayer</groupId>
      <artifactId>baseLib</artifactId>
      <version>unspecified</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.5</version>
      <scope>runtime</scope>
    </dependency>
  </dependencies>
</project>

````
baseLib 的则是这个样子的。
````java
 <dependency>
      <groupId>MavenPlayer</groupId>
      <artifactId>baseLib</artifactId>
      <version>unspecified</version>
      <scope>compile</scope>
    </dependency>
````
所以说，是在maven中查询不到的。

那么应该怎么办呢？<br>
#### 方案1 APP中导入 module
其中一个办法就是，既然没有找到，APP中导入不就可以了？
````java
 implementation 'com.demo.maven1:maven1:1.0.0'
    implementation project(path: ':baseLib')
````
#### 方案2 将baseLib 发布为maven
这个配置了就好。
#### 方案3 能否将使用的module 一起打包
这种方案有一种弊端，如果多个maven 包含相同的baselib,就会出现类冲突的问题。

### maven中导入 jar 文件 
我们在maven 中直接导入文件：
````java
    implementation files('libs\\maven2-1.0.0.jar')
````
发布maven的时候，对应的pom 文件中并没有包含 maven2-1.0.0.jar。所以说，他默认是不会写的libs 中的文件的。
要想将libs 中的文件一起携带进去需要添加配置。
#### 携带lib文件发布maven
