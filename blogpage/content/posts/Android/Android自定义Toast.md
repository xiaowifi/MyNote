+++
date = "2021-5-27"
title = "Android自定义Toast"
description = "Android自定义Toast"
tags = [ "toast"]
categories = [
"android基础"
]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> 话说toast 这个调调 在Android上使用蛮频繁的。通常就是通过自己的自定义，如果不自定义的话，可能会提一个 toast重复提示的bug，虽然这个可以通过其他逻辑控制与限制。
> 写笔记嘛，那就都写。
## 正文
### toast 单例
> toast 自定义1，单例。直接抄一个阿里的toast 单例。

````aidl

public class ToastUtils {

    private static Toast mToast;
    private static Field mFieldTN;
    private static Field mFieldTNHandler;

    static {
        if (Build.VERSION.SDK_INT == 25) {
            try {
                mFieldTN = Toast.class.getDeclaredField("mTN");
                mFieldTN.setAccessible(true);
                mFieldTNHandler = mFieldTN.getType().getDeclaredField("mHandler");
                mFieldTNHandler.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化/获取mToast对象，适配android 7.1，处理BadTokenException
     *
     * @param context Context
     * @return Toast
     */
    @SuppressLint("ShowToast")
    private static Toast initToast(Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
            if (Build.VERSION.SDK_INT == 25) {
                hook(mToast);
            }
        }
        return mToast;
    }

    /**
     * Toast位置显示在屏幕中间 默认短时长{@link Toast#LENGTH_SHORT}
     *
     * @param context Context
     * @param content 显示内容
     */
    public static void showInCenter(Context context, String content) {

        Toast toast = initToast(context);
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * show Toast 默认短时长 {@link Toast#LENGTH_SHORT}
     *
     * @param context Context
     * @param message 内容
     */
    public static void show(Context context, String message) {

        show(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * show Toast 默认短时长 {@link Toast#LENGTH_SHORT}
     *
     * @param context Context
     * @param stringId 内容id
     */
    public static void show(Context context, int stringId) {

        show(context, stringId, Toast.LENGTH_SHORT);
    }

    /**
     * show Toast 可选时长
     *
     * @param context Context
     * @param message 内容
     * @param duration {@link Toast#LENGTH_SHORT},{@link Toast#LENGTH_LONG}
     */
    public static void show(Context context, String message, int duration) {

        Toast toast = initToast(context);
        toast.setDuration(duration);
        toast.setText(message);
        mToast.show();
    }

    /**
     * show Toast 可选时长
     *
     * @param context Context
     * @param stringId 内容id
     * @param duration {@link Toast#LENGTH_SHORT},{@link Toast#LENGTH_LONG}
     */
    public static void show(Context context, int stringId, int duration) {

        Toast toast = initToast(context);
        toast.setDuration(duration);
        toast.setText(stringId);
        mToast.show();
    }

    /**
     * show Toast 可选位置
     *
     * @param context Context
     * @param message 内容
     * @param duration {@link Toast#LENGTH_SHORT},{@link Toast#LENGTH_LONG}
     */
    public static void show(Context context, String message, int gravity, int duration) {

        Toast toast = initToast(context);
        toast.setDuration(duration);
        toast.setText(message);
        toast.setGravity(gravity, 0, 0);
        mToast.show();
    }


    /**
     * 7.1手机上的BadTokenException 相关处理
     *
     * @param toast Toast对象
     */
    private static void hook(Toast toast) {
        try {
            Object tn = mFieldTN.get(toast);
            Handler preHandler = (Handler)mFieldTNHandler.get(tn);
            mFieldTNHandler.set(tn, new FiexHandler(preHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 7.1手机上的BadTokenException 相关处理
     *
     */
    private static class FiexHandler extends Handler {
        private Handler impl;

        FiexHandler(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            impl.handleMessage(msg);
        }
    }
}

````
### toast 修改位置
> 这个逻辑蛮简单的，和view 设置位置差不多。
````aidl
 toast.setGravity(gravity, 0, 0);
````

### toast 自定义view 
> 网络上花里胡哨的toast 就是通过自定义view 去实现的。


````aidl

        Toast currentToast = Toast.makeText(context, "", duration);
        View toastLayout = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);

        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        if (!withIcon) {
            toastIcon.setVisibility(View.GONE);
        }
        if (null != icon) {
            toastIcon.setImageDrawable(icon);
            toastIcon.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(message)) {
            toastTextView.setText(message);
        }
        toastTextView.setTextColor(textColor);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        currentToast.setView(toastLayout);

        if (!allowQueue) {
            if (lastToast != null)
                lastToast.cancel();
            lastToast = currentToast;
        }
        currentToast.setGravity(Gravity.CENTER, 0, 0);

```` 
## 结束


