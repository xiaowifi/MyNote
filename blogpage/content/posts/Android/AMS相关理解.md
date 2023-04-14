## 前言
如果说，站在4大组件的角度上而言，AMS就是binder中的Server。
AMS的全称是activityManagerService,字面意思上只是管理activity，但是4大组件都归他管理。
那么我们来思考一个问题，为什么我们代码hook的都是binder的client端，而不是AMS。
> 因为AMS是整个系统的，改了整个系统都被改了，所以不允许改。
# 正文
