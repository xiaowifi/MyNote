+++
date = "2021-2-22"
title = "关于顶呱呱Android应用中baseActivity设计思路"
description = "关于顶呱呱Android应用中baseActivity设计思路"
series = ["顶呱呱"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
> 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 
## 正文
> 当前项目 基于AndroidX,使用jetpack为基础设计。使用databinding。
### 变量
* viewModel jetpack基础。
* viewDataBinding databinding
* LoadService 对于服务器数据进行界面响应切换模块等。需要手动在需要的代码模块注册。
* Context  带生命周期上下文，为当前activity。

### 方法
#### onCreate
* 判断getSupportActionBar 是否为空，为空就隐藏 bar.
* 设置context
* 注册路由 ARouter
* 提供初始化之前方法。initContentViewBefore();可重写
* 提供初始化databinding方法 performDataBinding();
* 提供初始化view方法 initView();需要实现。
* 提供初始化数据方法 initData();需要实现。
* 提供view监听设置方法 initListener() 需要实现
#### onDestroy
* viewmodule销毁
#### getLayoutId
> 需要实现 传递当前layout id
#### performDataBinding
````aidl
 public void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initViewModel();
        viewDataBinding.setLifecycleOwner(this);
        if (getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
//        binding.setVariable(viewModelId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        viewDataBinding.executePendingBindings();
    }
````
#### getViewModel 
> 通过反射创建的viewModel。
#### initViewModel
> 通过getViewModel生成当前界面的viewmodel。
> 当前方法在performDataBinding 中调用。
> 对viewmodel中空数据和错误数据进行更改监听回调。并且提供子类可使用方法。


### 工具
* [ARouter](https://github.com/alibaba/ARouter/blob/master/README_CN.md) 用于组件化开发。
* [LoadSir](https://github.com/KingJA/LoadSir) 界面多状态切换 工具。空界面，错误界面，成功界面等等。


