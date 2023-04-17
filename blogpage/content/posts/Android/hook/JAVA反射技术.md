
> 反射是通过class 进行反射的。通过反射技术可以拿到对应的class，创建对应对象或者
> 通过hook技术 调用某些方法。
> 反射是java的查看、检测、修改自身的一种行为。
> 在编译阶段，编译器将我们编写的java文件编译成.class文件。而在运行期，jvm又将.class文件通过类加载器ClassLoader加载一个类对应的Class对象到内存当中。通过修改Class对象，达到我们查看、检测、修改自身的行为。
> 通常而言，我们采用反射技术，一般是包含几个逻辑。
>   * 当前class 我拿不到，但是我知道运行时会有
>   * 这个class不满足我的业务诉求
>   * 适合动态代理的业务诉求
> 对于一个class 而言，我们能操作的一般有，函数，变量等。
> 
# 正文
###  为啥JAVA 要设计 class与object 
 * 如果没有class，每个对象都会存在类信息，类信息属于同类型信息。
 * 如果没有object，节省了内存，会导致类信息不安全，改一个类就会改变其他对象。
 * 类信息和object为了防止关联，解决上面问题，方法区存放类信息，根据class生成的对象，放到堆区。
 * 方法区是JAVA独有的，其他语言都是代码段
 * 反射速度低于new 对象速度。
 * 虚拟机 定义的方法区和堆区 便于GC
 * JAVA 虚拟机是字节码为基础单位，Android 是dex指令 
 * JAVA-class-dex-方法的dex指令
 * 方法区和堆区存放到主存（内存条指向的内存）中。
 * CPU调用高速缓存（栈区）（3级缓存，），由高速缓存调用主存。
 * 单例对象需要使用volatile，用于解决单例在多线程中的处理。
## JAVA Class 
一个class 包含所有内容。
* 包含类信息
* 成员变量
* 方法 
* 构造器 构造函数
* 注解信息 
> 我们知道 注解 可以标记一个class 中的所有东西，且注解信息是存储到class 中的。
* toString 如果是注解或者接口 输出就是 interface，否则就是 class,枚举都是class +包名+类名称
* toGenericString  public class entity.BaseDao 
* forName 通过class 包名+类名 生成class 
* 反射 newInstance 无参构造 
* isInstance  这个对象能不能被转化为这个类
* isAssignableFrom()方法是从类继承的角度去判断，
* isAssignableFrom()方法是判断是否为某个类的父类，
* instanceof关键字是从实例继承的角度去判断。
* instanceof关键字是判断是否某个类的子类。
* isArray 是否是数组 
* isPrimitive 此方法主要用来判断Class是否为原始类型（boolean、char、byte、short、int、long、float、double）
* isAnnotation 是否是注解  
* isSynthetic 当且仅当该字段是由Java语言规范定义的合成字段时才为true 反射出来就是 FALSE？
* getName 获取class 的名称 
* getTypeParameters 获取类的泛型 
* getSuperclass 获取父类？
* getGenericSuperclass 获取父类泛型 ？
* getPackage 获取包名 
* getInterfaces 获取接口？
* getGenericInterfaces 获取泛型接口 
* getComponentType 获取类型 
* getModifiers 获取修改器 
* getSigners 获取签名？
* getEnclosingMethod 获取封闭方法 
* getEnclosingMethodInfo 获取封闭方法的信息 
* getEnclosingConstructor 获取私有的构造器 
* getDeclaringClass 获取申明的类 
* getEnclosingClass 获取私有的类？
* getSimpleName 获取简单名称
* getTypeName 获取类型名称 
* getCanonicalName 获取规范名称 
* isAnonymousClass 是否是匿名内 
* isLocalClass 是否是本地class 是不是 反射或者动态代理出来的就是false ?
* isMemberClass 
* getSimpleBinaryName 获取二进制名称？
* getClasses 获取很多个class？
* getFields 获取public 标记的字段？
* getMethods 获取public 标记的方法 
* getConstructors 获取 public 的构造函数 
* getField 通过名称 获取 字段？
* getDeclaredClasses 获取申明的类？
* getDeclaredFields 获取所有的字段 不包含父类 
* getDeclaredMethods 获取所有声明的方法 不包含父类 
* getDeclaredConstructors 获取所有声明的构造函数 
* getResourceAsStream 将资源转换为流 
* getResource 获取资源地址 
* getProtectionDomain 获取保护域 ？
* desiredAssertionStatus 断言状态 
* getEnumConstants 获取枚举常量 
* cast 将对象强制转换为所表示的类或接口 
* asSubclass 
* getAnnotation
* isAnnotationPresent
* getAnnotationsByType
* getAnnotations
* getDeclaredAnnotation
* getDeclaredAnnotationsByType
* getDeclaredAnnotations
* getAnnotatedSuperclass
* getAnnotatedInterfaces






