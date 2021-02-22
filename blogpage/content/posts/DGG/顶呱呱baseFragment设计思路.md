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
> 同样基于 jetpack中的mvvm.和baseactivity类似，
### 方法
#### onCreate
* 提供初始化参数方法。initParameters
#### onCreateView
* 通过id生成databinding
* 通过反射生成viewmodel

#### onViewCreated
* 绑定UI
* 初始化view,需要实现
* 初始化数据 viewModel中的数据livedata监听
* 初始化数据 需要实现
* 初始化view监听
#### setLoadSir 
> 设置多层界面。需要手动调用。

### 使用工具
* [LoadSir](https://github.com/KingJA/LoadSir) 界面多状态切换 工具。空界面，错误界面，成功界面等等。



