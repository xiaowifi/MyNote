当前目主要是做第3方maven 相关的学习的笔记位置。
## 资料
* [google 提供 camerax 相关api Demo地址](https://github.com/android/camera-samples)
* [google maven仓库地址](https://maven.google.com/web/index.html?)
* [google camerax 官网](https://developer.android.com/jetpack/androidx/releases/camera#1.2.0-alpha02)
# 正文
## Camrea 2相关
Camera2 是最新的低级 Android 相机包，取代了已弃用的Camera类。Camera2 为复杂的用例提供了深入的控制，但需要您管理特定于设备的配置。您可以在参考文档中阅读有关特定 Camera2 类和函数的信息。
对于大多数开发人员，我们建议使用CameraX Jetpack 库。有关详细信息，请参阅选择相机库。
## CamreaX相关
> camreax 是对于camrea 2的封装。
> CameraX 是一个 Jetpack 库，旨在帮助您更轻松地开发相机应用。对于新应用，我们建议从 CameraX 开始。它提供一致且易于使用的 API，适用于绝大多数 Android 设备，并向后兼容 Android 5.0（API 级别 21）。
```aidl
// CameraX dependencies (first release for video is: "1.1.0-alpha10")
    def camerax_version = "1.1.0-beta01"
    // The following line is optional, as the core library is included indirectly by camera-camera2
    //以下行是可选的，因为核心库间接包含在 camera-camera2 中
    implementation "androidx.camera:camera-core:${camerax_version}"
    implementation "androidx.camera:camera-camera2:${camerax_version}"
    implementation "androidx.camera:camera-lifecycle:${camerax_version}"
    implementation "androidx.camera:camera-video:${camerax_version}"
    implementation "androidx.camera:camera-view:${camerax_version}"
    implementation 'androidx.camera:camera-extensions:${camerax_version}'
```
上述是默认最新Demo maven 相关文件，camerax 是对于camera2 api 的封装。
### maven 解析
主要的对于maven 进行描述。
#### camera-core
> Jetpack 相机库的核心组件，该库提供一致且可靠的相机基础，可在所有 Android 中实现出色的相机驱动体验。
* [版本序列](https://maven.google.com/web/index.html?#androidx.camera:camera-core)
#### camera-camera2
* [版本序列](https://maven.google.com/web/index.html?#androidx.camera:camera-camera2)
> Jetpack 相机库的 Camera2 实现和扩展，该库提供一致且可靠的相机基础，可在所有 Android 中实现出色的相机驱动体验。
#### camera-lifecycle
> Jetpack 相机库的生命周期组件，该库提供一致且可靠的相机基础，可在所有 Android 中实现出色的相机驱动体验。
* [版本序列](https://maven.google.com/web/index.html?#androidx.camera:camera-lifecycle)
#### camera-video
> Jetpack 相机库的视频组件，该库提供一致且可靠的相机基础，可在所有 Android 中实现出色的相机驱动体验。
* [版本序列](https://maven.google.com/web/index.html?#androidx.camera:camera-video)
#### camera-view
> Jetpack 相机库的 UI 工具，该库提供一致且可靠的相机基础，可在所有 Android 中实现出色的相机驱动体验。
* [版本序列](https://maven.google.com/web/index.html?#androidx.camera:camera-view)
#### camera-extensions
> Jetpack 相机库的 OEM 扩展，该库提供与 OEM 特定相机功能集成的接口。
* [版本序列](https://maven.google.com/web/index.html?#androidx.camera:camera-extensions)
#### mlkit-vision
> 最新版本 API 才有的.Jetpack 相机库的 MLKit 视觉组件，该库提供无缝集成，可在所有 Android 中实现相机驱动的计算机视觉功能。
* [版本序列](https://maven.google.com/web/index.html?#androidx.camera:camera-mlkit-vision)