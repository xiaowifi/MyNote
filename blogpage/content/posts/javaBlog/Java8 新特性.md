+++
date = "2021-01-05"
title = "java8 新特性"
description = "java 8 java8 新特性"
tags = [ "java8"]
categories = [
    "技术类"
]
featured = true
+++
## Lambda 表达式
lambda表达式 允许我们将行为传到函数中，之前只能选择匿名内部类，现在可以直接使用lambda表达式。
````aidl

// 1. 不需要参数,返回值为 5  
() -> 5  
  
// 2. 接收一个参数(数字类型),返回其2倍的值  
x -> 2 * x  
  
// 3. 接受2个参数(数字),并返回他们的差值  
(x, y) -> x – y  
  
// 4. 接收2个int型整数,返回他们的和  
(int x, int y) -> x + y  
  
// 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)  
(String s) -> System.out.print(s) 
// 6 接收一个值，返回一个值
( x) -> {
      System.out.print("QQQQQ");
      return x + x*0.05;
     }

// 对集合继续迭代
 List<String> languages = Arrays.asList("java","scala","python");
     languages.forEach(s -> {
      s.length();
     });
// 实现 map函数可以说是函数式编程里最重要的一个方法了。map的作用是将一个对象变换为另外一个。
List<Double> cost = Arrays.asList(10.0, 20.0,30.0);
     Stream<Double> stream = cost.stream().map(x -> {
      return x + x * 0.05;
     });

// reduce实现的则是将所有值合并为一个  .map(x -> x+x*0.05) 是转变。
List<Double> cost = Arrays.asList(10.0, 20.0,30.0);
double allCost = cost.stream().map(x -> x+x*0.05).reduce((sum,x) -> sum + x).get();

// filter 过滤一些元素 返回大于25的数据。
List<Double> cost = Arrays.asList(10.0, 20.0,30.0,40.0);
     List<Double> filteredCost = cost.stream().filter(x -> x > 25.0).collect(Collectors.toList());
````

## 函数式接口

JDK 1.8 之前已有的函数式接口:

- java.lang.Runnable 
````aidl
// 创建新的线程  System.out.print(""); 执行在run() 方法中。
 new Thread(()-> {
      System.out.print("");
     }).start();
````
- java.util.concurrent.Callable Callable是类似于Runnable的接口，实现Callable接口的类和实现Runnable的类都是可被其它线程执行的任务。
    - Callable规定的方法是call()，而Runnable规定的方法是run()
    - Callable的任务执行后可返回值，而Runnable的任务是不能返回值的
    - call()方法可抛出异常，而run()方法是不能抛出异常的
    - 运行Callable任务可拿到一个Future对象，可了解任务执行情况，可取消任务的执行，还可获取任务执行的结果.
````aidl
 Integer integer1 = Executors.newSingleThreadExecutor().submit(() -> {
            return 6;
        }).get();
````
- java.security.PrivilegedAction 暂时没有找到
- java.util.Comparator 比较器
````aidl
  String[] str = {"aadsaf","dqwd","dqwfcsqc","xqsccaac","csfwffqaf","czxca","cas","cacs","casc"};
        Arrays.sort(str,(o1,o2)->{return 1;});
````
- java.io.FileFilter 抽象路径名的过滤器
````aidl
File[] files = dir.listFiles((pathname)->{
        {
            if(pathname.isDirectory())
                return true;
            return pathname.toString().endsWith(".java");
        }
    });
````
- java.nio.file.PathMatcher 路径过滤器
- java.lang.reflect.InvocationHandler JAVA 动态代理 对象的执行方法，交给代理来负责。比如user.get() 方法，是User对象亲自去执行。而使用代理则是由proxy去执行get方法。
````aidl

````
- java.beans.PropertyChangeListener  PropertyChangeSupport类来支持关联属性事件的触发
> JavaBean的属性与一般Java程序中所指的属性，或者说与所有面向对象的程序设计语言中对象的属性是一个概念，在程序中的具体体现就是类中的变量。在JavaBean的设计中，按照属性的不同作用又细分为四类：单值属性；索引属性；关联属性；限制属性。
  关联属性，也称之为绑定属性。绑定属性会在属性值发生变化时，通知所有相关的监听器。为了实现一个绑定属性，必须实现两个机制。
