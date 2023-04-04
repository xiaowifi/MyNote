## 前言
一切皆文件的计算机系统下，数据从硬件到驱动到系统再到用户空间，这一系列设计都离不开IO操作。操作系統作为硬件指令的操作集合，而驱动则是数据读取标准。
那么一切IO操作都经过系统是无可厚非的。那么IO的瓶颈就存在于系统和用户空间的交互方案上了。
### IO对于系统的影响
#### 使用率
是指磁盘处理IO的时间百分比，过高的使用率通常意味着磁盘的IO存在性能瓶颈。
> 比如，下载视频的时候，往D盘下载，然后把D盘已经下载好的视频丢硬盘的过程中，就会发现复制过程会变慢。
#### 饱和度
是指磁盘处理IO的繁忙程度，过高的饱和度，意味着磁盘存在着严重的性能瓶颈。
> 和上面的那个类似。变慢是多个问题统一导致的。
#### IOPS 
是指每秒的IO请求数，适用于大量小文件的情景。
#### 吞吐量
是指每秒的IO请求大小，适用于大文件的情景。
响应时间是指：IO请求从发出到收到响应的时间间隔。
### io模型对于性能的影响
结合前文，IO模型是指系统与用户空间的交互方案，所以不同的交互方案用于解决不懂的业务诉求。常见的IO模型有:
* 阻塞IO
* 非阻塞IO
* 复用IO
* 信号驱动IO
* 异步IO
# 正文
IO的优化是在解决CPU的瓶颈问题，但是通常在C端很少出现，所以在学习IO的角度上而言，我们更注重探寻IO本质原理及序列化的应用于DEX 文件加壳脱壳。
## 内核层
### 内核linux的io栈
通常而言，我们将Linux存储系统的IO栈，由上到下分为3个层次，分别是文件系统层，通用块层和设备层。<br>
存储系统的IO通常是整个系统中最慢的一环，所以Linux通过多种缓存机制来优化IO效率。<br>
为了优化存储系统访问文件的性能，会使用页缓存，索引节点缓存，目录项缓存等多种缓存机制，以及减少对于下层块设备的直接调用。<br>
同时为了优化块设备的访问效率，会使用缓冲区，来缓存块设备的数据。
#### 文件系统层
文件系统层：包括虚拟文件系统和其他各种文件系统的具体实现，它为上层的应用程序，提供标准的文件访问接口，对下会通过通用块层，来存储和管理磁盘数据。
#### 通用块层
通用块层，包括块设备IO队列和IO调度器，它会对文件系统的IO请求进行排队，仔通过重新拍下和请求合并，才会发送给下一级的设备层。
#### 设备层
包括存储设备和相应的驱动程序，负责最终物理设备的IO操作。
### 内核空间对于IO的操作方案
页：4k数据为一页，一页数据是IO操作的基本单位。
> 所以这玩意儿涉及到数据对齐和空白数据填充，因为没法保证之前这个存储区域中没有数据，所以覆盖的时候需要覆盖空白数据。

空间局部性原理：在常规操作下，如果数据量较大的情况下可能会出现预占位4-16K的情况。
> 这玩意，没有懂
## JAVA 层
### JAVA 对于basic IO 的支持
说的是这玩意儿是阻塞式IO，等于说，这行代码没有指向完成之前，下一行不执行。
#### file
fileInputStream和fileOutPutStream提供基础文件流。对于文件操作，在系统中通常认为所有数据都核心单位是文件。
````aidl
        String path = "E:\\javaProject\\JAVADemo\\FilePutStreamDemo.txt";
        FileOutputStream outputStream = new FileOutputStream(path, false);
        outputStream.write("ABCDE".getBytes());
        outputStream.close();
        FileInputStream inputStream = new FileInputStream(path);
        int available = inputStream.available();
        if(available != 0){
            byte[] b = new byte[available];
            inputStream.read(b, 0, available);
            System.out.println(b);
            System.out.println(b.length);
            System.out.println(new String(b, "utf-8"));
        }
        inputStream.close();
````
上面例子就是，先存在一个数据到文件中，然后将文件的数据读取出来。这有一个问题，就是数据读取了就再也读取不到了。比如我先读取了A,然后后面就读取出来的数据就没有A了。
````aidl
        byte[] bytes = new byte[1];
        bytes[0]= (byte) inputStream.read();
        System.out.println(new String(bytes, "utf-8"));
