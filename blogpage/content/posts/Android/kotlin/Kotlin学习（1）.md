+++
date = "2020-10-01"
title = "kotlin学习（1）"
description = "kotlin学习（1）"
series = ["kotlin"]
categories = [
"android基础"
]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 正文
> 双shift  show kotlin ,然后将字节码转化为JAVA 代码。

变量：var a:int = 5
* string
* int 
* double
* float
* long
* short
* byte
* char
* boolean
* list
* set
* map

<br>
只读变量：val a:int =10;

<br>
编译时常量：const var max :int =100;只能在函数之外定义。只能是基础数据类型。
> 编译时常量是在编译的时候赋值，而函数是运行的时候才赋值，函数内的变量也是在运行时候
> 才赋值。
* string
* int 
* double
* float
* long
* short
* byte
* char
* boolean

<br>
表达式：
* if/else 建议用when 和JAVA类似 建议用 swish 
* range  in（a,b）小于等于， 用于检查某个值是否在指定范围之内。

<br>

string 模板：$(可执行代码块)

<br>

函数：可见性修饰符 fun 函数名称（函数入参）函数反参数{}
* 入参 可以包含缺省值，某个入参可以定义默认值。
* 具名函数入参 （name=5）建议使用。
* unit 类型，kotlin 中没有返回值的函数叫unit 函数。
* noting 类型 todo 函数的任务就是抛出异常，就是永远别指望她运行成功。返回noting 类型。故意抛异常也可以这么用。
* 反引号中的函数名，用于解决JAVA 和Kotlin相互调用但是关键字占用的情况。

<br>
匿名函数。
* 匿名函数也有类型，匿名函数的可以当做变量赋值给函数类型的变量。
* 匿名函数会隐式或者自动返回函数体最后一行语句的结果。
* 如果一个函数的lambda表达式参数排在最后面，或者是唯一的参数。那么lambda表达式外层的小括号可以省略。 
* 函数内联，inline  lambda表达式可以让你灵活的编写应用。在jvm 上定义的lambda会以对象实例的形式存在。jvm会为所以和lambda
打交道的变量分配内存，这就产生了内存开销。kotlin内联机制，哪里需要lambda，编译器就会间函数体复制到对应的位置。
* 使用lambda的递归函数无法内联，因为会导致复制粘贴无限循环，编译器会发出警告。

<br>

Null 安全
* 变量不允许直接赋值为null
* string ? 如果为空，当前代码不执行。
* string ?.let
* 断言 ！！必须执行。
* string?:"空" 空合并操作符，如果string为Null，就使用双引号中的值。

<br>

先决条件函数
* checknotNull 如果参数为Null，则抛出异常，否则返回非Null的值。
* require 如果是FALSE ，抛出异常。
* requireNotNull,如果是Null，抛异常。否则返回
* error 如果是Null，抛异常。否则返回。
* assert 如果是FALSE 抛异常，并且打上断言编辑器标记。

<br>

字符串操作 
* substring 字符串截取,substring.(0 until index),大于0 小于index
* split 返回list。解构赋值。var(a,b,c)=name.split(",")，如果不需要的值就下划线_，就可以规律掉不需要的元素。
* replace 字符串替换。支持传入一个正则表达式。
* == 字符串内容，=== 比较对象引用。
* forEach 遍历。

<br>

数字类型
* tointOrNull 转为int 或者 Null。防止类型转化异常。
* "%.2f".format(8.523456778) -> 四舍五入=8.52

<br>

标准库函数
* apply 配置函数 返回当前对象
* let 返回 return的结果。
* run 返回 lambda结果 
* with 是run 的变体
* also 和let类似
* takeIf  为true 返回传入对象
* takeUnless 为FALSE 返回传入对象。

<br>

List 只读
* getOrElse() 
* getOrNUll()
mutableList 可变
* mutator  
* for in 遍历
* for each 遍历
* for eachIndexed 遍历 获取index和value

<br>

set 只读
* mutableSet 可变
* distinct list去重复。

<br>

map 只读 
* mutableMap 可变

<br>

kotlin 中默认编译的方法和类都是 不可继承重写的。需要open 关键字修饰后才可以继承重写。
* kotlin 所有类都是Any  
* 类型转化 as  
* object 对象声明 ，做单例，做子类继承重写方法，（匿名内部类）
* companion object 伴生对象。
* 临时变量（包括仅使用一次的参数通常会以下划线开头的名称命名）
* init {} 初始化
* 初始化顺序：
    * 主构造函数里申明的属性
    * 类级别的属性赋值
    * init初始化里的属性赋值和函数调用
    * 次构造函数里的属性赋值和函数调用。
* LateInit 关键字用于延迟初始化，在用它之前负责初始化。只要无法确定lateinit 变量是否初始化完成，可以执行isInitialized 的检查。
* 惰性初始化，by lazy{} 首次使用才初始化。

<br>
 
嵌套类=内部类
<br>
data class 数据类，用于存储数据的类。
* 主构造函数中申明的属性，都会打印。toString,hash，equals等常用的方法都被重写了。
* 为啥重写equals 会重写hashcode? 
* copy 复制对象。部分特殊定义属性不能赋值，会采用默认值。
* 支持结构语法，默认class 要支持结构语法需要根据变量值重写componcnt1到变量的最大个数，可以少写，不能多写。
* 使用数据类的条件：
    * 数据类必须至少有一个参数的主构造函数。
    * 主构造函数的参数比如是val 或者var.
    * 数据类不能使用 abstract（抽象类）,open（可被继承）,sealed（密封类）,和 inner 修饰符。
    
