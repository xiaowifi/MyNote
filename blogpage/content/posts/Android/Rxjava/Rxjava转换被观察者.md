
# 前言
本Demo 主要是针对 Rxjava  Android 版本进行实践<br>
[Rxjava Android 版本 github 地址](https://github.com/ReactiveX/RxAndroid) <br>
[Rxjava 地址](https://github.com/ReactiveX/RxJava) <br>
[Rxjava 官方文档地址](http://reactivex.io/) <br>

# 正文 
## Observable
主要是转换被观察者Observable
### Buffer —定期将来自一个Observable的项目收集成束，然后发出这些束，而不是一次发出一个
### FlatMap —将可观察对象发出的项目转换为可观察项目，然后将这些项目的排放平展为单个可观察项目
### GroupBy —将一个Observable划分为一组Observable，它们分别与原始Observable发射一组不同的项，并按key进行组织
### Map —通过对每个项目应用函数来转换Observable发出的项目
### Scan —将函数依次应用于Observable发出的每个项目，并发出每个连续的值
### Window —定期将项目从“可观察”窗口细分为“可观察”窗口，然后发出这些窗口，而不是一次发出一个窗口

# Rxjava所有方法
[可观察算子的字母顺序列表](http://reactivex.io/documentation/operators.html)