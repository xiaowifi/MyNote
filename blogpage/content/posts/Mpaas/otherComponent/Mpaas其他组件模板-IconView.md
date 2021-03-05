+++
date = "2021-3-3"
title = "Mpaas其他组件-AUCheckIcon "
slug = "dgg"
series = ["Mpaas"]
featured = true

+++
##导入包
> mpaas 提供Android studio 插件，在插件中选择使用。
````aidl
// ui 库 
 api 'com.mpaas.android:antui'
````
## 正文
> https://help.aliyun.com/document_detail/71740.html?spm=a2c4g.11186623.6.1505.535331de0J0Ddm
> 基于android.widget.CheckBox。
### AUCheckIcon
> AUCheckIcon 组件用于实现选择框的 IconView。的意思是图标需要通过 IconView类似的设置吗，但是IconView 的设置图标是setIconByName.
#### xml 
````aidl
 <com.alipay.mobile.antui.common.AUCheckIcon
        android:id="@+id/au_check_icon"
        android:layout_width="45dp"
        android:layout_height="45dp"/>
````
#### 设置
 ````aidl
/**选中状态*/
public static final int STATE_CHECKED = 0x01;
/**未选中状态*/
public static final int STATE_UNCHECKED = 0x02;
/**不可取消勾选状态*/
public static final int STATE_CANNOT_UNCHECKED = 0x03;
/**不可勾选状态*/
public static final int STATE_CANNOT_CHECKED = 0x04;
/**
 * 设置 checkIcon 的状态
 * @param state
 */
public void setIconState(int state);
/**
 * 获取checkIcon的状态
 * @return
 */
public int getIconState() ;
````
#### 样式
> 采用checkbox button style 在xml中设置 checked 会有状态切换效果，通过Java 代码设置未发现更改样式。
> 可能还是和颜色配置有关，通过配置正确的style 可能达到修改的样式效果。

````aidl
 private void initView(int var1, boolean var2) {
        if (var2) {
            this.setBackgroundResource(drawable.drawable_check_icon);
            this.setButtonDrawable(new ColorDrawable(0));
        } else {
            this.setButtonDrawable(drawable.drawable_check_icon);
        }

        this.setIconState(var1);
    }
````

### AUCheckBox
> 基于android.widget.CheckBox。和CheckBox一样的使用方式。

````aidl
    <com.alipay.mobile.antui.basic.AUCheckBox
        android:button="@drawable/check_box_style"
        android:layout_width="45dp"
        android:layout_height="45dp"/>
````