
## 正文
````aidl
final ViewConfiguration configuration = ViewConfiguration.get(context);
//  获取最小的滑动距离。
 this.touchSlop = configuration.getScaledTouchSlop();
````
