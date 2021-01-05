+++
date = "2020-12-30"
title = "Android中关于Rxjava Flowable相关笔记整理(转载)"
description = "Android中关于Rxjava Flowable 相关笔记整理(转载)"
tags = [ "android","Rxjava"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
thumbnail = "https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201230114639.png"
+++
# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>
[Rxjava javaDoc地址](http://reactivex.io/RxJava/2.x/javadoc/)

# 正文

数据流发射，处理，响应可能在各自的线程中独立进行，上游在发射数据的时候，不知道下游是否处理完，也不会等下游处理完之后再发射。

这样，如果上游发射的很快而下游处理的很慢，会怎样呢？

将会产生很多下游没来得及处理的数据，这些数据既不会丢失，也不会被垃圾回收机制回收，而是存放在一个异步缓存池中，如果缓存池中的数据一直得不到处理，越积越多，最后就会造成内存溢出，这便是Rxjava中的背压问题。

例如，运行以下代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-565306f01fa1bc24.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/711/format/webp)

demo1.jpg

创建一个可观察对象Obervable在Schedulers.newThread()()的线程中不断发送数据，而观察者Observer在Schedulers.newThread()的另一个线程中每隔5秒接收一条数据，运行后，查看内存使用如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-d68c51355a9d8e2d.gif?imageMogr2/auto-orient/strip|imageView2/2/w/552/format/webp)

backpressure.gif

由于上下游分别在各自的线程中独立处理数据（如果上下游在同一线程中，下游对数据的处理会堵塞上游数据的发送，上游发送一条数据后会等下游处理完之后再发送下一条），而上游发送数据速度远大于下游接收数据的速度，造成上下游流速不均，导致数据累计，最后引起内存溢出。

Flowable

Flowable是为了解决背压（backpressure）问题，而在Observable的基础上优化后的产物，与Observable不是同一组观察者模式下的成员，Flowable是Publisher与Subscriber这一组观察者模式中Publisher的典型实现，Observable是ObservableSource/Observer这一组观察者模式中ObservableSource的典型实现；

所以在使用Flowable的时候，可观察对象不再是Observable,而是Flowable;观察者不再是Observer，而是Subscriber。Flowable与Subscriber之间依然通过subscribe()进行关联。

有些朋友可能会想，既然Flowable是在Observable的基础上优化后的产物，Observable能解决的问题Flowable都能进行解决，何不抛弃Observable而只用Flowable呢。其实，这是万万不可的，他们各有自己的优势和不足。

由于基于Flowable发射的数据流，以及对数据加工处理的各操作符都添加了背压支持，附加了额外的逻辑，其运行效率要比Observable低得多。

因为只有上下游运行在各自的线程中，且上游发射数据速度大于下游接收处理数据的速度时，才会产生背压问题。

所以，如果能够确定上下游在同一个线程中工作，或者上下游工作在不同的线程中，而下游处理数据的速度高于上游发射数据的速度，则不会产生背压问题，就没有必要使用Flowable，以免影响性能。

通过Flowable发射处理数据流的基础代码如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-302cfc5e2f73a565.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/905/format/webp)

demo2.jpg

执行结果如下：

System.out: 发射----> 1System.out: 发射----> 2System.out: 发射----> 3System.out: 发射----> 完成System.out: 接收----> 1System.out: 接收----> 2System.out: 接收----> 3System.out: 接收----> 完成

我们发现运行结果与Observerable没有区别，但是的代码中，除了为上下游指定各自的运行线程外，还有三点不同

一、create方法中多了一个**BackpressureStrategy**类型的参数。

二、onSubscribe回调的参数不是Disposable而是**Subscription**，多了行代码：

s.request(Long.MAX_VALUE);

三、Flowable发射数据时，使用的发射器是**FlowableEmitter**而不是ObservableEmitter

BackpressureStrategy背压策略

在Flowable的基础创建方法create中多了一个BackpressureStrategy类型的参数，

BackpressureStrategy是个枚举，源码如下：

publicenumBackpressureStrategy {  ERROR,BUFFER,DROP,LATEST,MISSING}

