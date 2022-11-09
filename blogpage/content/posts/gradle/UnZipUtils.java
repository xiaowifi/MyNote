package com.chips.cpsmerchant.plugin;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author: nuoye
 * @since: https://gitee.com/lalalaxiaowifi
 * @date: 2022/10/31 10:55
 * @description: 这个是描述
 * @update: [ 2022/10/31] [更改人] [更改内容]
 **/
public class UnZipUtils {
    public static void unzipFile(String destDirPath,String path) {
        File resourceFile = new File(path);
        File[] resourceFiles = resourceFile.listFiles();
        for (File file : resourceFiles) {
            if(file.getName().endsWith("apk")){
                String zipPath = path+File.separator+file.getName();
                File srcFile = new File(zipPath);
                // 开始解压
                ZipFile zipFile = null;
                try {
                    zipFile = new ZipFile(srcFile, Charset.forName("GBK"));
                    Enumeration<?> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) entries.nextElement();

                        // 如果是文件夹，就创建个文件夹
                        if (entry.isDirectory()) {
                            String dirPath = destDirPath + "/" + entry.getName();
                            File dir = new File(dirPath);
                            dir.mkdirs();
                            //System.out.println("检测到目录："+entry.getName());
                        } else {
                            // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                            //System.out.println("检测到文件："+entry.getName());
                            File targetFile = new File(destDirPath + "/" + entry.getName());
                            // 保证这个文件的父文件夹必须要存在
                            if (!targetFile.getParentFile().exists()) {
                                targetFile.getParentFile().mkdirs();
                            }
                            targetFile.createNewFile();
                            // 将压缩文件内容写入到这个文件中
                            InputStream is = zipFile.getInputStream(entry);
                            FileOutputStream fos = new FileOutputStream(targetFile);
                            int len;
                            int BUFFER_SIZE = 1024*10*1000;
                            byte[] buf = new byte[BUFFER_SIZE];
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                            }
                            // 关流顺序，先打开的后关闭
                            fos.close();
                            is.close();
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("unzip error from ZipUtils", e);
                } finally {
                    if (zipFile != null) {
                        try {
                            zipFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("解压成功======"+destDirPath);
            }
        }
    }

}
