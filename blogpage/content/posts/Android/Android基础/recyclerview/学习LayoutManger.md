# 前言
LayoutManager负责在 a 中测量和定位项目视图RecyclerView，并确定何时回收用户不再可见的项目视图的策略。通过改变LayoutManageraRecyclerView可以用来实现标准的垂直滚动列表、统一网格、交错网格、水平滚动集合等等。提供了几个库存布局管理器供一般使用。
如果 LayoutManager 指定了默认构造函数或带有签名 ( Context, AttributeSet, int, int) 的构造函数，则 RecyclerView 将在膨胀时实例化并设置 LayoutManager。然后可以从 获得最常用的属性getProperties。如果 LayoutManager 指定了两个构造函数，则非默认构造函数将优先。
## 资料
* [LayoutManager](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.LayoutManager)
# 正文
我们先从默认的子类开始学习。只有懂了系统提供的子类的实现方式，那么我们去自定义不规则列表就很容易了。

已知的直接子类[GridLayoutManager](https://developer.android.com/reference/androidx/leanback/widget/GridLayoutManager) , [LinearLayoutManager](https://developer.android.com/reference/androidx/recyclerview/widget/LinearLayoutManager) , [StaggeredGridLayoutManager](https://developer.android.com/reference/androidx/recyclerview/widget/StaggeredGridLayoutManager)

| `GridLayoutManager`          | 在网格中为 leanback和`RecyclerView.LayoutManager`布置项目的实现。`VerticalGridView``HorizontalGridView` |
| ---------------------------- | ------------------------------------------------------------ |
| `LinearLayoutManager`        | `RecyclerView.LayoutManager`提供与 . 类似功能的实现`android.widget.ListView`。 |
| `StaggeredGridLayoutManager` | 以交错网格形式布置子项的 LayoutManager。                     |

已知的间接子类[GridLayoutManager](https://developer.android.com/reference/androidx/recyclerview/widget/GridLayoutManager) , [WearableLinearLayoutManager](https://developer.android.com/reference/androidx/wear/widget/WearableLinearLayoutManager)

| `GridLayoutManager`           | `RecyclerView.LayoutManager`在网格中布置项目的实现。         |
| ----------------------------- | ------------------------------------------------------------ |
| `WearableLinearLayoutManager` | 这种特定于 wear 的实现`LinearLayoutManager`为更新子布局提供了基本的偏移逻辑。 |

## LinearLayoutManager

```
public class LinearLayoutManager extends RecyclerView.LayoutManager implements
        ItemTouchHelper.ViewDropHandler, RecyclerView.SmoothScroller.ScrollVectorProvider {}
```

通过上面的代码 我们可以知道，他继承了 RecyclerView.LayoutManager，同时实现了ItemTouchHelper.ViewDropHandler, RecyclerView.SmoothScroller.ScrollVectorProvider。 

### LayoutManager的处理

通常而言，继承layoutManger 只需要实现 一个函数：generateDefaultLayoutParams

为RecyclerView的子级创建默认LayoutParams对象。

```
@Override
public RecyclerView.LayoutParams generateDefaultLayoutParams() {
    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
}
```

而LinearLayoutManger 全部采用的是子布局的高宽。

### ViewDropHandler 拖拽 

这个也只提供了一个需要实现的函数。prepareForDrop。而这个函数是ItemTouchHelper 调用：

recyclerView.onTouchEvent------> recyclerView 的dispatchToOnItemTouchListeners 函数------(当mInterceptingOnItemTouchListener不为空的时候)----->ItemTouchHelper.onTouchEvent----(如果action为ACTION_MOVE)--->onMoved---(如果onMove为true)----->ItemTouchHelper.onMoved----如果实现于ViewDropHandler--->prepareForDrop

熟悉基于Recycleview 就知道，这个调调其实是处理拖拽效果的。

### ScrollVectorProvider
* [google ScrollVectorProvider ](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.SmoothScroller.ScrollVectorProvider?hl=en)


## GridLayoutManager

## StaggeredGridLayoutManager
## WearableLinearLayoutManager

# 结束