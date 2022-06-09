## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [MediaExtractor解析和封装mp4](https://www.cnblogs.com/renhui/p/7474096.html)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)

# 正文
## 解析
开启硬解不崩溃:
```aidl
{"mMap":{"track-id":1,"level":2048,"mime":"video/avc","frame-count":385,"profile":65536,"language":"```","color-standard":1,"display-width":1440,"csd-1":{"bigEndian":true,"hb":[0,0,0,1,104,-50,60,-128],"isReadOnly":false,"nativeByteOrder":false,"offset":0,"_elementSizeShift":0,"address":0,"capacity":8,"limit":8,"mark":-1,"position":0},"color-transfer":3,"durationUs":12633055,"display-height":1080,"width":1440,"color-range":2,"max-input-size":101823,"frame-rate":30,"height":1080,"csd-0":{"bigEndian":true,"hb":[0,0,0,1,103,66,-64,40,-38,-127,104,8,-105,-106,1,-31,0,-123,64],"isReadOnly":false,"nativeByteOrder":false,"offset":0,"_elementSizeShift":0,"address":0,"capacity":19,"limit":19,"mark":-1,"position":0}}}
```
开启硬解崩溃:
```aidl

```
### 示例代码
````aidl
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
````