## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)
* [google MediaCodec ](https://developer.android.com/reference/android/media/MediaCodec)
# 正文
我们知道视频轨读取出来的每一帧都是压缩数据，所以说，我们需要对其解码。在之前自己播放的过程中，我们传入了一个Surface作为解码后的视频的输出。
而bitmap 则不需要一个输出。所以我们需要拿到解码后的数据，然后自己处理，整改流程和播放流程一致。唯一的区别就在于需要把解码后的数据获取出来进行转换。
这个流程在视频的时候有用，我们需要获取到本地视频的某个时间段的帧序列，然后展示出来。
## 自己封装版本
```java
public class VideoGetImage implements Runnable {
    String path;
    private MediaCodec videoMediaCodec;
    MediaExtractor mediaExtractor;
    int track = -1;
    public VideoGetImage( String path) {
        this.path = path;
    }

    /**
     * 初始化。
     *
     * @throws IOException
     */
    public void init() throws IOException {
        mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(path);
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; ++i) {
            MediaFormat format = mediaExtractor.getTrackFormat(i);
            Log.e(TAG.TAG, "run: " + new Gson().toJson(format));
            // 姑且不处理音频。
            if (format.getString(MediaFormat.KEY_MIME).startsWith("video")) {
                if (videoMediaCodec == null) {
                    track = i;
                    videoMediaCodec = MediaCodec.createDecoderByType(format.getString(MediaFormat.KEY_MIME));
                    videoMediaCodec.configure(format, null, null, 0);
                    videoMediaCodec.start();
                }
            }
        }
    }

    /**
     * 开始获取图片 
     */
    public void Play() {
        if (null != videoMediaCodec) {
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        // 选中视频轨道
        mediaExtractor.selectTrack(track);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        // 因为不是每一帧都是按照顺序并且可用的，所以。
        ByteBuffer[] inputBuffers = videoMediaCodec.getInputBuffers();
        videoMediaCodec.getOutputBuffers();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        boolean first = false;
        long startWhen = 0;
        while (true) {
            // 将样本数据存储到字节缓存区
            int inputIndex = videoMediaCodec.dequeueInputBuffer(10000);
            if (inputIndex >= 0) {
                ByteBuffer inputBuffer = inputBuffers[inputIndex];
                int readSampleSize = mediaExtractor.readSampleData(inputBuffer, 0);
                // 如果没有可获取的样本，退出循环
                if (readSampleSize < 0) {
                    mediaExtractor.unselectTrack(track);
                    break;
                }
                videoMediaCodec.queueInputBuffer(inputIndex, 0, readSampleSize, mediaExtractor.getSampleTime(), 0);
                // 读取下一帧数据
                mediaExtractor.advance();
            }
            //
            int outIndex = videoMediaCodec.dequeueOutputBuffer(bufferInfo, 10000);

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
                        long sleepTime = (bufferInfo.presentationTimeUs / 1000) - (System.currentTimeMillis() - startWhen);
                        Log.d(TAG.TAG, "info.presentationTimeUs : " + (bufferInfo.presentationTimeUs / 1000) + " playTime: " + (System.currentTimeMillis() - startWhen) + " sleepTime : " + sleepTime);
                        if (sleepTime > 0) {
                            // 睡眠一会
                            Thread.sleep(sleepTime);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 获取图片 Image
                    Image image = videoMediaCodec.getOutputImage(outIndex);
                    outStream.reset();
                    Rect rect = image.getCropRect();
                    YuvImage yuvImage = new YuvImage(getDataFromImage(image), ImageFormat.NV21, rect.width(), rect.height(), null);
                    yuvImage.compressToJpeg(rect, 100, outStream);
                    byte[] bytes = outStream.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    videoMediaCodec.releaseOutputBuffer(outIndex, true);
                    break;
            }

            // 所有解码帧都已渲染。
            if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                Log.d(TAG.TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
                break;
            }
        }
        // 释放资源
        videoMediaCodec.stop();
        videoMediaCodec.release();
        mediaExtractor.release();
    }
    private static byte[] getDataFromImage(Image image) {
        if (!isImageFormatSupported(image)) {
            throw new RuntimeException("can't convert Image to byte array, format " + image.getFormat());
        }
        Rect crop = image.getCropRect();
        int format = image.getFormat();
        int width = crop.width();
        int height = crop.height();
        Image.Plane[] planes = image.getPlanes();
        byte[] data = new byte[width * height * ImageFormat.getBitsPerPixel(format) / 8];
        byte[] rowData = new byte[planes[0].getRowStride()];
        int channelOffset = 0;
        int outputStride = 1;
        for (int i = 0; i < planes.length; i++) {
            switch (i) {
                case 0:
                    channelOffset = 0;
                    outputStride = 1;
                    break;
                case 1:
                    channelOffset = width * height + 1;
                    outputStride = 2;
                    break;
                case 2:
                    channelOffset = width * height;
                    outputStride = 2;
                    break;
            }
            ByteBuffer buffer = planes[i].getBuffer();
            int rowStride = planes[i].getRowStride();
            int pixelStride = planes[i].getPixelStride();
            int shift = (i == 0) ? 0 : 1;
            int w = width >> shift;
            int h = height >> shift;
            buffer.position(rowStride * (crop.top >> shift) + pixelStride * (crop.left >> shift));
            for (int row = 0; row < h; row++) {
                int length;
                if (pixelStride == 1 && outputStride == 1) {
                    length = w;
                    buffer.get(data, channelOffset, length);
                    channelOffset += length;
                } else {
                    length = (w - 1) * pixelStride + 1;
                    buffer.get(rowData, 0, length);
                    for (int col = 0; col < w; col++) {
                        data[channelOffset] = rowData[col * pixelStride];
                        channelOffset += outputStride;
                    }
                }
                if (row < h - 1) {
                    buffer.position(buffer.position() + rowStride - length);
                }
            }
        }
        return data;
    }

    private static boolean isImageFormatSupported(Image image) {
        int format = image.getFormat();
        switch (format) {
            case ImageFormat.YUV_420_888:
            case ImageFormat.NV21:
            case ImageFormat.YV12:
                return true;
        }
        return false;
    }
}

```
## 网络博客版本
````java
public class VideoToFrames {
    private static final String TAG = "VideoToFrames";

    private static final long DEFAULT_TIMEOUT_US = 10000;

    private boolean stopDecode = false;

    private void videoDecode(String videoFilePath) {
        MediaExtractor extractor = null;
        MediaCodec decoder = null;
        try {
            File videoFile = new File(videoFilePath);
            extractor = new MediaExtractor();
            extractor.setDataSource(videoFile.toString());
            int trackIndex = selectTrack(extractor);
            if (trackIndex < 0) {
                throw new RuntimeException("No video track found in " + videoFilePath);
            }
            extractor.selectTrack(trackIndex);
            MediaFormat mediaFormat = extractor.getTrackFormat(trackIndex);
            String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
            decoder = MediaCodec.createDecoderByType(mime);
            // 输出支持的颜色格式
            showSupportedColorFormat(decoder.getCodecInfo().getCapabilitiesForType(mime));
            int decodeColorFormat = MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible;
            if (isColorFormatSupported(decodeColorFormat, decoder.getCodecInfo().getCapabilitiesForType(mime))) {
                mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, decodeColorFormat);
                Log.i(TAG, "set decode color format to type " + decodeColorFormat);
            } else {
                Log.i(TAG, "unable to set decode color format, color format type " + decodeColorFormat + " not supported");
            }
            decodeFramesToImage(decoder, extractor, mediaFormat);
            decoder.stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (decoder != null) {
                decoder.stop();
                decoder.release();
            }
            if (extractor != null) {
                extractor.release();
            }
        }
    }

    /**
     * 输出 支持的颜色 格式
     * @param caps caps
     */
    private void showSupportedColorFormat(MediaCodecInfo.CodecCapabilities caps) {
        System.out.print("supported color format: ");
        for (int c : caps.colorFormats) {
            System.out.print(c + "\t");
        }
        System.out.println();
    }

    /**
     * 判断是否支持的颜色格式
     * @param colorFormat
     * @param caps
     * @return
     */
    private boolean isColorFormatSupported(int colorFormat, MediaCodecInfo.CodecCapabilities caps) {
        for (int c : caps.colorFormats) {
            if (c == colorFormat) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param decoder
     * @param extractor
     * @param mediaFormat
     * @throws InterruptedException
     */
    private void decodeFramesToImage(MediaCodec decoder, MediaExtractor extractor, MediaFormat mediaFormat) throws InterruptedException {
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        boolean sawInputEOS = false;
        boolean sawOutputEOS = false;
        // 开启解码
        decoder.configure(mediaFormat, null, null, 0);
        decoder.start();
        // 开启循环
        while (!sawOutputEOS && !stopDecode) {
            if (!sawInputEOS) {
                int inputBufferId = decoder.dequeueInputBuffer(DEFAULT_TIMEOUT_US);
                if (inputBufferId >= 0) {
                    ByteBuffer inputBuffer = decoder.getInputBuffer(inputBufferId);
                    int sampleSize = extractor.readSampleData(inputBuffer, 0);
                    if (sampleSize < 0) {
                        decoder.queueInputBuffer(inputBufferId, 0, 0, 0L, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                        sawInputEOS = true;
                    } else {
                        long presentationTimeUs = extractor.getSampleTime();
                        decoder.queueInputBuffer(inputBufferId, 0, sampleSize, presentationTimeUs, 0);
                        extractor.advance();
                    }
                }
            }
            int outputBufferId = decoder.dequeueOutputBuffer(info, DEFAULT_TIMEOUT_US);
            if (outputBufferId >= 0) {
                if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    sawOutputEOS = true;
                }
                boolean doRender = (info.size != 0);
                if (doRender) {
                    Image image = decoder.getOutputImage(outputBufferId);
                    compressToJpeg2(image);
                    image.close();
                    decoder.releaseOutputBuffer(outputBufferId, true);
                }
            }
        }
    }

    private static int selectTrack(MediaExtractor extractor) {
        int numTracks = extractor.getTrackCount();
        for (int i = 0; i < numTracks; i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith("video/")) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isImageFormatSupported(Image image) {
        int format = image.getFormat();
        switch (format) {
            case ImageFormat.YUV_420_888:
            case ImageFormat.NV21:
            case ImageFormat.YV12:
                return true;
        }
        return false;
    }

    private static byte[] getDataFromImage(Image image) {
        if (!isImageFormatSupported(image)) {
            throw new RuntimeException("can't convert Image to byte array, format " + image.getFormat());
        }
        Rect crop = image.getCropRect();
        int format = image.getFormat();
        int width = crop.width();
        int height = crop.height();
        Image.Plane[] planes = image.getPlanes();
        byte[] data = new byte[width * height * ImageFormat.getBitsPerPixel(format) / 8];
        byte[] rowData = new byte[planes[0].getRowStride()];
        int channelOffset = 0;
        int outputStride = 1;
        for (int i = 0; i < planes.length; i++) {
            switch (i) {
                case 0:
                    channelOffset = 0;
                    outputStride = 1;
                    break;
                case 1:
                    channelOffset = width * height + 1;
                    outputStride = 2;
                    break;
                case 2:
                    channelOffset = width * height;
                    outputStride = 2;
                    break;
            }
            ByteBuffer buffer = planes[i].getBuffer();
            int rowStride = planes[i].getRowStride();
            int pixelStride = planes[i].getPixelStride();
            int shift = (i == 0) ? 0 : 1;
            int w = width >> shift;
            int h = height >> shift;
            buffer.position(rowStride * (crop.top >> shift) + pixelStride * (crop.left >> shift));
            for (int row = 0; row < h; row++) {
                int length;
                if (pixelStride == 1 && outputStride == 1) {
                    length = w;
                    buffer.get(data, channelOffset, length);
                    channelOffset += length;
                } else {
                    length = (w - 1) * pixelStride + 1;
                    buffer.get(rowData, 0, length);
                    for (int col = 0; col < w; col++) {
                        data[channelOffset] = rowData[col * pixelStride];
                        channelOffset += outputStride;
                    }
                }
                if (row < h - 1) {
                    buffer.position(buffer.position() + rowStride - length);
                }
            }
        }
        return data;
    }

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    private Bitmap compressToJpeg2(Image image) throws InterruptedException {
        outStream.reset();
        Rect rect = image.getCropRect();
        YuvImage yuvImage = new YuvImage(getDataFromImage(image), ImageFormat.NV21, rect.width(), rect.height(), null);
        yuvImage.compressToJpeg(rect, 100, outStream);
        byte[] bytes = outStream.toByteArray();

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
````
# 结论
通过这个和通过mediaCodec 解码视频的代码是查不到的。区别在于没有设置Surface，同时有主动获取到一个Image对象，然后将Image通过YUV匹配规则转换为一个bitmap.
yuv对象转bitmap 不建议这么写，libYuv 写得更改。<br>
在视频裁剪的具体业务上，建议直接获取到I帧，或者设置时间间隔。将解码后的byte 存放起来。是否需要再显示的时候再转码成bitmap，还是说渲染到时候通过自定义个View 渲染YUV数据都是可行的。
