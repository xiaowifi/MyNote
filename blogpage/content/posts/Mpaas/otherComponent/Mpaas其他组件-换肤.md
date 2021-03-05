+++
date = "2021-3-3"
title = "Mpaas其他组件-换肤"
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
##### 前言
>  说回正题，换肤这个功能，在好多app中都用。因为比较急促的开始整mpaas,导致文档没有看全，我也没有搜索到换肤这个调调。
> 但是在好多mpaas 控件中，这个调调是可以统一设置的。因为涉及到换肤，所以，控件并没有代码设置这一项。卑微，之前换肤切style不是这么写的。
> 作为一个老搬砖工了，经验害死人呀。
> 所有控件中不可更改属性都应该优先查找主题中是否支持修改。而不是考虑用父类原生使用，经验告诉我改不了。
## 换肤
> 作为大厂的东西，mpaas Android 组件中支持 2中换肤模式，一种是全局设置，一种是界面设置。
* 全局设置
* 界面设置
### 内容资料
> 在demo中的  package com.alipay.mobile.antui.viewdemo;  ChangeSkinActivity有详细介绍。<br>
> 本笔记也是基于这个activity进行描述扩展。
#### 换肤存储
````aidl
 AUThemeManager.setCurrentThemeKey(AUThemeManager.THEMEKEY_DEFAULT);
        SharedPreferences sharedPreferences = getSharedPreferences("com.alipay.mobile.antui", MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CurrentThemeKey", AUThemeManager.THEMEKEY_DEFAULT);
        editor.commit();
````
#### 换肤自定义
> 换肤是基于Java 代码实现，所以所有的子类都应该继承于 AUAbsTheme。ChangeThemeImpl提供了简单的使用方式。
##### key 值来源
> AUThemeKey 中包含所以mpaas ui 中支持的换肤的属性。AUAbsTheme中设置主题的key,应该从这个地方来。
### 单界面设置主题
> 单界面换肤，基于AUThemeFactory这个对象。通过 getLayoutInflater().setFactory(factory);进行设置。在setContentView方法之前。fragment中应该是差不多的。
#### 使用换肤功能
> 高端的食材往往只需要采用最朴素的烹饪方式。大厂大佬提供的换肤就一句话。
```aidl
 factory.applySkin(this, ChangeThemeImpl.getInstance());
```
#### 完整代码
````aidl
 private AUThemeFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory = new AUThemeFactory();
        getLayoutInflater().setFactory(factory);
        setContentView(R.layout.view_change_skin);
        //这句话应该放到哪里都行。不行就换一个地方。先写笔记后写demo，莫得办法
         factory.applySkin(this, ChangeThemeImpl.getInstance());
    }
    
     @Override
    protected void onDestroy() {
        super.onDestroy();
        // 需要关闭下
        factory.clean();
    }
    
````
#### 全局配置
> 在 Application 中写。emmmm? 未进行耗时内存验证。
````aidl
//将自定义的主题放进去
 AUThemeManager.putTheme("changeSkin", ChangeThemeImpl.getInstance());
 // 然后存储
        SharedPreferences sharedPreferences = getSharedPreferences("com.alipay.mobile.antui", MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CurrentThemeKey", "changeSkin");
        editor.commit();
        // 最后使用。emmmmm? 应该可以不用存储。人傻了
        AUThemeManager.setCurrentThemeKey("changeSkin");
````
#### ausegment 可用定义
> 虽然SEGMENT定了了多个，但是代码中就用了这两个。
````aidl
        put(AUThemeKey.SEGMENT_TEXTCOLOR,R.drawable.segment_color);
        put(AUThemeKey.SEGMENT_BOTTOM_COLOR,R.color.AU_COLOR2);
````
