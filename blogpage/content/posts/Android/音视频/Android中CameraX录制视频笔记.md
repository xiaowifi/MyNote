### ǰ�� 
�ο����ݣ�
* [google camerax ���ĵ�ַ](https://developer.android.google.cn/training/camerax) 
* [Google Android ���Github Demo��ַ](https://github.com/android/camera-samples) 
## ����
> ������Ҫ��github�е�Demo��* [Google Android ���Github Demo��ַ](https://github.com/android/camera-samples) 
### Ȩ������
ActivityResultContracts�� ���������ɶ�� 
````aidl
    implementation 'androidx.activity:activity-ktx:1.4.0'
    implementation "androidx.fragment:fragment-ktx:1.4.0"
````
### maven ������
````aidl
 // CameraX dependencies (first release for video is: "1.1.0-alpha10")
    def camerax_version = "1.1.0-alpha12"
    // The following line is optional, as the core library is included indirectly by camera-camera2
    implementation "androidx.camera:camera-core:${camerax_version}"
    implementation "androidx.camera:camera-camera2:${camerax_version}"
    implementation "androidx.camera:camera-lifecycle:${camerax_version}"
    implementation "androidx.camera:camera-video:${camerax_version}"
    // If you want to additionally use the CameraX View class
    implementation "androidx.camera:camera-view:1.0.0-alpha32"
````
### navigation
````aidl
    def nav_version = "2.4.0-rc01"
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
````
### �������
* [ImageCapture�������](https://developer.android.google.cn/training/camerax/configuration)


### ����ͷԤ��
* �ٷ�����ʹ�� [PreviewView](https://developer.android.google.cn/training/camerax/preview)

### ͼƬ����