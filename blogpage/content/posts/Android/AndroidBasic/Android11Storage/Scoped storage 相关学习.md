# Android 11 适配 Scoped Storage 沙箱模式

* Android 系统中 所有资源文件 都是通过external.db 进行管理的。
* 所以11 系统上通过直接查询external.db 获取url,如果是需要写入文件，就需要再对rul 进行文件流写入。
* ContentValues 对象用于增删改查
* getContentResolver 用于直接操作 上面对象进行增删改查。
* 特定的文件只能存储到特定的文件夹下面。比如视频无法存储到图片文件夹下面。多媒体和文件需要分开。
