import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class CopyApkTools {
    // 这个直接上
    static String apkPath = "/Users/nuoye/Downloads/311";
    static String saveApkPath = "/Users/nuoye/Downloads/save";

    public static void main(String[] args) {
        File apkFiles = new File(apkPath);
        Map<String, List<File>> allApks=new HashMap<>();
        getChildApk(allApks,apkFiles);
        Set<String> strings = allApks.keySet();
        for (String channel: strings){
            File channelFile=new File(saveApkPath+"/"+channel);
            String toPath=channelFile.getAbsolutePath()+"/release";
            for (File file: allApks.get(channel)){
                copyApkFile(file,toPath);
            }
        }
    }

    private static void copyApkFile(File file, String toPath) {
        String name = file.getName();
        File outFile = new File(toPath + "/" + name);
        if (!outFile.getParentFile().exists()){
            //如果上级目录不存在，就创建
            outFile.getParentFile().mkdirs();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(outFile);
            //新文件输出流
            byte[] buffer = new byte[1024 * 1000];
            int len;
            //将文件流信息读取文件缓存区，如果读取结果不为-1就代表文件没有读取完毕，反之已经读取完毕
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        }catch (Exception e){

        }

    }

    private static void getChildApk(Map<String, List<File>> allApks, File apkFiles) {
        if (apkFiles.isDirectory()){
            File[] files = apkFiles.listFiles();
            for (File file:files){
                getChildApk(allApks,file);
            }
        }else {
            String name = apkFiles.getName();
            System.out.println(name);
            if (name.endsWith(".apk")){
                //表示是apk文件
                String replace = name.replace(".apk", "");
                String[] split = replace.split("-");
                String channel= split[1];
                if (!allApks.containsKey(channel)){
                    allApks.put(channel,new ArrayList<>());
                }
                allApks.get(channel).add(apkFiles);
            }
        }
    }
}
