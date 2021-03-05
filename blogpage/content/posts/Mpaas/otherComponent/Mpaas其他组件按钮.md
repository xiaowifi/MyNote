+++
date = "2021-3-3"
title = "Mpaas其他组件-按钮"
slug = "dgg"
series = ["Mpaas"]
featured = true
+++
##导入包
> mpaas 提供Android studio 插件，在插件中选择使用。
````aidl
// ui 库 
 api 'com.mpaas.android:antui'
````
## AUButton 
````aidl
<com.alipay.mobile.antui.basic.AUButton
                style="@style/mainButtonStyle"
                android:layout_margin="12dp"
                android:clickable="true"
                android:text="主要操作 Normal" />
````

> 基于 android.widget.Button 

### Style 接口

| 属性名             | 说明       | tag  |
| :----------------- | :--------- | ---- |
| mainButtonStyle    | 页面主按钮 | main |
| subButtonStyle     | 页面次按钮 | sub  |
| warnButtonStyle    | 警告按钮   | warn |
| assMainButtonStyle | 辅助主按钮 | ass  |
| assButtonStyle     | 辅助次按钮 | ass  |
| listButtonStyle    | 列表按钮   | list |

#### 基于style 自定义样式

```
<item name="android:tag">main</item>
```

这个属性一定要重置，否则自定义样式可能不会生效。

默认提供下列type.

```
public static final java.lang.String BTN_TYPE_MAIN = "main";
public static final java.lang.String BTN_TYPE_SUB = "sub";
public static final java.lang.String BTN_TYPE_WARNING = "warning";
public static final java.lang.String BTN_TYPE_MAIN_GROUP = "mainGroup";
public static final java.lang.String BTN_TYPE_SUB_GROUP = "subGroup";
public static final java.lang.String BTN_TYPE_ASS_TRANS = "ass_trans";
public static final java.lang.String BIN_TYPE_NO_RECT = "no_rect";
```