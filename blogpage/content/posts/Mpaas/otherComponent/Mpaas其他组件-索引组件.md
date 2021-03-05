+++
date = "2021-3-3"
title = "Mpaas其他组件-索引组件"
slug = "dgg"
series = ["Mpaas"]
featured = true
+++
##导入包
> mpaas 提供Android studio 插件，在插件中选择使用。
````aidl
// ui 库 
 api 'com.mpaas.android:antui'
 
 https://help.aliyun.com/document_detail/71741.html?spm=a2c4g.11186623.6.1506.29b84e8a480Z08
````
## 正文
````aidl
 <com.alipay.mobile.antui.basic.AUBladeView
        android:layout_width="24dp"
        android:layout_height="wrap_content"
        app:top1Text="⊙"
        app:top2Text="オ"/>
````

## 自定义属性

| 属性名        | 说明                                     | 类型      |
| :------------ | :--------------------------------------- | :-------- |
| top1Text      | 自定义第一个文本字符                     | reference |
| top2Text      | 自定义第二个文本字符                     | reference |
| showSelectPop | 是否显示滑动或者点击过程中中间弹出的浮层 | boolean   |

### 监听

```aidl
 /**
     * 设置字母选中监听
     */
        public void setOnItemClickListener(OnItemClickListener listener) 
        public interface OnItemClickListener {
        /**
         * 设置字母选中监听
         * @param clickChar 点击或者选中的字母
         */
        void onItemClick(String clickChar);
        /**
         * 手指抬起的事件，无特殊需求，无需关注此方法
         */
        void onClickUp();
    }
```
#### 分栏
> * 默认加载数据为a到z#。
> * 可以添加两个头，在a 之前。
> * 默认文本应该是12号，#999999的颜色值。
