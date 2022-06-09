## 资料
* [google Android media 官网](https://developer.android.com/reference/android/media/package-summary)
* [MediaExtractor解析和封装mp4](https://www.cnblogs.com/renhui/p/7474096.html)
* [google MediaExtractor官网](https://developer.android.com/reference/android/media/MediaExtractor)

# 正文
## 解析
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