其作用是什么呢？

Flowable的异步缓存池不同于Observable，Observable的异步缓存池没有大小限制，可以无限制向里添加数据，直至OOM,而Flowable的异步缓存池有个固定容量，其大小为128。

BackpressureStrategy的作用便是用来设置Flowable通过异步缓存池存储数据的策略。

ERROR

在此策略下，如果放入Flowable的异步缓存池中的数据超限了，则会抛出MissingBackpressureException异常。

运行如下代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-1702091139744974.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/696/format/webp)

demo3.jpg

Flowable发射129条数据，Subscriber在睡10秒之后再开始接收，运行后会发现控制台打印如下异常：

W/System.err:io.reactivex.exceptions.MissingBackpressureException:create:couldnotemit value due to lack of requestsW/System.err:at io.reactivex.internal.operators.flowable.FlowableCreate$ErrorAsyncEmitter.onOverflow(FlowableCreate.java:411)W/System.err:at io.reactivex.internal.operators.flowable.FlowableCreate$NoOverflowBaseAsyncEmitter.onNext(FlowableCreate.java:377)W/System.err:at net.fbi.rxjava2.RxJava2Demo$6.subscribe(RxJava2Demo.java:103)W/System.err:at io.reactivex.internal.operators.flowable.FlowableCreate.subscribeActual(FlowableCreate.java:72)W/System.err:at io.reactivex.Flowable.subscribe(Flowable.java:12218)W/System.err:at io.reactivex.internal.operators.flowable.FlowableSubscribeOn$SubscribeOnSubscriber.run(FlowableSubscribeOn.java:82)W/System.err:at io.reactivex.internal.schedulers.ScheduledRunnable.run(ScheduledRunnable.java:59)W/System.err:at io.reactivex.internal.schedulers.ScheduledRunnable.call(ScheduledRunnable.java:51)W/System.err:at java.util.concurrent.FutureTask.run(FutureTask.java:237)W/System.err:at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:154)W/System.err:at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:269)W/System.err:at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1113)W/System.err:at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:588)W/System.err:at java.lang.Thread.run(Thread.java:818)

如果将Flowable发射数据的条数改为128，则不会出现此异常。

DROP

在此策略下，如果Flowable的异步缓存池满了，会丢掉将要放入缓存池中的数据。

运行如下代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-89131789357ff178.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/848/format/webp)

demo4.jpg

在上面代码中通过创建Flowable发射500条数据，每隔100毫秒发射一次，并记录开始发射和结束发射的时间，下游每隔300毫秒接收一次数据，运行后，控制台打印日志如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-9c3f26ad32d558a3.gif?imageMogr2/auto-orient/strip|imageView2/2/w/424/format/webp)

GIF111.gif

通过日志

![img](https:////upload-images.jianshu.io/upload_images/6773051-eaf4ad8457018c72.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/359/format/webp)

1.jpg

我们可以发现Subscriber在接收完第128条数据后，再次接收的时候已经到了288，而这之间的60条数据正是因为缓存池满了而被丢弃掉了。

那么问题来了，当Flowable在发射第129条数据的时候，Subscriber已经接收了42条数据了，第129条数据为什么没有放入缓存池中呢？日志如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-c23597bb66f951df.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/344/format/webp)

2.jpg

那是因为缓存池中数据的清理，并不是Subscriber接收一条，便清理一条，而是每累积到95条清理一次。也就是Subscriber接收到第96条数据时，缓存池才开始清理数据，之后Flowable发射的数据才得以放入。

