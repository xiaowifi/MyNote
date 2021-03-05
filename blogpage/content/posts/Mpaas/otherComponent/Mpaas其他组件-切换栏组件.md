+++
date = "2021-3-3"
title = "Mpaas其他组件-切换栏组件"
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
## 切换栏组件
> com.alipay.mobile.antui.segement.AUSegment

#### 自定义属性

10.0.20 及以后版本添加了 `scroll` 属性。

| 属性名          | 用途                                                         | 类型              |
| :-------------- | :----------------------------------------------------------- | :---------------- |
| tabCount        | Tab 数量(如果说这个值定义为2，但是tab数量有3个，只会显示前两个tab)，不设置这个可能不会均分父布局 | integer           |
| tab1Text        | Tab1 文案                                                    | string，reference |
| tab2Text        | Tab1 文案                                                    | string，reference |
| tab3Text        | Tab3 文案                                                    | string，reference |
| tab4Text        | Tab4 文案                                                    | string，reference |
| tabTextArray    | Tab 文本数组                                                 | string，reference |
| uniformlySpaced | 是否自适应                                                   | boolean           |
| tabTextColor    | 文字颜色                                                     | reference，color  |
| tabTextSize     | 文字大小                                                     | dimension         |
| buttomLineColor | 底部线条颜色                                                 | color，reference  |
| scroll          | 是否支持滚动                                                 | boolean           |
| add             | 显示一个（+）的tab ,默认位于右边，默认值为false，可以自定义图标，只有基础事件，通过getAddIcon() 设置事件等，比如说点击，长按等等。 | boolean           |

#### style 

````
<declare-styleable name="Segment">
        <attr name="tabCount" format="integer" />
        <attr name="tab1Text" format="string|reference" />
        <attr name="tab2Text" format="string|reference" />
        <attr name="tab3Text" format="string|reference" />
        <attr name="tab4Text" format="string|reference" />
        <attr name="tabTextArray" format="string|reference" />
        <attr name="uniformlySpaced" format="boolean" />
        <attr name="tabTextColor" format="color" />
        <attr name="tabTextSize" format="dimension" />
        <attr name="buttomLineColor" format="color|reference" />
        <attr name="scroll" format="boolean" />
        <attr name="repeatClick" format="boolean" />
        <attr name="add" format="boolean" />
        <attr name="tabSpace" format="dimension" />
        <attr name="edgeSpace" format="dimension" />
    </declare-styleable>
````



#### 参考内容

