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
> 和fragment 类似。
### 方法
#### onCreate
* 提供 initParameters 初始化参数。
* ARouter 阿里组件化初始化。
#### onCreateView
* 初始化viewdatabinding
* 获取rootView
* 设置创建为true  isViewCreated
* 初始化viewmodel
#### onViewCreated
* viewmodel 绑定UI
* 初始化数据监听
* 当界面可见的时候，统一进行数据处理
````aidl
if (!isHidden() && getUserVisibleHint()) {
// 可见状态,进行事件分发
dispatchUserVisibleHint(true);
}
````

#### setUserVisibleHint
> 修改Fragment的可见性 setUserVisibleHint 被调用有两种情况： 1）在切换tab的时候，会先于所有fragment的其他生命周期，先调用这个函数，可以看log 2) 对于之前已经调用过setUserVisibleHint方法的fragment后，让fragment从可见到不可见之间状态的变化
#### onHiddenChanged
用FragmentTransaction来控制fragment的hide和show时， 那么这个方法就会被调用。每当你对某个Fragment使用hide 或者是show的时候，那么这个Fragment就会自动调用这个方法。
#### dispatchUserVisibleHint
* 父fragment不可见的时候 不做处理
* 设置状态为不显示的时候不做处理。
* 如果界面可见，且为第一次可见，onFragmentFirstVisible调用
* 如果界面可见，可能不是第一可见，onFragmentResume();将次状态定义为真正可见。
* dispatchChildVisibleState 将事件分发到内联fragment。
* 如果界面不可见，onFragmentPause() 定义为真正的不可见。
* dispatchChildVisibleState 通知内联fragment 不可见了。

#### dispatchChildVisibleState
* 通过父层的fragment可见性 传参，拉去子类的fragment管理器，控制其子fragment是否可见。

#### isParentInvisible
> 判断父fragment的可见性
#### isSupportVisible
> 当前fragment 可见的时候为true
#### onResume
> 如果不是第一次调用，就进行显示，因为第一次默认会调用显示。
#### onPause
> 当由可见转变到不可见，且当前fragment包含子fragment的时候，通过代码设置 子fragment不可见。
#### onDestroyView
> 将view是否创建的标致值isViewCreated 设置为false
#### onDestroy
> 如果viewmodel 不为空，且已经绑定，需要解除绑定。
#### onFragmentFirstVisible
* 初始化view 需要实现
* 初始化数据 需要实现
* 初始化监听 需要实现
#### setLoadSir
> 需要手动调用 多层界面样式初始化。
#### showContent
> 上面数据初始化成功后，调用显示正常数据。

### 界面分层
结合setLoadSir 这个方法，当然也可以不结合这个方法。因为现在的adapter包含了这类功能。他只是提供了统一的方法调用。在viewModel 中设置MutableLiveData，通过改变监听实现达到，
````aidl
 ((MvvmBaseViewModel)viewModel).loadFailData.observe(getActivity(), new Observer<BaseFailData>() {
            @Override
            public void onChanged(BaseFailData failData) {
                showFailure(failData.getCode(),failData.getMessage());
            }});
````
同样的监听还有4个:
* loadEmptyData 加载空界面样式
* loadFailData  加载失败界面样式
* loadMoreEmptyData 加载更多为空的尾部样式
* loadMoreFailData  加载更多的失败的样式。
