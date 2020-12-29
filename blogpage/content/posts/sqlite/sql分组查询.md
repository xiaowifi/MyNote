+++
date = "2020-12-29"
title = "sql分组查询"
description = "sql分组查询"
tags = [ "sql"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true

+++
> 当前笔记复制来源于：https://www.cnblogs.com/friday69/p/9389720.html 

![image-20201229102221800](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/image-20201229102221800.png)

## 1.SQL分组查询GroupBy+Group_concat

group by 是分组，是分组，是分组，分组并不是去重，而是分组

将查询结果按一个或多个进行分组，字段值相同的为一组

GroupBy+Group_concat ： 表示分组之后，根据分组结果，使用 group_contact() 来放置每一组的每字段的值的集合

```mysql
select deparmant, GROUP_CONCAT(`name`) from employee GROUP BY deparmant
```

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113348163-1636260185.jpg)

根据 department 分组，通过 group_concat（'name'）,查看每组里面的姓名都有哪些



```sql
SELECT gender,GROUP_CONCAT(`name`) from employee GROUP BY gender
```

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113348163-1636260185.jpg)

根据gender 分类，看 不同的 性别都有哪些 人

分组注意事项： 在分组时，select后面跟的的字段一般都会出现在 group by 后

```sql
SELECT name,gender from employee GROUP BY gender,name
-- 先按gender分组，再按姓名分组...
```

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113348163-1636260185.jpg)



------





## 2.SQL分组+聚合函数

```sql
select deparmant, GROUP_CONCAT(salary), SUM(salary),AVG(salary) 平均工资,MAX(salary) 最高工资 from employee GROUP BY deparmant;
-- 根据department 分组，计算各部门下工资总数，平均工资，最高工资![1532919789347](D:\Python\python_learning\Python_Blog\02\SQL\4.png)
```

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113348163-1636260185.jpg)





```sql
-- 查询每个部门的部门名称以及每个部门的人数
SELECT deparmant, GROUP_CONCAT(`name`), COUNT(*) from employee GROUP BY deparmant
```

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113355515-1523520780.png)





```sql
-- 查询每个部门的部门名称以及每个部门工资大于1500的人数
SELECT deparmant,GROUP_CONCAT(salary), COUNT(*) from employee WHERE salary > 1500 GROUP BY deparmant
```

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113419253-1886423671.png)



## 3.SQL分组GroupBy+Having

- group by + having 用来分组查询后指定一些条件来输出查询结果
- having 和 where 一样，但 having 只能用于 group by

```sql
-- 查询工资总和大于 9000的部门名称
SELECT deparmant, GROUP_CONCAT(salary), SUM(salary) FROM employee 
GROUP BY deparmant 
HAVING SUM(salary) > 9000;
```

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113410466-1750182046.png)



**having 和 where 的区别：**

- having 是在分组后对数据进行过滤，where 是在分组前对数据进行过滤
- having后面可以使用分组函数(统计函数)，where后面不可以使用分组函数
- where 是对分组前记录的条件，如果某行记录没有满足where字句的条件，那么这行记录不会参加分组；而having是对分组后数据的约束





```sql
-- 查询工资大于2000的，工资总和大于9000的部门名称以及工资和
select deparmant,GROUP_CONCAT(salary), SUM(salary) from employee 
WHERE salary > 2000 
GROUP BY deparmant 
HAVING sum(salary) > 9000
ORDER BY SUM(salary) DESC;
```

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113432874-1174371851.png)



## 4.sql语句书写顺序

![img](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/1387811-20180730113439953-1881145557.png)