>
    - 无论何时，只要属性的值发生变化，该bean必须发送一个PropertyChange事件给所有已注册的监听器。该变化可能发生在调用set方法时，或者程序的用户做出某种动作时。
    - 为了使感兴趣的监听器能够进行注册，bean必须实现以下两个方法：
        - void addPropertyChangeListener(PropertyChangeListener listener);
        - void removePropertyChangeListener(PropertyChangeListener listener);
        可以通过java.bean包下的PropertyChangeSupport类来管理监听器。要使用这个类，bean必须有一个此类的数据域。
        private PropertyChangeSupport changes = new PropertyChangeSupport(this);
        这样就可以将添加和移除监听器的任务交给这个对象。
        public void addPropertyChangeListener(PropertyChangeListener listener) {
        　　changes.addPropertyChangeListener(listener);
        }
        public void removePropertyChangeListener(PropertyChangeListener listener) {
        　　changes.removePropertyChangeListener(listener);
        }

     当bean的属性发生变化时，使用PropertyChangeSupport对象的firePropertyChange方法，它会将一个事件发送给所有已经注册的监听器。该方法有三个参数：属性的名字、旧的值以及新的值。属性的值必须是对象，如果是简单数据类型，则必须进行包装。

- java.awt.event.ActionListener Android 中用不到这个包

- javax.swing.event.ChangeListener Android 中也用不到这个包

JDK 1.8 新增加的函数接口：java.util.function
 它包含了很多类，用来支持 Java的 函数式编程，该包中的函数式接口有：
- 1    BiConsumer<T,U>   代表了一个接受两个输入参数的操作，并且不返回任何结果 
````aidl
			//逻辑和 Consumer相同，不过是一个传入是一个参数，一个传入的是两个参数。
			BiConsumer<Integer,Integer> consumer=(x,y)->{
            LYLog.e(x+y);
        };
        consumer.accept(5,6);
        BiConsumer<Integer,Integer> consumer1=(x,y)->{
            LYLog.e(x-y);
        };
        consumer1.accept(7,8);
        BiConsumer<Integer, Integer> consumer2 = consumer.andThen(consumer1);
        consumer2.accept(9,10);
````
- 2    BiFunction<T,U,R>  代表了一个接受两个输入参数的方法，并且返回一个结果 
````aidl
应该和 Function 差不多
````
- 3    BinaryOperator<T>  代表了一个作用于于两个同类型操作符的操作，并且返回了操作符同类型的结果 
````aidl
// 通过apply可以知道，这个传入两个参数，返回一个处理后的结果。
        BinaryOperator<Integer> operator=(x,y)->{return x+y;};
        LYLog.e(operator.apply(5,7));
        //这么取最小值,通过自然比较器
        BinaryOperator<Integer> minBy = BinaryOperator.minBy(Comparator.naturalOrder());
        LYLog.e(minBy.apply(5,8));
        //这么取最大值,通过自然比较器
        BinaryOperator<Integer> maxBy = BinaryOperator.maxBy(Comparator.naturalOrder());
        LYLog.e(maxBy.apply(5,8));
        // 自定义比较器？o1.length() - o2.length() 比较两个字段的长度，（o1.length() - o2.length()） 取反 导致的效果就是 定义的是取最大值，其实是取最小值。
        BinaryOperator<String> operator1 = BinaryOperator.maxBy((String o1, String o2) -> {
            return -(o1.length() - o2.length());
        });
        LYLog.e(operator1.apply("QWQ","QAZQ"));
````
- 4    BiPredicate<T,U>  代表了一个两个参数的boolean值方法     
````aidl
 			// 和Predicate类似，只是多了一个参数。
 			BiPredicate<Integer,Integer> predicate=(x,y)->{return  x>y;};
       LYLog.e(predicate.test(5,6));
       //Log:false
````
- 5    BooleanSupplier  代表了boolean值结果的提供方    
````aidl
// 这个调调，感觉就是内部类实现的时候，返回一个 布尔值，没有传参，同线程中感觉没有什么用，主要是异步操作的时候可能需要回调一个布尔值。又想用朗母达表达式的时候。
````
- 6    Consumer<T>  代表了接受一个输入参数并且无返回的操作     
````aidl
 Consumer<Integer> consumer1=(x)->{ LYLog.e(x); };
 consumer1.accept(5);// 执行这个地方的代码的时候，会打印5这句话。
 // 但是如果想所使用 andthem ，两个 consumer 的泛型应该相同。
        Consumer<String> consumer2=(x)->{
            LYLog.e(x);
        };
        Consumer<Integer> consumer = consumer1.andThen(consumer2);
        consumer.accept(6);
        //上面代码就会抛出 不兼容的类型: Consumer<String>无法转换为Consumer<? super Integer>
        关于 consumer1.andThen(consumer2); 执行顺序：先执行 consumer1的方法，再执行 consumer2的方法

