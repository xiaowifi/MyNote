+++
date = "2021-2-19"
title = "kotlin在Android上的体验"
description = "kotlin在Android上的体验"
series = ["顶呱呱"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
> 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 
## 正文
> 之前就整过一个用Java写法写kotlin的项目。感觉嘛，就是没有用到kotlin的优势。虽然现在Java版本感觉挺优雅的(就是很舒服)。但是现在Android中jetpack demo竟然是kotlin写的，那就需要懂kotlin写法了，要不然学习难度会加大的啊。
> 那么直接开整。其实基础语法都差不多，就整不同点。直接上菜鸟教程。https://www.runoob.com/kotlin/kotlin-tutorial.html 
### 返回值类型
kotlin是写在方法后的。如果没有返回值就可以不写。
````aidl
fun sum(a: Int, b: Int): Int {   // Int 参数，返回值 Int
    return a + b
}
```` 
### 可变长度传参 vararg
```aidl
fun vars(vararg v:Int){
    for(vt in v){
        print(vt)
    }
}
```
### 结合lambda
````aidl
fun main(args: Array<String>) {
    val sumLambda: (Int, Int) -> Int = {x,y -> x+y}
    println(sumLambda(1,2))  // 输出 3
}
````