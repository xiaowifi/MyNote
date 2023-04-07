> 当前主要是对于Android MediaFormat class 进行描述，主要是这个类可以用于标记Android 支持硬件解码的视频格式等。
## 资料
* [Google mediaFormat 官网](https://developer.android.com/reference/android/media/MediaFormat)
#  正文

| 公共方法             |                                                              |
| :------------------- | ------------------------------------------------------------ |
| `boolean`            | `containsFeature(String name)`如果格式中存在给定名称的特征，则返回 true。 |
| `boolean`            | `containsKey(String name)`如果格式中存在给定名称的键，则返回 true。 |
| `static MediaFormat` | `createAudioFormat(String mime, int sampleRate, int channelCount)`创建最小的音频格式。 |
| `static MediaFormat` | `createSubtitleFormat(String mime, String language)`创建最小的字幕格式。 |
| `static MediaFormat` | `createVideoFormat(String mime, int width, int height)`创建最小的视频格式。 |
| `ByteBuffer`         | `getByteBuffer(String name)`返回 ByteBuffer 键的值。         |
| `ByteBuffer`         | `getByteBuffer(String name, ByteBuffer defaultValue)`返回 ByteBuffer 键的值，如果缺少键，则返回默认值。 |
| `boolean`            | `getFeatureEnabled(String feature)`返回是启用 ( `true`) 还是禁用 ( `false`) 功能。 |
| `Set<String>`        | `getFeatures()`返回`Set`此 MediaFormat 中包含的功能的视图。  |
| `float`              | `getFloat(String name, float defaultValue)`返回浮点键的值，如果缺少键，则返回默认值。 |
| `float`              | `getFloat(String name)`返回浮点键的值。                      |
| `int`                | `getInteger(String name)`返回整数键的值。                    |
| `int`                | `getInteger(String name, int defaultValue)`返回整数键的值，如果缺少键，则返回默认值。 |
| `Set<String>`        | `getKeys()`返回`Set`此 MediaFormat 中包含的键的视图。        |
| `long`               | `getLong(String name)`返回长键的值。                         |
| `long`               | `getLong(String name, long defaultValue)`返回长键的值，如果缺少键，则返回默认值。 |
| `Number`             | `getNumber(String name, Number defaultValue)`返回数字键的值，如果缺少键，则返回默认值。 |
| `Number`             | `getNumber(String name)`返回数字键的值。                     |
| `String`             | `getString(String name)`返回字符串键的值。                   |
| `String`             | `getString(String name, String defaultValue)`返回字符串键的值，如果缺少键，则返回默认值。 |
| `int`                | `getValueTypeForKey(String name)`返回键的值类型。            |
| `void`               | `removeFeature(String name)`如果存在，则删除给定的功能设置。 |
| `void`               | `removeKey(String name)`如果存在，则删除给定键的值。         |
| `void`               | `setByteBuffer(String name, ByteBuffer bytes)`设置 ByteBuffer 键的值。 |
| `void`               | `setFeatureEnabled(String feature, boolean enabled)`设置是启用 ( `true`) 还是禁用 ( `false`) 功能。 |
| `void`               | `setFloat(String name, float value)`设置浮点键的值。         |
| `void`               | `setInteger(String name, int value)`设置整数键的值。         |
| `void`               | `setLong(String name, long value)`设置长键的值。             |
| `void`               | `setString(String name, String value)`设置字符串键的值。     |
| `String`             | `toString()`返回对象的字符串表示形式。                       |