![img](https:////upload-images.jianshu.io/upload_images/6773051-f3b96438710af53c.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/367/format/webp)

3.jpg

查看日志可以发现，Subscriber接收到第96条数据后，Flowable发射第288条数据。而第128到288之间的数据，正好处于缓存池存满的状态，而被丢弃，所以Subscriber在接收完第128条数据之后，接收到的是第288条数据，而不是第129条。

LATEST

与Drop策略一样，如果缓存池满了，会丢掉将要放入缓存池中的数据，不同的是，不管缓存池的状态如何，LATEST都会将最后一条数据强行放入缓存池中。

将上述代码中的DROP策略改为LATEST：

![img](https:////upload-images.jianshu.io/upload_images/6773051-3b4c3da66f6f3775.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/836/format/webp)

demo5.jpg

运行后日志对比如下：

DROP:

![img](https:////upload-images.jianshu.io/upload_images/6773051-731d7d2456cbc431.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/360/format/webp)

DROP.jpg

LATEST：

![img](https:////upload-images.jianshu.io/upload_images/6773051-4f559f1f819ad03b.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/355/format/webp)

LATEST.jpg

latest策略下Subscriber在接收完成之前，接收的数据是Flowable发射的最后一条数据，而Drop策略下不是。

BUFFER

此策略下，Flowable的异步缓存池同Observable的一样，没有固定大小，可以无限制向里添加数据，不会抛出MissingBackpressureException异常，但会导致OOM。

运行如下代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-10728caee49b4c06.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/702/format/webp)

demo6.jpg

查看内存使用:

![img](https:////upload-images.jianshu.io/upload_images/6773051-07576ae68d0728ea.gif?imageMogr2/auto-orient/strip|imageView2/2/w/582/format/webp)

GIF222.gif

会发现和使用Observalbe时一样，都会导致内存剧增，最后导致OOM,不同的是使用Flowable内存增长的速度要慢得多，那是因为基于Flowable发射的数据流，以及对数据加工处理的各操作符都添加了背压支持，附加了额外的逻辑，其运行效率要比Observable低得多。

MISSING

此策略表示，通过Create方法创建的Flowable没有指定背压策略，不会对通过OnNext发射的数据做缓存或丢弃处理，需要下游通过背压操作符（onBackpressureBuffer()/onBackpressureDrop()/onBackpressureLatest()）指定背压策略。

onBackpressureXXX背压操作符

Flowable除了通过create创建的时候指定背压策略，也可以在通过其它创建操作符just，fromArray等创建后通过背压操作符指定背压策略。

onBackpressureBuffer()对应BackpressureStrategy.BUFFER

onBackpressureDrop()对应BackpressureStrategy.DROP

onBackpressureLatest()对应BackpressureStrategy.LATEST

例如代码

![img](https:////upload-images.jianshu.io/upload_images/6773051-f12d70d4146dad85.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/703/format/webp)

demo7.jpg

等同于，代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-70376a02e4f20f0c.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/652/format/webp)

demo8.jpg

Subscription

Subscription与Disposable均是观察者与可观察对象建立订阅状态后回调回来的参数，如同通过Disposable的dispose()方法可以取消Observer与Oberverable的订阅关系一样，通过Subscription的cancel()方法也可以取消Subscriber与Flowable的订阅关系。

不同的是接口Subscription中多了一个方法request(long n)，如上面代码中的：

s.request(Long.MAX_VALUE);

此方法的作用是什么呢，去掉这个方法会有什么影响呢？

运行如下代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-a6f03e8db9774794.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/769/format/webp)

demo9.jpg

运行结果如下：

System.out: 发射----> 1System.out: 发射----> 2System.out: 发射----> 3System.out: 发射----> 完成

我们发现Flowable照常发送数据，而Subsriber不再接收数据。

这是因为Flowable在设计的时候，采用了一种新的思路——**响应式拉取**方式，来设置下游对数据的请求数量，上游可以根据下游的需求量，按需发送数据。

如果不显示调用request则默认下游的需求量为零，所以运行上面的代码后，上游Flowable发射的数据不会交给下游Subscriber处理。

运行如下代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-4b55196bdf93e334.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/760/format/webp)

demo10.jpg

运行结果如下：

System.out: 发射----> 1System.out: 发射----> 2System.out: 发射----> 3System.out: 发射----> 完成System.out: 接收----> 1System.out: 接收----> 2

我们发现通过s.request(2);设置Subscriber的数据请求量为2条，超出其请求范围之外的数据则没有接收。

多次调用request会产生怎样的结果呢？

运行如下代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-3f892abe9180c752.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/842/format/webp)

demo11.jpg

通过Flowable发射10条数据，在onSubscribe(Subscription s) 方法中调用两次request，运行结果如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-9fd6ff08fa332115.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/405/format/webp)

