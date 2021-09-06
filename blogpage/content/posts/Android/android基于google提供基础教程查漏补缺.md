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
* 控件的 contentDescription 
* 图片控件的 srcCompat 这个设置src类似，但是对于某些图片，建议使用src compat 
* view的主题 popupTheme 
* view的主题 android:theme
* tools:context
* tools:showIn
* 新工具类：ContextCompat compat 这个调调
* AsyncTask 线程
* uri uri 的使用方式如下：
````aidl
Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
````
* HttpURLConnection http 连接对象。
* AsyncTaskLoader AsyncTask更好用的版本。
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
* ShareCompat 分享工具类。
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
* JobService 用来执行一些并非即时执行的后台进程。
* BroadcastReceiver
* Intent 静态常量
* autoSizeTextType 调整大小类型。
* MotionLayout
* alpha 0到1 之间的透明度。
* androidx.cardview.widget.CardView 就是单纯的卡片化view
* com.google.android.material.tabs.TabLayout
* Space 空间
* cropToPadding 如果为 true，图像将被裁剪以适合其填充。
* listitem
* showIn
* ImageFilterView
* layout_editor_absoluteX
* layout_editor_absoluteY
* fontFamily 字体系列的属性。
* layout_scrollFlags
* androidx.drawerlayout.widget.DrawerLayout 抽屉布局 
* typeface 默认文本字体
* androidx.constraintlayout.utils.widget.MockView
* fragment.setRetainInstance(true) 用于限制当界面配置发生改变的时候，fragment只会创建一次。
* databinding  OnRebindCallback 绑定的生命周期监听。
* recycleview+databinding+OnRebindCallback 用于刷新处理。
* Collections.addAll()
* 系统设计inflate 第3个参数的意义。用于是否添加进入view？或者说是否设置layoutparent 
* 自定义view的构造方法，那个不能删除。两个参数的构造方法不能删除，因为反射实现的逻辑，调用了两个入参的构造方法。
* 想要保证view不会重复添加，这个和view的添加逻辑有关，在view中存储一个父类对象，如果父类对象不为空的情况下，会直接抛异常。这种常见用于layout 添加到view 或者做大对象内存或者不可中断view的
  的处理，通常是移出父类，然后再添加。常见问题，recycleview 设置viewHolder 和fragment 设置view的时候，如果事先绑定了view就会抛出上面错误。
* 布局嵌套能写多少层，这个和jvm 方法压栈有关，递归调用 会重复压栈。
* source insight.
* ConcatAdapter recyclerview 新版本的adapter，主要作用的顺序提供多个adapter。 


