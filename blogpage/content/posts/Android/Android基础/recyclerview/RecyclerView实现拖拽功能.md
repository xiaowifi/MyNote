# 前言
列表实现拖拽改变位置功能，在某些场景下十分常见了。recycleview 提供了非常简单的API。便于我们调用。
## 资料
* [recyclerView 拖拽简单Demo ](https://blog.csdn.net/qq_40987010/article/details/118388009)
# 正文 
那我们直接开整。首先先明确需要的class;
* RecyclerView.Adapter 列表渲染的adapter
* RecyclerView view本体 
* ItemTouchHelper.Callback 用于用户操作及其处理回调的。
* ItemTouchHelper 将RecyclerView与ItemTouchHelper.Callback 进行关联。

通过上面的信息，我们可以知道，最重要的就是ItemTouchHelper.Callback了。
## ItemTouchHelper.Callback 
ItemTouchHelper.Callback 是一个abstract class 。提供了需要自己实现的函数。
* getMovementFlags 用户操作状况控制，如果返回0就是没有拖拽了，比如只能拖拽某些部分什么的，都是在这里控制
  * makeMovementFlags( ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.END)
* onMove  当用户正在拖拽子view的时候调用。
* onSwiped  当用户正在滑动子view的时候调用。
* isItemViewSwipeEnabled 表述是否支持滑动。如果为FALSE的时候，拖拽到时候就没法滑动了。
* isLongPressDragEnabled 表示支付支持长按拖拽。
* onSelectedChanged 当被拖动或者被滑动的ViewHolder改变时调用，actionState会返回当前viewHolder的状态，有三个值：在这个方法我们可以对View进行一些UI的更新操作，例如当用户拖动时，让View高亮显示等。
  * ACTION_STATE_SWIPE：当View刚被滑动时返回
  * ACTION_STATE_DRAG：当View刚被拖动时返回
  * ACTION_STATE_IDLE：当View即没被拖动也没被滑动时或者拖动、滑动状态还没被触发时，返回这个状态
* clearView 当View被拖动或滑动完后并且已经结束了运动动画时调用，我们可以在这里进行UI的复原，例如当View固定位置后，让View的背景取消高亮。
* convertToRelativeDirection 
* getDefaultUIUtil
* getMaxDragScroll
* makeFlag
* makeMovementFlags
* canDropOver
* chooseDropTarget
* convertToAbsoluteDirection
* getAbsoluteMovementFlags
* getAnimationDuration
* getBoundingBoxMargin
* getMoveThreshold
* getSwipeEscapeVelocity
* getSwipeThreshold
* getSwipeVelocityThreshold
* hasDragFlag
* hasSwipeFlag
* interpolateOutOfBoundsScroll
* onChildDraw
* onChildDrawOver
* onDraw
* onDrawOver
## 代码实例 
````aidl
        MyItemDropHelper dropHelper=new MyItemDropHelper();
        ItemTouchHelper touchHelper=new ItemTouchHelper(dropHelper);
        touchHelper.attachToRecyclerView(recyclerDrop);
````
# 结束 