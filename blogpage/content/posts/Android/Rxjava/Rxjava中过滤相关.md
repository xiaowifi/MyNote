

## 前言

> 在应用开发去去抖动还是很有必要的，或者说节流。最常见的就是事件的重复性。比如说方法重复调用，导致对象重复初始化，或者按钮重复点击等等。大体来说就是要过滤一些东西。比如说只是抓取第一次，其他的过滤，也可以是抓取最后一次，前面的 过滤。当然了一般都有时间限制。
> 比如说 300毫秒呢的重复点击只有一次生效，或者抢东西的时候，第一次生效。当然了，这个调调是可以通过严谨的代码逻辑去控制的，但是，写的地方多了，就烦了。所以说过滤就很重要。过滤掉那些不需要的调用，可以明确逻辑，然后减少些莫名其妙bug.最重要的是防止被搞，比如说，后台大佬没有对请求进行过滤，然后点一下，请求一下，就一直开脚本点，手机不炸我不停止，还是有点恶心人的。还有一些东西，全局就初始化一次，你经常去初始化人家就不行。

## 正文

过滤这个调调，在Rxjava 中有两个大的分类。

* throttle 相关的时间控制
    * [`Throttle`](http://reactivex.io/documentation/operators/debounce.html)
    * [`throttleFirst`](http://reactivex.io/documentation/operators/sample.html)
    * [`throttleLast`](http://reactivex.io/documentation/operators/sample.html)
    * [`throttleWithSelector`](http://reactivex.io/documentation/operators/debounce.html)
    * [`throttleWithTimeout`](http://reactivex.io/documentation/operators/debounce.html)
* 一个是发送控制.
    * Debounce —仅在经过特定时间跨度时才从Observable发出一项，而不发出另一项
    * Distinct -抑制可观察对象发出的重复项
    * ElementAt—仅发射可观察对象发射的项目n
    * Filter —仅从可观察对象中发出通过谓词测试的项
    * First —仅从Observable发射第一项或满足条件的第一项
    * Last —只发射可观察对象发射的最后一个项目(直接调用，发送next没有监听到回调)
    * Sample —定期发射Observable发射的最新项目
    * Skip—抑制Observable发出的前n个项目
    * SkipLast—抑制Observable发出的最后n个项目
    * Take—仅发射可观察对象发射的前n个项目
    * TakeLast—只发射可观察对象发射的最后n个项目

其实他们有很多是通用的，比如说Debounce的官方介绍文档和throttle的介绍文档是一样的。区别就在于，一个是基于时间的，一个是基于发送对象的。反正都是要过滤，所以就一套整完整。Rxjava感觉主要的控制Observable
发送消息，比如说:

```
observer.onNext();
observer.onComplete();
observer.onError();
observer.onSubscribe();
```

无论时间控制还是发送控制，都是基于上面几个方法的。其他的回调，方法什么的没有自定义的地步。通常都是自定义observer，处理发送消息罢了。比如说RxBinding。


