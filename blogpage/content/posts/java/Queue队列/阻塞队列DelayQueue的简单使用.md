# 前言
DelayQueue 一个支持延时获取元素的无界阻塞队列
## 资料
* 入列(添加元素)时，如果元素数量超过队列总数，会进行等待（阻塞），待队列的中的元素出列后，元素数量未超过队列总数时，就会解除阻塞状态，进而可#### 以继续入列；
* 出列(删除元素)时，如果队列为空的情况下，也会进行等待（阻塞），待队列有值的时候即会解除阻塞状态，进而继续出列；
* 阻塞队列的好处是可以防止队列容器溢出；只要满了就会进行阻塞等待；也就不存在溢出的情况；
* 只要是阻塞队列，都是线程安全的；

# 正文 DelayQueue
支持延时获取元素的无界阻塞队列，队列元素必须实现Delayed接口，在创建元素的时候可以指定多久才能从队列中获取当前元素。
时间到了才能从队列中获取到元素。
1. 淘宝订单业务:下单之后如果三十分钟之内没有付款就自动取消订单。
2. 饿了吗订餐通知:下单成功后60s之后给用户发送短信通知。（即等待多久之后才能取出元素，适用于取出之前要完成各种准备工作的对象，给其充分时间让其完成准备，然后再取出）
3. 关闭空闲连接。服务器中，有很多客户端的连接，空闲一段时间之后需要关闭之。
4. 缓存。缓存中的对象，超过了空闲时间，需要从缓存中移出。
5. 任务超时处理。在网络协议滑动窗口请求应答式交互时，处理超时未响应的请求等。


````java
public class DelayQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<MyDelayed> queue = new DelayQueue<>();
        queue.add(new MyDelayed("key1", 1));
        queue.add(new MyDelayed("key2", 5));
        queue.add(new MyDelayed("key3", 10));
        queue.add(new MyDelayed("key4", 15));
        queue.add(new MyDelayed("key5", 6));

        System.out.println("会一直阻塞，直到元素过期" + System.currentTimeMillis());
        // 移除并返回队列头部的元素     如果队列为空，则阻塞
        System.out.println(queue.take());
        System.out.println("会一直阻塞，直到元素过期" + System.currentTimeMillis());
        System.out.println(queue.take());
        System.out.println("会一直阻塞，直到元素过期" + System.currentTimeMillis());
        System.out.println(queue.take());
        System.out.println("会一直阻塞，直到元素过期" + System.currentTimeMillis());
        System.out.println(queue.take());
        System.out.println("会一直阻塞，直到元素过期" + System.currentTimeMillis());
        System.out.println(queue.take());
        System.out.println("会一直阻塞，直到元素过期" + System.currentTimeMillis());
    }
}

class MyDelayed implements Delayed {
    private String key;
    private long currentTime;
    private long expireTime;

    public MyDelayed(String key, long expireTime) {
        this.key = key;
        this.expireTime = expireTime;
        this.currentTime = System.currentTimeMillis();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取剩余的时间
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return expireTime - unit.MILLISECONDS.toSeconds(System.currentTimeMillis() - currentTime);
    }

    /**
     * 剩余时间升序排列
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        MyDelayed p = (MyDelayed) o;
        if (this.getDelay(TimeUnit.MICROSECONDS) > p.getDelay(TimeUnit.MICROSECONDS))
            return 1;
        if (this.getDelay(TimeUnit.MICROSECONDS) > p.getDelay(TimeUnit.MICROSECONDS))
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "MyDelayed{" +
                "key='" + key + '\'' +
                ", currentTime=" + currentTime +
                ", expireTime=" + expireTime +
                '}';
    }
}
````
返回结果：可以看到，这个调度是什么时间到了就返回谁。
````java
会一直阻塞，直到元素过期1667805537450
MyDelayed{key='key1', currentTime=1667805537450, expireTime=1}
会一直阻塞，直到元素过期1667805538457
MyDelayed{key='key2', currentTime=1667805537450, expireTime=5}
会一直阻塞，直到元素过期1667805542457
MyDelayed{key='key5', currentTime=1667805537450, expireTime=6}
会一直阻塞，直到元素过期1667805543458
MyDelayed{key='key3', currentTime=1667805537450, expireTime=10}
会一直阻塞，直到元素过期1667805547456
MyDelayed{key='key4', currentTime=1667805537450, expireTime=15}
会一直阻塞，直到元素过期1667805552457
````

