+++
date = "2021-7-27"
title = "RecycleView中的Item刷新diffutil联合使用"
description = "RecycleView中的Item刷新diffutil联合使用"
categories = [
"android基础"
]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
> recyclerview 的item刷新会闪一下，而且 有的时候 只有一点点细微的改动，我们期望不刷新整个item去实现，比如说点赞的状态等，这个是比较简单的刷新操作，另外一种就是服务器列表数据更新了某个状态，而界面上的
> 变更只有一点点，比如说还是点赞，一种是完全依托服务器数据，一种是依托服务器数据状态，刷新整个item是可以实现效果的，但是和我们的目不一样，或者一个视频播放列表，刷新了item就导致视频缓存没有了或者视频从头开始播放。
> 或者是一个列表中包含多个列表图片，刷新整个item，会导致图片重复加载等等，所以我们期望刷新局部而不是刷新整体。<br>

 常用的解决思路大概就这么几种。
 * 通过代码比对值的变更，通过item 然后找到view进行更改，这种是最简单，但是是最乱的一种方式。
 * 结合notifyItemChanged 刷新，传入 payload，重写onbindviewholder 3个参数的方法，对payload 进行判断。
 * databinding 的绑定模式。
 * diffutil 这个也是结合payload 进行处理的。只是说这个提供了一个接口，内部自己计算，相对于自己写更加简单，而且这个自己写刷新全部，<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210803133935.jpg" style="zoom:35%;" />

这几种模式都是基于逻辑上的数据变更去实现的，只是diffutil看起来代码更加规范。databinding 的状态变更刷新反而看起来更加简单。
<br>
可能还是不是太适应databinding 的写法吧，主要是用得少，用得简单，所以个人意愿上是比较倾向于diffutil 一些的，但是diffutil 代码量其实比较多的。写好还是蛮不容易的，完全依赖逻辑去控制界面刷新渲染，没有注释而言，接盘是痛苦的。

