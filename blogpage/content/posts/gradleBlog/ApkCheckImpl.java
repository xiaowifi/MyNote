package com.chips.cpsmerchant.plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: nuoye
 * @since: https://gitee.com/lalalaxiaowifi
 * @date: 2022/11/9 17:41
 * @description: 这个是描述
 * @update: [ 2022/11/9] [更改人] [更改内容]
 **/
public class ApkCheckImpl {
    // apk 的完整地址
    String apkPath="";
    // app 的包名
   static String apkPackage="com.chips.cpscustomer";
    // app 的启动 activity的名称。全路径。全路径
   static String apkStartActivity="com.chips.module_main.ui.start.TestStartActivity";

    public ApkCheckImpl(String apkPath) {
        this.apkPath = apkPath;
    }

    /**
     * List of devices attached
     * 6HJ4C19816044805       device product:SEA-AL10 model:SEA_AL10 device:HWSEA-A transport_id:8
     * @return
     * @throws IOException
     */
    public static Boolean devices() throws IOException{
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("adb devices -l");
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);
        String line = in.readLine();
        Boolean success=false;
        while (line!=null){
            if (line.contains("device")){
                success=true;
            }
            System.out.println(line);
            line=in.readLine();
        }
        System.out.println("获取完成，尝试卸载APP上已经安装的apk："+apkPackage);
        runtime.exec("adb uninstall "+apkPackage);
        System.out.println("卸载完成："+apkPackage);
        return success;
    }

    /**
     * 覆盖安装 当前的apk
     * @return
     * @throws IOException
     */
    public Boolean check() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("adb install -r "+apkPath);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);
        String line = in.readLine();
        Boolean success=false;
        while (line!=null){
            if (line.equals("Success")){
                //表示验证完成。
                success=true;
            }
            System.out.println(line);
            line=in.readLine();
        }
        System.out.println("验证完成"+success);
        if (success){
            return startPage();
        }
        return success;
    }

    /**
     * 没有问题的包
     * Starting: Intent { cmp=com.chips.cpscustomer/com.chips.module_main.ui.start.TestStartActivity }
     * Status: ok
     * LaunchState: COLD
     * Activity: com.huawei.android.launcher/.unihome.UniHomeLauncher
     * TotalTime: 2528
     * WaitTime: 2548
     * Complete
     * @throws IOException
     * 有问题的长这样。
     * Starting: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=com.chips.cpscustomer/com.chips.module_main.ui.start.TestStartActivity }
     * Status: ok
     * LaunchState: UNKNOWN (-1)
     * Activity: com.huawei.android.launcher/.unihome.UniHomeLauncher
     * WaitTime: 10222
     * Complete
     */
    public boolean startPage() throws IOException {
        System.out.println("开始启动APP");
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("adb shell am start -S -W  "+apkPackage+"/"+apkStartActivity);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);
        String line = in.readLine();
        Boolean success=false;
        // 因为上面语句的执行后，会通过-s 去关闭现有的APP，然后再次启动，所以说，这里只需要判断这个调调是不是冷启动就行。
        while (line!=null){
            if (line.contains("LaunchState")&&line.contains("COLD")){
                success=true;
            }
            System.out.println(line);
            line=in.readLine();
        }
        System.out.println("启动完成校验完成"+success);
        return success;
    }
}
