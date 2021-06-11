+++
date = "2021-6-11"
title = "Mpaas 相关文档汇总"
description = "Mpaas 相关文档汇总"
tags = [ "mpaas"]

featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> [mpaas 主页](https://help.aliyun.com/document_detail/51741.html) <br>
## 正文
### 工作空间相关
> 开始配置mpaas 的时候，会从阿里后台下载一个config文件。 android 需要提供正式环境的apk，然后下载一个config 文件。
内容大概都是这个样子的。因为有多个工作空间，所以这个调调，就有多个，当然字段是一样的，就是值不一样。下面内容被删除了部分信息，保留字段了。无法直接使用。
````aidl
{
	"appId":"",
	"appKey":"",
	"base64Code":"/9j/4/2/2/wAARCAADAAMDASIAAhEBAxEB/8/8+Tl5ufo6erx8vP09fb3+/8/8+Pn6/9/++iQTI1EMRmCMv9zR2PQM6L/e+DtobnevS3PdGJ0EIA/K394aH6P7paEMAczXk4VzYejvyNQ/yFgWFgUDTzaYzN5i802xmwmBeP7tbaRuGkCFjLwPNXHs7mlqnT4Bp5jQq5digff98FkuWuWNhX/VkdKKy2wtZqzCeumKM3ilNUKqhX576kOI3xem3VgW4yR8b7DnyhjuTq6zrC/gTeE2r29cCiJ0uREAC77pFw8C4uqt3OM7xlA3anZzwNvUNiGKWIT41UAJJmR8FMyGKT914Gl2e7yRWNPAKwh5mXJHTDO9uPBiMq1FsIntwROaWkOO8QfdKh0NabzMgWTkJjbY5uOkYfsuEUeCULcc+TsnkB3cBxDWkdu6OBquTcWkL3PhvXF6DPkKW7dI/sQjbSdVgqdcRvI/kGi69TUvVhPZuidHaAKJUfHzWnN7FP5C6RUgEIUG8KQb7FiBq557uvSoa5qAK82xwXlYbmzeJWyS1gxmJZ3uUhafYTDmZCehwhP4r/cSSW+1EM+SYYPvZiJ0+NEa627Ci7Xqh/U/wAAwqAYBgAAAAA=",
	"packageName":"",
	"rootPath":"mpaas/android/ONEX6D6D335231650-release",
	"workspaceId":"release",
	"syncport":"443",
	"syncserver":"mpaas-mss.aliyuncs.com",
	"logGW":"https://mpaas-mas-loggw.aliyuncs.com",
	"pushPort":"443",
	"pushGW":"mpaas-mps.aliyuncs.com",
	"mpaasapi":"https://cn-hangzhou-component-gw.cloud.alipay.com/mgw.htm",
	"rpcGW":"https://mpaas-mgs.aliyuncs.com/mgw.htm",
	"mrtcserver":"wss://cn-hangzhou-mrtc.cloud.alipay.com/ws",
	"mpaasConfigVersion":"V_1.0",
	"mpaasConfigEnv":"ONEX_CLOUD",
	"mpaasConfigPluginExpired":"",
	"mpaasConfigLicense":"+/QiL/xP2y4+IzktpDZdmF9+/2+nX3jm8WHRUyY+fu8ADt1/++FjnwvB81rXZ0+/+A=="
}
````
既然key 都是一样的，所谓的工作空间就有不同的值去约束定义。通常来讲，手动切换这个config文件之后，通过mpaas 插件重新导入之后是可以运行的。
但是万一出现了问题了呢？
这个就涉及到打log联调等等了。
#### 不同工作空间值的差异
相同:
* appId 
* appKey
* packageName
* syncport
* syncserver
* logGW
* pushPort
* pushGW
* mpaasapi
* rpcGW
* mrtcserver
* mpaasConfigVersion
* mpaasConfigEnv
* mpaasConfigPluginExpired


<br>
差异:<br>
* base64Code
* rootPath
* workspaceId
* mpaasConfigLicense

从上面可以看出，这个可以用来标记不同工作空间的字段不动，base64code 和mpaasConfigLicense 明显是用于角色权限安全鉴定的。
那么只有rootPath和workspaceId可以用于区分工作空间了。<br>
而进行小程序的请求的时候，也传入了这个参数的。
````aidl
2021-06-10 18:13:54.231 18844-19498/com.chips.cpscustomer D/DynamicRelease: [URGENT_DISP_2] DynamicReleaseCenterOperator.rpcRequest(runtimeInfo=RuntimeInfo{mUserId='m.shupian.cn', mProductId='ONEX6D6D335231650_ANDROID-release', mProductVersion='1.0.2', mChannelId='mpaas_default', mExtraInfo='{}'}, types={2021052411096666=0, 66666692=0}
2021-06-10 18:13:54.319 18844-19498/com.chips.cpscustomer I/DynamicRelease: [URGENT_DISP_2] appid:2021052411096666 ,appversion:0
2021-06-10 18:13:54.319 18844-19498/com.chips.cpscustomer I/DynamicRelease: [URGENT_DISP_2] appid:66666692 ,appversion:0
2021-06-10 18:13:54.340 18844-19498/com.chips.cpscustomer W/DynamicRelease: [URGENT_DISP_2] rpc request: UnionResourceRequest{platform=android, productId=ONEX6D6D335231650_ANDROID-release, productVersion=1.0.2, releaseVersion=1.0.2, utdid=XXd1ianEBfYDABNMclZtvwtw, clientId=210610174855331|210610174855331, phoneBrand=HONOR, phoneModel=HLK-AL00, vmType=Dalvik, channel=mpaas_default, apiLevel=29, osVersion=10, netType=WIFI, extraInfo={}, resourceParam=[UnionResourceParam{bizType=NEBULA, resourceIdv=[UnionResourceIdv{resId=2021052411096666, resVersion=0}, UnionResourceIdv{resId=66666692, resVersion=0}]}], uid=m.shupian.cn, cpuInstructionList=[arm64-v8a,armeabi-v7a,armeabi], manufacturer=HUAWEI}
2021-06-10 18:13:54.342 18844-19498/com.chips.cpscustomer D/DynamicRelease: [URGENT_DISP_2] mpaas_url = https://cn-hangzhou-component-gw.cloud.alipay.com/mgw.htm
2021-06-10 18:13:55.033 18844-19547/com.chips.cpscustomer I/DynamicRelease: [NetworkThread#2] DynamicReleaseLauncher rpc time: 1623320035154
2021-06-10 18:13:55.070 18844-19498/com.chips.cpscustomer W/DynamicRelease: [URGENT_DISP_2] rpc result: UnionResourceResult{success=true, message=成功, info=[UnionResourceInfo{bizType=NEBULA, success=true, rollback=false, item=[UnionResourceItem{resId=2021052411096666, resVersion=1.0.4.0, fileMD5=49b8f60abefef682f877b7601c3abb64, fileUrl=https://mcube-prod.oss-cn-hangzhou.aliyuncs.com/ONEX6D6D335231650-release/2021052411096666/1.0.4.0_all/nebula/2021052411096666_1.0.4.0.amr, downloadType=CDN, storeType=MEM, resStatus=1, resExtraData=[UnionExtraData{key=vhost, value=https://2021052411096666.shupian.cn}, UnionExtraData{key=resName, value=资料库}, UnionExtraData{key=app_type, value=1}, UnionExtraData{key=suburl, value=}, UnionExtraData{key=size, value=0}, UnionExtraData{key=resType, value=4}, UnionExtraData{key=installType, value=1}, UnionExtraData{key=autoinstall, value=1}, UnionExtraData{key=extendinfo, value={"launchParams":{"enableTabBar":"NO","enableKeepAlive":"YES","enableDSL":"YES","nboffline":"sync","enableWK":"YES","page":"pages/index/main","tinyPubRes":"YES","enableJSC":"YES"},"usePresetPopmenu":"YES"}}, UnionExtraData{key=iconUrl, value=}, UnionExtraData{key=fallbackbaseurl, value=https://mcube-prod.oss-cn-hangzhou.aliyuncs.com/ONEX6D6D335231650-release/2021052411096666/1.0.4.0_all/nebula/fallback/}, UnionExtraData{key=mainurl, value=/index.html#pages/index/main}], lazyLoad=0}], quickRollback=0}]}

````
从上面数据可以看出，mProductId就就是使用的是[rootPath]()，mChannelId似乎每个工作空间都是一样的[mpaas_default]()，这个需要后期排查下。

#### 应用更新错误
[mPaaS 小程序提示 "应用更新错误（1001）"](https://help.aliyun.com/document_detail/195307.html?spm=5176.21213303.J_6028563670.7.36c23edau0VJSr&scm=20140722.S_help%40%40%E6%96%87%E6%A1%A3%40%40195307.S_0.ID_195307-RL_%E5%BA%94%E7%94%A8%E6%9B%B4%E6%96%B0%E9%94%99%E8%AF%AF-OR_s%2Bhelpproduct-V_1-P0_0)
<br> 通常上而言，iOS上可以使用，android提示应用更新错误，一般就是工作空间配置错误。这个时候就需要打查看log了。直接搜索“DynamicRelease” ，查找mpaas 小程序的请求日志。
##### release环境 
>  在我们的config文件中，包含多个字段用于标志空间。[rootPath](),[workspaceId]()
## 结束

2021-06-11 11:16:22.479 1899-1950/? I/ActivityManager: Start proc 2314:com.chips.cpscustomer/u0a464 for start application com.chips.cpscustomer
2021-06-11 11:16:23.508 1899-5211/? V/ActivityManager: startProcess: name=com.chips.cpscustomer:push app=null knownToBeDead=true thread=null pid=-1 preloadStatus=-1
2021-06-11 11:16:23.514 1899-5211/? V/ActivityManager: startProcess: name=com.chips.cpscustomer:tools app=null knownToBeDead=true thread=null pid=-1 preloadStatus=-1
2021-06-11 11:16:23.518 1899-1950/? I/ActivityManager: Start proc 2393:com.chips.cpscustomer:push/u0a464 for service {com.chips.cpscustomer/com.alipay.mobile.common.logging.process.LogServiceInPushProcess}
2021-06-11 11:16:23.528 1899-1950/? I/ActivityManager: Start proc 2402:com.chips.cpscustomer:tools/u0a464 for service {com.chips.cpscustomer/com.alipay.mobile.common.logging.process.LogServiceInToolsProcess}
2021-06-11 11:16:24.568 1899-5211/? V/ActivityManager: Successfully start provider ContentProviderRecord{7d21f83 u0 com.android.providers.settings/.hwext.HwSettingsProvider} launchingApp=null caller pid= 2314
2021-06-11 11:16:25.252 1899-2048/? D/ActivityManager: finishBindApplication callingPid 2314
2021-06-11 11:16:25.252 1899-2048/? D/ActivityManager: finishBindApplication preloadStatus: 0 app: ProcessRecord{8231929 2314:com.chips.cpscustomer/u0a464} uidRec.isPreload: false
2021-06-11 11:16:25.401 1899-2048/? V/ActivityManager: startProcess: name=com.chips.cpscustomer:pushcore app=null knownToBeDead=true thread=null pid=-1 preloadStatus=-1
2021-06-11 11:16:25.406 1899-2048/? I/ActivityManager: Need to start process com.chips.cpscustomer:pushcore for provider ContentProviderInfo{name=com.chips.cpscustomer.DataProvider className=cn.jpush.android.service.DataProvider}, callerApp: ProcessRecord{8231929 2314:com.chips.cpscustomer/u0a464}
2021-06-11 11:16:25.406 1899-2048/? V/ActivityManager: startProcess: name=com.chips.cpscustomer:pushcore app=ProcessRecord{7f38851 0:com.chips.cpscustomer:pushcore/u0a464} knownToBeDead=false thread=null pid=0 preloadStatus=0
2021-06-11 11:16:25.406 1899-2048/? V/ActivityManager_MU: Waiting to start provider ContentProviderRecord{7c1e55f u0 com.chips.cpscustomer/cn.jpush.android.service.DataProvider} launchingApp=ProcessRecord{7f38851 0:com.chips.cpscustomer:pushcore/u0a464} caller pid= 2314 for 20000 ms
2021-06-11 11:16:25.413 1899-1950/? I/ActivityManager: Start proc 2833:com.chips.cpscustomer:pushcore/u0a464 for service {com.chips.cpscustomer/com.chips.push.cps.XService}
2021-06-11 11:16:25.455 1899-2054/? D/HwActivityManagerServiceEx: notifyAppToTop pid:2314, enable:1
2021-06-11 11:16:25.464 2465-2989/? D/DollieActivityManagerAdapter: [Resumed] bg:true pid:2314 uid:10464 ComponentInfo{com.chips.cpscustomer/com.chips.module_main.ui.start.StartPageActivity}

2021-06-11 11:36:35.432 1899-1950/? I/ActivityManager: Start proc 6839:com.chips.cpscustomer/u0a464 for start application com.chips.cpscustomer
2021-06-11 11:36:36.473 1899-2406/? V/ActivityManager: startProcess: name=com.chips.cpscustomer:push app=null knownToBeDead=true thread=null pid=-1 preloadStatus=-1
2021-06-11 11:36:36.476 1899-2406/? V/ActivityManager: startProcess: name=com.chips.cpscustomer:tools app=null knownToBeDead=true thread=null pid=-1 preloadStatus=-1
2021-06-11 11:36:36.483 1899-1950/? I/ActivityManager: Start proc 6899:com.chips.cpscustomer:push/u0a464 for service {com.chips.cpscustomer/com.alipay.mobile.common.logging.process.LogServiceInPushProcess}
2021-06-11 11:36:36.493 1899-1950/? I/ActivityManager: Start proc 6905:com.chips.cpscustomer:tools/u0a464 for service {com.chips.cpscustomer/com.alipay.mobile.common.logging.process.LogServiceInToolsProcess}
2021-06-11 11:36:37.531 1899-2406/? V/ActivityManager: Successfully start provider ContentProviderRecord{7d21f83 u0 com.android.providers.settings/.hwext.HwSettingsProvider} launchingApp=null caller pid= 6839
2021-06-11 11:36:38.150 1899-2048/? D/ActivityManager: finishBindApplication callingPid 6839
2021-06-11 11:36:38.151 1899-2048/? D/ActivityManager: finishBindApplication preloadStatus: 0 app: ProcessRecord{7b69171 6839:com.chips.cpscustomer/u0a464} uidRec.isPreload: false
2021-06-11 11:36:38.222 6839-7090/com.chips.cpscustomer W/ips.cpscustome: Accessing hidden method Landroid/app/ActivityManagerNative;->getDefault()Landroid/app/IActivityManager; (greylist, reflection, allowed)
2021-06-11 11:36:38.317 1899-2054/? D/HwActivityManagerServiceEx: notifyAppToTop pid:6839, enable:1
2021-06-11 11:36:38.324 2465-2989/? D/DollieActivityManagerAdapter: [Resumed] bg:true pid:6839 uid:10464 ComponentInfo{com.chips.cpscustomer/com.chips.module_main.ui.start.StartPageActivity}

35.432-38.324 
通过上面移动推送初始化位置后对启动log进行对比。
减少了pushcore的初始化进程逻辑。但是启动时长还是在3秒左右。所以需要进行log打印用于获取方法耗时。


 attachBaseContext  :1
 onCreate  :112
 initDomain  :1
 attachBaseContext  :0
 onCreate  :1
 initDomain  :0
 attachBaseContext  :1
 onCreate  :0
 initDomain  :1
 super.onCreate()  :1096
 getProcessName  :1
 equals  :0
 FilePreview.init(this);  :1
 onPostInit  :328
 CpsMPaasInitHelper  :12
 QuinoxlessFramework.init();  :0
 initARouter();  :1
 initLogin();  :149
 MapInit.getInstance().initMap(this);  :9
 setWebDataSuffixPath(this);  :0
 ModuleLifecycleConfig.getInstance().initModuleAhead(this);  :4
 initOssConfig();  :14
 initLive  :0
 initIM  :18
 initScan  :1
 initBugly  :31
 initAnalysisSDK  :33
 initShareConfig  :0
 setErrorHandler  :2
 super.onCreate()  :1478
 getProcessName  :1
 super.onCreate()  :1420
 getProcessName  :0
 attachBaseContext  :0
 onCreate  :3
 initDomain  :0
 super.onCreate()  :1072
 getProcessName  :0

 通过上面可以得出：super.onCreate()执行了多次，而耗时操作基本全是 super.onCreate()在执行，从逻辑上将，super.onCreate()当前进程只是需要初始化一次。
 所以这次的优化是将super.onCreate() 的初始化固定到主进程中。

 attachBaseContext  :0
 onCreate  :117
 getProcessName  :0
 initDomain  :0
 attachBaseContext  :1
 onCreate  :0
 getProcessName  :1
 super.onCreate()  :1034
 FilePreview.init(this);  :1
 attachBaseContext  :1
 onCreate  :0
 getProcessName  :1
 onPostInit  :323
 CpsMPaasInitHelper  :22
  QuinoxlessFramework.init();  :0
  initARouter();  :1
  initLogin();  :155
  MapInit.getInstance().initMap(this);  :9
 setWebDataSuffixPath(this);  :1
  ModuleLifecycleConfig.getInstance().initModuleAhead(this);  :3
 initOssConfig();  :17
 initLive  :0
 initIM  :20
 initScan  :1
 initBugly  :32
 initAnalysisSDK  :24
 initShareConfig  :0
 setErrorHandler  :2
 attachBaseContext  :1
 onCreate  :2
 getProcessName  :0





 attachBaseContext  :0
 onCreate  :102
 getProcessName  :0
 initDomain  :0
 attachBaseContext  :1
 onCreate  :0
 getProcessName  :1
 attachBaseContext  :1
 onCreate  :0
 getProcessName  :0
 super.onCreate()  :1224
 FilePreview.init(this);  :1
 initARouter();  :0
 initLogin();  :85
 MapInit.getInstance().initMap(this);  :8
 setWebDataSuffixPath(this);  :1
 ModuleLifecycleConfig.getInstance().initModuleAhead(this);  :3
 initOssConfig();  :12
 initLive  :1
 initIM  :17
 initScan  :1
 initBugly  :26
 initAnalysisSDK  :26
 initShareConfig  :0
 setErrorHandler  :2
// mainActivity 初始化后
 onPostInit  :1745
 CpsMPaasInitHelper  :13
 attachBaseContext  :0
 onCreate  :3
 getProcessName  :0

