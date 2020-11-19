+++
date = "2020-10-01"
title = "T-SQL与SQL基础"
description = "T-SQL与SQL基础"
tags = [ "数据库"]
categories = [
    "杂学"
]
series = ["专升本"]
featured = true

+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

#### 数据库定义
数据库是按照一定的数据模型组织起来并存放在存储介质中的数据集合。他是用来组织和存取大量数据大管理系统。

## 正文

SQL语言是关系数据库管理的标准语言，不同的数据库管理系统在SQL 语言基础上进行扩展。T-SQL 是SQL Server 在SQL的基础上增加控制语句和系统函数的扩展。

SQL是应用于数据库的结构化查询语言，是一种非过程性语言，本身不能脱离数据库存在。由数据库系统完成具体的内部操作。      

工具使用:DataGrip：https://www.jetbrains.com/datagrip/              

### 创建数据库

#### T-SQL

这个是普通的创建。这个和SQL 创建是一样的。

```
CREATE DATABASE 数据库名;
```

其他字段内容创建：

```
create database 数据库名;
on(
name="数据库名",filename="文件存储路径",size=300mb,filecrowth=1mb
)
log on (
name="log文件名",filename="log文件地址",size=30mb,maxsize=1--mb,filegroeth=10%
)
```

- 数据库 最多128个字符。
- on 子句，指定数据库文件名和文件组属性。
- log on 子句 指定log 文件属性。
- Filespec  指定数据文件的属性，给出文件的逻辑名，存储路径，大小以及增长特性。就是on 和log on 的子句之一。
- name:filespec 定义的文件名。
- Filename :filespes 定义的文件路径。
- Size  filespes 定义的文件大小。
- maxsize filespec 定义的文件最大大小。
- filegrowth:指定 filespec 的文件增长量。

针对 数据库文件的增长量是绝对大小，比如说1MB,但是针对log 文件的增长量是百分比 比如说5%;

#### SQL 

在使用 my sql 中就没有其他字段内容。

```
CREATE DATABASE 数据库名;
```

### 修改数据库

通常修改数据名，存储位置，log 等。

#### T-SQL

 ````
alter database 数据库名
add file (filespec)或者
add log file (filespec)或者
remove file "删除指定的文件" 或者
modify file (filespes) 或者
modify name= name
 ````

- add file  使用 filespec 添加数据文件
- add log file 使用filespec 添加log文件
- remove file  使用filespec 删除指定的文件
- modify file 使用 file spec 更改指定文件的信息
- modify name 重命名数据。

因为电脑的问题，没法装sql server,所以 上面语句是一次只能执行一条还是所以都可以待确定。

#### SQL

好像不支持代码整什么的，我没有找到。

### 使用数据库

```` 
use database_name;
````

sql和T-sql没有多少区别。

### 删除数据库

````
drop database_name;
````

用得少，SQL和T-SQL 应该是一样的。

### TSQL创建表

````
create table 数据库名 架构名 表名
（字段，主键，外键，约束）
````

数据库名字不写默认是当前数据库，架构名不写 默认是dbo。感觉在基础应用上是差不多的，只是SQL 没有ON 中的对应部分。

### 删除表

````
drop table tablename;
````

Sql 和 T-sql 是一致的。

### 复制表

主要是复制从一个表中抽取一部分字段，生成一个新的表

````
select  列名 info 新表 from 列名所在的表
````

列名包含多个，用逗号隔开，* 是所以字段。没有尝试过，不知道他的复制表结构还是表结构和表中的内容，应该是表结构。这个应该sql 语句。

###  修改表

````
alter table tablename;
````

- alter column 修改表中对应列的属性，约束应该也是属性。

- add  增加 字段 列，包含约束。

- drop 删除表中的字段或者约束。

  没有用过，sqlte 特性导致只能增加字段，删除字段不行。这个也应该是sql 语句。

### 插入数据

插入数据的对象可能是 表，视图，部分函数（）

````
insert into 表名|视图|函数 
````

插入数据受 列序列影响，如果不设置列 则需要按照sql表中的字段 一一传入。

### 修改数据

根据某个条件，修改表中对应条件达成后对应的数据。应该没有现在条件查询出来的对象的唯一性吧。

````
update 表名|视图名 set 修改的字段="修改的内容" 条件
````

修改多个字段用逗号隔开。

### 删除数据

````
delete 表名|视图名 条件
````

如果没有条件就是删除所有数据。

### 查询

涉及到太多条件查询。单独开一个文档写。

