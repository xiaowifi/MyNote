## 资料
* [view系列疑惑之关于view的onMeasure执行两次的问题深层探究解析](https://www.jianshu.com/p/7c6e64747d0d)
* [AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)
# 正文
> 2022年8 最近公司接入了支付宝的魔方卡片。偶现了一个问题，卡片显示缩小了，同时布局显示错乱了。emmmm? 发现了这个问题的手机上老板的手机。
> 测试部也没有复现这个问题，因为iOS 没有这个问题。同时正常情况下是对的，那么问题来了，到底是谁的问题？<br>

> 既然大佬被请去喝茶培训去了，那无论是谁的问题，是不是都是接盘人的问题？还好卡片不是我接盘，嘻嘻。

在花费海量的时间阅读大佬的代码与功能逻辑之后，发现卡片包含以下特性。
* 如果设置卡片宽高为内容包裹，或者填充父容器，卡片会随着父容器的大小而变化。
* 如果对于卡片高宽写死，那么父容器不管怎么变化，他也不会变化，这和符合Android view 设计。
> 这不是就可以说明，卡片的布局显示方式和Android view一模一样，没有区别。

问题来了？
* 他们设计的时候，将卡片的宽高定死了。
* 在Android 上没有处理onMeasure与onLayout？
* dp转px 使用的是TypedValue 获取(这很正常，好像也没有什么不对)？

通过上面资料[view系列疑惑之关于view的onMeasure执行两次的问题深层探究解析](https://www.jianshu.com/p/7c6e64747d0d) 可以确定 onMeasure 首次会执行两次，onLayout 会执行一次。
如果说viewGroup的话，每次添加一个view，都会执行一次onMeasure和onLayout。所以在特定的情况下，不处理这两个方法也没有问题是吧。

那么到底是什么导致了，卡片获取到的屏幕高宽会比实际的小呢？ 这个估计就和屏幕适配有关了？，通常来说，UI设计师设计界面的时候，通常只会设计一套iOS的。然后Android 上进行适配，比如说设计稿是375的，那么卡片传入过来的dp就是375dp
在任何手机上都都传入 375dp。但是context传入的不同，会导致获取到的px 不同，如：<br>
通过系统级别:(方法1)
````java
public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
````
通过view或者activity:(方法2)
````java
 public static int dp2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }
````
通过方法1 获取115dp 获取到的px 值为 316,通过方法2获取到的是331。emmmmm ？但是在没有屏幕适配方案的Demo中 这两个方法获取到值是一模一样的。
emmmm ? 我看你在为难我玛卡巴卡.png。那么懂屏幕适配的时机是不是就很重要了(主要是偶现问题，不好直接放弃这个界面的屏幕适配吧，没有定位到问题，老板也不同意放弃屏幕适配啊)。


屏幕适配方案：我们采用的是字节跳动开源的[AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)。
* <u>***由于修改的 density 在整个项目中是全局的，所以只要一次修改，项目中的所有地方都会受益***<u> 
* <u>***自动运行 只需要声明一个 ContentProvider，在它的 onCreate 方法中启动框架即可，在 App 启动时，系统会在 App 的主进程中自动实例化你声明的这个 ContentProvider，并调用它的 onCreate 方法，执行时机比 Application#onCreate 还靠前，可以做一些初始化的工作***<u> 
