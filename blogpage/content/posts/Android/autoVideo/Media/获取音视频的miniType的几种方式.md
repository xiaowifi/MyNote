# 前言
在音视频开发的过程中。我从文件系统中获得的音视频文件，部分的业务诉求上是需要获取到音视频的miniType。而在ffmpeg 对于音视频的转码等操作都是需要获取到音视频的miniType的。
[Android api中支持的miniType](Android支持音视频格式文件MediaFormat解析.md)
# 正文 
## 通过ContentResolver
当我们在查询文件数据库的时候，发现了他提供了一个字段。
````java
MediaStore.Video.Media.MIME_TYPE
````
那么这个字段是否是视频轨道的miniType呢？

示例代码：
````java
 Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                    null, null, MediaStore.Video.Media.DATE_MODIFIED);
````
第2个参数逻辑意义上是需要传入需要查询到的字段的。查询需要的字段而不是查询表所有字段，这个是性能优化的点之一。

通过打断点发现，我们获取到的miniType 的值为：video/mp4，而我们需要获取到的是视频轨道的miniType 而这个明显不符合我们的要求。我们通过查看MediaStore.Video.Media.MIME_TYPE
的官方注释可以发现他其实是文件类型：
```java
        @Column(Cursor.FIELD_TYPE_STRING)
public static final String MIME_TYPE = "mime_type";
```
## 通过 MediaExtractor
示例代码:
```java
 MediaExtractor mVideoExtractor = new MediaExtractor();
        try {
            mVideoExtractor.setDataSource(path);
            int trackCount = mVideoExtractor.getTrackCount();
            LogUtils.e("共有通道 " + trackCount);
            for (int i = 0; i < trackCount; ++i) {
                MediaFormat format = mVideoExtractor.getTrackFormat(i);
                LogUtils.e(new Gson().toJson(format));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
```
运行结果：
```java
{"mMap":{"track-id":1,"level":512,"mime":"video/avc","frame-count":441,"profile":65536,"language":"und","display-width":720,"csd-1":{"hb":[0,0,0,1,104,-50,60,-128],"isReadOnly":false,"offset":0,"_elementSizeShift":0,"address":0,"capacity":8,"limit":8,"position":0},"durationUs":14666667,"display-height":1280,"width":720,"max-input-size":71834,"frame-rate":30,"height":1280,"csd-0":{"hb":[0,0,0,1,103,66,-64,31,-38,-126,-48,40,72,7,-124,2,21],"isReadOnly":false,"offset":0,"_elementSizeShift":0,"address":0,"capacity":17,"limit":17,"position":0}}}
{"mMap":{"max-bitrate":128261,"sample-rate":44100,"track-id":2,"mime":"audio/mp4a-latm","profile":2,"bitrate":128261,"language":"und","aac-profile":2,"encoder-delay":2048,"durationUs":14744671,"channel-count":2,"encoder-padding":10,"max-input-size":522,"csd-0":{"hb":[18,16],"isReadOnly":false,"offset":0,"_elementSizeShift":0,"address":0,"capacity":2,"limit":2,"position":0}}}

```
我们可以看到MediaFormat中包含了一个字段：mime 而这个就是我们想要的mime