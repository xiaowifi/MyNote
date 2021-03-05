+++
date = "2021-3-3"
title = "Mpaas其他组件-操作条组件"
slug = "dgg"
series = ["Mpaas"]
featured = true

+++
##导入包
> mpaas 提供Android studio 插件，在插件中选择使用。
````aidl
// ui 库 
 api 'com.mpaas.android:antui'
````
## AUCardOptionView 

```
<com.alipay.mobile.antui.basic.AUCardOptionView
        android:id="@+id/card_option_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="50dp"/>
```

控件需要代码添加。类型固定，包含，点赞，打赏，评论。 默认是基于父布局均分的。参数私有，无法通过继承等方式增加类型，图标更改应该还是在主题设置中配置。

```
public static final java.lang.String TYPE_PRAISE = "praise";
public static final java.lang.String TYPE_REWARD = "reward";
public static final java.lang.String TYPE_COMMENT = "comment";
```

// 创建一个子view。

````
  AUCardOptionView.CardOptionItem optionItem1 = new AUCardOptionView.CardOptionItem();
        optionItem1.type = AUCardOptionView.TYPE_PRAISE;
        optionItem1.hasClicked = false;
````

#### 参数设置

```
// 这个可以使文本保持不变。 比如说点赞过了，但是文本还是点赞两个字。
optionView.setViewInfo(optionItems,AUCardOptionView.TEXT_NOT_CHANGE);
```

```
// 这个会计数。没有点赞数的时候显示赞，有赞的时候显示数量。
optionView.setViewInfo(optionItems);
```

````
 optionView.setViewInfo(optionItems,false);// 这个传入true的时候。和上面一样会显示数量，当为false的时候，只有图标、
````

> 参数设置一共有4种方法。

```
AUCardOptionView.CardOptionItem.count 基础数量，默认是0
AUCardOptionView.CardOptionItem.hasClicked 是否被点击过了
AUCardOptionView.CardOptionItem.official  这个值暂时没有找到用处。
```

