````java
 // 空闲
         int free=(int) Runtime.getRuntime().freeMemory()/1024;
         // 内存总量
         int total=(int) Runtime.getRuntime().totalMemory()/1024;
         // 最大
         int maxMemory=(int) Runtime.getRuntime().maxMemory()/1024;
         //Java虚拟机可用的处理器数。
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(String.format("空闲：%skb 总量：%skb 最大：%skb 处理器数量：%s",free,total,maxMemory,processors));
        //空闲：244119kb 总量：249344kb 最大：-500224kb 处理器数量：6
````