+++
date = "2020-10-01"
title = "Android sqlite增删改查"
tags = ["sqlite"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
+++

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dcf76e3e7a1646aab6cf921dfffd3a45~tplv-k3u1fbpfcp-zoom-1.image)

## 前言

[日常搬砖sqlite目录](https://juejin.im/post/6868506837000388615) 再目录中提到了一些东西，有兴趣可以点过去看一下，觉得还行麻烦点下赞。

## 正文

### 第一步创建数据库

再目录文档中，引用了官方文档的一段话 *`SQLiteOpenHelper` 类包含一组用于管理数据库的实用 API。当您使用此类获取对数据库的引用时，系统仅在需要时才执行可能需要长时间运行的数据库创建和更新操作，而不是在应用启动期间执行。您仅需调用 `getWritableDatabase()` 或 `getReadableDatabase()` 即可*。

* 第一步应该是创建数据库文件。通过上面的引用可以知道 我们创建 数据库文件只需要 继承 SQLiteOpenHelper即可。而SQLiteOpenHelper的构造函数需要传入context，name，factory,version.通常情况下，我们创建数据库的时候只需要context，name，version，name 确定数据库文件的名字，version用于判断创建或更新数据库。

* 系统仅在需要时才执行可能需要长时间运行的数据库创建和更新操作，所以我们的创建表的sql语句是可以装到一个list 中的，当然也可以存放到一个map 中，通过表名+创建表的sql。只要我们不获取SQLiteDatabase 数据库就不会创建更新。

*  *由于在数据库关闭时，调用 `getWritableDatabase()` 和 `getReadableDatabase()` 的成本比较高，因此只要您有可能需要访问数据库，就应保持数据库连接处于打开状态* * ，我们决定使用单例。

* 在onCreate 中通过execSQL();创建表。

  ### 所以整个 SQLiteOpenHelper 可以长这个样子：

  ​	

  ```
  public class SqliteDBHelper extends SQLiteOpenHelper {
      public static final String DATABASE_NAME = "base.db";
      public static final int DATABASE_VERSION = 1;//版本号
      private static volatile SqliteDBHelper helper = null;
      List<String> allTabs = new ArrayList<>();//这个存放所有的最新的SQL表。不同版本用户创建时候直接创建所以表。
  
      private SqliteDBHelper(Context context) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
          initAllSql();
          /*UpDateDao dao = new UpDateDao(getReadableDatabase(), allTabs);
          dao.init();*/
      }
  
      private void initAllSql() {
          allTabs.add(SQLTab.create_tab_ledger); //创建账本表。
          allTabs.add(SQLTab.create_tab_investment);//投资表
          allTabs.add(SQLTab.create_tab_income);//收入表
          allTabs.add(SQLTab.create_tab_expenses);//消费表
          allTabs.add(SQLTab.create_tab_owing);//债务表
          allTabs.add(SQLTab.create_tab_repayment); //创建还债表。
          allTabs.add(SQLTab.crart_tab_search_history);//创建历史搜索表
      }
  
      public static SqliteDBHelper getInstance() {
          if (helper == null) {
              synchronized (SqliteDBHelper.class) {
                  if (helper == null) {
                      helper = new SqliteDBHelper(Utils.getApp());
                  }
              }
          }
          return helper;
      }
  
      @Override
      public void onCreate(SQLiteDatabase db) {
          for (String tab : allTabs) {
              db.execSQL(tab);
          }
      }
  
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         /* UpDateDao dao = new UpDateDao(db, allTabs);
          dao.init();*/
      }
  }
  ```

  至于为啥要用list 把创建表sql 存储起来，主要是便于后期更新操作。

  

## 使用表

```  CREATE TABLE ledger ( _id Integer  primary key autoincrement , type varchar(200) NOT NULL ,type_id Integer NOT NULL,password varchar(200) NOT NULL , ledger_name varchar(200) NOT NULL , show Integer NOT NULL)```

### 插入数据

因为插入数据相对简单，create_tab_ledger 使用这个表做介绍。插入语句  db.insert(FeedEntry.TABLE_NAME, null, values); TABLE_NAME是对应的表的表名。

```java
ContentValues values = new ContentValues();
values.put(FeedEntry.COLUMN_NAME_TITLE, title);
values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

// Insert the new row, returning the primary key value of the new row
long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
```

newRowId 是插入后返回的行数，如果返回-1，表示插入失败。



### 获取数据

db.query() 这个方法需要传入的参数比较多，但是很详细，不太适合简单粗暴的写法的我，就直接附上google提供的写法。

#### google写法

如需从数据库中读取信息，请使用 `query()` 方法，向其传递您的选择条件和所需的列。该方法合并了 `insert()` 和 `update()` 元素，不过列列表定义了要提取的数据（“预测值”），而不是要插入的数据。查询结果会包含在 `Cursor` 对象中返回给您。

```java
SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
String[] projection = {
    BaseColumns._ID,
    FeedEntry.COLUMN_NAME_TITLE,
    FeedEntry.COLUMN_NAME_SUBTITLE
    };

// Filter results WHERE "title" = 'My Title'
String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
String sortOrder =
    FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

Cursor cursor = db.query(
    FeedEntry.TABLE_NAME,   // The table to query
    projection,             // The array of columns to return (pass null to get all)
    selection,              // The columns for the WHERE clause
    selectionArgs,          // The values for the WHERE clause
    null,                   // don't group the rows
    null,                   // don't filter by row groups
    sortOrder               // The sort order
    );
List itemIds = new ArrayList<>();
while(cursor.moveToNext()) {
  long itemId = cursor.getLong(
      cursor.getColumnIndexOrThrow(FeedEntry._ID));
  itemIds.add(itemId);
}
cursor.close();
```

第三个参数和第四个参数（`selection` 和 `selectionArgs`）会合并起来创建一个 WHERE 子句。由于这两个参数是与选择查询分开提供的，因此它们在合并之前会进行转义。这样一来，您的选择语句就不受 SQL 注入的影响。如需详细了解所有参数，请参阅 `query()` 参考。

如需查看光标中的某一行，请使用 `Cursor` move 方法之一，您必须始终在开始读取值之前调用该方法。由于光标从位置 -1 开始，因此调用 `moveToNext()` 会将“读取位置”置于结果的第一个条目上，并返回光标是否已经过结果集中的最后一个条目。对于每一行，您都可以通过调用 `Cursor` get 方法之一（例如 `getString()` 或 `getLong()`）读取列的值。对于每个 get 方法，您必须传递所需列的索引位置，您可以通过调用 `getColumnIndex()` 或 `getColumnIndexOrThrow()` 获取该位置。遍历结果之后，请对光标调用 `close()` 以释放其资源。例如，以下代码展示了如何获取存储在光标中的所有项目 ID 并将其添加到列表中：

```java
List itemIds = new ArrayList<>();
while(cursor.moveToNext()) {
  long itemId = cursor.getLong(
      cursor.getColumnIndexOrThrow(FeedEntry._ID));
  itemIds.add(itemId);
}
cursor.close();
```

#### 个人偏好原生sql写法

直接通过db. rawQuery(查询sql,null)获得Cursor。

##### 直接获取全部

```
SELECT * from 表名
```

 ##### 排序

```
SELECT * from 表名 ORDER BY _id desc （这个的意思通过 ID 排序）
 order by XX DESC 降序排列
  order by XX ASC 升序排列
```

##### 条件查询

一个条件：

SELECT * from 表名 WHERE  条件 排序

多个条件

SELECT * from 表名 where 条件 and 条件 排序

分页查询：

SELECT * from 表名 where 条件 and 条件 排序 limit 查询个数 offset 开始位置

分页查询获取获取总数和页数:

```
SELECT count(*) FROM 表名 +条件，cursor.getCount() 获取返回个数
```

获取corsor 中包含的字段:

cursor.getColumnNames(),如果查询的是列表，那么需要循环获取哦。

通过Cursor 对象获取查询出来的参数。

```
while (cursor.moveToNext()) {
	cursor.getString(cursor.getColumnIndex("name"))；因为基础数据类型不同，外层获取方法不同，但是cursor.getColumnIndex("name")写法应该是一样的，但是如果当前ColumnName 中没有这个字段，那么会抛出异常
}
```

#### 更新

```java
// New value for one column
String title = "MyNewTitle";
ContentValues values = new ContentValues();
values.put(FeedEntry.COLUMN_NAME_TITLE, title);

// Which row to update, based on the title
String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
String[] selectionArgs = { "MyOldTitle" };

int count = db.update(
    FeedReaderDbHelper.FeedEntry.TABLE_NAME,
    values,
    selection,
    selectionArgs);
```

#### 删除

```java
// Define 'where' part of query.
String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
// Specify arguments in placeholder order.
String[] selectionArgs = { "MyTitle" };
// Issue SQL statement.
int deletedRows = db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
```

emmmm? 我还是喜欢通过sql语句进行删除。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。