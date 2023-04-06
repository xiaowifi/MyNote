
# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>
[Rxjava javaDoc地址](http://reactivex.io/RxJava/2.x/javadoc/)
# 正文
> 如果上游发射的很快而下游处理的很慢，会怎样呢？
> 将会产生很多下游没来得及处理的数据，这些数据既不会丢失，也不会被垃圾回收机制回收，而是存放在一个异步缓存池中，如果缓存池中的数据一直得不到处理，越积越多，最后就会造成内存溢出，这便是Rxjava中的背压问题。
> Flowable类实现了反应式流模式，并提供了工厂方法，中间运算符以及使用反应式数据流的能力.
````aidl
Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
                System.out.print("onSubscribe" + s);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.print("onNext" + integer);
            }

            @Override
            public void onError(Throwable t) {
                System.out.print(t);
            }

            @Override
            public void onComplete() {
                System.out.print("onComplete");
            }
        });
````
## 笔记
通过上面的代码可以看出。这个和普通的观察者使用没有多少区别。
- 1 创建的Flowable的时候需要传递一个BackpressureStrategy中的枚举。Flowable的异步缓存池不同于Observable，Observable的异步缓存池没有大小限制，可以无限制向里添加数据，直至OOM,而Flowable的异步缓存池有个固定容量，其大小为128。
    BackpressureStrategy的作用便是用来设置Flowable通过异步缓存池存储数据的策略。
    - BackpressureStrategy.MISSING  写入OnNext事件时不会进行任何缓冲或删除。 *下游必须处理任何溢出
    - BackpressureStrategy.ERROR 发出信号MissingBackpressureException，以防下游无法跟上。
    - BackpressureStrategy.BUFFER 缓冲<em> all </ em> onNext值，直到下游使用它。
    - BackpressureStrategy.DROP Drops the most recent onNext value if the downstream can't keep up.
    - BackpressureStrategy.LATEST 仅保留最新的onNext值，如果*不能跟上，则覆盖以前的任何值。
- 2 onSubscribe回调的参数不是Disposable而是Subscription 
    - request(long n) 在通过此方法发出需求信号之前，{@ link Publisher}不会发送任何事件。所以说这个方法是必定需要回调的。
    - cancel() 请求{@link Publisher}停止发送数据并清理资源。 * <p> *调用取消后，仍可能发送数据以满足先前发出的信号需求。
- 3 Flowable发射数据时，使用的发射器是FlowableEmitter而不是ObservableEmitter

### BackpressureStrategy.ERROR
在此策略下，如果放入Flowable的异步缓存池中的数据超限了，则会抛出MissingBackpressureException异常。因为只能缓冲128条信息，如果发送超过了128条就会抛出MissingBackpressureException异常。
还有一点，flowable 发送消息是不管下面设置要接收多少条的，如果发送128条，但是只需要一条，128条还是会存在缓存中。
### BackpressureStrategy.DROP
在此策略下，如果Flowable的异步缓存池满了，会丢掉将要放入缓存池中的数据。缓存池中数据的清理，并不是Subscriber接收一条，便清理一条，而是每累积到95条清理一次。也就是Subscriber接收到第96条数据时，缓存池才开始清理数据，之后Flowable发射的数据才得以放入。
### BackpressureStrategy.LATEST
与Drop策略一样，如果缓存池满了，会丢掉将要放入缓存池中的数据，不同的是，不管缓存池的状态如何，LATEST都会将最后一条数据强行放入缓存池中。
### BackpressureStrategy.BUFFER
此策略下，Flowable的异步缓存池同Observable的一样，没有固定大小，可以无限制向里添加数据，不会抛出MissingBackpressureException异常，但会导致OOM。和使用Observalbe时一样，都会导致内存剧增，最后导致OOM,不同的是使用Flowable内存增长的速度要慢得多，那是因为基于Flowable发射的数据流，以及对数据加工处理的各操作符都添加了背压支持，附加了额外的逻辑，其运行效率要比Observable低得多。
### BackpressureStrategy.MISSING
此策略表示，通过Create方法创建的Flowable没有指定背压策略，不会对通过OnNext发射的数据做缓存或丢弃处理，需要下游通过背压操作符（onBackpressureBuffer()/onBackpressureDrop()/onBackpressureLatest()）指定背压策略。
onBackpressureXXX背压操作符
Flowable除了通过create创建的时候指定背压策略，也可以在通过其它创建操作符just，fromArray等创建后通过背压操作符指定背压策略。
onBackpressureBuffer()对应BackpressureStrategy.BUFFER 
onBackpressureDrop()对应BackpressureStrategy.DROP 
onBackpressureLatest()对应BackpressureStrategy.LATEST  

### Subscription
Subscription与Disposable均是观察者与可观察对象建立订阅状态后回调回来的参数，如同通过Disposable的dispose()方法可以取消Observer与Oberverable的订阅关系一样，通过Subscription的cancel()方法也可以取消Subscriber与Flowable的订阅关系。
不同的是接口Subscription中多了一个方法request(long n)，如上面代码中的：
s.request(Long.MAX_VALUE);
Flowable在设计的时候，采用了一种新的思路——响应式拉取方式，来设置下游对数据的请求数量，上游可以根据下游的需求量，按需发送数据。
通过s.request(2);设置Subscriber的数据请求量为2条，超出其请求范围之外的数据则没有接收。
两次需求累加后的数量。
### FlowableEmitter
longrequested();
我们可以通过这个方法来获取当前未完成的请求数量。
其实不论下游通过s.request();设置多少请求量，我们在上游获取到的初始未完成请求数量都是128。
Flowable有一个异步缓存池，上游发射的数据，先放到异步缓存池中，再由异步缓存池交给下游。所以上游在发射数据时，首先需要考虑的不是下游的数据请求量，而是缓存池中能不能放得下，否则在缓存池满的情况下依然会导致数据遗失或者背压异常。如果缓存池可以放得下，那就发送，至于是否超出了下游的数据需求量，可以在缓存池向下游传递数据时，再作判断，如果未超出，则将缓存池中的数据传递给下游，如果超出了，则不传递。
通过e.requested()获取到的上游当前未完成请求数量并不是一直递减的，在递减到33时，又回升到了128.而回升的时机正好是在下游接收了96条数据之后。我们之前说过，异步缓存池中的数据并不是向下游发射一条便清理一条，而是每等累积到95条时，清理一次。通过e.requested()获取到的值，正是在异步缓存池清理数据时，回升的。也就是，异步缓存池每次清理后，有剩余的空间时，都会导致上游未完成请求数量的回升，这样既不会引发背压异常，也不会导致数据遗失。
上游在发送数据的时候并不需要考虑下游需不需要，而只需要考虑异步缓存池中是否放得下，放得下便发，放不下便暂停。所以，通过e.requested()获取到的值，并不是下游真正的数据请求数量，而是异步缓存池中可放入数据的数量。数据放入缓存池中后，再由缓存池按照下游的数据请求量向下传递，待到传递完的数据累积到95条之后，将其清除，腾出空间存放新的数据。如果下游处理数据缓慢，则缓存池向下游传递数据的速度也相应变慢，进而没有传递完的数据可清除，也就没有足够的空间存放新的数据，上游通过e.requested()获取的值也就变成了0，如果此时，再发送数据的话，则会根据BackpressureStrategy背压策略的不同，抛出MissingBackpressureException异常，或者丢掉这条数据。

所以上游只需要在e.requested()等于0时，暂停发射数据，便可解决背压问题，然后接收到数据的时候向添加的缓存中增加1。



