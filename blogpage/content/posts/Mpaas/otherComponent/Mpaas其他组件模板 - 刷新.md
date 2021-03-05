+++
date = "2021-3-3"
title = "Mpaas其他组件-刷新"
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
## 刷新组件
* AURefreshListView 这个本身就是一个listview.
* AUPullRefreshView 这个是一个容器，需要内部添加。
### AURefreshListView
xml 使用，本身就是一个listview.不用内部填充listview.
````aidl
  <com.alipay.mobile.antui.load.AURefreshListView
        android:id="@+id/refresh_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
````
#### 方法
> 关闭刷新头和加载更多都需要在ui线程中使用。
* setLoadingText 这个是下拉刷新未松手的自定义文本。
* setLoadingView 提供修改刷新头的背景和刷新头样式，提供两种样式。比如说：AntLoadingView.STYLE_WHITE
````aidl
    AntLoadingView 
    public static final java.lang.String STYLE_BLUE = "_BLUE";
    public static final java.lang.String STYLE_WHITE = "_WHITE";
````
* setLoadingView 传入自定义的loadingview.
* setOnLoadMoreListener 设置加载更多监听。
* updateLoadMore(isShowLoad,hasMore) 在ui线程调用
* setOnPullRefreshListener 刷新监听
* finishRefresh() 关闭刷新。UI 线程调用。
* setLoadingView() 和设置加载刷新头样式差不多。
#### 自定义刷新头实例代码
> 参考AntLoadingView。
> 加载更多类似。
### AUPullRefreshView
````aidl
    <com.alipay.mobile.antui.basic.AUPullRefreshView
            android:id="@+id/pull_refresh"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <com.alipay.mobile.antui.basic.AUListView
                android:id="@+id/list"
                android:layout_width="fill_parent"
                android:layout_height="fill_parent" />
    </com.alipay.mobile.antui.basic.AUPullRefreshView>
````
> 这是容器，要加listview.这个调调没有加载更多。
> 应该支持自定义加载loading头。
> 可以设置是否可以下拉刷新。
#### 方法
* setRefreshListener 这是下拉刷新监听，同时设置刷新头和是否可以下拉刷新。