
## 前言

fragment懒加载到处都可以搜索到。2021年7月26 在调试本地fragment懒加载的时候，发现未达到效果，搜索懒加载相关代码的时候，偶然发现android X下的懒加载写法。
故记录一波。
### 参考资料
* Google 在 Androidx 在 FragmentTransaction 中增加了 setMaxLifecycle 方法来控制 Fragment 所能调用的最大的生命周期函数。
* 在 FragmentPagerAdapter 与 FragmentStatePagerAdapter 新增了含有 behavior 字段的构造函数。如果 behavior 的值为 BEHAVIOR_SET_USER_VISIBLE_HINT，那么当 Fragment 对用户的可见状态发生改变时，setUserVisibleHint 方法会被调用。
  如果 behavior 的值为 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ，那么当前选中的 Fragment 在 Lifecycle.State#RESUMED 状态 ，其他不可见的 Fragment 会被限制在 Lifecycle.State#STARTED 状态。
* [参考内容:android X下的fragment 懒加载](https://github.com/AndyJennifer/AndroidxLazyLoad)
* ViewPager2 本身就支持对实际可见的 Fragment 才调用 onResume 方法。(所以要用viewpager 2 而不是viewpager,好的，马上改代码)
## 正文
所以说，基于上面的参考资料。可以通过 setMaxLifecycle在 onResume上面做懒加载？

## 结束


