---
title: "{{ replace .Name "-" " " | title }}" # 博客文章的标题。
date: {{ .Date }} # 帖子创建日期。
description: "Article description." # 用于搜索引擎的描述。
featured: true # 设置帖子是否为精选帖子，使其显示在侧边栏上。如果是当前页面，则不会在侧边栏中列出精选帖子
draft: true # 设置是否呈现此页面。将不会呈现 true 的草稿。
toc: false # 控制是否应自动为一级链接生成目录。
# menu: main
featureImage: "/images/path/file.jpg" #在博客文章上设置特色图片。
thumbnail: "/images/path/thumbnail.png" #设置出现在主页卡片内的缩略图。
shareImage: "/images/path/share.png" # 为社交媒体共享指定单独的图像。
codeMaxLines: 10 # 在自动折叠之前覆盖代码块内多少行的全局值。
codeLineNumbers: false #覆盖用于在代码块中显示行号的全局值。
figurePositionShow: true # 覆盖显示图形标签的全局值。
categories:
  - Technology
tags:
  - Tag_name1
  - Tag_name2
---

**Insert Lead paragraph here.**