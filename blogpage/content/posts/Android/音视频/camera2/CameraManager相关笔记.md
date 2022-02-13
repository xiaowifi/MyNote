## 参考资料

* [CameraManger官方文档](https://developer.android.google.cn/reference/android/hardware/camera2/CameraManager)
* [CSDN](https://blog.csdn.net/afei__/article/details/85342160)

## 前言

当前class 主要实现几个功能。

* 查询摄像头信息。
* 打开摄像头
* 操作对应摄像头的闪光等。

## 正文

| 嵌套类  |                                                              |
| :------ | ------------------------------------------------------------ |
| `class` | `CameraManager.AvailabilityCallback`相机设备可用或无法打开的回调。 |
| `class` | `CameraManager.TorchCallback`相机闪光灯手电筒模式变得不可用、禁用或启用的回调。 |

| 公共方法                         |                                                              |
| :------------------------------- | ------------------------------------------------------------ |
| `CameraCharacteristics`          | `getCameraCharacteristics(String cameraId)`查询摄像头设备的能力。 |
| `CameraExtensionCharacteristics` | `getCameraExtensionCharacteristics(String cameraId)`查询摄像头设备的摄像头扩展能力。 |
| `String[]`                       | `getCameraIdList()`按标识符返回当前连接的相机设备列表，包括可能被其他相机 API 客户端使用的相机。 |
| `Set<Set<String>>`               | `getConcurrentCameraIds()`返回当前连接的摄像头设备标识符的组合集，支持同时配置摄像头设备会话。 |
| `int`                            | `getTorchStrengthLevel(String cameraId)`返回与 cameraId 关联的闪光灯单元的亮度级别。 |
| `boolean`                        | `isConcurrentSessionConfigurationSupported(Map<String, SessionConfiguration> cameraIdAndSessionConfig)`检查提供的一组相机设备及其对应的是否 `SessionConfiguration`可以同时配置。 |
| `void`                           | `openCamera(String cameraId, CameraDevice.StateCallback callback, Handler handler)`打开与具有给定 ID 的相机的连接。 |
| `void`                           | `openCamera(String cameraId, Executor executor, CameraDevice.StateCallback callback)`打开与具有给定 ID 的相机的连接。 |
| `void`                           | `registerAvailabilityCallback(Executor executor, CameraManager.AvailabilityCallback callback)`注册回调以获取有关相机设备可用性的通知。 |
| `void`                           | `registerAvailabilityCallback(CameraManager.AvailabilityCallback callback, Handler handler)`注册回调以获取有关相机设备可用性的通知。 |
| `void`                           | `registerTorchCallback(Executor executor, CameraManager.TorchCallback callback)`注册一个回调以获取有关手电筒模式状态的通知。 |
| `void`                           | `registerTorchCallback(CameraManager.TorchCallback callback, Handler handler)`注册一个回调以获取有关手电筒模式状态的通知。 |
| `void`                           | `setTorchMode(String cameraId, boolean enabled)`在不打开相机设备的情况下设置给定ID相机的闪光灯单元的手电筒模式。 |
| `void`                           | `turnOnTorchWithStrengthLevel(String cameraId, int torchStrength)`在手电筒模式下设置与给定 cameraId 关联的手电筒的亮度级别。 |
| `void`                           | `unregisterAvailabilityCallback(CameraManager.AvailabilityCallback callback)`移除之前添加的回调；回调将不再接收连接和断开回调。 |
| `void`                           | `unregisterTorchCallback(CameraManager.TorchCallback callback)`移除之前添加的回调；回调将不再接收手电筒模式状态回调。 |

### Code

> 逻辑上讲，硬件操作需要在子线程中进行，所以这个调调需要初始化一个子线程和一个handler.同时当界面生命周期结束的时候，关闭线程。

````aidl
// 开启线程。
 cameraThread = new HandlerThread("cameraThread");
 cameraThread.start();
 cameraHandler = new Handler(cameraThread.getLooper());
 // 关闭线程
  cameraThread.quitSafely();
  try {
        cameraThread.join();
    }catch (Exception e){
    }
````

#### 开始获取 cameraManger

````aidl
cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
````

#### 开始相机可用添加监听

````aidl
cameraManager.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
            @Override
            public void onCameraAvailable(@NonNull String cameraId) {
                super.onCameraAvailable(cameraId);
                Log.e(TAG, "onCameraAvailable 可用的相机: "+cameraId);
            }

            @Override
            public void onCameraUnavailable(@NonNull String cameraId) {
                super.onCameraUnavailable(cameraId);
                Log.e(TAG, "onCameraUnavailable相机不可用: "+cameraId );
            }

            @Override
            public void onCameraAccessPrioritiesChanged() {
                super.onCameraAccessPrioritiesChanged();
                Log.e(TAG, "onCameraAccessPrioritiesChanged 关于相机访问优先级已更改: " );
            }
        },cameraHandler);
````

#### 添加闪光灯监听

````aidl
        cameraManager.registerTorchCallback(new CameraManager.TorchCallback() {
            @Override
            public void onTorchModeUnavailable(@NonNull String cameraId) {
                super.onTorchModeUnavailable(cameraId);
                Log.e(TAG, "onTorchModeUnavailable闪光灯不可用 : "+cameraId );
            }

            @Override
            public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
                super.onTorchModeChanged(cameraId, enabled);
                Log.e(TAG, "onTorchModeChanged 闪光灯模式已经更改 : "+cameraId+"  "+enabled );
            }
        },cameraHandler);
````

#### 单纯打开闪光灯。

````aidl
 cameraManager.setTorchMode("0",true);
````

#### 单纯关闭闪光灯

````aidl
 cameraManager.setTorchMode("0",false);
````

#### 打开相机

````aidl
                    cameraManager.openCamera("0", new CameraDevice.StateCallback() {
                        @Override
                        public void onOpened(@NonNull CameraDevice cameraDevice) {
                            Log.e(TAG, "onOpened 打开相机: "+cameraDevice.getId() );
                        }

                        @Override
                        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                            Log.e(TAG, "onDisconnected 相机断开: "+cameraDevice.getId() );
                        }

                        @Override
                        public void onError(@NonNull CameraDevice cameraDevice, int i) {
                            Log.e(TAG, "onDisconnected 发生了错误: "+cameraDevice.getId()+"  "+i );
                        }
                    },cameraHandler);

````

#### 打开相机下的闪光灯
> 如果子打开摄像头之后，使用 cameraManager.setTorchMode("0",true)是无法打开闪光灯的，同时也无法关闭闪光等。
> 要想操作闪光灯需要拿到session，通过session 进行操作。
#### 打开相机调用监听调用流程
> 如果在调用打开相机之前开启闪光灯，后调用打开相机，闪光灯会关闭。
第一次调用打开相机
* onTorchModeUnavailable闪光灯不可用 : 0
* onCameraUnavailable相机不可用: 0
* onOpened 打开相机: 0
第2次调用打开相机
* onDisconnected 相机断开: 0
* onTorchModeChanged 闪光灯模式已经更改 : 0  false
* onCameraAvailable 可用的相机: 0
* onTorchModeUnavailable闪光灯不可用 : 0
* onCameraUnavailable相机不可用: 0
* onOpened 打开相机: 0

