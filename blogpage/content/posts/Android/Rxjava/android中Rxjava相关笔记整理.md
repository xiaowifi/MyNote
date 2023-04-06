
# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>
[Rxjava javaDoc地址](http://reactivex.io/RxJava/2.x/javadoc/)
# 正文

## 被观察者 

## 创建可观察物

产生新的Observable的运算符。

- [**`Create`**](http://reactivex.io/documentation/operators/create.html) —通过编程调用观察者方法从头开始创建Observable
- [**`Defer`**](http://reactivex.io/documentation/operators/defer.html) -在观察者订阅之前不要创建Observable，并为每个观察者创建一个新的Observable
- [**`Empty`/ `Never`/`Throw`**](http://reactivex.io/documentation/operators/empty-never-throw.html) -创建具有非常精确和限制行为观测量
- [**`From`**](http://reactivex.io/documentation/operators/from.html) —将其他一些对象或数据结构转换为可观察的
- [**`Interval`**](http://reactivex.io/documentation/operators/interval.html) —创建一个Observable，它发出以特定时间间隔隔开的整数序列
- [**`Just`**](http://reactivex.io/documentation/operators/just.html) —将一个对象或一组对象转换为发出该对象或那些对象的Observable
- [**`Range`**](http://reactivex.io/documentation/operators/range.html) —创建一个Observable，它发出一系列连续整数
- [**`Repeat`**](http://reactivex.io/documentation/operators/repeat.html) —创建一个Observable，它反复发出特定的项目或项目序列
- [**`Start`**](http://reactivex.io/documentation/operators/start.html) —创建一个Observable，它发出函数的返回值
- [**`Timer`**](http://reactivex.io/documentation/operators/timer.html) —创建一个Observable，它在给定的延迟后发出单个项目

## 转换可观察物

转换由Observable发出的项目的运算符。

- [**`Buffer`**](http://reactivex.io/documentation/operators/buffer.html) —定期将来自一个Observable的项目收集成束，然后发出这些束，而不是一次发出一个
- [**`FlatMap`**](http://reactivex.io/documentation/operators/flatmap.html) —将可观察对象发出的项目转换为可观察项目，然后将这些项目的排放平展为单个可观察项目
- [**`GroupBy`**](http://reactivex.io/documentation/operators/groupby.html) —将一个Observable划分为一组Observable，它们分别与原始Observable发射一组不同的项，并按key进行组织
- [**`Map`**](http://reactivex.io/documentation/operators/map.html) —通过对每个项目应用函数来转换Observable发出的项目
- [**`Scan`**](http://reactivex.io/documentation/operators/scan.html) —将一个函数依次应用于Observable发出的每个项目，并发出每个连续的值
- [**`Window`**](http://reactivex.io/documentation/operators/window.html) —定期将项目从“可观察”窗口细分为“可观察”窗口，然后发出这些窗口，而不是一次发出一个窗口

## 过滤可观察物

有选择地从Observable源发出项目的运算符。

- [**`Debounce`**](http://reactivex.io/documentation/operators/debounce.html) —仅在经过特定时间跨度时才从Observable中发出一项，而不发出另一项
- [**`Distinct`**](http://reactivex.io/documentation/operators/distinct.html) -抑制可观察对象发出的重复项
- [**`ElementAt`**](http://reactivex.io/documentation/operators/elementat.html) —仅发射可观察对象发射的项目*n*
- [**`Filter`**](http://reactivex.io/documentation/operators/filter.html) —仅从可观察对象中发出通过谓词测试的项
- [**`First`**](http://reactivex.io/documentation/operators/first.html) —仅从Observable发出第一项或满足条件的第一项
- [**`IgnoreElements`**](http://reactivex.io/documentation/operators/ignoreelements.html) —不要从Observable发出任何项目，而是镜像其终止通知
- [**`Last`**](http://reactivex.io/documentation/operators/last.html) —只发射可观察对象发射的最后一个项目
- [**`Sample`**](http://reactivex.io/documentation/operators/sample.html) —定期发射Observable发射的最新项目
- [**`Skip`**](http://reactivex.io/documentation/operators/skip.html) —抑制Observable发出的前*n个*项目
- [**`SkipLast`**](http://reactivex.io/documentation/operators/skiplast.html) —抑制Observable发出的最后*n个*项目
- [**`Take`**](http://reactivex.io/documentation/operators/take.html) —仅发射可观察对象发射的前*n个*项目
- [**`TakeLast`**](http://reactivex.io/documentation/operators/takelast.html) —只发射可观察对象发射的最后*n个*项目

## 结合可观察物

与多个源Observables一起创建单个Observable的运算符

- [**`And`/ `Then`/`When`**](http://reactivex.io/documentation/operators/and-then-when.html) -联合组由两个或更多发射观测量项借助于`Pattern`与`Plan`中介
- [**`CombineLatest`**](http://reactivex.io/documentation/operators/combinelatest.html) —当两个Observable之一发射一个项目时，通过指定的函数合并每个Observable发射的最新项目，并根据此函数的结果发射项目
- [**`Join`**](http://reactivex.io/documentation/operators/join.html) —在根据另一个可观察对象发出的项目定义的时间窗口中，只要发射了一个可观察对象的项目，则合并两个可观察对象发出的项目
- [**`Merge`**](http://reactivex.io/documentation/operators/merge.html) -通过合并排放量将多个可观测值合并为一个
- [**`StartWith`**](http://reactivex.io/documentation/operators/startwith.html) —在开始从源中发出项目之前，发出指定的项目序列
- [**`Switch`**](http://reactivex.io/documentation/operators/switch.html) —将发出Observable的Observable转换为发出可观察到的最新事物的单个Observable
- [**`Zip`**](http://reactivex.io/documentation/operators/zip.html) —通过指定的函数将多个可观测对象的发射合并在一起，并根据此函数的结果为每个组合发射单个项目

## 错误处理运算符

有助于从Observable的错误通知中恢复的运算符

- [**`Catch`**](http://reactivex.io/documentation/operators/catch.html)-`onError`通过继续执行顺序而没有错误地从通知中恢复
- [**`Retry`**](http://reactivex.io/documentation/operators/retry.html)—如果源Observable发送了`onError`通知，请重新订阅该通知，以希望它可以完成而不会出错

## 可观察的公用事业运营商

一个有用的运算符的工具箱，用于处理Observables

- [**`Delay`**](http://reactivex.io/documentation/operators/delay.html) —将排放量从可观察的时间向前移特定量
- [**`Do`**](http://reactivex.io/documentation/operators/do.html) -注册一项行动以应对各种可观察到的生命周期事件
- [**`Materialize`/`Dematerialize`**](http://reactivex.io/documentation/operators/materialize-dematerialize.html) —表示发出的项目和作为发出的项目发送的通知，或者逆转此过程
- [**`ObserveOn`**](http://reactivex.io/documentation/operators/observeon.html) -指定观察者将在其上观察此Observable的调度程序
- [**`Serialize`**](http://reactivex.io/documentation/operators/serialize.html) -强制Observable进行序列化调用并保持良好行为
- [**`Subscribe`**](http://reactivex.io/documentation/operators/subscribe.html) -根据观测对象的排放和通知进行操作
- [**`SubscribeOn`**](http://reactivex.io/documentation/operators/subscribeon.html) —指定Observable订阅时应使用的调度程序
- [**`TimeInterval`**](http://reactivex.io/documentation/operators/timeinterval.html) —将发射项目的Observable转换为发射项目，以指示两次发射之间所经过的时间
- [**`Timeout`**](http://reactivex.io/documentation/operators/timeout.html) —镜像源Observable，但如果经过特定时间但没有任何发射项，则发出错误通知
- [**`Timestamp`**](http://reactivex.io/documentation/operators/timestamp.html) —将时间戳记附加到Observable发出的每个项目
- [**`Using`**](http://reactivex.io/documentation/operators/using.html) -创建与可观察对象具有相同使用寿命的可使用资源

## 条件运算符和布尔运算符

评估一个或多个可观察对象或可观察对象发出的项目的运算符

- [**`All`**](http://reactivex.io/documentation/operators/all.html) —确定可观察对象发出的所有项目是否都满足某些条件
- [**`Amb`**](http://reactivex.io/documentation/operators/amb.html) —给定两个或多个源Observable，仅从这些Observable中的第一个发出所有项目以发出一个项目
- [**`Contains`**](http://reactivex.io/documentation/operators/contains.html) —确定一个Observable是否发出特定项目
- [**`DefaultIfEmpty`**](http://reactivex.io/documentation/operators/defaultifempty.html) —从源Observable发射项目，如果源Observable不发射任何东西，则为默认项目
- [**`SequenceEqual`**](http://reactivex.io/documentation/operators/sequenceequal.html) —确定两个可观察对象是否发出相同的项目序列
- [**`SkipUntil`**](http://reactivex.io/documentation/operators/skipuntil.html) —丢弃可观察对象发出的物品，直到第二个可观察对象发出物品为止
- [**`SkipWhile`**](http://reactivex.io/documentation/operators/skipwhile.html) —丢弃Observable发出的项目，直到指定条件为假
- [**`TakeUntil`**](http://reactivex.io/documentation/operators/takeuntil.html) —在第二个Observable发出项目或终止后，丢弃Observable发出的项目
- [**`TakeWhile`**](http://reactivex.io/documentation/operators/takewhile.html) —在指定条件为假之后，丢弃Observable发出的项目

## 数学运算符和聚合运算符

对Observable发出的整个项目序列进行操作的运算符

- [**`Average`**](http://reactivex.io/documentation/operators/average.html) —计算Observable发出的数字的平均值，并发出该平均值
- [**`Concat`**](http://reactivex.io/documentation/operators/concat.html) —从两个或多个可观测对象发射的发射，而不会相互干扰
- [**`Count`**](http://reactivex.io/documentation/operators/count.html) —计算源Observable发出的项目数，仅发出此值
- [**`Max`**](http://reactivex.io/documentation/operators/max.html) —确定并发出Observable发出的最大价值的物品
- [**`Min`**](http://reactivex.io/documentation/operators/min.html) —确定并发出Observable发出的最小值项
- [**`Reduce`**](http://reactivex.io/documentation/operators/reduce.html) —将函数依次应用于Observable发出的每个项目，并发出最终值
- [**`Sum`**](http://reactivex.io/documentation/operators/sum.html) —计算一个Observable发出的数字的总和，并发出该总和

## 背压运算符

- [**背压运算符**](http://reactivex.io/documentation/operators/backpressure.html)-处理可观察物的策略，可观察物的产生比观察者消耗它们的速度更快

## 可连接的可观察算子

具有更精确控制的订阅动态的专业可观察物

- [**`Connect`**](http://reactivex.io/documentation/operators/connect.html) -指示可连接的Observable开始向其订阅者发送项目
- [**`Publish`**](http://reactivex.io/documentation/operators/publish.html) —将普通的Observable转换为可连接的Observable
- [**`RefCount`**](http://reactivex.io/documentation/operators/refcount.html) -使Connectable Observable表现得像普通的Observable
- [**`Replay`**](http://reactivex.io/documentation/operators/replay.html) —确保所有观察者看到相同的发射项目序列，即使他们在Observable开始发射项目之后进行订阅

## 运营商转换可观察物

- [**`To`**](http://reactivex.io/documentation/operators/to.html) —将一个Observable转换为另一个对象或数据结构

### Observable
> 2.0 import io.reactivex.Observable; 
> [超级详细的官网地址](http://reactivex.io/documentation/operators.html#utility)

### Flowable 
这个是 java 版本中提供的，感觉需要和lambdas表达式一起使用，否则会代码爆红。创建方法感觉和Observable 差不多。但是Android中无法调用这个，无法通过编译。
> Flowable.just("").observeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
> 找不到org.reactivestreams.Publisher的类文件

只有一句话也不行。
>  Flowable.just("Hello world").subscribe(System.out::println);
  找不到org.reactivestreams.Publisher的类文件
> 但是通过百度，查找诸位大佬的博客，然后整到了一句这个。如果上游发射的很快而下游处理的很慢，会怎样呢？
> 将会产生很多下游没来得及处理的数据，这些数据既不会丢失，也不会被垃圾回收机制回收，而是存放在一个异步缓存池中，如果缓存池中的数据一直得不到处理，越积越多，最后就会造成内存溢出，这便是Rxjava中的背压问题。
> Flowable类实现了反应式流模式，并提供了工厂方法，中间运算符以及使用反应式数据流的能力.
````aidl
    Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
````

### Single  
RxJava（及其衍生产品，如RxGroovy和RxScala）已经开发了一个称为“单一”的 Observable变体。[官方巨详细文档地址](http://reactivex.io/documentation/single.html) <br>
Single就像是一个Observable，但是不发出一系列值（从无到无到无限），它总是发出一个值或错误通知。
因此，您无需使用三种方法来响应来自Observable的通知（onNext，onError和onCompleted）来订阅Single，而是仅使用两种方法进行订阅：
* onSuccess()
    Single通过此方法将Single发出的唯一项目
* onError
### Subject 
一种在ReactiveX的某些实现中可用的桥梁或代理，它既充当观察者又充当Observable。因为它是观察者，所以可以订阅一个或多个Observable，并且因为它是Observable，所以可以通过释放观察到的项来传递它们，也可以发出新的项。
因为一个主体订阅了一个Observable，它将触发Observable开始发出项目（如果Observable是“冷”的，也就是说，如果它在开始发出项目之前等待订阅）。这可以使最终的Subject成为原始“ cold”可观察到的“ hot”可观察到的变体。
### Observable中subscribe 方法重载
````
    // 表示观察者不对被观察者发送的事件作出任何响应（但被观察者还是可以继续发送事件）
    public final Disposable subscribe(Consumer<? super T> onNext) {}
    // 表示观察者只对被观察者发送的Next事件作出响应
    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {} 
    // 表示观察者只对被观察者发送的Next事件 & Error事件作出响应
    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {}
    // 表示观察者只对被观察者发送的Next事件、Error事件 & Complete事件作出响应
    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {}
    // 表示观察者只对被观察者发送的Next事件、Error事件 、Complete事件 & onSubscribe事件作出响应
    public final void subscribe(Observer<? super T> observer) {}
    // 表示观察者对被观察者发送的任何事件都作出响应
````

## 观察者创建 observer
> 2.0 import io.reactivex.Observer; 观察者用于接收 被观察者提供的数据信息。暂时不清楚被观察者调用其他观察者的逻辑。
* new Observer<泛型>(){};需要需要实现方法。Android上网络请求通常使用这个就好了。
* 其他观察者 [2.2.20 提供观察者 位于io.reactivex.functions 包下 ](http://reactivex.io/RxJava/2.x/javadoc/)

## 运行线程
在Android 中主线程不建议进行耗时操作，通常耗时操作都是在子线程中进行的。而Rxjava 也提供了线程切换能力。通常上讲 观察者和被观察者创建线程就是他们的运行线程。
> 使用包 : import io.reactivex.schedulers.Schedulers;

| 类型 | 含义 | 应用场景 |
| :-----| ----: | :----: |
| Schedulers.immediate() | 当前线程 = 不指定线程 | 默认 |
| AndroidSchedulers.mainThread() | Android主线程 | 操作UI |
| Schedulers.newThread() | 常规新线程 | 耗时等操作 |
| Schedulers.io() | io操作线程 | 网络请求、读写文件等io密集型操作 |
| Schedulers.computation() | CPU计算操作线程 | 大量计算操作 |
### 方法
* subscribeOn() 传入上述类型表示 被观察者运行线程。
* observeOn() 传入上述类型表示 观察者运行线程
> 多次指定被观察者生产事件线程，只有第一次有效果，其余指定线程无效。
> 多次指定观察者接收线程，每次指定均生效，每指定一次，就切换一次。

## Scheduler


## 观察者与被观察者连接关系
使用方法subscribe() 进行连接。<br>
通用连接格式是：被观察者(Observable). 被观察者运行线程(subscribeOn()).观察者运行线程(observeOn()).订阅（subscribe(观察者(observer))）<br>
### 中断连接
在观察者observer的开始连接回调方法:onSubscribe(Disposable d) 中，Disposable可用于中断连接，使用方法:<br>
disposable.dispose();// 切断连接<br>
### 多个Disposable
如果在非正确生命周期中出现UI调用，可能导致崩溃，对于多个Disposable，可以使用 CompositeDisposable，统一处理。
``` 
// 添加Disposable到CompositeDisposable容器
CompositeDisposable.add()
// 清空CompositeDisposable容器
CompositeDisposable.clear() 
// 这个需要尝试，这个clear是统一中断连接的意思吗？
```
### 延时


