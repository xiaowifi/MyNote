# 前言
LinkedTransferQueue一个由链表结构组成的无界阻塞队列。
## 资料
* 入列(添加元素)时，如果元素数量超过队列总数，会进行等待（阻塞），待队列的中的元素出列后，元素数量未超过队列总数时，就会解除阻塞状态，进而可以继续入列；
* 出列(删除元素)时，如果队列为空的情况下，也会进行等待（阻塞），待队列有值的时候即会解除阻塞状态，进而继续出列；
* 阻塞队列的好处是可以防止队列容器溢出；只要满了就会进行阻塞等待；也就不存在溢出的情况；
* 只要是阻塞队列，都是线程安全的；
# 正文 LinkedTransferQueue
LinkedTransferQueue 是一个高效阻塞无界链表队列。和SynchronousQueue.TransferQueue(公平模式)相比，它是可以统计长度，可以进行查询的；和LinkedBlockingQueue相比，它拥有更高的性能（使用CAS自旋）；和ConcurrentLinkedQueue相比，它拥有阻塞功能。
LinkedTransferQueue是一个由链表结构组成的无界阻塞TransferQueue队列。相对于其他阻塞队列，LinkedTransferQueue多了tryTransfer和transfer方法。可以算是 LinkedBolckingQueue 和 SynchronousQueue 和合体。LinkedTransferQueue是一种无界阻塞队列，底层基于单链表实现,其内部节点分为数据结点、请求结点；基于CAS无锁算法实现
LinkedTransferQueue实际上是ConcurrentLinkedQueue、SynchronousQueue（公平模式）和LinkedBlockingQueue的超集。而且LinkedTransferQueue更好用，因为它不仅仅综合了这几个类的功能，同时也提供了更高效的实现。

当我们不想生产者过度生产消息时，TransferQueue可能非常有用，可避免发生OutOfMemory错误。在这样的设计中，消费者的消费能力将决定生产者产生消息的速度。

````java
public class LinkedTransferQueueDemo {
    static LinkedTransferQueue<String> lnkTransQueue = new LinkedTransferQueue<String>();
    public static void main(String[] args) {
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new LinkedTransferQueueDemo().new Producer();
        Consumer consumer = new LinkedTransferQueueDemo().new Consumer();
        exService.execute(producer);
        exService.execute(consumer);
        exService.shutdown();
    }
    class Producer implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<3;i++){
                try {
                    System.out.println("等待生产...");
                    lnkTransQueue.transfer("A"+i);
                    System.out.println("生产完成了一个: A"+i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Consumer implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<3;i++){
                try {
                    System.out.println("等待消费...");
                    //
                    String s= lnkTransQueue.take();
                    System.out.println("消费完成 "+s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
````
输出日志：通过log 可以知道，当我们添加进去的时候，阻塞队列就可以立即获取到消息。并且马上消费掉。
````java
等待生产...
等待消费...
生产完成了一个: A0
等待生产...
消费完成 A0
等待消费...
生产完成了一个: A1
等待生产...
消费完成 A1
等待消费...
消费完成 A2
生产完成了一个: A2
````