## 前言
io 作为 一切皆文件系统的核心逻辑。用于处理计算机计算单元与存储单元的交互。IO模型在Linux上分为NIO 和BIO.
BIO 主要是需要经过系统调度，而NIO 则是直接对于存储单元直接操作(基于映射关系)。
通常在处理与硬件相关的通信的时候，我们是不走java io的，因为在bio模型下，走一次系统流程，读写不受自己控制。
同时存在进程切换到问题，所以Nio的方案和C直接读写方案在某种程度上更快，但是存在数据对齐的逻辑。
# 正文
我们主要是基于JAVA 下的IO APi 进行描述。
## BIO
BIO 相关的API 存在于下列包中。
````aidl
java.io
````
这里的输入与输出是针对于计算机程序而言的，从磁盘往程序里面读就需要输入流，从程序内部往磁盘中写入数据就是输出流。
### InputStream 输入流
输入流
#### FileInputStream 
文件输入流。
#### FilterInputStream
FilterInputStream的作用是用来封装其他的输入流，并为他们提供额外的功能，他的常用子类有bufferInputStream和
dataInputStream,bufferedInputStream 的作用是为了输入流提供缓冲功能，以及mark 和reset功能。
datainputStream 是用来装饰其他输入流，允许应用程序以与机器无关方式从底层输入流中读取JAVA数据类型。

* LineNumberInputStream 
* PushbackInputStream
* CheckedInputStream
* DigestInputStream
* DeflaterInputStream
* InflaterInputStream 
* DataInputStream

### OutputStream 输出流
输出流 

## NIO 
NIO 相关api 则是存在于
````aidl
java.nio 
````
