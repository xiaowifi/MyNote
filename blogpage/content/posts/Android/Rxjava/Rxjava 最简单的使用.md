+++
date = "2020-12-31"
title = "Rxjava最简单的使用"
description = "Rxjava最简单的使用"
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

# Rxjava所有方法
[可观察算子的字母顺序列表](http://reactivex.io/documentation/operators.html)