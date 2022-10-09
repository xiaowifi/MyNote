## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)
# 正文
通过上篇笔记[Android媒体处理MediaMuxer与MediaExtractor]((1)Android媒体处理MediaMuxer与MediaExtractor.md) 我们可以知道，可以通过MediaExtractor获取到视频轨的数据。
因为视频是被编码器编码后的数据，所以，当我们获取到每一帧数据的时候，这个数据是被压缩了的，所以我们需要将压缩的数据还原为YUV数据，然后渲染到SurfaceView 中。
这个功能似乎和mediaPlayer 一样。我们通过这种模式去尝试理解视频播放器的整体机制。
## 获得一个生命周期可用的Surface
````java
    surface1.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 可用的Surface
                Surface surface = holder.getSurface();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
````
## 获取视频的解码器
