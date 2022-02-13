## 资料
* [CaptureRequest 官方地址](https://developer.android.google.cn/reference/android/hardware/camera2/CaptureRequest)
* [CSDN](https://blog.csdn.net/afei__/article/details/86326991)
## 前言
CaptureRequest 主要是由3部分组成
* CaptureRequest
* CaptureRequest.builder 构造器用于通过设置不同的key 生成对象CaptureRequest对象。
* CaptureRequest.key 
## 正文

## code
### 预览
session=mCameraDevice.createCaptureSession();<br>
builder = session.getDevice().createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
#### 开启闪光灯
````aidl
builder.set(FLASH_MODE,FLASH_MODE_TORCH);
mSession.setRepeatingRequest(builder.build(),mCaptureCallback , cameraHandler);
````
#### 关闭闪光灯
````aidl
builder.set(FLASH_MODE,FLASH_MODE_OFF);
mSession.setRepeatingRequest(builder.build(),mCaptureCallback , cameraHandler);
````

