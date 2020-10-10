![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

之前整过一个Jekyll和静态网页的主页笔记，最近换季，导致感冒拉肚子，又为国庆生等等耽搁了快一个月了，原本打算这个系列在一周内整出来的，结果到现在还有2个TODO未实现。那么本篇主要级hexo.先上文档官网：

[gitee pages 官方文档](https://gitee.com/help/articles/4136#article-header0)  

Hexo 使用文档：https://hexo.io/docs/

<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009104008.png" style="zoom:33%;" />  这个是用Node.js .

既然我们用的IDEA,那么node,js也是需要安装的。必须安装。

## 正文

如果你的Node.js 已经安装成功了。那么按照hexo  提供的教程走就好了。

### 安装Hexo

先安装hexo：https://hexo.io/zh-cn/docs/

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009110155.png)

安装2选1就好。

### 创建执行HEXO 项目

#### 创建

创建代码还是很简单，找一个空目录在终端中执行：

```
hexo init myblog
```

Myblog 是自目录名称，根据个人爱好写。然后是:

​	cd myblog//切到myblog 目录：

最后执行：

```
npm install
```

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009111347.png) 

然后通过idea 打开该项目就可以进行编辑了。

#### 配置运行

因为这个项目是基于Node,js 运行的，那么我们直接对整个项目的运行环境进行设置？

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009112629.png)

对于一个闲鱼而言，从代码中找到运行项是很简单的。比如说**hexo server ** 我觉得这个就是运行Demo 的关键。

如果idea 打开这个文件没有运行按钮，就需要对整个项目目录配置为node 的项目比如：

![image-20201009112924696](/Users/yangfan/Library/Application Support/typora-user-images/image-20201009112924696.png)

但是通常还是有运行按钮的。运行成功后打开：http://localhost:4000/ 就可以看到初始创建的博客样子了。如果404 明显就是 hexo server 没有运行成功嘛。

#### 添加一篇文章

我们在运行成功后，发现里面有一篇默认文章。修改网页的一些默认值 https://hexo.io/zh-cn/docs/configuration。这里就不重复了。我们可以在项目的目录下找到：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009151236.png)

这个和运行出来的hello world 内容是差不多的。里面除了正文 还有：

```
---
title: 测试Demo1
---
```

这个怎么和Jekyll 的文章配置这么像啊，兄弟。而且没有要求用时间+名字，很舒服。那么我不写这个配置会出现什么情况？

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009151638.png)

和Jekyll 一样的嘛，标题都是抓的title。但是时间是抓的生成时间。而且Jekyll标题没有按照格式来的话，是不会显示到目录中的，这个好像可以设置。

### 配置

这个HEXO 也提供了：https://hexo.io/zh-cn/docs/configuration 。非常详细； 感觉没有记的必要。

相关运行指令也很详细：https://hexo.io/zh-cn/docs/commands 

这两个还是需要看一下子的，要不然直接使用别人的主题会直接懵逼。

### 添加一个主题

https://hexo.io/themes/ 先在这个网站上找一个喜欢的主题.

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009114751.png)

点击1预览效果。点击2 直接到github地址。还是老规矩，虽然提供了模板使用教程。

- 在项目目录执行主题下载：比如： git clone https://github.com/Youthink/hexo-themes-yearn.git themes/yearn.如果终端上提示下载成功了，那么：![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009141406.png)本地主题目录中一定是有这个文件夹的。
- 在_config 文件中将 theme的值赋值为：yearn 。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009141601.png)也就是我们上面下载的主题的名字。修改成功后保存一下。然后就是重启服务就好。

话说，之前Jekyll，我好像直接在github 上下载内容直接改，就可以运行。但是这个调调好像不行。

这个是我本地创建的博客项目内容：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009141915.png)

这个是我从github 上下载的主题：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009141815.png)

很明显，github 上明显只有主题内容的，没有所谓的Demo，所以也就无法直接运行了咯。如果通过git 下载失败的情况下。可以直接下载代码放到themes 中，而且好像没有限制主题个数。

### 增加栏目

因为不同的主题，所定义的字段不同，如果使用他的主题没有设置对应的值，有的情况是直接显示404，有的就是资源显示不了。https://hexo.io/zh-cn/docs/templates 我们通过官方提供的教程可以知道：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009160403.png)

那么关键字的定义和使用也在这里了，当然有些大佬的使用还是写的很是详细的，不需要直接通过代码去找，话说写笔记嘛，还是要整简单的，那么就直接上默认主题了咯。

因为主题在layout中，而且主题也有一个_config 文件<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009104008.png" style="zoom:33%;" />。话说字段的默认值是不是就在主题的_config 中哦。

