# 薯片C端android界面多状态组件（LoadSir）使用

### 参考资料：

* [GitHub LoadSir](https://github.com/KingJA/LoadSir)

### 使用

> 导入：compile 'com.kingja.loadsir:loadsir:1.3.8'

#### 初始化

> 当前组件支持通过class 填入多个 状态代表的view层，支持设置默认展示层，支持设置每个状态层的回调

##### 薯片自定义layout

* 错误样式：![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210818145801.png)

  * ErrorCallback
  *  EmptyCallback

* loading 样式：![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210818145842.png)

  ​	LoadingCallback

*  连接超时：![image-20210818145946831](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210818145946.png)TimeoutCallback

  

  默认显示 LoadingCallback

##### 全部初始化

```
LoadSir.beginBuilder()
        .addCallback(new ErrorCallback())
        .addCallback(new LoadingCallback())
        .addCallback(new EmptyCallback())
        .addCallback(new TimeoutCallback())
        .setDefaultCallback(LoadingCallback.class)
        .commit();
```

#### 使用

##### 初始化

```
mLoadService = LoadSir.getDefault()
        .register(viewDataBinding.relativeLoading, (Callback.OnReloadListener) v -> onLoadFailRetry(v));// 注册默认的点击事件。
```

##### 不控制callBack 显示

```
mLoadService.showSuccess();// 显示正文。
```

#####  控制callBack 显示内容

```
mLoadService.setCallBack(ErrorCallback.class, new Transport() {
    @Override
    public void order(Context context, View view) {
        TextView tv_err_msg = view.findViewById(R.id.tv_err_msg);
        tv_err_msg.setText(message);
    }
});
mLoadService.showCallback(ErrorCallback.class);//显示错误层。
```

区别就在于需要先设置callBack,然后再显示。

#####  回调

```
public View getRootView() {
    int resId = onCreateView();
    if (resId == 0 && rootView != null) {
        return rootView;
    }
    if (onBuildView(context) != null) {
        rootView = onBuildView(context);
    }

    if (rootView == null) {
        rootView = View.inflate(context, onCreateView(), null);
    }
    rootView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onReloadEvent(context, rootView)) {
                return;
            }
            if (onReloadListener != null) {
                onReloadListener.onReload(v);
            }
        }
    });
    onViewCreate(context, rootView);
    return rootView;
}
```

当view显示的时候，就会设置一次rootview的点击事件。所以除了正文，所有的的层的点击事件都会走 Callback.OnReloadListener。所以这个调调 需要对点击view进行区分，而不是简单粗暴的将重新加载设置到  Callback.OnReloadListener 就行。





