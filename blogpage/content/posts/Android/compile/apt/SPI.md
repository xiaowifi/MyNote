SPI 机制 服务发现模式
* 通过反射 拿到实现类 去调用方法。
* 可用规模化的初始化。
* 可以做低代码注入等。

  SPI ，全称为 Service Provider Interface，
  是一种服务发现机制。它通过在ClassPath路径下的META-INF/services文件夹查找文件，自动加载文件里所定义的类

## 实现原理
通过读取文本文件中的类全名，在代码层面反射生成接口的实现类，再执行实现类中的方法
是一种策略模式+IOC注入的综合体。

````groovy
//对象初始化        
ServiceLoader<SPIService> load = ServiceLoader.load(SPIService.class);
        Iterator<SPIService> iterator = load.iterator();
        //完成方法注入调用
        while(iterator.hasNext()){//获取文本文件内容，得到需要的实现类的名字
            SPIService spiService=iterator.next();//反射注入实现类对象
            spiService.execute();//执行实现类中的方法
        }
````