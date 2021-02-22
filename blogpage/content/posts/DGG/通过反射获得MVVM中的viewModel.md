+++
date = "2021-2-19"
title = "探索 Jetpack 库"
description = "探索 Jetpack 库"
series = ["顶呱呱"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
> 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 
## 正文
> 莫得啥说的，直接上代码

调用方式
````aidl
 return (VM) new ViewModelProvider(DggBaseActivity.this).get(new ClassNewInstanceUtil().getClass(this));
````
工具类代码
````aidl
public class ClassNewInstanceUtil<T> {

    public ClassNewInstanceUtil() {
    }

    public T createViewModel(Object object){
        try {
            Type superClass = object.getClass().getGenericSuperclass();
            assert superClass != null;
            if (superClass instanceof ParameterizedType){
                Type type = ((ParameterizedType) superClass).getActualTypeArguments()[1];
                Class<?> clazz = getRawType(type);
                return (T) clazz.newInstance();
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    public Class<?> getClass(Object object){
        try {
            Type superClass = object.getClass().getGenericSuperclass();
            assert superClass != null;
            if (superClass instanceof ParameterizedType){
                Type type = ((ParameterizedType) superClass).getActualTypeArguments()[1];
                Class<?> clazz = getRawType(type);
                return clazz;
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }




    // type不能直接实例化对象，通过type获取class的类型，然后实例化对象
    private static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
        }
    }


    public static Class<?> getTClazz(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
````