![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/dcf76e3e7a1646aab6cf921dfffd3a45~tplv-k3u1fbpfcp-zoom-1.image)

## 前言

搬砖生活中，有一种转换叫做序列化和反序列化。但是本文和这个没有多少关系，我们知道JAVA 的对象的存储是分键和值的，emmmm？map 好像也是哦，我们可能会遇到一个判断一个对象中是否包含多个值或者一个json 中只需要某个值的情况，那么是否可以针对简单对象提供一个json 转map的能力呢？emmmm？gson 实现了，比如这个样子：

```
new Gson().fromJson("",new TypeToken<Map<String,Object>>(){}.getType())
```

但是，这个和本文还是没有关系。我只是顺便引导一下。需要对象转map 的情况通常在与服务器互动过程中。我们定义了一个专门用于传递参数的对象，如果服务器不要求传递json,那么就需要转map了，或者手动赋值。手动？那是不可能的。JAVA 中一个骚操作，叫反射，但是它有一个问题，那就是无法反射到父类的参数。比如有一个每个接口都需要传递的参数，要么每次添加，要么创建对象时候生成。但是这都不是问题。

### 获取所以内容

```
//这个方法可以解决获取不到父类的情况
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

### object 转map 

```
/**
 * 实体类转map
 * @param object
 * @return
 */
public static HashMap<String, Object> entityToMap(Object object) {
    HashMap<String, Object> map = new HashMap();
    for (Field field : getAllFieldsByObject(object)){
        try {
            boolean flag = field.isAccessible();
            field.setAccessible(true);
            Object o = field.get(object);
            if (o!=null){
                map.put(field.getName(), o);
                field.setAccessible(flag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return map;
}
```

这个其实没有什么好讲解的。



### Map转实体类

```
/**
 * Map转实体类
 * @param map 需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
 * @param entity  需要转化成的实体类
 * 2020-07-22 17:43:48  更改后 可能有问题。
 * @return
 */
public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
    T t = null;
    try {
        t = entity.newInstance();
        for(Field field : getAllFieldsByObject(t)) {
            if (map.containsKey(field.getName())) {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object object = map.get(field.getName());
                if (object!= null && field.getType().isAssignableFrom(object.getClass())) {
                    field.set(t, object);
                }
                field.setAccessible(flag);
            }
        }
        return t;
    } catch (InstantiationException e) {
        LogUtils.e(e);
    } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        LogUtils.e(e);
    }
    return t;
}
```





## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。