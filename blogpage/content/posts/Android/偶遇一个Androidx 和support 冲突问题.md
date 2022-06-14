> 偶遇一个Androidx 和support 冲突问题

# 前言
> 近期项目使用Android x 已经快1年多了，最近突发奇想把项目中某些maven 库迁移到其他项目中。个人的设想角度上将，这个不是分分钟的事情吗？
> 毕竟我们maven 推送，依赖包管理什么的都是独立开来的，只需要开启Android studio 新建一个项目，把maven 所在module 拷贝进去，改一下settings.gradle配置。
> 然后把其他配置导入到对应的build.gradle 中，最后把测试代码拖拽到app module 中就好了。如果流畅的话，我觉得我10分钟不到就搞定了。然后？这理所当然的10分钟，我竟然要写一个blog ?
> 简直离谱，在不懂build.gradle 相关配置和运行机制面前，真的是世事无常，大肠包小肠。
## 资料
* [Android AndroidManifest应用清单概况](https://developer.android.com/guide/topics/manifest/manifest-intro)
* [合并多个AndroidManifest.xml](https://developer.android.com/studio/build/manifest-merge?hl=zh-cn)
* [AppComponentFactory](https://developer.android.com/reference/android/app/AppComponentFactory?hl=en)
# 正文
创建项目的时候，采用的是Android Studio 4.2.2。而且module 贼简单，就是基于Arouter 提供一个自定义Provider 类的接口，然后有一个默认的provider 实现类。
在原来的工程上运行完美。我只是单纯的想把他换一个位置，主要是主工程下面module 太多了影响编译速度，同时拉maven的时候如果整个项目文件过多，也不容易拉下来。
## 问题？
IDEA 提示我应该添加下面相关内容。
```aidl
Manifest merger failed : Attribute application@appComponentFactory value=(androidx.core.app.CoreComponentFactory) from [androidx.core:core:1.5.0] AndroidManifest.xml:24:18-86
	is also present at [com.android.support:support-compat:28.0.0] AndroidManifest.xml:22:18-91 value=(android.support.v4.app.CoreComponentFactory).
	Suggestion: add 'tools:replace="android:appComponentFactory"' to <application> element at AndroidManifest.xml:5:5-22:19 to override.
```
### 问题1:编辑器提示添加 tools:replace="android:appComponentFactory"
> 这个倒是很好添加。像这个样子就好。
```aidl
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.example.appconfig">
    <application
        android:name=".MyConfigApp"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        tools:replace="android:appComponentFactory"
        android:theme="@style/Theme.AppConfig"
       >
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```
但是添加成功后，却报问题 2
### 问题2:android:appComponentFactory 需要赋值
> 这个根据经验，通过百度得知 appComponentFactory可以为任意值？？？？ 那我瞎写了啊 
### 问题3:android.support.v4和core 冲突
> 错误问题如下：
```aidl
Duplicate class android.support.v4.app.INotificationSideChannel found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.app.INotificationSideChannel$Stub found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.app.INotificationSideChannel$Stub$Proxy found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.graphics.drawable.IconCompatParcelizer found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.os.IResultReceiver found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.os.IResultReceiver$Stub found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.os.IResultReceiver$Stub$Proxy found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.os.ResultReceiver found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.os.ResultReceiver$1 found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.os.ResultReceiver$MyResultReceiver found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class android.support.v4.os.ResultReceiver$MyRunnable found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class androidx.core.graphics.drawable.IconCompatParcelizer found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class androidx.core.internal.package-info found in modules core-1.5.0-runtime.jar (androidx.core:core:1.5.0) and support-compat-28.0.0-runtime.jar (com.android.support:support-compat:28.0.0)
Duplicate class androidx.versionedparcelable.CustomVersionedParcelable found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.NonParcelField found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.ParcelField found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.ParcelImpl found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.ParcelImpl$1 found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.ParcelUtils found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.VersionedParcel found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.VersionedParcel$1 found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.VersionedParcel$ParcelException found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.VersionedParcelParcel found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.VersionedParcelStream found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.VersionedParcelStream$FieldBuffer found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.VersionedParcelable found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)
Duplicate class androidx.versionedparcelable.VersionedParcelize found in modules versionedparcelable-1.1.1-runtime.jar (androidx.versionedparcelable:versionedparcelable:1.1.1) and versionedparcelable-28.0.0-runtime.jar (com.android.support:versionedparcelable:28.0.0)

```
如果简单百度或者Google过，他一定会让你将整个项目整成Androidx。问题是我gradle.properties 中 android.useAndroidX=true 确实Android x 啊。
而且整体项目都是Android X,而新开的项目也是默认Android X.所以还是配置问题是吧？
## 结论?
> 通过上面的信息，我初步认为，不是代码的问题，而是配置的问题。因为Android x 是配置到 gradle.properties中，第一步就是对比gradle.properties 文件的差异。
> 在Android Studio 4.2.2 中创建项目的gradle.properties值如下:
````aidl
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
````
> 只有两条配置？emmmmm? 这不科学？我咋记得我主项目中配置不了这些呢？主项目配置。
````aidl
android.useAndroidX=true
android.enableJetifier=true
android.enableR8=false
android.useNewApkCreator=false
````
> 我将主工程中的配置添加到 新项目的gradle.properties中。便可以编译运行了。也不需要配置 问题 1.2的解决方案。
## 知识点？
> 参考资料参考blog 头部的资料。在这个问题的整体解决思路中，大致遇到了下列几个知识点:
* xmlns:tools="http://schemas.android.com/tools"
* tools:replace="android:appComponentFactory"
* android:appComponentFactory 
* org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
* android.useAndroidX=true
* android.enableJetifier=true
* android.enableR8=false
* android.useNewApkCreator=false
> 那么下面，我们将这些知识点一一表述出来。
### xmlns:tools="http://schemas.android.com/tools"
> 这个Google 上翻译的原话，参考[合并多个AndroidManifest.xml](https://developer.android.com/studio/build/manifest-merge?hl=zh-cn) 
> APK 或 Android App Bundle 文件只能包含一个 AndroidManifest.xml 文件，但 Android Studio 项目可以包含多个清单文件，这些清单文件由主源代码集、build 变体和导入的库提供。
> 因此，在构建应用时，Gradle 构建系统会将所有清单文件合并成一个清单文件打包到应用中。<br>
> 合并规则标记是一个 XML 属性，可用于指定您对如何解决合并冲突或移除不需要的元素和属性的偏好。您可以对整个元素应用标记，也可以只对元素中的特定属性应用标记。
> 合并两个清单文件时，合并工具会在优先级较高的清单文件中查找这些标记。
> 所有标记都属于 Android tools 命名空间，因此您必须先在 <manifest> 元素中声明此命名空间。

所以这个声明出现的前提就是需要有多个AndroidManifest.xml 需要合并。那么问题问题1提示的解决方案是需要存在的。为啥声明也可以合并清单文件？
### tools:replace="android:appComponentFactory"
> 既然tools 是表示 清单文件合并的标记。那么replace 则是标记中的某个关键字。<br>
> tools:replace="attr, ..."
> 将优先级较低的清单中的指定属性替换为此清单中的属性。换句话说，始终保留优先级较高的清单中的值。

低优先级清单：
````aidl
<activity android:name="com.example.ActivityOne"
    android:theme="@oldtheme"
    android:exported="false"
    android:windowSoftInputMode="stateUnchanged">
````
高优先级清单：
````aidl
<activity android:name="com.example.ActivityOne"
    android:theme="@newtheme"
    android:exported="true"
    android:screenOrientation="portrait"
    tools:replace="android:theme,android:exported">
````
合并后的清单结果：
````aidl
<activity android:name="com.example.ActivityOne"
    android:theme="@newtheme"
    android:exported="true"
    android:screenOrientation="portrait"
    android:windowSoftInputMode="stateUnchanged">
````
所以问题1其实想要的是通过清单文件优先级控制，将support强行指定为Android，而这个指定则是通过后面配置的android:appComponentFactory 去指定的？
### android:appComponentFactory 
> 通过上面几个知识点，我们可以知道，AppComponentFactory 这个值是能否设置将替换的关键。所以这个值不能瞎写啊，结合上面一开始的提示，我将值变更为
````aidl
 android:appComponentFactory="androidx.core.app.CoreComponentFactory"
````
或者 
````aidl
  android:appComponentFactory="android.support.v4.app.CoreComponentFactory"
````
还是提示 问题3。emmmm？ 所以，我这个值还是错误的。要想知道这个值到底应该怎么写似乎需要了解 android:appComponentFactory到底是干啥的。[AppComponentFactory](https://developer.android.com/reference/android/app/AppComponentFactory?hl=en)
直接翻译过来感觉看不懂。
### org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
### android.useAndroidX=true
### android.enableJetifier=true
### android.enableR8=false
### android.useNewApkCreator=false