`bmgr` 是一款 shell 工具，可用于与搭载 Android 2.2（API 级别 8）或更高版本的设备上的备份管理器进行交互。该工具提供了用于启动备份和恢复操作的命令，这样您就无需重复擦除数据或执行类似的干扰性步骤来测试应用的备份功能。`bmgr` 工具支持[自动备份](https://developer.android.com/guide/topics/data/autobackup)和[键/值对备份](https://developer.android.com/guide/topics/data/keyvaluebackup)。

注意：`bmgr restore` 不适用于[加密备份](https://developer.android.com/guide/topics/data/autobackup#define-device-conditions)。

您可以通过 [adb shell](https://developer.android.com/studio/command-line/adb) 在设备上运行 `bmgr` 命令，然后使用 [logcat](https://developer.android.com/studio/command-line/logcat) 监控命令的输出。如需查看可用命令的列表和说明，请直接运行 `bmgr` 工具，而不要提供任何参数。如需了解如何触发备份和恢复操作，请参阅[测试备份和恢复](https://developer.android.com/guide/topics/data/testingbackup)。

如需了解如何在应用中支持备份，请参阅[数据备份](https://developer.android.com/guide/topics/data/backup)