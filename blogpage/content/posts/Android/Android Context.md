
## 前言

我去，上午酝酿了半天，吃饭没有保存，白写了这么多字。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png" style="zoom:25%;" />  有点难受。

说正事吧，Context 这个对象几乎贯穿Android 所有事物。而 像[Application](https://developer.android.google.cn/reference/android/app/Application)，[Activity](https://developer.android.google.cn/reference/android/app/Activity) 都是其间接子类。而这么屌的还有很多，这里就不一一列举了，直接上[google Android Context](https://developer.android.google.cn/reference/android/content/Context) 。Context 子类这么多，最常用的也就是 Application 和Activity了，而这两个子类都是有生命周期的。那么这两个子类获取到的Context有什么不同呢？

- Application 和Activity都有自己的生命周期，那么获取到的Context 的也是有生命周期的。

- JAVA 继承实现的关系，Application和Activity 无法直接强制转换。

  ![子类](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921140823.png)

  





## 正文

### Application 

Application 通常都需要自己继承一个，然后在AndroidManifest 的Application节点中 对android:name 进行赋值。在Application 中获取 Context 也非常简单，就是它本身了。Application 也提供了2个获取 Context 的方法。

Application 继承于ContextWrapper，getBaseContext()，getApplicationContext()这两个方法都是ContextWrapper 实现的。

#### getBaseContext()

- [ ] 

- [ ] 待完成，这个调调有点复杂，容我捋一捋、

#### getApplicationContext()

- [ ] 待完成。这个调调有点复杂，容我捋一捋、

#### Activity 中获取Application 

Activity 继承于ContextThemeWrapper，而ContextThemeWrapper 继承于ContextWrapper。这其中的逻辑就很复杂，但是获取方式很简单：

```
getApplication();
```

#### fragment 中获取Application

Fragment 中可以直接获取到Activity，那么获取Application就需要先获取 Activity.

```
getActivity().getApplication();
```

#### 直接获取Application

```
private static Application getApplicationByReflect() {
    try {
        @SuppressLint("PrivateApi")
        Class<?> activityThread = Class.forName("android.app.ActivityThread");
        Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
        Object app = activityThread.getMethod("getApplication").invoke(thread);
        if (app == null) {
            throw new NullPointerException("u should init first");
        }
        return (Application) app;
    } catch (NoSuchMethodException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (InvocationTargetException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    throw new NullPointerException("u should init first");
}
```



## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。