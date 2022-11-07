# 前言
ArrayBlockingQueue 有界队列，阻塞式,初始化时必须指定队列大小，且不可改变；，底层由数组实现。
用数组实现的有界阻塞队列。此队列按照先进先出（FIFO）的原则对元素进行排序。默认情况下不保证访问者公平的访问队列，所谓公平访问队列是指阻塞的所有生产者线程或消费者线程，当队列可用时，可以按照阻塞的先后顺序访问队列，即先阻塞的生产者线程，可以先往队列里插入元素，先阻塞的消费者线程，可以先从队列里获取元素。通常情况下为了保证公平性会降低吞吐量。

## 资料
# 正文 ArrayBlockingQueue
````java
public class ArrayBlockingQueueDemo {
    public static void main(String[] args) {
        final BlockingQueue queue = new ArrayBlockingQueue(3);
        for(int i=0;i<3;i++){
            new Thread(){
                public void run(){
                    while(true){
                        try {
                            Thread.sleep((long) (Math.random()*100));
                            System.out.println(Thread.currentThread().getName() + "准备放数据!");
                            queue.put(1);
                            System.out.println(Thread.currentThread().getName() + "已经放了数据，" +
                                    "队列目前有" + queue.size() + "个数据");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }.start();
        }

        new Thread(){
            public void run(){
                while(true){
                    try {
                        //将此处的睡眠时间分别改为100和1000，观察运行结果
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "准备取数据!");
                        queue.take();
                        System.out.println(Thread.currentThread().getName() + "已经取走数据，" +
                                "队列目前有" + queue.size() + "个数据");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.start();
    }

}
````
输出：可以看到，当满了的时候，只有获取了才能添加。
````java
Thread-1准备放数据!
Thread-1已经放了数据，队列目前有1个数据
Thread-2准备放数据!
Thread-2已经放了数据，队列目前有2个数据
Thread-2准备放数据!
Thread-2已经放了数据，队列目前有3个数据
Thread-1准备放数据!
Thread-0准备放数据!
Thread-2准备放数据!
Thread-3准备取数据!
````