## app 启动流程
* activityThread（handler ） main  程序入口。
* 一个应用，就是一个进程。
* applicationThread activityTread 通过它获取AMS
* instrumenttation 用于启动activity或者 Application等包含生命周期方法的调用。
![app 启动流程图](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/App%E5%90%AF%E5%8A%A8%E6%B5%81%E7%A8%8B.jpg)

* activityStackSupervisor activity的堆栈。
* activityRecord activity 信息存储类。
* clientTransaction activity 启动事务。
* launchActivityItem  

## activity 启动流程

## View 绘制流程
![activity解析 xml 流程图](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/Activity%E7%9A%84XML%E8%A7%A3%E6%9E%90%E5%B8%83%E5%B1%80.jpg)
![UI具体绘制流程图](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/UI%E7%9A%84%E5%85%B7%E4%BD%93%E7%BB%98%E5%88%B6%E6%B5%81%E7%A8%8B.jpg)
* phoneWindow 显示窗口唯一的实现类。
    * DecorView 窗口的顶层视图。
* AppcompatDelegateImpl  appCompatActivity 的窗口
* setContentView 主要作用是解析xml 文件，并且添加到窗口的根view上。
* windowMangerImpl 窗口管理器的实现类。用于View的添加到窗口上。
* windowMangerGlobal 
* requestLayout 重新测量绘制view。
* measure()  测量。
* onDraw() 绘制。
* 界面的测量 绘制都是在Resume 中执行的。





