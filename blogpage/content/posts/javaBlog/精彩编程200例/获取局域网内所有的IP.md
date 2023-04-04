逻辑是拿到当前ip,然后遍历循环IP组，去ping。通过ping的结果判断是否是同一个局域网。
由于Java调用命令是以文件句柄的形式调用，Java并没有获取到shell，因此这里需要从cmd.exe调用dir，修改指令为cmd /c dir。同理，Linux下需要执行/bin/sh -c xxx
根本原因：Java并没有获取到cmd shell，因此我们不是和shell交互，因此直接输入指令是没用的
/c表示运行结束后关闭cmd.exe，另外/k表示不关闭

````java
   public static void main(String[] args) throws IOException {
    // ping 这个ip 然后获取输出结果。
        Process process = Runtime.getRuntime().exec("ping 172.16.133.229");
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);
        String line = in.readLine();
        while (line!=null){
            System.out.println(line);
            line=in.readLine();
        }
    }
````