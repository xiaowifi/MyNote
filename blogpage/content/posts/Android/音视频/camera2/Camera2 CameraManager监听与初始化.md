## 资料
* [项目地址 ](https://github.com/SereneGuest/Camera2)
* [google camera2 官方地址](https://developer.android.google.cn/reference/android/hardware/camera2/package-summary)
* [google camera2 gitHub Demo地址](https://github.com/android/camera-samples)
* [CameraManager](https://developer.android.google.cn/reference/android/hardware/camera2/CameraManager) 
# 正文
> CameraManager 用于检测、表征和连接到 CameraDevices.
有关与摄像头设备通信的更多详细信息，请阅读摄像头开发人员指南或camera2 软件包文档。
## 获取 CameraManager 
````aidl
    CameraManager cameraManager = (CameraManager) requireContext().getSystemService(Context.CAMERA_SERVICE);
````
## 添加监听
