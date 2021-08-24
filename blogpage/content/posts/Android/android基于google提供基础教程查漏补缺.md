+++
date = "2021-08-21"
title = "android基于google提供基础教程查漏补缺"
description = "android基于google提供基础教程查漏补缺"
categories = [
    "android基础"
]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
* [参考资料：google教程](https://developer.android.google.cn/courses/fundamentals-training/toc-v2)
* [参考资料：google基础教程 github 代码库](https://github.com/google-developer-training/android-fundamentals-apps-v2)
## 正文
* recycleView 的viewHolder 有一个getLayoutPosition() 可以获取当前viewHolder 的下标，点击事件可以写到viewHolder里面了。
* textview textAppearance 外观，18，16，14。
* textview autoLink=" web" 网站会高亮显示，显示颜色为:colorAccent
* textview textAlignment 定义文本对齐方式。
* activity registerForContextMenu 注册菜单。
* 控件的contentDescription 
* 图片控件的srcCompat
* view的主题 popupTheme
* view的主题 android:theme
* tools:context
* tools:showIn
* 新工具类：ContextCompat compat 这个调调
* AsyncTask
* uri
````aidl
Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
````
* HttpURLConnection
* AsyncTaskLoader
* ActivityOptions
* ObjectAnimator
* getWindow().setEnterTransition()
* Transition
* Spinner
* EditorInfo
* android:imeOptions
* android:tint 对图标进行着色。
* android:tintMode="" 着色模式
* ItemTouchHelper recycleview 进行拖拽和侧滑删除。
* adjustViewBounds adjustViewBounds只有在ImageView一边固定，一边为wrap_content的时候才有意义。设置为true的时候，可以让ImageView的比例和原始图片一样，以达到让图片充满的ImageView的效果
* ShareCompat 
* AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_YES);
* recreate 重新创建activity。
* AndroidJUnitRunner Android 单元测试脚本 Android Demo中项目名称包含:Espresso 则表示包含单元测试用例。
* @RunWith(JUnit4.class) 这个好像也界面分开的，有点懵逼。
* intent-filter
* FloatingActionButton
* PreferenceManager
* PreferenceActivity
* NavigationView
* DrawerLayout
* Snackbar 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
## 结束


