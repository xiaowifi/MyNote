
## 前言
> 区分与静态代理。
> * 需要实现 InvocationHandler 实现 invoke方法。
retrofit 就是基于注解然后动态代理调用okhttp
# 正文
为啥要使用动态代理
* 面试会问
* 动态代理可以增加程序的灵活程度，如加入方法执行前后的判断
* 解耦的实现方案，可以将调用层和实现层进行分离，如Retrofit网络请求。
* 动态代理不需要接口的实现类，适用于IPC进程通信，将方法调用转成其他的逻辑。
* 动态代理可以解决程序执行流程。
动态代理含义:
* 定义：给目标对象提供一个代理对象，并由代理对象控制目标对象的引用。  

## 在本地生成一个class
````java
public class ProxyCreatClass  {

    public interface MyHello {
        void hello();
    }
    public static void main(String[] args) {
        // 生成一个class 文件
        File file=new File("ProxyHello.class");
        byte[] proxyHellos = ProxyGenerator.generateProxyClass("ProxyHello", new Class[]{MyHello.class});
        FileOutputStream outputStream= null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(proxyHellos);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
````
通过上面的代码，我们就可以在项目根目录下生成了一个ProxyHello.class.
````java
public final class ProxyHello extends Proxy implements MyHello {
    private static Method m1;
    private static Method m3;
    private static Method m2;
    private static Method m0;

    public ProxyHello(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final void hello() throws  {
        try {
            super.h.invoke(this, m3, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m3 = Class.forName("com.company.IOC.MyHello").getMethod("hello");
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
````
## 动态代理
````java
public class ProxyIOCDemo {
    public static interface SayHello {
        void say();
        void hello();
    }
    public static class MySayHello implements SayHello {
        @Override
        public void say() {
            System.out.println("小哥哥快来玩呀");
        }

        @Override
        public void hello() {
            System.out.println("大爷快来玩啊");
        }
    }

    public static void main(String[] args) {
        MySayHello hello=new MySayHello();
        // 生成了一个class，加载了这个class，实例化了这个对象（通过反射）
        SayHello sayHello = (SayHello) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), new ProxyHandler(hello));
        sayHello.say();
        sayHello.hello();
    }
    public static class ProxyHandler implements InvocationHandler{
        private Object object;

        /**
         * 代理对象 一定要持有被代理对象。
         * @param object
         */
        public ProxyHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // todo 如果代理的对象中存在多个 函数，建议判断一下函数名称。
            Object invoke;
            if (method.getName().equals("hello")){
                System.out.println("before invoke  在这里就可以插入自己的业务处理" +method.getName());
                invoke= method.invoke(object, args);
                System.out.println("end invoke 在这里就可以插入自己的业务处理 " +method.getName());
            }else {
                invoke=method.invoke(object,args);
            }
            return invoke;
        }
    }

}
````
* Proxy.newProxyInstance   生成了一个class，加载了这个class，实例化了这个对象（通过反射）
* 调用函数的时候，最终会调用到InvocationHandler的invoke 中。
* 动态代理主要作用就是在函数的执行前后插入自己的逻辑。
## 动态代理的原理 
结合上面的那个信息我们知道，动态创建一个class是调用了ProxyGenerator.generateProxyClass。而且动态代理就一句话。所以我们直接从newProxyInstance函数入手。
*  Class<?> cl = getProxyClass0(loader, intfs); 我们可以看到他调用了这句话，查找或生成指定的代理类。
* getProxyClass0 中有几行注释:
    >如果由给定加载器实现定义的代理类
    给定的接口存在，这将简单地返回缓存的副本；
    否则，它将通过ProxyClassFactory创建代理类。说明动态代理的生成的class只会创建一次。而这个函数中只有一句业务代码：proxyClassCache.get(loader, interfaces)
*  proxyClassCache = new WeakCache<>(new KeyFactory(), new ProxyClassFactory()); 所以上面调用get 是调用的是ProxyClassFactory的apply()函数。而在apply()
   函数中就有ProxyGenerator.generateProxyClass() 创建一个class。然后调用Native函数 defineClass0 去把这个生成的class加载进来。
* newProxyInstance 会拿到构造函数，然后去反射出对象。
# 结束


