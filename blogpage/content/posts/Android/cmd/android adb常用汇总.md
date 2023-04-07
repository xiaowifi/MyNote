### 资料 
* [google android adb](https://developer.android.com/studio/command-line/adb)
## 正文

ADB 工具是Android SDK目录的platform-tools 目录下的一个命令行工具。建议配置环境变量。

#### adb 安装apk
````
adb install D:\androidProject\DGGChips\app\d_environment\release\d_environment2021061001.apk
````

#### adb app 启动时长 
````
 adb shell am start -W -n com.chips.cpscustomer/com.chips.module_main.ui.start.StartPageActivity
````

#### 列出adb 进程名称

````
ps -a | grep adb
````

#### 启动adb

````
adb start-server
````

#### 关闭adb

````
adb kill-server
````

#### 查询可连接设备

````
adb devices -l
````

#### 连接设备

> 只能连接同一个局域网的设备，默认端口是55555

````
adb connect 设备ip:5555
````

#### 覆盖安装

````
adb install -r xx.apk
````

#### 卸载app

````
adb uninstall appid
````

#### 从Android中复制文件到电脑

````
adb pull /mnt/sdcard/output.wav ./output.wav
````

上述代码是将Android 设备sd卡根目录下的output.wav文件取出来，放到电脑的当前目录。其中/mnt/sdcard 代表Android sd卡的根目录。

#### 将电脑文件发送到手机

````
adb push input.wav /mnt/sdcard/input.wav
````

上述代码是，将input.wav 放到Android设备的根目录。

#### 当前设备所有日志

````
adb logcat
````

#### 清空现在所有的日志缓冲区

````
adb logcat -c
````

#### 日志过滤某些tag

````
adb locat-s "audioEncoder"
````

#### 过滤多个tag

````
adb locat -s "audioencoder|audio"
````

#### 过滤日志等级

````
adb logcat tag:D tag1:D *:s
````

上述代码是过滤tag与tag1，debug级别以及Error 级别的信息。

#### 过滤进程日志

````
adb logcat -pid 进程id
````

#### 获取进程id

````
adb shell "ps | grep appid"
````

#### 列出根目录的所有文件

````
adb shell "cd /mnt/sdcard: ls -l |grep wav"
````

列出所有的wav文件

#### 查看某个APP 内存占用情况

````
adb shell dumpsys meminfo appid
````

Native heap 代表Native层的堆的大小。因为在Android 引擎中，虽然对APP占用的内存大小有定义，但是对于app 使用Native 代码进行内存的分配和销毁是不进行约束的。EGL mtrack 则代表OpenGL ES 所占用显存大小，因为在手机设备上都是集成显卡，所以没有单独的显存，而是和内存进行共享。如果这一行的内存在无限增大，则应该注意在应用程序中是否有未释放的纹理对象或者帧缓存对象等OpenGL ES 对象。

#### 查看录屏

````
adb shell screenrecord --size 720x1280--bit-rate500000 /mnt/sdcard/case-1.mp4
````

可以在设置中打开显示点按操作，会在视频中会有鼠标点。

上述命令中size是宽乘以高，bit 可以根据情况自己设置。case-1.mp4 作为存储的文件名。在设备上录制完成后，可以按快捷键Ctrl+C 停止录屏。

#### 查看空转线程

````
adb shell top -t -m 5
````

top命令是查看进程占用cpu的情况的指令。top 指令加上 -t表示查看线程占用CPU的情况。-m 表示仅查看CPU占用最高的5个线程。

#### keytool
需要先切换到keytool 目录下。jdk 目录
````aidl
C:\Program Files\Java\jdk1.8.0_221\bin
````
然后执行命令 
````aidl
keytool -exportcert -list -v -keystore C:\android_projects\test.jks
````
