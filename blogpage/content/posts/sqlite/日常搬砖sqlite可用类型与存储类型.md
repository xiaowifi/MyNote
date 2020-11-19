+++
date = "2020-10-01"
title = "Android基础数据类型与sqlite存储类型"
description = "Android基础数据类型与sqlite存储类型"
tags = [ "数据类型","sqlite"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
+++
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dcf76e3e7a1646aab6cf921dfffd3a45~tplv-k3u1fbpfcp-zoom-1.image)

## 前言

之前又一篇文章上讲到 cursor.getType(cursor.getColumnIndex(key))  可以获取到对应值的类型，通过cursor 类可以知道，cursor 就提供了4种类型。而ContentValues 定义了不止4总类型，sqlite的插入与更新都可以通过ContentValues 对象进行操作的。[目录地址](https://juejin.im/post/6868506837000388615) 

* cursor 定义了4种类型
* ContentValues 定义了不止4总类型
* sqlite 插入更新都可以通过 ContentValues 对象进行
* 问题1：sqlite  在创建表的时候是否支持所以的ContentValues 定义类型。
* 问题2:sqlite 支持的类型 对应了cursor中的哪些类型。
* 有一个教程博客写的很清晰[runoob SQLite 数据类型](https://www.runoob.com/sqlite/sqlite-data-types.html)

安利一个软件sqlitestudio。/data/data/PACKAGE NAME/ 是sqlite 默认存储路径。

## 正文

### 创建sqlite表

ContentValues 支持存放主要类型：String，Byte，Short，Integer，Long，Float，Double，Boolean，byte[]等。那么创建sql，我们知道 string 是通过varchar 存储。不知道直接写string 是否可以创建表。

````
 create table if not exists all_content_values ( _id Integer  primary key autoincrement , string_key String, byte_key Byte,short_key Short ,long_key Long, float_key Float,double_key Double,Boolean_key Boolean,byte_array byte[] )
````

if not exists 这个是用于防止表重复创建。

### 通过表名获取表中的字段

通过上面的创建表的表名 all_content_values

```
private void getTabInfoByName(String tab_name) {
    String sql="PRAGMA table_info("+tab_name+")";
    Cursor cursor = db.rawQuery(sql, null);
    LogUtils.e(cursor.getColumnNames());
    List<String> keys=new ArrayList<>();
    while (cursor.moveToNext()){
        keys.add(cursor.getString(cursor.getColumnIndex("name")));
    }
    LogUtils.e(keys);

}
//获取到的数据 keys
[_id, string_key, byte_key, short_key, long_key, float_key, double_key, Boolean_key, byte_array]
```

创建时候设定的字段: _id,string_key,byte_key,short_key,long_key,float_key,double_key,Boolean_key,byte_array.这么一对比，好像没有少字段，而且表好像还创建成功了，**emmmm? 那么我创建表的时候是不是可以直接用存了啊，不用把string 写成 varchar 了**。通过 sqlitestudio 也可以直接查看表结构

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bacb5efa457d4162b1b42dc0bcfbbab7~tplv-k3u1fbpfcp-zoom-1.image)

### 存储字段

[runoob JAVA 基础数据类型](https://www.runoob.com/java/java-basic-datatypes.html)   [目录地址](https://juejin.im/post/6868506837000388615)  [sqlite 增删改查](https://juejin.im/post/6868507678935777288)

存储数据通过 ContentValues  

```
ContentValues values=new ContentValues();
//因为int对应的是自增主键 所以不对int 进行赋值。
values.put("string_key","string_key");
values.put("byte_key",100);
values.put("short_key",50);
values.put("long_key",1l);
values.put("float_key",1f);
values.put("double_key",1d);
values.put("Boolean_key",true);
values.put("byte_array",new byte[]{100,50});
long insert = db.insert(SQLTab.tab_name_all_V, null, values);
```

当 insert 返回值不为-1的时候就插入插入成功了。

![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/eac0fbe89e504155b64056eff6741b62~tplv-k3u1fbpfcp-zoom-1.image)

### 将插入的数据获取出来

```
 private void getAllContentValues() {
        //直接查询所以字段
        String sql="SELECT * from "+SQLTab.tab_name_all_V;
        Cursor cursor = db.rawQuery(sql, null);
        ArrayMap<Integer,List<String>> types=new ArrayMap<>();
        while (cursor.moveToNext()){
            for (String key: cursor.getColumnNames()){
                int type = cursor.getType(cursor.getColumnIndex(key));
                if (types.containsKey(type)){
                    if (!types.get(type).contains(key)){
                        types.get(type).add(key);
                    }
                }else {
                    List<String> keys=new ArrayList<>();
                    keys.add(key);
                    types.put(type,keys);
                }

            }
        }
        LogUtils.e(new Gson().toJson(types));
    }
```

通过log打印 

{"1":["_id","byte_key","short_key","long_key","Boolean_key"],"2":["float_key","double_key"],"3":["string_key"],"4":["byte_array"]}

### cursor 中type与ContentValues 对应

cursor 定义类型中 FIELD_TYPE_NULL=0，FIELD_TYPE_INTEGER=1，FIELD_TYPE_FLOAT=2，FIELD_TYPE_STRING=3，FIELD_TYPE_BLOB = 4。那么 我们可以总结下。

* FIELD_TYPE_INTEGER =1 包含的类型有 int,byte,short，long，Boolean。
* FIELD_TYPE_FLOAT=2 包含的类型有： float，double。
* FIELD_TYPE_STRING=3 包含的类型有:string
* FIELD_TYPE_BLOB = 4 包含的类型有：byte []  

通过上面的关系，我们就可以通过反射将cursor 中的值赋值到应该class 对象上面去了。同时也可以cursor中的值转化为ContentValues 对象。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。

