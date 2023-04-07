## 资料

目前使用比较广泛的是C/C++静态代码检测工具有CppCheck和pc-lint等。

# 正文

## 静态代码检测

使用CppCheck.

## 运行时检测

在Android平台上，比较好用又比较可靠的的一种解决内存问题的办法： 把Android NDK的C代码移植到其他平台上运行，然后使用该平台下的工具（如valgrind等）进行检测。或者将其他平台上的工具移植到Android中，leakTracer就可以作为动态检测内存泄漏的工具。线上使用breakpad 收集crash.