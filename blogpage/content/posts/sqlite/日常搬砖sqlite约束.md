
## 前言

顺便推一下[日常搬砖sqlite目录](https://juejin.im/post/6868506837000388615) 。

接着上面的写，之前有写到使用sqlite 实现增删改查等简单使用。sqlite是一个关系型数据库，既然是数据库那么它就一定不会这么简单了，是吧，要不然就没有专门做数据库设计的岗位了<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200910104356.png" style="zoom:25%;" />。当然了在Android上的应用通常可能没有这么复杂。那么简单的约束还是可以了解下的。毕竟我们可能不会做出超出我们逻辑认知的操作。如果想要实现某个功能，那么知道当前工具是否可以是很有必要的吧。

## 正文

那么我们就直接上菜，Android 上无论是room 还是greendao，都是基于sqlite提供服务的，他们实现的sqlite 就必定有，是吧。我们知道一个表的设计包括 表名，字段名，字段名对应的类型（必须），字段名对应的其他约束。

###  类型

之前有一篇文章关于[sqlite可用类型与存储类型](https://juejin.im/post/6868510436254777357/). 在这里就不再次描述了。

### 约束

[runoob sqlite ](https://www.runoob.com/sqlite/sqlite-tutorial.html) 本文中大多数都是基于该教程，这个还是可以看一下的。[SQLite 约束](https://www.runoob.com/sqlite/sqlite-constraints.html) 

* primary key: 唯一标识数据库表中的各行/记录

* autoincrement :自增 通常用于 int类型的字段。

* NOT NULL:确保某列不能有 NULL 值

* DEFAULT :当某列没有指定值时，为该列提供默认值

* UNIQUE 约束：确保某列中的所有值是不同的.

* **CHECK 约束**：CHECK 约束确保某列中的所有值满足一定条件。

  

  算了，感觉自己写的没有别人提供的约束教程好，这个就当做笔记算了，直接查看 上面的教程吧。心态有点难受。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。