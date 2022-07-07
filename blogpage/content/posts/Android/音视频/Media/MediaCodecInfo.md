## 资料
* [MediaCodecInfo google](https://developer.android.com/reference/android/media/MediaCodecInfo?hl=en)
* [Java MediaCodecInfo类代码示例](https://vimsky.com/examples/detail/java-class-android.media.MediaCodecInfo.html)
* [编码器设置CBR问题](https://blog.csdn.net/kris_fei/article/details/73527630)
# 正文
> 提供有关设备上可用的给定媒体编解码器的信息。您可以通过查询来遍历所有可用的编解码器MediaCodecList
## MediaCodecInfo 类描述
| 公共方法                           |                                                              |
| :--------------------------------- | ------------------------------------------------------------ |
| `String`                           | `getCanonicalName()`检索基础编解码器名称。                   |
| `MediaCodecInfo.CodecCapabilities` | `getCapabilitiesForType(String type)`枚举编解码器组件的功能。 |
| `String`                           | `getName()`检索编解码器名称。                                |
| `String[]`                         | `getSupportedTypes()`查询编解码器支持的媒体类型。            |
| `boolean`                          | `isAlias()`查询编解码器是否是另一个底层编解码器的别名。      |
| `boolean`                          | `isEncoder()`查询编解码器是否为编码器。                      |
| `boolean`                          | `isHardwareAccelerated()`查询编解码器是否硬件加速。          |
| `boolean`                          | `isSoftwareOnly()`查询编解码器是否仅为软件。                 |
| `boolean`                          | `isVendor()`查询编解码器是Android平台提供（false）还是设备厂商提供（true）。 |
| 嵌套类  |                                                              |
| `class` | `MediaCodecInfo.AudioCapabilities`支持查询编解码器的音频功能的类。 |
| `class` | `MediaCodecInfo.CodecCapabilities`封装给定编解码器组件的功能。 |
| `class` | `MediaCodecInfo.CodecProfileLevel`封装可用于编解码器组件的配置文件。 |
| `class` | `MediaCodecInfo.EncoderCapabilities`支持查询编解码器编码能力的类。 |
| `class` | `MediaCodecInfo.VideoCapabilities`支持查询编解码器的视频功能的类。 |
### 调用全嵌套类的代码示例：
````aidl
            int codecCount = MediaCodecList.getCodecCount();
            Log.e(TAG, "initConsumers:共有 "+codecCount );
            List<String> encoders=new ArrayList<>();
            List<String> dCoders=new ArrayList<>();
            List<String []> encoderTypes=new ArrayList<>();
            List<String []> dCoderTypes=new ArrayList<>();
            for (int i=0;i<codecCount;i++){
                StringBuilder buffer=new StringBuilder();
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
                buffer.append("\n 解码器名称:"+codecInfoAt.getName());
                buffer.append("\n 是否是解码器:"+codecInfoAt.isEncoder());
                if (codecInfoAt.isEncoder()){
                    encoders.add(codecInfoAt.getName());
                    encoderTypes.add(codecInfoAt.getSupportedTypes());
                }else {
                    dCoders.add(codecInfoAt.getName());
                    dCoderTypes.add(codecInfoAt.getSupportedTypes());
                }
                buffer.append("\n 查询当前支持的媒体类型:"+new Gson().toJson(codecInfoAt.getSupportedTypes()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    buffer.append("\n 检索基础编解码器名称:"+codecInfoAt.getCanonicalName());
                    buffer.append("\n 编解码器是否是另一个底层编解码器的别名:"+codecInfoAt.isAlias());
                    buffer.append("\n 编解码器是否硬件加速:"+codecInfoAt.isHardwareAccelerated());
                    buffer.append("\n 编解码器是否仅为软件:"+codecInfoAt.isSoftwareOnly());
                    buffer.append("\n 是否是设备商提供："+codecInfoAt.isVendor());
                }
                for (String type: codecInfoAt.getSupportedTypes()){
                    MediaCodecInfo.CodecCapabilities forType = codecInfoAt.getCapabilitiesForType(type);
                    buffer.append("\n"+type+":DefaultFormat:"+forType.getDefaultFormat().toString());
                    // 返回视频功能信息 这个调调 可能返回Null，所以通过type 去确定下
                    MediaCodecInfo.VideoCapabilities videoCapabilities = forType.getVideoCapabilities();
                    buffer.append("\n 视频功能："+new Gson().toJson(videoCapabilities) );
                    // 返回音频功能的相关信息   可能返回Null，所以通过type 去确定下
                    MediaCodecInfo.AudioCapabilities audioCapabilities = forType.getAudioCapabilities();
                    buffer.append("\n 音频功能："+new Gson().toJson(audioCapabilities) );
                    buffer.append("\n MimeType:"+forType.getMimeType());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       buffer.append("\n 并发编解码器实例的最大数量:"+ forType.getMaxSupportedInstances());
                    }
                    // 获取编码功能，如果这个调调为Null 就不是编码器。
                    MediaCodecInfo.EncoderCapabilities encoderCapabilities = forType.getEncoderCapabilities();
                    if (null!=encoderCapabilities){
                        buffer.append("\n 再次确定这个调调是编码器");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            buffer.append("\n 返回支持的质量值范围:"+new Gson().toJson(encoderCapabilities.getQualityRange()));
                            buffer.append("\n 返回编码器复杂度值的支持范围："+new Gson().toJson(encoderCapabilities.getComplexityRange()));
                        }

                    }
                }
                Log.e(TAG, "initConsumers: "+ buffer);
                Log.e(TAG, "encoders:"+new Gson().toJson(encoders));
                Log.e(TAG, "encoders:"+new Gson().toJson(encoderTypes));
                Log.e(TAG, "dCoders"+new Gson().toJson(dCoders));
                Log.e(TAG, "dCoders"+new Gson().toJson(dCoderTypes));
````
### 官方提示Demo:
````aidl
 private static MediaCodecInfo selectCodec(String mimeType) {
     int numCodecs = MediaCodecList.getCodecCount();
     for (int i = 0; i < numCodecs; i++) {
         MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);

         if (!codecInfo.isEncoder()) {
             continue;
         }

         String[] types = codecInfo.getSupportedTypes();
         for (int j = 0; j < types.length; j++) {
             if (types[j].equalsIgnoreCase(mimeType)) {
                 return codecInfo;
             }
         }
     }
     return null;
 }
````
### MediaCodecInfo.CodecCapabilities
封装给定编解码器组件的功能。例如，它支持哪些配置文件/级别组合，它能够在哪些颜色空间中提供解码数据，以及一些编解码器类型特定的功能标志。
您可以通过传递 MIME 类型来获取给定`MediaCodecInfo`对象 的实例。`getCapabilitiesForType()`
[官方地址](https://developer.android.com/reference/android/media/MediaCodecInfo.CodecCapabilities)

| 公共方法                                  |                                                              |
| :---------------------------------------- | ------------------------------------------------------------ |
| `static MediaCodecInfo.CodecCapabilities` | `createFromProfileLevel(String mime, int profile, int level)`检索某个 和 的编解码`mime type`器`profile`功能`level`。 |
| `MediaCodecInfo.AudioCapabilities`        | `getAudioCapabilities()`返回音频功能，或者`null`如果这不是音频编解码器。 |
| `MediaFormat`                             | `getDefaultFormat()`为具有默认值的配置返回具有默认值的 MediaFormat 对象。 |
| `MediaCodecInfo.EncoderCapabilities`      | `getEncoderCapabilities()`返回编码功能，或者`null`如果这不是编码器。 |
| `int`                                     | `getMaxSupportedInstances()`返回支持的并发编解码器实例的最大数量。 |
| `String`                                  | `getMimeType()`返回为其创建此编解码器功能对象的 mime 类型。  |
| `MediaCodecInfo.VideoCapabilities`        | `getVideoCapabilities()`返回视频功能，或者`null`如果这不是视频编解码器。 |
| `boolean`                                 | `isFeatureRequired(String name)`查询编解码器功能要求。       |
| `boolean`                                 | `isFeatureSupported(String name)`查询编解码器功能。          |
| `boolean`                                 | `isFormatSupported(MediaFormat format)`查询编解码器是否支持给定的`MediaFormat`. |

### [MediaCodecInfo.VideoCapabilities](https://developer.android.com/reference/android/media/MediaCodecInfo.VideoCapabilities)

> 支持查询编解码器的视频功能的类。

| 嵌套类  |                                                              |
| :------ | ------------------------------------------------------------ |
| `class` | `MediaCodecInfo.VideoCapabilities.PerformancePoint`视频性能点是由像素数、像素率和帧率定义的一组标准性能点。 |

| 公共方法                                                  |                                                              |
| :-------------------------------------------------------- | ------------------------------------------------------------ |
| `boolean`                                                 | `areSizeAndRateSupported(int width, int height, double frameRate)`返回是否支持给定的视频大小 ( `width`and `height`) 和`frameRate`组合。 |
| `Range<Double>`                                           | `getAchievableFrameRatesFor(int width, int height)`返回视频大小的可实现视频帧速率范围。 |
| `Range<Integer>`                                          | `getBitrateRange()`返回支持的比特率范围（以比特/秒为单位）。 |
| `int`                                                     | `getHeightAlignment()`返回视频高度的对齐要求（以像素为单位）。 |
| `Range<Integer>`                                          | `getSupportedFrameRates()`返回支持的帧速率范围。             |
| `Range<Double>`                                           | `getSupportedFrameRatesFor(int width, int height)`返回视频大小支持的视频帧速率范围。 |
| `Range<Integer>`                                          | `getSupportedHeights()`返回支持的视频高度范围。              |
| `Range<Integer>`                                          | `getSupportedHeightsFor(int width)`返回视频宽度支持的视频高度范围 |
| `List<MediaCodecInfo.VideoCapabilities.PerformancePoint>` | `getSupportedPerformancePoints()`返回支持的性能点。          |
| `Range<Integer>`                                          | `getSupportedWidths()`返回支持的视频宽度范围。               |
| `Range<Integer>`                                          | `getSupportedWidthsFor(int height)`返回视频高度支持的视频宽度范围。 |
| `int`                                                     | `getWidthAlignment()`返回视频宽度的对齐要求（以像素为单位）。 |
| `boolean`                                                 | `isSizeSupported(int width, int height)`返回是否支持给定的视频大小 (`width`和 `height`)。 |

### [MediaCodecInfo.AudioCapabilities](https://developer.android.com/reference/android/media/MediaCodecInfo.AudioCapabilities)

> 支持查询编解码器的音频功能的类。

| 公共方法           |                                                              |
| :----------------- | ------------------------------------------------------------ |
| `Range<Integer>`   | `getBitrateRange()`返回支持的比特率范围（以比特/秒为单位）。 |
| `Range[]<Integer>` | `getInputChannelCountRanges()`                               |
| `int`              | `getMaxInputChannelCount()`返回支持的最大输入通道数。        |
| `int`              | `getMinInputChannelCount()`返回支持的最小输入通道数。        |
| `Range[]<Integer>` | `getSupportedSampleRateRanges()`返回支持的采样率范围数组。   |
| `int[]`            | `getSupportedSampleRates()`如果编解码器仅支持离散值，则返回支持的采样率数组。 |
| `boolean`          | `isSampleRateSupported(int sampleRate)`查询编解码器是否支持采样率。 |

### [MediaCodecInfo.EncoderCapabilities](https://developer.android.com/reference/android/media/MediaCodecInfo.EncoderCapabilities)

> 支持查询编解码器编码能力的类。

| 公共方法         |                                                          |
| :--------------- | -------------------------------------------------------- |
| `Range<Integer>` | `getComplexityRange()`返回编码器复杂度值的支持范围。     |
| `Range<Integer>` | `getQualityRange()`返回支持的质量值范围。                |
| `boolean`        | `isBitrateModeSupported(int mode)`查询是否支持码率模式。 |









