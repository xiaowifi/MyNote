# 前言 
PriorityBlockingQueue 一个带优先级的队列，而不是先进先出队列。元素按优先级顺序被移除，而且它也是无界的，也就是没有容量上限，虽然此队列逻辑上是无界的，但是由于资源被耗尽，所以试图执行添加操作可能会导致 OutOfMemoryError 错误；
## 资料
# 正文 PriorityBlockingQueue
和 PriorityQueue 感觉有点像啊。
````java
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) {
        PriorityBlockingQueue<MyPeople> pbq = new PriorityBlockingQueue<>(10, new Comparator<MyPeople>() {
            @Override
            public int compare(MyPeople o1, MyPeople o2) {
                return o1.age- o2.age;
            }
        });
       for (int i=0;i<3;i++){
           pbq.add(new MyPeople("张：",(new Random().nextInt(100))));
       }
        System.out.println(pbq);
    
    }
}
class MyPeople {
    String name;
    int age;
    public MyPeople(String name, int age){
        this.name = name;
        this.age = age;
    }
    public String toString() {
        return "姓名："+name + " 年龄：" + age;
    }
}
````
输出:
````java
[姓名：张： 年龄：3, 姓名：张： 年龄：67, 姓名：张： 年龄：52]
````
