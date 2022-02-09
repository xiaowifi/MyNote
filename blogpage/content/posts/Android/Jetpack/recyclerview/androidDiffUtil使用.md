+++
date = "2021-9-6"
title = "androidDiffUtil的简单使用"
description = "androidDiffUtil的简单使用"
categories = [
    "android基础"
]
featured = false
draft = true 
+++
## 前言
> diffUtil 是Google 提供的一个便于使用的数组数据对比工具，一般用于两个数组数据进行对比差异，比如插入，更新，移除，位置变更等回调都是支持的。
> 通常和recyclerview的adapter 联合使用。主要是提供一种数据刷新的方案，优化列表刷新，添加，移除等相关代码吧。

参考资料：
* [Google DiffUtil文档 ](https://developer.android.google.cn/reference/androidx/recyclerview/widget/DiffUtil.html?hl=de)
## 使用
### 使用
> 这个调调主要是数据变更计算，返回 （增加，删除，移动，位置变更）等回调。所以这个调调的使用也是比较简单的。
#### 1.基于对比对象实现DiffUtil 接口
> 这个调调的比较顺序。
* 先比较两个对象的关键字段，比如说ID。
* 如果关键字段相同则比较其他字段。如果不同则不比较其他字段。

示例代码
````aidl
public class SavvyTabDiffUtil extends DiffUtil.Callback {
    List<ServerTab> oldItems;
    List<ServerTab> newItems;

    public SavvyTabDiffUtil(List<ServerTab> oldItems, List<ServerTab> newItems) {
        this.oldItems = oldItems;
        this.newItems = newItems;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldItems.get(oldItemPosition).getId(), newItems.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldItems.get(oldItemPosition).getName(), newItems.get(newItemPosition).getName());
    }
}
````
如果要结合recyclerview使用可以重写getChangePayload 方法，这个方法默认返回空，关键字段相同，剩余字段不同的时候，就会调用这个方法，用于返回一个自定义用于标记数据变更的字段，用于做局部刷新，如果说返回空的话，就直接刷新整个item。

使用:
* 设置数据获得对比对象。需要注意工具类会保留保留最新的数据列表。
````aidl
 DiffUtil.DiffResult result = DiffUtil.calculateDiff(new SavvyTabDiffUtil(tabNAdapter.getData(), serverTabs), true);
````
* 设置接收回调对象
````aidl
 result.dispatchUpdatesTo();// 如果结合adapter使用需要传入adapter。否则就传入 ListUpdateCallback。
````
> 如果传入adapter，内部则会自动调用 adapter的各种刷新方法，如果传入ListUpdateCallback 则会把各种逻辑的回调返回。<br>
> 如果传入了adapter 需要在dispatchUpdatesTo之后，将最新的数据设置到adapter中。
### 与recyclerview 联合使用 
> 这个相对简单一点，主要是结合recyclerview的刷新机制与方法，传入adapter 内部自动调用刷新方法。

adapter的常用刷新方法：
* adapter.notifyItemRangeInserted(position, count);
* adapter.notifyItemRangeRemoved(position, count);
* adapter.notifyItemMoved(fromPosition, toPosition);
* adapter.notifyItemRangeChanged(position, count, payload);
> 当然了，还有一个最为简单粗暴的：notifyDataSetChanged();

上面的代码中，有一个 getChangePayload() 则是返回 payload。 这个是不是和正常刷新的item调用的notifyItemChanged(int position, @Nullable Object payload)需要传入的payload很像，
而事实上他也是调用了这个的刷新。如果实现了getChangePayload()，adapter中就需要实现adapter的onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull List<Object> payloads).
通过循环payloads拿到对应的 payload，从而实现局部刷新。

### 数组数据对比
> 这个应用场景比较少，或者比较常见，我们通常采用了其他的思路去解决。比如说一个界面中显示的fragment是通过服务器数组控制的，我们通过数据的ID或者code 去查找对应的fragment。并且显示出来。
> 本地可以编辑（增加，删除，移动，位置变更）服务器返回的数组，那么同样的，对应的fragment列表就需要实现同样的逻辑。常用的解决思路 大概是 拿到最新的数组，先去查找已有的fragment数组中是否已经
> 创建了对应的fragment，如果存在就将对应的fragment放置到对应的下标，如果不存在就创建新的fragment，如果最后包含未被使用的fragment，就销毁。流程大概是这个样子的。当我们基于DiffUtil开发的时候，就可以
> 基于数据对比结果的 （增加，删除，移动，位置变更）去刷新局部了，而不是刷新整体。

上面的与adapter联合使用，所以dispatchUpdatesTo() 传入的是adapter。当我们需要拿到数据的变更逻辑的时候就需要传入 ListUpdateCallback的实现类了。
*  void onInserted(int position, int count);
*  void onRemoved(int position, int count);
*  void onMoved(int fromPosition, int toPosition);
*  void onChanged(int position, int count, @Nullable Object payload);
> 我们就需要通过上面的逻辑去刷新列表了。这里面的方法可能会回调多次，比如说，下标为5的位置，我先移除原来的，然后添加一个新的，那么会先走移除，再添加。
> 或者几个item的位置互相变换，onMoved也会回调多次。
## 结束
> DiffUtil 主要是提供一种刷新思路和一种约束好的刷新行为方式，他的对比算法应该算是比较有效的了，这个比自己去写对比要容易很多。


