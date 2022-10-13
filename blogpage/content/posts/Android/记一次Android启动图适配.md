## 资料
* [android 应用资源概述](https://developer.android.com/guide/topics/resources/providing-resources)
* [Android 屏幕适配——smallestWidth适配](https://www.jianshu.com/p/c1dd77050e68)
* [AndroidAutoSize ](https://github.com/JessYanCoding/AndroidAutoSize)
* [Activity config  ](https://developer.android.com/guide/topics/manifest/activity-element#config)
* [骚年你的屏幕适配方式该升级了!-SmallestWidth 限定符适配方案](https://www.jianshu.com/p/2aded8bb6ede)
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



### Google 文档

#### **表 1.** 项目 `res/` 目录中支持的资源目录。

| 目录        | 资源类型                                                     |
| :---------- | :----------------------------------------------------------- |
| `animator/` | 用于定义[属性动画](https://developer.android.com/guide/topics/graphics/prop-animation)的 XML 文件。 |
| `anim/`     | 用于定义[补间动画](https://developer.android.com/guide/topics/graphics/view-animation#tween-animation)的 XML 文件。（属性动画也可保存在此目录中，但为了区分这两种类型，属性动画首选 `animator/` 目录。） |
| `color/`    | 用于定义颜色状态列表的 XML 文件。请参阅[颜色状态列表资源](https://developer.android.com/guide/topics/resources/color-list-resource) |
| `drawable/` | 位图文件（`.png`、`.9.png`、`.jpg`、`.gif`）或编译为以下可绘制资源子类型的 XML 文件：位图文件九宫图（可调整大小的位图）状态列表形状动画可绘制对象其他可绘制对象请参阅[可绘制资源](https://developer.android.com/guide/topics/resources/drawable-resource)。 |
| `mipmap/`   | 适用于不同启动器图标密度的可绘制对象文件。如需详细了解如何使用 `mipmap/` 文件夹管理启动器图标，请参阅[将应用图标放在 mipmap 目录中](https://developer.android.com/training/multiscreen/screendensities#mipmap)。 |
| `layout/`   | 用于定义界面布局的 XML 文件。请参阅[布局资源](https://developer.android.com/guide/topics/resources/layout-resource)。 |
| `menu/`     | 用于定义应用菜单（例如选项菜单、上下文菜单或子菜单）的 XML 文件。请参阅[菜单资源](https://developer.android.com/guide/topics/resources/menu-resource)。 |
| `raw/`      | 需以原始形式保存的任意文件。如要使用原始 `InputStream` 打开这些资源，请使用资源 ID（即 `R.raw.*filename*`）调用 `Resources.openRawResource()`。但是，如需访问原始文件名和文件层次结构，则可以考虑将某些资源保存在 `assets/` 目录（而非 `res/raw/`）下。`assets/` 中的文件没有资源 ID，因此您只能使用 `AssetManager` 读取这些文件。 |
| `values/`   | 包含字符串、整数和颜色等简单值的 XML 文件。其他 `res/` 子目录中的 XML 资源文件会根据 XML 文件名定义单个资源，而 `values/` 目录中的文件可描述多个资源。对于此目录中的文件，`<resources>` 元素的每个子元素均会定义一个资源。例如，`<string>` 元素会创建 `R.string` 资源，`<color>` 元素会创建 `R.color` 资源。由于每个资源均使用自己的 XML 元素进行定义，因此您可以随意命名文件，并在某个文件中放入不同的资源类型。但是，您可能需要将独特的资源类型放在不同的文件中，使其一目了然。例如，对于可在此目录中创建的资源，下面给出了相应的文件名约定：arrays.xml：资源数组（[类型数组](https://developer.android.com/guide/topics/resources/more-resources#TypedArray)）colors.xml：[颜色值](https://developer.android.com/guide/topics/resources/more-resources#Color)dimens.xml：[尺寸值](https://developer.android.com/guide/topics/resources/more-resources#Dimension)strings.xml：[字符串值](https://developer.android.com/guide/topics/resources/string-resource)styles.xml：[样式](https://developer.android.com/guide/topics/resources/style-resource)请参阅[字符串资源](https://developer.android.com/guide/topics/resources/string-resource)、[样式资源](https://developer.android.com/guide/topics/resources/style-resource)及[更多资源类型](https://developer.android.com/guide/topics/resources/more-resources)。 |
| `xml/`      | 可在运行时通过调用 `Resources.getXML()` 读取的任意 XML 文件。各种 XML 配置文件（例如[可搜索配置](https://developer.android.com/guide/topics/search/searchable-config)）都必须保存在此处。 |
| `font/`     | 带有扩展名的字体文件（例如 `.ttf`、`.otf` 或 `.ttc`），或包含 `<font-family>` 元素的 XML 文件。如需详细了解作为资源的字体，请参阅 [XML 中的字体](https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml)。 |





#### **表 2.** 配置限定符名称。

| 配置                             | 限定符值                                                     | 说明                                                         |
| :------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| MCC 和 MNC                       | 示例： `mcc310` `mcc310-mnc004` `mcc208-mnc00` 等等          | 移动设备国家/地区代码 (MCC)，（可选）后跟设备 SIM 卡中的移动设备网络代码 (MNC)。例如，`mcc310` 是指美国的任一运营商，`mcc310-mnc004` 是指美国的 Verizon 公司，而 `mcc208-mnc00` 是指法国的 Orange 公司。如果设备使用无线电连接（GSM 手机），则 MCC 和 MNC 值来自 SIM 卡。您也可以单独使用 MCC（例如，将特定于国家/地区的合法资源添加到应用中）。如果只需根据语言指定，则改用语言、脚本（可选）和区域（可选）限定符（稍后进行介绍）。如果决定使用 MCC 和 MNC 限定符，请谨慎执行此操作并测试限定符是否按预期工作。另请参阅配置字段 `mcc` 和 `mnc`，二者分别表示当前的移动设备国家代码和移动设备网络代码。 |
| 语言、脚本（可选）和区域（可选） | 示例： `en` `fr` `en-rUS` `fr-rFR` `fr-rCA` `b+en` `b+en+US` `b+es+419` `b+zh+Hant` `b+sr+Latn+RS` | 语言通过由两个字母组成的 [ISO 639-1](http://www.loc.gov/standards/iso639-2/php/code_list.php) 语言代码进行定义，可以选择后跟两个字母组成的 [ISO 3166-1-alpha-2](https://www.iso.org/obp/ui/#iso:pub:PUB500001:en) 区域码（前缀用小写字母 `r`）。这些代码不区分大小写；`r` 前缀用于区分区域码。您不能单独指定区域。Android 7.0（API 级别 24）引入对 [BCP 47 语言标记](https://tools.ietf.org/html/bcp47)的支持，可供您用来限定特定于语言和区域的资源。语言标记由一个或多个子标记序列组成，每个子标记都能优化或缩小由整体标记标识的语言范围。如需了解有关语言标记的详细信息，请参阅[用于标识语言的标记](https://tools.ietf.org/html/rfc5646)。如要使用 BCP 47 语言标记，请将 `b+` 和两个字母的 [ISO 639-1](http://www.loc.gov/standards/iso639-2/php/code_list.php) 语言代码串联；其后还可选择使用其他子标记，用 `+` 分隔即可。如果用户在系统设置中更改语言，则语言标记可能会在应用的生命周期中发生变更。如需了解运行时应用会因此受到何种影响，请参阅[处理运行时变更](https://developer.android.com/guide/topics/resources/runtime-changes)。有关针对其他语言本地化应用的完整指南，请参阅[本地化](https://developer.android.com/guide/topics/resources/localization)。另请参阅 `getLocales()` 方法，了解该方法提供的已定义语言区域列表。此列表包含主要的语言区域。 |
| 布局方向                         | `ldrtl` `ldltr`                                              | 应用的布局方向。`ldrtl` 是指“布局方向从右到左”。`ldltr` 是指“布局方向从左到右”（默认的隐式值）。此配置适用于布局、可绘制对象或值等任何资源。例如，如要针对阿拉伯语提供某种特定布局，并针对任何其他“从右到左”的语言（例如波斯语或希伯来语）提供某种通用布局，则可提供以下资源：`res/` ` layout/` `  main.xml`（默认布局） ` layout-ar/` `  main.xml`（特定于阿拉伯语的布局） ` layout-ldrtl/` `  main.xml`（任何“从右到左”语言，阿拉伯语除外，因为“ar”语言限定符具有更高的优先级。）**注意**：如要为应用启用从右到左的布局功能，则必须将 [`supportsRtl`](https://developer.android.com/guide/topics/manifest/application-element#supportsrtl) 设置为 `"true"`，并将 [`targetSdkVersion`](https://developer.android.com/guide/topics/manifest/uses-sdk-element#target) 设置为 17 或更高版本。此项为 API 级别 17 中的新增配置。 |
| smallestWidth                    | `sw<N>dp`  示例： `sw320dp` `sw600dp` `sw720dp` 等等         | 应用可用屏幕区域的最短尺寸。具体而言，应用窗口的 `smallestWidth` 是窗口可用高度和宽度的最小尺寸（您也可将其视为窗口的“最小可能宽度”）。您可使用此限定符来确保应用界面的可用宽度至少为 `<N>` dp。例如，如果布局要求屏幕区域的最小尺寸始终至少为 600 dp，则可使用此限定符创建布局资源 `res/layout-sw600dp/`。仅当可用屏幕的最小尺寸至少为 600 dp（无论 600 dp 表示的边是用户所认为的高度还是宽度）时，系统才会使用这些资源。调整窗口大小（更改可用宽度/高度）或重新定位（可能会更改系统边衬区）时，最小宽度可能会改变。使用最小宽度确定一般屏幕尺寸非常有用，因为宽度通常是设计布局时的驱动因素。界面通常是竖直滚动的，但其在水平方向上所需的最小空间则相对有很多的限制。可用宽度也是确定是否针对手机使用单窗格布局，或针对平板电脑使用多窗格布局的关键因素。因此，您可能最关注每台设备上的最小可能宽度。系统在计算设备的最小宽度时，会将屏幕装饰和系统界面都考虑在内。例如，如果设备屏幕上的某些永久性界面元素沿着最小宽度轴占据空间，则系统会声明最小宽度小于实际屏幕尺寸，因为这些屏幕像素不适用于您的界面。以下是一些可用于常见屏幕尺寸的值：320，适用于屏幕配置如下的设备：240x320 ldpi（QVGA 手机）320x480 mdpi（手机）480x800 hdpi（高密度手机）480，适用于 480x800 mdpi 之类的屏幕（平板电脑/手机）。600，适用于 600x1024 mdpi 之类的屏幕（7 英寸平板电脑）。720，适用于 720x1280 mdpi 之类的屏幕（10 英寸平板电脑）。当应用为多个资源目录提供不同的 smallestWidth 限定符值时，系统会使用最接近（但未超出）设备 smallestWidth 的值。此项为 API 级别 13 中的新增配置。另请参阅 [`android:requiresSmallestWidthDp`](https://developer.android.com/guide/topics/manifest/supports-screens-element#requiresSmallest) 属性（声明与应用兼容的最小 smallestWidth）和 `smallestScreenWidthDp` 配置字段（存放设备的 smallestWidth 值）。如需详细了解如何使用此限定符针对不同的屏幕进行设计，请参阅[支持不同的屏幕尺寸](https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes)。 |
| 可用宽度和高度                   | `w<N>dp` `h<N>dp`  示例： `w720dp` `w1024dp` `h720dp` `h1024dp` 等等 | 指定资源应使用的最小可用屏幕宽度或高度（以 `dp` 为单位，由 `<N>` 值定义）。当设备屏幕方向在横屏和竖屏之间切换时、设备折叠或展开时或者系统进入或退出多窗口模式时，系统会将这些配置值与当前的显示宽度和高度进行比较。在多窗口模式下，这些值反映的是包含应用的窗口的宽度和高度，而不是设备屏幕的宽度和高度。同样，对于嵌入的 activity，该值与各个 activity 的宽度和高度（而非屏幕的宽度和高度）相关，请参阅 [activity 嵌入](https://developer.android.com/guide/topics/large-screens/activity-embedding)。可用宽度和高度通常有助于确定是否使用多窗格布局，因为即使是在平板电脑设备上，您通常也不会希望在竖屏模式下像横屏模式那样使用多窗格布局。因此，您可以使用这些限定符来指定布局所需的最小宽度和/或高度，而无需同时使用屏幕尺寸和屏幕方向限定符。当应用为这些配置提供具有不同值的多个资源目录时，系统会使用最接近（但未超出）设备当前屏幕宽度的值。系统将实际屏幕宽度与指定宽度之间的差值和实际屏幕高度与指定高度之间的差值相加（未指定的高度和宽度值为 0），以确定最接近的目录。该值会排除[窗口边衬区](https://developer.android.com/reference/android/view/WindowInsets)占据的区域，因此，如果设备显示屏的边缘具有永久性界面元素，则宽度和高度值会小于实际屏幕尺寸（即使应用使用 [`Window#setDecorFitsSystemWindows(boolean)`](https://developer.android.com/reference/android/view/Window#setDecorFitsSystemWindows(boolean)) 或 [`WindowCompat#setDecorFitsSystemWindows(Window,boolean)`](https://developer.android.com/reference/androidx/core/view/WindowCompat#setDecorFitsSystemWindows(android.view.Window,boolean)) 以边缘对齐的方式显示）。部分非固定的竖屏装饰（例如可在全屏模式下可隐藏的手机状态栏）和窗口装饰（例如标题栏或操作栏）不在考虑范围之内，因此应用必须准备好处理稍小于其指定值的空间。**注意**：系统会选择宽度和高度均匹配的资源。因此，强烈建议您同时指定这两种资源，而不是仅指定其中一种资源。例如，如果实际屏幕为 w720dp 乘以 h1280dp，并且其中一个资源符合 w720dp 的条件，另一个资源符合 w700dp-h1200dp 的条件，即使前一个资源与其指定的资源完全匹配，系统也会选择后者。此项为 API 级别 13 中的新增配置。另请参阅 `screenWidthDp` 和 `screenHeightDp` 配置字段，这些字段用于保存当前屏幕的宽度和高度。如需详细了解如何使用此限定符针对不同的屏幕进行设计，请参阅[支持不同的屏幕尺寸](https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes)。 |
| 屏幕尺寸                         | `small` `normal` `large` `xlarge`                            | `small`：尺寸类似于低密度 QVGA 屏幕的屏幕。小屏幕的最小布局尺寸约为 320x426 dp。例如，QVGA 低密度屏幕和 VGA 高密度屏幕。`normal`：尺寸类似于中等密度 HVGA 屏幕的屏幕。标准屏幕的最小布局尺寸约为 320x470 dp。例如，WQVGA 低密度屏幕、HVGA 中等密度屏幕、WVGA 高密度屏幕。`large`：尺寸类似于中等密度 VGA 屏幕的屏幕。大屏幕的最小布局尺寸约为 480x640 dp。例如，VGA 和 WVGA 中等密度屏幕。`xlarge`：明显大于传统中等密度 HVGA 屏幕的屏幕。超大屏幕的最小布局尺寸约为 720x960 dp。在大多数情况下，屏幕超大的设备体积太大，不能放进口袋，最常见的是平板式设备。此项为 API 级别 9 中的新增配置。**注意**：使用尺寸限定符并不意味着相应资源仅适用于该尺寸的屏幕。如果没有为备用资源提供最符合当前设备配置的限定符，则系统可能会使用其中[最匹配](https://developer.android.com/guide/topics/resources/providing-resources#BestMatch)的资源。**注意**：如果所有资源均使用大于当前屏幕的尺寸限定符，则系统**不会**使用这些资源，并且应用将在运行时崩溃（例如，如果所有布局资源均以 `xlarge` 限定符标记，但设备是标准尺寸的屏幕）。此项为 API 级别 4 中的新增配置。另请参阅 `screenLayout` 配置字段，该字段用于指示屏幕为小尺寸、标准尺寸还是大尺寸。如需了解详情，请参阅[屏幕兼容性概览](https://developer.android.com/guide/practices/screens_support)。 |
| 屏幕宽高比                       | `long` `notlong`                                             | `long`：宽屏，如 WQVGA、WVGA、FWVGA`notlong`：非宽屏，如 QVGA、HVGA 和 VGA此项为 API 级别 4 中的新增配置。此配置完全基于屏幕的宽高比（宽屏较宽），与屏幕方向无关。另请参阅 `screenLayout` 配置字段，该字段用于指示屏幕是否为宽屏。 |
| 圆形屏幕                         | `round` `notround`                                           | `round`：圆形屏幕，例如圆形可穿戴式设备`notround`：方形屏幕，例如手机或平板电脑此项为 API 级别 23 中的新增配置。另请参阅 `isScreenRound()` 配置方法，该方法用于指示屏幕是否为圆形屏幕。 |
| 广色域                           | `widecg` `nowidecg`                                          | `widecg`：显示广色域，如 Display P3 或 AdobeRGB`nowidecg`：显示窄色域，如 sRGB此项为 API 级别 26 中的新增配置。另请参阅 `isScreenWideColorGamut()` 配置方法，该方法用于指示屏幕是否具有广色域。 |
| 高动态范围 (HDR)                 | `highdr` `lowdr`                                             | `highdr`：显示高动态范围`lowdr`：显示低/标准动态范围此项为 API 级别 26 中的新增配置。另请参阅 `isScreenHdr()` 配置方法，该方法用于指示屏幕是否具有 HDR 功能。 |
| 屏幕方向                         | `port` `land`                                                | `port`：设备处于竖屏模式（纵向）`land`：设备处于横屏模式（水平）如果用户旋转屏幕，此配置可能会在应用生命周期中发生变化。如需了解这会在运行时期间给应用带来哪些影响，请参阅[处理运行时变更](https://developer.android.com/guide/topics/resources/runtime-changes)。另请参阅 `orientation` 配置字段，该字段用于指示当前的设备方向。 |
| 界面模式                         | `car` `desk` `television` `appliance` `watch` `vrheadset`    | `car`：设备正在车载基座中显示`desk`：设备正在桌面基座中显示`television`：设备正在通过电视显示内容，通过将界面投影到离用户较远的大屏幕上，为用户提供“十英尺”体验。主要面向遥控交互或其他非触控式交互`appliance`：设备用作不带显示屏的装置`watch`：设备配有显示屏，并且可戴在手腕上`vrheadset`：设备正在通过虚拟现实耳机显示内容此项为 API 级别 8 中的新增配置，API 13 中的新增电视配置，API 20 中的新增手表配置。如需了解在设备插入基座或从基座中取出时您的应用会如何响应，请参阅[确定和监控插接状态和基座类型](https://developer.android.com/training/monitoring-device-state/docking-monitoring)。如果用户将设备插入基座，此配置可能会在应用生命周期中发生变化。您可以使用 `UiModeManager` 启用或停用其中的部分模式。如需了解这会在运行时期间给应用带来哪些影响，请参阅[处理运行时变更](https://developer.android.com/guide/topics/resources/runtime-changes)。 |
| 夜间模式                         | `night` `notnight`                                           | `night`：夜间`notnight`：白天此项为 API 级别 8 中的新增配置。如果夜间模式停留在自动模式（默认），此配置可能会在应用生命周期中发生变化。在这种情况下，该模式会根据当天的时间进行调整。您可以使用 `UiModeManager` 启用或停用此模式。如需了解这会在运行时期间给应用带来哪些影响，请参阅[处理运行时变更](https://developer.android.com/guide/topics/resources/runtime-changes)。 |
| 屏幕像素密度 (dpi)               | `ldpi` `mdpi` `hdpi` `xhdpi` `xxhdpi` `xxxhdpi` `nodpi` `tvdpi` `anydpi` `*nnn*dpi` | `ldpi`：低密度屏幕；约为 120 dpi。`mdpi`：中密度（传统 HVGA）屏幕；约为 160 dpi。`hdpi`：高密度屏幕；约为 240 dpi。`xhdpi`：超高密度屏幕；约为 320 dpi。此项为 API 级别 8 中的新增配置`xxhdpi`：超超高密度屏幕；约为 480 dpi。此项为 API 级别 16 中的新增配置。`xxxhdpi`：超超超高密度屏幕使用（仅限启动器图标，请参阅“支持多种屏幕”中的[注释](https://developer.android.com/guide/practices/screens_support#xxxhdpi-note)）；约为 640 dpi。此项为 API 级别 18 中的新增配置。`nodpi`：此限定符可用于您不希望为匹配设备密度而进行缩放的位图资源。`tvdpi`：密度介于 mdpi 和 hdpi 之间的屏幕；约为 213 dpi。这不属于“主要”密度组。它主要用于电视，而大多数应用都不需要它。对于大多数应用而言，提供 mdpi 和 hdpi 资源便已足够，系统将视情况对其进行缩放。此项为 API 级别 13 中的新增配置`anydpi`：此限定符适合所有屏幕密度，其优先级高于其他限定符。这对于[矢量可绘制对象](https://developer.android.com/training/material/drawables#VectorDrawables)非常有用。此项为 API 级别 21 中的新增配置`*nnn*dpi`：用于表示非标准密度，其中 `*nnn*` 是正整数屏幕密度。在大多数情况下，此限定符并不适用。使用标准密度存储分区，可显著减少因支持市场上各种设备屏幕密度而产生的开销。这六种主要密度之间的缩放比例为 3:4:6:8:12:16（tvdpi 密度忽略不计）。因此，9x9 (ldpi) 位图相当于 12x12 (mdpi)、18x18 (hdpi)、24x24 (xhdpi) 位图，依此类推。如果您认为图片资源在电视或其他某些设备上的呈现效果不够好，进而想尝试使用 tvdpi 资源，则缩放比例应为 1.33*mdpi。例如，如果某张图片在 mdpi 屏幕上的大小为 100 px x 100 px，那么它在 tvdpi 屏幕上的大小应该为 133 px x 133 px。**注意**：使用密度限定符并不意味着资源仅适用于该密度的屏幕。如果您没有为备用资源提供最符合当前设备配置的限定符，则系统可能会使用其中[最匹配](https://developer.android.com/guide/topics/resources/providing-resources#BestMatch)的资源。如需详细了解如何处理不同屏幕密度以及 Android 如何缩放位图以适应当前密度，请参阅[支持多种屏幕](https://developer.android.com/guide/practices/screens_support)。 |
| 触摸屏类型                       | `notouch` `finger`                                           | `notouch`：设备没有触摸屏。`finger`：设备有一个专供用户通过手指直接与其交互的触摸屏。另请参阅 `touchscreen` 配置字段，该字段用于指示设备的触摸屏类型。 |
| 键盘可用性                       | `keysexposed` `keyshidden` `keyssoft`                        | `keysexposed`：设备具有可用的键盘。如果设备启用了软件键盘（不无可能），那么即使用户未找到硬件键盘，或者该设备没有硬件键盘，也可使用此限定符。如果设备未提供软件键盘或软件键盘已停用，则只有在向用户公开硬件键盘时，才可使用此限定符。`keyshidden`：设备具有可用的硬件键盘，但其处于隐藏状态，且设备未启用软件键盘。`keyssoft`：设备已启用软件键盘（无论是否可见）。如果您提供了 `keysexposed` 资源，但未提供 `keyssoft` 资源，则无论键盘是否可见，只要系统已启用软件键盘，其便会使用 `keysexposed` 资源。如果用户打开硬件键盘，此配置可能会在应用生命周期中发生变化。如需了解这会在运行时期间给应用带来哪些影响，请参阅[处理运行时变更](https://developer.android.com/guide/topics/resources/runtime-changes)。另请参阅配置字段 `hardKeyboardHidden` 和 `keyboardHidden`，这两个字段分别用于指示硬件键盘的可见性和任一键盘（包括软件键盘）的可见性。 |
| 主要的文本输入法                 | `nokeys` `qwerty` `12key`                                    | `nokeys`：设备没有用于文本输入的硬件按键。`qwerty`：设备具有硬件 QWERTY 键盘（无论其对用户是否可见）。`12key`：设备具有包含 12 个按键的硬件键盘（无论其对用户是否可见）。另请参阅 `keyboard` 配置字段，该字段用于指示可用的主要文本输入法。 |
| 导航键可用性                     | `navexposed` `navhidden`                                     | `navexposed`：导航键可供用户使用。`navhidden`：导航键不可用（例如位于合上的盖子后面）。如果用户显示导航键，此配置可能会在应用生命周期中发生变化。如需了解这会在运行时期间给应用带来哪些影响，请参阅[处理运行时变更](https://developer.android.com/guide/topics/resources/runtime-changes)。另请参阅 `navigationHidden` 配置字段，该字段用于指示导航键是否处于隐藏状态。 |
| 主要的非触摸导航方法             | `nonav` `dpad` `trackball` `wheel`                           | `nonav`：除了使用触摸屏以外，设备没有其他导航设施。`dpad`：设备具有用于导航的方向键。`trackball`：设备具有用于导航的轨迹球。`wheel`：设备具有用于导航方向盘（不常见）。另请参阅 `navigation` 配置字段，该字段用于指示可用的导航方法类型。 |
| 平台版本（API 级别）             | 示例： `v3` `v4` `v7` 等等                                   | 设备支持的 API 级别。例如，`v1` 对应 API 级别 1（搭载 Android 1.0 或更高版本的设备）；`v4` 对应 API 级别 4（搭载 Android 1.6 或更高版本的设备）。如需了解有关这些值的详细信息，请参阅 [Android API 级别](https://developer.android.com/guide/topics/manifest/uses-sdk-element#ApiLevels)文档。 |

**注意**：自 Android 1.0 起便已添加部分配置限定符，因此并非所有版本的 Android 系统都支持所有限定符。使用新限定符会隐式添加平台版本限定符，因此较旧版本系统的设备必然会忽略它。例如，使用 `w600dp` 限定符会自动包括 `v13` 限定符，因为可用宽度限定符是 API 级别 13 中的新增配置。为避免出现任何问题，请始终包含一组默认资源（一组不包含任何限定符的资源）。如需了解详细信息，请参阅[利用资源提供最佳的设备兼容性](https://developer.android.com/guide/topics/resources/providing-resources#Compatibility)部分。
