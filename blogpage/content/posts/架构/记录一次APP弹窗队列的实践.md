## 前言

2022年11月11日。很吉利的日子，APP功能日益复杂，在首页的的弹窗就超过4个，分别是首页的引导，APP的更新弹窗，APP的协议更新弹窗，APP的广告弹窗，权限申请弹窗等。因为有些弹窗是通过dialog 去实现的，当网络较慢的情况下，就会出现一个问题，APP都切换到其他板块去了，dialog 却弹出来了。于是我们打算优化这一流程。

# 正文

我们先对业务诉求进行分析。

* 我们需要将这些弹窗固定在首页弹起。
* 弹窗的弹起是有一定的顺序的。
* 这些弹窗部分依赖于一个网络接口，
* 有的却是本地参数判断。

## 设计

### 定义需要获取到外部参数：

依次弹起，我们可以分析下，可以使用一个队列存储。因为队列是先进先出的嘛。在首页弹起的功能，我们可以获取到首页的生命周期对象 lifecycleOwner，有些弹窗是dialogFragment 实现的，所以需要一个fragmentManger,然后就是上下文了,同时我们期望外部可以接受到我们队列处理完成的监听。而这些都可以通过一个队列管理器去存储他。那么我们需要的参数就大致是这些：

````
 	 FragmentManager fragmentManager;
    LifecycleOwner lifecycleOwner;
    Consumer<Boolean> queueEndConsumer;
````

### 定义队列对象操作的接口

我们对也是诉求中的‘‘这些弹窗部分依赖于一个网络接口，有的却是本地参数判断’’，因为本地数据是可以主线程获取到，我们可以在添加到队列中之前判断是否添加到队列，然后是网络请求部分，网络请求时异步的，所以我们需要定义一个接口去标记他准备好了，为了使用简单化，我们将处理完成和不需要弹起弹窗的逻辑混为一谈，不需要弹起弹窗的逻辑就包含了，网络错误，网络成功后判断不需要弹，登录过期等等。

````
public interface ViewCallBack {
    /**
     * 准备完成
     */
    void ready(int position);

    /**
     * 结束操作。
     */
    void endHandle(int position);
}
````

为啥要返回一个position，在我的预想里面，是通过排序队列去做的，而不是通过数组顺序。实际上我却是用的数组顺序，通过排序队列是便于后期迭代，比如这期的登录过期弹窗就没有处理，我们登录过期弹窗是接口返回的，虽然也可以强行调用一个必须登录的接口判断是否登录过期，但是感觉怪怪的。

### 定义队列对象 

队列对象需要负责提供是否添加到队列中，需要回调上面的操作接口，需要处理网络请求，本地逻辑，需要处理弹窗等等。

```
public abstract class ViewQueueItem {

    protected int position;
    protected ViewCallBack callBack;
    protected FragmentManager fragmentManager;
    protected LifecycleOwner lifecycleOwner;
    /**
     * 是否准备好了。
     */
    protected Boolean ready=false;
    /**
     * 是否已经完成
     */
    protected Boolean endHandle=false;

    public Boolean getEndHandle() {
        return endHandle;
    }

    public void setEndHandle(Boolean endHandle) {
        this.endHandle = endHandle;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * 有一些逻辑上不需要处理的。比如说APP 第一次安装启动。
     * 更新弹窗就不需要处理。协议更新弹窗也不需要处理。
     *
     * @return needHandle
     */
    public abstract boolean needHandle();

    /**
     * 设置监听回调
     *
     * @param callBack callBack
     */
    void setCallBack(ViewCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 开始准备
     */
    public abstract void gettingReady();


    /**
     * 开始操作
     */
    public abstract void startHandle();


    /**
     * 获取到当前队列的位置。
     *
     * @param position position
     */
    void setPosition(int position) {
        this.position = position;
    }
}
```

可以看到，我依旧把下标丢进去了。

### 调用对象的设计

我们上面需要在首页显示的时候，才显示弹窗，所以我们单纯的使用MutableLiveData 即可。

### 队列管理器设计

