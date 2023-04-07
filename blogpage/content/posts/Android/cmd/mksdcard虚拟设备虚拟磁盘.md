https://developer.android.com/studio/command-line/mksdcard

您可以使用 `mksdcard` 工具创建 FAT32 磁盘映像，然后将该映像加载到运行不同 Android 虚拟设备 (AVD) 的模拟器中，以模拟多个设备中存在相同 SD 卡的情形。

Android SDK 工具软件包中提供的 `mksdcard` 工具位于 `android-sdk/emulator/mksdcard` 内。

如果您不需要可在多个虚拟设备之间共享的磁盘映像，则无需使用 `mksdcard` 命令。默认情况下，模拟器会使用由活动 AVD 生成并存储的默认映像。