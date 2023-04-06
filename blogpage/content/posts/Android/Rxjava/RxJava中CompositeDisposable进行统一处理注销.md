
# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>

# 正文
> 在观察者的回调方法中，有一个onSubscribe(Disposable d) 开始连接方法，Disposable 可以控制是否关闭后面的回调。JAVA 对象是有生命周期的。
> 这个时候，对于某些监听回调，就可以统一安排处理了。

````
 private final CompositeDisposable mDisposable = new CompositeDisposable();
// subscribe 需要使用返回 一个Disposable的那个方法。
 Disposable md = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

            }
        }).subscribe(integer -> Log.e(TAG, "demo2: " + integer));
        disposable.add(md);

@Override
    protected void onStop() {
        super.onStop();
        mDisposable.clear();
    }
````