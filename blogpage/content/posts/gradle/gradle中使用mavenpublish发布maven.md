# 前言
## 资料
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