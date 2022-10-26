### 参考资料
* [注解教程](https://www.runoob.com/w3cnote/java-annotation.html)
> JAVA 注解受用的地方很多，但是主要是提供架构约束。
> Java 注解（Annotation）又称 Java 标注，是 JDK5.0 引入的一种注释机制。
> Java 语言中的类、方法、变量、参数和包等都可以被标注。和 Javadoc 不同，Java 标注可以通过反射获取标注内容。在编译器生成类文件时，
> 标注可以被嵌入到字节码中。Java 虚拟机可以保留标注内容，在运行时可以获取到标注内容 。 当然它也支持自定义 Java 标注。
> 通常的使用方式是，动态代理和结合APT技术生成一个class。或者在架构设计中限制输入和输出。
## 正文 
注解主要作用在于标记，然后传值等。
### 注解的生效范围
> 注解通过 @Retention() 设置其注解生效范围。
* RetentionPolicy.SOURCE 只会在编译的时候，class 可以获取到注解信息
* RetentionPolicy.CLASS 在编译成claas 都可以获取到 注解信息，但是运行时候，就没有了
* RetentionPolicy.RUNTIME 运行时候可以获取到注解信息。
### 注解的标记范围
> 主要是生命 注解添加到什么位置。通过 @Target(ElementType.ANNOTATION_TYPE) 标记
* ElementType.ANNOTATION_TYPE 注释类型声明
* ElementType.TYPE  可以标记类，接口，注释类型，或者枚举声明。
* ElementType.FIELD  标记字段
* ElementType.METHOD  方法标记
* ElementType.PARAMETER 参数声明
* ElementType.CONSTRUCTOR 构造方法
* ElementType.LOCAL_VARIABLE 局部变量声明
* ElementType.PACKAGE 标记包 

### 常用的注解
* @Override 检查该方法是否是重写方法，如果发现其父类火灾引用的接口并没有该方法时候，会编译报错。重写方法的时候，编辑器一般会添加上。
    * 这个就属于 方法上 编译时声明
* @Deprecated 标记过时的方法或者类，会报编译警告。
    * 运行时到处都可以标记声明 
* @SuppressWarnings 忽略注解声明中的警告。
    * 编译时，啥都可以标记。
* @Documented 标记这些注解包含在用户文档中。？ 没有用过，感觉建议整进去。它的作用是说明该注解能出现在 javadoc 中。
    * 运行时。ElementType.TYPE
* @Target 用于标记注解 标记范围
    * 运行时 ElementType.TYPE
* @Inherited 标记注解继承，方便扩展。说他可以被继承。
    * 运行时 ElementType.TYPE
* @SafeVarargs 忽略任何使用参数为泛型变量的方法或构造函数调用产生的警告。
    * 运行时 ElementType.TYPE
* @FunctionalInterface - Java 8 开始支持，标识一个匿名函数或函数式接口。
    * 运行时 ElementType.TYPE
* @Repeatable - Java 8 开始支持，标识某注解可以在同一个声明上使用多次。
    * 运行时 ElementType.TYPE

### 使用
在单纯的JAVA 层面，大概就几种使用方式。
* 架构入参等限制。大概就是编译前约束
* 通过反射 获取方法，字段上的标记，或者获取标记中的值，这个值一般都是写死的。没法动态改变。
* 通过注解取代 枚举相关使用。
* 通过注解在运行时候生效的特性，与动态代理一起使用。
* 通过注解编译时生效的特性，与APT一起使用生成一个class
* 对字节码进行修改
所以场景一般是以下场景：
* 编译前-代码约束
* 编译时-在JAVA转class 之前配合APT生成JAVA文件
* 编译时-在生成dex 文件前改变class
* 运行时-通过反射获取到注解的参数，生成动态代理类。

#### 通过注解取代 枚举相关使用 
实例：
````aidl
    String goodsTrade = "PRO_CLASS_TYPE_TRANSACTION";

    //销售
    String goodsSale = "PRO_CLASS_TYPE_SALES";

    //资源
    String goodsRes = "PRO_CLASS_TYPE_SERVICE_RESOURCE";

    //服务
    String goodsService = "PRO_CLASS_TYPE_SERVICE";

    @StringDef({goodsService, goodsTrade})
    @Retention(RetentionPolicy.SOURCE)
    @interface ProductTypeCode {

    }
````
#### 注解上的注解
````java
//用于标记到注解上
@Target(ElementType.ANNOTATION_TYPE)
// 运行时候生效
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    String listenerSetter();
}

// 使用
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter ="")
public @interface OnClick {
  int [] ids() default {-1};
  String name() default "";
}

//使用注解
@OnClick(ids = {R.id.accelerate,R.id.aligned},name = "name")
public void demo(){

}
}

````
### 获取标记的注解
注解是独立于class的标记，所以需要获取到标记的注解就只能通过class去获取，不同位置上的标记需要到对应的节点上去获取，比如方法上的注解就只能通过方法获取，通过class是无法获取到上面的注解的。






























