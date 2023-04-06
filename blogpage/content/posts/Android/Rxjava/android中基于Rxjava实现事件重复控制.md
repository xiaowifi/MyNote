
## 前言
#### 资料
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>
[Rxjava javaDoc地址](http://reactivex.io/RxJava/2.x/javadoc/)
[RxBinding GitHub地址](https://github.com/JakeWharton/RxBinding)

#### QWQ
> android搬砖久了，也慢慢习惯了。最近发现自己对重复点击，或者方法重复调用就没有整理过，使用方式也大概是需要处理的地方处理。通常是记一个时间，然后算两个时间。点击事件倒是封装了简单的。方法调用全靠逻辑控制。
> IDEA你已经是成熟的编辑器了，该学会自己写代码了。最近搬砖的时候，发现项目中竟然有Rxjava和Rxbinding.emmmmmm?竟然有这个。那么事件重复不就解决了，别问，问就是才入坑。
## 正文

### 点击事件重复

话说这个简单。基于rxjava 的操作符 debounce 就可以实现了。debounce主要是做拦截处理的。处理逻辑，只要当前对象在某个时间段内重复发送onNext,那么就不发送，最简单的逻辑就是，他其实可以看做一个延时发送onNext功能，发送了onNext，会排到一个队列中，当最后一个在时间段内没有被其他onNext顶替的时候才会回调到监听方法中，如同是最后一下onNext 延时回调了。具体的可以参考rxjava.rxJava会在后面整理。当然了实现重复过滤的操作还是蛮多的，你说我只要第一个，不要其他的，换操作符就行，本笔记主要是整理思路。

##### rxbinding 使用

> 因为这个时间可能是需要固定的，不想每个都改，然后写成static 需要注明生产消费线程，否则会拉去不到线程，无法操作UI.

```
public static void clicks(View view, Consumer onNext){
    RxView.clicks(view).debounce(300, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
}
```

当然了，大佬提供了其他的，就需要自己去看了，详细的后面（后面就不知道会排到什么时候）会整理到笔记中。

### 方法调用重复(抛弃前面的使用最后面的)

> 看了rxbinding的使用，发现他就自定义 Observable，然后在里面调用 onNext,然后，瞬间觉得，这个调调，我还是可以的。然后开整。

因为方法调用重复，一般就是将需要过滤的方法放到监听后执行。在方法调用的前置条件中去执行onNext.emmmmm?这不就是普通的Rxjava的使用吗？既然大佬都自己定义了一个Observable，那我也要自己整一个。这个地方可能就需要涉及到一个逻辑了，他发送者和消费者是对应的，那么你在一个方法中实现发送者和消费者的监听，然后不停的调用哪个方法，也达不到重复使用的目的呀，所以这个发送者消费者关联是不是就应该只执行一次，通过onNext多次调用达到效果。 

还有一个问题，onNext执行前的代码可能相同，是不是也需要单独提到一个方法中然后再调用onNext呢。

##### 前置执行方法

```
@FunctionalInterface
public interface DggFunction {
    void apply();
}
```

#### 自定义 Observable

```
/**
 * @author:yangfan
 * @date: 2021/3/11 20:28
 * @description: 这个主要是提供一个方法使用的 Observable。在调用 DggFunction中的apply时候，发送 onNext
 * @update: [ 2021/3/11] [更改人] [更改内容]
 **/
public class FunctionObservable extends Observable<DggFunction> {
    DggFunction dggFunction;
    Observer observer;
    
    public FunctionObservable(DggFunction dggFunction) {
        this.dggFunction = dggFunction;
    }

    @Override
    protected void subscribeActual(Observer<? super DggFunction> observer) {
        this.observer=observer;
    }
    public void apply(){
        if (dggFunction!=null){

        }
        dggFunction.apply();
        observer.onNext(dggFunction);
    }

    /**
     * 很单纯的提供一个在300毫秒里面重复点击。
     * FunctionObservable observable = FunctionObservable.getObservable(() -> {
     *             LogUtils.e("前置执行了很多次");
     *         }, o -> {
     *             LogUtils.e("但是我就在固定时间内取最后一次");
     *         });
     * observable.apply();// 这个可以调用多次，但是回调在条件内触发只有一次。
     * @param front 前置执行，这个调调可以执行很多次。如果没有前置条件，比如说点击事件，那这个方法中就可以为空，对象不能为空。
     * @param onNext 这个回调只会在300毫秒内的最后一个执行后调用。计算规则，如果front 执行后300毫秒内没有再次执行就回调这个方法。如果再次执行，就再等300毫秒内是否再次执行，直到front300内没有再次执行当且仅当没有执行后 回调这个。
     * @return FunctionObservable ，界面层中调用 apply 执行 前置前置方法front
     */
    public static FunctionObservable getObservable(DggFunction front, Consumer  onNext){
        return getObservable(front,onNext,300,TimeUnit.MILLISECONDS);
    }

    /**
     * 这个地方提供一个空的DggFunction。功能和上面相同。
     * @param onNext
     * @return
     */
    public static FunctionObservable getObservable(Consumer onNext){
        return getObservable(()->{},onNext,300,TimeUnit.MILLISECONDS);
    }

    /**
     *  //这个调调是在主线程中监听，在主线程中返回。
     * @param front 前置执行。
     * @param onNext 重复过滤
     * @param time 过滤时间
     * @param unit 过滤时间类型
     * @return FunctionObservable
     */
    public static FunctionObservable getObservable(DggFunction front, Consumer  onNext,long time,TimeUnit unit){
        FunctionObservable observable = new FunctionObservable(front);
        //这个调调是在主线程中监听，在主线程中返回。
        observable.debounce(time, unit).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
        return observable;
    }
```

#### 使用 

> 注释个人觉得写得蛮详细的就不解释了。

````
FunctionObservable observable = FunctionObservable.getObservable(() -> {
               LogUtils.e("前置执行了很多次");
              }, o -> {
                  LogUtils.e("但是我就在固定时间内取最后一次");
              });
````

大概流程就是：

* 声明创建 observable对象和其监听回调，声明一次，多次莫得解决重复的能力，你不停的new,有效果就特么是怪事。
* 在合适或者可能出现重复事件的地方执行observable.apply();因为observable 的apply() 中调用了自定义function中的apply(),后执行了onNext();
* 在监听中获得最后一次调用回调，执行 Consumer的方法。

#### 场景

* 比如按钮的重复点击然后执行方法，就可以不用Rxbinding 了。
* 列表item的点击事件调用方法。
* 网络请求重复调用
* loading层或者dialog重复显示。
* 本地数据库重复插入。这个调调很常见，但是巨难找到，如果多个人写就是难上加难了。

> 大多数事件都是重复点击调用导致的。虽然可以通过严密的代码逻辑控制，但是控制得太多了，代码又不是抖A萌要行行控制，脑阔也遭不住呀。这么写就可以偷懒简单处理一些重复调用了。毕竟都特么搬砖了，你叫我逻辑控制？

### 抛弃后面用最开始的(功能防止抖动)

这个逻辑场景还是蛮多的，这个和通过记住第一次时间，匹配后面时间，差不多。比如点击一个按钮的时候，没有loading层控制，用户可以多次点击的时候，在第2次点击时候提示他不要重复点击（这个调调需要考虑下使用Rxjava实现），还有就是抢东西，应该是取第一次，如果人家不停的点，然后取最后一次，那也太骚了吧。所以这种场景就没法使用延时来过滤重复事件了。那我们就需要使用第一个，而且必须是同时间段的第一个。