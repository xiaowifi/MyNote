## 前言
话说，有一天，在路上蹦蹦跳跳的，突然捡到一个没有混淆的jar文件，如获至宝。这玩意儿可不常见。那么我们可以对他做什么呢？
# 正文
拿着这个jar 文件迫不及待的打开了电脑。enmmmm，不导入没啥问题，一打开，就越来越刑了。那么我们如何读取到jar 文件中的内容呢。
首先，我们要明确一个东西，那就是jar 文件是压缩格式的文件包，那么直接压缩工具解压就行。所以我们可以自己把很多class打包成jar也是可以的，对吧。
在某些时候，通过解压工具解压这种手动操作。似乎不现实。那么怎么通过运行一个代码便可以得到呢。

那么这就需要了解几个类了：
* java.util.jar.JarFile 
* java.util.jar.JarOutputStream

JarFile 作为一个更加封装的工具，他可以帮助我们获取到一个jar文件里面的所有class 文件及其文件信息。JarOutputStream则是帮助我们把多个class合并成一个jar文件。

## 读取jar文件中的class
````aidl
        JarFile jarFile=new JarFile(jarPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()){
            JarEntry jarEntry = entries.nextElement();
            System.out.println(jarEntry.getName());

        }
        jarFile.close();
````
通过上面的代码，我们可以知道。通过JarEntry 可以获取类信息，而且：
```aidl
 InputStream inputStream = jarFile.getInputStream(jarEntry);
```
那么就可以获取到class的所有内容了，那么便可以通过输出流另存了。
## jar 文件复制
````aidl
        String jarPath="E:\\javaProject\\JAVADemo\\alicloud-android-utdid-1.1.5.3.jar";
        JarFile jarFile=new JarFile(jarPath);
        String newJarPath="E:\\javaProject\\JAVADemo\\copy.jar";
        JarOutputStream jarOutputStream=new JarOutputStream(Files.newOutputStream(Paths.get(newJarPath)));
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()){
            JarEntry jarEntry = entries.nextElement();
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            jarOutputStream.putNextEntry(jarEntry);
            int available = inputStream.available();
            byte [] all= new byte[available];
            inputStream.read(all);
            jarOutputStream.write(all);
            inputStream.close();
        }
        jarFile.close();
        jarOutputStream.closeEntry();
        jarOutputStream.close();
````
通过上面的代码，我们可以很明确的知道，写入一个jar 文件需要两步：
* jarOutputStream.putNextEntry(jarEntry);设置class的名称。
* jarOutputStream.write(all);设置class的输入流

所以说，如果我们不期望某个jar 中存在某些class，直接不输入就行。如果说，我们期望换class的输入流，那么换成自己需要的输入流就行。
## 意义
比如说，我们需要对于一些自己写的jar 进行埋点和修改。在Android 编译时技术中，一个transform对应则不同的目录，需要把class和jar自己复制到下一个transform定义的目录中。
那么，这中间，他并不管理输入文件和输出文件是否一致，这便有了操作空间，同时我们很多jar 文件其实在maven里面，所以直接改或者覆盖maven的那个文件时机上也不现实，所以我们只需要把需要更改的jar改了整到另外一个jar 文件里面。然后把他丢给下一个transform就行。

