## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)
* [google MediaCodec ](https://developer.android.com/reference/android/media/MediaCodec)
* [使用MediaCodec解码使用SurfaceView显示视频](http://t.zoukankan.com/CoderTian-p-6221944.html)
# 正文
通过上篇笔记[Android媒体处理MediaMuxer与MediaExtractor]((1)Android媒体处理MediaMuxer与MediaExtractor.md) 我们可以知道，可以通过MediaExtractor获取到音频轨的数据。
而音频也是编码后的数据，如果需要调用硬件播放音频，那么需要将编码后的数据如AAC转换为PCM,然后进行播放。
## 获得一个生命周期可用的Surface

## 获取音频的解码器
````java
 mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(path);
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; ++i) {
            MediaFormat format = mediaExtractor.getTrackFormat(i);
            Log.e(TAG.TAG, "run: " + new Gson().toJson(format));
          
        }
````


