+++
date = "2021-9-6"
title = "androidDiffUtil的简单使用2"
description = "androidDiffUtil的简单使用2"
categories = [
"android基础"
]
featured = false
draft = true 
+++

## 前言
> 之前写了一个DiffUtil 简单使用的笔记，主要是基于DiffUtil 进行数组比较，获得添加，更新，删除，换位置等回调。
> 需要重写DiffUtil.Callback 方法，也需要获取DiffUtil.DiffResult去设置回调对象，同时也需要把新数据设置到adapter上。<br>
> 这种步骤虽然只有3步。但是Google期望他更加简单与简洁与recyclerview联动。于是便有了DiffUtil.ItemCallback。
> DiffUtil.ItemCallback 同样是需要依靠旧 新两个数组。然后获得了变更回调。但是这种回调却不需要我们去管理了。我们只需要针adapter设置
> DiffUtil.ItemCallback的重写类就行。

> Google 提供了两个更加便于联动的adapter

* ListAdapter 
* PagedListAdapter   
* PagingDataAdapter (这个调调paging版本不一样,操作不一样)
## 正文
### 重写 DiffUtil.ItemCallback
DiffUtil.ItemCallback 同样支持重写返回一个payload对象。
````aidl
public class UserDiffUtilItemCallBack extends DiffUtil.ItemCallback<UserBean> {
    @Override
    public boolean areItemsTheSame(@NonNull UserBean oldItem, @NonNull UserBean newItem) {
        return Objects.equals(oldItem.getId(),newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull UserBean oldItem, @NonNull UserBean newItem) {
        return Objects.equals(oldItem.getName(),newItem.getName());
    }
}
````
### 与ListAdapter联合使用
````aidl
public class UserListAdapter extends ListAdapter<UserBean, UserHolder> {
    public final static int NAME_CHANGE_PAYLOAD = 5;
    Context context;

    String TAG = "UserAdapter ";

    public UserListAdapter(Context context) {
        super(new UserDiffUtilItemCallBack());
        this.context = context;
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.textView.setText(getItem(position).getName() + "   " + getItem(position).getId());
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull List<Object> payloads) {
        //因为这个调调 无论如何都会调用 onBindViewHolder，所以这个需要判断下呢。
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            for (Object o : payloads) {
                int payload = (int) o;
                if (payload == NAME_CHANGE_PAYLOAD) {
                    holder.textView.setText(getItem(position).getName() + "   " + getItem(position).getId());
                }

            }
        }
        Log.e(TAG, "onBindViewHolder: " + payloads.toString());

    }


}
````
#### 增加
adapter.submitList(UserDebugTools.getDebugs());
#### 加载更多
List<UserBean> all = new ArrayList<>();
all.addAll(adapter.getCurrentList());
all.addAll(UserDebugTools.getDebugs());
adapter.submitList(all);
#### item刷新
adapter.getCurrentList().get(0).setName("变更后呢 + " + System.currentTimeMillis());
//提供一个变化后的标记 payload 过去。用于标记变化呢。通过循环拿到这个变化
adapter.notifyItemChanged(0, NAME_CHANGE_PAYLOAD);
#### 移除
adapter.notifyItemRemoved(0);
### 与PagingDataAdapter 联合使用

## 结束


