![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dcf76e3e7a1646aab6cf921dfffd3a45~tplv-k3u1fbpfcp-zoom-1.image)

## 前言

欢迎光临。

顺便推一下[日常搬砖sqlite目录](https://juejin.im/post/6868506837000388615) 。

现在还是主要总结一些简单一点的，太复杂了的还是往后排一下，反正我是记得我留了不少的坑等着填，现在先写这个吧，免得分心又拖更了。

之前有简单的描述了下sqlite的增删改查，google 还是推荐使用[room android ](https://developer.android.google.cn/training/data-storage/room/index.html)的。国内也有很多大佬写的很详细，比如说[Android Room 官方指南](https://blog.csdn.net/u011897062/article/details/82107709) 这个感觉就很全面详细，我看完我就觉得，这个room 相关的博客我都不用写了，直接复制就好。通过这个大佬的博客呢，我找到了我们的组织 [Android 的github 地址](https://github.com/android?page=1)。就好比流浪多年的我找到了家的感觉。当然也不是之前不知道，就好比之前不知道家里面有宝贝，突然有一天一个同乡说"我们要拆迁啦，你要回去看看吗？"，我回头一想，"嗯？宝贝？我当初出来打工到处找第3方框架的时候咋没有发现？拆迁了？那我要回去看看"，结果一回去，只见靠近种花家那面墙上已经用红油漆喷了一个不怎么圆的圆。咦，果然是有好宝贝。

说回正题。[architecture-components-samples](https://github.com/android/architecture-components-samples) 这个里面就有google 提供的Demo。看到这么多的Demo？我觉得我好像真的没有写room 相关的必要了，要不我给你们说脱口秀？

## 正文

### 为啥要使用room

其实，作为用sqlite 一向都是sql 的我再没有写博客之前，我还真的没有仔细想过这个问题。现在比较好友的sqlite的工具还是蛮多的，有点久了，不是这么容易记起来，以后慢慢列举吧。下面还是来说说个人认为的优点吧。

* google 爸爸出品，而且现在已经到Android X了，是[Jetpack](https://developer.android.com/jetpack) 重要的一环，也便于升级到Android X，同时jetpack 使用量已经升上去了，出现问题的概率很小，有问题多半是使用有问题。jetpack多半是必会技能，哪怕是面试装杯。
* google Demo 在as中导入贼快。可能我这山区才通网，用梯子导别人的Demo都很慢。
* 通过注解对象关系映射（应该是这个词）不必写创建表的sql，便于表字段约束，同时理解简单很多。同类型的也有好几个。
* 可以更加简单实现一表一数据库文件。对于哪些不常用的功能表可以整合到单独的数据库文件中，当然直通过SQLiteOpenHelper也可以达到这个效果。更新还是需要每个自己更新，个人感觉分数据库写处理更新逻辑要清晰很多。
* 通过 dao 注解 便于功能区分，耦合要低很多。通过自己写interface也可以实现。
* 不要杠，人家提供规则是为了使用方便，而不是为了找到其他相同效果的不同写法。个人感觉最主要的是jetpack，用这个如果想，可以把jetpack 所有组件都用上了。
* sqlite 的查询可以直接写到主线程中，而room的相关操作抖需要写到子线程中，所以还可以学一盘rxjava ? 按照道理讲，数据库的读写都应该写到子线程中的。room 中 RoomDatabase 提供了很多约束方法。

好了，代码量和逻辑分化的增加在可以装杯的海量优势面前不值一提。那么我们就可以开整。这篇主要是简单的使用讲解，Demo是基于[contentprovidersample](https://github.com/android/architecture-components-samples/tree/master/PersistenceContentProviderSample)  

### 导入room 

```
def room_version = "1.1.1"
implementation "android.arch.persistence.room:runtime:$room_version"
annotationProcessor "android.arch.persistence.room:compiler:$room_version" // use kapt for Kotlin
// optional - RxJava support for Room
implementation "android.arch.persistence.room:rxjava2:$room_version"
// optional - Guava support for Room, including Optional and ListenableFuture
implementation "android.arch.persistence.room:guava:$room_version"
```

如果是Android X 

```groovy
dependencies {
      def room_version = "2.2.5"

      implementation "androidx.room:room-runtime:$room_version"
      annotationProcessor "androidx.room:room-compiler:$room_version" // For Kotlin use kapt instead of annotationProcessor

      // optional - Kotlin Extensions and Coroutines support for Room
      implementation "androidx.room:room-ktx:$room_version"

      // optional - RxJava support for Room
      implementation "androidx.room:room-rxjava2:$room_version"

      // optional - Guava support for Room, including Optional and ListenableFuture
      implementation "androidx.room:room-guava:$room_version"

      // Test helpers
      testImplementation "androidx.room:room-testing:$room_version"
    }
```

### 设计表

[runoob SQLite 教程](https://www.runoob.com/sqlite/sqlite-tutorial.html) 通过这个教程，我们可以知道sqlite 的增删改查的sql和对应约束。而room创建表也是基于sqlite的约束。当然要实现约束的方式有很多，并不一定要基于sqlite。

[google官方Entity](https://developer.android.google.cn/training/data-storage/room/defining-data)   个人觉得，数据库存储对象就存储对象。不要往里面搞哪些不用存储到数据库的字段。

必须包含标记:@Entity ,@PrimaryKey.

```
@Entity(tableName = Cheese.TABLE_NAME)
public class Cheese {

    /** The name of the Cheese table. */
    public static final String TABLE_NAME = "cheeses";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The name of the name column. */
    public static final String COLUMN_NAME = "name";

    /** The unique ID of the cheese. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    /** The name of the cheese. */
    @ColumnInfo(name = COLUMN_NAME)
    public String name;
   }
   
   // 当前class 有一些和创建表无关的方法，我就没有复制。autoGenerate 是id  自动增长。@ColumnInfo(index = true, name = COLUMN_ID) name = COLUMN_ID 设置创建表中字段的名称。
   index = true 和查询有关。
```

### 数据库查询Dao

```
@Dao
public interface CheeseDao {

    /**
     * Counts the number of cheeses in the table.
     *
     * @return The number of cheeses.
     */
    @Query("SELECT COUNT(*) FROM " + Cheese.TABLE_NAME)
    int count();

    /**
     * Inserts a cheese into the table.
     *
     * @param cheese A new cheese.
     * @return The row ID of the newly inserted cheese.
     */
    @Insert
    long insert(Cheese cheese);

    /**
     * Inserts multiple cheeses into the database
     *
     * @param cheeses An array of new cheeses.
     * @return The row IDs of the newly inserted cheeses.
     */
    @Insert
    long[] insertAll(Cheese[] cheeses);

    /**
     * Select all cheeses.
     *
     * @return A {@link Cursor} of all the cheeses in the table.
     */
    @Query("SELECT * FROM " + Cheese.TABLE_NAME)
    Cursor selectAll();

    /**
     * Select a cheese by the ID.
     *
     * @param id The row ID.
     * @return A {@link Cursor} of the selected cheese.
     */
    @Query("SELECT * FROM " + Cheese.TABLE_NAME + " WHERE " + Cheese.COLUMN_ID + " = :id")
    Cursor selectById(long id);

    /**
     * Delete a cheese by the ID.
     *
     * @param id The row ID.
     * @return A number of cheeses deleted. This should always be {@code 1}.
     */
    @Query("DELETE FROM " + Cheese.TABLE_NAME + " WHERE " + Cheese.COLUMN_ID + " = :id")
    int deleteById(long id);

    /**
     * Update the cheese. The cheese is identified by the row ID.
     *
     * @param cheese The cheese to update.
     * @return A number of cheeses updated. This should always be {@code 1}.
     */
    @Update
    int update(Cheese cheese);

}
```



### 数据库操作对象RoomDatabase



#### 增加

#### 删除

#### 修改

#### 查询

### 数据库更新



### 留坑

1. 如果一个实体类包含多个主键，通过字段注解，entity注解，字段注解+entity注解是否都可行。
2. 多个字段不允许重复。通过字段注解，entity注解，字段注解+entity注解是否都可行。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。

