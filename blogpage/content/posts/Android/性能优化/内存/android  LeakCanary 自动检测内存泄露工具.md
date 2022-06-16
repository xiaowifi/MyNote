> LeakCanary LeakCanary是Square公司为Android开发者提供的一个自动检测内存泄漏的工具，LeakCanary本质上是一个基于MAT进行Android应用程序内存泄漏自动化检测的的开源工具，我们可以通过集成LeakCanary提供的jar包到自己的工程中，一旦检测到内存泄漏，LeakCanary就会dump Memory信息，并通过另一个进程分析内存泄漏的信息并展示出来，随时发现和定位内存泄漏问题，而不用每次在开发流程中都抽出专人来进行内存泄漏问题检测，极大地方便了Android应用程序的开发。
## 资料
* [LeakCanary GitHub 地址](https://github.com/square/leakcanary)
* [csdn LeakCanary 使用教程](https://blog.csdn.net/noblef/article/details/108975861)
# 正文