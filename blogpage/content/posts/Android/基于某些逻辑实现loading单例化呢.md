+++
date = "2021-8-19"
title = "基于某些逻辑实现loading单例化呢"
description = "基于某些逻辑实现loading单例化呢"
categories = [
"android基础"
]
featured = true
draft = true 
+++
## 前言
> 网络加载遮罩层的使用还是蛮常见的，比如防止各种重复点击，比如防止数据没有加载下来用户就进行操作了。
> 但是无论是dialog还是fragment.或者的PopupWindow 都是基于context，所以在一定程度上限制了loading层的单例化。
> 搬砖多年，写得最多的loading就是在activity中控制loading的显示或者隐藏操作，fragment或者viewModel 显示或者隐藏loading 都是需要通过activity调用。
> loading层单例化这种想法随着activity中多个fragment的使用越来越强，虽然觉得这种想法有点骚。<br>
> 虽然写代码的时候，都想着通过强逻辑去控制这些东西，但是强逻辑本身就是一个假的概念，搬砖过程中，通常通过经验去加强逻辑，
> 今天突然遇到一个bug,服务器json返回的对象中某个百分百存在的字段在新版本中竟然不是百分百存在的了，本地再转databinding的数据模型的时候，直接空指针了，还好通过Rxjava 全部注册拦截，没有崩溃，但是loading层就是没有消失。
> 虽然一直说，不可百分百信任服务器数据，需要进行容错判断。但是这个调调，不是百分百控制完了的呀。<br>
> 所以loading 层的单例就有存在的意义了，起码在我这里是这个样子的，但是PopupWindow实现loading层就有一个问题，
> 如果网络贼快，显示和消失就贼快。

[导入类：Utils](https://github.com/Blankj/AndroidUtilCode)



### CpsCallback 实现类
````aidl
public abstract class CpsCallback implements Serializable {
    private View rootView;
    private Context context;

    public CpsCallback() {
    }

    public CpsCallback setCallback(Context context) {
        this.context = context;
        return this;
    }

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
        onViewCreate(context, rootView);
        return rootView;
    }

    protected View onBuildView(Context context) {
        return null;
    }
    
    protected abstract int onCreateView();

    /**
     * Called immediately after {@link #onCreateView()}
     *
     * @since 1.2.2
     */
    protected void onViewCreate(Context context, View view) {
    }
}

````
### loadingTools 实现类
````aidl
public class LoadingTools {
    private static LoadingTools tools;
    CpsCallback callBack;
    private PopupWindow showPop;
    boolean touchable = false;//

    private LoadingTools() {
        Utils.getActivityLifecycle().setOnActivityDestroyedListener(destroyedListener);
        Utils.getActivityLifecycle().addListener("loading", changedListener);
    }

    // 单例。
    public static LoadingTools getInstance() {
        if (tools == null) {
            tools = new LoadingTools();
        }
        return tools;
    }

    /**
     * 设置显示的callback
     *
     * @param callBack
     * @return
     */
    public LoadingTools setShowCallBack(CpsCallback callBack) {
        this.callBack = callBack;
        return this;
    }

    /**
     * 设置是否可以通过外部点击隐藏弹窗。
     *
     * @param touchable
     * @return
     */
    public LoadingTools setOutsideTouchable(boolean touchable) {
        this.touchable = touchable;
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        LogUtils.e("显示");
        if (showPop != null && showPop.isShowing()) {
            return;
        }
        if (showPop != null) {
            showPop.dismiss();
        }
        Activity activity = Utils.getActivityLifecycle().getTopActivity();
        ViewGroup content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        showPop = getShowPop();
        showPop.showAtLocation(content, Gravity.CENTER, 0, 0);
    }

    /**
     * 消失。
     */
    public void dismiss() {
        LogUtils.e("显示");
        if (showPop != null && showPop.isShowing()) {
            showPop.dismiss();
        }
    }

    /**
     * 获取显示的
     */
    private PopupWindow getShowPop() {
        Activity activity = Utils.getActivityLifecycle().getTopActivity();
        callBack.setCallback(activity);
        View view = callBack.getRootView();
        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.transparent));
        popupWindow.setBackgroundDrawable(dw);
        //设置外部点击关闭ppw窗口
        popupWindow.setOutsideTouchable(touchable);
        //软键盘 可以获取焦点
        popupWindow.setFocusable(false);
        return popupWindow;
    }


    // 销毁监听。
    final Utils.OnActivityDestroyedListener destroyedListener = activity -> {
        if (showPop != null) {
            LogUtils.e("关闭了"+activity.getClass().getName());
            showPop.dismiss();
        }
    };

    final Utils.OnAppStatusChangedListener changedListener = new Utils.OnAppStatusChangedListener() {
        @Override
        public void onForeground() {
            //在前台
        }

        @Override
        public void onBackground() {
            // 处于后台。
            if (showPop != null) {
                //showPop.dismiss();
            }
        }
    };

}
````
### 初始化
````aidl
LoadingTools.getInstance().setShowCallBack(new CpsCallback());
````
### 调用
* 显示:LoadingTools.getInstance().show();
* 隐藏:LoadingTools.getInstance().dismiss();