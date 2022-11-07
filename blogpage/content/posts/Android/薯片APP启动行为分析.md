# 前言

目前在处理薯片APP的隐私合规问题。腾讯那边反馈了APP的行为分析报告。其中有一条是“同意隐私政策前”,"读取系统设置",腾讯那边要求如果是必须的就申明到隐私协议里面，如果不是必须的就需要注释掉。

## 资料

* [整改图文教程查](https://wikinew.open.qq.com/index.html#/iwiki/4007030527)

## 正文

| 同意隐私政策前 | 读取系统设置 | 否   | android.permission.WRITE_SETTINGS | sound_effects_enabled      | android.provider.Settings$NameValueCache.getStringForUser(Settings.java:1878)<---android.provider.Settings$System.getStringForUser(Settings.java:2267)<---android.provider.Settings$System.getIntForUser(Settings.java:2342)<---android.media.AudioManager.querySoundEffectsEnabled(AudioManager.java:1968)<---android.media.AudioManager.playSoundEffect(AudioManager.java:1885)<---android.view.ViewRootImpl.playSoundEffect(ViewRootImpl.java:6159)<---android.view.View.playSoundEffect(View.java:22540)<---android.view.View.performClick(View.java:6293)<---android.view.View$PerformClick.run(View.java:24770)<---android.os.Handler.handleCallback(Handler.java:790)<---android.os.Handler.dispatchMessage(Handler.java:99)<---android.os.Looper.loop(Looper.java:164)<---android.app.ActivityThread.main(ActivityThread.java:6843)<---java.lang.reflect.Method.invoke(Native Method)<---com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:438)<---com.android.internal.os.ZygoteInit.main(ZygoteInit.java:807) |
| -------------- | ------------ | ---- | --------------------------------- | -------------------------- | ------------------------------------------------------------ |
| 同意隐私政策前 | 读取系统设置 | 否   | android.permission.WRITE_SETTINGS | animator_duration_scale    | android.provider.Settings$NameValueCache.getStringForUser(Settings.java:1878)<---android.provider.Settings$Global.getStringForUser(Settings.java:10470)<---android.provider.Settings$Global.getString(Settings.java:10459)<---android.provider.Settings$Global.getFloat(Settings.java:10775)<---org.chromium.content.browser.accessibility.BrowserAccessibilityState.recordAccessibilityHistograms(chromium-SystemWebView.apk-default-418316203:2) |
| 同意隐私政策前 | 读取系统设置 | 否   | android.permission.WRITE_SETTINGS | force_resizable_activities | android.provider.Settings$NameValueCache.getStringForUser(Settings.java:1878)<---android.provider.Settings$Global.getStringForUser(Settings.java:10470)<---android.provider.Settings$Global.getString(Settings.java:10459)<---android.provider.Settings$Global.getInt(Settings.java:10638)<---com.android.internal.policy.PhoneWindow.<init>(PhoneWindow.java:328)<---android.app.Activity.attach(Activity.java:7007)<---android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3047)<---android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3191)<---android.app.ActivityThread.-wrap11(Unknown Source:0)<---android.app.ActivityThread$H.handleMessage(ActivityThread.java:1921)<---android.os.Handler.dispatchMessage(Handler.java:106)<---android.os.Looper.loop(Looper.java:164)<---android.app.ActivityThread.main(ActivityThread.java:6843)<---java.lang.reflect.Method.invoke(Native Method)<---com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:438)<---com.android.internal.os.ZygoteInit.main(ZygoteInit.java:807) |

## 问题排查

我们可以通过表格看到，他其实都是调用了Settings 里面的内容。而且都是读取这些信息，通常我们写业务代码的不会获取这些内容的。

### sound_effects_enabled 

```
Settings.System.SOUND_EFFECTS_ENABLED;
```

这个是获取是否开启按键音。

### animator_duration_scale

基于动画的动画的缩放因子。这会影响所有这些动画的开始延迟和持续时间。设置为0将导致动画立即结束。默认值为1。

###  force_resizable_activities

是否可以调整任何活动的大小。如果这是真的无论清单值如何，活动都可以针对多窗口调整大小。（0=假，1=真）

