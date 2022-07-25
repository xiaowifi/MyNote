#### 1.1 FFmpeg简介

 		 FFmpeg（FastForward Mpeg）是一款遵循GPL的开源软件，在音视频处理方面表现十分优秀，几乎囊括了现存所有的视音频格式的编码，解码、转码、混合、过滤及播放。同时也是一款跨平台的软件，完美兼容Linux、Windows、Mac OSX等平台。其实它由3大部件组成：

> FFmpeg：由命令行组成，用于多媒体格式转换
>
> FFplay：基于FFmpeg开源代码库libraries做的多媒体播放器
>
> FFprobe：基于FFmpeg做的多媒体流分析器
>

源码下载（目前最新版本4.2.3）：http://www.FFmpeg.org/download.html

#### 1.2 FFmpeg强大的两个功能

> 1 命令功能 
>
> 2 api功能

#### 2.1 命令功能

##### 2.1.1应用程序使用方法

![image-20220725183413556](https://s2.loli.net/2022/07/25/maBEQKnMwX7c15p.png)

上图实例是将一个bus.avi中视频分离出来，利用三个应用程序中的ffmpeg.exe。

```
ffmpeg –y –i input –vcodeccopy –an output.avi
```



> 其中-y表示覆盖同名文件，-i表示输入文件即bus.avi，-vcodec表示编码方式，后面的copy表示用原来的编码方式，即不重新编码，-an表示去除音频，后面的busv.avi表示分离出的视频文件。
>

同理将视频中的音频文件分离出来的命令行为：

```
ffmpeg -ibus.avi -acodec copy -vn busa.wav
```

​     上面举例说明了应用程序的用法，应用程序的命令行相对代码要简单很多，也能实现例如音视频分离、转码、播放等各种功能，如视频转码的命令行为：

    ffmpeg -y -i input.mp4 -vcodec libx264 -acodec copy output.mp4

 



​	这个命令用于剪切视频，-ss表示从第几秒开始，如上实例为从第5秒开始，-t代表剪持续几秒长度的视频，如上实例就是剪10秒长度的视频，copy表示视频编码格式和音频编码格式与原视频统一。

```
ffmpeg -ss 0:0:5 -t 0:0:10 -i input.avi -vcodec copy -acodec copy output.avi
```

**1.分离视频音频流**

```
ffmpeg -i input_file -vcodec copy -an output_file_video　　//分离视频流
ffmpeg -i input_file -acodec copy -vn output_file_audio　　//分离音频流
```

**视频解复用**

```
ffmpeg –i test.mp4 –vcodec copy –an –f m4v test.264
ffmpeg –i test.avi –vcodec copy –an –f m4v test.264
```

**视频转码**



```
ffmpeg –i test.mp4 –vcodec h264 –s 352*278 –an –f m4v test.264              //转码为码流原始文件
ffmpeg –i test.mp4 –vcodec h264 –bf 0 –g 25 –s 352*278 –an –f m4v test.264  //转码为码流原始文件
ffmpeg –i test.avi -vcodec mpeg4 –vtag xvid –qsame test_xvid.avi            //转码为封装文件
```

**4.视频封装**

```
ffmpeg –i video_file –i audio_file –vcodec copy –acodec copy output_file
```

**视频剪切**

```

ffmpeg –i test.avi –r 1 –f image2 image-%3d.jpeg        //提取图片
ffmpeg -ss 0:1:30 -t 0:0:20 -i input.avi -vcodec copy -acodec copy output.avi    //剪切视频
//-r 提取图像的频率，-ss 开始时间，-t 持续时间
```

**视频录制**

```
ffmpeg –i rtsp://192.168.3.205:5555/test –vcodec copy out.avi
```

**YUV序列播放**

```
ffplay -f rawvideo -video_size 1920x1080 input.yuv
```

**YUV序列转AVI**

```
ffmpeg –s w*h –pix_fmt yuv420p –i input.yuv –vcodec mpeg4 output.avi
```

##### 2.1.2常用参数说明：

主要参数：

> 1.  -i 设定输入流 
> 2. -f 设定输出格式
> 3.  -ss 开始时间 视频参数：
> 4.  -b 设定视频流量，默认为200Kbit/s
> 5. -r 设定帧速率，默认为25
> 6.  -s 设定画面的宽与高 -aspect 设定画面的比例
> 7.  -vn 不处理视频 
> 8. -vcodec 设定视频编解码器，未设定时则使用与输入流相同的编解码器 音频参数： 
> 9. -ar 设定采样率 -ac 设定声音的Channel数
> 10.  -acodec 设定声音编解码器，未设定时则使用与输入流相同的编解码器 -an 不处理音频



#### 3.1api功能

##### 3.1.1函数一 

 ```
int av_image_get_buffer_size(enum AVPixelFormat pix_fmt, int width, int height, int align);
 ```

> 函数的作用是通过指定像素格式、图像宽、图像高来计算所需的内存大小

重点说明一个参数align:此参数是设定内存对齐的对齐数，也就是按多大的字节进行内存对齐。

> 1. 比如设置为1，表示按1字节对齐，那么得到的结果就是与实际的内存大小一样
> 2. 比如设置为4，表示按4字节对齐。也就是内存的起始地址必须是4的整倍数。



##### 3.1.2函数二 

二、av_image_alloc()是这样定义的。此函数的功能是按照指定的宽、高、像素格式来分析图像内存。

```
int av_image_alloc(uint8_t *pointers[4], int linesizes[4], int w, int h, enum AVPixelFormat pix_fmt, int align);
```

​		pointers[4]：保存图像通道的地址。如果是RGB，则前三个指针分别指向R,G,B的内存地址。第四个指针保留不用 linesizes[4]：保存图像每个通道的内存对齐的步长，即一行的对齐内存的宽度，此值大小等于图像宽度。

>  w:         要申请内存的图像宽度。
>
>  h:         要申请内存的图像高度。
>
>  pix_fmt:    要申请内存的图像的像素格式。
>
>  align:      用于内存对齐的值。
>
>  返回值：所申请的内存空间的总大小。如果是负值，表示申请失败。

##### 3.1.2函数三

```
int av_image_fill_arrays(uint8_t *dst_data[4], int dst_linesize[4],
const uint8_t *src, enum AVPixelFormat pix_fmt, int width, int height, int align);
```

​		av_image_fill_arrays()函数自身不具备内存申请的功能，此函数类似于格式化已经申请的内存，即通过av_malloc()函数申请的内存空间。

av_image_fill_arrays()中参数具体说明：

>  dst_data[4]：    [out]对申请的内存格式化为三个通道后，分别保存其地址
>
>  dst_linesize[4]:    [out]格式化的内存的步长（即内存对齐后的宽度)
>
>  *src:    [in]av_alloc()函数申请的内存地址。
>
>  pix_fmt:  [in] 申请 src内存时的像素格式
>
> width:    [in]申请src内存时指定的宽度
>
> height:    [in]申请scr内存时指定的高度
>
> align:    [in]申请src内存时指定的对齐字节数。

通过以上实例可以看到，

（a)计算所需内存大小av_image_get_bufferz_size() --> 

   (b) 按计算的内存大小申请所需内存 av_malloc() --> (c) 对申请的内存进行格式化 av_image_fill_arrays();

3.1.2函数四

```
 sws_getContext(width, height, vCodecCtx->pix_fmt,
                                     width, height, AV_PIX_FMT_RGBA, SWS_BICUBIC, NULL, NULL, NULL);
```



![img](1.1 FFmpeg简介.assets/20180727162423757)