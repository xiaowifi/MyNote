+++
date = "2021-5-11"
title = "Coordinatorlayout简单单层吸顶效果"
description = "Coordinatorlayout简单单层吸顶效果"
tags = [ "模板"]
categories = [
    "杂学"
]
series = ["模板"]
featured = true
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
》 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## 前言
````aidl
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:id="@+id/coordinator"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!--AppBarLayout继承自LinearLayout-->
        <androidx.coordinatorlayout.widget.CoordinatorLayout
            app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <androidx.core.widget.NestedScrollView
                app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
                <LinearLayout
                    android:orientation="vertical"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent">
                    <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>  <TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/><TextView
                        android:layout_marginTop="1dp"
                        android:background="#673AB7"
                        android:gravity="center"
                        android:text="!!!!!!!!!!!!!"
                        android:layout_width="match_parent"
                        android:layout_height="45dp"/>
                </LinearLayout>
            </androidx.core.widget.NestedScrollView>
            <com.google.android.material.appbar.AppBarLayout

                app:elevation="0dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:contentScrim="#ffffff">

                <!--tabs之上的一些布局，比如一个轮播图-->
                <!--防止最后recyclerview一条数据显示不全 android:minHeight="?actionBarSize"-->
                <!--设置收缩后的颜色app:contentScrim="@color/amber_600"-->
                <com.google.android.material.appbar.CollapsingToolbarLayout

                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:minHeight="0dp"
                    app:contentScrim="#A8A8A8"
                    app:expandedTitleGravity="bottom|center_horizontal"
                    app:expandedTitleMarginBottom="16dp"




                    app:layout_scrollFlags="scroll|enterAlways"
                    app:title="@string/app_name">

                    <!--滑动出去的内容-->
                    <!--app:layout_collapseMode="parallax"交错上移-->
                    <!--app:layout_collapseMode="pin"整体上移-->
                    <TextView
                        android:background="#A746B8"
                        android:gravity="center"
                        android:text="头部"
                        android:layout_width="match_parent"
                        android:layout_height="95dp"/>

                </com.google.android.material.appbar.CollapsingToolbarLayout>
                <!--需要浮动的布局-->
                <TextView
                    android:background="#2196F3"
                    android:gravity="center"
                    android:text="浮动"
                    android:layout_width="match_parent"
                    android:layout_height="55dp"/>

            </com.google.android.material.appbar.AppBarLayout>
        </androidx.coordinatorlayout.widget.CoordinatorLayout>

        <com.google.android.material.appbar.AppBarLayout
            android:id="@+id/appbar"
            app:elevation="0dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:contentScrim="#ffffff">

            <!--tabs之上的一些布局，比如一个轮播图-->
            <!--防止最后recyclerview一条数据显示不全 android:minHeight="?actionBarSize"-->
            <!--设置收缩后的颜色app:contentScrim="@color/amber_600"-->
            <com.google.android.material.appbar.CollapsingToolbarLayout
                android:id="@+id/collapsingToolbarLayout"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:minHeight="0dp"
                app:contentScrim="#A8A8A8"
                app:layout_collapseMode="pin"
                app:expandedTitleGravity="bottom|center_horizontal"
                app:expandedTitleMarginBottom="16dp"
                app:layout_scrollFlags="scroll|enterAlways"
                app:title="@string/app_name">

                <!--滑动出去的内容-->
                <!--app:layout_collapseMode="parallax"交错上移-->
                <!--app:layout_collapseMode="pin"整体上移-->
                <TextView
                    android:background="#A746B8"
                    android:gravity="center"
                    android:text="头部"
                    android:layout_width="match_parent"
                    android:layout_height="95dp"/>

            </com.google.android.material.appbar.CollapsingToolbarLayout>
            <!--需要浮动的布局-->
            <TextView
                android:background="#2196F3"
                android:gravity="center"
                android:text="浮动"
                android:layout_width="match_parent"
                android:layout_height="55dp"/>

        </com.google.android.material.appbar.AppBarLayout>
    </androidx.coordinatorlayout.widget.CoordinatorLayout>
````

> 这个有一个问题，默认不顶顶部布局，因为他没有实现NestedScrollingChild。emmmm? 
## 结束


