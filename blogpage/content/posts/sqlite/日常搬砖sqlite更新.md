+++
date = "2020-10-01"
title = "Android sqlite更新"
tags = ["sqlite"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
+++
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dcf76e3e7a1646aab6cf921dfffd3a45~tplv-k3u1fbpfcp-zoom-1.image)

## 前言

顺便推一下[日常搬砖sqlite目录](https://juejin.im/post/6868506837000388615) 

这个目录里面有sqlite 初始化 增删改查等一些东西，我们就接着这个系列讲数据库更新，之前我们数据库更新判断是通过版本号进行的。而且有一个问题哈，如果我有1，2，3，4，5，共5个版本，通过为了分别应对每个版本，onUpgrade这个方法里面要写很长的东西，简单粗暴的方式当然就是用户强制升级到最新版本，那么我们就只需要处理最新版本的事情了，问题是每一次升级不都是当前最新版本吗？开发阶段中，通过数据库版本号升级要写一大堆逻辑，卸载的化数据又没有了。于是比较懒的我就开始思考一个问题，我每次用之前检测一次是否一致不就好了？那么就需要准备需要的理论知识了、

* 系统仅在需要时才执行可能需要长时间运行的数据库创建和更新操作，而不是在应用启动期间执行。您仅需调用 `getWritableDatabase()` 或 `getReadableDatabase()` 即可。（这个获取运行时）
* sqlite 的表不支持直接重命名表名。如果想要更改表的名字，需要将原表名设置一个临时表名，然后通过新表名创建表，然后删除零时表。
* sqlite 只支持对表增加字段。如果需要删除字段，修改字段名，修改字段对应的类型，都需要通过创建新表，然后复制对应的数据到新表，再删除原表达到效果，这么一想，向表中增加字段也可以通过上面操作进行。
* sqlite有一个sqlite_master 表，通过这个表可以查找所以表的表名，创建sql语句等。
* sqlite 可以查看表的信息和字段 通过 PRAGMA table_info(student)  语句。
* cursor.getColumnNames()。

## 正文

通过上面的理论依据，我觉得下面的理论是可行的。

1. 在获取SQLiteDatabase 之后立即运行一次更新检测逻辑。

2. 将创建表的sql语句通过list 或者map 存储起来。

3. 通过sqlite_master获得表名和创建语句

4. 通过创建sql与sqlite_master获得的创建sql语句对比，同时优先判断是否有创建的表名。
5. 如果没有则创建表，如果有比对创建sql是否一致。如果创建sql不一致，将原表名重命名为临时表，然后通过创建表，将原表中的数据通过ContentValues取出，赋值到创建表中，然后删除临时表。
6. 这么写有一个逻辑问题，如果表中的一个字段修改类型之后，可能导致插入失败。[建议参考 日常搬砖sqlite可用类型与存储类型](https://juejin.im/post/6868510436254777357/)，所以不建议对字段修改类型，汉语博大精深换一个名字我觉得是可以的。

如果忽略6这一点，那么我们的sqlite 启动检查更新的逻辑还是行得通的，毕竟只是对表名和表创建sql进行比对，这个还是很快的。当然性能肯定没有版本号更新好，但是胜在简单粗暴。`ps(这只是一种思路，我孤陋寡闻的，反正我的没有见过别人这么写过，毕竟性价比太低了，还是应该老老实实的更新版本号的)`那么就可以开整。

### 通过sqlite_master 获取表名和创建sql

```
public Map<String,String> getAllTabs() {
    Map<String,String> map=new HashMap<>();
    String sql = " SELECT * FROM sqlite_master WHERE type = 'table'";//PRAGMA table_info(student) 这个也可以查询表信息。但是只能针对某一个知道表名的表。
    Cursor cursor = db.rawQuery(sql, null);
    while (cursor.moveToNext()) {
        map.put(cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("sql")));
    }
    return map;
}
```

还有一些其他参数的，因为没有用到就没有列出来，需要的自己打断点或者log 通过 cursor.getColumnNames() 获取包含字段。

### 获取创建表的表名

如果通过map 存储，那么key 一般就是表名就可以忽略。主要是通过去除C 左括号右边的字符串，删除 中可能包含的 create table if not exists 字段，然后去除空格实现的。应该正则表达式也可以实现，容我往后面排一下日程。

### 比对两个创建sql是否一致

因为创建表的时候 里面可能有 if not exists ，我在打印创建好的表的创建sql的时候发现里面没有if not exists 但是我创建sql里面是有这个的，所以需要先置换掉 if not exists，还有一些常见约束等等，因为我没有尝试完，所以这个先放一边，我们假设 创建表的字段约束只有 primary key autoincrement，NOT NULL。只需要去除 if not exists和空格，然后将大小写转化一致，然后通过equals 就可以判断两个字符串是否相同了。

### 获取表中的数据

这个需要全部获取出来，所以直接是SELECT * FROM  tabname.

```
public List<ContentValues> getDatasByTabname(String tabName) {
    List<ContentValues> maps = new ArrayList<>();
    String sql = "SELECT * FROM " + tabName;
    Cursor cursor = db.rawQuery(sql, null);
    while (cursor.moveToNext()) {
        ContentValues values = new ContentValues();
        LogUtils.e(cursor.getColumnNames());
        for (String key : cursor.getColumnNames()) {
            int index = cursor.getColumnIndex(key);
            int type = cursor.getType(index);
            if (type == Cursor.FIELD_TYPE_STRING) {
                //按照string的取法。
                values.put(key, cursor.getString(index));
            } else if (type == Cursor.FIELD_TYPE_BLOB) {
                values.put(key, cursor.getBlob(index));
            } else if (type == Cursor.FIELD_TYPE_FLOAT) {
                values.put(key, cursor.getFloat(index));
            } else if (type == Cursor.FIELD_TYPE_INTEGER) {
                values.put(key, cursor.getInt(index));
            } else {
                //表示是空就不取。
            }
        }
        maps.add(values);
    }
    cursor.close();
    return maps;
}
```



### 对表重命名和删除表

```
String reNameSql = "ALTER TABLE '" + tabName + "' RENAME TO '" + tabName + "1'";
db.execSQL("DROP TABLE " + tabName + "1");
```

上面一句话的意思是将tabName 更改为 tabName+1,表名临时有效。

下面一句话就是删除tabName+1的表。

### 下面是更新所有代码

```
public class UpDateDao {
    SQLiteDatabase db;
    List<String> allTabs;

    public UpDateDao(SQLiteDatabase db, List<String> allTabs) {
        this.db = db;
        this.allTabs = allTabs;
    }

    /**
     * 开始更新。
     */
    public void init() {
        Map<String, String> dbtabs = getAllTabs();
        LogUtils.e(dbtabs);

        for (String creat_tab_sql : this.allTabs) {
            String tabName = getTabNameBySql(creat_tab_sql);
            LogUtils.e(tabName);
            if (dbtabs.containsKey(tabName)) {
                //因为要保持原表名，所以将+1的名字删除，通过将原表名重命名后，将新表命名为原表名的操作实现表的更新。
                //在这里判断原表是否有数据。如果没有数据就直接删除原表，并且将新表重名未 原表名。
                if (comparedSql(creat_tab_sql,dbtabs.get(tabName))){
                    //表示需要创建的表和已经存在的表是一样的。
                }else {
                    String reNameSql = "ALTER TABLE '" + tabName + "' RENAME TO '" + tabName + "1'";
                    db.execSQL(reNameSql);
                    db.execSQL(creat_tab_sql);
                    List<ContentValues> contentValues = getDatasByTabname(tabName);
                    for (ContentValues values : contentValues) {
                        long insert = db.insert(tabName, null, values);
                        LogUtils.e(insert == -1 ? "插入失败" + new Gson().toJson(values) : "插入成功");
                    }
                    //然后删除原表
                    db.execSQL("DROP TABLE " + tabName + "1");
                }
            } else {
                //表示当前表不存在，需要创建。那么创建的就是最新的表。
                db.execSQL(creat_tab_sql);
            }
        }

    }



    /**
     * 去除其他无关内容对比两个创建表的字符串是否相等。
     * @param creat_tab_sql
     * @param dbsql
     */
    private boolean comparedSql(String creat_tab_sql, String dbsql) {
        String sq1 = removeIrrelevant(creat_tab_sql).toLowerCase();
        String sq2 = removeIrrelevant(dbsql).toLowerCase();
        if (sq1.isEmpty()||sq2.isEmpty()){
            return false;
        }else {
            return sq1.equals(sq2);
        }
    }

    /**
     * 去除无关的内容。
     * @param creatTabSql
     * @return
     */
    private String removeIrrelevant(String creatTabSql) {
        if (creatTabSql.length()>0){
            creatTabSql=creatTabSql.replace("if not exists","");
            creatTabSql=creatTabSql.replace(" ","");
            return creatTabSql.trim();
        }
        return "";
    }

    public List<ContentValues> getDatasByTabname(String tabName) {
        List<ContentValues> maps = new ArrayList<>();
        String sql = "SELECT * FROM " + tabName;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            LogUtils.e(cursor.getColumnNames());
            for (String key : cursor.getColumnNames()) {
                int index = cursor.getColumnIndex(key);
                int type = cursor.getType(index);
                if (type == Cursor.FIELD_TYPE_STRING) {
                    //按照string的取法。
                    values.put(key, cursor.getString(index));
                } else if (type == Cursor.FIELD_TYPE_BLOB) {
                    values.put(key, cursor.getBlob(index));
                } else if (type == Cursor.FIELD_TYPE_FLOAT) {
                    values.put(key, cursor.getFloat(index));
                } else if (type == Cursor.FIELD_TYPE_INTEGER) {
                    values.put(key, cursor.getInt(index));
                } else {
                    //表示是空就不取。
                }
            }
            maps.add(values);
        }
        cursor.close();
        return maps;
    }

    public void showAllTabs() {
        //通过table 查询，因为一般存储为tab ,也会查询出 android_metadata 等于说写不写 没有多少区别。
        String sql = " SELECT * FROM sqlite_master WHERE type = 'table'";//PRAGMA table_info(student) 这个也可以查询表信息。但是只能针对某一个知道表名的表。
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            List<String> tab = new ArrayList<>();
            tab.add(cursor.getString(cursor.getColumnIndex("name")));
            tab.add(cursor.getString(cursor.getColumnIndex("tbl_name")));
            tab.add(cursor.getString(cursor.getColumnIndex("type")));
            tab.add(cursor.getInt(cursor.getColumnIndex("rootpage")) + "");
            tab.add(cursor.getString(cursor.getColumnIndex("sql")));
            LogUtils.e("当前所以表" + tab);
        }

    }

    public Map<String,String> getAllTabs() {
        Map<String,String> map=new HashMap<>();
        String sql = " SELECT * FROM sqlite_master WHERE type = 'table'";//PRAGMA table_info(student) 这个也可以查询表信息。但是只能针对某一个知道表名的表。
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            map.put(cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("sql")));

        }
        return map;
    }

    /**
     * 通过创建表的sql获取到表名
     *
     * @param sql
     * @return
     */
    private String getTabNameBySql(String sql) {
        sql = sql.replace(SQLTab.create_tab_heard, "");
        String[] split = sql.split("\\(");
        if (split.length >= 2) {
            String tab_name = split[0];
            if (tab_name.length() > 1) {
                return tab_name.trim();//去除前后空格
            }
            return null;
        }
        return null;
    }
}
```

### 运行位置

因为我的SQLiteOpenHelper 是单例模式。所以 更新是在构造方法中。

####  ps(这只是一种思路，我孤陋寡闻的，反正我的没有见过别人这么写过，毕竟性价比太低了，还是应该老老实实的更新版本号的)

但是如果结合版本号，还是可以在正式版上运行的吧。



## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。

