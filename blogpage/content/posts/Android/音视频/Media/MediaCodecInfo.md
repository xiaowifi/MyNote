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
| :------ | ------------------------------------------------------------ |
| `class` | `MediaCodecInfo.AudioCapabilities`支持查询编解码器的音频功能的类。 |
| `class` | `MediaCodecInfo.CodecCapabilities`封装给定编解码器组件的功能。 |
| `class` | `MediaCodecInfo.CodecProfileLevel`封装可用于编解码器组件的配置文件。 |
| `class` | `MediaCodecInfo.EncoderCapabilities`支持查询编解码器编码能力的类。 |
| `class` | `MediaCodecInfo.VideoCapabilities`支持查询编解码器的视频功能的类。 |
