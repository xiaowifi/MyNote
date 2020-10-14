![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

之前写了点gitee pages 相关的笔记。

[基于html拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/如何基于gitee pages 搭建一个属于自己的主页.md)

[基于Jekyll拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/2020-09-29-基于Gitee pages+Jekyll拥有一个属于自己的主页.md)

[基于Hexo拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/基于Gitee pages+Hexo拥有一个属于自己的主页.md)

[基于Hugo拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/基于Gitee pages+Hugo拥有一个属于自己的主页.md)

但是都是基于同名的项目实现的，而且都是推到根目录下的。Jekyll 还是推的源码上去，hexo和Hugo都是推的编译后的文件。因为Jekyll，Hexo，Hugo都是用的静态网页，所以静态网页也是可以直接用的。顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

## 正文

但是，我昨天认真工作(摸鱼的时候)突然想到了一个问题，既然都是生成的静态网页，为啥我直接写的静态网页可以放到非同名项目的子目录下运行，为啥Jekyll，hexo ,hugo 等就不行呢，按道理讲，都是编译生成静态网页啊。先放一个静态网页成功的例子：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013094954.png)



![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013095041.png)

运行效果就先不贴了。那么问题来了，是什么导致gitee pages 上面Jekyll，Hexo，Hugo 生成的静态网页用不起的呢？为啥一定要推到根目录，且一定要同名项目呢？还是关于这个配置，我的理解有问题。先贴一个gitee pages 提供的教程：https://gitee.com/help/articles/4136#article-header0 

> ###  Jekyll、Hugo、Hexo 编译判断依据
>
> 1. 编译 Hugo 依据：仓库编译目录下存在`config.toml|json|yaml`文件和`content`目录的时候，会使用`hugo`生成静态文件。
> 2. 编译 Hexo 依据：仓库编译目录下存在`package.json`，`_config.yml`文件和`scaffolds`目录的，会使用`hexo generate`生成静态文件，由于每次部署编译需要重新克隆编译并进行`npm install`，所以使用 Hexo 的时间相对 Hugo 和 Jekyll 会长一些。
> 3. 当不符合上述1和2条件的时候，就默认使用Jekyll编译。

通过教程上面的这个内容，个人理解，Jekyll ，hugo，hexo 按道理讲都应该支持上传项目源码的啊。为啥我的只有jekyll 上传源码到同名根目录才可以有效果？其他的都不行呢？这个坑留着，后面慢慢解。另外还有一个问题。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013100608.png)

第2点中的:**生成的资源 url 应该为 https://ipvb.gitee.io/blog/style.css 才对** ,我觉得他证明了非同名项目，且在字目录中是可以实现个人主页的。上传上去的效果不正确，那就是资源文件的url 不正确。于是我采集了静态网页显示正常的图片的url和jekyll 显示不正常的url.

静态网页显示正常的url:http://lalalaxiaowifi.gitee.io/pictures/images/g2.jpg

 jekyll 配置不正常显示的url:http://lalalaxiaowifi.gitee.io/images/avatar.jpg   这个不正常的url 在同名项目的根目录下是运行正常的，我把整个Jekyll项目放到了pictures/tree/master/Blog/jekyllBlog 下面，等于说我的项目名叫pictures，使用分支是master，根目录下的Blog/jekyllBlog，他就显示不正常了，但是通过对比两个url，发现放到其他目录下，生成的链接地址都是错误的(原因参考 上面图片的第2点)。因为这个也证明了我之前的一个猜测，个人用户应该只有一个主页。开启一个后，其他的必定会被关闭，个人理解Gitee pages 对应的localhost 就是个人根目录，比如说我的根目录应该就是：http://lalalaxiaowifi.gitee.io，再比如说我的pictures地址就应该http://lalalaxiaowifi.gitee.io/pictures。也确实如此。那么既然如此，那就按照Gitee Pages 提供的再配置一次。

#### Jekyll 

因为之前有写了一个简单的Jekyll笔记。[基于Jekyll拥有自己的主页](https://gitee.com/lalalaxiaowifi/pictures/blob/master/博客/其他/2020-09-29-基于Gitee pages+Jekyll拥有一个属于自己的主页.md) ，之前因为某种不可抗拒的原因(<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923150601.png" style="zoom:33%;" />)导致直接放到同名项目中了。那么现在就直接整到非同名项目中。

- 创建一个名叫 jekylldemo 的项目，先设置为公开的。https://gitee.com/lalalaxiaowifi/jekylldemo
- 将其他主页暂停了。
- 将之前同名项目先提交到jekylldemo这个库的根目录。
- 配置jekyll 

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013104032.png)

根据这个，我们将 

```
baseurl: "/jekylldemo"
```

修改到_config.yml中。但是有些资源文件找不到。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013115758.png)

但是不配置baseurl：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013115409.png)

虽然在感觉上都是资源文件没有找到，但是明显的区别是：

