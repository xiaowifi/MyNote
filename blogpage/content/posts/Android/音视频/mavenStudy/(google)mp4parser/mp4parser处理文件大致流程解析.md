> 一个用于读取、写入和创建 MP4 文件的 Java API
> 适用于所有基于 ISO 14496 的文件（MP4、Quicktime、DCF、PDCF...）的通用解析器和编写器
## 资料
* [gitHub mp4parser](https://github.com/sannies/mp4parser)
* [maven主页地址](https://search.maven.org/artifact/com.googlecode.mp4parser/isoparser)
* [主页地址](https://code.google.com/archive/p/mp4parser/)
* [examples gradle 格式化包装](https://gitee.com/lalalaxiaowifi/java-mp4-parser-demo)
# 正文
无论是裁剪的Demo，还是两个视频轨道合并的Demo中。我们可以发现大致流程就是:
* 生成一个movie对象，对象中包含一个Track列表
* 获得一个Container 文件构造器
* 生成一个输入输出流
* 文件构造器器中生成文件
* 关闭输入输出流

那么我们就以官方Demo中的合并视频文件相关代码进行分解学习。
````java
 public static void main(String[] args) throws IOException {
        Movie m = MovieCreator.build("c:\\content\\ffmpeg.mp4");
        DefaultMp4Builder defaultMp4Builder = new DefaultMp4Builder();
        Container c = defaultMp4Builder.build(m);
        FileOutputStream fos = new FileOutputStream("C:\\content\\out.mp4");
        WritableByteChannel wbc = Channels.newChannel(fos);
        c.writeContainer(wbc);
    }
````
## 使用的class理解
### Movie 
通过上述的代码我们知道。Movie 作为输入的对象的解析存储。
#### 变量 
````java
    Matrix matrix = Matrix.ROTATE_0;
    List<Track> tracks = new LinkedList<Track>();
````
* Matrix 
* Track 轨道接口，用的是其子类
#### 提供的函数 