````
上面代码就是读取了一次数据。但是只是读取了一位。
#### mem 
byteArrayInputStream/byteArrayOutputStream 将数据写入内存。
这个玩意，感觉没有太懂使用场景，主要是他的提供了不一样的API。可能音视频这种对于数据位校验处理的逻辑很适合这种模式吧。或者说一下协议。
#### print
printStream 提供对于字符的转换。 看名字应该是打印什么相关的。但是应该和转换相关。
#### piped 
pipedOutputStream和pipedInputStream 输入输出流绑定，当前输出流接受的是输入流的数据，一般用于通信，而且是同内存区块的通信。常见的是线程通信。
或者说对于文件转码后存储可能也可以用这个玩意。
#### object
objectInputStream和objectOutputStream 将对象序列表处理,需要涉及到文件的读写。
````aidl
        String path = "E:\\javaProject\\JAVADemo\\ObjectStreamDemo.txt";
        ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(path));
        outputStream.writeObject(new ObjectStreamEntity("hi","boy"));
        outputStream.close();
        ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(path));
        Object object = inputStream.readObject();
        ObjectStreamEntity entity= (ObjectStreamEntity) object;
````
#### data
dataInputStream和dataOutputStream 提供各类型的相关转换。
#### buffered
bufferedInputStream 和bufferedOutputStream 提供程序缓冲去支持。
#### random 
RandomAccessFile 提供位移与随机的数据加载。
### JAVA 对于NIO的支持
相对于BIO，NIO采用的是回调形式的返回对象数据。
#### mappedByteBuffer 缓冲区
JAVA BIO中对于大文件通常采用的是bufferedReader,bufferedInputstream这种带缓冲的
IO处理。不过在NIO中引入了mappedByteBuffer 操作大文件的方式，其读写性能极高。(所以文件读写慢，可以换一种读写方式)。

fileChannel 提供了map 方法把文件映射到虚拟内存，通常情况下，可以映射整个文件，如果文件较大的话，可以分段映射。

mappedBytebuffer 使用虚拟内存，因此分配map的内存大小不受jvm的xmx 参数限制，但也是有大小限制的。
当文件超过1.5G限制的时候，可以通过position参数重新map文件后的内容。mappedByteBuffer在处理大文件时候性能确实很高，但是还是存在一些问题，如内存占用，文件关闭不确定，被其打开的文件只有在垃圾回收的才会被关闭。
### 性能分析
* 从代码层面上而言，从硬盘将数据读取到内存中，都要经过文件系统的拷贝，并且数据拷贝操作由文件系统和硬件驱动实现，理论上拷贝数据都效率是一致的。
* 但是通过内存映射的方法访问硬盘的文件，效率要比read和write系统调用高，这是为什么。
* read是系统调用，首先将文件从硬盘拷贝到内核空间的一个缓冲区，再将数据拷贝到用户空间，实际上存在两次拷贝。write则是反向同理。
* map 也是系统调用，但没有进行数据拷贝，当缺页中断发生时候，直接将文件从硬盘拷贝到用户空间，所以只有一次拷贝。
### posix 可移植操作系统接口
posix把同步IO操作定义为导致进程阻塞直到IO完成的操作，反之则是异步IO。
#### 阻塞式IO模型
使用recv的默认参数移植等数据直到拷贝到用户空间，这段时间内进程始终阻塞。所以阻塞IO是代码同步IO。
#### 非阻塞IO模型
改变flags,让recv不管有没有获取到数据都返回，如果没有数据那么一段时间后再调用recv看看，如此循环。但他只是在检测无数据都时候是非阻塞的，在数据点到达的时候，依然要等待复制数据到用户空间，因此他还是同步IO。
#### IO复用模型
这里是在调用recv前先调用select或者poll，这两个系统调用都可以在内核准备好数据时告知用户进程，这个时候再调用recv一定是有数据都，隐藏这一过程中他说阻塞于select或者poll，而没有阻塞于recv。
这种IO模型比较特别，分个段，因为他能同时监听多个文件描述符FD.epoll也属于IO复用模型。
#### 信号驱动IO模型
通过调用sigaction注册信号函数，等内核数据准备好的时候系统中断当前程序，执行型号函数，
#### 异步IO模型
调用aio_read，让内核等数据准备好，并且复制到用户空间后执行实现指定好的函数，这才是真的异步IO
## okio 对于JAVA IO的优化
不管是读入还是写出，缓冲区的存在必然涉及到copy的过程，而如果涉双流操作，比如一个流读入，然后写入到另外一个输出流，在缓冲存在多情况下数据都走向是：
* 输入流到缓冲区
* 从输入流缓冲区copy 到数组
* 从数组copy到输出流缓冲区
* 从输出流缓冲区读取数据到输入流

而OKIO则是将两个缓冲合并成一份。所以ＯＫＩＯ的核心是解决双流操作的问题。


