# 前言
## 资料
参考资料：
* https://blog.csdn.net/chennai1101/article/details/103279477
* 
# 正文
NamedDomainObjectContainer 可以存储多个自定义参数。

例如我们需要自定义一个这个调调。
````aidl
testMyDoMain {// 这个对应代码中的  def textExt= project.getExtensions().create("testMyDoMain", TestExtension,project)
    testDomain {// 这个对应代码中的testDomain(Action<NamedDomainObjectContainer<TestDomainObj>> action)
        doman1 { // 这个其实是TestDomainObj 对象 
            setName("doman1 name")// 这个是TestDomainObj 的函数
            setMsg("doman1 msg")
        }
        doman2 {
            setName("doman2 name")
            setMsg("doman2 msg")
        }
    }
}
````
## TestDomainObj
````aidl
class TestDomainObj {
    String name="";
    String msg="";

    TestDomainObj(String name) {
        this.name = name
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getMsg() {
        return msg
    }

    void setMsg(String msg) {
        this.msg = msg
    }

    @Override
    public String toString() {
        return "TestDomainObj{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
````
## TestExtension
````aidl
public class TestExtension {
    NamedDomainObjectContainer<TestDomainObj> testDomainObjs;



    public TestExtension(Project project) {
        NamedDomainObjectContainer<TestDomainObj> container = project.container(TestDomainObj.class);
        testDomainObjs=container;
    }

    /**
     * 让其支持Gradle DSL 语法.主要还是这个函数，DLS 主要调用这个。
     * @param action
     */
    void testDomain(Action<NamedDomainObjectContainer<TestDomainObj>> action){
        action.execute(testDomainObjs);
    }
}
````
## plugin 
````aidl
 def textExt= project.getExtensions().create("testMyDoMain", TestExtension,project)
````
我们是不是可以通过脚本文件中的闭包名称去获取到他的参数。
## 获取参数
这个就有一个生命周期相关的问题了。build.gradle存在生命周期。所以需要在对应的生命周期中才可以获取到这个参数。
因为在插件引入的时候，这个对象是空的，脚本的命令与参数还没有存储到这个对象中来。
