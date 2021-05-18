+++ date = "2021-2-23"
title = "Android上使用测试目录"
description = "Android上使用测试目录"
series = ["testing"]
featured = true 

+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
> [博客主页](http://lalalaxiaowifi.gitee.io/pictures/) <br>
> [Google提供Android测试教程文档](https://developer.android.google.cn/training/testing/fundamentals) <br>
> Android使用测试还是蛮重要的，毕竟减少run的时间，虽然run的时候可以摸鱼。嘻嘻。

测试金字塔说明了应用应如何包含三类测试（即小型、中型和大型测试）：

* 小型测试是指单元测试，用于验证应用的行为，一次验证一个类。
* 中型测试是指集成测试，用于验证模块内堆栈级别之间的互动或相关模块之间的互动。
* 大型测试是指端到端测试，用于验证跨越了应用的多个模块的用户操作流程。

沿着金字塔逐级向上，从小型测试到大型测试，各类测试的保真度逐级提高，但维护和调试工作所需的执行时间和工作量也逐级增加。因此，您编写的单元测试应多于集成测试，集成测试应多于端到端测试。虽然各类测试的比例可能会因应用的用例不同而异，但我们通常建议各类测试所占比例如下：小型测试占
70%，中型测试占 20%，大型测试占 10%。
<br>
[简书大佬提供的测试教程](https://www.jianshu.com/p/aa51a3e007e2)

## 配置环境

Android在创建项目的时候，会自动导入。

````aidl
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
````
### 简单使用
> [简书大佬写的Junit 4详细教程](https://www.jianshu.com/p/dba1290f9dc8)
通过上面的环境可以知道，使用的是Junit 4,所以创建测试用例的时候，应该选择Junit4.
* 创建一个class。
* AS也提供了一种快捷方式：选择对应的类->将光标停留在类名上->按下ALT + ENTER->在弹出的弹窗中选择Create Test 
    > 可以选择测试工具，和方法体，改名称也行。
* 选中方法的时候会自动创建带运行标记的@Test的方法，如果不选择就需要手动填写。
#### 编写测试，验证流程。
比如我创建一个下面这个class
````aidl
public class TestDemo {
    public boolean show(){
        return false;
    }
}

````
生成的测试用例
````aidl

public class TestDemoTest {

    @Test
    public void show() {
        new TestDemo().show();
    }
}
````
直接执行show 方法，如果没有抛错说明流程验证成功了。<br>
修改一下代码，验证返回值。
````aidl
  @Test
    public void show() {
        assertThat(new TestDemo().show(),is(true));
    }
````
因为我上面show的返回值是false，但是我判断的是true,就会运行终端提示：
````aidl
java.lang.AssertionError: 
Expected: is <true>
     but: was <false>
Expected :is <true>
Actual   :<false>
````
无论 Testdemo 调用Java中自定义内容，还是手写Java。都不会抛错，但是调用Android中的类会抛错，所以junit可能不适合用于测试包含Android PAI的测试。
### 单元测试环境配置

在app/build.gradle 中配置.便于单元测试可以访问编译版本资源。

````aidl
    android {
        // ...

        testOptions {
            unitTests {
                includeAndroidResources = true
            }
        }
    }
    
````

### mockito 隔离单元测试
> 因为Junit 4 在使用Android API 的时候出现了些问题. Mockito库支持模拟创建，验证和存根。
> 所以 根据大佬说，这个是要模拟创建的问题。那就应该使用需要模拟创建的测试。<br>
> 在搜索了多个大佬写的mockito android 教程后，直接找到 [这个大佬写的很详细的mockito android](https://www.jianshu.com/p/a3b59fad17e6)

> 此外在写单元测试的过程中，一个很普遍的问题是，要测试的目标类会有很多依赖，这些依赖的类/对象/资源又会有别的依赖，从而形成一个大的依赖树，要在单元测试的环境中完整地构建这样的依赖，是一件很困难的事情。
> 所幸，我们有一个应对这个问题的办法：Mock。简单地说就是对测试的类所依赖的其他类和对象，进行mock － 构建它们的一个假的对象，定义这些假对象上的行为，然后提供给被测试对象使用。被测试对象像使用真的对象一样使用它们。用这种方式，我们可以把测试的目标限定于被测试对象本身，就如同在被测试对象周围做了一个划断，形成了一个尽量小的被测试目标。
> <br> 所以，这个调调的核心能力是提供了对象创建。不需要手动new 一个了。

但是他的运行好像是基于junit 的。哈哈哈哈。
#### 使用核心创建能力

````aidl
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * 使用mockito
 */
public class MockitoDemoTest {
    @Test
    public void toMockito(){
        //通过模拟创建一个对象。
        List mockList = Mockito.mock(List.class);
        //当其调用mockList.get(0) 返回一个 one
        Mockito.when(mockList.get(0)).thenReturn("one");
        // 调用mockList.get(0) 并且赋值给str ,因为上面设置了 调用的时候返回one,这个时候str 应该等于one.
        String str= (String) mockList.get(0);
        // 断言为真，str为one,为假应该就是输出不通过。
        Assert.assertTrue("one".equals(str));
        //这个同理
        Assert.assertTrue(mockList.size()==0);
        // 验证mockList.get(0) 是否被调用。 如果没有被调用，Wanted but not invoked: 会验证失败
        Mockito.verify(mockList).get(0);
    }
}

````
#### 调用Android资源
```aidl
import android.content.Context;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 使用这个调调调用Android 资源。
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoDemoForAndroidTest {

    @Mock
    Context mMockContext;

    /**
     * 使用Android 资源
     */
    @Test
    public void getAndroid(){
        // 当获取方法的时候，改变返回值为小哥哥。
        Mockito.when(mMockContext.getString(R.string.app_name)).thenReturn("小哥哥");
        // 获取应用名称 因为上面代码监听更改了，所以这app_name 应该为小哥哥
        String app_name = mMockContext.getString(R.string.app_name);
        // 添加断言。判断是否是小哥哥
        Assert.assertTrue("小哥哥".equals(app_name));
        // 判断 方法是否调用
        Mockito.verify(mMockContext).getString(R.string.app_name);
    }
}

```
上面代码和第一个代码还是有明显的区别的。使用了 @Mock注解 生成的对象，而不是 Mockito.mock(Context.class);所以类上的注解：@RunWith(MockitoJUnitRunner.class) 是必须存在的。
#### mockito注解和方法
方法:
*  Mockito.mock(List.class) 创建对象。
*  Mockito.when(mockList.get(0)).thenReturn("one"); 监听mockList.get(0) 并且设置其返回值为one
   > then 包含设置返回值，设置调用方法和设置异常抛出等等。
*  Mockito.verify(mockList).get(0);验证调用代码。
*  InOrder inOrder=Mockito.inOrder(list0); 代码顺序执行验证。
````aidl
/**
 * 调用顺序验证
 */
public class MockitInorderTest {
    @Test
    public void inorder(){

        List list0 = Mockito.mock(List.class);
        list0.add("0");
        list0.add("1");
        list0.add("2");
        // 创建 顺序添加流程。如果执行顺序不是 0,1,而是其他类型，比如1.0,0,2，就会验证失败、但是只要011，会失败，012，就会成功。
        InOrder inOrder=Mockito.inOrder(list0);
        inOrder.verify(list0).add("0");
        inOrder.verify(list0).add("1");
        // 上面是对单个对象进行添加监听。
        List list1 = Mockito.mock(List.class);
        List list2 = Mockito.mock(List.class);
        list1.add("0");
        list2.add("1");
        InOrder inOrder1 = Mockito.inOrder(list1, list2);
        inOrder1.verify(list1).add("0");
        inOrder1.verify(list2).add("1");
        // 因为这个是监听代码执行，所以同样代码 只能执行一次。比如说有多次list0.add("0"); 就不行，但是你可以添加其他参数，而不是list0.add("0");、
        
    }
}

````
* Mockito.verifyNoInteractions(list3) 确保 list3 对象未被调用。所以没有被调用的就可以删除了。嘻嘻，要反逻辑使用。
* Mockito.doThrow(),doAnswer()，doNothing()，doReturn() 和doCallRealMethod(),用于修改已有的when()监听。
* Mockito.spy(),用于复制一个new的对象，然后使用do相关方法进行监听。例如这个样子:
  >   Mockito.doReturn("foo").when(spy).get(0);
*   
注解:
* @Mock 便捷创建对象，需要和类注解 @RunWith(MockitoJUnitRunner.class) 
* @captor 简化了argumentCaptor的创建，而这个还没有搞懂。
* @spy 这个是复制new对象的副本。没搞懂怎么用
* @InjectMcks 自动将模拟或者间谍字段注入测试对象。没搞懂场景。

### PowerMockito 针对final,private,static
> 因为Mockito对于 final,private,static等方法不能mock，所以使用PowerMockito.
> [github地址](https://github.com/powermock/powermock/wiki/Getting-Started)
> <br> [简书大佬的教程](https://www.jianshu.com/p/6631bd826677)


#### 环境配置
````aidl
  testCompile 'org.powermock:powermock-module-junit4:1.6.5'
    testCompile 'org.powermock:powermock-module-junit4-rule:1.6.5'
    testCompile 'org.powermock:powermock-api-mockito:1.6.5'
    testCompile 'org.powermock:powermock-classloading-xstream:1.6.5'
````
### 阿里系TestableMock
> 讲道理，我今天早上看了一早上的mockito,然后发现还要看PowerMockito,然后发现了这个调调。
> [testableMock github 官网](https://github.com/alibaba/testable-mock?utm_source=gold_browser_extension)
> <br> 按照他们的说法是，他们很牛逼。ok .先记一下。

### Robolectric
> Robolectric可以解决此类问题，它的设计思路便是通过实现一套JVM能运行的Android代码，从而做到脱离Android环境进行测试。
> [robolectric 教程地址](http://robolectric.org/getting-started/) ,ok.懂了。主要看这个调调，其他的先放弃。这整界面相关的单元测试，卧槽深得我心。
#### 环境配置
````aidl
//导入
testImplementation 'org.robolectric:robolectric:4.4'
// 环境配置。
android {
  testOptions {
    unitTests {
      includeAndroidResources = true
    }
  }
}

````





###  junit 通用注解
* @Before：初始化方法 对于每一个测试方法都要执行一次（注意与BeforeClass区别，后者是对于所有方法执行一次）
* @After：释放资源 对于每一个测试方法都要执行一次（注意与AfterClass区别，后者是对于所有方法执行一次）
* @Test：测试方法，在这里可以测试期望异常和超时时间
* @Test(expected=ArithmeticException.class)检查被测方法是否抛出ArithmeticException异常
* @Ignore：忽略当前测试方法，一般用于测试方法还没有准备好，或者太耗时之类的
* @BeforeClass：针对所有测试，只执行一次，且必须为static void
* @AfterClass：针对所有测试，只执行一次，且必须为static void
* @Test(timeout=100) public void method()	性能测试，如果方法耗时超过100毫秒->失败
* @FixMethodOrder(MethodSorters.NAME_ASCENDING) public class TestClass{}	使得该测试类中的所有测试方法都按照方法名的字母顺序执行，可以指定3个值，分别是DEFAULT、JVM、NAME_ASCENDING


                                                                                          