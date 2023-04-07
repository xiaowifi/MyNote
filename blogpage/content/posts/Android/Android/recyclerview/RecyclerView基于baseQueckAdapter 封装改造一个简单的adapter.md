> 当前基于网络上的一个adapter [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper) 进行进行代码移出与添加。

主要解决下面问题 
* 当简单实用diff刷新的时候，会出现item刷新与diff尾部刷新同时调用的情况。出现无法try cache 的情况。
* 删除部分不需要的功能。
* 针对头部，尾部，空布局 提供刷新回调机制。


