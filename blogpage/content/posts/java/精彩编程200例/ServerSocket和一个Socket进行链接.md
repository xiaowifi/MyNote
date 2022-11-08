

````java
public  class MyServerSocket implements Runnable {

    @Override
    public void run() {
        try {
            // 浏览器 直接访问 http://172.16.133.229:1920/
            ServerSocket serverSocket = new ServerSocket(1920);
            System.out.println("服务开启成功");
            while (true) {
                System.out.println("等待客户机的连接.....");
                Socket accept = serverSocket.accept();
                System.out.println("连接成功....");
                System.out.println(accept.toString());
                System.out.println("netAddress:"+accept.getInetAddress().getHostAddress());
                System.out.println("netAddress:"+accept.getInetAddress().getCanonicalHostName());
                System.out.println("netAddress:"+accept.getInetAddress().getHostName());
                // 这个是socket的输入
                PrintWriter writer = new PrintWriter(accept.getOutputStream(), true);
                writer.println("hello 靓仔");
                BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream(), StandardCharsets.UTF_8));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println("reader:"+line);
                    line = reader.readLine();
                }
                writer.close();
                accept.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
````
运行上面的代码。通过浏览器直接访问。http://172.16.133.229:1920/。然后可以获取一下结果。
````java
服务开启成功
        等待客户机的连接.....
        连接成功....
        Socket[addr=/172.16.133.229,port=55063,localport=1920]
        netAddress:172.16.133.229
        netAddress:PC-20210218OWZI
        netAddress:PC-20210218OWZI
        reader:GET / HTTP/1.1
        reader:Host: 172.16.133.229:1920
        reader:Connection: keep-alive
        reader:Upgrade-Insecure-Requests: 1
        reader:User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36
        reader:Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
reader:Accept-Encoding: gzip, deflate
reader:Accept-Language: zh,zh-CN;q=0.9,en;q=0.8,zh-HK;q=0.7
reader:
````
那么，socket 自己写呢？
````java
 System.out.println("准备创建socket");
            Socket socket=new Socket("172.16.133.229", 1920);
            System.out.println("连接成功");
            System.out.println(socket.toString());
            System.out.println("MySocket netAddress:"+socket.getInetAddress().getHostAddress());
            System.out.println("MySocket netAddress:"+socket.getInetAddress().getCanonicalHostName());
            System.out.println("MySocket netAddress:"+socket.getInetAddress().getHostName());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("01 01 我是07 ");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
            String line = reader.readLine();
            while (line != null) {
                System.out.println("MySocket reader:"+line);
                line = reader.readLine();
            }
            System.out.println("获取返回------结束");
            writer.close();
            reader.close();
            socket.close();
````
```java
准备创建socket
连接成功
Socket[addr=/172.16.133.229,port=1920,localport=64651]
MySocket netAddress:172.16.133.229
MySocket netAddress:PC-20210218OWZI
MySocket netAddress:PC-20210218OWZI
MySocket reader:hello 靓仔
```