## 资料
* [android 应用资源概述](https://developer.android.com/guide/topics/resources/providing-resources)
* [Android 屏幕适配——smallestWidth适配](https://www.jianshu.com/p/c1dd77050e68)
* [AndroidAutoSize ](https://github.com/JessYanCoding/AndroidAutoSize)
* [Activity config  ](https://developer.android.com/guide/topics/manifest/activity-element#config)
# 正文
之前版本的启动图内容很简单，主题色背景，中间一个小logo。为了防止logo 变形，我们采用layer-list。logo 采用svg,然后再item 中固定宽高。
然后用item 一个绘制背景，一个绘制logo。这种效果还行，起码没有收反馈说，这个有啥问题。layer-list 作为window的背景被设置到了主题上 <br>
近期，我们APP 启动图发生了变更，左边一个巨大的中文logo.底部一个小logo,但是巨大的log没有提供svg，而且png 套图。那么就尴尬了。
因为我们的业务处理上，启动页是谁一个单独的界面，通过逻辑控制去初始化和跳转到首页，更尴尬的是我们是在onCreate 中处理的逻辑。所以在启动页通过设置view 去达到屏幕
适配方案是不行的，往往在已经跳转到目标界面的时候，view都没有显示出来。<br>
我们现在便遇到了问题:

* 大logo是png,如果是一张整图的话，去设置window的背景的话，在不同的手机上有不同的拉伸效果。
* 将大logo添加到Layer-list 中的时候，通过item去控制大小和相对位置，会出现大logo 在不同手机上显示的大小不一样，这和UI想象的宽度百分比适配完全是两码事。
* 将这个流程画到view中，因为业务的关系，还没有显示出来，就已经跳走了。

我们一步步的拆解功能。
## 一张整图设置为window 背景
在启动有优化的过程中，我们为了看起来让APP点击了图标后有响应，会设置一个自己的启动图做为背景，往往这个启动图相当简单。通常就是一个小logo 居中或者横向对其。
UI设计师在设计出图的时候，往往会将背景和logo 分开切。当然了也有出一张整的PNG的。设计师往往只会提供几套图片，如drawable-,通常就是hdpi,mdpi,xhdpi，xxhdpi
,xxxhdpi等，这套图一丢进去，就会发现一个问题：<br>
我这个手机上是好好的，为啥另外一个手机上图片拉伸了？<br>
那我们真的就没法解决一张整图png的情况吗？答案是否定的，我们[android 应用资源概述](https://developer.android.com/guide/topics/resources/providing-resources) 
的表2中可以看到，他允许资源文件夹配置属性。比如drawable就可以配置成drawable-sw240dp,或者drawable-2340*1080。所以，我们可以新建一个这样尺寸的drawable,
用来存放UI基于这个尺寸的图片。<br>
当然了，这种方案还是有不足的，1是这么会加大apk的包体积，2是设计师可能不会同意这种方案。他不同意的情况下，就只能通过Layer-list组合出一张图片了。
## Layer-list适配方案
这种情况是最常用的适配方案了。但是出现了一些波折,在跑Demo的时候，发现在item 写的dp 值在不同手机上大小不一样。emmmmm？这不很正常吗？
当小logo的时候，小一点点设计师看不出来，当一个图片超过屏幕一半的时候，小一点点就特别明显了。<br>
emmmmm? 为啥你这个和界面的适配不一样，你界面适配都是适配了360dp 的？<br>
emmmmm? 这个是一张图片。<br>
我看到你这里写的dp！！！！！<br>
这个Demo没有添加屏幕适配方案，容我添加一下！！！。当添加完autosize之后。<br>
你这个屏幕为啥会从刚刚那个样子闪成适配后的样子！,这么做是不行的，不能闪！！！！<br>
那就只能换一种解决方案了。smallestWidth在被差点被遗忘后再次被搬到了代码里。抄一段官方的描述:

> 用可用屏幕区域的最短尺寸。具体而言，应用窗口的 `smallestWidth` 是窗口可用高度和宽度的最小尺寸（您也可将其视为窗口的“最小可能宽度”）。您可使用此限定符来确保应用界面的可用宽度至少为 `<N>` dp。
>
> 例如，如果布局要求屏幕区域的最小尺寸始终至少为 600 dp，则可使用此限定符创建布局资源 `res/layout-sw600dp/`。仅当可用屏幕的最小尺寸至少为 600 dp（无论 600 dp 表示的边是用户所认为的高度还是宽度）时，系统才会使用这些资源。调整窗口大小（更改可用宽度/高度）或重新定位（可能会更改系统边衬区）时，最小宽度可能会改变。
>
> 使用最小宽度确定一般屏幕尺寸非常有用，因为宽度通常是设计布局时的驱动因素。界面通常是竖直滚动的，但其在水平方向上所需的最小空间则相对有很多的限制。可用宽度也是确定是否针对手机使用单窗格布局，或针对平板电脑使用多窗格布局的关键因素。因此，您可能最关注每台设备上的最小可能宽度。
>
> 系统在计算设备的最小宽度时，会将屏幕装饰和系统界面都考虑在内。例如，如果设备屏幕上的某些永久性界面元素沿着最小宽度轴占据空间，则系统会声明最小宽度小于实际屏幕尺寸，因为这些屏幕像素不适用于您的界面。
>
> 以下是一些可用于常见屏幕尺寸的值：
>
> - 320，适用于屏幕配置如下的设备：
>   - 240x320 ldpi（QVGA 手机）
>   - 320x480 mdpi（手机）
>   - 480x800 hdpi（高密度手机）
> - 480，适用于 480x800 mdpi 之类的屏幕（平板电脑/手机）。
> - 600，适用于 600x1024 mdpi 之类的屏幕（7 英寸平板电脑）。
> - 720，适用于 720x1280 mdpi 之类的屏幕（10 英寸平板电脑）。
>
> 当应用为多个资源目录提供不同的 smallestWidth 限定符值时，系统会使用最接近（但未超出）设备 smallestWidth 的值。
>
> 此项为 API 级别 13 中的新增配置。
>
> 另请参阅 [`android:requiresSmallestWidthDp`](https://developer.android.com/guide/topics/manifest/supports-screens-element#requiresSmallest) 属性（声明与应用兼容的最小 smallestWidth）和 `smallestScreenWidthDp` 配置字段（存放设备的 smallestWidth 值）。
>
> 如需详细了解如何使用此限定符针对不同的屏幕进行设计，请参阅[支持不同的屏幕尺寸](https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes)。

这个时候，肯定不能对于针对于图片设置，那就只能针对于dp值，写一个dimen，然后整多个values。不同的smallestWidth内部的对应的dp的具体值不一样。

## 设置view

这种方案针对于 那种进入启动页后，有动画效果的APP就可以等view 渲染出来之后再跳转出去，逻辑上的等等过程我们通常可以请求一下数据，初始化一些内容使得首页渲染得更快。 

我尝试在APP内部重新设置一个主题，对window设置背景图片等等。往往因为在跳转新界面的时候，启动页暂停了，就不会渲染出来了。有一个骚想法，就是不管干啥，跳转到时候延迟个500毫秒，基本上就可以渲染出来了[手动狗头!]() [狗头保命！]() 。

## 总结

方案的选择，其实有蛮多影响因素的，还是得看业务诉求。我在Demo中使用autosize 适配后屏幕会闪一下。但是在自己的手机上又没有，为了防止其他手机上也会闪，我选择了取消autosize的适配，采用了smallestWidth。至于为啥会闪一下，我有点懵逼。

在使用Layer-list的过程中也不是流畅，因为我们底部logo 只有50dp,在手机底部导航栏开启后，看起来就没有50dp <u>其实是有50的只是被挡住了，而且我们背景是白色的，设置导航栏透明感觉怪怪的</u>。而且特别长的手机上，显示和设计稿还是不一样，中间和头部会空很多，虽然怪，但是内容还是显示完整清晰了的。小屏手机和正常的手机上 显示还是正常的。

### 为啥为闪的分析

简单分析一下，屏幕为啥会闪。现象是背景图中图片突然由大变小或者由小变大。结合上面的经验，开始出现的效果是没有适配前的效果，闪之后的出现的就是适配后调整的结果，而在Google 提供的 [Activity config  ](https://developer.android.com/guide/topics/manifest/activity-element#config) 配置变更中可以看到。有类似于屏幕密度发生变更后 会重新执行生命周期的。在打印的logo就没有发现他执行[onConfigurationChanged()](https://developer.android.com/reference/android/app/Activity#onConfigurationChanged(android.content.res.Configuration)) 。那么这个方向好像是错的，因为好像没有遇到autosize 跳转另外一个自定义尺寸的手机有闪烁的情况。

我在[Android 屏幕适配——smallestWidth适配](https://www.jianshu.com/p/c1dd77050e68) 中看到了这句话：

```
Data type is double. System default value is 360
```
那么，这个值是否会影响我们的屏幕适配呢？我autoSize 适配的时候，写的367，而base_dp=360，在模拟器就会闪。当两个值是一样的时候就不会闪。









