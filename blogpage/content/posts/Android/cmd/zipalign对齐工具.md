https://developer.android.com/studio/command-line/zipalign

`zipalign` 是一种 zip 归档文件对齐工具，有助于确保归档文件中的所有未压缩文件相对于文件开头对齐。这样一来，您便可直接通过 `mmap(2) `访问这些文件，而无需在 RAM 中复制这些数据并减少了应用的内存用量。

在将 APK 文件分发给最终用户之前，先使用 `zipalign` 进行优化。如果您使用 Android Studio 进行构建，系统会自动完成此操作。本文档适用于自定义构建系统的维护者。