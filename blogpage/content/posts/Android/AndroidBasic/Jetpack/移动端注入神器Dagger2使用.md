# 前言

Hint 使用的是dagger2实现。主要是用于IOC注入：
IOC注入是将原来由程序代码中主动获取的资源转变为由第三方获取，并使原来主动的代码转换为被动接收的方式，以达到解耦的效果。称为控制反转。
dagger2 是Google 提供的dagger的2.0 以上版本。，
## 资料
* [dagger2 github 地址](https://github.com/google/dagger)
* [dagger2 文档](https://dagger.dev/dev-guide/)  
* [Component的继承体系、局部单例](https://www.jianshu.com/p/f610a5606575)
* [神兵利器Dagger2一站式全解（详细总结）](https://blog.csdn.net/valada/article/details/106225715)
* [在Android中使用dagger2](https://developer.android.com/training/dependency-injection/dagger-android)
* [android 依赖注入](https://developer.android.com/training/dependency-injection)

* [在多模块中使用dagger](https://developer.android.com/training/dependency-injection/dagger-multi-module)

* 导包:
````html
 implementation 'com.google.dagger:dagger:2.4'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.4'
````
# 正文
* 注入的位置不能用多态。
* 默认对象是不是同一个，为了使得对象使用同一个，而不使用单例可以使用 @singleton 
* 通过@singleton 的单例是有生命周期的，绑定到那个class就和当前生命周期绑定。如果要app 单例就将component 做成单例去注入。同时因为doubleCheck的get函数的原因，所以说线程安全。
* 生成的class 在build-generated-（ap-generated-sourecs)-(bulidType)-out-包名 目录下
* 因为这个是通过注解处理器生成的代码。应该不存在多module使用上的问题。

## 简单使用
IOC注入是将原来由程序代码中主动获取的资源转变为由第三方获取，并使原来主动的代码转换为被动接收的方式，以达到解耦的效果。称为控制反转。
<br>
通过上述的这句话，我们可以知道，dagger2是用于提供对象的，通常而言，我们的JAVA 对象通过工厂，反射，单例，代理，直接通过构造函数进行创建，但是存在耦合性的问题。
通过工厂模式则可以降低耦合，但是需要写工厂模式的相关代码，同时调用的时候是主动触发获取。是否能够出现一种解决方案可以满足下列条件:
* 我只需要声明我要什么？
* 我不想关心对象怎么来的？
* 我不想去创建或者主动调用对象的生成  
* 我这个对象一个class或者整个什么周期只会存在一个或是少量几个。

我们将需求分析一下，只需要申明那需要注解，不关心对象怎么来的可以调用工厂，不想去创建，我可以生成对应的代码主动给你，前提是你总得调用我的代码吧，和databinding 或者viewbinding类似，域内唯一可以参考viewModel。
当然了dagger比databinding或者viewbinding更早。

基于这个逻辑，dagger 便出现了。
dagger 将上述的需求分为以下几个角色:

* 用于标记赋值的变量：@inject
* 用于提供对象@module
* 用于生成工厂的代码 @providers  
* 用于生成主动赋值的代码 @Component
* 用于标记生命周期内部的单例 @singleton
### 代码示例
比如，我MainActivity.claas 中需要通过dagger自动完成HttpObject 的赋值，那么我们需要先定义一个HttpObject的claas
#### 定义HttpObject 的class
````html
public class HttpObject {
}

````
#### 在MainActivity中使用赋值标记@inject
````html
public class MainActivity extends AppCompatActivity {

    @Inject
    HttpObject httpObject;
}
````
#### 创建一个类用于提供HttpObject @Module和 @Provides
````html
@Module
public class HttpModule {
    @Provides
    public HttpObject providerHttpObject(){
        //todo 处理逻辑。
        return new HttpObject();
    }
}
````
#### 注册赋值类的生成标记
````html

@Component(modules = {HttpModule.class})
public interface HttpComponent {
//注入的位置就写在参数上        不能用多态
void injectMainActivity(MainActivity mainActivity);
}
````
### 生成的文件解析
> 我android stuido 默认使用的jdk 版本不是java 8所以运行annotationProcessor的时候出现了一个异常。
> Unable to load class ‘javax.annotation.Generated‘.主要的解决思路就是：切换jdk 版本到1.8 <br>
> file->project structure -> sdk location -> jdk location 切换，部分as 版本 可能不一致。

通过上面的的简单的示例代码，我们直接编译，如果成功的化，就可以生成对应的claas了，编译成功尤其重要，因为我们需要调用编译成功的class 去注册。类似于viewbinding 一样。
#### module和provider
我们上面定义class的时候，module 标记在了class 上。Provides标记在了函数上。所以这两个注解生成的class也是同一个。
````html
public final class HttpModule_ProviderHttpObjectFactory implements Factory<HttpObject> {
  private final HttpModule module;

  public HttpModule_ProviderHttpObjectFactory(HttpModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public HttpObject get() {
    return Preconditions.checkNotNull(
        module.providerHttpObject(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<HttpObject> create(HttpModule module) {
    return new HttpModule_ProviderHttpObjectFactory(module);
  }
}
````
这上面的代码意思就很简单，将我们定义的 HttpModule 对象丢在一个工厂里面，这个工厂提供了一个get函数用于返回HttpModule.providerHttpObject() 返回的参数。
所以，所以我们需要知道HttpModule 在什么情况下被创建出来的。从我们自己写的代码逻辑上看，唯一和HttpModule有关系的是，我们定义 HttpComponent 的时候，在注解中使用了HttpModule，所以，HttpModule是否是在HttpComponent的生成
class 中使用了。
#### Component
```html
public final class DaggerHttpComponent implements HttpComponent {
  private Provider<HttpObject> providerHttpObjectProvider;

  private MembersInjector<MainActivity> mainActivityMembersInjector;

  private DaggerHttpComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static HttpComponent create() {
    return builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.providerHttpObjectProvider =
        HttpModule_ProviderHttpObjectFactory.create(builder.httpModule);

    this.mainActivityMembersInjector =
        MainActivity_MembersInjector.create(providerHttpObjectProvider);
  }

  @Override
  public void injectMainActivity(MainActivity mainActivity) {
    mainActivityMembersInjector.injectMembers(mainActivity);
  }

  public static final class Builder {
    private HttpModule httpModule;

    private Builder() {}

    public HttpComponent build() {
      if (httpModule == null) {
        this.httpModule = new HttpModule();
      }
      return new DaggerHttpComponent(this);
    }

    public Builder httpModule(HttpModule httpModule) {
      this.httpModule = Preconditions.checkNotNull(httpModule);
      return this;
    }
  }
}
```
通过上面生成的代码，我们可以将功能分为两个部分，1部分是Builder去创建对象，一部分是注册函数。
在Builder 中我们可以看到 如果外部没有传入一个HttpModule的时候，就会通过无参构造函数创建一个，所以默认的生成的对象中在module 对象没有被共享的时候，生成的对象是不一样的。
这个就可以回答上面的问题“HttpModule是如何创建出来的”。<br>
我们继续看注册函数，主要是下面两个函数：
```html
  private void initialize(final Builder builder) {

    this.providerHttpObjectProvider =
        HttpModule_ProviderHttpObjectFactory.create(builder.httpModule);

    this.mainActivityMembersInjector =
        MainActivity_MembersInjector.create(providerHttpObjectProvider);
  }

  @Override
  public void injectMainActivity(MainActivity mainActivity) {
    mainActivityMembersInjector.injectMembers(mainActivity);
  }

```
在创建DaggerHttpComponent的时候，会创建HttpModule_ProviderHttpObjectFactory的对象并且传入了HttpModule,和创建MainActivity_MembersInjector的对象并且传入了HttpModule_ProviderHttpObjectFactory的对象。
<br> 在injectMainActivity 函数中要求传入一个MainActivity去调用MainActivity_MembersInjector的injectMembers函数，那么injectMembers 执行了什么呢？
#### @Inject 
我们在需要被动赋值的class 中标记@Inject，而这个class 也会生成一个class
````html

public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<HttpObject> httpObjectProvider;

  public MainActivity_MembersInjector(Provider<HttpObject> httpObjectProvider) {
    assert httpObjectProvider != null;
    this.httpObjectProvider = httpObjectProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<HttpObject> httpObjectProvider) {
    return new MainActivity_MembersInjector(httpObjectProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.httpObject = httpObjectProvider.get();
  }

  public static void injectHttpObject(
      MainActivity instance, Provider<HttpObject> httpObjectProvider) {
    instance.httpObject = httpObjectProvider.get();
  }
}
````
我们通过DaggerHttpComponent的injectMainActivity函数可以知道，他执行调用这个class的injectMembers函数。
这个函数的内部实现也很简单，先判断空，然后将HttpModule_ProviderHttpObjectFactory 的get函数的返回值赋予给instance.httpObject。
### 尝试得出总结
通过上面简单的Demo和生成文件进行分析可以得出以下结论：
* 我们需要在主动调用DaggerHttpComponent去注册一次 然后才能对于标记对象进行赋值。
* 他获取值的时候是 注册的时候就赋值了的。 
* 他允许HttpModule通过外部传入进去。

所以注册函数可以这么写：
```java
 DaggerHttpComponent.create().injectMainActivity(this);
```
还可以这么写：
````java
 DaggerHttpComponent.builder().httpModule(new HttpModule()).build().injectMainActivity(this);
````

类关系图：

<img src="assets/Package%20dagger2demo-16643354173532.png" alt="Package dagger2demo" style="zoom:33%;" />

## 进阶

通过上面的简单使用，我们对于dagger2 有了一个简单的理解。但是具体的业务开发往往比这种Demo更加复杂。

### 一个module 中返回多个provider

这种情况好像出现的比较常见，但是我在调试的时候，多个component 不能注册到同一个class 里面，这种情况是不允许的，但是可以通过逻辑处理去规避这种问题，后面再描述。

#### 代码示例

````
@Module
public class AAndBModule {
    @Provides
    public AEntity getAEntity(){
        return new AEntity();
    }
    @Provides
    public BEntity getBEntity(){
        return new BEntity();
    }
}

@Component(modules = {AAndBModule.class})
public interface AndBComponent {
    void inject(TestClass testClass);
}

public class TestClass {
    @Inject
    AEntity entity;
    @Inject
    BEntity bEntity;

    public TestClass() {
        //DaggerAndBComponent.create().inject(this);
        DaggerAndBComponent.builder().aAndBModule(new AAndBModule()).build().inject(this);
    }
}
````

通过上面的代码我们可以知道，只有 AAndBModule和简单Demo 发生了改变，他提供了两个provider 函数，其他情况没有啥变化。

#### 生成文件

![image-20220928115356538](assets/image-20220928115356538.png)

通过生成的文件进行，我们发现，一个module中定义了多个Provider 就会生成多个Factory。我们知道获取对象都是基于factory 调用get函数，然后调用module中对应的函数的，所以其他生成文件中也将添加传入 多个Factory的方式。

component 改变内容：

````
  private void initialize(final Builder builder) {

    this.getAEntityProvider = AAndBModule_GetAEntityFactory.create(builder.aAndBModule);

    this.getBEntityProvider = AAndBModule_GetBEntityFactory.create(builder.aAndBModule);

    this.testClassMembersInjector =
        TestClass_MembersInjector.create(getAEntityProvider, getBEntityProvider);
  }
````

inject 的构造函数和create 也发生了改变。

### 一个component 多个module 

这种情况和一个module 多个provider 一样，会生成多个Factory

### 生成class生命周期内的单例 @Singleton

这种和viewModel 生命周期内部单例实现方式类似，但是需要使用注解@Singleton,在module，Provider和componet 上都添加@Singleton注解。

##### 代码示例

```
@Singleton
@Module
public class AModule {
    @Singleton
    @Provides
    public AEntity getAEntity(){
        return new AEntity();
    }

}


@Singleton
@Component(modules = {AModule.class})
public interface AndBComponent {
    void inject(TestClass testClass);
}
```

##### 生成的文件

这里主要是描述不一样的地方。

````
public final class DaggerAndBComponent implements AndBComponent {
  private Provider<AEntity> getAEntityProvider;

  private MembersInjector<TestClass> testClassMembersInjector;

  private DaggerAndBComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static AndBComponent create() {
    return builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.getAEntityProvider =
        DoubleCheck.provider(AModule_GetAEntityFactory.create(builder.aModule));

    this.testClassMembersInjector = TestClass_MembersInjector.create(getAEntityProvider);
  }

  @Override
  public void inject(TestClass testClass) {
    testClassMembersInjector.injectMembers(testClass);
  }

  public static final class Builder {
    private AModule aModule;

    private Builder() {}

    public AndBComponent build() {
      if (aModule == null) {
        this.aModule = new AModule();
      }
      return new DaggerAndBComponent(this);
    }

    public Builder aModule(AModule aModule) {
      this.aModule = Preconditions.checkNotNull(aModule);
      return this;
    }
  }
}
````

通过上面的代码，我们可以看到 在函数 initialize 中对于：

```
this.getAEntityProvider =
    DoubleCheck.provider(AModule_GetAEntityFactory.create(builder.aModule));
```

和正常情况不一致，如果不添加@Singleton,那么获取provider 的代码应该是：

```
this.getAEntityProvider = AModule_GetAEntityFactory.create(builder.aModule);
```

所以能够产生class 生命周期内部的单例就是一定是代码：DoubleCheck.provider() 完成的。

````
public final class DoubleCheck<T> implements Provider<T>, Lazy<T> {
  private static final Object UNINITIALIZED = new Object();

  private volatile Provider<T> provider;
  private volatile Object instance = UNINITIALIZED;

  private DoubleCheck(Provider<T> provider) {
    assert provider != null;
    this.provider = provider;
  }

  @SuppressWarnings("unchecked") // cast only happens when result comes from the provider
  @Override
  public T get() {
    Object result = instance;
    if (result == UNINITIALIZED) {
      synchronized (this) {
        result = instance;
        if (result == UNINITIALIZED) {
          instance = result = provider.get();
          /* Null out the reference to the provider. We are never going to need it again, so we
           * can make it eligible for GC. */
          provider = null;
        }
      }
    }
    return (T) result;
  }

  /** Returns a {@link Provider} that caches the value from the given delegate provider. */
  public static <T> Provider<T> provider(Provider<T> delegate) {
    checkNotNull(delegate);
    if (delegate instanceof DoubleCheck) {
      /* This should be a rare case, but if we have a scoped @Bind that delegates to a scoped
       * binding, we shouldn't cache the value again. */
      return delegate;
    }
    return new DoubleCheck<T>(delegate);
  }

  /** Returns a {@link Lazy} that caches the value from the given provider. */
  public static <T> Lazy<T> lazy(Provider<T> provider) {
    if (provider instanceof Lazy) {
      @SuppressWarnings("unchecked")
      final Lazy<T> lazy = (Lazy<T>) provider;
      // Avoids memoizing a value that is already memoized.
      // NOTE: There is a pathological case where Provider<P> may implement Lazy<L>, but P and L
      // are different types using covariant return on get(). Right now this is used with
      // DoubleCheck<T> exclusively, which is implemented such that P and L are always
      // the same, so it will be fine for that case.
      return lazy;
    }
    return new DoubleCheck<T>(checkNotNull(provider));
  }
}

````

我们通过DoubleCheck 的源码我们知道他实现了Provider 接口，而我们Factory也继承了这个接口。在provider 函数中，判断类型和是否为空后 返回了一个DoubleCheck 对象，所以 在injectMembers 函数中通过entityAndEntity2Provider.get()调用的是DoubleCheck  中的get函数。而这个DoubleCheck 中的get函数：

```
public T get() {
  Object result = instance;
  if (result == UNINITIALIZED) {
    synchronized (this) {
      result = instance;
      if (result == UNINITIALIZED) {
        instance = result = provider.get();
        /* Null out the reference to the provider. We are never going to need it again, so we
         * can make it eligible for GC. */
        provider = null;
      }
    }
  }
  return (T) result;
}
```

实现了线程安全，和对象唯一。而这个对象的唯一依托于Component的唯一.所以只要Component是同一个对象，那么在使用@Singleton后，就会返回一样的对象。默认情况下component 是类创建的对象。所以这种单例是是有使用区域限制的。

### 多个component 注册到同一个class

````
@Component(modules = {BModule.class})
public interface BComponent {
    void inject(TestClass testClass);
}

@Component(modules = {AModule.class})
public interface AComponent {
    void inject(TestClass testClass);
}
````

通过上面的代码，我们可以看到都是注册到TestClass。编译的时候就会提示：

````
cannot be provided without an @Inject constructor or from an @Provides- or @Produces-annotated method.
    void inject(TestClass testClass);
````

会不会是函数名称的问题？

```
cannot be provided without an @Inject constructor or from an @Provides- or @Produces-annotated method.
    void injectA(TestClass testClass);
```

果然，天真的我，嘻嘻。

那么应该怎么实现两个component 注册到同一个class 呢？分成两个component 一般都是业务的拆分导致的，最为粗暴的方式就是避免这种情况，直接整一个新的。但是如果避免不了呢？事实，dagger基于@Subcomponent提供几种思路

#### 方案一：Subcomponent 显示暴露另外一个component

````
@Subcomponent(modules = {BModule.class})
public interface BComponent {
    void inject(TestClass testClass);
}

@Component(modules = {AModule.class})
public interface AComponent {
    BComponent getBComponent();
}
````

从上面的代码我们可以看出，AComponent并没有注册函数，在AComponent中直接返回了BComponent。在生成的class 中并没有BComponent对应的class, 所以在使用上：

```
DaggerAComponent.builder().aModule(new AModule()).build().getBComponent().inject(this);
```

DaggerAComponent 的代码：

````
public final class DaggerAComponent implements AComponent {
  private Provider<AEntity> getAEntityProvider;

  private DaggerAComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static AComponent create() {
    return builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.getAEntityProvider = AModule_GetAEntityFactory.create(builder.aModule);
  }

  @Override
  public BComponent getBComponent() {
    return new BComponentImpl();
  }

  public static final class Builder {
    private AModule aModule;

    private Builder() {}

    public AComponent build() {
      if (aModule == null) {
        this.aModule = new AModule();
      }
      return new DaggerAComponent(this);
    }

    public Builder aModule(AModule aModule) {
      this.aModule = Preconditions.checkNotNull(aModule);
      return this;
    }
  }

  private final class BComponentImpl implements BComponent {
    private final BModule bModule;

    private Provider<BEntity> getBEntityProvider;

    private MembersInjector<TestClass> testClassMembersInjector;

    private BComponentImpl() {
      this.bModule = new BModule();
      initialize();
    }

    @SuppressWarnings("unchecked")
    private void initialize() {

      this.getBEntityProvider = BModule_GetBEntityFactory.create(bModule);

      this.testClassMembersInjector =
          TestClass_MembersInjector.create(
              DaggerAComponent.this.getAEntityProvider, getBEntityProvider);
    }

    @Override
    public void inject(TestClass testClass) {
      testClassMembersInjector.injectMembers(testClass);
    }
  }
}
````

我们和之前的DaggerAComponent进行对比：发现DaggerAComponent 中没有了注册函数，而注册函数被放到了BComponentImpl 中。

#### 方案二：容器中提供子容器的实现

代码示例：

```
public class TestClass {
    class AEntity{}
    class BEntity{}
    @Module()
    class AEntityModule{
        @Provides
        public AEntity getEntity(){
            return new AEntity();
        }
    }
    @Module()
    class BEntityModule{
        @Provides
        public BEntity getEntity(){
            return new BEntity();
        }
    }
    @Component(modules = {AEntityModule.class})
    interface AComponent{
        BComponent.Builder getBuilder();
    }
    @Subcomponent(modules = {BEntityModule.class})
    interface BComponent{
        void inject(TestClass testClass);
        @Subcomponent.Builder
        interface Builder {
            BComponent build();
            Builder requestModule(BEntityModule module);
        }
    }
    @Inject AEntity entity;
    @Inject BEntity bEntity;

    public TestClass() {
        DaggerTestClass_AComponent.builder().aEntityModule(new 		AEntityModule()).build().getBuilder().requestModule(new BEntityModule()).build().inject(this);
        Log.e("demo5", "TestClass: "+entity.hashCode() );
        Log.e("demo5", "TestClass: "+bEntity.hashCode() );
    }
}
```

通过上面的代码我们可以看到，在BComponent中我们保留了注入class但是提供了一个BComponent的build.而且BComponent的class 标记是@Subcomponent。在调用上和方案1的区别在于：

```java
DaggerTestClass_AComponent.builder().aEntityModule(new AEntityModule()).build().getBuilder().requestModule(new BEntityModule()).build().inject(this);
```

方案1中：

```
DaggerAComponent.builder().aModule(new AModule()).build().getBComponent().inject(this);
```

。通过这两个调用的对比，可以发现方案2 是可以对于BComponent提供自定义module的。感觉方案1和方案2都是差不多的，都是在容器中提供一个一个子容器的实现的调用。而且这个和之前的blog有一点区别就是并没有对于module中使用 subcomponents，我的dagger 版本中并没有这个调调，但是上诉代码还是可以注入。

#### 方案三：使用dependencies注解

代码示例：

```
public class TestClass {
    class AEntity {
    }

    class BEntity {
    }

    @Module
    class AEntityModule {
        @Provides
        public AEntity getEntity() {
            return new AEntity();
        }
    }

    @Module
    class BEntityModule {
        @Provides
        public BEntity getEntity() {
            return new BEntity();
        }
    }

    @Component(modules = {AEntityModule.class},dependencies = {BComponent.class})
    interface AComponent {
        void inject(TestClass testClass);
    }

    @Component(modules = {BEntityModule.class})
    interface BComponent {
        BEntity getEntity();
    }

    @Inject
    AEntity entity;
    @Inject
    BEntity bEntity;

    public TestClass() {
        DaggerTestClass_AComponent.builder().aEntityModule(new AEntityModule())
                .bComponent(DaggerTestClass_BComponent.builder().
                        bEntityModule(new BEntityModule())
                        .build())
                .build().inject(this);
        Log.e("demo5", "TestClass: " + entity.hashCode());
        Log.e("demo5", "TestClass: " + bEntity.hashCode());
    }
}
```

我们可以看到，BComponent和上面两个方案相比使用的不再是Subcomponent而是之前的Component。但是提供一个BEntityModule的返回对象的函数：

```
BEntity getBEntity();
```

同时AComponent上添加了@Component(modules = {AEntityModule.class},dependencies = {BComponent.class})。

在使用和赋值的时候：

```
DaggerTestClass_AComponent.builder().aEntityModule(new AEntityModule())
        .bComponent(DaggerTestClass_BComponent.builder().
                bEntityModule(new BEntityModule())
                .build())
        .build().inject(this);
```

module和 component必须赋值。

### 多个构造函数导致的依赖迷失@named

比如某个业务诉求上要求返回两个对象，但是两个对象的参数或者信息不一样，或者直接返回的对象的示例的实现不一样。这种情况下，我们就可以使用@name 注解去标记。代码示例：

```
public class TestClass {
    class User{
       public String name;
        public User(String name) {
            this.name = name;
        }
    }
    @Module
    class UserModule{
        @Named("user1")
        @Provides
        User User1(){return new User("张三");}
        @Named("user2")
        @Provides
        User User2(){return new User("张四");}
    }
    @Component(modules = {UserModule.class})
    interface UserComponent{
        void inject(TestClass testClass);
    }
    @Named("user1")
    @Inject
    User user;
    @Named("user2")
    @Inject
    User user2;

    public TestClass() {
        DaggerTestClass_UserComponent.builder().userModule(new UserModule()).build().inject(this);
        Log.e("demo8", "TestClass: "+user.name );
        Log.e("demo8", "TestClass: "+user2.name );
    }
}
```

这个主要是Provider和@Inject上添加@named。在某个博客上看到，这种情况叫做依赖注入迷失。当然了这种情况还有下面另外一种解决方案 @Qualifier

### 多个构造函数导致的依赖迷失@Qualifier

当我们去查看Named的源码的时候发现，他也是基于@Qualifier

```
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface Named {

    /** The name. */
    String value() default "";
}
```

所以，我们是不是可以自己搞一个类似的，只需要把类名称改了？

示例代码：

```
public class TestClass {
    @Qualifier
    @Documented
    @Retention(RUNTIME)
    public @interface User1 {

        /** The name. */
        String value() default "";
    }
    @Qualifier
    @Documented
    @Retention(RUNTIME)
    public @interface User2 {

        /** The name. */
        String value() default "";
    }
    class User{
       public String name;
        public User(String name) {
            this.name = name;
        }
    }
    @Module
    class UserModule{
        @User1
        @Provides
        User User1(){return new User("张三");}
        @User2
        @Provides
        User User2(){return new User("张四");}
    }
    @Component(modules = {UserModule.class})
    interface UserComponent{
        void inject(TestClass testClass);
    }
    @User1
    @Inject
    User user;
    @User2
    @Inject
    User user2;

    public TestClass() {
        DaggerTestClass_UserComponent.builder().userModule(new UserModule()).build().inject(this);
        Log.e("demo8", "TestClass: "+user.name );
        Log.e("demo8", "TestClass: "+user2.name );
    }
}
```

### 自定义注解

通过上面的内容，我们发现竟然可以基于@Qualifier自定义注解去设置类似于别名的内容。那么我们还可以定义什么不同的注解呢？最新版本中没有发现自定义@Singleton的意义，之前是component的组合模式下会报错，但是我Demo这个版本没有报错。

```
@Scope
@Documented
@Retention(RUNTIME)
public @interface Singleton {}
```

示例代码：

```
public class TestClass {
    @Scope
    @Documented
    @Retention(RUNTIME)
    public @interface UserScope {
    }

    class User {
        public String name;
        public User(String name) {
            this.name = name;
        }
    }
    @UserScope
    @Module
    class UserModule {
        @UserScope
        @Provides
        User User1() {
            return new User("张三");
        }
    }
    @UserScope
    @Component(modules = {UserModule.class})
    interface UserComponent {
        void inject(TestClass testClass);
    }
    @Inject
    User user;
    @Inject
    User user2;

    public TestClass() {
        DaggerTestClass_UserComponent.builder().userModule(new UserModule()).build().inject(this);
        Log.e("Demo10", "TestClass: "+user.hashCode());
        Log.e("Demo10", "TestClass: "+user2.hashCode());
    }
}
```

可以从上面的代码中可以看到我们自定义的局部单例的注解和dagger 提供的注解@Singleton的使用是相同的。

```
@Scope
@Documented
@Retention(RUNTIME)
public @interface UserScope {
}
```

### 外部提供参数+Lazy+Provider

因为我们module 可以自己设置进去，所以外部传参可以放module 的构造函数中。

示例代码：

```
public class TestClass {
    class User{
       public String name;
        public User(String name) {
            this.name = name;
        }
    }
    @Module
    class UserModule{
        String name;

        public UserModule(String name) {
            this.name = name;
        }

        @Provides
        User User1(){return new User(name);}

    }
    @Component(modules = {UserModule.class})
    interface UserComponent{
        void inject(TestClass testClass);
    }

    @Inject
    User user;
    @Inject
    Lazy<User> user0;
    @Inject
    Lazy<User> user1;
    @Inject
    Provider<User> user2;
    public TestClass() {
        DaggerTestClass_UserComponent.builder().userModule(new UserModule("李四")).build().inject(this);
      Log.e("demo8", "TestClass:user  "+user.hashCode() );
        Log.e("demo8", "TestClass:user  "+user.hashCode() );
        Log.e("demo8", "TestClass: Lazy user0 "+user0.get().hashCode() );
        Log.e("demo8", "TestClass: Lazy user0 "+user0.get().hashCode() );
        Log.e("demo8", "TestClass: Lazy user1 "+user1.get().hashCode() );
        Log.e("demo8", "TestClass:Provider user2 "+user2.get().hashCode() );
        Log.e("demo8", "TestClass:Provider user2 "+user2.get().hashCode() );
    }
}
```

运行结果：

````
E/demo8: TestClass:user  64817157
E/demo8: TestClass:user  64817157
E/demo8: TestClass: Lazy user0 107749466
E/demo8: TestClass: Lazy user0 107749466
E/demo8: TestClass: Lazy user1 226583179
E/demo8: TestClass:Provider user2 245333864
E/demo8: TestClass:Provider user2 86712193
````

我们可以看到，初始化后就立即注入的user的hashCode 多次打印是没有变化的。

同一个Lazy的User0的hashcode 是一样的，不同的Lazy的hashCode 的值是不一样的。

但是同一个Provider每次执行get获取到的User的hashCode 不一致。

想要理解正常注入，Lazy，Provider3个的区别。那么我们需要去查看dagger 生成的代码。

```
 @Override
  public void injectMembers(TestClass instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.user = userAndUser2AndUser0AndUser1Provider.get();
    instance.user0 = DoubleCheck.lazy(userAndUser2AndUser0AndUser1Provider);
    instance.user1 = DoubleCheck.lazy(userAndUser2AndUser0AndUser1Provider);
    instance.user2 = userAndUser2AndUser0AndUser1Provider;
  }
```

结合上面的局部单例的Demo，我们可以知道DoubleCheck的get函数维护了单例对象，所以同一个Lazy获取到的值是一样的，不同的Lazy 不同是因为调用lazy函数就会创建一个 DoubleCheck 对象，所以不同的Lazy 返回的值不同。

那么为啥Provier 每次调用get函数返回的值都不同呢？是因为他直接调用了我们提供的module的函数，而我们module中提供的对象每次都是通过构造函数创建出来的，所以说一样的。

# 结束 

在使用上，还是蛮复杂的。但是单纯的从功能上而言，这种解耦方式适合那种对于大项目模块划分得非常细的那种，那种就很适合这种模式，学习这个主要是要去理解Android 的hint。但是小项目，使用这个的意义不是太大的感觉，毕竟我理解得也不是太深刻，也有自己的思维局限性。而且代码解耦程度越高，说明对业务的分析理解就越详细。其实打算写理解的，主要是用得少，感觉理解是多么的苍白。

