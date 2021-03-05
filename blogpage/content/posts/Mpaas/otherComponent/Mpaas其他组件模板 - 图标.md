+++
date = "2021-3-3"
title = "Mpaas其他组件-图标"
slug = "dgg"
series = ["Mpaas"]
featured = true

+++
##导入包
> mpaas 提供Android studio 插件，在插件中选择使用。
````aidl
// ui 库 
 api 'com.mpaas.android:antui'
````
## AUIconView

> 资源化的图标和阿里提供的图标。两种使用图标方式。
>
> AUIconView 为 iconfont 矢量图控件，可以同时实现 TextView 及 ImageView 的功能。
>
> iconfont 图片控件（可当做 TextView 来使用）实际是通过 TextView 的 TTF 字体文件，定义特殊的 Unicode 码对应一类图片字体。也就是说，iconfont 相当于是加载了一个字体，一个字体对应了多张图片，每个图片有一个 Unicode 码。
>
> 每个 iconfont 集合实际就是一个 TTF 字体文件，因此可以加载多个 TTF 字体文件。每个 TTF 字体文件有一个名称，默认 AntUI 的 TTF 字体文件名称为 auiconfont。
>
> 

## 图标资源

| 资源 ID                                                 | 对应的示例名称 |
| :------------------------------------------------------ | :------------- |
| com.alipay.mobile.antui.R.string.iconfont_more          | 更多           |
| com.alipay.mobile.antui.R.string.iconfont_cancel        | 取消           |
| com.alipay.mobile.antui.R.string.iconfont_voice         | 语音           |
| com.alipay.mobile.antui.R.string.iconfont_collect_money | 收款           |
| com.alipay.mobile.antui.R.string.iconfont_back          | 返回           |
| com.alipay.mobile.antui.R.string.iconfont_user_setting  | 用户设置       |
| com.alipay.mobile.antui.R.string.iconfont_user          | 用户           |
| com.alipay.mobile.antui.R.string.iconfont_add           | 添加           |
| com.alipay.mobile.antui.R.string.iconfont_praise        | 点赞           |
| com.alipay.mobile.antui.R.string.iconfont_map           | 地图           |
| com.alipay.mobile.antui.R.string.iconfont_checked       | 勾选           |
| com.alipay.mobile.antui.R.string.iconfont_notice        | 公告           |
| com.alipay.mobile.antui.R.string.iconfont_add_user      | 添加用户       |
| com.alipay.mobile.antui.R.string.iconfont_comment       | 评论           |
| com.alipay.mobile.antui.R.string.iconfont_selected      | 选择           |
| com.alipay.mobile.antui.R.string.iconfont_bill          | 账单           |
| com.alipay.mobile.antui.R.string.iconfont_pulldown      | 下拉           |
| com.alipay.mobile.antui.R.string.iconfont_scan          | 扫描           |
| com.alipay.mobile.antui.R.string.iconfont_list          | 列表           |
| com.alipay.mobile.antui.R.string.iconfont_delete        | 删除           |
| com.alipay.mobile.antui.R.string.iconfont_share         | 分享           |
| com.alipay.mobile.antui.R.string.iconfont_search        | 搜索           |
| com.alipay.mobile.antui.R.string.iconfont_complain      | 投诉           |
| com.alipay.mobile.antui.R.string.iconfont_qrcode        | 二维码         |
| com.alipay.mobile.antui.R.string.iconfont_unchecked     | 取消勾选       |
| com.alipay.mobile.antui.R.string.iconfont_right_arrow   | 右箭头         |
| com.alipay.mobile.antui.R.string.iconfont_help          | 帮助           |
| com.alipay.mobile.antui.R.string.iconfont_group_chat    | 群聊           |
| com.alipay.mobile.antui.R.string.iconfont_contacts      | 联系人         |
| com.alipay.mobile.antui.R.string.iconfont_setting       | 设置           |
| com.alipay.mobile.antui.R.string.iconfont_phone_book    | 通讯录         |
| com.alipay.mobile.antui.R.string.iconfont_phone_contact | 手机联系人     |

#### 传入图片

* setImageResource
*  setImageDrawable
*  setIconfontUnicode 可以传入文本图片等等。
* setIconByName 

> 还有几个，需要和阿里系列中的icon 一起使用。