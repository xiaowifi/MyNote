# 正文

## 模块分组

Android 项目基于grade 提供构建编译。对于一个小项目而言，不存在太复杂的分组概念，估计就一个base或是一个common，然后一个app就可以了。但是对于一个需要频繁迭代开发人员较多的项目而言，分包就显得尤为的关键了。首先是根目录下的app，然后是module，最后的lib。module用于存放业务module，lib用于存放common或者base等外部或者基础库。那么如何分目录呢？

> 创建module等时候，module名称= 目录名称+冒号+module真正的名称。

这个的自动在setting中生成的配置文件就会像这个样子：

```
include ':app'
include ':lib:common'
```

逻辑上所有module都需要依赖于common。

## common 依赖库

```
implementation project(':lib:net')
    api project(':lib:silicompressor')
    //阿里云播放器的资源
    //api project(':lib:aliyunplayerres')
    //视频压缩
//    implementation 'com.github.guoxiaoxing:phoenix-compress-video:1.0.15'
    //下拉刷新
    api 'com.scwang.smart:refresh-layout-kernel:2.0.3'      //核心必须依赖
    api 'com.scwang.smart:refresh-header-classics:2.0.3'    //经典刷新头
    api 'com.scwang.smart:refresh-footer-classics:2.0.3'    //经典加载

    //基础adapter
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4'

    // ViewModel
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    api("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    api("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    //Lifecycles common-java8
    api("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
    //databinding
    api('androidx.databinding:databinding-runtime:4.2.1')

    //glide
    api "com.github.bumptech.glide:glide:$glide_version"
    //分包
    api 'androidx.multidex:multidex:2.0.1'
    //本地数据库
    api 'com.orhanobut:hawk:2.0.1'
    //阿里巴巴Arouter
    api "com.alibaba:arouter-api:$arouter_version"

    //koin 依赖核心库
    api "io.insert-koin:koin-core:$koin_version"
    api "io.insert-koin:koin-android:$koin_version"
    //rxBus
    api 'com.hwangjr.rxbus:rxbus:3.0.0'
    //banner
    api 'com.youth.banner:banner:1.4.10'
    //indicator
//    api 'com.github.hackware1993:MagicIndicator:1.5.0'
//    api(name: 'magicindicator-1.5.0', ext: 'aar')


    //webview// (必选)
    api 'com.github.Justson.AgentWeb:agentweb-core:v4.1.9-androidx'
    api 'com.github.lzyzsd:jsbridge:1.0.4'
//    api files('libs/agentweb-core-4.1.9.aar')
//    api files('libs/jsbridge-1.0.4.aar')

    //图片选择器框架
    api 'io.github.lucksiege:pictureselector:v2.7.3-rc08'
    //存储
    api 'com.tencent:mmkv-static:1.2.8'

    api 'org.jsoup:jsoup:1.10.3'
    //选择器
    api 'com.contrarywind:Android-PickerView:4.1.9'

    // 沉浸式基础依赖包，必须要依赖
    api 'com.gyf.immersionbar:immersionbar:3.0.0'
    // fragment快速实现（可选）
    api 'com.gyf.immersionbar:immersionbar-components:3.0.0'
    // kotlin扩展（可选）
    api 'com.gyf.immersionbar:immersionbar-ktx:3.0.0'

    api 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:6.7.9'
    //ali oss
    api 'com.aliyun.dpa:oss-android-sdk:2.9.5'

    //极光-统计分析
    api 'cn.jiguang.sdk:janalytics:2.1.4'

    //阿里云播放器
    api 'com.aliyun.sdk.android:AliyunPlayer:5.4.1-full'
    //阿里MQTT
    api 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0'
    api 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'
    //高德地图
    api 'com.amap.api:search:latest.integration'
    api 'com.amap.api:location:5.6.0'
    //权限
    api 'com.guolindev.permissionx:permissionx:1.6.1'
    //富文本
    api 'com.zzhoujay.richtext:richtext:2.5.1'

//    api(name: 'FlycoRoundView_Lib', ext: 'aar') {
//        exclude group: 'com.android.support'
//    }
    //svga
    api 'com.github.yyued:SVGAPlayer-Android:2.6.1'
//    api(name: 'svgaplayer-2.6.0', ext: 'aar')
//    api files('libs/svgaplayer-2.6.0.aar')

    //zxing
    api 'com.google.zxing:core:3.4.1'
    api 'com.google.zxing:android-core:3.3.0'

    api 'io.github.razerdp:BasePopup:3.1.8'
    //图片压缩
    api 'id.zelory:compressor:3.0.1'
    // 友盟统计SDK
    api 'com.umeng.umsdk:common:9.4.2'// 必选
    api 'com.umeng.umsdk:asms:1.4.0'// 必选
    api 'com.umeng.umsdk:abtest:1.0.0'//使用U-App中ABTest能力
    api 'com.umeng.umsdk:apm:1.4.2' // U-APM包依赖(必选)
    //骨架
    api 'com.ethanhua:skeleton:1.1.2'
    api 'io.supercharge:shimmerlayout:2.1.0'
    //圆形图片
    api 'de.hdodenhof:circleimageview:3.0.1'
    //版本升级
    api 'com.github.xuexiangjys:XUpdate:2.1.0'
    api 'com.github.xuexiangjys:XHttp2:2.0.4'
    api 'com.alibaba:fastjson:1.2.62'

    api('com.github.gzu-liyujiang.AndroidPicker:WheelPicker:4.1.6') {
        exclude group: 'com.android.support'
    }
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
```

* https://github.com/sucese/phoenix 视频图片压缩。
* https://github.com/scwang90/SmartRefreshLayout 下拉刷新，上拉加载更多
* https://github.com/CymChad/BaseRecyclerViewAdapterHelper。recyclerview 的adapter
* glide 图片加载
* arouter 路由导航
* https://github.com/orhanobut/hawk android的键值对存储 
* https://github.com/AndroidKnife/RxBus 消息总线
* banner
* https://github.com/hackware1993/MagicIndicator viewpager 指示器 



# 结束

