![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

之前整了一些笔记。

[gitee+picgo 图床上传图片404](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/gitee 图床上传图片404.md)

[基于html拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/如何基于gitee pages 搭建一个属于自己的主页.md)

[基于Jekyll拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/2020-09-29-基于Gitee pages+Jekyll拥有一个属于自己的主页.md)

[Gitee Pages Jekyll如何才能放到子目录和非同名项目中](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/Gitee Pages Jekyll如何才能放到子目录和非同名项目中.md)

[基于Hexo拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/基于Gitee pages+Hexo拥有一个属于自己的主页.md)

[基于Hugo拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/基于Gitee pages+Hugo拥有一个属于自己的主页.md)

都是基于Gitee 的。反正最近很闲，没有多少动力学习Android，其实没有完成的todo 还是蛮多的。但是就想摸鱼。毕竟越是难的东西，花费的精力就越多，像网上哪些多少天就学会什么成为高阶开发什么的，对我完全不适用，除非就真的很简单，但是会简单的也成不了高阶吧。话说回来，Gitee pages 主页就比较简单了，至于为啥药写这么多简单的笔记，主要锻炼自己总结能力和逻辑能力吧。

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

## 正文

话说，之前有一个[基于Hexo拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/基于Gitee pages+Hexo拥有一个属于自己的主页.md) 。这个是推的编译后的文件到同名项目的根目录。而一个人只有一个同名项目，同时在gitee pages上只能开启一个主页。对于我这种脑洞满天飞，且不断作死的baby.我觉得，既然人家文档都提供了，不利用起来![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923150707.png)

那就开整，还是先贴文档。https://gitee.com/help/articles/4136#article-header0 

> 1. 编译 Hexo 依据：仓库编译目录下存在`package.json`，`_config.yml`文件和`scaffolds`目录的，会使用`hexo generate`生成静态文件，由于每次部署编译需要重新克隆编译并进行`npm install`，所以使用 Hexo 的时间相对 Hugo 和 Jekyll 会长一些。

哦哦，理解一波，这个可以直接提交项目源代码。

> Hexo 配置文件`_config.yml`的`url`和`root`修改如下：
>
> ```
> url: https://ipvb.gitee.io/blog
> root: /blog
> ```

这个理解一波，就是可以提交到非同名项目下。基于之前Jekyll 使用github 主题导致问题。那么，我们这个hexo就不用别人的主题，直接使用系统提供的。这里解释下，Github 对Github pages 对应的项目名有明确的要求，不像Gitee pages 玩的这么花，如果是放在同名根目录下是没有问题的，如果是放到非同名项目中。那么可能因为主题作者没有适配一些东西，导致资源文件找不到，还是建议放到同名根目录，毕竟个人主页嘛，一个就好。也没有一些莫名其妙的问题。

### 源码提交

之前那篇笔记是通过一键部署到同名项目的根目录下的。既然是提交项目源代码，那么就不能用一键部署了咯。那就开整。之前本地安装过环境。就直接创建项目了。

```
hexo init myblog
npm install
```

稍微修改写congfig 中的内容。

本地开启服务。然后打开http://localhost:4000/，如果运行没有抛错误，且界面显示正常，那么将当做他成功了吧。

#### 同名项目

我Gitee 的用户名是lalalaxiaowifi，那么就需要创建一个名叫:lalalaxiaowifi 的项目了。

##### 根目录

直接将刚刚生成的项目代码直接提交到根目录。根据上面的Hexo 编译依据。我将代码提交到了master 分支，所以直接部署master 分支的根目录。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201014112407.png)

OK，直接404？我还等了这么久。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201014113621.png)

Gitee pages 是检测index.html,这明显是index.html都没有找到。emmmm？等于说在Gitee Pages 中编译可能失败了。

> 1. 编译 Hexo 依据：仓库编译目录下存在`package.json`，`_config.yml`文件和`scaffolds`目录的，会使用`hexo generate`生成静态文件，由于每次部署编译需要重新克隆编译并进行`npm install`，所以使用 Hexo 的时间相对 Hugo 和 Jekyll 会长一些。

