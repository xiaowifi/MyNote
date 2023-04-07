+++
date = "2021-3-5"
title = "记一个关于Android studio 无法查看Java源码的bug"
featured = true
+++

## 前言
顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 
### 正文 
最近换到到Windows电脑，安装新的idea，才发现的问题。
```aidl
  // IntelliJ API Decompiler stub source generated from a class file
  // Implementation of methods is not available
  /* compiled code */
```
因为无法查看源码，导致搬砖效率都低了好多。
#### 解决方式
检查路径：File->setting->Plugins，然后下拉，找到Java Bytecode Decompiler。
但是Android studio
11:34	Plugin Error
Plugin "GsonFormat" is incompatible (supported only in IntelliJ IDEA).
Plugin "Java Decompiler IntelliJ Plugin" is incompatible (supported only in IntelliJ IDEA).
> 换到idea 就行



