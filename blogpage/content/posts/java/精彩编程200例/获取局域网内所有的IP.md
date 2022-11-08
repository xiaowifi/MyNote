逻辑是拿到当前ip,然后遍历循环IP组，去ping。通过ping的结果判断是否是同一个局域网。
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