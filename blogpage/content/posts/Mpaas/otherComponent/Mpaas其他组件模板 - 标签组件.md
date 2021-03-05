+++
date = "2021-3-3"
title = "Mpaas其他组件"
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
## AUTabBarItem

> 需要通过外层布局包裹，这个只是一个文本加图标的组合体。每个view独立，基础使用无法达到Android中 tablayou 切换时候，底部条平滑滚动的效果。因为相对独立，所以可能出现同时选中两个的情况，需要代码逻辑控制。

#### xml

```
<com.alipay.mobile.antui.bar.AUTabBarItem
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:clickable="true"
    android:text="标签1"
    app:topIconSid="@drawable/tab_icon" />
```

#### 自定义属性

| 属性名      | 说明     | 类型             |
| :---------- | :------- | :--------------- |
| topIconSid  | 图标     | reference        |
| topIconSize | 图标大小 | dimension        |
| textColor   | 文字颜色 | color，reference |

##### 颜色

```
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_selected="true" android:color="@color/AU_COLOR1" />
    <item android:state_focused="true" android:color="@color/AU_COLOR1" />
    <item android:state_pressed="true" android:color="@color/AU_COLOR1" />
    <item android:color="@color/AU_COLOR6"/>
</selector>
```

##### 图标

```
<selector xmlns:android="http://schemas.android.com/apk/res/android" >
    <item android:state_selected="true" android:drawable="@drawable/tab_bar_alipay_pressed"/>
    <item android:state_pressed="true" android:drawable="@drawable/tab_bar_alipay_pressed"/>
    <item android:drawable="@drawable/tab_bar_alipay_normal"/>
</selector>
```