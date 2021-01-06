+++
date = "2021-01-05"
title = "PropertyChangeListener关联属性的简单介绍"
description = "PropertyChangeListener关联属性的简单介绍"
tags = [ "java基础"]
categories = [
    "技术类"
]
featured = true

+++



JavaBean的属性与一般Java程序中所指的属性，或者说与所有面向对象的程序设计语言中对象的属性是一个概念，在程序中的具体体现就是类中的变量。在JavaBean的设计中，按照属性的不同作用又细分为四类：单值属性；索引属性；***\*关联属性\****；限制属性。

　　本文主要介绍如何使用PropertyChangeSupport类来支持关联属性事件的触发。

## 　　1．关联属性

　　关联属性，也称之为绑定属性。绑定属性会在属性值发生变化时，通知所有相关的监听器。为了实现一个绑定属性，必须实现两个机制。

　　1）　无论何时，只要属性的值发生变化，该bean必须发送一个PropertyChange事件给所有已注册的监听器。该变化可能发生在调用set方法时，或者程序的用户做出某种动作时。

　　2）　为了使感兴趣的监听器能够进行注册，bean必须实现以下两个方法：

> > ```
> > void addPropertyChangeListener(PropertyChangeListener listener);
> > ```
>
> > ```
> > void removePropertyChangeListener(PropertyChangeListener listener);
> > ```

## 　　2．使用PropertyChangeSupport 管理 监听器

　　可以通过java.bean包下的PropertyChangeSupport类来管理监听器。要使用这个类，bean必须有一个此类的数据域。

> > ```
> > private PropertyChangeSupport changes = new PropertyChangeSupport(this);
> > ```

　　这样就可以将添加和移除监听器的任务交给这个对象。

> > ```
> > public void addPropertyChangeListener(PropertyChangeListener listener) {
> > ```
>
> > ```
> > 　　changes.addPropertyChangeListener(listener);
> > ```
>
> > ```
> > }
> > ```
>
> > ```
> > public void removePropertyChangeListener(PropertyChangeListener listener) {
> > ```
>
> > ```
> > 　　changes.removePropertyChangeListener(listener);
> > ```
>
> > ```
> > }
> > ```

`  当bean的属性发生变化时，使用PropertyChangeSupport对象的firePropertyChange方法，它会将一个事件发送给所有已经注册的监听器。该方法有三个参数：属性的名字、旧的值以及新的值。属性的值必须是对象，如果是简单数据类型，则必须进行包装。`

```
 changes.firePropertyChange("ourString", oldString, newString);
```

　　所有注册的监听器实现PropertyChangeListener接口，该接口中只有一个方法。

public void propertyChange(PropertyChangeEvent e);

　　当bean的属性值发生变化时，该方法中的代码就会被触发。可以通过

> 　　e.getOldValue();
>
> 　　e.getNewValue();

　　来得到changes.firePropertyChange("ourString", oldString, newString);中的oldString和newString。

## 　　3．为什么要使用PropertyChangeSupport

　　使用这个类 管理 监听器的好处：

  1）它是线程安全的。如果使用一个循环体来set Bean的属性，则这个类可以保证所有监听器执行触发事件的有序。

　　2）这个类支持fire带索引的属性改变事件（详见java.bean.IndexedPropertyChangeEvent）。此时向注册的监听器发送一个

​    PropertyChangeEvent的方法为：

```
void fireIndexedPropertyChange(String PropertyName,int index,Object oldValue,Object newValue);
```

## 　　4．示例

　　MyBoundBean类(具体代码见附件)是一个JavaBean,我们关注它的唯一一个属性ourString的变化情况，它的初始值是Hello。并通过PropertyChange类来管理监听器。注意在set方法中会调用firePropertyChange方法。

```
MyBoundBean.javaimport java.beans.PropertyChangeListener;import java.beans.PropertyChangeSupport;public class MyBoundBean {　　String ourString = "Hello";　　private PropertyChangeSupport changes = new PropertyChangeSupport(this);　　public void setString(String newString) {　　　 String oldString = ourString;　　　 ourString = newString;　　　 changes.firePropertyChange("ourString", oldString, newString);　　}　　public String getString() {　　　 return ourString;　　}　　public void addPropertyChangeListener(PropertyChangeListener listener) {　　　 changes.addPropertyChangeListener(listener);　　}　　public void removePropertyChangeListener(PropertyChangeListener listener) {　　　 changes.removePropertyChangeListener(listener);　　}}
![Java:在Bean中使用PropertyChangeSupport支持PropertyChangeListeners](http://img.ddvip.com/2008_12_05/1228463098_ddvip_6138.jpg)
```

 

```
MyBoundBean b = new MyBoundBean();…public void actionPerformed(ActionEvent e) {　　jButton1.setText(textBox.getText());　　b.setString(textBox.getText());}
```

　　目标bean的属性一改变（注意，初始值是"Hello"），将会触发propertyChange方法的执行。将文本框的内容设置为目标bean的ourString属性的旧值，同时，将jButton2的test设置成目标bean的ourString属性的新值。

```
public void propertyChange(PropertyChangeEvent e) {　　if (e.getSource() == b) {　　　 textBox.setText(e.getOldValue().toString());　　　 jButton2.setText(e.getNewValue().toString());　　}}
```

　　如果不实现PropertyChangeListener接口的话，可以使用匿名内部类来达到同样的效果。（具体代码见附件MyCallBound2.java）

```
MyBoundBean b = new MyBoundBean();…b.addPropertyChangeListener(new PropertyChangeListener() {　　public void propertyChange(PropertyChangeEvent e) {　　　 // 这样一来，我们就可以用自己定义的名字实现事件　　　 ourString_propertyChange(e);　　}});
```

 

　　这样一来，我们就可以用自己定义的名字实现事件。

```
void ourString_propertyChange(PropertyChangeEvent e) {　　if (e.getSource() == b) {　　　 textBox.setText(e.getOldValue().toString());　　　 jButton2.setText(e.getNewValue().toString());　　}}
```