
## 前言

之前整了一个Gitee page 的笔记。

[gitee+picgo 图床上传图片404](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/gitee 图床上传图片404.md)

[基于html拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/如何基于gitee pages 搭建一个属于自己的主页.md)

[基于Jekyll拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/2020-09-29-基于Gitee pages+Jekyll拥有一个属于自己的主页.md)

[Gitee Pages Jekyll如何才能放到子目录和非同名项目中](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/Gitee Pages Jekyll如何才能放到子目录和非同名项目中.md)

[基于Hexo拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/基于Gitee pages+Hexo拥有一个属于自己的主页.md)

[Hexo+Gitee pages进阶](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/Hexo+Gitee pages进阶.md)

[基于Hugo拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/基于Gitee pages+Hugo拥有一个属于自己的主页.md)

因为Jekyll 和Hexo的经验让我写这个笔记的时候索然无味。话说hugo 也是提交的静态网页到同名项目到根目录，然后还配置了baseurl才可以的。

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

## 正文

作为一个杠精，我觉得，既然Jekyll，hexo 都可以上传源码和静态网页到同名项目和非同名项目。那么我Hugo必定可行，只是我没有找到解决方式罢了。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923150707.png)

通过Jekyll和Hexo的经验，都是编译后提交才显示成功的，而且根目录可以子目录就一定可以，子目录可以根目录也可以。同名项目和非同名项目在Gitee page 上的部署的区别主要在于设置。主题可能影响部署中资源文件使用情况。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200910104356.png)

那么就开整。

还是直接上教程：https://gitee.com/help/articles/4136#article-header0

> 1. 编译 Hugo 依据：仓库编译目录下存在`config.toml|json|yaml`文件和`content`目录的时候，会使用`hugo`生成静态文件。

这个说明可以部署源码。

> Hugo 配置文件`config.toml`的baseURL修改如下
>
> ```
> baseURL = "https://ipvb.gitee.io/blog"
> ```

> hugo new site quickstart
>
> 切到项目目录下主题。
>
> ```
> git submodule add https://github.com/budparr/gohugo-theme-ananke.git themes/ananke
> ```

> 修改config 中的theme = "ananke" 

本地开启server

> hugo server -D

浏览器打开：http://localhost:1313/ 当没有问题的时候，就可以直接考虑部署情况了。先编译一次：

> hugo -D

因为需要涉及到同名项目和非同名项目中部署源码和静态文件。也都涉及到子目录和根目录。那么我们将项目代码复制到一个page子目录中，修改其中内容。重新两个都重新编译。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201014155147.png)

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201014155226.png)

### 同名项目

通过上面的图片。可以看到我们将静态网页，和项目代码复制子文件夹都上传上去了。OK直接404，通过第一次使用Hugo的经验，我觉得可能是需要设置baseurl。但是我不死心的直接部署了下public中的内容的时候，发现竟然可以显示。

> 编译 Hugo 依据：仓库编译目录下存在`config.toml|json|yaml`文件和`content`目录的时候，会使用`hugo`生成静态文件。

于是我重新读了下这个编译依据，发现content是必须包含才能编译的，但是我content为空的时候，gitee上面没有这个目录。ok 问题找到了，为了简单，我们就直接复制 主题的中的Demo内容到项目中，然后重新编译。上传。但是还是404 ？使用静态网页的时候，图片资源链接地址居然是:http://example.org/post/chapter-6/ ,上面没有配置baseurl 的时候图片也显示不出来。难度真的是baseurl 的问题，于是我又配置了baseurl .结果运行源码还是404，![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/064F9061-FFED-438A-8749-54415D223028%E7%9A%84%E5%89%AF%E6%9C%AC2.png)，运行静态文件却是没有问题了。

算了，鲁迅曾经说过，成年人最大的优点就是及时止损。这Hugo 源码项目我就不运行了。主要是编译后的静态文件也上传了，还是我缺少什么文件配置哦，也搞不懂，毕竟就这么几句话，不会是不能上传编译后的文件吧。还是他Hogo server 就没有开起来，但是hugo server 没有开起来，hugo 生成的静态文件也跑不起来啊。

### 非同名项目

既然同名项目都需要配置baseurl,那么非同名项目也差不多吧。今天就先水到这里。告辞。

## 总结

反正baseurl 是必须配置的。生成的静态文件放到根目录和子目录都是可以运行的。至于为啥项目源码运行不了，我觉得肯定是我漏了什么，但是有点迷，找不到。以后找到了再更新吧。好像gitee 有一个技术交流群，容我抽时间去请教下，如果大佬您知道，麻烦告知下，谢谢了。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

