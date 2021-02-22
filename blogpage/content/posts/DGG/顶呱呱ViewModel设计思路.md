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
> 基于jetpack.viewModel 基于livedata 进行数据更新监听。所以数据外层需要使用livedata壳进行包装。
### 正常的列表数据
````aidl
public  final MutableLiveData<List<? extends BaseCustomModel>> loadContentList = new MutableLiveData<>();
````
### 正常的单model数据
````aidl

    public final MutableLiveData<BaseCustomModel> loadContentModel = new MutableLiveData<>();

````
### 空数据 包装
````aidl
  public  final MutableLiveData<Boolean> loadEmptyData = new MutableLiveData<>();
````
### 非正常code 包装
````aidl
 public  final MutableLiveData<BaseFailData> loadFailData = new MutableLiveData<>();
````
### 加载更多为空包装
> 可能没有下一页。
````aidl
public  final MutableLiveData<Boolean> loadMoreEmptyData = new MutableLiveData<>();
````
### 加载更多发生错误
````aidl
  public  final MutableLiveData<BaseFailData> loadMoreFailData = new MutableLiveData<>();
````

