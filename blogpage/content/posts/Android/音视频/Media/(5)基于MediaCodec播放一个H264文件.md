## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)
* [google MediaCodec ](https://developer.android.com/reference/android/media/MediaCodec)
# 正文
通过前面的代码，我们知道可以通过 MediaExtractor读取到视频的某一帧，然后解码出来渲染到屏幕上。而MediaExtractor无法解析一个单独的H264文件。
那我们应该如何播放一个单纯的H264文件呢？这个就需要了解H264文件的结构了。<br>
整体思路是:
* 通过流将一个H264文件逐步或者完整的读取到内存中。以byte数组存储
* 通过H264文件格式，拆分出一帧的数据。
* 将这一帧数据丢给MediaCodec 解码，然后渲染到屏幕上。

一般H264码流最开始的两个NALU是SPS和PPS，第三个NALU是IDR。SPS、PPS、SEI这三种NALU不属于帧的范畴。
## 整体代码
````java
public class H264Player implements Runnable{
    Surface surface;
    String path;
    private MediaCodec videoMediaCodec;
    // 所有的字节
    byte [] buf;
    int totalSize=0;
    public H264Player(Surface surface, String path) {
        this.surface = surface;
        this.path = path;
    }
    /**
     * 初始化
     */
    public void init()throws IOException {
        // 文件转换为字节。
        InputStream is=new DataInputStream(new FileInputStream(new File(path)));
        int len;
        int size=1024;
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        buf=new byte[size];
        while ((len=is.read(buf,0,size))!=-1){
            bos.write(buf,0,len);
        }
        buf=bos.toByteArray();
        // 计算总长度
        totalSize=buf.length;
        // todo 获取SPS和PPS sps和pps中包含了涉及到解码的相关信息。
        // 因为解码的时候，通常我们是知道文件的编码格式的之后，比如说，直播，音视频通话等等。所以这个我们直接基于H264创建解码器。
        videoMediaCodec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
        MediaFormat format=MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC,368,384);
        format.setInteger(MediaFormat.KEY_FRAME_RATE,15);
        videoMediaCodec.configure(format, surface, null, 0);
        videoMediaCodec.start();
    }

    /**
     * 播放。
     */
    public void Play() {
        if (null != videoMediaCodec) {
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        int startIndex=0;
        MediaCodec.BufferInfo info=new MediaCodec.BufferInfo();
        // 内部的队列
        ByteBuffer[] inputBuffers = videoMediaCodec.getInputBuffers();
        boolean first = false;
        long startWhen = 0;
        while (true){
            // 如果总长度等于0.或者当前下标大于等于总长度就停止。
            if (totalSize==0||startIndex>=totalSize){
                break;
            }
            //计算一帧的结束位置。
            int nextFrameStart= findByFrame(startIndex+2,totalSize);
            // 查询可用的byteBuffer
            int inIndex = videoMediaCodec.dequeueInputBuffer(10000);
            if (inIndex>=0){
                ByteBuffer byteBuffer=inputBuffers[inIndex];
                byteBuffer.clear();
                byteBuffer.put(buf,startIndex,nextFrameStart-startIndex);
                videoMediaCodec.queueInputBuffer(inIndex,0,nextFrameStart-startIndex,0,0);
                startIndex=nextFrameStart;
            }else {
                continue;
            }
            int outIndex = videoMediaCodec.dequeueOutputBuffer(info, 10000);
            switch (outIndex) {
                case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                    Log.e(TAG.TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
                    videoMediaCodec.getOutputBuffers();
                    break;

                case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                    Log.e(TAG.TAG, "INFO_OUTPUT_FORMAT_CHANGED format : " + videoMediaCodec.getOutputFormat());
                    break;

                case MediaCodec.INFO_TRY_AGAIN_LATER:
                    Log.e(TAG.TAG, "INFO_TRY_AGAIN_LATER");
                    break;
                default:
                    if (!first) {
                        startWhen = System.currentTimeMillis();
                        first = true;
                    }
                    try {
                        long sleepTime = (info.presentationTimeUs / 1000) - (System.currentTimeMillis() - startWhen);
                        Log.d(TAG.TAG, "info.presentationTimeUs : " + (info.presentationTimeUs / 1000) + " playTime: " + (System.currentTimeMillis() - startWhen) + " sleepTime : " + sleepTime);
                        if (sleepTime > 0) {
                            // 睡眠一会
                            Thread.sleep(sleepTime);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    videoMediaCodec.releaseOutputBuffer(outIndex, true);
                    break;
            }

            // 所有解码帧都已渲染，现在可以停止播放
            if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                Log.d(TAG.TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
                break;
            }
        }
    }

    /**
     * 获取一帧的结束位置。
     * @param start 开始位置
     * @param totalSize 总长度。
     * @return 计算到第一个分隔符
     */
    private int findByFrame(int start, int totalSize) {
        for (int i=start;i<totalSize-4;i++){
            if (buf[i]==0x00&&buf[i+1]==0x00&&buf[i+2]==0x00&&buf[i+3]==0x01){
                return i;
            }
        }
        return -1;
    }
}

````
## 总结 
通过代码对比，我们我们知道，这个和直接通过MediaExtractor读取一帧数据的区别主要集中到以下几点：
* 需要获取miniType，mediaFormat
* 所以需要自己解析sps和pps(代码上虽然没有实现，但是还是需要获取，嘻嘻)
* 需要自己获取一帧的数据，这个就需要知道为啥（开始位置+2),和(buf[i]==0x00&&buf[i+1]==0x00&&buf[i+2]==0x00&&buf[i+3]==0x01)了

