
# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>

# 正文 
### 链式调用 Observable
````
  Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(5);
                e.onComplete();
            }
        }).observeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        
                    }

                    @Override
                    public void onNext(Integer value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
````

#### Lambda 表达式版本
````aidl
 Observable.create(emitter -> {
            emitter.onNext("onNext"+1);
        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(integer ->{
            System.out.print(integer);
        },(e) ->{
            System.out.print("onError");
        },()->{
            System.out.print("onComplete");
        },(d)->{
            System.out.print("onSubscribe");
        });
````

### 链式调用 Single
````
Single.just("qqqq").observeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });

````

### Flowable
````aidl
Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                
                if (emitter.requested()!=0){
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                    emitter.onNext(4);
                }
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
    }
````
正常Lambda 表达式版本
````aidl
        Disposable subscribe = Flowable.create(emitter -> {
            if (emitter.requested() != 0) {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER).subscribe((i) -> {
            System.out.print("onNext");
        }, (e) -> {
            System.out.print("onError");
        }, () - {
                System.out.print("onComplete");
        },(s)->{
            s.request(1);
            System.out.print("onSubscribe");
        });
````
如果要实现接一条然后才可以发一条的话，上面这么写就不行。毕竟需要Subscription。

# Rxjava所有方法
[可观察算子的字母顺序列表](http://reactivex.io/documentation/operators.html)