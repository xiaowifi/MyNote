>  想了下，感觉这个调调 做音视频的可能多一点。还是把放音视频里面吧。
## 资料
* [SurfaceView与TextureView详解](https://www.51cto.com/article/679096.html)
* [TextureView 的血与泪](https://zhuanlan.zhihu.com/p/147322501?from_voters_page=true)
* [SurfaceView google官网](https://developer.android.com/reference/android/view/SurfaceView)
* [TextureView google官网](https://developer.android.com/reference/android/view/TextureView)
# 正文
````aidl
将 TextureView 集成到视图层次结构中的一个含义是它的性能可能比 SurfaceView 慢。TextureView 的内容必须在内部从底层表面复制到显示这些内容的视图中。因此，建议将 SurfaceView 作为更通用的解决方案，以解决需要渲染到表面的问题。
````
上面这句话含泪看到。之前在处理ijk 软解的时候会出现卡顿现象。
## SurfaceView的简单使用
因为配置更改的情况下，SurfaceView 中的Surface会销毁重新创建。所以需要监听Surface的状态。<br>
示例代码：
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



