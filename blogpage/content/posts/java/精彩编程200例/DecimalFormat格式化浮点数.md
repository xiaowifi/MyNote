## 占位符
### 0表示数字占位符，实际位数不够时补零
比实际数字的位数多，不足的地方用0补上。
* new DecimalFormat(“00.00”).format(3.14) //结果：03.14
* new DecimalFormat(“0.000”).format(3.14) //结果： 3.140
* new DecimalFormat(“00.000”).format(3.14) //结果：03.140
整数部分比实际数字的位数少，整数部分不改动
小数部分比实际数字的位数少，根据小数部分占位符数量保留小数
* new DecimalFormat(“0.000”).format(13.146) //结果：13.146
* new DecimalFormat(“00.00”).format(13.146) //结果：13.15
× new DecimalFormat(“0.00”).format(13.146) //结果：13.15

### 比实际数字的位数多，不变
* new DecimalFormat("##.##").format(3.14) //结果：3.14
* new DecimalFormat("#.###").format(3.14) //结果： 3.14
* new DecimalFormat("##.###").format(3.14) //结果：3.14
### 整数部分比实际数字的位数少，整数部分不改动
小数部分比实际数字的位数少，根据小数部分占位符数量保留小数
* new DecimalFormat("#.###").format(13.146) //结果：13.146
* new DecimalFormat("##.##").format(13.146) //结果：13.15
* new DecimalFormat("#.##").format(13.146) //结果：13.15

````java
 DecimalFormat format=new DecimalFormat("#0.###");
        System.out.println(format.format(2.3456));//2.346
        System.out.println(format.format(12.3456));//12.346
        System.out.println(format.format(12));//12
````