````
- 7    DoubleBinaryOperator  代表了作用于两个double值操作符的操作，并且返回了一个double值的结果。
`````aidl
		// 这个调调和 BinaryOperator 功能一模一样，就是定义了类型。
		DoubleBinaryOperator operator=(x,y)->{return x+y;};
        LYLog.e(operator.applyAsDouble(5,6));
        // Log:11.0
`````
- 8    DoubleConsumer  代表一个接受double值参数的操作，并且不返回结果。 
````aidl
		// 和consumer一样，只是定义了数据类型、
````
- 9    DoubleFunction<R>  代表接受一个double值参数的方法，并且返回结果 
````aidl
		// 和function 一样，定义了数据类型。
````
- 10   DoublePredicate  代表一个拥有double值参数的boolean值方法   
````aidl
	// 和predicate 一样，定义了数据类型。
````
- 11   DoubleSupplier  代表一个double值结构的提供方    
````aidl
	// 定义了一个 只是返回的 double的内部内。
````
- 12   DoubleToIntFunction  接受一个double类型输入，返回一个int类型结果。 

- 13   DoubleToLongFunction  接受一个double类型输入，返回一个long类型结果 

- 14   DoubleUnaryOperator  接受一个参数同为类型double,返回值类型也为double 。 

- 15   Function<T,R>  接受一个输入参数，返回一个结果。     
````aidl
 Function<Integer,Integer> function=(x)->{return x+1;};
        Function<Integer,Integer> function1=(x)->{return x+2;};
        LYLog.e(function.apply(5));
        LYLog.e(function1.apply(5));
        // 这个应该 andThen，应该先执行 function，然后执行function1
        Function<Integer, Integer> function2 = function.andThen(function1);
        // compose 先执行 function1，再执行 function
        Function<Integer, Integer> function3 = function.compose(function1);
        LYLog.e(function2.apply(5));
        LYLog.e(function3.apply(5));
        LYLog.e(function3.apply(5));
        //接收什么就返回什么
        Function<Integer, Integer> identity = Function.identity();
        LYLog.e(identity.apply(5));
````
- 16   IntBinaryOperator  接受两个参数同为类型int,返回值类型也为int 。 

- 17   IntConsumer  接受一个int类型的输入参数，无返回值 。     

- 18   IntFunction<R>  接受一个int类型输入参数，返回一个结果 。

- 19   IntPredicate  ：接受一个int输入参数，返回一个布尔值的结果。 

- 20   IntSupplier  无参数，返回一个int类型结果。        

- 21   IntToDoubleFunction  接受一个int类型输入，返回一个double类型结果 。 

- 22   IntToLongFunction  接受一个int类型输入，返回一个long类型结果。 

- 23   IntUnaryOperator  接受一个参数同为类型int,返回值类型也为int 。

- 24   LongBinaryOperator  接受两个参数同为类型long,返回值类型也为long。 

- 25   LongConsumer  接受一个long类型的输入参数，无返回值。    

- 26   LongFunction<R>  接受一个long类型输入参数，返回一个结果。  

- 27   LongPredicate  R接受一个long输入参数，返回一个布尔值类型结果。 

- 28   LongSupplier  无参数，返回一个结果long类型的值。     

- 29   LongToDoubleFunction  接受一个long类型输入，返回一个double类型结果。 

- 30   LongToIntFunction  接受一个long类型输入，返回一个int类型结果。 

- 31   LongUnaryOperator  接受一个参数同为类型long,返回值类型也为long。 

- 32   ObjDoubleConsumer<T>  接受一个object类型和一个double类型的输入参数，无返回值。 

- 33   ObjIntConsumer<T>  接受一个object类型和一个int类型的输入参数，无返回值。 

- 34   ObjLongConsumer<T>  接受一个object类型和一个long类型的输入参数，无返回值。 