参考资料：
* [Google DiffUtil文档 ](https://developer.android.google.cn/reference/androidx/recyclerview/widget/DiffUtil.html?hl=de)
* [recyclerview](https://developer.android.google.cn/guide/topics/ui/layout/recyclerview)
* [csdn diffutil 教程](https://blog.csdn.net/zxt0601/article/details/52562770)
## 正文
> 既然都是基于recyclerview 实现刷新，recyclerview 对刷新的支持就尤为重要。
### recyclerView 
#### 刷新
##### notifyItemChanged 刷新某个item
* position item对应的位置。
* payload object 类型。这个传值或者干其他的都行，diffutil的getChangePayload返回对象就是这个值，这个值默认为null。
##### notifyDataSetChanged 刷新所有
> 这个默认就是刷新所有，没有入参什么的。
##### notifyItemRangeInserted 
> 插入刷新 
##### notifyItemRangeRemoved 
> 移除刷新 
##### notifyItemMoved 
> 移动刷新
##### notifyItemRangeChanged 
>  item刷新。notifyItemChanged就是调用这个方法。

* position
* count 这个值默认值是1，
* payload 这个值默认值为空。
#####  diffutil 
>  它会自动计算新老数据集的差异，并根据差异情况，自动调用以下四个方法.（抄的，觉得他有道理，暂时没有验证）
* adapter.notifyItemRangeInserted(position, count);
* adapter.notifyItemRangeRemoved(position, count);
* adapter.notifyItemMoved(fromPosition, toPosition);
* adapter.notifyItemRangeChanged(position, count, payload);
#### 数据绑定

![image-20210803115209742](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210803115209.png)

如图所示，recyclerview调用 绑定的时候，会优先调用 3个入参的方法，然后内部调用两个入参的方法。payload 是存在的。默认就调用3个参数的构造方法。结合notifyItemChanged  中的 payload ，只要我们刷新的时候，判断提供这个值，那么局部刷新是可以通过onbindviewHolder 去实现的。通常的时候，我们只需要实现 onbindviewholder 两个入参的方法就行。

既然 diffutil   会自动调用notify方法，那么我们就直接写重写onbindviewholder 就行了是吧。因为notifyItemChanged  我们可以传入一个payload，那么刷新全部的时候，是怎么调用的呢？

## DiffUtil

> 这个调调，主要是干啥的？个人感觉就是进行数据对比的，列表数据对比，对象数据对比，而且他只是提供接口，但是结合官网信息。

> DiffUtil 是一个实用程序类，用于计算两个列表之间的差异并输出将第一个列表转换为第二个列表的更新操作列表。
>
> 它可用于计算 RecyclerView 适配器的更新。查看`ListAdapter`和 `AsyncListDiffer`可以简化在后台线程上使用 DiffUtil 的方法。
>
> DiffUtil 使用 Eugene W. Myers 的差分算法来计算将一个列表转换为另一个列表的最少更新次数。Myers 的算法不处理移动的项目，因此 DiffUtil 对结果运行第二遍以检测移动的项目。
>
> 请注意 DiffUtil、`ListAdapter`和`AsyncListDiffer`要求列表在使用时不发生变化。这通常意味着不应直接修改列表本身及其元素（或至少，差异中使用的元素的属性）。相反，应在内容更改时提供新列表。传递给 DiffUtil 的列表共享未变异的元素是很常见的，因此并不严格要求重新加载所有数据以使用 DiffUtil。
>
> 如果列表很大，此操作可能需要大量时间，因此建议您在后台线程上运行此操作，`DiffUtil.DiffResult`然后将其应用到主线程上的 RecyclerView 上。
>
> 该算法针对空间进行了优化，并使用 O(N) 空间来查找两个列表之间的最少添加和删除操作次数。它具有 O(N + D^2) 预期时间性能，其中 D 是编辑脚本的长度。
>
> 如果启用移动检测，则需要额外的 O(MN) 时间，其中 M 是添加项目的总数，N 是删除项目的总数。如果您的列表已经按相同的约束排序（例如为帖子列表创建时间戳），您可以禁用移动检测以提高性能。[文档数据参考来源](https://developer.android.google.cn/reference/androidx/recyclerview/widget/DiffUtil.html?hl=de)

果然，肤浅的是我，我只是把他用来刷新列表了。感觉有点深奥，没事，看起来牛逼就行，用起来好用也行。

### 使用

> 当我们确定先用再去理解他的时候，就不需要知道他内部实现，只要可以用，无脑摁回车就行。只有好用的时候，才会去理解体会他的内在，是吧，又不是没有替换方案。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20210803135522.png" style="zoom:33%;" />

根据官方文档介绍，diffutil 大致提供了下面几种能力。

* 用于计算两个列表之间的差异并输出将第一个列表转换为第二个列表的更新操作列表（感觉有点复杂，对比两个列表，先对比长度，然后是关键字段，然后是其他字段，其他字段不一致返回一个payload对象，可以通过拿到payload 对象的自定义内容刷新部分view），需要实现的接口：[DiffUtil.Callback](https://developer.android.google.cn/reference/androidx/recyclerview/widget/DiffUtil.Callback?hl=de) 

* 用于计算列表中两个非空项目之间差异（这个比第一个更容易理解，就是两个对象进行对比，他提供的功能上可以对比关键内容，如果关键内容一致，对比其他内容，如果其他内容不一致返回一个payload 对象，可以通过拿到payload 对象的自定义内容刷新部分view ）需要实现的接口：[DiffUtil.ItemCallback](https://developer.android.google.cn/reference/androidx/recyclerview/widget/DiffUtil.ItemCallback?hl=de)<T>

> 至于是怎么计算的，有点复杂。

### [DiffUtil.Callback](https://developer.android.google.cn/reference/androidx/recyclerview/widget/DiffUtil.Callback?hl=de)

* getOldListSize 获取老数据的长度
* getNewListSize 获取新数据的长度

* areItemsTheSame 判断两个数组同一个位置的对象是否相同，一般比对id 或者什么的。

* areContentsTheSame 如果areItemsTheSame 返回true,则表示对象相同，这个方法才会被调用，这个比对其他字段更改。如果没有更改就是true

* getChangePayload 当 areContentsTheSame 返回false的时候这个方法才会被调用，表示对象相同，但是对象的值有更改，这个地方如果需要局部刷新，就需要返回自定义更改对象，否则就需要返回 null，返回null的时候，会刷新整个item，结合recyclerview的onbindviewholder方法，他默认就是刷新整个item。所以需要自己判断下呢。

  

#### 使用





### [DiffUtil.ItemCallback](https://developer.android.google.cn/reference/androidx/recyclerview/widget/DiffUtil.ItemCallback?hl=de)<T>



## 结束

