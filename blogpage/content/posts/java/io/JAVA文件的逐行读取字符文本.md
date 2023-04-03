## 前言
文本文件逐行读取是一个非常简单的需求。这种需求同时是需要逐行解析的。
# 正文
## 代码示例
````aidl
 FileInputStream fileInputStream = new FileInputStream(new File("E:\\javaProject\\JAVADemo\\BeiShiGaoZhong_1.json"));
        InputStreamReader streamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(streamReader);
        java.lang.String line = reader.readLine();
        while (line != null) {
            String finalLine = line;
            line = reader.readLine();
        }
        reader.close();
````
## 解析
通过上面的Demo。我们可以看到，我们逐行读取文件主要了几个类。
### FileInputStream
这个将文件转换为文件输入流。
> FileInputStream从文件系统中的文件中获取输入字节。可用的文件取决于主机环境。
> FileInputStream用于读取原始字节流，如图像数据。要读取字符流，请考虑使用 FileReader。
### InputStreamReader 
将输入流转换为输入流的读取器对象。FileReader 则是InputStreamReader的子类 
> InputStreamReader是从字节流到字符流的桥梁：它读取字节并使用指定的字符集将其解码为字符。它使用的字符集可以通过名称指定，也可以显式给定，或者可以接受平台的默认字符集。
> InputStreamReader的read（）方法之一的每次调用都可能导致从底层字节输入流中读取一个或多个字节。为了能够有效地将字节转换为字符，可以从底层流中提前读取比满足当前读取操作所需的字节更多的字节。
### BufferedReader 
缓冲读写。 
> 从字符输入流中读取文本，缓冲字符，以便有效读取字符、数组和行。
> 可以指定缓冲区大小，也可以使用默认大小。默认值对于大多数用途来说足够大。