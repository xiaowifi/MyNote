## ArrayBlockingQueue

用数组实现的有界阻塞队列。此队列按照先进先出（FIFO）的原则对元素进行排序。默认情况下不保证访问者公平的访问队列，所谓公平访问队列是指阻塞的所有生产者线程或消费者线程，当队列可用时，可以按照阻塞的先后顺序访问队列，即先阻塞的生产者线程，可以先往队列里插入元素，先阻塞的消费者线程，可以先从队列里获取元素。通常情况下为了保证公平性会降低吞吐量。

### LinkedBlockingQueue

基于链表的阻塞队列，同ArrayListBlockingQueue类似，此队列按照先进先出（FIFO）的原则对元素进行排序，其内部也维持着一个数据缓冲队列（该队列由一个链表构成），当生产者往队列中放入一个数据时，队列会从生产者手中获取数据，并缓存在队列内部，而生产者立即返回；只有当队列缓冲区达到最大值缓存容量时（LinkedBlockingQueue可以通过构造函数指定该值），才会阻塞生产者队列，直到消费者从队列中消费掉一份数据，生产者线程会被唤醒，反之对于消费者这端的处理也基于同样的原理。而LinkedBlockingQueue之所以能够高效的处理并发数据，还因为其对于生产者端和消费者端分别采用了独立的锁来控制数据同步，这也意味着在高并发的情况下生产者和消费者可以并行地操作队列中的数据，以此来提高整个队列的并发性能。
作为开发者，我们需要注意的是，如果构造一个LinkedBlockingQueue对象，而没有指定其容量大小，LinkedBlockingQueue会默认一个类似无限大小的容量（Integer.MAX_VALUE），这样的话，如果生产者的速度一旦大于消费者的速度，也许还没有等到队列满阻塞产生，系统内存就有可能已被消耗殆尽了。

## 相同：

LinkedBlockingQueue和ArrayBlockingQueue都是可阻塞的队列

内部都是使用ReentrantLock和Condition来保证生产和消费的同步；

当队列为空，消费者线程被阻塞；当队列装满，生产者线程被阻塞；

使用Condition的方法来同步和通信：await()和signal()

## 不同：

1、锁机制不同

LinkedBlockingQueue中的锁是分离的，生产者的锁PutLock，消费者的锁takeLock

而ArrayBlockingQueue生产者和消费者使用的是同一把锁；

2、底层实现机制也不同

LinkedBlockingQueue内部维护的是一个链表结构。

在生产和消费的时候，需要创建Node对象进行插入或移除，大批量数据的系统中，其对于GC的压力会比较大。

而ArrayBlockingQueue内部维护了一个数组

在生产和消费的时候，是直接将枚举对象插入或移除的，不会产生或销毁任何额外的对象实例。

3、构造时候的区别

LinkedBlockingQueue有默认的容量大小为：Integer.MAX_VALUE，当然也可以传入指定的容量大小

ArrayBlockingQueue在初始化的时候，必须传入一个容量大小的值

4、执行clear()方法

LinkedBlockingQueue执行clear方法时，会加上两把锁

5、统计元素的个数

LinkedBlockingQueue中使用了一个AtomicInteger对象来统计元素的个数，ArrayBlockingQueue则使用int类型来统计元素。
