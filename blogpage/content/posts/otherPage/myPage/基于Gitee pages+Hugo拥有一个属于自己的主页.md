
## 前言

之前整过一个静态网页，一个Jekyll，一个hexo.话说，如果我知道的不完整，我怎么才能知道什么才是适合自己的呢？<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010140942.png" style="zoom:50%;" />

那么，就开整。

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

## 正文

老规矩，还是先贴官方教程:

Gitee pages 适用文档: https://gitee.com/help/articles/4136#article-header0

Hugo 使用文档：https://gohugo.io/documentation/

> Hugo is the **world’s fastest static website engine.** It’s written in Go (aka Golang) and developed by [bep](https://github.com/bep), [spf13](https://github.com/spf13) and [friends](https://github.com/gohugoio/hugo/graphs/contributors).

不说别的，光说上面的这一句话包含的意思，一个Go，一个最快。我就应该先了解这个，了解这个之后，前面几个笔记也不会这么苦逼的写了，直接放弃就好。

### 环境

既然是基于Go,那么IDEA 开发环境还是需要的。老规矩，还是先整插件:https://plugins.jetbrains.com/

 https://plugins.jetbrains.com/plugin/9568-go 下载和IDEA版本号相同的插件。

因为网络问题，GO SDK 在IDEA 中下载缓慢，甚至版本号都拉不到，所以需要自己手动下载安装。

https://golang.google.cn/dl/ 

### 安装运行

不同的系统环境，安装的方式不同。详情看教程：https://gohugo.io/getting-started/installing 。就因为我没有看这个教程，我的电脑更新Homebrew 已经更新10分钟了。因为安装的方式太多了，所以就不截图介绍了。

> hugo version 通过这个验证版本号，同时验证安装是否成功。有版本号就成功了，没有应该就是没有成功。

但是一项脑洞到处跑的我，突然想到了 hugo 可能出现在IDEA的插件中![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009104008.png)

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010144637.png) 

我果然在IDEA 插件库中找到了一个。但是本地还是需要一个安装Hugo.但是也不是没有用，起码可以直接运行项目。

通过Homebrew安装过程中或许会出现:

> The following directories are not writable by your user:
>
>  /usr/local/sbin

好像是没有权限，终端执行：

> sudo chown -R `whoami`:admin /usr/local/sbin  
>
> 注：(/usr/local/sbin 是抛错提供的地址，错误是什么地址改成什么地址就好，应该是这个样子，如果解决不了，麻烦baidu下，我也不晓得)

如果版本验证成功之后，就可以通过下面语句在终端中执行，生成项目了。

> hugo new site quickstart
>
> quickstart是项目名称。而且终端打开是有目录的，建议切到需要创建项目的目录再执行上面语句。

当创建目录中出现内容，且没有抛错误的情况下，逻辑上这个调调就创建好了。

#### 运行

按照之前Jekyll和Hexo的经验来说，创建好了，就应该可以运行。在项目目录终端执行：

> hugo server -D

我在之前下载了一个Hogo 插件，那么直接通过这个插件运行项目是否是可以的呢？

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010153759.png)

这么对项目进行配置之后，发现和hugo server -D 效果是一样的。

运行成功后打开：http://localhost:1313/ 终端中会提示。

竟然是一个空界面？就因为我没有设置主题？根据提示是因为在layouts中没有一个叫home.html 的文件？那我创建一个不就好了

#### 导入主题

抄一波官方文档。

