````aidl
public void scrollToTop() {
    //解决到顶部无法滑动的问题
    mRecyclerView.scrollToPosition(0);
    //拿到 appbar 的 behavior,让 appbar 滚动
    ViewGroup.LayoutParams layoutParams = appBarLayout.getLayoutParams();
    CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
    if (behavior instanceof AppBarLayout.Behavior) {
        AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
        //拿到下方tabs的y坐标，即为我要的偏移量
        int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
        if (topAndBottomOffset != 0) {
            appBarLayoutBehavior.setTopAndBottomOffset(0);
            appBarLayout.setExpanded(true, true);
        }
    }
}
````