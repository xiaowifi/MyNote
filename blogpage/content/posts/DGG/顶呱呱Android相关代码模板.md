+++
date = "2021-2-19"
title = "Android相关代码模板"
description = "Android相关代码模板"
series = ["顶呱呱"]
featured = true
+++
## 正文 
>  基于DGG Android base  生成，activity,lazyFragment,fragment等等。
> https://freemarker.apache.org/docs/index.html
### 文件
* MVVM(DGG)Activity activity模板
* MVVM(DGG)Fragment fragment模板，提供lazyfragment模板和fragment模板
### Activity生成内容
* 基于mvvm 的activity
* viewModel
* viewRequest
* layout(databinding 外部层)
### fragment相关生成类型
* 可选lazyFragment和fragment
* viewModel
* viewRequest
* layout(databinding 外部层)
### 其他
* 生成的文件中，提供时间和作者注释。现在作者默认为“Android大佬”
* 命名采用驼峰命名法，资源文件自动转为下划线命名。
### 存放
* mac  /Applications/IntelliJ\ IDEA.app/Contents/plugins/android/lib/templates/activities  这个目录是存放activity模板
* mac  /Applications/IntelliJ\ IDEA.app/Contents/plugins/android/lib/templates/fragments  这个目录是存放fragment模板
* Windows 类似。应用安装路径/plugins/android/lib/templates/activities  
* windows 下Android studio 可能没有这个目录，但是IDEA 有这个目录。

### 打包
> 暂时不提供打包为插件内容，使用需要手动放置到对应目录。放置后需要重启编辑器。编辑器更新卸载重装后需要手动放置。
><br> Android studio 4.1以上版本 不支持这种方式导入模板。
> Android studio 4.1及其以上版本 需要采用 https://github.com/xiaowifi/dgg_android_code_template 这个项目的主模板创建。并且生成jar使用。
### 联系
yangfan6@dgg.net
