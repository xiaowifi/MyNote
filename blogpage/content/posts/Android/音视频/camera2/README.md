> 当前主要对于Android 系统库中的的camera2 官方提供的API 进行描述笔记整理。
## 资料
* [google camera2 官方地址](https://developer.android.google.cn/reference/android/hardware/camera2/package-summary)
* [google camera2 gitHub Demo地址](https://github.com/android/camera-samples)
## 前言
android.hardware.camera2 包为连接到 Android 设备的各个相机设备提供了一个接口。它取代了不推荐使用的Camera类。

此包将相机设备建模为管道，它接收用于捕获单个帧的输入请求，根据请求捕获单个图像，然后输出一个捕获结果元数据包，以及一组用于请求的输出图像缓冲区。请求按顺序处理，多个请求可以同时进行。由于摄像头设备是具有多个阶段的管道，因此需要在运行中处理多个请求才能在大多数 Android 设备上保持全帧率。

要枚举、查询和打开可用的摄像头设备，请获取一个 [CameraManager](https://developer.android.google.cn/reference/android/hardware/camera2/CameraManager) 实例。

个人CameraDevices提供一组静态属性信息，描述硬件设备以及设备的可用设置和输出参数。此信息通过 CameraCharacteristics对象提供，并可通过getCameraCharacteristics(String)

要从相机设备捕获或流式传输图像，应用程序必须首先创建一个camera capture session 带有一组输出 Surfaces 的用于相机设备的 createCaptureSession(SessionConfiguration). 每个 Surface 都必须预先配置appropriate size and format（如果适用）以匹配相机设备可用的尺寸和格式。目标 Surface 可以从各种类中获得，包括SurfaceView、 SurfaceTexture通过 Surface(SurfaceTexture)、 MediaCodec、MediaRecorder、 Allocation和ImageReader。

通常，相机预览图像被发送到SurfaceView或TextureView（通过其 SurfaceTexture）。DngCreator可以使用ImageReader和JPEG格式捕获 JPEG 图像或 RAW 缓冲区RAW_SENSOR。在 RenderScript、OpenGL ES 中或直接在托管或本机代码中对相机数据进行应用程序驱动的处理，最好分别通过AllocationYUV Type、SurfaceTexture和格式ImageReader来完成。YUV_420_888

然后，应用程序需要构建一个CaptureRequest，它定义了相机设备捕获单个图像所需的所有捕获参数。该请求还列出了哪些配置的输出表面应用作此捕获的目标。CameraDevice 有一个 factory method用于为给定用例创建一个request builder，它针对运行应用程序的 Android 设备进行了优化。

设置请求后，可以将其交给活动捕获会话以供一次性使用capture或无休止地repeating使用。这两种方法还有一个变体，它接受请求列表以用作突发捕获/重复突发。重复请求的优先级低于捕获，因此在capture()配置重复请求时提交的请求将在当前重复（突发）捕获的任何新实例开始捕获之前被捕获。

处理请求后，相机设备将生成一个TotalCaptureResult对象，其中包含有关相机设备在捕获时的状态以及最终使用的设置的信息。如果需要舍入或解决矛盾的参数，这些可能与请求有所不同。相机设备还将向Surfaces请求中包含的每个输出发送一帧图像数据。这些是相对于输出 CaptureResult 异步生成的，有时会晚得多。
## 正文
> 从上面的翻译可以大致，知道，相机这个硬件调用大致需要下面这些类。
* [CameraManager](https://developer.android.google.cn/reference/android/hardware/camera2/CameraManager) 
> 相机的管理工具，可以获取当前设备支持的相机类型，比如前后摄像头等等，通过前后摄像头的id 然后打开摄像头对象。CameraDevice
* [CameraDevice](https://developer.android.google.cn/reference/android/hardware/camera2/CameraDevice) 
> 摄像头对象。用于描述硬件信息
* [CameraDevice.StateCallback](https://developer.android.google.cn/reference/android/hardware/camera2/CameraDevice.StateCallback)
> 用于接收有关相机设备状态更新的回调对象。
必须CameraManager#openCamera为打开相机设备的方法提供回调实例。
这些状态更新包括有关设备完成启动（允许CameraDevice.createCaptureSession(SessionConfiguration)被调用）、有关设备断开或关闭以及有关意外设备错误的通知。
* [CameraCharacteristics](https://developer.android.google.cn/reference/android/hardware/camera2/CameraCharacteristics)
> 获取摄像头的硬件信息,这个信息是不可变的。
* [CameraCharacteristics.Key](https://developer.android.google.cn/reference/android/hardware/camera2/CameraCharacteristics.Key)
> 查询CameraCharacteristics 的相关信息。
* [CameraCaptureSession](https://developer.android.google.cn/reference/android/hardware/camera2/CameraCaptureSession)
> 用于从相机捕获图像或重新处理先前在同一会话中从相机捕获的图像。
CameraCaptureSession 是通过向 提供一组目标输出表面来创建的 createCaptureSession，或者通过为 可重新处理的捕获会话提供一个InputConfiguration和一组目标输出表面来 创建。createReprocessableCaptureSession一旦创建，会话将处于活动状态，直到相机设备创建新会话或相机设备关闭。
所有捕获会话都可用于从相机捕获图像，但只有可重新处理的捕获会话才能重新处理先前在同一会话中从相机捕获的图像。
创建会话是一项昂贵的操作，可能需要数百毫秒，因为它需要配置相机设备的内部管道并分配内存缓冲区以将图像发送到所需的目标。因此设置是异步完成的， createCaptureSession并且 createReprocessableCaptureSession会将准备使用的 CameraCaptureSession 发送到提供的侦听器的 onConfigured回调。如果无法完成配置，则 onConfigureFailed调用 ，并且会话不会变为活动状态。
如果相机设备创建了一个新会话，则关闭前一个会话，并onClosed调用其关联的回调。如果在会话关闭后调用所有会话方法，将抛出 IllegalStateException。
已关闭的会话会清除所有重复请求（就像stopRepeating()已被调用一样），但在新创建的会话接管并重新配置相机设备之前，仍将照常完成所有正在进行的捕获请求。
* [CameraCaptureSession.CaptureCallback](https://developer.android.google.cn/reference/android/hardware/camera2/CameraCaptureSession.CaptureCallback)
> 创建session 的回调 当请求触发捕获开始时以及捕获完成时调用此回调。如果在捕获图像时出错，将触发错误方法而不是完成方法。
* [CameraCaptureSession.StateCallback](https://developer.android.google.cn/reference/android/hardware/camera2/CameraCaptureSession.StateCallback)
> 相机状态的回调。
* [CameraConstrainedHighSpeedCaptureSession](https://developer.android.google.cn/reference/android/hardware/camera2/CameraConstrainedHighSpeedCaptureSession)
> 受限高速捕获会话，用于从高速视频录制用例 CameraDevice捕获高速图像。CameraDevice
CameraConstrainedHighSpeedCaptureSession 是通过向 CameraDevice#createCaptureSession(SessionConfiguration)类型提供会话配置来创建的 SessionConfiguration.SESSION_HIGH_SPEED。CameraCaptureSession.StateCallback然后可以将从中返回的 CameraCaptureSession 转换为 CameraConstrainedHighSpeedCaptureSession。一旦创建，会话将处于活动状态，直到相机设备创建新会话或相机设备关闭。
CameraCharacteristics#REQUEST_AVAILABLE_CAPABILITIES如果相机设备支持高速视频功能（即包含 ） ，活动高速捕获会话是仅针对高速视频录制 (>=120fps) 用例的专用捕获会话CameraMetadata#REQUEST_AVAILABLE_CAPABILITIES_CONSTRAINED_HIGH_SPEED_VIDEO。它只接受通过 创建createHighSpeedRequestList(CaptureRequest)的请求列表，并且请求列表只能通过captureBurst或 提交到该会话setRepeatingBurst。有关 CameraDevice#createCaptureSession(android.hardware.camera2.params.SessionConfiguration) 限制的更多详细信息，请参阅。
创建会话是一项昂贵的操作，可能需要数百毫秒，因为它需要配置相机设备的内部管道并分配内存缓冲区以将图像发送到所需的目标。因此设置是异步完成的，并将 CameraDevice#createConstrainedHighSpeedCaptureSession准备使用的 CameraCaptureSession 发送到提供的侦听器的 CameraCaptureSession.StateCallback#onConfigured回调。如果无法完成配置，则CameraCaptureSession.StateCallback#onConfigureFailed调用 ，并且会话不会变为活动状态。
如果相机设备创建了一个新会话，则关闭前一个会话，并onClosed调用其关联的回调。如果在会话关闭后调用所有会话方法，将抛出 IllegalStateException。
已关闭的会话会清除所有重复请求（就像CameraCaptureSession.stopRepeating()已被调用一样），但在新创建的会话接管并重新配置相机设备之前，仍将照常完成所有正在进行的捕获请求。
> <br> 等于说，和上面通用的session 差不多噢。
* [CameraMetadata](https://developer.android.google.cn/reference/android/hardware/camera2/CameraMetadata)
> 相机控制和信息的基类。
该类定义了用于查询相机特征或捕获结果以及设置相机请求参数的基本键/值映射。
CameraMetadata 的所有实例都是不可变的。在对象的整个生命周期中 ，键列表getKeys() 永远不会改变，任何键返回的值也不会改变
* [CameraOfflineSession](https://developer.android.google.cn/reference/android/hardware/camera2/CameraOfflineSession)
> 通过成功调用 切换到离线模式的相机捕获会话 CameraCaptureSession#switchToOffline。
离线捕获会话允许客户端选择一组支持离线模式的相机注册表面。在成功的离线模式切换后，这些表面上的所有非重复挂起请求将继续由相机堆栈处理，即使客户端关闭相应的相机设备也是如此。
离线捕获会话实例将在完成后替换所有捕获回调中先前活动的捕获会话参数CameraCaptureSession#switchToOffline。
待处理的离线捕获请求的处理将在离线会话移动到CameraOfflineSessionCallback#onReady 回调指示的就绪状态后开始。
与常规CameraCaptureSession的离线捕获会话相比，将不接受任何进一步的捕获请求。此外，CameraOfflineSession#close所有剩余的方法都会抛出UnsupportedOperationException并且不受支持。
* [CaptureRequest](https://developer.android.google.cn/reference/android/hardware/camera2/CaptureRequest)
> 从相机设备捕获单个图像所需的不可变设置和输出包。
包含捕获硬件（传感器、镜头、闪光灯）、处理管道、控制算法和输出缓冲区的配置。还包含目标 Surfaces 的列表，以将图像数据发送到此捕获。
CaptureRequests 可以使用Builder实例创建，通过调用获取CameraDevice#createCaptureRequest
CaptureRequests 被给予CameraCaptureSession#capture或 CameraCaptureSession#setRepeatingRequest从相机捕捉图像。
每个请求都可以为相机指定一个不同的目标表面子集，以将捕获的数据发送到。CameraDevice#createCaptureSession当请求提交到会话时，请求中使用的所有表面都必须是最后一次调用的表面列表的一部分 。
例如，用于重复预览的请求可能仅包括用于预览 SurfaceView 或 SurfaceTexture 的 Surface，而高分辨率静态捕获还包括来自为高分辨率 JPEG 图像配置的 ImageReader 的 Surface。
重新处理捕获请求允许将先前从相机设备捕获的图像发送回设备以进行进一步处理。它可以使用 创建 CameraDevice#createReprocessCaptureRequest，并与使用创建的可重新处理的捕获会话一起使用CameraDevice#createReprocessableCaptureSession。
* [CaptureResult](https://developer.android.google.cn/reference/android/hardware/camera2/CaptureResult)
> 从图像传感器捕获的单个图像结果的子集。
包含捕获硬件（传感器、镜头、闪存）、处理管道、控制算法和输出缓冲区的最终配置的子集。
CaptureResults 由 aCameraDevice在处理 a 后产生 CaptureRequest。还可以在捕获结果上查询为捕获请求列出的所有属性，以确定用于捕获的最终值。结果还包括有关捕获期间相机设备状态的其他元数据。
并非所有返回的属性CameraCharacteristics#getAvailableCaptureResultKeys() 都必须可用。一些结果是partial并且不会具有每个键集。只有total结果才能保证请求启用的每个密钥都可用。
CaptureResult对象是不可变的。
* [MultiResolutionImageReader](https://developer.android.google.cn/reference/android/hardware/camera2/MultiResolutionImageReader)
> MultiResolutionImageReader 类包装了一组ImageReaders具有相同格式和不同大小、源相机 ID 或相机传感器模式的组。
此类的主要用例是让一个 multi-camera或一个超高分辨率传感器相机输出可变大小的图像。对于实现光学变焦的逻辑多摄像头，不同的物理摄像头可能具有不同的最大分辨率。因此，当相机设备根据缩放比例在物理相机之间切换时，特定格式的最大分辨率可能会发生变化。对于超高分辨率传感器相机，相机设备可能会根据光照条件认为在最高分辨率模式/默认模式下运行会更好或更差。所以应用程序可以选择让相机设备代表它来决定。
MultiResolutionImageReader 仅当相机设备通过在CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP.
要从 MultiResolutionImageReader 获取图像，应用程序必须使用 回调ImageReader传递的对象 ImageReader.OnImageAvailableListener#onImageAvailable来调用 ImageReader#acquireNextImage或ImageReader#acquireLatestImage。应用程序不能使用回调ImageReader传递的来获取未来的图像，因为未来的 ImageReader.OnImageAvailableListener.onImageAvailable(ImageReader)图像可能来自.ImageReaderMultiResolutionImageReader
* [TotalCaptureResult](https://developer.android.google.cn/reference/android/hardware/camera2/TotalCaptureResult)
> 从图像传感器捕获的单个图像的总组装结果。
包含捕获硬件（传感器、镜头、闪光灯）、处理管道、控制算法和输出缓冲区的最终配置。
ATotalCaptureResult由 aCameraDevice在处理 a 后产生 CaptureRequest。还可以在捕获结果上查询为捕获请求列出的所有属性，以确定用于捕获的最终值。结果还包括有关捕获期间相机设备状态的其他元数据。
返回的所有属性CameraCharacteristics#getAvailableCaptureResultKeys() 都是可用的（也就是说，当且仅当请求启用了该键时 CaptureResult#get才会返回非。除非使用开关启用（例如），否则默认情况下禁用一些键（例如）。请参阅每个键逐案记录。nullCaptureResult#STATISTICS_FACESCaptureRequest#STATISTICS_FACE_DETECT_MODE
对于逻辑多摄像头设备，如果 CaptureRequest 包含底层物理摄像头的表面，则相应的TotalCaptureResult对象将包含该物理摄像头的元数据。物理相机 id 和结果元数据之间的映射可以通过getPhysicalCameraResults(). 如果所有请求的表面都用于逻辑相机，则不会包含物理相机的元数据。
TotalCaptureResult对象是不可变的。
* [CameraAccessException](https://developer.android.google.cn/reference/android/hardware/camera2/CameraAccessException)
> 相机访问异常。CameraAccessException如果 无法查询或打开相机设备CameraManager，或者与打开的连接CameraDevice不再有效，则抛出此错误。
* [Surface]() 
> 摄像头输出承接对象，这个预览，图像分析(二维码扫描，人像定位)，录制，拍照等，每个操作都需要一个这个对象。
* [SurfaceView](https://developer.android.google.cn/reference/android/view/SurfaceView)
> 
* [MediaCodec](https://developer.android.google.cn/reference/android/media/MediaCodec) 
> 应该是视频，音频编码解码工具。
* [MediaRecorder](https://developer.android.google.cn/reference/android/media/MediaRecorder) 
> 音频视频录制工具。
* [Allocation](https://developer.android.google.cn/reference/android/renderscript/Allocation) 
* [ImageReader](https://developer.android.google.cn/reference/android/media/ImageReader)
> ImageReader 类允许应用程序直接访问呈现为Surface
一些 Android 媒体 API 类接受 Surface 对象作为渲染目标，包括MediaPlayer、MediaCodec、 和 CameraDeviceImageWriterRenderScript Allocations. 每个来源可以使用的图像大小和格式各不相同，应在特定 API 的文档中进行检查。
图像数据被封装在Image对象中，可以同时访问多个这样的对象，最多可以访问 maxImages构造函数参数指定的数量。通过它发送到 ImageReader 的新图像将Surface排队，直到通过acquireLatestImage() oracquireNextImage()调用访问。由于内存限制，如果 ImageReader 没有以等于生产速率的速率获取和释放图像，则图像源最终会在尝试渲染到 Surface 时停止或丢弃图像。
* [DngCreator](https://developer.android.google.cn/reference/android/hardware/camera2/DngCreator)
> 该类DngCreator提供将原始像素数据写入 DNG 文件的功能。
此类设计用于与ImageFormat.RAW_SENSOR 从 中可用的缓冲区CameraDevice或由应用程序生成的 Bayer 类型的原始像素数据一起使用。DNG 元数据标签将从CaptureResult对象生成或直接设置。
DNG 文件格式是一种跨平台文件格式，用于存储来自相机传感器的像素数据，并且应用了最少的预处理。DNG 文件允许在用户定义的色彩空间中定义像素数据，并具有关联的元数据，允许在后处理期间将此像素数据转换为标准 CIE XYZ 色彩空间。
* [RenderScript]() 
* [OpenGL ES ]()
### 
