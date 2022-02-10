## 参考资料
* [CameraDevice官方文档](https://developer.android.google.cn/reference/android/hardware/camera2/CameraDevice)
* [CameraDevice CSDN](https://blog.csdn.net/afei__/article/details/85342597)

## 前言
在使用 camera2中。经常需要和 CameraDevice 打交道。
在翻译中，CameraDevice 这个被定义为与设备相关。
````aidl

/**
 * <p>The CameraDevice class is a representation of a single camera connected to an
 * Android device, allowing for fine-grain control of image capture and
 * post-processing at high frame rates.
 * CameraDevice 类是连接到 Android 设备的单个摄像头的表示，允许以高帧速率对图像捕获和后处理进行细粒度控制</p>
 *
 * <p>Your application must declare the
 * {@link android.Manifest.permission#CAMERA Camera} permission in its manifest
 * in order to access camera devices.</p>
 *
 * <p>A given camera device may provide support at one of several levels defined
 * in {@link CameraCharacteristics#INFO_SUPPORTED_HARDWARE_LEVEL}.
 * If a device supports {@link CameraMetadata#INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY LEGACY} level,
 * the camera device is running in backward compatibility mode and has minimum camera2 API support.
 * If a device supports the {@link CameraMetadata#INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED LIMITED}
 * level, then Camera2 exposes a feature set that is roughly equivalent to the older
 * {@link android.hardware.Camera Camera} API, although with a cleaner and more
 * efficient interface.
 * If a device supports the {@link CameraMetadata#INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL EXTERNAL}
 * level, then the device is a removable camera that provides similar but slightly less features
 * as the {@link CameraMetadata#INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED LIMITED} level.
 * Devices that implement the {@link CameraMetadata#INFO_SUPPORTED_HARDWARE_LEVEL_FULL FULL} or
 * {@link CameraMetadata#INFO_SUPPORTED_HARDWARE_LEVEL_3 LEVEL3} level of support
 * provide substantially improved capabilities over the older camera
 * API. If your application requires a full-level device for
 * proper operation, declare the "android.hardware.camera.level.full" feature in your
 * manifest.</p>
 *
 * @see CameraManager#openCamera
 * @see android.Manifest.permission#CAMERA
 * @see CameraCharacteristics#INFO_SUPPORTED_HARDWARE_LEVEL
 */
````
## 常量
* public static final int TEMPLATE_PREVIEW = 1;
> 创建适用于相机预览窗口的请求。具体来说，这意味着高帧率优先于最高质量的后期处理。这些请求通常与 {@link CameraCaptureSessionsetRepeatingRequest} 方法一起使用。保证所有相机设备都支持此模板。
* TEMPLATE_STILL_CAPTURE
> 创建适合静态图像捕获的请求。具体来说，这意味着优先考虑图像质量而不是帧速率。这些请求通常与 {@link CameraCaptureSessioncapture} 方法一起使用。除了不是 {@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE BACKWARD_COMPATIBLE} 的 {@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT DEPTH_OUTPUT} 设备外，所有相机设备都保证支持此模板。
* TEMPLATE_RECORD
> 创建适合视频录制的请求。具体而言，这意味着使用稳定的帧速率，并为记录质量设置后处理。这些请求通常与 {@link CameraCaptureSessionsetRepeatingRequest} 方法一起使用。除了不是 {@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE BACKWARD_COMPATIBLE} 的 {@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT DEPTH_OUTPUT} 设备外，所有相机设备都保证支持此模板。
* TEMPLATE_VIDEO_SNAPSHOT
> 在录制视频时创建适合静态图像捕获的请求。具体来说，这意味着在不中断正在进行的录制的情况下最大限度地提高图像质量。这些请求通常与 {@link CameraCaptureSessioncapture} 方法一起使用，而基于 {@link TEMPLATE_RECORD} 的请求与 {@link CameraCaptureSessionsetRepeatingRequest} 一起使用。这个模板是保证除了传统设备上的所有的相机设备支持（{@link CameraCharacteristicsINFO_SUPPORTED_HARDWARE_LEVEL} {@code ==} {@链接CameraMetadataINFO_SUPPORTED_HARDWARE_LEVEL_LEGACY LEGACY}）和{@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT DEPTH_OUTPUT}不在设备{@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE BACKWARD_COMPATIBLE} .
* TEMPLATE_ZERO_SHUTTER_LAG
> 创建适用于零快门延迟静态捕捉的请求。这意味着在不影响预览帧速率的情况下最大限度地提高图像质量。 AEAWBAF 应处于自动模式。这适用于应用程序操作的 ZSL。对于设备操作的 ZSL，如果可用，请使用 {@link CaptureRequestCONTROL_ENABLE_ZSL}。支持 {@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_PRIVATE_REPROCESSING PRIVATE_REPROCESSING} 功能或 {@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_YUV_REPROCESSING YUV_REPROCESSING} 功能的相机设备保证支持此模板。
* TEMPLATE_MANUAL 
> 用于直接应用程序控制捕获参数的基本模板。禁用所有自动控制（自动曝光、自动白平衡、自动对焦），并将后处理参数设置为预览质量。手动捕获参数（曝光、灵敏度等）设置为合理的默认值，但应根据预期用例由应用程序覆盖。支持 {@link CameraMetadataREQUEST_AVAILABLE_CAPABILITIES_MANUAL_SENSOR MANUAL_SENSOR} 功能的相机设备保证支持此模板。





















