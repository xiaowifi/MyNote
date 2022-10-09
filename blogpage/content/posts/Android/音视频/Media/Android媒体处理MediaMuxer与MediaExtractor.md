## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [MediaExtractor解析和封装mp4](https://www.cnblogs.com/renhui/p/7474096.html)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)

# 正文
## MediaExtractor 
MediaExtractor 是Android 系统中对于音视频轨道进行分解处理的一个工具。
顾名思义，MediaExtractor 可以从数据源中**提取**经过编码的媒体数据。MediaExtractor 不仅可以解析本地媒体文件，还可以解析网络媒体资源。

主要是以下几个步骤：

1、创建实例

```
MediaExtractor mediaExtractor = new MediaExtractor();
```

2、设置数据源

```
mediaExtractor.setDataSource(path);
```

3、获取数据源的轨道数，切换到想要的轨道

```
// 轨道索引
int videoIndex = -1;
// 视频轨道格式信息
MediaFormat mediaFormat = null;
// 数据源的轨道数
int trackCount = mediaExtractor.getTrackCount();
for (int i = 0; i < trackCount; i++) {
    MediaFormat format = mediaExtractor.getTrackFormat(i);
    String mimeType = format.getString(MediaFormat.KEY_MIME);
    if (mimeType.startsWith("video/")) {
        videoIndex = i;
        mediaFormat = format;
        break;
    }
}
// 切换到想要的轨道
mediaExtractor.selectTrack(videoIndex);
```

4、对所需轨道数据循环读取读取每帧，进行处理

```
while (true) {
    // 将样本数据存储到字节缓存区
    int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
    // 如果没有可获取的样本，退出循环
    if (readSampleSize < 0) {
        mediaExtractor.unselectTrack(videoIndex);
        break;
    }
    ...
    ...
    // 读取下一帧数据
    mediaExtractor.advance();
}
```

5、完成后释放资源

```
mediaExtractor.release();
```
### MediaExtractor 是否可以直接解析H264或者aac 文件？
答案是否定的。
直接设置一个h264和aac 文件会报错：java.io.IOException: Failed to instantiate extractor.

### MediaExtractor 常用函数
* selectTrack 切换到想要的轨道
* readSampleData 读取当前帧的数据   
* advance() 读取下一帧。  
* unselectTrack 取消追踪。传入的是track的id。通常和selectTrack，
* getSampleTime 获取当前帧的时间，呈现时间（以微秒为单位）

## MediaMuxer
MediaMuxer 是Android 系统中对于音视频合成的一个工具
同样，名字已经说明了一切。MediaMuxer 可以将多个流混合**封装**起来，支持 MP4、Webm 和 3GP 文件作为输出，而且从 Android N 开始，已经支持在 MP4 中混合 B 帧了。

MediaMuxer 的作用是生成音频或视频文件；还可以把音频与视频混合成一个音视频文件。

主要是以下几个步骤：

1、创建实例

```
MediaMuxermediaMuxer = new MediaMuxer(path, format);
```

path: 输出文件的名称；format: 输出文件的格式，当前只支持 MP4 格式。

2、将音频轨或视频轨添加到 MediaMuxer，返回新的轨道

```
int trackIndex = mediaMuxer.addTrack(videoFormat);
```

3、开始合成

```
mediaMuxer.start();
```

4、循环将音频轨或视频轨的数据写到文件

```
 while (true) {
     // 将样本数据存储到字节缓存区
     int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
     // 如果没有可获取的样本，退出循环
     if (readSampleSize < 0) {
         mediaExtractor.unselectTrack(videoIndex);
         break;
     }
     bufferInfo.size = readSampleSize;
     bufferInfo.flags = mediaExtractor.getSampleFlags();
     bufferInfo.offset = 0;
     bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
     mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo);
     // 读取下一帧数据
     mediaExtractor.advance();
 }
```

5、完成后释放资源

```
mediaMuxer.stop();
mediaMuxer.release();
```


## 从 MP4 文件中分离出视频生成无声视频文件

