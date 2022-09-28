# 前言

Hint 使用的是dagger2实现。主要是用于IOC注入：
IOC注入是将原来由程序代码中主动获取的资源转变为由第三方获取，并使原来主动的代码转换为被动接收的方式，以达到解耦的效果。称为控制反转。

## 资料
* [dagger2](https://github.com/google/dagger)
* [神兵利器Dagger2一站式全解（详细总结）](https://blog.csdn.net/valada/article/details/106225715)

# 正文

* 注入的位置不能用多态。
* 默认对象是不是同一个，为了使得对象使用同一个，而不使用单例可以使用 @singleton 
* 通过@singleton 的单例是有生命周期的，绑定到那个class就和当前生命周期绑定。如果要app 单例就将component 做成单例去注入。同时因为doubleCheck的get函数的原因，所以说线程安全。

