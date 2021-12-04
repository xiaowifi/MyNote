### 窗体中的view 的绘制流程

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20211201212605.png)

* performTraversals
* MeasureSpec的三种测量模式
  ![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20211201214552.png)

### 自定义viewGroup

> 需要实现 3个构造方法。2个入参的构造方法 为必须传入。还需要实现下列方法。

* onMeasure
    * 在这个中获取到的测量模式为当前自己的测量模式。MeasureSpec.getMode，但是获取的宽高是父容器提供的宽高参考值。
    * 如果自定义viewGroup 需要被使用为根容器，就需要像其他viewGroup 一样，在onMeasure 中 通过measureChildWithMargins 计算子view的测量模式。 否则子view
      就不会显示正常，这个也是 自定义viewGroup 在 onMeasure 中可以获取到自己的测量模式的原因。
    * setMeasuredDimension 传入 最新的测量模式。因为要计算子view 的宽高
* onLayout
    * 这个里面主要是对子view 的绘制位置 进行 设置。参考值由 onMeasure 中计算出来。
    * 针对子控件进行摆放是比较消耗性能的。
        * 子控件的刷新。
        * 父布局高宽变化。

* onInterceptTouchEvent
  > 由于有最小滑动 距离，所以在这里 将最小滑动距离 认定为点击事件。
  > 处理后，在由onTouchEvent 处理。ViewConfiguration 可以获取到最小滑动距离。
  > 所以说，这个调调 优先于onTouchEvent 执行。
* onTouchEvent
  > 这个里面做滑动处理。
  > 为了做惯性滑动。在当前方法中获取到滚动到加速度。 VelocityTracker.obtain();
* scrollBy 滚动
  > scrollBy 属于增量滚动。基于现在的位置。滚动的是画布。 滚动的是canvas，子view 还是在原来的位置上，只是看起来是滚动了的，所以对viewGroup而言，滚动后需要对子view 进行重新摆放。
  > scrollTo 是滚动到屏幕都绝对位置。
* SparseArray 



































