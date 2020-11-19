+++
date = "2020-10-01"
title = "Android sqlite增删改查另类写法"
tags = ["sqlite"]
categories = [
    "技术类"
]
series = ["android基础"]
featured = true
+++

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dcf76e3e7a1646aab6cf921dfffd3a45~tplv-k3u1fbpfcp-zoom-1.image)

## 前言

啊哈，今天又更新了。接上一篇[日常搬砖sqlite可用类型与存储类型](https://juejin.im/post/6868510436254777357/) 。

顺便推一下[日常搬砖sqlite目录](https://juejin.im/post/6868506837000388615)

之前又一篇博客提到了json 对象不仅仅可以通过对象class 解析，也可以解析成map.巧合的是sqlite 添加和更新需要的ContentValues 也是一个类似于map的结构。既然object 与map互转，同理可以解决 object可以和ContentValues 互相转化，是吧。还有一个点  cursor.getType(cursor.getColumnIndex(key)) 可以获取到值的type,所以开整。先总结下可能需要的逻辑。

***

* ContentValues 也是一个类似于map的结构。
* 逻辑上object可以和ContentValues 互相转化
*  cursor.getType(cursor.getColumnIndex(key)) 可以获取到值的type。
* 通过反射可以获取到对应字段设定的type
* Cursor 获取到的type 包含 FIELD_TYPE_NULL，FIELD_TYPE_INTEGER，FIELD_TYPE_FLOAT，FIELD_TYPE_STRING，FIELD_TYPE_BLOB（ps:不要问我从哪里看到的，点开对应的class 都有）
* ContentValues 支持存放主要类型：String，Byte，Short，Integer，Long，Float，Double，Boolean，byte[]等。（ps:不要问我从哪里看到的，点开对应的class 都有）

***

## 正文

### 通过反射拿到所以的Field

```
/**
 * 获取所有的内容，解决父类中参数获取不到的bug
 * @param object
 * @return
 */
private static Field[] getAllFieldsByObject(Object object){
    Class clazz = object.getClass();
    List<Field> fieldList = new ArrayList<>();
    while (clazz != null){
        fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
        clazz = clazz.getSuperclass();
    }
    Field[] fields = new Field[fieldList.size()];
    fieldList.toArray(fields);
    return fields;
}

/**
     * 获取对象中所有可用字段。
     * @param clazz
     * @return
     */
    private static Field[] getAllFieldsByClass(Class clazz){
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
```

### object转ContentValues 

```
/**
 * 将对象转为 ContentValues
 * @param object
 * @return
 */
public static ContentValues entityToContentValues(Object object) {
    ContentValues map=new ContentValues();
    for (Field field : getAllFieldsByObject(object)){
        try {
            boolean flag = field.isAccessible();
            field.setAccessible(true);
            Object o = field.get(object);
            if (o!=null){
                if (o instanceof Integer){
                    map.put(field.getName(),(Integer) o);
                }
                if (o instanceof String){
                    map.put(field.getName(),(String) o);
                }
                if (o instanceof Double){
                    map.put(field.getName(),(Double) o);
                }
                if (o instanceof Float){
                    map.put(field.getName(),(Float) o);
                }
                if (o instanceof Byte){
                    map.put(field.getName(),(Byte) o);
                }
                if (o instanceof Short){
                    map.put(field.getName(),(Short) o);
                }
                if (o instanceof Long){
                    map.put(field.getName(),(Long) o);
                }if (o instanceof Boolean){
                    map.put(field.getName(),(Boolean) o);
                }
                if (o instanceof  byte[]){
                    map.put(field.getName(),( byte[]) o);
                }

                field.setAccessible(flag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return map;
}
```

这个可能有些字段没有转化完，但是一应该是完整的。

### 从cursor 中获取ContentValues

因为corsor 定义了4种类型。大概只能这么转化

```
ContentValues values = new ContentValues();
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
```



### 从cursor获取class 对象

这个就需要反射获取到key了。

```
/**
 * 将cursor 中的数据根据 传入的class 赋值到里面。
 * @param cursor
 * @param entity
 * @param <T>
 * @return
 */
public static <T> T getObjectByCursor(Cursor cursor,Class<T> entity){
        T t = null;
        try {
            t = entity.newInstance();
            for(Field field : getAllFieldsByClass(entity)) {
                Class<?> type = field.getType();
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                if (type == byte [] .class||type== Byte [] .class){
                    field.set(t, cursor.getBlob(cursor.getColumnIndex(field.getName())));
                }
                if (type==byte.class||type==Byte.class){
                    field.set(t,cursor.getInt(cursor.getColumnIndex(field.getName())));
                }
                if (type == String.class){
                    field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
                }
                if (type == short.class||type==Short.class){
                    field.set(t, cursor.getShort(cursor.getColumnIndex(field.getName())));
                }
                if (type == int.class||type==Integer.class){
                    field.set(t, cursor.getInt(cursor.getColumnIndex(field.getName())));
                }
                if (type == long.class||type==Long.class){
                    field.set(t, cursor.getLong(cursor.getColumnIndex(field.getName())));
                }
                if (type == float.class||type==Float.class){
                    field.set(t, cursor.getFloat(cursor.getColumnIndex(field.getName())));
                }
                if (type == double.class||type==Double.class){
                    field.set(t, cursor.getDouble(cursor.getColumnIndex(field.getName())));
                }
                if (type==boolean.class||type==Boolean.class){
                    field.set(t,cursor.getInt(cursor.getColumnIndex(field.getName()))==1?true:false);
                }

                field.setAccessible(flag);
            }
            return t;
        } catch (InstantiationException e) {
            LogUtils.e(e);
        } catch (IllegalAccessException e) {
            LogUtils.e(e);
        }
        return t;
    }
```

### 从cursor 中获取列表数据

这个主要是基于上面方法。

```
/**
 *Cursor 获取列表数据
 * @param cursor
 * @param c
 * @return
 */
public static  <T> List<T> getObjectsByCursor(Cursor cursor,Class<T> c){
    List<T>  objects=new ArrayList<>();
    while (cursor.moveToNext()) {
        objects.add(getObjectByCursor(cursor,c));
    }
    cursor.close();
    return objects;
}
```

说一个点，虽然JAVA 有自动回收机制，但是 cursor.close(); 还是可以写的。

### sqlite 增加 查找 更新 

增加和更新 都是通过ContentValues操作的，那么我们传入设定的正确约束对象转化为ContentValues就可以了。

而查找方面，上列仅仅用于查找多个字段的数据，你说只需要查一个字段一个参数，直接通过 cursor 获取就好了，不用这么麻烦吧，靓仔。

``` 
cursor.getColumnNames() 这个调调可以查看当前包含的字段。很重要。
```

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。

