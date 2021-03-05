+++
date = "2021-3-3"
title = "Mpaas其他组件-开关组件"
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
## 正文

> ``` 
> package com.alipay.mobile.antui.basic; AUSwitch 
> AUSwitch 基于Android Switch ，
> ```

### 修改内容

> 主要修改内容：

````
  this.setThumbResource(drawable.au_switch_thumb);
        this.setTrackResource(drawable.au_switch_track);
        this.setSwitchMinWidth(DensityUtil.dip2px(this.getContext(), 70.0F));
        this.setTextOn("   ");
        this.setTextOff("   ");
        this.textOn = this.getResources().getString(string.opened);
        this.textOff = this.getResources().getString(string.closed);
````

### xml 使用

```
<com.alipay.mobile.antui.basic.AUSwitch
        android:id="@+id/change_switch"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        android:layout_marginTop="8dp"
        android:checked="false"
        android:layout_marginBottom="8dp"
/>
```

### 自定义图片

```
AUSwitch change_switch= view.findViewById(R.id.change_switch);
change_switch.setThumbResource(R.drawable.keyboard_switch);
change_switch.setThumbResource(R.drawable.keyboard_switch_press);
```

#### 效果

![image-20210305160542073](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210305160542.png)

![image-20210305160554123](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210305160554.png)

![image-20210305160606370](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210305160606.png)

> 有点丑，主要是证明他是可以修改的。需要自己约束好这个调调。可能对于圆角类型的switch 不是太友好。