>  JetPack组件LifeCycle与LiveData
## 正文 
* liveData 中post 和set的区别 
    * post 调用的set 方法，只是在讲回调切换到UI线程。
    * set 方法 不是UI线程　会抛异常。
×　liveData 监听
    * observer
    * 观察者有两种方式回调，一种是生命周期前注册回调，另外一种是set 之后再回调，因为Lifecycle 添加监听回调特性。所以会继续回调。









    