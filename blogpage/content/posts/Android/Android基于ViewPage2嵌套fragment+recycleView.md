+++
date = "2021-11-09"
title = "Android基于ViewPage2嵌套fragment+recycleView"
description = "Android基于ViewPage2嵌套fragment+recycleView"
tags = [ "viewpager2"]
categories = [
"android基础"
]
featured = true
draft = false
+++
## 前言
> 2021年11月9日，接到一个新的砖，做一个仿照京东的分类列表。要求上拉和下拉的时候，没有加载头，可以直接切换过去。
> 左右两个列表做联动。每个分类下都数据 分开请求。拿到设计稿的第一时间的想法是，通过viewpager2设置竖向滚动，嵌套fragment，fragment嵌套recyclerView。
> 
## 正文
