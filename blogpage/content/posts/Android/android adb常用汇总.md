+++
date = "2021-6-11"
title = "android adb常用功能汇总"
description = "android adb常用功能汇总"
tags = [ "adb"]

featured = true
+++
## 正文
#### adb 安装apk
adb install D:\androidProject\DGGChips\app\d_environment\release\d_environment2021061001.apk

#### adb app 启动时长 
 adb shell am start -W -n com.chips.cpscustomer/com.chips.module_main.ui.start.StartPageActivity