## 前言
这个需求也很简单。就是讲究逐行写入。
# 正文
## 代码示例
### 字节输入
````aidl
  File file=new File("E:\\javaProject\\JAVADemo\\FileOutPutLineDemo.txt");
        // append表示是否在后面追加。false 表示不追加，true 表示追加
        FileOutputStream outputStream=new FileOutputStream(file,true);
        outputStream.write("QQQQQQQQ\n".getBytes());
        outputStream.close();
````
### 字符串写入
````aidl
 File file=new File("E:\\javaProject\\JAVADemo\\FileOutPutLineDemo2.txt");
        FileOutputStream outputStream=new FileOutputStream(file,true);
        OutputStreamWriter writer=new OutputStreamWriter(outputStream);
        writer.append("111111111111111\n");
        writer.write("2222222222222\n");
        writer.append("333333333333\n");
        writer.write("44444444\n");
        // 这个是裁剪字符串的。
        writer.write("1234567890\n",2,5);
        writer.flush();
        writer.close();
        outputStream.close();
````
这玩意儿  writer.close() 和writer.flush(); 都可以把数据写入进去。但是close是关流。