# 前言
aapt2是一种构建工具，Android studio 和Android gradle插件使用他来编译和打包应用资源。aapt2 会解析资源、为资源编制索引。并将资源编译为针对Android平台进行优化过的二进制格式。

Android gradle 插件 3.0.0及其更高版本默认开启AAPT2。如果你更愿意使用自己的中断和构建系统而不是Android studio，则可以通过命令行只用AAPT2。
AAPT2支持通过启用增量编译实现更快的资源编译，这是通过将资源处理拆分为两个步骤来实现的：
* 编译：将资源文件编译为二进制格式。
* 链接：合并所有已编译的文件，并将他们打包到一个软件包中。
## 资料
* [AAPT2（Android 资源打包工具）](https://developer.android.com/studio/command-line/aapt2)
# 正文
## 编译
AAPT2 支持编译所有 [Android资源类型](https://developer.android.com/guide/topics/resources/available-resources)
如客绘制对象和xml 文件，调用AAPT2进行编译时，每次调用都应该传递一个资源文件作为输入，然后AAPT2会协议该文件生成一个扩展名为.flat的中间二进制文件。
虽然你可以使用--dir 标记多个资源文件目录传递给AAPT2.但是如果这个做，就无法获得增量编译资源的优势。
输出文件的类型可能会因您为编译提供的输入而异：



| 输入                                                         | 输出                                                         |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| XML 资源文件（如 [String](https://developer.android.com/guide/topics/resources/string-resource) 和 [Style](https://developer.android.com/guide/topics/resources/style-resource)），它们位于 `res/values/` 目录下。 | 以 `*.arsc.flat` 作为扩展名的资源表。                        |
| 其他所有资源文件。                                           | 除 `res/values/` 目录下的文件以外的其他所有文件都将转换为扩展名为 `*.flat` 的二进制 XML 文件。此外，默认情况下，所有 PNG 文件都会被压缩，并采用 `*.png.flat `扩展名。如果选择不压缩 PNG，您可以在编译期间使用 `--no-crunch` 选项。 |

aapt2输出的文件不是可执行文件，稍后你必须在链接阶段添加这些二进制文件作为输入来生成apk,但是，所生成的apk 文件不是可以立即部署到Android 设备上的可执行文件，因为他不包含dex 文件，且未签名。

## 链接

在链接阶段，AAPT2会合并在编译阶段生成的所有中间文件（资源表，二进制xml和处理过的png）并将他们打包成一个apk,此外，在此阶段还生成其他辅助文件，如R.java和proguard 规则文件，不同，生成的apk 不包含dex字节码且未签名，如果不使用Android gradle 则可以使用其他命令行工具，如d8将JAVA 字节码编译为dex,以及使用apksigner 为apk 签名。

## 转储

dump y用于输出有关您使用link 命令生成的apk 信息。

# 总结 