# 前言
> java代码----(1)---->class---(2)----->dex----(3)---->apk
> javassist 主要是操作（2）阶段。所以不能在原工程上进行更改。
> (2)阶段 也叫transform。
所以需要在原工程以外新建一个工程。通常的操作就是在插件中生成编译时需要的工程。
## 资料
* [javassist github](https://github.com/jboss-javassist/javassist)
* [javassist demo](https://www.w3cschool.cn/article/35230124.html)

