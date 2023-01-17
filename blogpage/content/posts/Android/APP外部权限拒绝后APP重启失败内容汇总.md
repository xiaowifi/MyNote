# 

# 目前掌握的信息

APP在进入设置界面拒绝权限后。返回APP，组件功能异常。

## 正常逻辑 

### APP正常启动日志

````
E/LogUtils:: e: onTerminate
E/LogUtils:: e: attachBaseContext
E/LogUtils:: e: onCreate
E/LogUtils:TowActivity  getFlags: e: 270532608
E/LogUtils:MainActivity  getFlags: e: 0
````

## APP 跳转设置后拒绝权限后的日志

```
E/LogUtils:: e: onTerminate
E/LogUtils:: e: attachBaseContext
E/LogUtils:: e: onCreate
E/LogUtils:: e: MainActivity 启动了，开始获取参数
E/LogUtils:MainActivity: e: android:viewHierarchyState--------Bundle[mParcelledData.dataSize=680]
E/LogUtils:MainActivity: e: androidx.lifecycle.BundlableSavedStateRegistry.key--------Bundle[EMPTY_PARCEL]
E/LogUtils:MainActivity: e: android:lastAutofillId--------1073741823
E/LogUtils:MainActivity: e: android:fragments--------android.app.FragmentManagerState@a8ddd76
E/LogUtils:: e: MainActivity 获取启动参数结束
E/LogUtils:MainActivity  getFlags: e: 0
```

###  返回上一个界面

````
E/LogUtils:: e: TowActivity启动了，开始获取参数
E/LogUtils:: e: android:viewHierarchyState--------Bundle[mParcelledData.dataSize=604]
E/LogUtils:: e: androidx.lifecycle.BundlableSavedStateRegistry.key--------Bundle[EMPTY_PARCEL]
E/LogUtils:: e: android:lastAutofillId--------1073741823
E/LogUtils:: e: android:fragments--------android.app.FragmentManagerState@f862a74
E/LogUtils:: e: TowActivity获取启动参数结束
E/LogUtils:TowActivity  getFlags: e: 268435456
````

### 再次进入

````
E/LogUtils:MainActivity  getFlags: e: 0
````

## 资料分析

* 通过flags 可以知道。当APP重新启动的时候。flag 有值。

* ```
  android.os.Process.myPid()。进程id也会不一样。所以这个也可以作为判断的依据。
  ```

* 当APP重启后，回退栈中的所有activity 都会走onCreate,并且flag有值。切不为0.

* 当activity 正常启动。bundle中如果没有传参就为Null。

* 可以通过bundle获取到是否是应用重启的字段。比如lastautofillid和BundlableSavedStateRegistry 

* 可以结合flag的值去处理，因为当APP 处于后台的时候，回收一些内容的时候，也需要处理这种缓存。

* Application的生命周期会重新执行。但是因为隐私合规相关问题导致很多组件的初始化需要从这个位置迁移到其他位置。

# 优化方向

目前会导致APP崩溃的问题，一般分为两种，一种是fragment或者或者activity及其本身参数问题导致的崩溃。还有一种就是组件化内容的内容未初始化。

目前的解决方案也分为两个板块：

* 判断进程、flag、缓存等组合逻变更后，重启APP。
  * 这个就需要中途去注入代码拦截掉，onCreate 函数的执行。通常采用编译编译时代码注入。这个和美团的热修复逻辑差不多。只是比他更简单。
* 通过更严谨的代码逻辑去处理这个调调。

# 详细设计

基于这个诉求，我们打算将两种方案同时执行。采用编译时技术注入代码块。生成class等等。编译时插件包含以下功能：

* 允许设置不注入代码的activity全类名
* 获取APP代码中的activity。在onCreate 函数中插入 if 判断。
* 提供判断是否是拦截逻辑的判断类。

