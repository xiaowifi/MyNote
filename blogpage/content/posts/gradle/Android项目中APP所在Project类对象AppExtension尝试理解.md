# 前言
最近有一些业务诉求，可能需要通过打包流程标准化去控制。
所以需要获取到task。而我们编译时技术的时候，通常都需要拿到一个project对象，然后再拿到AppExtension.示例代码
````aidl
 AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
````
或者是：通过名字获取，而这个名字就是“Android”
他的来源则是：
```aidl
apply plugin: 'com.android.application'
```
只有当导入了这个plugin的时候，才会有这个对象。所以这个对象，可以拿到所有的配置，及其动态的添加内容。
## 资料
* [Gradle学习之Android-DSL AppExtension篇](https://www.jianshu.com/p/1e4ecb1899b4)
# 正文


