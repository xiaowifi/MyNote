## 资料
* [AndroidManifest 文件简述](https://developer.android.com/guide/topics/manifest/manifest-intro)

# 正文

每个Android 项目必须有一个 AndroidManifest.xml 文件。且必须以此命名。如果说组件化设计中需要 多个的话，需要保证两个文件保持一致，同时通过build.gradle 配置运行时使用哪一个。同时build.gradle 中也可以配置此文件中需要的参数。

重点是，清单文件需声明以下内容：

- 应用的软件包名称，其通常与代码的命名空间相匹配。 构建项目时，Android 构建工具会使用此信息来确定代码实体的位置。 打包应用时，构建工具会使用 Gradle 构建文件中的应用 ID 来替换此值，而此 ID 则用作系统和 Google Play 上的唯一应用标识符。[了解关于软件包名称和应用 ID 的更多内容](https://developer.android.com/guide/topics/manifest/manifest-intro#package-name)。
- 应用的组件，包括所有 Activity、服务、广播接收器和内容提供程序。 每个组件都必须定义基本属性，例如其 Kotlin 或 Java 类的名称。 清单文件还能声明一些功能，例如其所能处理的设备配置，以及描述组件如何启动的 Intent 过滤器。[了解关于应用组件的更多内容](https://developer.android.com/guide/topics/manifest/manifest-intro#components)。
- 应用为访问系统或其他应用的受保护部分所需的权限。 如果其他应用想要访问此应用的内容，则清单文件还会声明其必须拥有的权限。 [了解关于权限的更多内容](https://developer.android.com/guide/topics/manifest/manifest-intro#perms)。
- 应用需要的硬件和软件功能，这些功能会影响哪些设备能够从 Google Play 安装应用。[了解关于设备兼容性的更多内容](https://developer.android.com/guide/topics/manifest/manifest-intro#compatibility)。

如果您使用 [Android Studio](https://developer.android.com/studio) 构建应用，则系统会为您创建清单文件，并在您构建应用时（尤其是在使用[代码模板](https://developer.android.com/studio/projects/templates)时）添加大部分基本清单元素。

## 节点

### action

向 Intent 过滤器添加操作。 `<intent-filter>` 元素必须包含一个或多个 `<action>` 元素。如果 Intent 过滤器中没有 `<action>` 元素，则过滤器不接受任何 `Intent` 对象。如需详细了解 Intent 过滤器和操作规范在过滤器中的作用，请参阅 [Intent 和 Intent 过滤器](https://developer.android.com/guide/components/intents-filters)。

### activity

### activity-alias

### application

### compatible-screens

### data

### grant-uri-permission

### instrumentation

### intent-filter

### manifest

### meta-data

### path-permission

### permission

### permisssion-group

### permisssion-tree

### profileable

### provider

### queries

### receiver

### service

### supports-gl-textture

### supports-screens

### uses-configuration

### uses-feature

### uses-library

### uses-permission

### uses-permission-sdk-23

### uses-sdk



# 总结

