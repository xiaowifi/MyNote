+++ 
date = "2021-6-22"
title = "薯片C端androidCMS配置项JSON"
description = "薯片C端androidCMS配置项JSON"

featured = false
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)

## 前言

> cms 导航配置用于实现，app内部跳转，app打开小程序，app打开H5等功能。
> 通过cms 配置导航参数，用于动态控制界面显示功能效果等。

现支持

* 原生功能界面
* H5界面
* Flutter 功能界面
* mpaas 小程序
* mpaas 离线包

## 正文

### json

````aidl
{
    "path": "/common/android/SingleWeb",
    "parameter": {
        "urlstr": "http://172.16.133.75:3001/activity/newproduct",
        "isHideNav": 1,
        "isH5": "00000001",
        "page": ""
    },
    "isLogin": "0",
    "version": "1.0.0"
}
````

### 字段解析

|  字段   | 用途  |
|  ----  | ----  |
|  path   | 原生功能界面跳转路由。H5界面 App内部打开的界面，打开H5界面固定参数为:/common/android/SingleWeb   |
| isLogin | 是否需要登录 |
| version | 版本号，好像莫得用 |
|parameter | 参数对象 |
| urlstr| app 内部打开H5提供的H5路径  |
|isHideNav | app 内部打开H5 是否使用原生标题栏=1 隐藏 |
|isH5 | 用于存储 将要打开的小程序或者离线包的id   |
|appletsId | 用于存储 将要打开的小程序或者离线包的id |
|page| mpaas 小程序或者离线包将要打开的界面，默认直接进入小程序或者离线包主页 |
|title| H5功能使用，显示的原生标题的标题名称 |
|isHideBack | =1 隐藏原生返回键， | 
|isFlutterEnter | 是否是flutter打开的H5 界面 |
|linkType| =1，进行逻辑判断跳转小程序，离线包，H5,原生功能界面，=2 app内部打开一个网页，=3 app 内部打开一个带原生标题的网页|



