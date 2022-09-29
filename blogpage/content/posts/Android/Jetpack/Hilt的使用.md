# 前言

Hilt 是 Android 的依赖项注入库，可减少在项目中执行手动依赖项注入的样板代码。执行[手动依赖项注入](https://developer.android.com/training/dependency-injection/manual) 要求您手动构造每个类及其依赖项，并借助容器重复使用和管理依赖项。

Hilt 通过为项目中的每个 Android 类提供容器并自动管理其生命周期，提供了一种在应用中使用 DI（依赖项注入）的标准方法。Hilt 在热门 DI 库 [Dagger](https://developer.android.com/training/dependency-injection/dagger-basics)  的基础上构建而成，因而能够受益于 Dagger 的编译时正确性、运行时性能、可伸缩性和 [Android Studio 支持](https://medium.com/androiddevelopers/dagger-navigation-support-in-android-studio-49aa5d149ec9)。如需了解详情，请参阅 [Hilt 和 Dagger](https://developer.android.com/training/dependency-injection/hilt-android#hilt-and-dagger)。

我们在使用dagger 的时候，需要写很多component，同时处理多个component注入到同一类中的情况，而hilt减少了我们对于component的使用，同时也规避了处理component注入到同一个类中的情况，在使用过程中，我只需要关心module的创建及其使用位置即可，但是这个是有一个使用前提的，那就是在注入以下的类中才不用我们自己写component：

Hilt 目前支持以下 Android 类：

- `Application`（通过使用 `@HiltAndroidApp`）
- `ViewModel`（通过使用 `@HiltViewModel`）
- `Activity`
- `Fragment`
- `View`
- `Service`
- `BroadcastReceiver`

如果需要注入到非这些类中的时候，就需要自己写component了。

## 资料

* [android 依赖注入](https://developer.android.com/training/dependency-injection)
* [依赖注入-维基百科](https://en.wikipedia.org/wiki/Dependency_injection)
* [google hilt-android](https://developer.android.com/training/dependency-injection/hilt-android)
* [将 Hilt 和其他 Jetpack 库一起使用 ](https://developer.android.com/training/dependency-injection/hilt-jetpack)

### 导包

```java
// 导入插件
classpath 'com.google.dagger:hilt-android-gradle-plugin:2.38.1'
// 使用插件
plugins {
  id 'kotlin-kapt'
  id 'dagger.hilt.android.plugin'
}
// 导入包
dependencies {
    implementation "com.google.dagger:hilt-android:2.38.1"
    kapt "com.google.dagger:hilt-compiler:2.38.1"
}
```

# 正文

## 隔离层设计

主要是防止APP 直接调用某些可能发生变化的提供者，然后通过某些手段去中转一次，用于实现使用者不关系具体实现的逻辑。

隔离层的设计的实现方案：

* 代理模式（静态代理+动态代理）
  * 比较轻量，耦合度较高
* Hilt注入
  * 使用第3方框架，比较重，耦合度高。
  * hint 一般使用在APP中，对于提供给第3方使用的内容，一般不使用hint 
* SPI机制 
  * 标准的0耦合机制，内存性能和上面两种更高 

### 静态代理

### 动态代理

## hint 的使用

 ### 导包

#### 根目录下的build.gradle 添加 classPath

````
classpath 'com.google.dagger:hilt-android-gradle-plugin:2.38.1'
````

#### module的build.gradle 添加插件

````
plugins {
    id 'dagger.hilt.android.plugin'
}
````

#### 导入hint 包和annotationProcessor

```
implementation "com.google.dagger:hilt-android:2.38.1"
annotationProcessor "com.google.dagger:hilt-compiler:2.38.1"
```

如果使用了kotlin的话，需要使用kapt 替代annotationProcessor,同时导入kapt:id 'kotlin-kapt'

### hilt初始化

hint 的使用需要在項目的Application 中添加注解：

```java
@HiltAndroidApp
public class MyApplication extends Application {
}
```

因为hilt帮我们处理了component，所以这个@HiltAndroidApp 这个注解就非常重要，比如要有，hilt需要通过这个注解帮我们去生成了对应的component 相关的代码。

### hilt 的使用

使用dagger的时候，我们知道逻辑意义上，可以注入到任何class中。在Android上，部分支持的类可以不自己写component。Hilt 目前支持以下 Android 类：

- `Application`（通过使用 `@HiltAndroidApp`）
- `ViewModel`（通过使用 `@HiltViewModel`）
- `Activity`
- `Fragment`
- `View`
- `Service`
- `BroadcastReceiver`

如果您使用 `@AndroidEntryPoint` 为某个 Android 类添加注释，则还必须为依赖于该类的 Android 类添加注释。例如，如果您为某个 fragment 添加注解，则还必须为使用该 fragment 的所有 activity 添加注解。

**注意**：在 Hilt 对 Android 类的支持方面适用以下几项例外情况：Hilt 仅支持扩展 [`ComponentActivity`](https://developer.android.com/reference/kotlin/androidx/activity/ComponentActivity) 的 activity，如 [`AppCompatActivity`](https://developer.android.com/reference/kotlin/androidx/appcompat/app/AppCompatActivity)。Hilt 仅支持扩展 `androidx.Fragment` 的 Fragment。Hilt 不支持保留的 fragment。

所以我们需要某个注解去标记生成上面那些类型的component，hilt提供的注解就是：@InstallIn 

@InstallIn 常见的值

* ActivityComponent。用于标记使用在activity上。
* ActivityRetainedComponent 
* FragmentComponent
* ServiceComponent
* ViewComponent
* ViewModelComponent
* ViewWithFragmentComponent

在dagger 的使用过程中，我们的注入class 也是在component中实现的，hilt减少了我们写component的过程，那么我们就需要提供一个标记去让dagger知道要注入的class的位置。而hilt提供的注解就是：@AndroidEntryPoint  这个注解用于标记注入的位置。标记在主要注入的class上。

#### activity 中使用

#### viewModel 中使用

#### fragment中使用

#### view 中使用

#### service 中使用

#### broadcastReceiver  

#### class 生命周期单例和全局单例

在dagger 中，我们使用@Singleton标记需要生命周期内单例，而单例的实现是注解处理器生成了对应的class实现了对应的效果，既然单例的声明周期全靠生成的代码决定，所以在hint 中对于class 类的单例和全局单例做了处理。对于class 内部单例有生命周期的class 会自动调用对应的声明周期去销毁。所以不同的生命周期的单例注解不一样。目前提供以下用于标记生命周期的注解：

* ActivityRetainedScoped
* ActivityScoped
* FragmentScoped
* ServiceScoped
* ViewModelScoped
* ViewScoped 

### hilt接口注入

我们对于某些功能进行抽离的时候，往往需要抽离出一个接口，然后提供不同的实现。而hilt 就可以直接将实现类注入进去，减少了我们使用设计模式的情景。@binds 

#### 一个接口多个实现类

