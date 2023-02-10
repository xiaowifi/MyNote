# 前言
2023年2月8日，在处理mpaas 相关问题的时候，发现mpaas 对于单例的初始化使用了atomic系列去防止单例多次初始化。
````aidl
package java.util.concurrent.atomic;
````
# 正文
AtomicBoolean 可用在应用程序中（如以原子方式更新的标志），但不能用于替换 Boolean。

换一句话说，Atomic就是原子性的意思，即能够保证在高并发的情况下只有一个线程能够访问这个属性值。（类似我们之前所说的volatile）

一般情况下，我们使用 AtomicBoolean 高效并发处理 “只初始化一次” 的功能要求：
````aidl
private static AtomicBoolean initialized = new AtomicBoolean(false);
public void init()
{
if( initialized.compareAndSet(false, true) )
{
// 这里放置初始化代码....
}
}
````
如果没有AtomicBoolean，我们可以使用volatile做如下操作：
````aidl
public static volatile initialized = false;
public void init()
{
if( initialized == false ){
initialized = true;
// 这里初始化代码....
}
}
````
## 