> 请参阅[themes.gohugo.io](https://themes.gohugo.io/)以获取要考虑的主题列表。本快速入门使用了精美的[Ananke主题](https://themes.gohugo.io/gohugo-theme-ananke/)。
>
> 首先，从GitHub下载主题并将其添加到您站点的`themes`目录中：
>
> ```bash
> cd quickstart
> git init
> git submodule add https://github.com/budparr/gohugo-theme-ananke.git themes/ananke
> ```
>
> *对于非git用户的注意事项：*
>
> - 如果您尚未安装git，则可以从以下[网址](https://github.com/budparr/gohugo-theme-ananke/archive/master.zip)下载此主题的最新版本的存档：[https](https://github.com/budparr/gohugo-theme-ananke/archive/master.zip) : [//github.com/budparr/gohugo-theme-ananke/archive/master.zip](https://github.com/budparr/gohugo-theme-ananke/archive/master.zip)
> - 解压缩该.zip文件以获得“ gohugo-theme-ananke-master”目录。
> - 将该目录重命名为“ ananke”，并将其移至“ themes /”目录。
>
> 然后，将主题添加到站点配置中：
>
> ```bash
> echo 'theme = "ananke"' >> config.toml
> ```

需要将在config.toml 文件中新增：

```
theme = "ananke"
```

然后运行，还是空界面？那就是没有设置主题。

#### 添加些内容

继续抄官方文档：

> 您可以手动创建内容文件（例如作为`content/<CATEGORY>/<FILE>.<FORMAT>`）并在其中提供元数据，但是您可以使用该`new`命令为您做一些事情（例如添加标题和日期）：
>
> ```
> hugo new posts/my-first-post.md
> ```
>
> 如果需要，请编辑新创建的内容文件，它将从以下内容开始：
>
> ```markdown
> ---
> title: "My First Post"
> date: 2019-03-26T08:47:11+01:00
> draft: false`
> ---
> ```
>
> 草稿不会被部署；完成帖子后，将帖子标题更新为`draft: false`。更多信息[在这里](https://gohugo.io/getting-started/usage/#draft-future-and-expired-content)。

通过在终端执行：hugo new posts/my-first-post.md 

就可以得到下面效果，根据文档介绍将 draft: false

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010154641.png)

然后再运行项目。

复制一个md文件，修改title ,得到了：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010155509.png)

#### 目录结构介绍

官方提供：https://gohugo.io/getting-started/directory-structure/

太长了，不好复制，

### 了解

首先还是要了解目录结构：https://gohugo.io/getting-started/directory-structure/。也方便了解他人主题中的代码。https://gohugo.io/getting-started/configuration/ 可以找到配置项，和Jekyll和Hexo 差不多，都是基于config 文件进行配置。

- 基于config 配置项目。
- 主题存在于themes目录中
- layou是模板
- static 是静态资源

反正知识点很多，就不一一列举了。

#### 适用一个喜欢的主题

https://themes.gohugo.io/，可以通过这个网站找一个喜欢的主题，当然了也可以自己写一个(本笔记也不提供这个调调，有点难为我，还是自己看官方文档吧)。比如说我比较喜欢下面的这个主题：https://github.com/luizdepra/hugo-coder/，根据提供的导入方式。

> git submodule add https://github.com/luizdepra/hugo-coder.git themes/hugo-coder

将项目根目录下的config 中的theme 赋值：

```
theme = "hugo-coder"
```

同时 这个主题提供了几乎完整的接入文档。

https://github.com/luizdepra/hugo-coder/wiki/Configurations#complete-example

同时主题中的exampleSite也几乎包含了该主题使用Demo。为了简单就直接复制Demo重的内容到项目中运行看效果。

#### Menus

直接整文档：https://gohugo.io/content-management/menus/#a-single-menu 。从这个文档可以非常直观的看到Menus的定义。

#### 部署到Gitee 

话说，这个我也翻了好多篇博客，才找到的解决方案。我一开始以为这个调调和Jekyll一样不需要编译直接上传就可以，然后发现需要通过：

> hugo -D

编译，我以为编译后直接像Hexo一样上传到根目录就可以了，然后还行不行。所幸看到一个大佬说应该上传public文件夹中所以内容。当我将public 所有内容推到gitee 上面，更新gitee pages 的时候，显示的和本地预览完全不一样。然后又开始翻大佬们的博客，终于翻到了，需要修改baseurl为gitee pages 提供的地址。当我将地址复制进去，编译再提交的时候，发现还是一样，确定大佬的博客后，将/添加到地址后面，像这个样子：

```
baseurl = "http://lalalaxiaowifi.gitee.io/" 
后面的斜杠很重要。
```

再次更新，

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201012151102.png)

总于显示内容了，除了效果不对。但是点击其他地方也没有404，个人觉得很奈斯。总结下吧。

- 每次更新前需要执行 hogo -D 编译项目，编译后会更新生成一个public 文件夹
- 上传到gitee 上面的是public中的所有内容
- 如果不考虑其他的，只需要提交到根目录就好。
- Baseurl 一定要修改，且要以斜杠结尾/////////

### 总结

2020-10-12，总于把静态网页，Jekyll，Hexo，hogo 在gitee pages 上的简单处理整完了。现在总结下吧。

- 如果Gitee pages 更新没有效果，清空浏览器缓存再试一次。
- 如果不进行设置，通过静态网页可以直接放到非同名项目（同名项目是指：gitee 上的名字和项目名称一致）上面显示，也可以直接放到子目录中。
- Jekyll 可以通过直接提交项目到同名根目录上，开启就可以加载出来，好像是不需要更新baseurl。但是如果把笔记完全抛给别人了，虽然Demo 很好看，也可以通过下载Jekyll demo 查看主题适用方式，但是明显感觉支持多套主题。
- hexo 需要编译后提交编译后的项目到git.也可以直接放根目录，不配置的话，需要放到同名项目中。但是支持多套主题，通过一键部署，而不是通过手动git 提交。编译后别人就无法像Jekyll那样直接看到md 文件了。接入主题感觉比较吃主题作者的文档，要不然需要自己看代码(或许有Demo，只是我没有找到)，github 上面找到的都是主题代码。
- hogo 也是需要编译后提交，手动git 提交。支持多套主题，而且主题中包含该主题的适用demo(很舒服)，我看有大佬将hogo 放到了非同名项目中，需要配置baseurl,当然其他几个也有baseurl ,感觉还是需要配置。
- 我感觉只要baseurl和root 设置对了，gitee pages 是支持非同名项目布置主页的。毕竟Gitee Pages 教程中也就只有这两个的配置。

说一个个人感觉，我觉得hogo还是要比其他两个好一点。容我整理一波，毕竟hogo也就是了解，毕竟用别人的主题，提供Demo，和多主题切换什么的。

## 更新
2020-10-14 16:45:07 
[Hugo+Gitee pages补充](https://gitee.com/lalalaxiaowifi/pictures/blob/master/%E5%8D%9A%E5%AE%A2/%E5%85%B6%E4%BB%96/Hogo+Gitee%20pages%E8%BF%9B%E9%98%B6.md)

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 