- 配置 baseurl 之后，部分css ,js 等资源是获取得到的。
- 没有配置baseurl 却是所有资源文件都没有找到。
- 总结，配置baseurl后，起码离成功又近一步了。

因为是Android 搬砖工，对网页技术还是欠缺的，中途猜了很多问题，突然想到了，我适用的主题通常是基于github.github是不允许有这么骚的操作的，对于pages 是有固定的设置的。emmmm？那会不是是我baseurl 在代码中没有生效。我翻了好多博客，发现人家提供的教程就很简单，也没有出现我这种问题啊。那我直接整一个自带主题的那种项目上去？

##### Jekyll 放到非同名项目根目录

那就开整？

Jekyll new myblog

修改_config 中的baseurl :

> baseurl: "/jekylldemo"
>
> 因为我创建的项目就叫jekylldemo 

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013151502.png)

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013151750.png)

突然心态有点崩，因为找了很久的博客，要不是突然想到之前的顿悟："我所遇到的问题，可能在之前别人已经遇到了，如果使用一个东西，别人没有遇到相同的问题，那就是自己使用的问题，而且这个问题往往过于简单导致别人不会遇到这个问题"。所以，我觉得是我使用的主题的问题。这个主题放弃了吧。总结下吧。

- Gitee Pages 和 Github Pages 还是有区别的，至少在主题使用方面。如果是和Github一样创建同名项目，那么gitee 上使用gitbub 上面的使用的主题是没有问题的，但是如果使用非同名项目的时候，如果主题作者没有对 baseurl 进行扩展（因为github 好像不需要配置baseurl,他规定了项目名的![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923151008.png)）那么很可能 主题使用就会出现资源找不到。但是使用同名项目的时候，暂时却没有发现这种问题。导致这种问题出现的原因我觉得是gitee pages 的localhost 默认是个人主页，而不是单纯的项目，所以需要配置baseurl.

____

##### 将Jekyll放到非同名子目录

问题来了，Jekyll的项目代码可以放到子目录下面执行吗？![](https://i.loli.net/2020/09/22/RMG8JalA42dPnos.png)

那就开整？为了方便，那么我就直接将代码复制到pictures项目中。当然baseurl还是需要改成pictures。然后更新，清除缓存。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013153614.png)

奈斯，运行成功。

#### jekyll静态网页

 鲁迅曾经说过:"普通人能遇到的最大的问题，就是思想出了问题"。是吧，既然整，那就整完整，虽然现在已经快4点了，Jekyll 是可以生成静态网页的，Gitee Pages 是忽略了以下划线开头的文件夹的。那么将刚刚的生成的静态网页复制一份。复制到shtml中，然后更新一下？部署目录：Blog/jekyllBlog/shtml

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013154632.png)

emmmm？资源文件找不到。尴尬。

##### 同名项目根目录

之前我还把我的同名项目删除了。因为我的gitee pages 的名字叫 lalalaxiaowifi，那就需要创建一个叫lalalaxiaowifi的项目。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013160452.png)

开启服务后，显示成功了。

##### 同名项目子目录

将上面代码剪贴到根目录下的page 目录中。然后更新：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201013161029.png)

也可以显示成功。

##### 非同名根目录

将刚刚没有修改baseurl 的今天文件复制过来,清除缓存。然后更新。发现不用配置baseurl 竟然不可以。那非同名子目录肯定也是如此了。那就将baseurl 修改后重新生成静态网页，和上面一样，baseurl 设置为项目名。编译后放到根目录。不用配置部署目录。直接更新。是可以显示的。

##### 非同名子目录

既然上面的都可以，同理可以得到，子目录也可以啊。

### 总结

时间还是蛮赶的， 我觉得我浑身是肝，这个笔记从昨天下午开始规划，今天上午因为主题原因卡进度了半天，搞得下午顺畅些。感觉公司前端大佬的指导，因为时间有点赶，所以可能没有那么严谨，测试的次数不够，模块也没有点完，只是草草的跑了一圈。

- Jekyll在gitee pages 上面使用，还是建议使用同名项目。无论是直接上传源码还是静态网页，毕竟一个个人账号好像只能有一个主页。
- Jekyll 提供的主题，部分在gitee pages 上使用非同名项目的时候会出现资源找不到，感觉原因是没有适配baseurl.使用同名项目就没有这个问题。
- Gitee  pages 支持Jekyll生成的静态 网页，但是本地如果不是通过Jekyll serve 开启服务，本地无法像其他静态网页那样打开展示，会出现资源文件找不到的情况。
- gitee pages 使用jekyll 生成的静态网页，如果是同名项目不用设置baseurl ,但是非同名项目一定药设置baseurl,baseurl是项目名称。
- Gitee pages 支持将项目放到子目录中。非同名项目还是需要配置baseurl.

好了，今天就先到这里了。下期可能更新hexo和hugo.

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

