# 前言
## 资料
* [GradleConnector API](https://docs.gradle.org/current/javadoc/org/gradle/tooling/GradleConnector.html)
* [gradle及其第3方工具](https://xy2401.com/local-docs/java.zh/gradle-6.0.1/third_party_integration.html)
* [Java GradleConnector类使用实例](http://www.manongjc.com/detail/36-uzficuyhjwyqtke.html)
# 正文
## 通过project并且执行一个task 
````aidl
 ProjectConnection connection = GradleConnector.newConnector()
                .useInstallation(project.getGradle().getGradleHomeDir())
                .forProjectDirectory(project.getRootDir())
                .connect();
// 执行task                 
connection.newBuild().forTasks("assemble").setStandardOutput(System.out).run(); 
````