编译生成的文件处于public 中。

文档上面说会使用`hexo generate` 生成静态文件啊。那我本地生成一次静态文件提交试试。我这把默认把public 设置到git忽略里面去了，需要找到**.gitignore** 文件将**/public** 移出掉。然后提交试试。 

好像成功了。难道是项目配置有问题，导致Gitee pages 无法编译项目，而只是提供了一个环境，并且开启了服务？而且，我界面加载出来了，怎么加载进度条还在中间跑。虽然有这么多的问题，但是还是说明部署源码到根目录是没有问题，只是需要本地编译下，生成public 之后上传。因为资料有限，暂时我无法得知，究竟应该如何配置才能使得hexo generate 编译成功，现在的解决方式就是本地编译，将源码和本地生成的静态文件一起上传上去。

##### 子目录

现在就是测试，将代码复制到子目录中是否可以编译运行了。首先看git的忽略配置

```
.DS_Store
Thumbs.db
db.json
*.log
node_modules/
.deploy*/
```

默认是忽略public的，因为我配置问题，导致需要上传public。那么我们将上面代码复制到一个子文件夹page中。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201014140703.png)

忽略文件不会提交到服务器，所以就不用复制了。然后提交更新，设置分支为master,目录为page，为了不受缓存影响，可以修改下内容重新编译下。本地编译后，是可以放到子目录的。

#### 非同名项目

既然同名项目可以放源码到根目录和子目录。那么根据这个:

>Hexo 配置文件`_config.yml`的`url`和`root`修改如下：

```
url: https://ipvb.gitee.io/blog
root: /blog
```

盲猜一手，可以放到非同名目录。之前写Jekyll毕竟的时候，有一个项目，那么就还是使用那个项目，先将正在运行的主页关闭掉。为了备份方便，我又将代码复制了一份。就不再走上面的创建项目的流程了，直接修改config 文件，和git 仓库地址就好。当然了，设置之后需要本地编译一下，同时运行下，看本地是否有问题。写到这里就突然索然无味了，有点鸡肋。按照jekyll的经验而言。肯定是没有问题的，主要是gitee pages 部署时间太长了。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201014144310.png)

##### 根目录

部署分支master。部署目录 不填。成功。

##### 子目录

部署分支master。部署目录 page。成功。

### 静态网页

从上面可以知道。通过 hexo generate 命令可以编译项目生成静态文件，而生成的静态文件处于根目录下public中。 

#### 同名项目

##### 根目录

通过一键部署的方式就是部署到根目录下的，所以这个可以不写了。

##### 子目录

按照道理讲，这个也是可以的，主要是部署目录需要的指向子目录。

#### 非同名项目

这个就需要和上面非同名目录源码一样，设置:

> url: https://ipvb.gitee.io/blog
> root: /blog

然后编译生成静态文件。

##### 子目录

通过上面的经验总结一下。根目录行的情况下，子目录也可以。反证一波，只要子目录可以，根目录是不是也可以。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png)那我们直接运行上面项目中的public .

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010144637.png)



果然静态文件是要比Hexo部署快很多，清理下浏览器缓存。OK是可以的，那根目录就不尝试了。

## 总结

哇，由于昨天的Jekyll的折磨，今天摸鱼显得格外的快。写这个笔记也没有想什么，主要是Jekyll都可以放源码和静态文件，为啥我最开始没有实现。

- hexo 在gitee pages是支持直接放源码的，因为我配置原因，需要本地编译后上传。
- hexo 源代码部署 支持同名项目和非同名项目，非同名项目下需要配置congif. 
- hexo 生成的静态网页部署 支持同名项目和非同名项目，非同名项目下源码需要配置congif. 然后重新编译上传。

因为我都是通过git 手动上传代码，所以没有涉及到一键部署。个人感觉用不习惯一键部署。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

