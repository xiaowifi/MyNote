![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dcf76e3e7a1646aab6cf921dfffd3a45~tplv-k3u1fbpfcp-zoom-1.image)

## 前言

顺便推一下[日常搬砖sqlite目录](https://juejin.im/post/6868506837000388615) 。

接上面内容。因为room会生根据 Database成一个 impl.java  文件，通过这个class可以看到创建表的sql。通过查看sqlite studio 需要把几个文件都复制到同一个文件夹中才可以查看完整的表，我通过room 创建的数据库有.db，.db-shm,.db-wal这3个后缀名文件，然后通过Stetho 发现我的id 竟然有两个，但是在Demo 上加上 Stetho 发现 Stetho获取的有一个rowid。emmmm？感觉情况完全不一样，所以我决定看看。

### sqlite 查看工具

之前找到了一篇博客对sqlite 表查看写得很详细的。[Android Studio查看SQLite数据库方法大全](https://blog.csdn.net/huweiliyi/article/details/105459022) .如果是通过Stetho 查看数据库，可能出现一个问题，404或者空白页。这个时候就需要使用一些梯子(科学上网)了，等加载出来之后就不需要了。浏览器直接输入**chrome://inspect**  找到设备就好。

## 正文

### BasicRxJavaSample

[这个Demo的地址](https://github.com/android/architecture-components-samples/tree/master/BasicRxJavaSample) 。这个Demo是room+rxjava.众所皆知，数据库的增删改查不应该存在于主线程中。好了我们直接看这个Demo所提供的实体类。

#### 实体类

```
@Entity(tableName = "users")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "userid")
    private String mId;

    @ColumnInfo(name = "username")
    private String mUserName;

    @Ignore
    public User(String userName) {
        mId = UUID.randomUUID().toString();
        mUserName = userName;
    }

    public User(@NonNull String id, String userName) {
        this.mId = id;
        this.mUserName = userName;
    }

    public String getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }
}
```

#### 主要使用了下面几个注解：

* ```
  @Entity(tableName = "users")//修改创建的表的表名，默认是class 名字
  ```

* ```
  @NonNull//表示这个字段在数据库存储不可为空，通常通过room创建的数值类型都是 NonNull，string类型的除外。而且string在room 创建表的时候使用的是TEXT。
  ```

* ```
  @PrimaryKey //设置主键 room 定义中 每个创建表的实体类都需要有主键，也可以包含多个主键。
  ```

* ```
  @ColumnInfo(name = "userid") //创建表和实体类字段名不一致。
  ```

* ```
  @Ignore //room 忽略注解。
  ```

#### impl 中创建表的sql

```
_db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`userid` TEXT NOT NULL, `username` TEXT, PRIMARY KEY(`userid`))");
```

通过上面创建表的sqlite可以发现，这个表主要有2个字段。这个和实体类定义的很一致。

#### 通过Stetho 查看到的结果

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3355bae1e96a45dbbaf81825e8c03c74~tplv-k3u1fbpfcp-zoom-1.image)

竟然有一个rowid。可是我上面创建sql 没有这个字段啊。

#### 通过sqlite studio 查看创建的表

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/9f668cd801e544289dcfc2c892462ead~tplv-k3u1fbpfcp-zoom-1.image)

emmm ?好像没有问题，那就是 Stetho自作主张给我添加的，脑阔疼。

#### 总结

通过impl 文件和导出sqlite数据库文件，说明Stetho 可能出现多余的字段，可能是重复的列。但是实际上并没有重复。验证自己创建的表的逻辑正确性 就可以直接通过impl文件直接确认了。如果想要通过导出数据验证表和数据需要导出所有的文件到同一个目录下，如果之前有连接，估计需要重新连接。数据库表创建实体类更新后需要build 项目更新 对应的impl 文件。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。