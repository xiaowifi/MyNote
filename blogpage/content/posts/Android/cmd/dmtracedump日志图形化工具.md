# 前言

dmtracedump 是一种用于跟踪日志文件生成图形化调用堆栈图的工具。此工具使用Graphviz Dot 实用程序创建图形化的输出。因此，你必须安装Graphviz 才能运行 dmtracedump.

如果尚未生成跟踪日志并将其从连接的设备保存到本地计算机，请转到[通过检测您的应用生成跟踪日志](https://developer.android.com/studio/profile/generate-trace-logs)。

`dmtracedump` 工具以树形图的形式生成调用堆栈数据，其中每个节点表示一个方法调用。它使用箭头显示调用流程（从父节点到子节点）。下图显示了 `dmtracedump` 的示例输出。

`dmtracedump` 工具在 Android SDK 工具软件包中提供，并位于 `android-sdk/platform-tools/` 下。

## 资料

* [dmtracedump](https://developer.android.com/studio/command-line/dmtracedump) 

# 正文

# 总结 