+++
date = "2020-10-01"
title = "SQL server 创建和修改数据库"
description = "SQL server 创建和修改数据库"
tags = [ "数据库"]

series = ["专升本"]
featured = false
draft = true 

+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

#### 数据库定义
数据库是按照一定的数据模型组织起来并存放在存储介质中的数据集合。他是用来组织和存取大量数据大管理系统。

## 正文

#### 数据库对象

- 表 table 是包含数据库中所有数据的数据库对象，由行和列构成。
- 视图 view  是由一个表或者多个表导出的表，又称为虚拟表。
- 索引 index 为了加快数据检索速度并可以保证数据唯一性的数据结构。
- 存储过程 stored procedure 为了完成特定功能的T SQL 语句集合，编译后存放于服务器端的数据库中
- 触发器 trigger 他是一种特定的存储过程，当某个规定的事件发生时该存储过程自动执行。

#### 系统数据库

sql Server 在安装时创建了4个系统数据库，master，model，msdb,tempdb.当系统数据库被破坏时sql Server 将不能正常启动工作。

- master 数据库：记录sqlServer系统信息，如登录账号，系统配置，数据库位置，数据库错误信息。用于控制用户数据库和sqlServer 的运行。
- model 数据库为创建数据库提供模板。
- msdb 代理服务数据库，为调度信息，作业记录等提供存储空间。
- tempdb 临时数据库，为临时表和临时存储过程提供存储空间。

#### 物理数据库

页和区是sql Server 的两个主要的数据存储单位。

- 页 每个页是 8KB,每1 MB 可以容纳128页。页是数据存储的基本单位。
- 区 每8个连接的页组成一个区，区的大小是64KB,1MB 的数据库有16个区，区用于控制表和索引的存储。