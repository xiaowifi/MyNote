## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)
* [google MediaCodec ](https://developer.android.com/reference/android/media/MediaCodec)
* [MediaCodec+MediaProjectionManager屏幕录制并且生成一个h264文件](屏幕录制生成一个h264文件.md)
# 正文
之前有Demo基于屏幕录制生成一个H264文件。我们可以对于编码有一个大致的流程，那么通过摄像头数据生成H264和屏幕录制有什么区别呢？
为了简单，我们使用Camera的预览数据，生成H264文件。
### 代码示例
````java
public class CameraPreview extends RelativeLayout implements Camera.PreviewCallback, SurfaceHolder.Callback {

    private Camera mCamera;

    byte[] buffer;
    private Camera.Size size;
    private MediaCodec mediaCodec;
    final int FRAME_RATE = 20;
    int index = 0;
    private SurfaceView surfaceView;

    private FileUtils utils;

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceView = new SurfaceView(context);
        addView(surfaceView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        surfaceView.getHolder().addCallback(this);
        utils = new FileUtils("_camera_");
    }

    /**
     * 开启预览
     */
    public void startPreview() {
        // 开启后置摄像头
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        Camera.Parameters parameters = mCamera.getParameters();
        size = parameters.getPreviewSize();
        try {
            mCamera.setPreviewDisplay(surfaceView.getHolder());
            // 显示的旋转了90度
            mCamera.setDisplayOrientation(90);
            buffer = new byte[size.width * size.height * 3 / 2];
            mCamera.addCallbackBuffer(buffer);
            mCamera.setPreviewCallbackWithBuffer(this);
            mCamera.startPreview();

            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            int width = size.width;
            int height = size.height;
            // 获取 mediaFormat ，在解码的时候可以乱传，但是在编码的时候，就不能乱传。
            MediaFormat format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height);
            // 帧率 1秒20帧
            format.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
            // i帧间隔。30帧后一个I帧，我们知道I帧数据可以不依赖于其他帧完整的解析出来。所以一个视频中I帧越多，文件大小越大。
            // 通常直播的时候帧率低，但是I帧的间隔小，但是在短视频等固定场景中，帧率会提高，I帧间隔会加大
            format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 30);
            // 码率，码率越高代表质量越清晰，可以理解为压缩等级，越小压缩比例越高。因为H264编码是有损压缩。所以越大还原度越高
            format.setInteger(MediaFormat.KEY_BIT_RATE, width * height);
            // 这个值必须传递。这个是编码器的数据来源。我们屏幕录制，camer2 提供的是YUV数据，所以这个填 YUV420
            format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible);
            //Surface是输出对象。  flags 为编码的标志，默认是解码。
            // 因为我们是编码，所以不需要提供一个Surface用于输出，所以Surface不需要设置。MediaCodec.CONFIGURE_FLAG_ENCODE 作为编码标志位，表示我们这个是编码器。
            mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mediaCodec.start();
        } catch (Exception e) {
            Log.e(TAG.TAG, "startPreview: ");
        }

    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Log.e(TAG.TAG, "onPreviewFrame: ");
        // 说的是摄像头是nv21，所以说灰色
        // bytes  这个是YUV数据。这里的宽高是需要交换的。而且这是一帧的数据。
        // 开启编码
        int inputBufferIndex = mediaCodec.dequeueInputBuffer(10000);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        if (inputBufferIndex >= 0) {
            ByteBuffer inputBuffer = mediaCodec.getInputBuffer(inputBufferIndex);
            inputBuffer.clear();
            inputBuffer.put(bytes);
            mediaCodec.queueInputBuffer(inputBufferIndex, 0, bytes.length, getPresentationTimeUs(), 0);
            index++;
        }
        int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 10000);
        if (outputBufferIndex >= 0) {

            ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(outputBufferIndex);
            byte[] data = new byte[bufferInfo.size];
            outputBuffer.get(data);
            utils.appendH264(data);
            mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
        }
        // 重新设置监听
        mCamera.addCallbackBuffer(bytes);
    }

    private long getPresentationTimeUs() {
        return 1000000 / FRAME_RATE * index;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        startPreview();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}

````
### 和屏幕录制的不同
在设置mediaFormat 的时候，数据来源的类型不同了。
````java
   format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible);
````
而且在拿到数据回调的时候，我们需要手动去设置编码数据。而屏幕录制的时候则不会。而在编码的时候，需要传入帧的时间。
1秒等于=1000000,所以时间就可以用1秒除以KEY_FRAME_RATE，然后乘以第几帧解决。

