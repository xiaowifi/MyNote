+++
date = "2020-10-01"
title = "Android sqlite前言"
tags = ["sqlite"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
+++
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dcf76e3e7a1646aab6cf921dfffd3a45~tplv-k3u1fbpfcp-zoom-1.image)

## 前言

话说现在已经2020年了，在Android 应用端上混混沌沌的搬砖已经3年多快4年了。之前也迷迷糊糊的写了些什么，但是却没有一个好一点的系列文章。大多数同行的评论都是"什么都懂一点，但是什么都不精"，还是蛮想改变这个现状的。所以打算认真写一个系列出来，因为最近在复习sqlite,想从各个大佬的项目中找到自己的不足。所以打算写一个sqlite的理解系列吧。

## sqlite 通用（Android）

* Android 系统内嵌了sqlite，所以使用sqlite的时候并不需要导入jar或者aar 文件或者连接池等。

* Android 中通过 SQLiteDatabase及其子类对表的创建，删除，改名，对内容的增删改查操作。同时可以获取版本号等其他操作。

* Android 上使用sqlite是可以更改 sqlite 存在的位置的，但是默认是/data/data/PACKAGE NAME/DB NAME目录*（就像您在设备的[内部存储空间](https://developer.android.google.cn/guide/topics/data/data-storage?hl=zh_cn#filesInternal)中保存文件一样，Android 会将您的数据库存储在您应用的私有文件夹中。您的数据安全无虞，因为在默认情况下，其他应用或用户无法访问此区域。）*。

* sqlite的更新和创建是在获取SQLiteDatabase的时候执行的（这个可以通过打印log知道）官方文档中提到*（`SQLiteOpenHelper` 类包含一组用于管理数据库的实用 API。当您使用此类获取对数据库的引用时，系统仅在需要时才执行可能需要长时间运行的数据库创建和更新操作，而不是在应用启动期间执行。您仅需调用 `getWritableDatabase()` 或 `getReadableDatabase()` 即可。）*

* sqlite 的表不支持直接重命名表名。如果想要更改表的名字，需要将原表名设置一个临时表名，然后通过新表名创建表，然后删除零时表。

* sqlite 只支持对表增加字段。如果需要删除字段，修改字段名，修改字段对应的类型，都需要通过创建新表，然后复制对应的数据到新表，再删除原表达到效果，这么一想，向表中增加字段也可以通过上面操作进行。

* sqlite 更新是通过检测版本号进行的。

* sqlite有一个sqlite_master 表，通过这个表可以查找所以表的表名，创建sql语句等。

* sqlite 可以查看表的信息和字段 通过 PRAGMA table_info(student)  语句。

* Cursir 使用完之后建议 执行cursor.close(); 关闭游标，但是SQLiteDatabase 不建议在SQLiteOpenHelper 中执行close() 方法，关闭了再同对象中使用可能会抛异常。*同时再官方文档中提到（由于在数据库关闭时，调用 `getWritableDatabase()` 和 `getReadableDatabase()` 的成本比较高，因此只要您有可能需要访问数据库，就应保持数据库连接处于打开状态。通常情况下，最好在发出调用的 Activity 的 `onDestroy()` 中关闭数据库）*。

* 创建表的时候建议增加 if not exists 已有表重复+1创建或者创建时候出错。像这个样子create table if not exists 表名（表字段）

* 由于这些操作可能会长时间运行，因此请务必在后台线程中调用 `getWritableDatabase()` 或 `getReadableDatabase()`。如需了解详情，请参阅 [Android 上的线程](https://developer.android.google.cn/training/multiple-threads?hl=zh_cn)

* Android SDK 提供一个 `sqlite3` shell 工具，您可以使用该工具浏览表内容、运行 SQL 命令以及在 SQLite 数据库中执行其他实用操作。如需了解详情，请参阅如何[发出 shell 命令](https://developer.android.google.cn/studio/command-line/adb?hl=zh_cn#shellcommands)。

* **强烈建议**将 [Room 持久性库](https://developer.android.google.cn/training/basics/data-storage/room?hl=zh_cn)用作抽象层以访问应用的 SQLite 数据库中的信息。

* ```
  cursor.getColumnNames() 这个调调 获取当前所以字段，很重要。当然也可以打断点，不知道为啥我打断点代码到处跳。
  ```



### 外部文档

[Android google sqlite文档](https://developer.android.google.cn/training/data-storage/sqlite?hl=zh_cn#java)

[sqlite 教程 菜鸟教程](https://www.runoob.com/sqlite/sqlite-tutorial.html)



## 日常搬砖sqlite系列

 [日常搬砖sqlite基础的增删改查](https://juejin.im/post/6868507678935777288/)

[日常搬砖sqlite可用类型与存储类型]()

日常搬砖sqlite基础的增改查（换一种思路）

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。