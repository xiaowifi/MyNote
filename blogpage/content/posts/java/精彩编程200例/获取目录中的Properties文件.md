properties 文件与map集合相同，不允许key的重复。根据key的hashCode存储。插入顺序并不是记录的顺序。

````java
 InetAddress localHost = InetAddress.getLocalHost();
        String canonicalHostName = localHost.getCanonicalHostName();
        String hostName = localHost.getHostName();
        String hostAddress = localHost.getHostAddress();
        byte[] address = localHost.getAddress();
        System.out.println(String.format("域名：%s , 主机名：%s ,ip %s",canonicalHostName,hostName,hostAddress));
        System.out.println(address.toString());
        String[] split = hostAddress.split("[.]");
        byte [] ipBytes=new byte[4];
        for(int i=0;i<4;i++){
        int m= Integer.parseInt(split[i]);
        byte b=(byte)(m&0xff);
        ipBytes[i]=b;
        }
        InetAddress byAddress = InetAddress.getByAddress(ipBytes);
        System.out.println(String.format("域名：%s , 主机名：%s ,ip %s",byAddress.getCanonicalHostName(),byAddress.getHostName(),byAddress.getHostAddress()));
        InetAddress byAddress1 = InetAddress.getByAddress(address);
        System.out.println(String.format("域名：%s , 主机名：%s ,ip %s",byAddress1.getCanonicalHostName(),byAddress1.getHostName(),byAddress1.getHostAddress()));

````
输出：
````java
域名：PC-20210218OWZI , 主机名：PC-20210218OWZI ,ip 172.16.133.229
[B@3feba861
域名：PC-20210218OWZI , 主机名：PC-20210218OWZI ,ip 172.16.133.229
域名：PC-20210218OWZI , 主机名：PC-20210218OWZI ,ip 172.16.133.229
````