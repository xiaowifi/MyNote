+++
date = "2020-12-30"
title = "Android中关于Rxjava相关笔记整理"
description = "Android中关于Rxjava相关笔记整理"
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

# 正文

## 被观察者
### Observable
> 2.0 import io.reactivex.Observable; 
> [超级详细的官网地址](http://reactivex.io/documentation/operators.html#utility)
* Observable.create();基础的创建一个被观察者，基于ObservableOnSubscribe.class,ObservableOnSubscribe获得的 ObservableEmitter 可以发送多个消息。
    * emitter.onNext(1);// 传入的数据类型 是创建ObservableOnSubscribe传入的泛型。数据将传送导观察者(observer)的onNext()方法中。
    * emitter.onComplete();  调用观察者(observer)的onComplete() 方法，表示不再发送onNext()方法数据。
    * emitter.onError(); 这个同理。不再发送 onNext() 和onComplete();
* Observable.just("A","B","C"); 依次通过onNext() 发送数据 [文档地址](http://reactivex.io/documentation/operators/just.html)
* Observable.fromArray(words);和just 相同，依次通过onNext() 发送数据。
* Observable.amb();[文档详解地址](http://reactivex.io/documentation/operators/amb.html) 当您将多个源Observable传递给Amb时，它将通过这些Observable中的一个的发射和通知：第一个通过发出项目或发送或 通知向 Amb发送通知的通知。Amb将忽略并丢弃所有其他来源可观察到的排放物和通知。
* Observable.ambArray();[文档详解地址](http://reactivex.io/documentation/operators/amb.html)  
* Observable.combineLatest();[文档详解地址](http://reactivex.io/documentation/operators/combinelatest.html)  所述CombineLatest操作者的行为以类似的方式给 邮编，但同时邮编只有当发射项各压缩的源可观察量已发射的先前解压缩的项目， CombineLatest发射每当一个项目的任何来源的观测量发射的项目（只要各源Observables发出了至少一项）。当任何一个源Observable发出一个项目时， CombinateLatest会使用您提供的函数将每个其他源Observable的最近发射的项目组合在一起，并从该函数发出返回值。
* Observable.concat();[文档详解地址](http://reactivex.io/documentation/operators/concat.html)  将通过Iterable序列提供的每个ObservableSource的元素连接成一个元素的单个序列，而无需对其进行交织。
* Observable.concatArray();
* Observable.defer();[文档详解地址](http://reactivex.io/documentation/operators/defer.html)  该推迟操作者等待，直到观察者订阅了它，然后它产生可观察到的，典型地与可观察到的工厂函数。它为每个订户重新进行此操作，因此尽管每个订户可能认为自己正在订阅相同的Observable，但实际上每个订户都有自己的单独序列。在某些情况下，等到最后一分钟（即直到订阅时间）生成Observable才能确保此Observable包含最新数据。
* Observable.empty();[文档详解地址](http://reactivex.io/documentation/operators/empty-never-throw.html)  创建一个不发射任何物品但正常终止的Observable
* Observable.never();和 empty同文档，创建一不发射任何物品且不会终止的 Observable
* Observable.throw();和empty同文档，创建一个不发出任何项目的并以错误终止的observable.
* Observable.fromCallable();返回一个Observable，当观察者订阅该Observable时，它将调用您指定的函数，然后发出从该函数返回的值。
* Observable.fromFuture();[文档详解地址](http://reactivex.io/documentation/operators/from.html)  当您使用Observables时，如果您要使用的所有数据都可以表示为Observables，而不是Observables和其他类型的混合表示，则将更加方便。这使您可以使用一组运算符来控制数据流的整个生命周期。
* Observable.fromIterable();[文档详解地址](http://reactivex.io/documentation/operators/from.html)  当您使用Observables时，如果您要使用的所有数据都可以表示为Observables，而不是Observables和其他类型的混合表示，则将更加方便。这使您可以使用一组运算符来控制数据流的整个生命周期。
* Observable.fromPublisher();将任意的Reactive-Streams Publisher转换为Observable。
* Observable.generate();返回冷的，同步的和无状态的值生成器。
* Observable.interval();[文档详解地址](http://reactivex.io/documentation/operators/interval.html)  该间隔符返回一个可观察发出的递增整数的无限序列，与排放之间你选择的时间恒定的间隔
* Observable.intervalRange();发出一系列长值的信号，第一个在某个初始延迟之后发出，然后在其余时间之后周期性发出
* Observable.merge();[文档详解地址](http://reactivex.io/documentation/operators/merge.html)  通过合并它们的排放将多个可观察物合并为一个
* Observable.range();[文档详解地址](http://reactivex.io/documentation/operators/range.html)  创建一个Observable，它发出特定范围的连续整数
* Observable.sequenceEqual();[文档详解地址](http://reactivex.io/documentation/operators/sequenceequal.html)  通过Sequence等于两个Observable，它将比较每个Observable发出的项目，并且true只有两个序列相同（相同的项目，相同的顺序，具有相同的终止状态）时，它返回的Observable才会发出。
* Observable.switchOnNext();[文档详解地址](http://reactivex.io/documentation/operators/switch.html)  Switch订阅一个发出Observable的Observable。每次观察到这些已发射的可观测对象之一时，Switch从先前发出的可观测对象退订的可观测对象开始从最新的可观测对象中发射项目。请注意，当从源Observable发出新的Observable时，它将取消订阅先前发出的Observable，而不是在新Observable发出项目时取消订阅。这意味着在下一个Observable发出之时到后续Observable本身开始发射之时之间，上一个Observable发出的项目将被丢弃（如上图中的黄色圆圈所示）。
    ![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201230152212.png)
* Observable.timer();[文档详解地址](http://reactivex.io/documentation/operators/timer.html)  该定时器操作创建一个可观察的是在您指定的时间跨度后，发射一种特定的项目
* Observable.unsafeCreate();通过包装必须根据基于Reactive-Streams的Observable规范实现的ObservableSource来创建Observable，方法是正确处理取消操作； Observable本身未提供任何防护措施。
* Observable.using();[文档详解地址](http://reactivex.io/documentation/operators/using.html)  在使用操作是你可以指示可观察到的创建仅在可观测的寿命存在并设置当可观察终止的资源的方式。
* Observable.wrap();如果尚未将ObservableSource封装成Observable，则将其包装到Observable中。
* Observable.zip();[文档详解地址](http://reactivex.io/documentation/operators/zip.html)  该对象将您选择的功能应用于两个（或多个）其他Observable依次发出的项的组合，此函数的结果成为返回的Observable发出的项。它按严格顺序应用此函数，因此新Observable发出的第一项将是函数应用于Observable＃1发出的第一项和Observable＃2发出的第一项的结果；新的zip-Observable发出的第二项将是函数应用于Observable＃1发出的第二项和Observable＃2发出的第二项的结果；等等。它只会发出与发出最少项目的源Observable发出的项目数量一样多的项目。
* Observable.all();[文档详解地址](http://reactivex.io/documentation/operators/all.html)  将谓词函数传递给All运算符，该函数接受源Observable发出的项目，并基于对该项目的评估返回布尔值。All返回一个Observable，该Observable发出一个布尔值： true当且仅当Source Observable正常终止并且true根据此谓词评估Source Observable发出的每个项目时； false如果源Observable发出的任何项目均false 根据此谓词进行评估。
* Observable.ambWith();[文档详解地址](http://reactivex.io/documentation/operators/amb.html)  当您将多个源Observable传递给Amb时，它将通过这些Observable中的一个的发射和通知：第一个通过发出项目或发送或 通知向 Amb发送通知的通知。Amb将忽略并丢弃所有其他来源可观察到的排放物和通知。
* Observable.any();[文档详解地址](http://reactivex.io/documentation/operators/contains.html)  向Contains运算符传递一个特定的项目，true如果该项目由源Observable发出，或者false如果源Observable在不发出该项目的情况下终止，则它将返回该Observable 。 一个相关的运算符IsEmpty返回一个Observable，true 当且仅当源Observable完成而没有发出任何项目时，该Observable才发出。false如果源Observable发出项，则发出该消息。
* Observable.blockingFirst();[文档详解地址](http://reactivex.io/documentation/operators/first.html)  如果您仅对Observable发出的第一项或满足某些条件的第一项感兴趣，则可以使用First运算符过滤Observable 。
* Observable.blockingLast();[文档详解地址](http://reactivex.io/documentation/operators/last.html)  如果您仅对Observable发出的最后一项或符合某些条件的最后一项感兴趣，则可以使用Last运算符过滤Observable 。
* Observable.blockingNext();[文档详解地址](http://reactivex.io/documentation/operators/takelast.html)  通过使用TakeLast运算符修改Observable， 您只能发出Observable发出的最后n个项目，而忽略它们之前的那些项目。
* Observable.blockingSingle();[文档详解地址](http://reactivex.io/documentation/operators/first.html) 如果您仅对Observable发出的第一项或满足某些条件的第一项感兴趣，则可以使用First运算符过滤Observable 。 
* Observable.toFuture();[文档详解地址](http://reactivex.io/documentation/operators/to.html)  ReactiveX的各种特定于语言的实现具有多种运算符，可用于将Observable或Observable发出的一系列项目转换为另一种对象或数据结构。其中一些阻塞直到Observable终止，然后产生等效的对象或数据结构；其他返回的Observable发出这样的对象或数据结构。
* Observable.buffer();[文档详解地址](http://reactivex.io/documentation/operators/buffer.html)  所述缓冲操作者变换可观察到的发射项目到可观察到的发射缓冲这些项目的集合。Buffer的各种特定于语言的实现中有许多变体，它们在选择将哪些项放入哪个缓冲区中时有所不同。
* Observable.cache();[文档详解地址](http://reactivex.io/documentation/operators/replay.html)  一个可连接的Observable与一个普通的Observable相似，不同之处在于它在订阅时不会开始发出项目，而仅在将Connect 运算符应用于该对象时才开始发出项目。这样，您可以提示Observable在您选择的时间开始发射物品
* Observable.cast();[文档详解地址](http://reactivex.io/documentation/operators/map.html)  过对每个项目应用函数来转换Observable发出的项目
* Observable.collect();[文档详解地址](http://reactivex.io/documentation/operators/reduce.html)  将一个函数依次应用于Observable发出的每个项目，并发出最终值
* Observable.compose();[文档详解地址](https://github.com/ReactiveX/RxJava/wiki/Implementing-Your-Own-Operators)  您可以实现自己的Observable运算符,通过向其应用特定的Transformer函数来转换ObservableSource。
* Observable.concatMap();[文档详解地址](http://reactivex.io/documentation/operators/flatmap.html)  将可观察对象发出的项目转换为可观察项目，然后将这些项目的排放展平为单个可观察项目
* Observable.concatWith();[文档详解地址](http://reactivex.io/documentation/operators/concat.html) 所述的毗连运算符连接的多个观测量的输出，使得它们的作用象一个单一的观察的，与所有通过之前的任何由第二可观察（等发射的物品发出的第一可观察被发射的项目，如果有多于二） 
* Observable.contains();[文档详解地址](http://reactivex.io/documentation/operators/contains.html)  向Contains运算符传递一个特定的项目，true如果该项目由源Observable发出，或者false如果源Observable在不发出该项目的情况下终止，则它将返回该Observable 
* Observable.count();[文档详解地址](http://reactivex.io/documentation/operators/count.html) 计数操作者变换可观察到的发射项目到可观察到的，其发射表示由源可观察发出的项数的单个值。如果源Observable因错误而终止，则Count将传递此错误通知，而不会先发出任何项。如果源Observable根本没有终止，则Count既不会发出项也不会终止。 
* Observable.debounce();[文档详解地址](http://reactivex.io/documentation/operators/debounce.html)  该防抖操作过滤了源可观察正在迅速紧接着又发射项目发出项目
* Observable.defaultIfEmpty();[文档详解地址](http://reactivex.io/documentation/operators/defaultifempty.html)  该DefaultIfEmpty操作员只需镜源可观察准确，如果源可观察到发射的任何项目。如果源Observable正常终止（带有 onComplete）而没有发出任何项目，则从DefaultIfEmpty返回的Observable 将在完成之前发出您选择的默认项目。
* Observable.delay();[文档详解地址](http://reactivex.io/documentation/operators/delay.html)  该延时运营商修改，每个发光部分源可观察的项目前，其源通过暂停的时间（即指定）一个特定的增量观测。这具有将Observable发出的整个项目序列在时间上向前移动指定的增量的效果。
* Observable.dematerialize();[文档详解地址](http://reactivex.io/documentation/operators/materialize-dematerialize.html)  代表发出的项目和作为发出的项目发送的通知，或者逆转此过程
* Observable.distinct();[文档详解地址](http://reactivex.io/documentation/operators/distinct.html)  该鲜明运营商只允许通过的项目还没有被发射滤波器可观察到的
* Observable.doAfterNext(); 将此项目发送到下游后，使用当前项目调用指定的使用者
* Observable.doAfterTerminate();[文档详解地址](http://reactivex.io/documentation/operators/do.html)  注册一个动作以应对各种可观察的生命周期事件
* Observable.doFinally(); 在此Observable发出onError或onCompleted信号或由下游处置后，调用指定的操作
* Observable.doOnDispose();[文档详解地址](http://reactivex.io/documentation/operators/do.html)   注册一个动作以应对各种可观察的生命周期事件
* Observable.doOnComplete();[文档详解地址](http://reactivex.io/documentation/operators/do.html)   注册一个动作以应对各种可观察的生命周期事件
* Observable.doOnEach();[文档详解地址](http://reactivex.io/documentation/operators/do.html)   注册一个动作以应对各种可观察的生命周期事件
* Observable.doOnError();[文档详解地址](http://reactivex.io/documentation/operators/do.html)   注册一个动作以应对各种可观察的生命周期事件
* Observable.doOnLifecycle();[文档详解地址](http://reactivex.io/documentation/operators/do.html)   注册一个动作以应对各种可观察的生命周期事件
* Observable.doOnNext();[文档详解地址](http://reactivex.io/documentation/operators/do.html)   注册一个动作以应对各种可观察的生命周期事件
* Observable.doOnSubscribe();[文档详解地址](http://reactivex.io/documentation/operators/do.html)   注册一个动作以应对各种可观察的生命周期事件
* Observable.doOnTerminate();[文档详解地址](http://reactivex.io/documentation/operators/do.html)   注册一个动作以应对各种可观察的生命周期事件
* Observable.elementAt();[文档详解地址](http://reactivex.io/documentation/operators/elementat.html)  在ElementAt操作者拉动位于指定索引位置由所述源发射的可观察的项目，并且发射序列中的项目，作为其自己的唯一发射项目。
* Observable.filter();[文档详解地址](http://reactivex.io/documentation/operators/filter.html)  该过滤器操作者通过通过测试您在谓词函数的形式规定只允许项目筛选可观察到的。
* Observable.firstElement();[文档详解地址](http://reactivex.io/documentation/operators/first.html)  如果您仅对Observable发出的第一项或满足某些条件的第一项感兴趣，则可以使用First运算符过滤Observable 
* Observable.first();[文档详解地址](http://reactivex.io/documentation/operators/first.html)  如果您仅对Observable发出的第一项或满足某些条件的第一项感兴趣，则可以使用First运算符过滤Observable 
* Observable.flatMap();[文档详解地址](http://reactivex.io/documentation/operators/flatmap.html)  所述FlatMap操作者变换可观察到的通过施加指定要由源观察的，其中该函数返回一个可观察的是自身发光的物品发出的每个项目的功能。然后，FlatMap合并这些结果Observable的发射，将这些合并的结果作为自己的序列发射。
* Observable.forEach();[文档详解地址](http://reactivex.io/documentation/operators/subscribe.html)  该订阅运算符是一个观察者连接到可观察到的胶水。为了使观察者能够看到Observable发出的项目，或者从Observable接收错误或完整的通知，它必须首先通过此运算符订阅该Observable。
* Observable.groupBy();[文档详解地址](http://reactivex.io/documentation/operators/groupby.html)  所述的GroupBy操作者划分可观察到的发射项目到可观察到的发射观测量，每一个其发射来自原始源可观察的项目的一些子集。哪些项目最终在哪个Observable上通常由区分函数决定，该函数对每个项目进行评估并为其分配键。具有相同键的所有项目均由相同的Observable发出。
* Observable.groupJoin();[文档详解地址](http://reactivex.io/documentation/operators/join.html)  大多数具有Join操作符的ReactiveX实现也具有GroupJoin 类似的操作符，不同之处在于，您定义的功能是将两个Observable发出的项组合在一起，而不是将源Observable发出的单个项与第二个Observable的项组合在一起，而是与Observable的项组合在一起从第二个Observable发出属于同一窗口的项目。
* Observable.hide();  隐藏此Observable及其一次性对象的标识。
* Observable.ignoreElements();[文档详解地址](http://reactivex.io/documentation/operators/ignoreelements.html)  所述IgnoreElements操作者抑制所有由所述源发射出可观察的项目，但允许其终止通知（或者onError或 onCompleted）穿过不变。
* Observable.isEmpty();[文档详解地址](http://reactivex.io/documentation/operators/contains.html)  向Contains运算符传递一个特定的项目，true如果该项目由源Observable发出，或者false如果源Observable在不发出该项目的情况下终止，则它将返回该Observable 。
* Observable.join();[文档详解地址](http://reactivex.io/documentation/operators/join.html)  大多数具有Join操作符的ReactiveX实现也具有GroupJoin 类似的操作符，不同之处在于，您定义的功能是将两个Observable发出的项组合在一起，而不是将源Observable发出的单个项与第二个Observable的项组合在一起，而是与Observable的项组合在一起从第二个Observable发出属于同一窗口的项目。
* Observable.lastElement();[文档详解地址](http://reactivex.io/documentation/operators/last.html)  如果您仅对Observable发出的最后一项或符合某些条件的最后一项感兴趣，则可以使用Last运算符过滤Observable 。
* Observable.last();[文档详解地址](http://reactivex.io/documentation/operators/last.html)  如果您仅对Observable发出的最后一项或符合某些条件的最后一项感兴趣，则可以使用Last运算符过滤Observable 。
* Observable.lift();[文档详解地址](https://github.com/ReactiveX/RxJava/wiki/Implementing-Your-Own-Operators)  您可以实现自己的Observable运算符
* Observable.map();[文档详解地址](http://reactivex.io/documentation/operators/map.html)  该地图操作员施加您选择由源发射可观察到每个项目的功能，并返回一个可观察到发射这些功能应用的结果。
* Observable.materialize();[文档详解地址](http://reactivex.io/documentation/operators/materialize-dematerialize.html)  代表发出的项目和作为发出的项目发送的通知，或者逆转此过程
* Observable.mergeWith();[文档详解地址](http://reactivex.io/documentation/operators/merge.html)  通过合并它们的排放将多个可观察物合并为一个
* Observable.ofType();[文档详解地址](http://reactivex.io/documentation/operators/filter.html)  该过滤器操作者通过通过测试您在谓词函数的形式规定只允许项目筛选可观察到的
* Observable.onErrorResumeNext();[文档详解地址](http://reactivex.io/documentation/operators/catch.html)  所述捕捉操作员截距一个onError 从源可观察通知和，而不是将其通过任何观察者在与项目的一些其它项目或序列来替换它，有可能使得到的可观察到正常地终止在所有终止。
* Observable.onErrorReturn();[文档详解地址](http://reactivex.io/documentation/operators/catch.html)  所述捕捉操作员截距一个onError 从源可观察通知和，而不是将其通过任何观察者在与项目的一些其它项目或序列来替换它，有可能使得到的可观察到正常地终止在所有终止。
* Observable.onErrorReturnItem();[文档详解地址](http://reactivex.io/documentation/operators/catch.html)  所述捕捉操作员截距一个onError 从源可观察通知和，而不是将其通过任何观察者在与项目的一些其它项目或序列来替换它，有可能使得到的可观察到正常地终止在所有终止。
* Observable.onExceptionResumeNext();[文档详解地址](http://reactivex.io/documentation/operators/catch.html)  所述捕捉操作员截距一个onError 从源可观察通知和，而不是将其通过任何观察者在与项目的一些其它项目或序列来替换它，有可能使得到的可观察到正常地终止在所有终止。
* Observable.onTerminateDetach();[文档详解地址]()  如果序列终止或下游调用dispose（），则取消对上游生产者和下游Observer的引用。
* Observable.publish();[文档详解地址](http://reactivex.io/documentation/operators/publish.html)  一个可连接的Observable与一个普通的Observable相似，不同之处在于它在订阅时不会开始发出项目，而仅在将Connect 运算符应用于该对象时才开始发出项目。这样，您可以提示Observable在您选择的时间开始发射物品。
* Observable.reduce();[文档详解地址](http://reactivex.io/documentation/operators/reduce.html)  将一个函数依次应用于Observable发出的每个项目，并发出最终值
* Observable.repeat();[文档详解地址](http://reactivex.io/documentation/operators/repeat.html)  在重复操作多次发出的一个项目。此运算符的某些实现允许您重复一系列项目，而某些允许您限制重复的次数。
* Observable.replay();[文档详解地址](http://reactivex.io/documentation/operators/replay.html)  一个可连接的Observable与一个普通的Observable相似，不同之处在于它在订阅时不会开始发出项目，而仅在将Connect 运算符应用于该对象时才开始发出项目。这样，您可以提示Observable在您选择的时间开始发射物品。
* Observable.safeSubscribe();订阅当前的Observable并将给定的Observer封装到SafeObserver 如果还不是SafeObserver）中，以处理行为异常的Observer引发的异常（不遵循Reactive-Streams规范）。
* Observable.sample();[文档详解地址](http://reactivex.io/documentation/operators/sample.html) 返回一个Observable，它在定期的时间间隔内发出源ObservableSource *发出的最新发出的项（如果有）。
* Observable.scan();[文档详解地址](http://reactivex.io/documentation/operators/scan.html)  将一个函数依次应用于Observable发出的每个项目，并发出每个连续的值
* Observable.serialize();[文档详解地址](http://reactivex.io/documentation/operators/serialize.html)  一个Observable可能异步地（可能是从不同的线程）调用其观察者的方法。这可能会使Observable违反Observable约定，因为它可能会尝试在其通知之一之前发送OnCompleted或通知，或者可能同时从两个不同的线程发出通知。您可以通过将Serialize运算符应用于此Observable使其行为良好且同步。
* Observable.share();[文档详解地址](http://reactivex.io/documentation/operators/refcount.html)  一个可连接的Observable与一个普通的Observable相似，不同之处在于它在订阅时不会开始发出项目，而仅在将Connect 运算符应用于该对象时才开始发出项目。这样，您可以提示Observable在您选择的时间开始发射物品
* Observable.singleElement();[文档详解地址](http://reactivex.io/documentation/operators/first.html)  如果此Observable 仅发出一个项目，则返回一个Observable，该Observable发出该Observable发出的单个项目；否则，如果此Observable发出一个以上项目或不发送任何项目，则发出 {@code IllegalArgumentException}或{@code NoSuchElementException}信号分别。
* Observable.skip();[文档详解地址](http://reactivex.io/documentation/operators/skip.html)  返回一个Observable，它跳过源ObservableSource发出的前{@code count}个项，并发出剩余的项。通过使用Skip运算符修改Observable， 可以忽略Observable发出的前n个项目，而仅关注其后的那些项目。
* Observable.skipUntil();[文档详解地址](http://reactivex.io/documentation/operators/skipuntil.html)  所述SkipUntil订阅源观察的，但忽略其排放量，直到作为第二可观察发出的项，在该点 SkipUntil开始镜像源观察的。
* Observable.skipWhile();[文档详解地址](http://reactivex.io/documentation/operators/skipwhile.html)  返回一个Observable，只要指定的条件成立，该Observable会跳过源ObservableSource发出的所有项目，但一旦条件变为false则发出所有其他源项目。
* Observable.sorted();  返回一个Observable，它以排序顺序发出源ObservableSource发出的事件。 ObservableSource发出的每个项目都必须针对序列中的所有其他项目实现{@link Comparable}。
* Observable.startWith();[文档详解地址](http://reactivex.io/documentation/operators/startwith.html)  返回一个Observable，该对象在开始发射由源ObservableSource发出的项目之前，在指定的{@link Iterable}中发射这些项目。
* Observable.switchIfEmpty();[文档详解地址]()  返回一个Observable，它发出源ObservableSource发出的项或备用 ObservableSource的项（如果源ObservableSource为空）。
* Observable.switchMap();[文档详解地址](http://reactivex.io/documentation/operators/flatmap.html)  
* Observable.take();[文档详解地址](http://reactivex.io/documentation/operators/take.html)  通过使用Take运算符修改Observable， 可以仅发出Observable发出的前n个项目，然后在忽略其余部分的情况下完成。
* Observable.takeLast();[文档详解地址](http://reactivex.io/documentation/operators/takelast.html)  通过使用TakeLast运算符修改Observable， 您只能发出Observable发出的最后n个项目，而忽略它们之前的那些项目。
* Observable.takeUntil();[文档详解地址](http://reactivex.io/documentation/operators/takeuntil.html)  在第二个Observable发出一个项目或终止之后，丢弃该Observable发出的任何项目
* Observable.takeWhile();[文档详解地址](http://reactivex.io/documentation/operators/takewhile.html)  该TakeWhile镜源可观察，直到你指定一些条件为假，在这一点TakeWhile停止镜像源可观察并终止其自身的观测。
* Observable.throttleFirst();[文档详解地址](http://reactivex.io/documentation/operators/sample.html)  返回一个Observable，该Observable在指定持续时间的连续*时间窗口内仅发出源ObservableSource发出的第一项。
* Observable.throttleLast();[文档详解地址](http://reactivex.io/documentation/operators/sample.html)  返回一个Observable，该Observable在指定持续时间的连续*时间窗口内仅发出源ObservableSource发出的最后一项。
* Observable.throttleWithTimeout();[文档详解地址](http://reactivex.io/documentation/operators/debounce.html)  该防抖操作过滤了源可观察正在迅速紧接着又发射项目发出项目。
* Observable.timeInterval();[文档详解地址](http://reactivex.io/documentation/operators/timeinterval.html)  返回一个Observable，它发出由Source ObservableSource发出的连续项之间的时间间隔的记录。
* Observable.timeout();[文档详解地址](http://reactivex.io/documentation/operators/timeout.html)  如果某个Observable在指定的时间段内未发出任何项目，则 使用Timeout运算符可以终止该Observable的 onError终止。
* Observable.timestamp();[文档详解地址](http://reactivex.io/documentation/operators/timestamp.html)  的时间戳操作者将时间戳由源重新发射在其自己的序列项目之前可观察出射的每个项目。时间戳指示物品何时发射。
* Observable.to();[文档详解地址](http://reactivex.io/documentation/operators/to.html) 在汇编期间调用指定的转换器函数，并返回其结果值。
* Observable.toList();[文档详解地址](http://reactivex.io/documentation/operators/to.html)  ReactiveX的各种特定于语言的实现具有多种运算符，可用于将Observable或Observable发出的一系列项目转换为另一种对象或数据结构。其中一些阻塞直到Observable终止，然后产生等效的对象或数据结构；其他返回的Observable发出这样的对象或数据结构。
* Observable.toMap();[文档详解地址](http://reactivex.io/documentation/operators/to.html)  ReactiveX的各种特定于语言的实现具有多种运算符，可用于将Observable或Observable发出的一系列项目转换为另一种对象或数据结构。其中一些阻塞直到Observable终止，然后产生等效的对象或数据结构；其他返回的Observable发出这样的对象或数据结构。
* Observable.unsubscribeOn();[文档详解地址](http://reactivex.io/documentation/operators/subscribeon.html)  修改源ObservableSource，以便订阅者将在指定的位置取消订阅
* Observable.window();[文档详解地址](http://reactivex.io/documentation/operators/window.html)  窗口类似于 Buffer，但是它不是从源Observable发出项目数据包，而是发出Observables，每个观测项目都从源Observable发出项目的子集，然后以onCompleted 通知终止。
* Observable.withLatestFrom();[文档详解地址](http://reactivex.io/documentation/operators/combinelatest.html)  仅在源ObservableSource（此实例）发出一个项目时，才使用{@code resultSelector} *函数将指定的ObservableSource合并到此ObservableSource序列中。
* Observable.zipWith();[文档详解地址](http://reactivex.io/documentation/operators/zip.html)  返回一个Observable，它发出的项是将指定函数应用于值对的结果，每对值来自源ObservableSource和指定的Iterable序列。
 

### Flowable 
这个是 java 版本中提供的，感觉需要和lambdas表达式一起使用，否则会代码爆红。创建方法感觉和Observable 差不多。但是Android中无法调用这个，无法通过编译。
> Flowable.just("").observeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
> 找不到org.reactivestreams.Publisher的类文件
 
只有一句话也不行。
>  Flowable.just("Hello world").subscribe(System.out::println);
  找不到org.reactivestreams.Publisher的类文件

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