- 35   Predicate<T>  接受一个输入参数，返回一个布尔值结果。    
````aidl
//传入一个值，返回一个布尔值，通常在做筛选器。
        Predicate<Integer> predicate = x -> {
           return x > 3;
        };
        //传入 5 ，判断是否大于3
        LYLog.e(predicate.test(5));
        // 获取大于3的数组
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> collect = list.stream().filter(predicate).collect(Collectors.toList());
        LYLog.e(collect);
        // 两个条件。and  大于3 小于9
        Predicate<Integer> predicate1=x ->{ return x<9; };
        Predicate<Integer> and = predicate.and(predicate1);
        LYLog.e(and.test(5));
        LYLog.e(list.stream().filter(and).collect(Collectors.toList()));
        // 或者，大于3，或者小于9.感觉这个逻辑有问题
        Predicate<Integer> or = predicate.or(predicate1);
        // 逻辑取反。
        Predicate<Integer> negate = predicate.negate();
        LYLog.e(negate.test(5));
        
        //返回值
Log:true
Log:[4, 5, 6, 7, 8, 9, 10]
Log:true
Log:[4, 5, 6, 7, 8]
Log:false
````
- 36   Supplier<T>  无参数，返回一个结果。    

- 37   ToDoubleBiFunction<T,U>  接受两个输入参数，返回一个double类型结果 

- 38   ToDoubleFunction<T>  接受一个输入参数，返回一个double类型结果 

- 39   ToIntBiFunction<T,U>  接受两个输入参数，返回一个int类型结果。 

- 40   ToIntFunction<T>  接受一个输入参数，返回一个int类型结果。  

- 41   ToLongBiFunction<T,U>  接受两个输入参数，返回一个long类型结果。 

- 42   ToLongFunction<T>  接受一个输入参数，返回一个long类型结果。 

- 43   UnaryOperator<T>  接受一个参数为类型T,返回值类型也为T。    


### FunctionalInterface 注解
函数式接口(Functional Interface)就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。
函数式接口可以被隐式转换为 lambda 表达式。
Lambda 表达式和方法引用（实际上也可认为是Lambda表达式）上。
````aidl
@FunctionalInterface
 interface FunctionalInterfaceDemo {
    String sayMessage(String message);
}
// 结合lambda表达式一起使用
 FunctionalInterfaceDemo demo= (message) ->{return message;};
````



### 结合 function 和lambda 一起使用

```
// 方法
public static int set(Function<Integer,Integer> function){
    return function.apply(9);
}
// 调用
set((x)->{return x;});
```

## 方法引用

### 方法引用的使用场景

　　我们用Lambda表达式来实现匿名方法。但有些情况下，我们用Lambda表达式仅仅是调用一些已经存在的方法，除了调用动作外，没有其他任何多余的动作，在这种情况下，我们倾向于通过方法名来调用它，而Lambda表达式可以帮助我们实现这一要求，它使得Lambda在调用那些已经拥有方法名的方法的代码更简洁、更容易理解。方法引用可以理解为Lambda表达式的另外一种表现形式。

###  方法引用的分类

| 类型         | 语法               | 对应的Lambda表达式                   |
| ------------ | ------------------ | ------------------------------------ |
| 静态方法引用 | 类名::staticMethod | (args) -> 类名.staticMethod(args)    |
| 实例方法引用 | inst::instMethod   | (args) -> inst.instMethod(args)      |
| 对象方法引用 | 类名::instMethod   | (inst,args) -> 类名.instMethod(args) |
| 构建方法引用 | 类名::new          | (args) -> new 类名(args)             |



## 默认方法

*首先，之前的接口是个双刃剑，好处是面向抽象而不是面向具体编程，缺陷是，当需要修改接口时候，需要修改全部实现该接口的类，目前的 java 8 之前的集合框架没有 foreach 方法，通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。所以引进的默认方法。他们的目的是为了解决接口的修改与现有的实现不兼容的问题。*

方法使用：default 标记。

```
public interface Vehicle {
    default void print(){
        System.out.println("我是一辆车!");
    }
}
```

使用的时候。

```
class Car implements Vehicle {
    public void print(){
        Vehicle.super.print();
    }
}
```

## Stream

Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。

Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。

Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。

这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。

元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果。

### 生成流

- **stream()** − 为集合创建串行流。
- **parallelStream()** − 为集合创建并行流。

### forEach 

迭代流中的每个数据

### map 

map 方法用于映射每个元素到对应的结果

```
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
// 获取对应的平方数
List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
```

### filter 

filter 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串：

```
List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
// 获取空字符串的数量
long count = strings.stream().filter(string -> string.isEmpty()).count();
```



## limit

limit 方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据：

Random random = new Random(); 

random.ints().limit(10).forEach(System.out::println);

