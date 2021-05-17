+++
date = "2021-4-21"
title = "顶呱呱视频项目详细设计"
description = "顶呱呱视频项目详细设计"
tags = [ "视频"]
categories = [
    "详细设计"
]
featured = true

+++

## 前言

> 530 项目？基于，当前项目基于必懂项目新增视频相关逻辑。所有视频播放都不涉及后台播放，但是需要处理界面重启（app 进入后台）

## 参考文献地址:

* [功能设计-概要设计-原型设计？](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=h2ujkt&p=%E4%BA%A7%E5%93%81%E7%BB%93%E6%9E%84%E5%9B%BE)
* [视频项目名词定义](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=ct1xgv&p=%E5%90%8D%E8%AF%8D%E5%AE%9A%E4%B9%89_1)
* [蓝湖地址](https://lanhuapp.com/web/#/item/project/stage?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=db7efc8e-770f-40d1-b03c-03e96bc57687)
* [缺省图]()
* [接口地址]()
* [阿里云视频点播主页](https://www.aliyun.com/product/vod?spm=a2c4g.11174283.J_8058803260.344.78be68a1I4Elt1)
* [视频点播-文档](https://help.aliyun.com/document_detail/125579.html?spm=5176.8413026.J_5905852530.4.351c1029b8o6AI)
* 文档地址：<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425093018.png" alt="image-20210425093018617" style="zoom:25%;" />
* demo 下载地址：![image-20210425100734671](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425100734.png)

## 结构图

![视频产品结构图](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210421095012.png)

## 模块拆解和功能区分

> 当前设计只是兼顾C端。

### 个人主页

### 视频聚合界面

#### 关注

#### 推荐

#### 大讲堂

#### 视频

#### 薯条计划

## 1 修改

> 当前修改基于必懂之前的设计。热榜无更改。

### 1.1 首页tab

> 支持整个页面左右滑动切换频道

##### 1.1.1 涉及改动接口

* 新增查询 视频相关tab的逻辑。

##### 1.1.2 交互

![image-20210421135742208](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210421135742.png)

参考上图。

* 视频所在tab 如果处于选中状态下，可以点击下拉，弹出分类筛选弹窗，默认选中 默认排序，点击热度排序更改排序规则。
* 如果视频没有被选中，需要清除下拉图标。参考：![image-20210421140230387](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210421140230.png)
* 再次点击视频按钮，会拖放展示选择条件；点击其他地方或视频按钮，则上拉隐藏筛选。
* 操作弹窗中，新增，视频讲堂分类。操作逻辑区别于其他分类。

### 1.2 关注列表

* 关注用户列 表新增关注的视频发布作者
* 关注用户动态列表 新增关注的视频发布作者的视频动态。
  ![视频动态item](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210421104442.png)

#### 1.2.1 item 分解

* 1 视频点赞数量。
* 2 视频评论数量
* 3 视频时长
* 4 视频封面图
* 5 必懂时间格式化逻辑+视频（比如：27分钟前·发布了视频）[时间格式化参考(公共说明):](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=xqw7m1&p=%E5%85%AC%E5%85%B1%E8%AF%B4%E6%98%8E)
* 6 视频标题。

#### 1.2.2 交互新增

* 视频点赞与取消点赞
* 视频评论列表与新增评论。评论列表中评论点赞与取消点赞。
* 判断当前类型是否是视频，如果是视频跳转到原生详情播放界面。（其他类型保持继续跳转wap）

#### 1.2.3 涉及改动接口

* [关注用户列表]()
* [关注用户动态列表]()
* [内容点赞与取消点赞]()
* [评论点赞与取消点赞]()
* [发表评论]()

### 1.3 推荐和通用分类

![推荐和通用分类视频item样式](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210421105213.png)

#### 1.3.1 item 分解

* 1 视频点赞数量

* 2 视频评论数量

* 3 视频详情内容

* 4 视频时长

* 5 视频封面图

* 6 （必懂没有做，这个也应该不做）

* 7 视频的标题

  ##### 1.3.2 交互新增

  * 判断当前类型是否是视频，如果是视频跳转到原生详情播放界面。（其他类型保持继续跳转wap）

  ##### 1.3.3 涉及改动接口

  * [推荐列表接口]()
  * [通过分类拉取列表数据接口]()

### 1.4 通用分类

> 需要查询当前分类下的视频，文章，回答，问题等等。并且增加视频样式的item

##### 更改接口

* 通过分类ID 获取分类列表。

## 2 新增

> 当前新增用于区别必懂已有功能。视频相关分类和聚合界面在当前分类栏目下标记。

### 2.1 视频列表

> 单纯的视频列表的聚合界面。需要实现视频列表播放功能。
>
> [原型](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=uz85nu&p=%E4%B8%BB%E9%A1%B5-%E8%A7%86%E9%A2%91)
>
> [蓝湖：默认排序](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=db7efc8e-770f-40d1-b03c-03e96bc57687&fromEditor=true)
>
> [蓝湖：视频包含排序弹窗](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=26bef3bf-11d3-497e-a5f5-26b52768b42b&fromEditor=true)

![image-20210421140640858](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210421140640.png)

#### 2.1.1 item 分解

* 1 发布者头像
* 2 发布者的昵称或者名称
* 3点赞数量
* 4评论数量
* 5视频时长
* 6 播放次数（观看次数）
* 7 视频名称
* 8 封面图片

#### 2.1.2 交互

* 点击3 可以实现 点赞和取消点赞。
* 点击4 可以实现评论和评论点赞。（产品说，这个地方不做评论功能，用于扩大进入详情的点击区域）
* 点击1  跳转作者主页
* 点击整个item 跳转到详情界面
* 点击视频区域进行视频相关操作
* 点击非以上区域，跳转到详情。

#### 2.1.3 item 参数

| 名称                            | 字段名       | 类型   |
| ------------------------------- | ------------ | ------ |
| 作者昵称                        | nickname     | string |
| 作者头像                        | avatar       | string |
| 数量                            | applaudCount | int    |
| 评论数量                        | remarkCount  | int    |
| 视频时长                        | video_time   | long   |
| 播放次数                        | number       | long   |
| 视频名称                        | video_name   | string |
| 封面图片                        | video_image  | string |
| 记录id                          | id           | string |
| 视频的分类                      | video_type   | string |
| 视频的url或者其他获取方式比如id | video_url    | string |
| 作者用户类型                    | usertype     | string |
| 作者的id                        | user_id      | string |

#### 2.1.4 接口返回json

* 逻辑正常

> {
>  "code": 200,
>  "message": "请求成功。客户端向服务器请求数据，服务器返回相关数据",
>  "data": {
>      "rows": [],
>      "pageSize": 10,
>      "total": 1,
>      "totalPage": 1,
>      "currentPage": 1
>  }
> }

> rows 用于包裹 分页数据

#### 2.1.5 列表播放交互

> 参考ui 样式。
>
> 这里仅展示视频列表下一个形式，此时可以在列表直接播放，支持横屏播放。
>
> 视频标题，显示30个字，超出...
>
> 播放成功后，标题栏隐藏和操作栏隐藏时间同步。5s
>
> 时长格式化：格式为MM：SS或HH：MM：SS。
>
> 不支持多分辨率播放。

[视频播放交互 原型设计](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=y2e6qj&p=%E8%A7%86%E9%A2%91%E5%88%97%E8%A1%A8)

[列表播放样式蓝湖地址](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=db7efc8e-770f-40d1-b03c-03e96bc57687&fromEditor=true)

* 当前列表中如果全部处于未播放。

  * 点击播放按钮，判断网络，如果非WiFi 提示：如果用户坚持播放则播放，否则不播放。

* 点击播放按钮，播放成功。并且显示控制层：![image-20210422153931998](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422153932.png)

    

  * 点击播放按钮，视频被下架删除显示：![image-20210422154211970](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422154212.png)

  * 点击播放按钮，视频加载失败显示：![image-20210422154249693](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422154249.png)

  * 点击立即刷新，走未播放流程。

  * 播放成功，操作控制层采用播放器自带的。

* 当前列表存在播放。

  * 执行其他item的播放逻辑，关闭上一个播放，进入 未播放流程判断。 
  * 列表滚动，当item 滚动出屏幕，暂停播放。再次点击非暂停item,进入未播放判断逻辑。

  

### 2.2 视频详情

* [视频详情-原型](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=17qphc&p=%E8%A7%86%E9%A2%91%E8%AF%A6%E6%83%85-%E9%BB%98%E8%AE%A4%E9%A1%B5)

* [视频详情蓝湖地址](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=9d2cbbda-243b-43a0-a3b5-52b87c05805a&fromEditor=true)

#### 2.2.1 界面参数分解

![image-20210422150301165](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422150301.png)



> 参考上图。

1. 封面图，视频的获取方式（url或者id）
2. 作者头像
3. 作者的昵称
4. 作者的标签或者个人简介
5. 是否关注
6. 视频的名称
7. 视频播放次数
8. 发布更新时间
9. 评论总数
10. 评论用户的头像
11. 评论用户的名称
12. 评论时间。
13. 是否点赞和点赞数量
14. 是否对视频点赞
15. 是否对视频收藏.

#### 2.2.2 整体交互

* 点击返回键，关闭已有播放器，关闭当前界面。
* 点击视频区域进行播放。
* 点击作者头像 跳转到作者主页。
* 点击关注，取消关注或者关注。
* 点击评论列表中的头像，跳转对应主页。
* 点击评论列表中的点赞，进行点赞与取消点赞。
* 点击底部的点赞按钮，对于视频进行点赞或取消点赞。
* 点击底部的收藏按钮，对于视频进行收藏或者取消收藏。
* 点击底部的输入框或者点击评论区域，弹起软键盘，进入输入样式：![image-20210422151247375](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422151247.png)  [蓝湖地址](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=f40a996f-e625-49c2-b206-f3d690282d8f&fromEditor=true) 
* 网络请求，视频被删除显示样式和视频信息拉去失败显示样式：![image-20210422152546508](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422152546.png) [蓝湖地址](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=36d960c6-c035-45ac-81f4-6f143b4ab093&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&fromEditor=true)

#### 2.2.3 播放交互

> [蓝湖交互效果图](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=b41633af-777c-43c3-bdcd-750345e7ba79&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&fromEditor=true)
>
> [原型设计播放交互](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=i34cka&p=%E8%A7%86%E9%A2%91%E8%AF%A6%E6%83%85)

* 点击返回键，关闭播放器。
* 点击播放按钮，判断是否是第一详情界面播放，如果是第一次详情界面播放，则显示引导层。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422165333.png" alt="image-20210422165332983" style="zoom:33%;" />
* 点击引导层的知道了，关闭引导层，进行正常播放操作逻辑。
* 正常播放，判断是否有网络。如果没有网络，显示：<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422172350.png" alt="image-20210422172350008" style="zoom:25%;" />，点击刷新，进行正常播放逻辑。
* 正常播放，判断是否是wifi ,如果不是WiFi，提示：<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422172129.png" alt="image-20210422172129899" style="zoom:33%;" />。点击立即播放，进入WiFi播放逻辑。
* WiFi播放逻辑，播放错误，需要判断错误类型。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422172755.png" alt="image-20210422172755487" style="zoom:25%;" /> <img src="C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210422172843726.png" alt="image-20210422172843726" style="zoom:25%;" />  <img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422172819.png" alt="image-20210422172819287" style="zoom:25%;" />

* WiFi 播放逻辑，播放成功，显示播放器操作界面。如果服务器返回多个分辨率的url，需要配置播放器的清晰度。



## 2.3 讲堂列表

* [蓝湖地址](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=5880d2eb-2aab-4c40-b35a-040e47a115a0&fromEditor=true)
* [原型地址](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=wr2zy9&p=%E4%B8%BB%E9%A1%B5-%E5%A4%A7%E8%AE%B2%E5%A0%82)

###  2.3.1 整体交互

* 点击banner ，banner通过cms 配置和配置跳转。
* 点击tab 切换显示的内容，通过手势滑动，切换外层的tab。内层tab 不支持手动滑动。
* tab 向上滑动吸顶。吸顶后更改样式。吸顶后逻辑意义上，banner将不再刷新切换，而取消吸顶后，banner会恢复自动切换。
  * 未吸顶：![image-20210422111457719](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422111457.png)
  * 吸顶：![image-20210422111402392](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422111402.png) 备注;横向滚动列表。
* banner 默认自动滚动，滚动时长和交互与之前保持一致。
* 加载：![image-20210422113016540](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422113016.png) 当前1区域属于固定头部，当且仅当 第一次进入讲堂加载。或者销毁后重新加载。默认加载第一个子tab数据。
* 下拉刷新，结合加载逻辑，刷新部分为子tab的列表。
* 上拉加载更多，只是加载子tab的列表数据。
* 点击item跳转详情。不做列表播放。

#### 2.3.2 外层接口

* banner cms配置
* 内部tab接口

##### 内部tab

![image-20210422114650166](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422114650.png)

*  tab 名称
*  tab id

| 名称       | 字段名  | 类型   |
| ---------- | ------- | ------ |
| tab 的名称 | tabName | string |
| tab 的id   | tabID   | string |

#####  json样式

````
{
    "code": 200,
    "message": "请求成功。客户端向服务器请求数据，服务器返回相关数据",
    "data": [
        {
            "tabID": "1",
            "tabName": "tab的名称"
        }
    ]
}
````





#### 2.3.3 item解析

![image-20210422112311744](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210422112311.png)

* 1 讲堂视频封面图片
* 2 讲堂标题
* 3 讲堂作者或者讲师
* 4 标签
* 5 学习人数（人数格式化参考[人数格式化参考文档]()）
* 6 学习进度（需要判断是否登录，如果没有登录则不显示）
* 7 本期不做，6功能点自动缩进到7位置。

##### item 交互

* 点击跳转到详情。
* 7 区域（本期不做）

##### item  参数

| 名称                                 | 字段名      | 字段类型 |
| ------------------------------------ | ----------- | -------- |
| 讲堂名称                             | name        | string   |
| 讲堂id                               | id          | string   |
| 讲堂封面图                           | image       | string   |
| 讲堂作者讲师名称                     | author_name | string   |
| 讲堂标签（保留）                     | tag         | string   |
| 学习人数                             | study_num   | int      |
| 学习进度（未登陆不显示，但是得返回） | study_pro   | int      |
| 是否参与学习                         | isStudy     | 布尔值   |
| 当前分类type                         | type        | int      |
| 讲堂作者用户类型                     | user_type   | int      |
| 讲堂作者id                           | author_id   | string   |

##### 接口返回json

* 逻辑正常

> {
> "code": 200,
> "message": "请求成功。客户端向服务器请求数据，服务器返回相关数据",
> "data": {
>   "rows": [],
>   "pageSize": 10,
>   "total": 1,
>   "totalPage": 1,
>   "currentPage": 1
> }
> }

> rows 用于包裹 分页数据

## 2.4 讲堂详情

* [讲堂原型](https://axhub.im/ax9/68524a59d05b0e91/#g=1&id=qc9vhn&p=%E5%A4%A7%E8%AE%B2%E5%A0%82%E8%AF%A6%E6%83%85-%E9%BB%98%E8%AE%A4%E9%A1%B5)
* [蓝湖讲堂详情第一次进入默认展示](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=48690030-134f-4ecb-8dd0-3eaa8ae85bac&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&fromEditor=true)
* [播放视频相关信息](https://help.aliyun.com/document_detail/56124.htm?spm=a2c4g.11186623.2.26.5d2f117er5vTXE#doc-api-vod-GetPlayInfo)

<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423134824.png" alt="image-20210423134824138" style="zoom:50%;" />

#### 2.4.1 服务器参数对界面的影响



* 不做自动播放逻辑。如果没有播放记录，头部显示：![image-20210423141328857](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423141328.png)

* 如果有播放记录：![image-20210423141353352](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423141353.png) 

* 目录列表标记显示为：![image-20210423141424965](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423141424.png)

* 播放过程中，播放中的目录 展示为：![image-20210423141450045](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423141450.png)

* 有播放记录且不是上一次播放的目录样式：<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423141644.png" alt="image-20210423141644863" style="zoom: 33%;" />

* 没有播放记录的目录样式：![image-20210423141727109](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423141727.png)

  

#### 2.4.2 区域解析



1. 点击返回键退出详情，返回上一个界面。
2. 默认展示讲堂的封面图。
3. 默认展示选中简介
4. 目录（用于点击切换隐藏显示红框区域）
5. 讲堂名称
6. 讲堂作者讲师
7. 讲堂作者讲师的标签或者个人简介(讲师称号)
8. 播放次数
9. 发布更新时间
10. 讲堂简介
11. 目录总数
12. 目录当前播放或者上一个播放的选中样式
13. 播放过，有播放进度的样式
14. 默认样式，未学习过
15. 唤起评论弹窗
16. 点赞和取消点赞
17. 收藏与取消收藏
18. 唤起评论弹窗。

#### 2.4.3 整体交互

* 点击1，关闭播放器，返回上一个界面。如果是横屏播放状态，就要切换到竖屏播放状态。
* 点击3，选中简介显示红框区域。
* 点击4，选中目录，隐藏红框标记区域。直接隐藏，不要隐藏缩进效果。
* 点击5，可能需要跳转作者主页（待定）
* 点10，弹窗出现简介全部内容弹窗（是否固定高度。内容滑动）
* 点12,13,14，执行播放交互。同时样式切换到12，其他item 样式切换到13或者14，根据是否播放切换样式、
* 点击15，18，进入评论弹窗，评论弹窗和必懂类似，支持评论和评论点赞。

#### 2.4.4 界面设计

<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423140123.png" alt="image-20210423140123156" style="zoom:33%;" />

* 1区域，竖屏状态下，固定到头部。
* 2区域，竖屏状态下，固定到底部。
* 剩余区域滚动。

#### 2.4.5 界面参数分解

| 名称               | 字段名 | 类型 |
| ------------------ | ------ | ---- |
| 讲堂名称                             | name        | string   |
| 讲堂id                               | id          | string   |
| 讲堂封面图                           | image       | string   |
| 讲堂作者讲师名称                     | author_name | string   |
| 讲堂标签（保留）                     | tag         | string   |
| 学习人数                             | study_num   | int      |
| 学习进度（未登陆不显示，但是得返回） | study_pro   | int      |
| 是否参与学习                         | isStudy     | 布尔值   |
| 当前分类type                         | type        | int      |
| 讲堂作者用户类型                     | user_type   | int      |
| 讲堂作者id                           | author_id   | string   |
| 讲堂更新时间   | updataTime | string |
| 是否点赞           |        |      |
| 是否收藏           |        |      |
| 上一次播放的目录id |        |      |
| 点赞数量 |        |      |
| 收藏数量 |        |      |



#### 2.4.6 item 分解
| 名称               | 字段名 | 类型 |
| ------------------ | ------ | ---- |
| 讲堂目录-目录名称  |        |      |
| 讲堂目录-目录id  |        |      |
| 讲堂目录-时长      |        |      |
| 讲堂目录-学习进度  |        |      |
| 讲堂目录-是否学习  |        |      |
| 视频的多分辨率url  |        |      |
| 音频的url  | string |      |

#### 2.4.7 item 交互

> 优先参考服务器参数对界面的影响。

*  点击当前item，标记为播放中状态。如果播放失败，也是播放状态。![image-20210423155427375](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423155427.png)

### 2.5 讲堂详情播放交互

> 当前需要考虑视频播放和音频播放。
>
> 目录上播放中定义：只要是执行播放交互就设定为播放中。

进入入口：

* 如果没有播放记录。需要点击目录item 进行播放交互。
* 如果存在播放记录，可以通过头部区域进行播放和点击目录item进行播放交互。点击头部播放，需要将对于的item 样式修改为播放中。

##### 2.5.1 WiFi 状态

* 用户点击继续播放和item播放视频，直接进入视频播放交互。
* 用户从竖屏状态下的音频样式点击切换到视频，进入视频播放交互。![image-20210423160351825](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423160351.png)

##### 2.5.2 非WiFi状态

> 进入详情的第一次都需要提示哦。

* 进入播放交互的时候，需要展示：![image-20210423160424903](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210423160424.png)。用户点击音频播放，执行音频交互。用户点击视频，执行视频交互

##### 2.5.3 竖屏状态

* 视频播放模式可以切换到音频。
* 音频播放模式可以切换到视频。
* 参考播放器自带效果。

##### 2.5.4 横屏状态。

* 只有视频可以横屏播放。
* 横屏播放不能做音频视频交互。
* 横屏状态下，不可以进行评论。
* 参考播放器自带效果。

#### 2.6 视频交互

> 视频播放，只是涉及到界面播放。

* 播放失败需要展示：<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425145021.png" alt="image-20210425145021433" style="zoom: 25%;" />

##### 2.6.1 竖屏

> [讲堂详情视频播放竖屏交互](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=54cffaca-0b9b-4a03-b33a-ea56dc6c2bf9&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&fromEditor=true)

*  首次开始播放，功能框架和视频全部显示,视频自动开始播放同时判断网络状态，若为非wifi时 播放时直接提醒。显示流量提醒：正在使用流量播放，本次播放将消耗10.1M流量。若是WiFi 就不提示。![image-20210425164332741](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425164332.png)（当前图片和蓝湖上不相同，已经确认提醒采用横屏状态下的提醒）（关于消失，toast是几秒消失，这个就几秒消失，15s）

* 点击切换到音频按钮，视频关闭，音频开始播放。（进度是否统一（瓜仔没有统一进度））
*  播放中，操作使用播放器自带。
*  播放完成，如果没有下一个，显示重播。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425165150.png" alt="image-20210425165150366" style="zoom:25%;" />
*  播放完成，如果存在下一个，显示重播，和下一个按钮。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425165133.png" alt="image-20210425165132977" style="zoom:25%;" />
* 点击返回键，关闭播放器，关闭界面。

   

##### 2.6.2 横屏

> [讲堂详细视频播放横屏交互](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=2e460adc-e465-41b1-a16b-dfbe05d03fff&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&fromEditor=true)
>
> 横屏状态下，没有切换到音频的逻辑。

*  首次开始播放，功能框架和视频全部显示,视频自动开始播放同时判断网络状态，若为非wifi时 （5秒后）显示流量提醒：正在使用流量播放，本次播放将消耗10.1M流量。若是WiFi 就不提示。![image-20210425164332741](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425164332.png)（如果竖屏提醒了，横屏也要提醒）（关于消失，toast是几秒消失，这个就几秒消失，15s）

* 点击返回键，切换到竖屏的播放状态。

*  倍速，清晰度，声音，亮度，采用播放器自带内容。

*  如果播放完成，没有下一个，显示重播。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425172554.png" style="zoom:25%;" />

*  如果播放完成，包含下一个，显示重播和下一个。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425172611.png" alt="image-20210425172611403" style="zoom:25%;" />

  

#### 2.7 音频交互

>  音频播放，后台播放（在详情界面，app 进入后台）。可能涉及到APP 生命周期内播放。听说老大要砍生命周期播放（不知道砍成功了没有）
>
>  只做竖屏，竖屏，竖屏。所以全屏按钮不要。
>
>  [音频只做竖屏的蓝湖地址](https://lanhuapp.com/web/#/item/project/detailDetach?pid=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&image_id=54cffaca-0b9b-4a03-b33a-ea56dc6c2bf9&project_id=f6ebd8c8-0bb0-4b06-9a71-2bd19cf68ac6&fromEditor=true)

* 播放失败 需要展示：<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425145104.png" alt="image-20210425145104134" style="zoom:25%;" />
* 播放正常。5秒后底部进度条隐藏,顶部保留,进入沉浸式收听音频模式,同时判断网络是否为 流量，若为流量则提示该内容：：<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425145614.png" alt="image-20210425145614651" style="zoom:25%;" />

* 若是WiFi状态下，上面文本隐藏。
* 加载中样式设计为：<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425150454.png" alt="image-20210425150454140" style="zoom:25%;" />。若点击，则操作条出现，不要全屏按钮。
* 播放完成，如果包含下一个音频。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425150550.png" alt="image-20210425150550567" style="zoom: 25%;" />
* 播放完成，没有下一个音频.<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210425150614.png" alt="image-20210425150613960" style="zoom:25%;" />
* 点击视频按钮，切换至视频播放。







#### 疑惑

*  必懂通用分类是否包含视频。
* 视频列表播放是否支持全屏播放。播放器需要支持列表全屏播放吗？
* 视频详情，刷新区域，播放过程中是否可以刷新和上拉更多。
* 讲堂列表。刷新区域和tab刷新机制。
* 讲堂详情，播放过程中是否支持刷新。
* 视频详情中，时间是更新时间还是创建时间。
* 讲堂详情中，时间是更新时间还是创建时间。
* 讲堂详情中，点击讲师名称是否跳转到讲师的个人主页。![image-20210426135646010](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210426135646.png)

