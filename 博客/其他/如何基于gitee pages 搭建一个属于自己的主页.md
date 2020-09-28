![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

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

支持多html结构，css,js 等都可以放到外面。emmmm？vue  那是不是也行？ vue 提供的单页应用是不是也可以？这个就需要自己验证了，毕竟我不会这个。我看了下这个界面的代码，这div 太多了吧。我感觉我不行，我还是适合 用下面的博客主页生成工具。

### Jekyll

既然自己写静态网页对于我这种小白而言太复杂了，那么用博客主页生成工具就可以了，毕竟鲁迅曾经说过"成年人最大的有点就是及时止损"。

Jekyll 使用文档：https://www.jekyll.com.cn/docs/home/。

这个要安装[Ruby](https://www.jekyll.com.cn/docs/installation/) 环境？![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923150601.png)

这丫的的一个脚本语言，我觉得我还是用html 比较好，毕竟我这个是会的。

#### 基于IDEA 安装环境

先整一个ruby教程[runoob Ruby 教程  ](https://www.runoob.com/ruby/ruby-tutorial.html)。这个感觉和Python很像嘛。那就开整。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928092244.png)

这个调调的使用感觉和Node.js 很像啊，不会要Node.js 环境吧。怕是需要看一下[Ruby 使用安装环境](https://www.jekyll.com.cn/docs/installation/#requirements) 。

我的是mac 环境。

![image-20200928092758280](/Users/yangfan/Library/Application Support/typora-user-images/image-20200928092758280.png)

这个是要Xcode ? 我一个Android  搬砖工，也没有安装这个调调啊，我觉得我的IDEA可以完成这个调调。那么直接[IDEA 插件官网](https://plugins.jetbrains.com/)。[IDEA Ruby 插件 ](https://plugins.jetbrains.com/plugin/1293-ruby)  根据自己的IDEA 版本号下载对应的版本就好，版本对不上就安装不了。然后自己将下载下来的插件安装到自己的idea 里面就好。然后就是用IDEA自身功能下载sdk就好。然后创建一个Ruby的空项目，创建一个Ruby文件。配置ruby的运行项中的sdk ：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928103005.png)

如果可以正常运行，那么环境就配置好了。当然也可能是运行项没有配置好，主要是配置sdk.这个必须要配置。

#### 下载运行Jekyll Demo 

现在就是按照[官网jekyll 操作指南](https://www.jekyll.com.cn/docs/) 下载 Jekyll 和创建环境就好。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928104822.png)

下面来说说遇到的问题，

- 1和2 需要 在前面加sudo  要不然可能没有目录权限，需要输入密码。
- 2 是在当前目录下创建一个myblog 目录用于生成文件。
- 3是进入目录，这个很重要，目录错了会导致运行不了。
- jekyll --version 可以查看版本号。如果有说明这个安装成功了的。
- bundler: failed to load command: jekyll (/usr/local/bin/jekyll) 这个问题，我从网络上找到的说需要 执行bundle install。
- 上面的问题是运行bundle exec jekyll serve 导致的，如果没有这个问题，直接打开 [http://localhost:4000](http://localhost:4000/) 会出现这个界面：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928105800.png)



通过目录结构:

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928110004.png)

盲猜一手，Gitee page 应该使用 箭头指向的目录吧。[目录结构](https://www.jekyll.com.cn/docs/structure/) 

#### 模板使用

好了，Demo 就运行完成了，下面就是找到一个好看的[模板](https://www.jekyll.com.cn/resources/)，然后整进去。比如我随便找的一个，提供的导入方式：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928113425.png)

前两个是修改对应的文件中的值，如果没有就添加。

命令行中 bundle 编译。最后就是 开启服务bundle exec jekyll serve ，打开 [http://localhost:4000](http://localhost:4000/) ：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928113657.png)



然后就成功了。

#### 迷惑

这个调调，我应该改那个文件，让他达到我想要的效果哦，

### Hugo

### Hexo



## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
因为可能发布多个平台，而且更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

[基于gitee pages的个人主页](http://lalalaxiaowifi.gitee.io/pictures)
