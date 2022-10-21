+++
date = "2021-08-29"
title = "Android搬砖MotionLayout初探"
description = "Android搬砖MotionLayout初探"
categories = [
"android基础"
]
featured = false
draft = true 
+++


## 前言
* [google 提供github Demo地址 ](https://github.com/android/views-widgets-samples/tree/main/ConstraintLayoutExamples) 强烈建议自己看Demo。
> 参考demo。
## 正文
我们知道 MotionLayout继承于 ConstraintLayout。而android studio 可以直接通过 点击预览 右键 <img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210922150423.png" alt="image-20210922150423794" style="zoom:50%;" /> 直接生成 MotionLayout。 所以需要先把布局写好，然后转 MotionLayout 控制动画。

将ConstraintLayout 转为 MotionLayout  会自动在 res/xml 中生成一个 xml 文件，而动画主要是在xml 中配置。而layout和动画xml 关联 ：		app:layoutDescription="@xml/activity_motion1_scene" 

### MotionScene

这个是MotionLayout 动画的根布局。直接根布局

* Transition  动画相关的
* ConstraintSet 约束相关的
* StateSet  demo 里面没有，待定。

可操作属性：

* defaultDuration 默认的动画时长
*  layoutDuringTransition 过渡期间布局的相关设置    ignoreRequest和honorRequest  

####  Transition

直接根布局：

* OnSwipe 手势拖拽  
*  KeyFrameSet 关键帧 
* OnClick 点击事件？ 

可操作属性：

* constraintSetStart 定义开始的xml,
*  constraintSetEnd 定义结束的xml 
*  duration 动画时长
*  autoTransition 
*  
*  

  #### ConstraintSet 



## 结束

