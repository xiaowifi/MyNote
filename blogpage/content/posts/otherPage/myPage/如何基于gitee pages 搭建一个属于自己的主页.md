
## 前言

话说，今天逛沸点的时候，看到一个大佬在推他的个人主页<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923150601.png" style="zoom:25%;" />，感觉很有意思<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923150707.png" style="zoom:33%;" />。

这么好的东西，其实我也想要。那么就开整。需要先找到[官方文档](https://gitee.com/help/articles/4136#article-header0) ，毕竟官方提供的才是原汁原味的嘛(但是有些人提供的就没有那个味道了<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923151008.png" style="zoom:50%;" />)。 

但是呢，东西是一个好东西，可惜我不会。gitee 推荐了3中静态网页方案，Jekyll、Hugo、Hexo ，这个，巧了，我又不会<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png" style="zoom:25%;" />。突然想起大学的老师，老师们好像有教静态网页的吧，巧了，我好像没有去上课。

但是，作为一个成年人，敌方这么努力的守水晶，我都要想办法推掉它的人，岂是这种小问题能够阻挡的。

> Jekyll、Hugo、Hexo 究竟是什么？
>
> Jekyll、Hugo、Hexo 是简单的博客形态的静态站点生产机器。它有一个模版目录，其中包含原始文本格式的文档，通过 Markdown 以及 Liquid 转化成一个完整的可发布的静态网站，你可以发布在任何你喜爱的服务器上。Jekyll、Hugo、Hexo 也可以运行在 码云（Gitee.com） Pages 上，也就是说，你可以使用码云的服务来搭建你的仓库页面、博客或者网站，而且是完全免费的。
>
> Jekyll 使用文档：https://www.jekyll.com.cn/docs/home/
> Hugo 使用文档：https://gohugo.io/documentation/
> Hexo 使用文档：https://hexo.io/docs/

通过gieee提供的教程，我看了下这个这几个教程，

<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/064F9061-FFED-438A-8749-54415D223028%E7%9A%84%E5%89%AF%E6%9C%AC2.png" style="zoom:50%;" />

我觉得作为一个成年人，应该学会及时止损。摸鱼应该比学习这个有意思多了，是吧。但是，静态网页我好像会？

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

## 正文

反正都是摸鱼，只要不学习JAVA，sql,Android,c，ndk 之类的都应该算摸鱼吧，这么一想，瞬间就有动力了。这个摸鱼的概率大一点，还是建议看[官方文档](https://gitee.com/help/articles/4136#article-header0) 。先按照步骤来。

- 注册一个gitee 账号https://gitee.com/ (有账号的跳过这一步)
- 通过自己的账号创建一个公开的项目(个人主页搞私有的，别人访问不到);确定有master 分支
- 在项目的服务中找到gitee pages 点击，按照步骤一步一步的开始gitee pages 。这个时候你就得到了自己的个人主页的域名了。
- 在对应的文件夹下面创建一个index.html 文件（这样通过域名访问就不会404了） 
- 最后通过 静态网页或Jekyll、Hugo、Hexo等博客主页生成工具实现自己的主页。
- 把做好的网页更新上去，然后在项目的服务中找到gitee pages 更新自己的主页。

### 静态网页

通过gitee pages 的文档我们可以知道，要想基于它创建主页需要下面几点：

- 静态网页
- 必须包含**index.html**

据说，只要不进行网络请求的网页都可以叫静态网页？那可操作性就很大了啊。比如说，可以通过网络上下载静态网页模板，自己魔改。比如说这个是我从模板王上下载的一个个人主页：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923160628.png) 

本地是可以打开的。我没有修改的情况下，直接更新到gitee上面，通过gitee给我生成的域名打开。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923161425.png)

Emmmm?  好像可以，如果打开的网页没有更新到，需要清理下浏览器缓存就好了。

我们来看一下这个网页的结构：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923161634.png)

支持多html结构，css,js 等都可以放到外面。emmmm？vue  那是不是也行？ vue 提供的单页应用是不是也可以？这个就需要自己验证了，毕竟我不会这个。我看了下这个界面的代码，这div 太多了吧。我感觉我不行，我还是试试用博客主页生成工具吧。

## 结束

这个2020-09-24 就写好了，就突然像把Jekyll、Hugo、Hexo 这3个一起整出来。我觉得是那天拉肚子导致思想出了问题。中途没有更新是因为请假回家了没有带电脑回去，急性肠胃炎是真的猛，我一猛男拉了两天肚子。打大乱斗都不想和队友聊家常了。最近LOL s10 入围赛 感觉LGD 是真的迷。

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 
