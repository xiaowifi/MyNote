## 资料
* [ConnectivityManager](https://developer.android.com/reference/android/net/ConnectivityManager)
* [google 网络访问](https://developer.android.com/reference/android/net/package-summary)
# 正文 
有关网络连接状态的查询的类。它还会在网络连接发生变化时通知应用程序。
这个类的主要职责是：
* 监控网络连接（Wi-Fi、GPRS、UMTS 等）
* 当网络连接发生变化时发送广播意图
* 当与网络的连接丢失时，尝试“故障转移”到另一个网络
* 提供 API 允许应用程序查询可用网络的粗粒度或细粒度状态
* 提供允许应用程序为其数据流量请求和选择网络的 API
