+++
date = "2021-01-08"
title = "java9新特性"
description = "java9新特性"
tags = [ "java9"]
categories = [
    "技术类"
]
featured = true
+++

#  Java 9 新特性

- **模块系统**：模块是一个包的容器，Java 9 最大的变化之一是引入了模块系统（Jigsaw 项目）。
- **REPL (JShell)**：交互式编程环境。
- **HTTP 2 客户端**：HTTP/2标准是HTTP协议的最新版本，新的 HTTPClient API 支持 WebSocket 和 HTTP2 流以及服务器推送特性。
- **改进的 Javadoc**：Javadoc 现在支持在 API 文档中的进行搜索。另外，Javadoc 的输出现在符合兼容 HTML5 标准。
- **多版本兼容 JAR 包**：多版本兼容 JAR 功能能让你创建仅在特定版本的 Java 环境中运行库程序时选择使用的 class 版本。
- **集合工厂方法**：List，Set 和 Map 接口中，新的静态工厂方法可以创建这些集合的不可变实例。
- **私有接口方法**：在接口中使用private私有方法。我们可以使用 private 访问修饰符在接口中编写私有方法。
- **进程 API**: 改进的 API 来控制和管理操作系统进程。引进 java.lang.ProcessHandle 及其嵌套接口 Info 来让开发者逃离时常因为要获取一个本地进程的 PID 而不得不使用本地代码的窘境。
- **改进的 Stream API**：改进的 Stream API 添加了一些便利的方法，使流处理更容易，并使用收集器编写复杂的查询。
- **改进 try-with-resources**：如果你已经有一个资源是 final 或等效于 final 变量,您可以在 try-with-resources 语句中使用该变量，而无需在 try-with-resources 语句中声明一个新变量。
- **改进的弃用注解 @Deprecated**：注解 @Deprecated 可以标记 Java API 状态，可以表示被标记的 API 将会被移除，或者已经破坏。
- **改进钻石操作符(Diamond Operator)** ：匿名类可以使用钻石操作符(Diamond Operator)。
- **改进 Optional 类**：java.util.Optional 添加了很多新的有用方法，Optional 可以直接转为 stream。
- **多分辨率图像 API**：定义多分辨率图像API，开发者可以很容易的操作和展示不同分辨率的图像了。
- **改进的 CompletableFuture API** ： CompletableFuture 类的异步机制可以在 ProcessHandle.onExit 方法退出时执行操作。
- **轻量级的 JSON API**：内置了一个轻量级的JSON API
- **响应式流（Reactive Streams) API**: Java 9中引入了新的响应式流 API 来支持 Java 9 中的响应式编程。

# 正文

### 模块系统

这个感觉就和Android app 分module 一样，主要是一个功能一个模块，这么代码就容易维护一些，感觉和插件化设计更搭配的样子。

### REPL

REPL(Read Eval Print Loop)意为交互式的编程环境。JShell 是 Java 9 新增的一个交互式的编程环境工具。它允许你无需使用类或者方法包装来执行 Java 语句。它与 Python 的解释器类似，可以直接 输入表达式并查看其执行结果。

> 感觉没有啥卵用，不过测试模块代码还是可以的，但是我写测试白盒测试代码不香吗？

### javaDoc 生成器

这个好像支持搜索了。

### 多版本兼容jar包

> 感觉和Android的马甲包配置没有啥区别，主要还是编译版本。应该是后台使用的吧。

### 集合工厂方法

Java 9 List，Set 和 Map 接口中，新的静态工厂方法可以创建这些集合的不可变实例。

这些工厂方法可以以更简洁的方式来创建集合。

- List 和 Set 接口, of(...) 方法重载了 0 ~ 10 个参数的不同方法 。
- Map 接口, of(...) 方法重载了 0 ~ 10 个参数的不同方法 。
- Map 接口如果超过 10 个参数, 可以使用 ofEntries(...) 方法。

> 我都在用集合了，我固定长度？感觉怪怪的，但是还是又需求在的，比如我初始化的时候，一定需要3个长度的list ,就不用挨着add 了

### 私有接口方法

在 Java 8 中，一个接口中能定义如下几种变量/方法：

- **常量**
- **抽象方法**
- **默认方法** **default**
- **静态方法**

在 Java 9 中，一个接口中能定义如下几种变量/方法：

- **常量**
- **抽象方法**
- **默认方法**
- **静态方法**
- **私有方法 **private
- **私有静态方法** private 

### 改进的进程 API

在 Java 9 之前，Process API 仍然缺乏对使用本地进程的基本支持，例如获取进程的 PID 和所有者，进程的开始时间，进程使用了多少 CPU 时间，多少本地进程正在运行等。

Java 9 向 Process API 添加了一个名为 ProcessHandle 的接口来增强 java.lang.Process 类。

ProcessHandle 接口的实例标识一个本地进程，它允许查询进程状态并管理进程。

ProcessHandle 嵌套接口 Info 来让开发者逃离时常因为要获取一个本地进程的 PID 而不得不使用本地代码的窘境。

我们不能在接口中提供方法实现。如果我们要提供抽象方法和非抽象方法（方法与实现）的组合，那么我们就得使用抽象类。

ProcessHandle 接口中声明的 onExit() 方法可用于在某个进程终止时触发某些操作。

### 改进的 Stream API

Java 9 为 Stream 新增了几个方法：dropWhile、takeWhile、ofNullable，为 iterate 方法新增了一个重载方法。

* Takewhile 方法使用一个断言作为参数，返回给定 Stream 的子集直到断言语句第一次返回 false。如果第一个值不满足断言条件，将返回一个空的 Stream。takeWhile() 方法在有序的 Stream 中，takeWhile 返回从开头开始的尽量多的元素；在无序的 Stream 中，takeWhile 返回从开头开始的符合 Predicate 要求的元素的子集。简单的讲，就是遍历列表的时候遇到什么条件停止遍历列表，然后将之前成功的返回为Stream。

*  dropWhile 和takewhile 相反，返回遇到条件之后的值。好像都没有返回条件本身。 

*  iterate  方法允许使用初始种子值创建顺序（可能是无限）流，并迭代应用指定的下一个方法。 当指定的 hasNext 的 predicate 返回 false 时，迭代停止。

  > IntStream.iterate(3, x -> x < 10, x -> x+ 3).forEach(System.out::println); 这句话的意思是：循环x的初始值是3，循环条件是x小于10，循环体中执行的语句是 x=x+3,所以打印的是369

*  ofNullable ofNullable 方法可以预防 NullPointerExceptions 异常， 可以通过检查流来避免 null 值。如果指定元素为非 null，则获取一个元素并生成单个元素流，元素为 null 则返回一个空流。

### try-with-resources

try-with-resources 是 JDK 7 中一个新的异常处理机制，它能够很容易地关闭在 try-catch 语句块中使用的资源。所谓的资源（resource）是指在程序完成后，必须关闭的对象。try-with-resources 语句确保了每个资源在语句结束时关闭。所有实现了 java.lang.AutoCloseable 接口（其中，它包括实现了 java.io.Closeable 的所有对象），可以使用作为资源。

try-with-resources 声明在 JDK 9 已得到改进。如果你已经有一个资源是 final 或等效于 final 变量,您可以在 try-with-resources 语句中使用该变量，而无需在 try-with-resources 语句中声明一个新变量。