<br>
enum class 枚举类 类的内容 枚举类都支持。
<br>

运算符重载。这个是Kotlin中特有的。
* + plus
* += plusAssign 
* == equals 
* > compareTo 
* "[]" get 
* .. rangeTo 
* in contains
 
<br>

代数数据类型(可以用来表示一组子类型的闭集)，枚举就是一种简单的代数数据类型ADT.
* 密封类 sealed  更加复杂的ADT

<br>

接口 interface 
* 重写字段和方法需要 override 
* 可以包含自己的get和set 方法。

<br>

抽象类：obstract class

<br>

* 泛型
* vararg 长度可变的参数 

<br>

in out 主要是架构约束。
* out (协变) 如果泛型类只将泛型类型作为函数的返回（输出）那么使用out。可以称之为生产类/接口，因为她主要是用来生产指定的泛型对象。
* in 逆变，如果是泛型入参（输入）
* out 子类泛型对象可以赋值给父类泛型对象。
* in  父类泛型对象可以赋值给子类泛型对象。
* 通过 inline 内联机制，配合reified 关键字对泛型进行标记，用于生成代码的时候通过内联将标记代码粘贴到对应的位置，从而达到通过 is 关键字判断泛型
  是否是某个类型的目的。
> 主要是解决泛型使用中强行定义了子类或者父类的实现的类后。需要拿到子类或者父类放情景。

<br>

定义扩展函数，扩展函数可以在不直接修改类定义的情况下，增加类功能，扩展函数可以用于自定义类，也可以用于 list,string,kotlin 标准库中的其他类。
扩展函数和继承类型，扩展函数也能共享类行为。在无法接触到某个类定义的时候，或者某个类没有定义open 关键字导致无法继承的时候，扩展函数就是其中手段之一、
* 正常扩展函数
* 泛型扩展函数
> 个人理解，基于Kotlin编译器编译特性，这个调用其实也是自动添加代码和内联机制差不多，只是不需要内联标记的吧，讲同类型的功能代码通过这种类似内联的方式
> 添加到需要的位置，可以很大程度上避免了，继承，多态的特性导致的对象臃肿。通过编译去实现某些功能代码块的注入，是一种更加灵活的架构思路。

<br>

函数式编程范式主要是依耐于高阶函数（以函数为入参或者以函数为返参）返回的数据。
<br>函数类别:
* 变换：transform,会遍历集合内容，用一个值参形式传入的变换器函数，变换每一个元素，然后返回包含已修改元素的集合给链上的其他函数。最常用的两个变换函数map,flatMap
    * map:返回修改后元素集合。原始集合未被修改。返回的个数比如和输入的个数保持一致，但类型可以被修改。
    * flatMap:用于操作一个集合的集合，将多个集合中的元素合并后返回一个包含所有元素的单一的集合。
* 过滤：filter 传入一个返回布尔值的函数，如果遍历的时候函数返回true当前对象添加到新的集合中，否则不添加。最后返回新集合，原始集合不被修改。 
* 合并：combine 将不同的集合合并成一个新的集合。
    * zip 合并成key value
    * fold 接受一个初始累加器值，随后根据匿名函数的结果更新。

<br>

序列 list set map 等集合创建后，她包含的元素都会被加入并且允许被访问被称为及早集合。
在Kotlin中，还有一种惰性集合，类似于类的惰性初始化。惰性集合类型性能表现优异，尤其是包含大量的元素的集合的时候，因为惰性集合元素的按需产生的。
kotlin 中内置的惰性集合类型叫做序列。序列不会索引排序她的内容，也不记录元素的数目，事实上，在使用序列时候，序列里的值可能有无限多个，因为某个数据源能产生无限多个元素。
* generateSequence 

<br>

kotlin和JAVA 互相调用操作。
* kotlin调用JAVA 代码返回Null，需要使用非空判断 string？
* JAVA 调用Kotlin 通过 @file:jvmName("name") 设置文件的引用名称。
* JAVA 调用Kotlin 对象属性，因为编译规范会自动生成get和set,可以设置@jvmField 标记达到类似public的调用效果。
* Kotlin 函数包含默认值，为了便于JAVA调用，Kotlin函数可以使用@jvmOverloads 注解进行标记,使得强行重载提供给JAVA，虽然本身就是重载成JAVA，但是不提供调用。
* Kotlin中，对应伴生对象中定义的属性 可以标记 @jvmField后直接调用。
* Kotlin中，对应伴生对象中定义的函数 可以标记 @jvmStatic后直接调用。
* kotlin与JAVA 的异常底层类型不一致，不标记会返回Exception 基础异常，无法正确定位到异常类型，需要将Kotlin中的异常标记为JAVA 异常 使用@Throws 注解标记。用于解决JAVA 无法try{}catch 拦截到该异常。

<br>
Android 项目中使用Kotlin 相关
* 
* 
* 
* 
* 

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

## 结束