AB417C9CAC5A4BD98375240B5A5C1D6A.jpg

我们发现Subscriber总共接收了7条数据，是两次需求累加后的数量。

通过日志我们发现，上游并没有根据下游的实际需求，发送数据，而是能发送多少，就发送多少，不管下游是否需要。

而且超出下游需求之外的数据，仍然放到了异步缓存池中。这点我们可以通过以下代码来验证：

![img](https:////upload-images.jianshu.io/upload_images/6773051-a866c3c464639ca3.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/695/format/webp)

demo12.jpg

通过Flowable发射130条数据，通过s.request(1)设置下游的数据请求量为1条，设置缓存策略为BackpressureStrategy.ERROR，如果异步缓存池超限，会导致MissingBackpressureException异常。

运行之后，日志如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-f4c46e603e4dd906.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/845/format/webp)

MissingBackpressureException.jpg

久违的异常出现了，所以超出下游需求之外的数据，仍然放到了异步缓存池中，并导致缓存池溢出。

那么上游如何才能按照下游的请求数量发送数据呢，

虽然通过request可以设置下游的请求数量，但是上游并没有获取到这个数量，如何获取呢？

这便需要用到Flowable与Observable的第三点区别，Flowable特有的发射器FlowableEmitter

FlowableEmitter

flowable的发射器FlowableEmitter与observable的发射器ObservableEmitter均继承自Emitter（Emitter在教程二中已经说过了）

比较两者源码可以发现；

publicinterfaceObservableEmitterextendsEmitter{voidsetDisposable(Disposable d);voidsetCancellable(Cancellable c);booleanisDisposed();ObservableEmitterserialize();}

与

publicinterfaceFlowableEmitterextendsEmitter{voidsetDisposable(Disposable s);voidsetCancellable(Cancellable c);longrequested();booleanisCancelled();FlowableEmitterserialize();}

接口FlowableEmitter中多了一个方法

longrequested();

我们可以通过这个方法来获取当前未完成的请求数量，

运行下面的代码，这次我们要先丧失一下原则，虽然我们之前说过同步状态下不使用Flowable，但是这次我们需要先看一下同步状态下情况。

![img](https:////upload-images.jianshu.io/upload_images/6773051-cc131867e57be227.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/851/format/webp)

demo13.jpg

打印日志如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-587f288da9916ac5.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/308/format/webp)

4.jpg

通过日志我们发现， 通过*e.requested()*获取到的是一个动态的值，会随着下游已经接收的数据的数量而递减。

在上面的代码中，我们没有指定上下游的线程，上下游运行在同一线程中。

这与我们之前提到的，同步状态下不使用Flowable相违背。那是因为异步情况下*e.requested()*的值太复杂，必须通过同步情况过渡一下才能说得明白。

我们在上面代码的基础上，给上下游指定独立的线程，代码如下

![img](https:////upload-images.jianshu.io/upload_images/6773051-d1dcaa125e629f9a.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/856/format/webp)

demo14.jpg

运行后日志如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-bd03a5018b599393.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/386/format/webp)

log5.jpg

虽然我们指定了下游的数据请求量为3，但是我们在上游获取未完成请求数量的时候，并不是3，而是128。难道上游有个最小未完成请求数量？只要下游设置的数据请求量小于128，上游获取到的都是128？

带着这个疑问，我们试一下当下游的数据请求量为500，大于128时的情况。

