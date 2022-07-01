## 资料

* [AndroidManifest 文件简述](https://developer.android.com/guide/topics/manifest/manifest-intro)
* [AndroidManifest Avtivity 节点](https://developer.android.com/guide/topics/manifest/activity-element)  
* [CSDN:android:configChanges属性解析](https://blog.csdn.net/weixin_42600398/article/details/122525304)
* [viewModel Google](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=zh_cn)

# 正文
> 最近在搬砖过程中，偶遇了一个崩溃问题，这个问题说简单也简单，说复杂也复杂。先简单的描述一下问题。<br>
> 最近我封装的播放器在某个小米手机上横竖屏切换会崩溃。emmmm？ 崩溃的问题是空指针，emmmmm？这特么不科学。这就涉及到了界面搭建方式了。
> 我们项目使用的是MVVM。直白一点就是一个MutableLiveData 粘性数据，而界面上嵌套了一个fragment，这设计很合理。<br> 然而不合理的地方在于，我们往fragment传递参数的时候。
> 是通过fragment的方法传递的，至于为啥没有通过viewModel 共享activity中的参数，当初写的时候emmmmmm。viewModel 的生命周期和activity的生命周期绑定在一起的，这个有一个前提，就是
> 如果activity未发生配置更改的情况下，viewModel的生命周期和activity的生命周期是绑定在一起的。emmmmm？ 我们知道当配置发生更改了之后，activity默认会重新创建，但是viewModel 不会被销毁重新创建。
> <br> 那么问题来了，为了防止配置更改了，我们需要对于activity进行某些配置防止activity重新执行生命周期。
## configChanges 属性解析
> 列出 Activity 将自行处理的配置变更。在运行时发生配置变更时，默认情况下会关闭 Activity 并将其重启，但使用该属性声明配置将阻止 Activity 重启。相反，Activity 会保持运行状态，并且系统会调用其 `onConfigurationChanged()` 方法。
>
> **注意**：应避免使用该属性，并且只应在万不得已的情况下使用。如需了解有关如何正确处理配置变更所致重启的详细信息，请阅读[处理运行时变更](https://developer.android.com/guide/topics/resources/runtime-changes)。
>
> 任何或所有下列字符串均是该属性的有效值。若有多个值，则使用“`|`”进行分隔，例如“`locale|navigation|orientation`”。

| 值                     | 说明                                                         |
| :--------------------- | :----------------------------------------------------------- |
| “`density`”            | 显示密度发生变更 - 用户可能已指定不同的显示比例，或者有不同的显示现处于活跃状态。在 API 级别 24 中引入。 |
| “`fontScale`”          | 字体缩放系数发生变更 - 用户已选择新的全局字号。              |
| “`keyboard`”           | 键盘类型发生变更 - 例如，用户插入外置键盘。                  |
| “`keyboardHidden`”     | 键盘无障碍功能发生变更 - 例如，用户显示硬键盘。              |
| “`layoutDirection`”    | 布局方向发生变更 - 例如，自从左至右 (LTR) 更改为从右至左 (RTL)。在 API 级别 17 中引入。 |
| “`locale`”             | 语言区域发生变更 - 用户已为文本选择新的显示语言。            |
| “`mcc`”                | IMSI 移动设备国家/地区代码 (MCC) 发生变更 - 检测到 SIM 并更新 MCC。 |
| “`mnc`”                | IMSI 移动设备网络代码 (MNC) 发生变更 - 检测到 SIM 并更新 MNC。 |
| “`navigation`”         | 导航类型（轨迹球/方向键）发生变更。（这种情况通常不会发生。） |
| “`orientation`”        | 屏幕方向发生变更 - 用户旋转设备。**注意**：如果应用面向 Android 3.2（API 级别 13）或更高版本的系统，则还应声明 `"screenSize"` 和 `"screenLayout"` 配置，因为当设备在纵向模式与横向模式之间切换时，该配置也会发生变更。 |
| “`screenLayout`”       | 屏幕布局发生变更 - 现处于活跃状态的可能是其他显示模式。      |
| “`screenSize`”         | 当前可用屏幕尺寸发生变更。该值表示目前可用尺寸相对于当前宽高比的变更，当用户在横向模式与纵向模式之间切换时，它便会发生变更。在 API 级别 13 中引入。 |
| “`smallestScreenSize`” | 物理屏幕尺寸发生变更。该值表示与方向无关的尺寸变更，因此它只有在实际物理屏幕尺寸发生变更（如切换到外部显示器）时才会变化。对此配置所作变更对应 [smallestWidth 配置](https://developer.android.com/guide/topics/resources/providing-resources#SmallestScreenWidthQualifier)的变化。在 API 级别 13 中引入。 |
| “`touchscreen`”        | 触摸屏发生变更。（这种情况通常不会发生。）                   |
| “`uiMode`”             | 界面模式发生变更 - 用户已将设备置于桌面或车载基座，或者夜间模式发生变更。如需了解有关不同界面模式的更多信息，请参阅 `UiModeManager`。在 API 级别 8 中引入。 |

所有这些配置变更都可能影响应用所看到的资源值。因此，调用 `onConfigurationChanged()` 时，通常有必要再次检索所有资源（包括视图布局、可绘制对象等），以正确处理变更。

**注意**：如要处理所有[多窗口模式](https://developer.android.com/guide/topics/ui/multi-window)相关的配置变更，请使用 `"screenLayout"` 和 `"smallestScreenSize"`。Android 7.0（API 级别 24）或更高版本的系统支持多窗口模式。
## 包含屏幕旋转播放器配置
````aidl
  // 小米某手机上发生崩溃的配置  
  android:configChanges="screenSize|orientation|keyboardHidden"
  // 小米某手机上不发生崩溃的配置 
  android:configChanges="screenSize|orientation|keyboardHidden|screenLayout|smallestScreenSize"
  // https://github.com/CarGuo/GSYVideoPlayer 上界面配置 
  android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode"
  // https://github.com/Doikki/DKVideoPlayer 界面配置
  android:configChanges="keyboardHidden|orientation|screenSize|screenLayout"
  // https://github.com/google/ExoPlayer exoPlayer 配置
  android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode"
````
> 看到这些差异直接泪目。
## 上述问题中的知识点
### viewModel 共享
导入包 
````aidl
androidx.lifecycle:lifecycle-viewmodel:2.3.1
````
#### 获取viewModel
```aidl
 new ViewModelProvider(ViewModelStoreOwner ower).get(“key”,class);
```
> 默认情况下 ViewModelStoreOwner activity和fragment都包含自己的ViewModelStoreOwner,所以viewModel数据共享关键就是用activity使用的ViewModelStoreOwner
### 配置更改ViewModel 不参与销毁
> 这个点主要是onConfigurationChanged()配合使用。在 ComponentActivity中对于生命周期添加了监听。
````aidl
getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source,
                    @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    // Clear out the available context
                    mContextAwareHelper.clearAvailableContext();
                    // And clear the ViewModelStore
                    if (!isChangingConfigurations()) {
                        getViewModelStore().clear();
                    }
                }
            }
        });
````