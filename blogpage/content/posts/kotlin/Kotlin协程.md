# 前言
协程 （在JVM 上是一个线程框架）
* GlobalScope.launch{} 在后台开启一个协程，协程的生命周期和当前jvm 生命周期相同。逻辑意义上可以开启无数个协程。
* delay是一个在协程中特殊的挂起函数，只能在协程中使用
* thread{} 是直接使用子线程。
* 挂起一般是主动触发的，由系统或者程序触发。不释放CPU，但是可能释放内存，放在外存。
* 阻塞一般是被动的，在抢占资源过程中未得到资源，释放CPU 不释放内存。
* runBlocking{} 开启运行主协程。
* job.join() 等待协程结束。
* 在作用域里面可以使用launch{} 开启子协程。
* 在作用域协程中，只有当所有子协程都执行完毕才会结束。
## 资料
* [android kotlin 协程](https://developer.android.google.cn/kotlin/coroutines?hl=zh-cn)
* [android kotlin 协程由浅入深](https://www.jianshu.com/p/301bacbda239)
* [kotlin 协程](https://kotlinlang.org/docs/coroutines-overview.html)
* [CSDN kotlin协程](https://blog.csdn.net/qq_41811862/article/details/120808850)

根据信息，我们知道，这个调调在Android上，需要导包。并不是基础库的内容。
````aidl
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
````

# 正文
## 简单DEMO
### GlobalScope.launch
这种方式就直接切换到协程里面去了。
````aidl
    fun demo(context: Context){
        GlobalScope.launch {
            Toast.makeText(context,"launch",Toast.LENGTH_SHORT).show()
        }
    }
````
### runBlocking 
````aidl
 fun demo3()= runBlocking {
        launch{
            Log.e(TAG, "demo3: " )
        }
    }
````
### runBlocking 多个协程
````aidl
   fun demo3()= runBlocking {
        launch{
            Log.e(TAG, "demo3: 2" )
        }
        launch{
            Log.e(TAG, "demo3: 1" )
        }
    }
````
## 
# 结束 