![img](https:////upload-images.jianshu.io/upload_images/6773051-10fc21d77c3a62e8.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/844/format/webp)

demo15.jpg

运行日志如下;

![img](https:////upload-images.jianshu.io/upload_images/6773051-b7bff24f0a6e7b4e.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/414/format/webp)

log6.jpg

结果还是128.

其实不论下游通过*s.request();*设置多少请求量，我们在上游获取到的初始未完成请求数量都是128。

这是为啥呢？

还记得之前我们说过，Flowable有一个异步缓存池，上游发射的数据，先放到异步缓存池中，再由异步缓存池交给下游。所以上游在发射数据时，首先需要考虑的不是下游的数据请求量，而是缓存池中能不能放得下，否则在缓存池满的情况下依然会导致数据遗失或者背压异常。如果缓存池可以放得下，那就发送，至于是否超出了下游的数据需求量，可以在缓存池向下游传递数据时，再作判断，如果未超出，则将缓存池中的数据传递给下游，如果超出了，则不传递。

如果下游对数据的需求量超过缓存池的大小，而上游能获取到的最大需求量是128，上游对超出128的需求量是怎么获取到的呢？

带着这个疑问，我们运行一下，下面的代码，上游发送150个数据，下游也需要150个数据。

![img](https:////upload-images.jianshu.io/upload_images/6773051-ee5a69964c7addd0.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/847/format/webp)

demo16.jpg

截取部分日志如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-648e811834111d88.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/377/format/webp)

log7.jpg

我们发现通过*e.requested()*获取到的上游当前未完成请求数量并不是一直递减的，在递减到33时，又回升到了128.而回升的时机正好是在下游接收了96条数据之后。我们之前说过，异步缓存池中的数据并不是向下游发射一条便清理一条，而是每等累积到95条时，清理一次。通过*e.requested()*获取到的值，正是在异步缓存池清理数据时，回升的。也就是，异步缓存池每次清理后，有剩余的空间时，都会导致上游未完成请求数量的回升，这样既不会引发背压异常，也不会导致数据遗失。

上游在发送数据的时候并不需要考虑下游需不需要，而只需要考虑异步缓存池中是否放得下，放得下便发，放不下便暂停。所以，通过*e.requested()*获取到的值，并不是下游真正的数据请求数量，而是异步缓存池中可放入数据的数量。数据放入缓存池中后，再由缓存池按照下游的数据请求量向下传递，待到传递完的数据累积到95条之后，将其清除，腾出空间存放新的数据。如果下游处理数据缓慢，则缓存池向下游传递数据的速度也相应变慢，进而没有传递完的数据可清除，也就没有足够的空间存放新的数据，上游通过*e.requested()*获取的值也就变成了0，如果此时，再发送数据的话，则会根据BackpressureStrategy背压策略的不同，抛出MissingBackpressureException异常，或者丢掉这条数据。

所以上游只需要在e.requested()等于0时，暂停发射数据，便可解决背压问题。

最终方案

下面我们回到最初的问题

运行下面代码：

![img](https:////upload-images.jianshu.io/upload_images/6773051-eae533e5eaf0f81e.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/716/format/webp)

demo17.jpg

由于下游处理数据的速度（Thread.sleep(50)）赶不上上游发射数据的速度，则会导致背压问题。

运行后查看内存使用如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-bf918584b8c86a86.gif?imageMogr2/auto-orient/strip|imageView2/2/w/562/format/webp)

GIF333.gif

内存暴增，很快就会OOM

下面，对其通过Flowable做些改进，让其既不会产生背压问题，也不会引起异常或者数据丢失。

代码如下：

![img](https:////upload-images.jianshu.io/upload_images/6773051-0e5fd579fb487c46.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/751/format/webp)

demo18.jpg

下游处理数据的速度*Thread.sleep(50)*赶不上上游发射数据的速度，

不同的是，我们在下游*onNext(Integer integer)*方法中，每接收一条数据增加一条请求量，

mSubscription.request(1)

在上游添加代码

if(e.requested()==0)continue;

让上游按需发送数据。

运行后查看内存：

![img](https:////upload-images.jianshu.io/upload_images/6773051-afa30fdace5c0d2e.gif?imageMogr2/auto-orient/strip|imageView2/2/w/562/format/webp)

GIF999.gif

内存一直相当的平静，而且上游严格按照下游的需求量发送数据，不会产生MissingBackpressureException异常，或者丢失数据。





