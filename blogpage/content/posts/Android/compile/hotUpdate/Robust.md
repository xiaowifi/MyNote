# 前言
## 实现思路:
> 实现思路。Robust 主要是针对JAVA 层函数进行处理，实现逻辑是：JAVA 函数作为功能实现的主体，那么异常通常是在JAVA函数中抛出。
> 那么是否可以实现一种机制，在函数前判断是否有异常，如果有异常就不执行函数中的代码。答案就是javassist技术。
> 通过他的原理我们就可以知道，他无法更改类，资源，和so 库等。他只是一个异常处理方案。而不是一个更新方案，基于classLoader加载机制，所以他可以实现不重启APP达到更新修复效果。
## 扩展疑问
* 是否支持标记允许修复函数？这么问的原因是class 函数很多，如果都添加的话会不会增加app 体积，增加的贼多。
## 资料
* [Robust 美团热更新](https://github.com/Meituan-Dianping/Robust/blob/master/README-zh.md)
* [javassist github](https://github.com/jboss-javassist/javassist)
* [Android热补丁之Robust原理解析](http://w4lle.com/2017/03/31/robust-0/)
# 正文