------

## sorted

sorted 方法用于对流进行排序。以下代码片段使用 sorted 方法对输出的 10 个随机数进行排序：

Random random = new Random();

 random.ints().limit(10).sorted().forEach(System.out::println);

------

## 并行（parallel）程序

parallelStream 是流并行处理程序的代替方法。以下实例我们使用 parallelStream 来输出空字符串的数量：

List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl"); 

// 获取空字符串的数量 

long count = strings.parallelStream().filter(string -> string.isEmpty()).count();

我们可以很容易的在顺序运行和并行直接切换。

------

## Collectors

Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：



List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");

 List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList()); 

 System.out.println("筛选列表: " + filtered); 

String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", ")); 

System.out.println("合并字符串: " + mergedString);

------

## 统计

另外，一些产生统计结果的收集器也非常有用。它们主要用于int、double、long等基本类型上，它们可以用来产生类似如下的统计结果。

List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

 IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics(); 

 System.out.println("列表中最大的数 : " + stats.getMax()); 

System.out.println("列表中最小的数 : " + stats.getMin());

 System.out.println("所有数之和 : " + stats.getSum()); 

System.out.println("平均数 : " + stats.getAverage());

## Nashorn JavaScript

Nashorn 一个 javascript 引擎。

**Nashorn JavaScript Engine 在 Java 15 已经不可用了。**

这已经在 Java 11 标记为：@deprecated (forRemoval = true)

从 JDK 1.8 开始，Nashorn取代Rhino(JDK 1.6, JDK1.7) 成为 Java 的嵌入式 JavaScript 引擎。Nashorn 完全支持 ECMAScript 5.1 规范以及一些扩展。它使用基于 JSR 292 的新语言特性，其中包含在 JDK 7 中引入的 invokedynamic，将 JavaScript 编译成 Java 字节码。

与先前的 Rhino 实现相比，这带来了 2 到 10倍的性能提升。

[教程](https://www.runoob.com/java/java8-nashorn-javascript.html)

## 时间api

###  本地化日期时间 API

LocalDateTime 简化了日期时间的处理，没有时区的问题。

### ZonedDateTime

如果我们需要考虑到时区，就可以使用时区的日期时间API：

## base64标准库

Base64工具类提供了一套静态方法获取下面三种BASE64编解码器：

- **基本：**输出被映射到一组字符A-Za-z0-9+/，编码不添加任何行标，输出的解码仅支持A-Za-z0-9+/。
- **URL：**输出映射到一组字符A-Za-z0-9+_，输出是URL和文件。
- **MIME：**输出隐射到MIME友好格式。输出每行不超过76字符，并且使用'\r'并跟随'\n'作为分割。编码输出最后没有行分割。

### 内嵌类

| 序号 | 内嵌类 & 描述                                                |
| :--- | :----------------------------------------------------------- |
| 1    | **static class Base64.Decoder**该类实现一个解码器用于，使用 Base64 编码来解码字节数据。 |
| 2    | **static class Base64.Encoder**该类实现一个编码器，使用 Base64 编码来编码字节数据。 |

### 方法

| 序号 | 方法名 & 描述                                                |
| :--- | :----------------------------------------------------------- |
| 1    | **static Base64.Decoder getDecoder()**返回一个 Base64.Decoder ，解码使用基本型 base64 编码方案。 |
| 2    | **static Base64.Encoder getEncoder()**返回一个 Base64.Encoder ，编码使用基本型 base64 编码方案。 |
| 3    | **static Base64.Decoder getMimeDecoder()**返回一个 Base64.Decoder ，解码使用 MIME 型 base64 编码方案。 |
| 4    | **static Base64.Encoder getMimeEncoder()**返回一个 Base64.Encoder ，编码使用 MIME 型 base64 编码方案。 |
| 5    | **static Base64.Encoder getMimeEncoder(int lineLength, byte[] lineSeparator)**返回一个 Base64.Encoder ，编码使用 MIME 型 base64 编码方案，可以通过参数指定每行的长度及行的分隔符。 |
| 6    | **static Base64.Decoder getUrlDecoder()**返回一个 Base64.Decoder ，解码使用 URL 和文件名安全型 base64 编码方案。 |
| 7    | **static Base64.Encoder getUrlEncoder()**返回一个 Base64.Encoder ，编码使用 URL 和文件名安全型 base64 编码方案。 |

**注意：**Base64 类的很多方法从 **java.lang.Object** 类继承。