
## 前言
> 话说，Android textview 应该算是最常用的控件了。但是现在的设计文本大多数都是内容居中对齐。
> 但是 textview 却搞事情的有一个内间距。
## 正文
````aidl
    <item name="android:includeFontPadding">false</item>
````
将上面代码放到 全局的style中就可以达到效果了。
然后是行距，这个就需要手动算了。
android:lineSpacingExtra="3dp"

代码可以设置行高的，所以行间距 也可以不自己算了。 
## 结束


