> WebView 的优化 

#### 界面加载流程

![image-20220510112235695](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20220510112239.png)

* webview 初始化:用户无反馈
* 建立连接-接受界面样式渲染：白屏
* 执行代码：loading
* 执行完成：展示

> 如果没有开启缓存所有东西都是放在内存中。

![image-20220510135032951](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20220510135039.png)

![image-20220510135113127](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20220510135113.png)

### 优化

> 缩短界面加载流程的时间

#### 初始化优化

当APP 首次打开的时候，默认并不是初始化浏览器内核，二是只有当webView 创建实例的时候，才会创建webView 的基础框架，所以与浏览器不同的是APP中打开webView第一步并不是建立连接，而是启动浏览器内核。webView 的内核是共享的，所以多实例，第一个打开了，后面几个就比较快了。

##### 优化方案

由于这个过程发生在native 的代码中，单纯靠前端代码是无法优化的，大部分的方案都是前端和客户端协作完成。

* 在APP启动的时候，就初始化一个webView,并隐藏。
* 在访问webView的时候，直接使用这个webView 并加载对应的网页。

> 缺点：加大了额外的内存消耗 
>
> 

##### 清除缓存

> webViewClearCache(true);// 建议这么清除缓存，最安全的方案。

通过反射清除webView 的缓存

```
public void setConfigCallback(WindowManager windowManager) {
    try {
        Field field = WebView.class.getDeclaredField("mWebViewCore");
        field = field.getType().getDeclaredField("mBrowserFrame");
        field = field.getType().getDeclaredField("sConfigCallback");
        field.setAccessible(true);
        Object configCallback = field.get(null);
        if (null == configCallback) {
            return;
        }
        field = field.getType().getDeclaredField("mWindowManager");
        field.setAccessible(true);
        field.set(configCallback, windowManager);
    } catch (Exception e) {
    }

}
```

#### 独立进程解决方案

> 如果说采用跨进程解决方案，APP进程和webview 进程的通信就属于跨进程通信了。当前进程ＧＧ了，不会影响主进程。

* 对于某个activity 设置独立进程 

> ```
> android:process=":remoteweb"
> ```