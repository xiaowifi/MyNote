+++
date = "2020-12-28"
title = "SQL知识点汇总"
description = "SQL知识点汇总"
tags = [ "sql"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
# 前言
> 最近写Demo的时候，经常发现自己对于sql好多都忘记了。

# 简单使用
* SELECT  查询 后面接查询字段
* SELECT DISTINCT 查询 去重
* WHERE 多条件查询，用and 连接
* and or 多条件查询拼接
* order by 对结果进行排序，默认是升序，ORDER BY DESC 降序，ORDER BY 多列（这个多列不是太懂）
* INSERT INTO 插入数据
* UPDATE 更新数据
* DELETE 删除数据
# 进阶
* SELECT TOP, LIMIT, ROWNUM 用于规定返回记录的条目数
* LIKE like查询
* 通配符
    * % 替代0个或者多个字符
    * '-' 替代一个字符
    * [charlist] 字符列中的任何单一字符
    * [^charlist]
      或
      [!charlist] 不在字符列中的任何单一字符
* in 查询，有点类型 数组中的那个 indexof ,可传入同一个字段的多个值
* BETWEEN 用于取两个值之间的范围值 两个值用 and 连接
* NOT BETWEEN 不在范围内
* 别名 AS 比如说 name as n ,就是将name设置为 n,
* JOIN 将两个或者多个表结合起来，基于共同字段。
* INNER JOIN 查询条件满足的行
* LEFT JOIN  从左表（table1）返回所有的行，即使右表（table2）中没有匹配。如果右表中没有匹配，则结果为 NULL。
* RIGHT JOIN 关键字从右表（table2）返回所有的行，即使左表（table1）中没有匹配。如果左表中没有匹配，则结果为 NULL。
* FULL OUTER JOIN 关键字只要左表（table1）和右表（table2）其中一个表中存在匹配，则返回行. FULL OUTER JOIN 关键字结合了 LEFT JOIN 和 RIGHT JOIN 的结果。
* UNION 操作符合并两个或多个 SELECT 语句的结果
* SELECT INTO 语句从一个表复制数据，然后把数据插入到另一个新表中。
* INSERT INTO SELECT 语句从一个表复制数据，然后把数据插入到一个已存在的表中。
* CREATE DATABASE 语句用于创建数据库。
* CREATE TABLE 语句用于创建数据库中的表。
* 约束
    * NOT NULL - 指示某列不能存储 NULL 值
    * UNIQUE - 保证某列的每行必须有唯一的值。
    * PRIMARY KEY - NOT NULL 和 UNIQUE 的结合。确保某列（或两个列多个列的结合）有唯一标识，有助于更容易更快速地找到表中的一个特定的记录。
    * FOREIGN KEY - 保证一个表中的数据匹配另一个表中的值的参照完整性。
    * CHECK - 保证列中的值符合指定的条件。
    * DEFAULT - 规定没有给列赋值时的默认值。
* CREATE INDEX 语句用于在表中创建索引。
* ALTER TABLE 语句用于在已有的表中添加、删除或修改列。
* Auto-increment 会在新记录插入表中时生成一个唯一的数字。
* IS NULL 是空
* IS NOT NULL 不是空
* Aggregate 函数计算从列中取得的值，返回一个单一的值。
    * AVG() - 返回平均值
    * COUNT() - 返回行数
    * FIRST() - 返回第一个记录的值
    * LAST() - 返回最后一个记录的值
    * MAX() - 返回最大值
    * MIN() - 返回最小值
    * SUM() - 返回总和
*  Scalar 函数基于输入值，返回一个单一的值
    * UCASE() - 将某个字段转换为大写
    * LCASE() - 将某个字段转换为小写
    * MID() - 从某个文本字段提取字符，MySql 中使用
    * SubString(字段，1，end) - 从某个文本字段提取字符
    * LEN() - 返回某个文本字段的长度
    * ROUND() - 对某个数值字段进行指定小数位数的四舍五入
    * NOW() - 返回当前的系统日期和时间
    * FORMAT() - 格式化某个字段的显示方式
* GROUP BY 语句用于结合聚合函数，根据一个或多个列对结果集进行分组。
* HAVING 子句原因是，WHERE 关键字无法与聚合函数一起使用。HAVING 子句可以让我们筛选分组后的各组数据。
* EXISTS 运算符用于判断查询子句是否有记录，如果有一条或多条记录存在返回 True，否则返回 False。










