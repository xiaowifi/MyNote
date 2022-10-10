## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)
* [google MediaCodec ](https://developer.android.com/reference/android/media/MediaCodec)
* [google AudioTrack](https://developer.android.com/reference/android/media/AudioTrack)  
* [Android MediaCodec解码音频，AudioTrack播放音频，PCM数据写入文件](https://www.codenong.com/cs106611303/)
# 正文
通过上篇笔记[Android媒体处理MediaMuxer与MediaExtractor]((1)Android媒体处理MediaMuxer与MediaExtractor.md) 我们可以知道，可以通过MediaExtractor获取到音频轨的数据。
而音频也是编码后的数据，如果需要调用硬件播放音频，那么需要将编码后的数据如AAC转换为PCM,然后进行播放。<br>
但是又一个疑惑，视频渲染到时候我们需要一个Surface渲染视频源，而将帧转换YUV后，再将YUV转换为bitmap的时候，却没有传递Surface。所以解码和渲染是两个事情。
那么音频解码成功后，如何播放呢？<br>
那肯定就是调用系统API了。
> audioTrack 适合低延迟播放，是更加底层的API，提供了非常强大的控制能力，适合流媒体等场景，需要结合解码器使用。
AudioTrack 类为 Java 应用程序管理和播放单个音频资源。它允许将 PCM 音频缓冲区流式传输到音频接收器以进行播放。这是通过使用 、 和 方法之一将数据“推送”到 AudioTrack 对象来 write(byte[], int, int)实现write(short[], int, int)的write(float[], int, int, int)。

AudioTrack 实例可以在两种模式下运行：静态或流式传输。
在 Streaming 模式下，应用程序使用其中一种方法将连续的数据流写入 AudioTrack write()。当数据从 Java 层传输到本机层并排队等待播放时，这些是阻塞和返回的。当播放音频数据块时，流模式最有用，例如：

由于播放声音的持续时间太大而无法存储在内存中，
由于音频数据的特性（高采样率，每个样本的位数......），内存太大而无法放入内存
在播放之前排队的音频时接收或生成。
在处理适合内存且需要以尽可能小的延迟播放的短声音时，应选择静态模式。因此，静态模式将更适合经常播放的 UI 和游戏声音，并且开销可能最小。
创建后，AudioTrack 对象会初始化其关联的音频缓冲区。这个缓冲区的大小，在构造过程中指定，决定了 AudioTrack 在数据用完之前可以播放多长时间。
对于使用静态模式的 AudioTrack，此大小是可以从中播放的声音的最大大小。
对于流模式，数据将以小于或等于总缓冲区大小的块的形式写入音频接收器。AudioTrack 不是最终的，因此允许子类，但不建议这样使用。
## 获取AudioTrack播放对象
````java
                    int streamType = AudioManager.STREAM_MUSIC;
                    int sampleRate = 44100;
                    int channelConfig = AudioFormat.CHANNEL_OUT_STEREO;
                    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
                    int mode = AudioTrack.MODE_STREAM;

                    int minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);
                    audioTrack = new AudioTrack(streamType, sampleRate, channelConfig, audioFormat,
                            Math.max(minBufferSize, 2048), mode);
                    audioTrack.play();
````
## 获取音频的解码器
````java
 mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(path);
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; ++i) {
            MediaFormat format = mediaExtractor.getTrackFormat(i);
            Log.e(TAG.TAG, "run: " + new Gson().toJson(format));
        if (format.getString(MediaFormat.KEY_MIME).startsWith("audio")) {
        if (audioMediaCodec == null) {
        track = i;
        audioMediaCodec = MediaCodec.createDecoderByType(format.getString(MediaFormat.KEY_MIME));
        audioMediaCodec.configure(format, null, null, 0);
        audioMediaCodec.start();
        
        }
        }
        }
````
## 读取 

