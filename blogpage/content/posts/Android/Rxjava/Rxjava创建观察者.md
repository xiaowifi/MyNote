

# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>
# 观察者创建 observer
> 2.0 import io.reactivex.Observer; 观察者用于接收 被观察者提供的数据信息。暂时不清楚被观察者调用其他观察者的逻辑。
* new Observer<泛型>(){};需要需要实现方法。Android上网络请求通常使用这个就好了。
# 其他观察者
 其他观察者 [2.2.20 提供观察者 位于io.reactivex.functions 包下 ](http://reactivex.io/RxJava/2.x/javadoc/)

- [*Action*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Action.html)
- [*BiConsumer*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/BiConsumer.html)
- [*BiFunction*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/BiFunction.html)
- [*BiPredicate*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/BiPredicate.html)
- [*BooleanSupplier*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/BooleanSupplier.html)
- [*Cancellable*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Cancellable.html)
- [*Consumer*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Consumer.html)
- [*Function*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Function.html)
- [*Function3*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Function3.html)
- [*Function4*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Function4.html)
- [*Function5*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Function5.html)
- [*Function6*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Function6.html)
- [*Function7*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Function7.html)
- [*Function8*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Function8.html)
- [*Function9*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Function9.html)
- [*IntFunction*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/IntFunction.html)
- [*LongConsumer*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/LongConsumer.html)
- [*Predicate*](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/functions/Predicate.html)

## action 
一个类似于Runnable的功能接口，但允许抛出一个已检查的异常。
````aidl


````

