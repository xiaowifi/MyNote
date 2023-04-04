## 前言
JAVA 中对于文件的删除和或者文件夹的删除也属于常用功能了。
# 正文
## 删除文件
我们要删除某个文件，需要判断当前文件是否存在。如果存在就删除。首先要明确一点那就是 路径错误了也会认为文件不存在。
### BIO代码示例
```aidl
        File file=new File("E:\\javaProject\\JAVADemo1\\FileOutPutLineDemo2.txt");
        File file2=new File("E:\\javaProject\\JAVADemo\\FileOutPutLineDemo2.txt");
        System.out.println(file.exists());
        System.out.println(file2.exists());
        if (file2.exists()){
            file2.delete();
        }
```
file 因为JAVADemo1 属于不存在的文件夹，所以判断出来认为文件不存在。
### NOI 代码示例
```aidl
 Files.delete(Paths.get("E:\\javaProject\\JAVADemo1"));
```
就这么一句话，就突出了一个字，质朴。问题是如果文件不存在会报错。和文件删除一致，这玩意没法删除文件夹。
````aidl
 boolean have= Files.deleteIfExists(Paths.get("E:\\javaProject\\JAVADemo\\ecode.txt"));
````
这玩意就可以删除没有的文件，不会报错，但是还是不能删除文件夹 
## 删除文件夹 
### BIO 代码示例。
````aidl
 private static void delFile(File groupFile) {
        System.out.println(groupFile.getName());
        if (groupFile.isDirectory()){
            File[] files = groupFile.listFiles();
            assert files != null;
            for (File file: files){
                if (file.isDirectory()){
                    delFile(file);
                }else {
                    file.delete();
                }
            }
        }else {
            groupFile.delete();
        }
    }
````
整体思路就是找到文件，然后删除。这种代码是没法删除掉空目录的。
删除更目录也需要转file后删除，当前可以先直接删除，判断是否是空目录。
### NIO
思路和BIO 一致，都是需要遍历出文件后删除。
````aidl
Path directory = Paths.get("E:\\javaProject\\JAVADemo\\build");
        Files.walkFileTree(directory, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return  FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
````
#### FileVisitResult 
这个调调用于表示逻辑，因为我们遍历文件可能是遍历到某种程度的停止了。
* FileVisitResult.CONTINUE 继续。 默认都是这个玩意
* FileVisitResult.TERMINATE 终止
* FileVisitResult.SKIP_SUBTREE 跳过子树
* FileVisitResult.SKIP_SIBLINGS 跳过同级