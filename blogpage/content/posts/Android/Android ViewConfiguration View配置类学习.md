+++
date = "2021-11-29"
title = "Android下ViewConfiguration 的学习（1）"
description = "Android下ViewConfiguration 的学习（1）"
tags = [ "ViewConfiguration"]
categories = [
"android基础"
]
featured = true
draft = false
+++
## 正文
````aidl
final ViewConfiguration configuration = ViewConfiguration.get(context);
//  获取最小的滑动距离。
 this.touchSlop = configuration.getScaledTouchSlop();
````
