## 资料
* [RandomAccessFile详解](https://blog.csdn.net/qq_40100414/article/details/120179117)
# 正文
RandomAccessFile是Java 输入/输出流体系中功能最丰富的文件内容访问类，它提供了众多的方法来访问文件内容，它既可以读取文件内容，也可以向文件输出数据。与普通的输入/输出流不同的是，RandomAccessFile支持"随机访问"的方式，程序可以直接跳转到文件的任意地方来读写数据。

* RandomAccessFile可以自由访问文件的任意位置。
* RandomAccessFile允许自由定位文件记录指针。
* RandomAccessFile只能读写文件而不是流。
