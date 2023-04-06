
# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>

# 正文 
## Observable
主要是创建一个新的被观察者Observable
### Create —通过编程调用观察者方法从头开始创建Observable
### Defer —在观察者订阅之前不要创建Observable，并为每个观察者创建一个新的Observable
### Empty/ Never/Throw -创建具有非常精确和限制行为观测量
### From —将其他一些对象或数据结构转换为可观察的
### Interval —创建一个Observable，它发出以特定时间间隔隔开的整数序列
### Just —将一个对象或一组对象转换为发出该对象或那些对象的Observable
### Range —创建一个Observable，它发出一系列连续整数
### Repeat —创建一个Observable，它反复发出特定的项目或项目序列
### Start —创建一个Observable，它发出函数的返回值
### Timer —创建一个Observable，它在给定的延迟后发出单个项目

# Rxjava所有方法
[可观察算子的字母顺序列表](http://reactivex.io/documentation/operators.html)