> activity ：com.alipay.mobile.antui.viewdemo.AUSegmentActivity
>
> layou:view_switch_tab 
>
> [Mpaas AUSegment  阿里云地址](https://help.aliyun.com/document_detail/71739.html?spm=a2c4g.11186623.6.1504.7d666d7fsQbQEi)

#### 默认样式

<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210303155345.png" alt="image-20210303155345319" style="zoom:80%;" />

#### 个人理解

> 这个调调，如果不设置滚动能力，如果tab 内容一样长，感觉是均分的，但是如果有一个特别突出(巨长)，会折叠中间部分(最多显示多少个字受view所占空间影响)，比如长这个样子“我的名...超级长”，一般是前面几个字后面就是几个字。当tab长度不一样，就不像内容数百分比分配了(具体效果看默认样式)，其实是均分了的，只是底部线随着文本长度变动的。
>
> 可以设置红点，红点相对位置可以调整。
>
> 暂未找到更改底部线长度的方法，他默认是基于显示文本长度。文本选中与未选中颜色样式可能需要通过style统一设置 textview的属性。如果不要底部线相关操作，可以设置为背景色，用红点功能。暂时可能不支持 修改未Android 所使用的tablayou 效果。

#### 代码联动

> 如果只有xml，没有切换显示能力。必须设置回调

````
 final AUSegment segment = (AUSegment) findViewById(R.id.switchtab);
        // 设置当前选中
        segment.setCurrentSelTab(0);
        // 设置回调，不设置回调。应该没有切换效果。
        segment.setTabSwitchListener(new AUSegment.TabSwitchListener() {
            @Override
            public void onTabClick(int position, View view) {
            }
        });
````

> 动态重置设置数据

````
segment.resetTabView(new String[]{"AA的", "选项一", "选项二", "选项一萨胡搞"});
````

#### Ausegment方法

* resetTabView 重置视图，这个调调同时重置 tabCount，比如xml中配置tabCount=2，但是重置数据的时候，长度为3，那么就会显示为3个。参考下tabCount 
* adjustLinePosition 调整底部显示线的位置
* selectTab 设置选中的tab,但是不调整底部线
* selectTabAndAdjustLine 选中tab 和底部线一起调整
* selectTabAndAdjustLine 选中tab和底部线，用于非viewpager联动情况，提供过场动画时间。
* setTabSwitchListener 设置tab 切换监听，不设置好像莫得切换效果，点击没有反应
* addTextRightView  加指定的位置加上红点
* addTextRightView  加指定的位置加上红点 （红点相对位置）

> emmmm？下面的好像是可滚动类型独有方法。强行用可能会出现  Attempt to invoke virtual method 'void android.widget.HorizontalScrollView.smoothScrollTo(int, int)。可能根据类型创建的时候生成viewgroup

* init  设置数据源（有滚动能力的才行）,比如说上面抛的问题就是这个方法抛出来的。

* setCurrentSelTab  设置选中的 Tab。文字和底部线同时切换。不设置滚动能力也可以用。但是官网好像不建议在非滚动能力view中使用哦。
* setDivideAutoSize 每个 Tab 固定左右间距为 14dp，当所有 Tab 不足初始宽度时提供是否等分接口。emmmmm? 还是滚动能力的view 才可以。

#### 颜色
> 对于这种tab 首先想到的是 更改选中和未选中状态下的文字颜色和底部栏颜色。通过上面的style  <attr name="tabTextColor" format="color" /> 这竟然是一个颜色值。那么，是不是通过selector 在xml 中设置颜色值就不走不通了。只能从style 中或者代码写入了。底部线倒是可以引入资源 <attr name="buttomLineColor" format="color|reference" /> buttomLineColor 使用shape资源会运行报错，图片资源也会。
````aidl
 private ColorStateList textColor = null;
  public void initStyleByTheme(Context var1) {
        AUAbsTheme var2 = AUThemeManager.getCurrentTheme();
        if (var2.containsKey(AUThemeKey.SEGMENT_TEXTCOLOR)) {
            this.textColor = var2.getColorStateList(var1, AUThemeKey.SEGMENT_TEXTCOLOR);
        }

        if (var2.containsKey(AUThemeKey.SEGMENT_BOTTOM_COLOR)) {
            this.buttomLineColor = var2.getColor(var1, AUThemeKey.SEGMENT_BOTTOM_COLOR);
        }

    }
````
通过上面方法可以知道，他textcolor 竟然是ColorStateList。所以设置单颜色肯定是没法修改选中的状态。
##### ColorStateList xml定义
> 和shape 差不多
````aidl
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="@color/color_main" android:state_selected="true"/>
    <item android:color="@color/AU_COLOR1" android:state_selected="false"/>
    <item android:color="@color/AU_COLOR2" />
</selector>
````
##### 资源使用
> 资源使用是采用，xml 定义资源覆盖主题切换资源定义。
#### 配合AUAbsTheme
* AUThemeKey.SEGMENT_TEXTCOLOR  ColorStateList
* AUThemeKey.SEGMENT_BOTTOM_COLOR  单颜色值，所以这个地方也可以证明 底部条的长度是不可直接修改了。

````aidl
        put(AUThemeKey.SEGMENT_TEXTCOLOR,R.drawable.segment_color);
        put(AUThemeKey.SEGMENT_BOTTOM_COLOR,R.color.AU_COLOR2);
````
##### 底部线

* buttomLineColor 设置单颜色。如果AU_COLOR9 和buttomLineColor  同时存在，优先使用 buttomLineColor 
* AU_COLOR9  默认的 ausegment 使用的底部颜色。但是滚动模式下app:scroll="true" 不是默认使用这个颜色值。 