在添加完队列之后，需要调用一个开始队列。我们将添加的class与真实的队列分开。开始的时候，开启一个循环，判断是否添加到队列中，然后在循环中设置一些队列实现类的基本参数，包括position（在我的设计中，position 是需要外部传入的，但是架不住砍需求）。

当一个队列对象准备好的时候，获取并判断队列头部的对象是否准备好，如果准备好了就调用显示方法，如果没有准备好，就继续等等，当一个对象通过逻辑判断认为他结束的时候，我们将他从队列中移除出去。

这么有一个需要注意的点就是，需要判断队列头部对象是否频繁调用显示函数。

完整代码：

````
public class ViewQueueManger extends ViewModel implements Observer<ViewQueueItem> {
    FragmentManager fragmentManager;
    LifecycleOwner lifecycleOwner;
    Consumer<Boolean> queueEndConsumer;
    /**
     * 这个用来存储之前的队列。
     */
    List<ViewQueueItem> queueItems = new ArrayList<>();

    MutableLiveData<ViewQueueItem> queueLive = new MutableLiveData<>();
    /**
     * 这个是排序队列。
     */
    PriorityQueue<ViewQueueItem> queue = new PriorityQueue<>(10, (o1, o2) -> o1.position - o2.position);

    public ViewQueueManger addQueue(ViewQueueItem queueItem) {
        queueItems.add(queueItem);
        return this;
    }
    public ViewQueueManger setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public ViewQueueManger setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        queueLive.observe(lifecycleOwner, this);
        return this;
    }



    public ViewQueueManger setQueueEndConsumer(Consumer<Boolean> queueEndConsumer) {
        this.queueEndConsumer = queueEndConsumer;
        return this;
    }



    /**
     *
     */
    public void start() {
        int index = 0;
        for (ViewQueueItem item : queueItems) {
            // 设置下标。
            if (item.needHandle()) {
                item.setPosition(index);
                item.setLifecycleOwner(lifecycleOwner);
                item.setFragmentManager(fragmentManager);
                // 直接丢进队列。
                LogUtils.e("添加进入队列：" + item.getClass().getName());
                queue.add(item);
                index++;
                item.setCallBack(new ViewCallBack() {
                    @Override
                    public void ready(int position) {
                        item.setReady(true);
                        LogUtils.e("ready:" + position + "  " + item.getClass().getName());
                        doQueue();
                    }

                    @Override
                    public void endHandle(int position) {
                        item.setEndHandle(true);
                        LogUtils.e("endHandle:" + position + "  " + item.getClass().getName());
                        // 移除当前完成的本身
                        if (queue.contains(item)) {
                            queue.remove(item);
                            LogUtils.e("移除已经结束的内容" + item.getClass().getName());
                        }
                        // 然后获取下一个。
                        doQueue();

                    }
                });
                // 开始准备
                item.gettingReady();
            }
        }
    }

    private void doQueue() {
        if (queue.size() > 0) {
            // 获取头部的数据。如果准备好了就操心。
            ViewQueueItem element = queue.element();
            // 准备完成，并且没有操作完。
            if (element.getReady() && !element.getEndHandle()) {
                if (null == queueLive.getValue()) {
                    queueLive.postValue(element);
                    LogUtils.e("发送展示要求：" + element.getClass().getName());
                } else if (queueLive.getValue() != null && queueLive.getValue() != element) {
                    queueLive.postValue(element);
                    LogUtils.e("发送展示要求：" + element.getClass().getName());
                }
            }
        }else {
            LogUtils.e("---------------");
            if (null!=queueEndConsumer){
                queueEndConsumer.accept(true);
            }
        }
    }


    @Override
    public void onChanged(ViewQueueItem viewQueueItem) {
        // 接受到需要显示的消息。
        viewQueueItem.startHandle();
    }
}
````



# 总结

其实，这就是对于队列的一种单纯的使用，这种想法萌生已久，需要注意的是ViewQueueItem的实现类必须逻辑严谨，必须调用准备好了或者结束的函数。

写完后才发现，这个调调 不仅仅是处理界面，可以处理一系列需要排队的诉求，也可以把权限相关的加进来。