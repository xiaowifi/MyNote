## 资料
# 正文
## 示例代码
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