```
/**
  * 分离视频的视频轨，输入视频 input.mp4，输出 output_video.mp4
  */
private void extractVideo() {
    MediaExtractor mediaExtractor = new MediaExtractor();
    MediaMuxer mediaMuxer = null;
    File fileDir = FileUtil.getExternalAssetsDir(this);
    try {
        // 设置视频源
        mediaExtractor.setDataSource(new File(fileDir, VIDEO_SOURCE).getAbsolutePath());
        // 轨道索引
        int videoIndex = -1;
        // 视频轨道格式信息
        MediaFormat mediaFormat = null;
        // 数据源的轨道数
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat format = mediaExtractor.getTrackFormat(i);
            String mimeType = format.getString(MediaFormat.KEY_MIME);
            if (mimeType.startsWith("video/")) {
                videoIndex = i;
                mediaFormat = format;
                break;
            }
        }
        // 切换到想要的轨道
        mediaExtractor.selectTrack(videoIndex);
        File outFile = new File(FileUtil.getMuxerAndExtractorDir(this), OUTPUT_VIDEO);
        if (outFile.exists()) {
            outFile.delete();
        }
        mediaMuxer = new MediaMuxer(outFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        // 将视频轨添加到 MediaMuxer，返回新的轨道
        int trackIndex = mediaMuxer.addTrack(mediaFormat);
        ByteBuffer byteBuffer = ByteBuffer.allocate(mediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE));
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        mediaMuxer.start();
        while (true) {
            // 将样本数据存储到字节缓存区
            int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
            // 如果没有可获取的样本，退出循环
            if (readSampleSize < 0) {
                mediaExtractor.unselectTrack(videoIndex);
                break;
            }
            bufferInfo.size = readSampleSize;
            bufferInfo.flags = mediaExtractor.getSampleFlags();
            bufferInfo.offset = 0;
            bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
            mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo);
            // 读取下一帧数据
            mediaExtractor.advance();
        }
        Toast.makeText(this, "分离视频完成", Toast.LENGTH_SHORT).show();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (mediaMuxer != null) {
            mediaMuxer.stop();
            mediaMuxer.release();
        }
        mediaExtractor.release();
    }
}
```
## 将一个aac和avc的视频拆分出来并且分别存储
````java
public class VideoTrackSave {
    String videopath="";

    public VideoTrackSave(String videopath) {
        this.videopath = videopath;
    }

    public Map<String,String> run() throws IOException {
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(videopath);
        int trackCount = mediaExtractor.getTrackCount();
        Map<String,String> map=new HashMap<>();
        for (int i = 0; i < trackCount; ++i) {
            MediaFormat format = mediaExtractor.getTrackFormat(i);
            Log.e(TAG.TAG, "run: " + new Gson().toJson(format));
            if (format.getString(MediaFormat.KEY_MIME).startsWith("video")) {
                mediaExtractor.selectTrack(i);
                map.put("video",saveH264(mediaExtractor,format,i));

            }else if (format.getString(MediaFormat.KEY_MIME).startsWith("audio")){
                mediaExtractor.selectTrack(i);
               map.put("audio", saveAAC(mediaExtractor,format,i));
            }
        }
        mediaExtractor.release();
        return map;
    }

    private String  saveAAC(MediaExtractor mediaExtractor, MediaFormat mediaFormat,int videoIndex) throws IOException{
        // 开启缓冲区。把文件写入
        long millis = System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocate(mediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE));
        String path=Environment.getExternalStorageDirectory()+"/audio_"+millis+".aac";
        FileOutputStream stream=new FileOutputStream(path,true);
        // 一帧 一帧的读取到另外一个视频轨里面。
        while (true) {
            // 将样本数据存储到字节缓存区
            int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
            // 如果没有可获取的样本，退出循环
            if (readSampleSize < 0) {
                mediaExtractor.unselectTrack(videoIndex);
                break;
            }
            stream.write(byteBuffer.array());
            // 读取下一帧数据
            mediaExtractor.advance();
        }
        stream.close();
        Log.e(TAG.TAG, "saveAAC: 文件存储完成" );
        return path;
    }

    private String saveH264(MediaExtractor mediaExtractor, MediaFormat mediaFormat,int videoIndex) throws IOException{
        // 开启缓冲区。把文件写入
        long millis = System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocate(mediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE));
        String path= Environment.getExternalStorageDirectory()+"/video_"+millis+".h264";
        FileOutputStream stream=new FileOutputStream(path,true);
        // 一帧 一帧的读取到另外一个视频轨里面。
        while (true) {
            // 将样本数据存储到字节缓存区
            int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
            // 如果没有可获取的样本，退出循环
            if (readSampleSize < 0) {
                mediaExtractor.unselectTrack(videoIndex);
                break;
            }
            stream.write(byteBuffer.array());
            // 读取下一帧数据
            mediaExtractor.advance();
        }
        stream.close();
        Log.e(TAG.TAG, "saveH264: 文件存储完成" );
        return path;
    }
}

````
