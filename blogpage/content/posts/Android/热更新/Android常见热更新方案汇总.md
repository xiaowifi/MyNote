# 前言
## 资料
* [Robust 美团热更新](https://github.com/Meituan-Dianping/Robust/blob/master/README-zh.md)
* [动态加载so库的Demo](https://github.com/TestPlanB/SillyBoy)
* [Tinker 腾讯系热更新方案](https://github.com/Tencent/tinker/wiki)
* [mpaas 热修复](https://help.aliyun.com/document_detail/115249.html)
* [bolog Android动态加载so！这一篇就够了！](https://juejin.cn/post/7107958280097366030)

# 正文

|       | Tinker | QZone | AndFix | Robust |
| ---------- | ----- | ------ | ------ | ---- |
| 类替换     | yes   | yes    | no     | no   |
| So替换     | yes   | no     | no     | no   |
| 资源替换   | yes   | yes    | no     | no   |
| 全平台支持 | yes   | yes    | yes    | yes  |
| 即时生效   | no    | no     | yes    | yes  |
| 性能损耗   | 较小  | 较大   | 较小   | 较小 |
| 补丁包大小 | 较小  | 较大   | 一般   | 一般 |
| 开发透明   | yes   | yes    | no     | no   |
| 复杂度     | 较低  | 较低   | 复杂   | 复杂 |
| gradle支持 | yes   | no     | no     | no   |
| Rom体积    | 较大  | 较小   | 较小   | 较小 |
| 成功率     | 较高  | 较高   | 一般   | 最高 |