<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png" style="zoom:50%;" /> 那么，项目中的config 中的值是否可以替换掉主题中的config 中的值吗？



话说：![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009162101.png)

menu 在项目的config 中是没有的，那么直接在主题的config中修改成中文，并且增加一个百度一下的按钮会出现什么情况？

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009162340.png)

改成功了，新增了按钮，并且中文改成功了。那修改项目的config 中的值呢？

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009162555.png)

没有效果。。。。。

那么似乎可以得到一个结论：主题中定义的某些值是无法在项目中被修改的。

#### 栏目中使用md文档

通过上面的例子可以知道，menu 控制着界面栏目的显示，既然Jekyll可以直接使用md当做对应的界面，那么hexo 是不是也可以？现有的信息如下：

- Jekyll 支持直接使用 md 文档当做某个界面，比如：关于我们。
- hexo 目前使用的界面是js 界面。
- hexo 可以基于post.js 显示md 文档。
- hexo 默认存放于：source/_post 中
- hexo 主题中也存在source 目录。
- hexo 标记链接地址使用的是相对地址。

上面提供的信息还是蛮多的，假设我们需要一个关于我们界面。大致有下面几种方式：

- 在主题中写一个html界面。(这个调调，我试了不下5分钟，我新建html，还是复制一个都404，我在主题使用md 也是404，我觉得是我相对路径写得有问题)
- 在项目中写一个html 界面。这个也是404。
- 使用项目中的md 文件。
- 使用主题中的md 文件。这个也是404。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923151008.png)一个想法3个404,我真的懵逼的。还好使用项目中的md文件没有出现问题。

##### 使用项目中的md 文件

![image-20201009171629308](/Users/yangfan/Library/Application Support/typora-user-images/image-20201009171629308.png)

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009171647.png)

 终于还是有一个是可行的。

##### 如何应对新增界面404

话说上面几个都是404，主要是相对路径设置不正确，这个就需要看一下文档了。根据这个文档:https://hexo.io/zh-cn/docs/asset-folders ,主要是我们这是静态界面。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010092551.png)

同理可以得到，既然image的使用都可以，那么新建一个page也没有毛病。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010092711.png)

然后在menu 中添加:

```
关于我的html界面: /page/about
```

在主题中的source中和项目的source新建界面还是有明显的区别的。

- 在主题的source中新建的html，如果没有手动写主题相关代码，那么就没有主题效果。
- 在项目中的source 中新建html,默认是有主题效果的。

##### 主题中使用md404

<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png" style="zoom:50%;" />  ，既然上的可以，同理可以得到，主题中使用md 文件作为一个界面还是可以的，对吧。

我们在主题的source 中新建_posts 目录，然后在里面创建一个md 文档，像这个样子：![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201010093924.png)

重启服务器之后，发现文章目录根本就没有这个about,那是否可以说明主题中的_posts目录只是当成了静态资源，和项目中的 _post 还是有明显的区别的。既然是静态资源，那就应该可以按照静态资源访问了咯。之前在教程中看到以下划线开头的目录都会被忽略（特定的除外），所以既然是静态资源，那么 _post 这个目录名字就不能用。

### 更新到Gitee Pages 

先不说别的，直接先贴文档。这个要部署，部署。暂时还没有尝试直接提交源码上去。

https://hexo.io/zh-cn/docs/one-command-deployment

https://gitee.com/help/articles/4136#article-header1

因为都是基于主题，所以不同的主题定义的东西还是不一样的，所以更多的东西就需要自己了解了。看官方教程，这个调调是可以放到某个字目录中的，但是需要修改一些配置。因为时间的原因，这里就直接推到根目录下面了。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201009173132.png)

好像一个个人账号只能有一个主页的样子，我gitee 上面有一个主页了。

### 总结

- 和Jekyll 相比，感觉可以适用多个主题。
- 对博客文件的控制性更加宽松，不需要时间+名字命名。
- 对模板的添加，我感觉都差不多，但是为啥有的模板只有一个html结束标签，强迫症受不了。
- 部署上面，我更加倾向于Jekyll，虽然提供一键部署等，其实也差不多。
- Jekyll 可以看到博客文件，这个部署的时候编译了的。
- 接入文档都是需要作者提供，感觉没有什么区别。感觉Hexo 扩展要好一些。
- 还是部署，部署到gitee 上面一直404。完全懵逼。和Jekyll 差不多，不进行配置，不是同名项目，根本就显示不了。比如说我的gitee 上面的名字叫：lalalaxiaowifi，那么我的主页的项目名也需要叫这个名字。

容我把todo 都整完了再说，如果可以放到非同名网站上面，也会更新一波。

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

[基于gitee pages的个人主页](http://lalalaxiaowifi.gitee.io/pictures)

