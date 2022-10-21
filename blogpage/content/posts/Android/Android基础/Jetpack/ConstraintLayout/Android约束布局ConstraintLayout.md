+++
date = "2020-10-01"
title = "Android约束布局ConstraintLayout"
description = "Android约束布局ConstraintLayout"
tags = [ "ConstraintLayout"]
categories = [
    "android基础"
]
featured = false
draft = true 

+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)

## 前言

* [constraintlayout github地址](https://github.com/androidx/constraintlayout)

* [参考资料：ConstraintLayout使用汇总](https://segmentfault.com/a/1190000014876944)

* [google Android 官网 constraintLayout](https://developer.android.com/training/constraint-layout)

* [google 提供github Demo地址 ](https://github.com/android/views-widgets-samples/tree/main/ConstraintLayoutExamples) 强烈建议自己看Demo。

* [sdk 中 constraintLayout 地址](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout)

* [google 开发者 微信公众号：解析ConstraintLayout的性能优势 ](https://mp.weixin.qq.com/s/gGR2itbY7hh9fo61SxaMQQ?utm_source=androidweekly.cn&utm_medium=website)

  > 为啥会在google中搜索到两个，一个是带演示的，一个不带演示，就纯粹的api介绍

版本号：implementation 'androidx.constraintlayout:constraintlayout:2.1.0'

## 正文

> 个人觉得，想要用一个东西，就得先知道，这个调调提供了些什么能力，或者说比起其他类型的有些什么优势。[google 开发者 微信公众号：解析ConstraintLayout的性能优势 ](https://mp.weixin.qq.com/s/gGR2itbY7hh9fo61SxaMQQ?utm_source=androidweekly.cn&utm_medium=website)  这么一看，这个调调还是应该早点学习的。

个人感觉 constraintlayout 就是为了替换掉 LinearLayout 和 RelativeLayout， LinearLayout 和 RelativeLayout 支持的布局方式constraintlayout同样支持。

### 简单使用

> 主要是针对某一类特性进行Demo展示

#### 相对定位常用属性

> 个人感觉这种定位方式应该算相对定位的一种了，而且LinearLayout在我看来也是相对定位，只是说定位关系不需要我们手动写罢了，而且LinearLayout 只有一种定位关系（位于上一个view的下方或者右边）。 这个可能是最常用的属性了。大致有10类。对应的值有两类，其他view的id,另外一种就是parent。如果不设置宽高，那么就需要定义view的上下左右参照位置，如果都是parent，那么大小就是和父view一样大小了。

*  当前view的顶部在谁的顶部  layout_constraintTop_toTopOf  
*  当前view的顶部在谁的底部 layout_constraintTop_toBottomOf
*  当前view的左边在谁的左边 layout_constraintLeft_toLeftOf
*  当前view的左边在谁的右边 layout_constraintLeft_toRightOf
*  当前view的右边在谁的右边 layout_constraintRight_toRightOf
*  当前view的右边在谁的左边 layout_constraintRight_toLeftOf
*  当前view的底部在谁的底部 layout_constraintBottom_toBottomOf
*  当前view的底部在谁的顶部 layout_constraintBottom_toTopOf
*  当前view的开始位置在谁的结束位置 layout_constraintStart_toEndOf  
*  当前view的开始位置在谁的开始位置 layout_constraintStart_toStartOf
*  当前view的结束位置在谁的结束位置 layout_constraintEnd_toEndOf
*  当前view的结束位置在谁的开始位置 layout_constraintEnd_toStartOf

好像RelativeLayout也有类似的属性，那么试图干掉RelativeLayout就一定有RelativeLayou 不具有的优势（除了上面说的性能优势[google 开发者 微信公众号：解析ConstraintLayout的性能优势 ](https://mp.weixin.qq.com/s/gGR2itbY7hh9fo61SxaMQQ?utm_source=androidweekly.cn&utm_medium=website) ），毕竟google想统一所有Android，那么想要我这样的搬砖工用这个，除了性能意外必须提供其他的优势，否则我是懒得去学习一个类似的轮子的。 如果点到values.xml 中，搜索 layout_constraint，会发现不止上面 10种，还包括了:<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/image-20210828150212837.png" alt="image-20210828150212837" style="zoom:50%;" />



*  layout_constraintBaseline_toBaselineOf 设置相对于某个view的横向对齐方式。对齐样式与两个view gravity 属性有关。

*  layout_constraintCircle 设置圆心view 

*  layout_constraintCircleAngle 以12点钟为开始，顺时针偏移的角度。

*  layout_constraintCircleRadius 这个感觉是圆心到当前view的中心的距离，这个偏移角度感觉也是圆心到view中心点的角度。

  > 基于某个view 周围圆形定位，只要我几何学得好？任何点都可以通过圆心半径角度算出来?

*  layout_constraintDimensionRatio 设置view的宽高比，比如:(3:2),(H,3:2),(w,3:2)

*  layout_constraintGuide_begin 开始位置 逻辑意义上和Guideline 一起使用

*   layout_constraintGuide_end  结束位置 逻辑意义上和Guideline 一起使用

  > 开始位置和结束位置 两个属性不能共存，同时存在会以开始位置为准。

*  layout_constraintGuide_percent 接受一个 float，默认是1，取值通常是0到1，默认guideline 是以当前方向 充满 父view的，而这个属性可以设置百分比，而这个属性的优先级相对于layout_constraintGuide_end和layout_constraintGuide_begin更高，虽然我没有明白这个调调的意义在哪，但是做百分比布局还是蛮香的。

*  layout_constraintHeight_default 枚举：spread，wrap，percent

*  layout_constraintWidth_default 枚举：spread，wrap，percent 

  > 设置默认宽高方式。view设置了不等于0的宽高好像都没有效果，但是没有找到是干嘛的，

*  layout_constraintHeight_max

*  layout_constraintHeight_min

*  layout_constraintWidth_max

*  layout_constraintWidth_min

  > 分别设置view 宽高的最小值和最大值。

*   layout_constraintHeight_percent

*  layout_constraintWidth_percent

  > 传入 float，0到1，设置宽高占父view的多少。

*  layout_constraintHorizontal_bias 横向的偏移量,这个有点复杂。没有懂适用场景。

*  layout_constraintVertical_bias  与layout_constraintHorizontal_bias类似。

* layout_constraintHorizontal_chainStyle 枚举，spread，spread_inside，packed

* layout_constraintVertical_chainStyle 与layout_constraintHorizontal_chainStyle

  1. spread_inside:两边不留空间，中间间距平分 
  2. spread:完全均分
  3. packed:完全不留间距

*  layout_constraintHorizontal_weight 权重 

*  layout_constraintVertical_weight  权重 

* layout_constraintTag tag 源码上是这么描述的：Define a category of view to be used by helpers and motionLayout

* 当然还有设置了没有使用的:

  * layout_constraintBaseline_creator  接收一个integer，设置基准线？但是是干嘛的没有整清楚,

  *  layout_constraintLeft_creator 接收一个ID或者parent,

  *  layout_constraintRight_creator 接收一个ID或者parent

  *  layout_constraintTop_creator 接收一个ID或者parent

  *  layout_constraintBottom_creator 接收一个ID或者parent

    > 这5类creator <img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/image-20210828160019730.png" alt="image-20210828160019730" style="zoom:50%;" />  为啥都是 skip.



#### 相对定位

> 相对定位主要是10个属性的使用。建议查看Demo [google 提供github Demo地址 ](https://github.com/android/views-widgets-samples/tree/main/ConstraintLayoutExamples) 强烈建议自己看Demo。

#### 类似线性布局

> 和相对点类似，查看Demo。[google 提供github Demo地址 ](https://github.com/android/views-widgets-samples/tree/main/ConstraintLayoutExamples) 强烈建议自己看Demo。

#### 以某个点为圆心定位

> 这个官方提供的Demo中，好像没有，但是api 相对简单，就是以一个点为中心定位view。下面是以tv_1 为圆心，100为半径定位view的位置。

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <!--围绕某个点圆形布局 基于tv_1 画圆 如果说 只要圆的节点足够多，是不是可以画几乎所有的位置？ -->
    <TextView
        android:id="@+id/tv_1"
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="@color/colorAccent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="#982A38"
        app:layout_constraintCircle="@id/tv_1"
        app:layout_constraintCircleAngle="45"
        app:layout_constraintDimensionRatio=""
        app:layout_constraintCircleRadius="100dp"
        tools:ignore="MissingConstraints" />

    <TextView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="#501C23"
        app:layout_constraintCircle="@id/tv_1"
        app:layout_constraintCircleAngle="90"
        app:layout_constraintCircleRadius="100dp"
        tools:ignore="MissingConstraints" />

    <TextView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="#C53044"
        app:layout_constraintCircle="@id/tv_1"
        app:layout_constraintCircleAngle="135"
        app:layout_constraintCircleRadius="100dp"
        tools:ignore="MissingConstraints" />

    <TextView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="#ff0022"
        app:layout_constraintCircle="@id/tv_1"
        app:layout_constraintCircleAngle="180"
        app:layout_constraintCircleRadius="100dp"
        tools:ignore="MissingConstraints" />

    <TextView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="#ff0022"
        app:layout_constraintCircle="@id/tv_1"
        app:layout_constraintCircleAngle="225"
        app:layout_constraintCircleRadius="100dp"
        tools:ignore="MissingConstraints" />

    <TextView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="#ff0022"
        app:layout_constraintCircle="@id/tv_1"
        app:layout_constraintCircleAngle="270"
        app:layout_constraintCircleRadius="100dp"
        tools:ignore="MissingConstraints" />
    <TextView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="#ff0022"
        app:layout_constraintCircle="@id/tv_1"
        app:layout_constraintCircleAngle="315"
        app:layout_constraintCircleRadius="100dp"
        tools:ignore="MissingConstraints" />
    <TextView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:background="#ff0022"
        app:layout_constraintCircle="@id/tv_1"
        app:layout_constraintCircleAngle="0"
        app:layout_constraintCircleRadius="100dp"
        tools:ignore="MissingConstraints" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

大概长这个样子：![image-20210828210702816](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/image-20210828210702816.png)



#### 定位辅助线Guideline

> 这种定位辅助线，官方中文翻译好像不叫这个，这个和UI设计的辅助线类似。这个是一个view，好处就是布局中不会展示，至于会不会占用绘制内存和时间（我先留坑）。通常来说，这个是不用赋值给大小的，这个只有两种类型横向和纵向，比如说 一个界面的上下左右的统一的，可能是固定值，也可能是百分比，巧了layout_constraintGuide_percent，layout_constraintGuide_begin，layout_constraintGuide_end.通常来说，我们定位view的时候，是通过设置內间距或者margin去实现这种效果，很少遇到定义一个view然后通过相对定位去做的情况，特别还是定义了一个啥事不干的view？但是定义辅助相对位置的view更加贴合UI设计。代码就相对简单，把Guideline 看做一个正常的view，其他的布局根据情况相对Guideline 进行相对定位，好处就是padding或margin就只用改一次。减少了布局的嵌套，为了少写点padding或者margin 再套一层view，也太疯狂了吧。

[查看代码](https://github.com/android/views-widgets-samples/blob/main/ConstraintLayoutExamples/constraintlayout/src/main/res/layout/constraint_example_3.xml)

#### 相对定位辅助view

这个我理解的不是太深。建议参考：[参考资料：ConstraintLayout使用汇总]((https://segmentfault.com/a/1190000014876944))

#### 相对定位统一隐藏或者显示

> 这个就是做多状态多view的隐藏的，先通过xml 定义好需要同一批隐藏或者显示的view之后，通过逻辑控制当前view的显示或者隐藏达到批量控制view 显示或者隐藏的目的。[参考资料：ConstraintLayout使用汇总]((https://segmentfault.com/a/1190000014876944))



### 开发情景

> 这个调调看起来蛮好用的，但是在实际开发中所需要面临的问题远远不止这些，很多使用和自己的理解还是不一样的，比如说边界值的问题。这个很常见，开发过程中，一个string的来源，长度的限制不是又我们想得那样，明明设计就只是出了5个字单行的样式，如果输入的时候没有限制，到APP端口，那么可能给你了几行，因为没有考虑到这种情景，然后ConstraintLayout和我们常用的布局的理解的结果方式不一样，然后就是几个bug。

#### 设置界面Item

![image-20210828214315643](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/image-20210828214315643.png)

就比如说，我们要画一个这个样式的设置列表。如果图片全是图标，那么有多种画法。

##### 常用画法

这是成本最高的一种画法。

```
<LinearLayout
    android:gravity="center_vertical"
    android:paddingEnd="10dp"
    android:paddingStart="10dp"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="45dp">
    <ImageView
        android:src="@drawable/ic_action_name"
        android:layout_width="45dp"
        android:layout_height="match_parent"/>
    <TextView
        android:ellipsize="end"
        android:lines="1"
        android:paddingEnd="10dp"
        android:paddingStart="10dp"
        android:gravity="center_vertical"
        android:text="这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称"
        android:layout_weight="1"
        android:layout_width="0dp"
        android:layout_height="match_parent"/>
    <ImageView
        android:src="@drawable/ic_baseline_arrow_right_24"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</LinearLayout>
```

或者直接就是 TextView 设置左右图片和图片间距，这种基于左右图片间距相同的情况。

```
<TextView
    android:ellipsize="end"
    android:lines="1"
    android:paddingEnd="10dp"
    android:paddingStart="10dp"
    android:gravity="center_vertical"
    android:text="这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称"
    android:drawablePadding="10dp"
    android:drawableLeft="@drawable/ic_action_name"
    android:drawableRight="@drawable/ic_baseline_arrow_right_24"
    android:layout_width="match_parent"
    android:layout_height="45dp"/>
```

但是如果都是网络图片呢？加载出来全部转bitmap也不是不行。针对网络图片，我通常还是会选择采用3个view的这种写法。

#####  使用ConstraintLayout

通过设置左右辅助线，然后相对定位，如果这个界面使用recycleview 搭建，感觉使用ConstraintLayout 优势不大，但是使用view叠加布局的话，优势蛮大的，至于点击事件？那还是使用recycle搭建吧。这个只是作为一种Demo思路。

```
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <androidx.constraintlayout.widget.Guideline
        android:layout_width="0dp"
        android:id="@+id/guide_left"
        app:layout_constraintGuide_begin="10dp"
        android:orientation="vertical"
        android:layout_height="0dp"/>
    <androidx.constraintlayout.widget.Guideline
        android:layout_width="0dp"
        android:id="@+id/guide_right"
        app:layout_constraintGuide_end="10dp"
        android:orientation="vertical"
        android:layout_height="0dp"/>
    <ImageView
        app:layout_constraintLeft_toLeftOf="@id/guide_left"
        app:layout_constraintTop_toTopOf="parent"
        android:id="@+id/image_1"
        android:src="@drawable/ic_action_name"
        android:layout_width="45dp"
        android:layout_height="45dp"/>
    <TextView
        app:layout_constraintRight_toLeftOf="@+id/image_2"
        app:layout_constraintBottom_toBottomOf="@id/image_1"
        app:layout_constraintTop_toTopOf="@id/image_1"
        app:layout_constraintLeft_toRightOf="@id/image_1"
        android:id="@+id/tv_name"
        android:ellipsize="end"
        android:lines="1"
        android:paddingEnd="10dp"
        android:paddingStart="10dp"
        android:gravity="center_vertical"
        android:text="这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称这个是名称"
        android:layout_weight="1"
        android:layout_width="0dp"
        android:layout_height="wrap_content"/>
    <ImageView
        app:layout_constraintRight_toRightOf="@id/guide_right"
        app:layout_constraintBottom_toBottomOf="@id/image_1"
        app:layout_constraintTop_toTopOf="@id/image_1"
        android:id="@+id/image_2"
        android:src="@drawable/ic_baseline_arrow_right_24"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

#### 个人主页头部

![image-20210828222551910](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/image-20210828222551910.png)

在或者说，我们要画一个这个样子的样式。月亮有多套图，月亮跟着名称后面走，最多不超过个人主页。

##### 常用画法

因为 名称和简介相对于图片或者整个头部居中，所以用LinearLayout 再包裹了一层，因为月亮是网络图片或者多套图片只能通过转drawable 设置到文本上。

```
<RelativeLayout
    android:paddingEnd="10dp"
    android:paddingStart="10dp"
    android:background="@color/colorAccent"
    android:layout_width="match_parent"
    android:layout_height="105dp">

    <ImageView
        android:id="@+id/image_head"
        android:layout_width="65dp"
        android:layout_height="65dp"
        android:layout_centerVertical="true"
        android:layout_marginRight="10dp"
        android:src="@drawable/ic_action_name" />
    <LinearLayout
        android:layout_toLeftOf="@+id/tv_home"
        android:layout_centerVertical="true"
        android:layout_toRightOf="@id/image_head"
        android:orientation="vertical"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
        <TextView
            android:lines="1"
            android:drawableRight="@drawable/ic_baseline_brightness_2_24"
            android:id="@+id/tv_user_name"
            android:text="这个是名称呢这个是名称呢这个是名称呢这个是名称呢这个是名称呢"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
        <TextView
            android:layout_marginTop="10dp"
            android:id="@+id/tv_user_info"
            android:textSize="10sp"
            android:text="这个是用户简介呢"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
    </LinearLayout>
    <TextView
        android:paddingLeft="10dp"
        android:id="@+id/tv_home"
        android:gravity="center"
        android:drawableRight="@drawable/ic_baseline_arrow_right_24"
        android:drawablePadding="5dp"
        android:layout_centerVertical="true"
        android:layout_alignParentRight="true"
        android:text="个人主页"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
</RelativeLayout>
```

但是不通过drawable 实现了，我想要月亮图是一个图片。

```
<RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="105dp"
    android:background="@color/colorAccent"
    android:paddingStart="10dp"
    android:paddingEnd="10dp">

    <ImageView
        android:id="@+id/image_head"
        android:layout_width="65dp"
        android:layout_height="65dp"
        android:layout_centerVertical="true"
        android:layout_marginRight="10dp"
        android:src="@drawable/ic_action_name" />

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_toLeftOf="@+id/tv_home"
        android:layout_toRightOf="@id/image_head"
        android:orientation="vertical">
        <LinearLayout
            android:orientation="horizontal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <TextView
            		android:maxWidth="200dp"
                android:id="@+id/tv_user_name"
                android:layout_width="0dp"
                android:layout_weight="1"
                android:layout_height="wrap_content"
                android:lines="1"
                android:text="这个是名称呢这个是名称呢这个是名称呢这个是名称呢这个是名称呢" />
            <ImageView
                android:src="@drawable/ic_baseline_brightness_2_24"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
        </LinearLayout>
       
        <TextView
            android:id="@+id/tv_user_info"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:text="这个是用户简介呢"
            android:textSize="10sp" />
    </LinearLayout>

    <TextView
        android:id="@+id/tv_home"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_centerVertical="true"
        android:drawableRight="@drawable/ic_baseline_arrow_right_24"
        android:drawablePadding="5dp"
        android:gravity="center"
        android:paddingLeft="10dp"
        android:text="个人主页" />
</RelativeLayout>
```

这么一看，卧槽，为了一个图标，又嵌套了一层LinearLayout。但是换成ConstraintLayout呢？

##### 使用 ConstraintLayout

> 通过定义辅助线，控制文本居中对齐，通过控制view的相对位置，控制月亮的显示，

```
<androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="105dp"
        android:background="@color/colorAccent"
        android:paddingStart="10dp"
        android:paddingEnd="10dp">
        <androidx.constraintlayout.widget.Guideline
            android:layout_width="0dp"
            android:id="@+id/guide_center"
            app:layout_constraintGuide_percent="0.5"
            android:orientation="horizontal"
            android:layout_height="0dp"/>
        <ImageView
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            android:id="@+id/image_head"
            android:layout_width="65dp"
            android:layout_height="65dp"
            android:layout_centerVertical="true"
            android:layout_marginRight="10dp"
            android:src="@drawable/ic_action_name" />

        <TextView
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintBottom_toBottomOf="@id/image_head"
            app:layout_constraintTop_toTopOf="@id/image_head"
            android:id="@+id/tv_home"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentRight="true"
            android:layout_centerVertical="true"
            android:drawableRight="@drawable/ic_baseline_arrow_right_24"
            android:drawablePadding="5dp"
            android:gravity="center"
            android:paddingLeft="10dp"
            android:text="个人主页" />


        <TextView
            android:gravity="center"
            app:layout_constraintWidth_max="220dp"
            app:layout_constraintLeft_toRightOf="@id/image_head"
            app:layout_constraintBottom_toTopOf="@id/guide_center"
            android:lines="1"
            android:drawableRight="@drawable/ic_baseline_brightness_2_24"
            android:id="@+id/tv_user_name"
            android:text="qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
        <TextView
            android:lines="1"
            app:layout_constraintRight_toLeftOf="@id/tv_home"
            app:layout_constraintLeft_toRightOf="@id/image_head"
            app:layout_constraintTop_toTopOf="@id/guide_center"
            android:id="@+id/tv_user_info"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="这个是用户简介呢这个是用户简介呢这个是用户简介呢"
            android:textSize="10sp" />

    </androidx.constraintlayout.widget.ConstraintLayout>
```

如果月亮是多套图标。

```
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="105dp"
    android:background="@color/colorAccent"
    android:paddingStart="10dp"
    android:paddingEnd="10dp">

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guide_center"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.5" />

    <ImageView
        android:id="@+id/image_head"
        android:layout_width="65dp"
        android:layout_height="65dp"
        android:layout_centerVertical="true"
        android:layout_marginRight="10dp"
        android:src="@drawable/ic_action_name"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_home"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_centerVertical="true"
        android:drawableRight="@drawable/ic_baseline_arrow_right_24"
        android:drawablePadding="5dp"
        android:gravity="center"
        android:paddingLeft="10dp"
        android:text="个人主页"
        app:layout_constraintBottom_toBottomOf="@id/image_head"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="@id/image_head" />

    <LinearLayout
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintBottom_toTopOf="@id/guide_center"
        app:layout_constraintLeft_toRightOf="@id/image_head"
        app:layout_constraintRight_toLeftOf="@id/tv_home">

        <TextView
            android:maxWidth="220dp"
            android:id="@+id/tv_user_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:lines="1"
            android:text="qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_baseline_brightness_2_24" />
    </LinearLayout>

    <TextView
        android:id="@+id/tv_user_info"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:lines="1"
        android:text="这个是用户简介呢这个是用户简介呢这个是用户简介呢"
        android:textSize="10sp"
        app:layout_constraintLeft_toRightOf="@id/image_head"
        app:layout_constraintRight_toLeftOf="@id/tv_home"
        app:layout_constraintTop_toTopOf="@id/guide_center" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

上面的那个版本 还有一个linearlayout,突然想到可以设置最大宽度。

```
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="105dp"
    android:background="@color/colorAccent"
    android:paddingStart="10dp"
    android:paddingEnd="10dp">

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guide_center"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.5" />

    <ImageView
        android:id="@+id/image_head"
        android:layout_width="65dp"
        android:layout_height="65dp"
        android:layout_centerVertical="true"
        android:layout_marginRight="10dp"
        android:src="@drawable/ic_action_name"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_home"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_centerVertical="true"
        android:drawableRight="@drawable/ic_baseline_arrow_right_24"
        android:drawablePadding="5dp"
        android:gravity="center"
        android:paddingLeft="10dp"
        android:text="个人主页"
        app:layout_constraintBottom_toBottomOf="@id/image_head"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="@id/image_head" />

   

        <TextView
            app:layout_constraintBottom_toTopOf="@id/guide_center"
            app:layout_constraintLeft_toRightOf="@id/image_head"
            app:layout_constraintWidth_max="220dp"
            android:id="@+id/tv_user_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:lines="1"
            android:text="qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" />

        <ImageView
            app:layout_constraintBottom_toBottomOf="@id/tv_user_name"
            app:layout_constraintTop_toTopOf="@id/tv_user_name"
            app:layout_constraintRight_toRightOf="@id/tv_user_name"
            android:layout_width="wrap_content"
            android:layout_height="0dp"
            android:src="@drawable/ic_baseline_brightness_2_24" />
    <TextView
        android:id="@+id/tv_user_info"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:lines="1"
        android:text="这个是用户简介呢这个是用户简介呢这个是用户简介呢"
        android:textSize="10sp"
        app:layout_constraintLeft_toRightOf="@id/image_head"
        app:layout_constraintRight_toLeftOf="@id/tv_home"
        app:layout_constraintTop_toTopOf="@id/guide_center" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
