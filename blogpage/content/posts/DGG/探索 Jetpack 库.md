+++
date = "2021-2-19"
title = "探索 Jetpack 库"
description = "探索 Jetpack 库"
series = ["顶呱呱"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
> 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 
## 正文
> 还是老规矩，先上官网 [官网](https://developer.android.google.cn/jetpack)
> ，然后就是 jetpack 支持些什么。[支持与包含](https://developer.android.google.cn/jetpack/androidx/explorer)
> ，最后是找到demo地址，然后拉demo源码 学习了。[demo地址](https://github.com/android/sunflower)
> <br> 这里就需要着重点一下设计模式了。常见的mvc.mvp,mvvm.而 jetpack 就支持了mvvm.当然也不是说，用了jetpack就不能写MVC不能写mvp了。
> 但是一个项目中最好固定一种设计模式。一套写法是最好的。而mvvm 只要注释写得好，比其他两个更好维护和阅读。
> <br> 既然是mvvm.和mvp和mvc 还是有区别的，mvp和mvc Google并没有提供对应的组件，一般都是自己封装，需要注意生命周期什么的。但是mvvm,Google 开始提供对应的组件。同时开始优化开发流程和代码可读性了。
> * ViewModel [教程地址](https://developer.android.google.cn/topic/libraries/architecture/viewmodel) 提供数据，这里面可以写的和 P层差不多，获取数据，处理数据什么的。
> * LiveData  提供数据更新观察能力，和生命周期绑定。和viewModel绑定，而P层大概就是ViewModel+liveDate的集合体。
> * databinding  主要是负责 界面渲染数据，这个可以少一层，findviewbyid。
> * 然后模块解耦，虽然这个mvvm是一个整体，但是搬砖过程中，难免会遇到 不同模块的子模块有相同的，不解耦的话，就会出现一个东西重复出现调用，虽然可以通过设计模式设置不同的server实现。也可以了解下依赖注入。
> * 最后就是数据缓存 sqlite 和room的使用了。room骚的是 他基本上是一个表一个库，所以需要注意连接池中的连接逻辑，通常只有一个数据库处于连接状态（待确认）。
### ViewModel
>  [教程地址](https://developer.android.google.cn/topic/libraries/architecture/viewmodel) 
> <br> Android 框架可以管理界面控制器（如 Activity 和 Fragment）的生命周期。Android 框架可能会决定销毁或重新创建界面控制器，以响应完全不受您控制的某些用户操作或设备事件。
> 如果系统销毁或重新创建界面控制器，则存储在其中的任何瞬态界面相关数据都会丢失。例如，应用可能会在它的某个 Activity 中包含用户列表。为配置更改重新创建 Activity 后，新 Activity 必须重新提取用户列表。对于简单的数据，Activity 可以使用 onSaveInstanceState() 方法从 onCreate() 中的捆绑包恢复其数据，但此方法仅适合可以序列化再反序列化的少量数据，而不适合数量可能较大的数据，如用户列表或位图。
> 另一个问题是，界面控制器经常需要进行可能需要一些时间才能返回的异步调用。界面控制器需要管理这些调用，并确保系统在其销毁后清理这些调用以避免潜在的内存泄漏。此项管理需要大量的维护工作，并且在为配置更改重新创建对象的情况下，会造成资源的浪费，因为对象可能需要重新发出已经发出过的调用。
> 诸如 Activity 和 Fragment 之类的界面控制器主要用于显示界面数据、对用户操作做出响应或处理操作系统通信（如权限请求）。如果要求界面控制器也负责从数据库或网络加载数据，那么会使类越发膨胀。为界面控制器分配过多的责任可能会导致单个类尝试自己处理应用的所有工作，而不是将工作委托给其他类。以这种方式为界面控制器分配过多的责任也会大大增加测试的难度。
> 从界面控制器逻辑中分离出视图数据所有权的操作更容易且更高效。

主要还是图片问题，无论是自己绘制的还是网络过来的，虽然可以有多级缓存。但是还是得自己去整一套吧。这个用得少，问题1，他缓存的数据我们应该不用管，然后正常关闭的再打开的数据，应该还是要从本地或者服务器拉去吧、
在配置更改期间会自动保留 ViewModel 对象，以便它们存储的数据立即可供下一个 Activity 或 Fragment 实例使用。这句话，感觉没懂，再配置更改期间是什么意思。
那么懂 “配置更改期间” 这个范围就很重要了。[处理配置变更](https://developer.android.google.cn/guide/topics/resources/runtime-changes)
这个里面对配置变更就有描述:<br>
> 某些设备配置可能会在运行时发生变化（例如屏幕方向、键盘可用性，以及当用户启用多窗口模式时）。发生这种变化时，Android 会重启正在运行的 Activity（先后调用 onDestroy() 和 onCreate()）。重启行为旨在通过利用与新设备配置相匹配的备用资源来自动重新加载您的应用，从而帮助它适应新配置。
那么，骚操作就来了。我们界面通常只有一套UI,特殊界面除外，那么，我正常界面强行竖屏不就好了。特殊界面，特殊处理嘛。就少了一种屏幕方向了，然后是键盘可用性（没处理过），多窗口模式（应该是和小窗差不多。这个好多手机都支持，需要适配）。这么一想，卧槽，好多，viewmodel 好东西。
#### “配置更改期间
通过查看大佬的博客。[引用博客](https://www.cnblogs.com/aiguozhe/p/3871485.html) <br>
在程序运行时，如果发生Configuration Change会导致当前的Activity被销毁并重新创建，即先调用onDestroy紧接着调用onCreate()方法。重建的目的是为了让应用程序通过自动加载可替代资源来适应新的配置。
设置Activity的android:configChanges=”orientation|keyboardHidden”时，在Android 3.2(API Level 13)之前，切屏还是会重新调用各个生命周期，不会执行onConfigurationChanged()方法。在Android 3.2之后必须在configChanges中添加screenSize才不会在切屏时重新调用各个生命周期。并执行onConfigurationChanged()方法。
Configuration 这个类描述了设备的所有配置信息，这些配置信息会影响到应用程序检索的资源。包括了用户指定的选项（locale和scaling）也包括设备本身配置(例如input modes，screen size  and  screen orientation).可以在该类里查看所有影响Configuration Change 的属性。

常见的引发Configuration Change的属性：

横竖屏切换：android:configChanges="orientation"

键盘可用性：android:configChanges="keyboardHidden"

屏幕大小变化：android:configChanges="screenSize"

语言的更改：android:configChanges="locale"
<br> [贴出一个可以更改配置的参数](https://developer.android.google.cn/reference/android/content/res/Configuration#lfields)
#### viewModel 生命周期
通过上面可以看出，他是基于activity的，所以同一个activity里面fragment获取到的应该是一样的。而且作用范围只是配置更改期间，等于说只要不打开新的activity或者关闭当前activity。viewModel 是一直存在的。当当前activity被销毁的时候就会被销毁。
* 通过fragment获取到的是一个，那么传参也可以这么使用了。避免了fragment 写方法或者使用eventBus 传参了，但是这个需要单独设计，可能并没有方法传参或者eventBus使用方便明朗。
### LiveData
这个LiveData是结合viewmodel使用的，但是杠精的我，觉得他也可以单独使用。这个和观察者模式差不多。既然是观察者，Rxjava 是不是也可以实现这种效果。主要是他更改了自动发送更新消息太骚了。爱了。具体实现还是需要看一下逻辑。
### databinding
这个主要是在layout中，减少findviewbyid的使用，是数据直接操作界面的优化方案之一，结合观察者使用，简化了代码量，但是加重了设计负担，不能无脑搬砖了。
### 解耦 
可能是工厂模式的简化版本吧，工厂模式中对于不同需求提供提供不同的server方法，而解耦的目的是，server类和方法都直接干掉，写到bean里面。
### 其他
当然jetpack提供了很多东西，不只是有mvvm.他的目的是简化开发，所以东西很多，现在主要是理论，后面再弄其他的。
## 结束
好了，理论都知道了。我发现他demo是kotlin的。所以先看一波kotlin的相关的东西。