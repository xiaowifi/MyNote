---
layout: post
title: "基于Gitee pages+Jekyll 拥有一个属于自己的主页"
date: 2015-08-25 
description: "日常搬砖-基于Gitee pages+Jekyll 拥有一个属于自己的主页"
tag: 其他
---  
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 。之前写过一个基于静态html的笔记。

## 正文

### Jekyll

既然自己写静态网页对于我这种小白而言太复杂了，那么用博客主页生成工具就可以了，毕竟鲁迅曾经说过"成年人最大的优点就是及时止损"。

Jekyll 使用文档：https://www.jekyll.com.cn/docs/home/。

这个要安装[Ruby](https://www.jekyll.com.cn/docs/installation/) 环境？![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923150601.png)

这丫的感觉一个脚本语言，我觉得我还是用html 比较好，毕竟我这个是会的。

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



然后就成功了。但是，我查看了我mylog 下的文件，发现并没有被修改，等于说，这个只是一个主题。需要自己写内容。关键是我就看了下环境和创建运行文档。编辑相关的还没有看。

#### 迷惑

这个调调，我应该改那个文件，让他达到我想要的效果哦，那么我们就直接从github 上把他的所有代码拉下来，运行一下，应该就可以大概直到这个博客生成工具的一些东西了,但是很麻烦，毕竟创建myblog 的时候是有代码在里面的并不是空项目。到这里我们就可以还原修改后的部分了，或者删除myblog，然后新建一个。

---

通过官网上提供的教程和demo，我们可以明显的看到。_config.yml 文件中的一些配置直接影响到整个界面的展示。比如说`theme: minima` 这个调调如果没有的话，整个界面就是白屏的，而我们上面使用的别人的主题也是修改这个值。如果将这个文件修改成：

```
title: title
email: email
description: >-
  description？
baseurl: ""
url: ""
twitter_username: twitter_username
github_username:  github_username


theme: minima
plugins:
  - jekyll-feed
```

通过 bundle 和bundle exec jekyll serve 之后：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928144930.png)

通过Demo的代码可以直到index.markdown 作为index.html 生成源只有一句有用的代码:

```
---
layout: home
---
```

虽然无法找到home的位置，而且about 中的layout 和index中的不一样：

```
---
layout: page
title: About
permalink: /about/
---
```

如果我把about 中的layout 复制给index ，那么index 中间就应该没有东西了。还有一个字段 title 我们可以看到如果设置了title:

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928152316.png)

他会出现两次，如果我不设置呢？about 在首页上都没有了。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923151008.png" style="zoom:33%;" />

```
permalink：这个在没有看文档的时候，是没有发现有什么用的，但是文档上建议写。如果又本地链接跳转：[qwq](/qwq) 
直接permalink 就过去了。
```

既然title 声明一个就有一个，那我多创建几个会不会有效果哦。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928153303.png)

我新建了一个other和qwq.

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png)

我想建一个中文的？结果他还是读取的title的值。

我没有推特，是不是把twitter_username删除了，就不会显示了？emmmm？果然是。好框架，这api 真的很适合我。众所皆知，md 是可以运行一些html 语法的，当然了有些博客把这个功能屏蔽了。

<span  style="color: #AE87FA; ">比如说，我觉得这个颜色就很好看</span> ![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200928154627.png)，但是csdn就显示不了。

但是Liquid基于这个Demo，我还是没有懂。

### GitHub下载一个Demo

话说，一口吃不成胖子。要整就整一个看起来自己喜欢的。https://github.com/leopardpan/leopardpan.github.io

那么我们就基于这个分析。基于上面的经验，layout 是决定这个博客长什么样子的关键，默认可能是page。

- 主题：https://www.jekyll.com.cn/docs/themes/
- layout https://www.jekyll.com.cn/docs/layouts/ 

先把大佬的代码下载下来，用上面的装好环境的IDEA 打开执行：

```终端
bundle install

 bundle exec jekyll serve
```

运行成功后网页打开： http://127.0.0.1:4000

#### 修改基础信息

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200929092723.png)

我们根据这个主题上面的提示，进行自行修改。修改后保存，因为修改了配置文件，所以需要重新编译运行：bundle exec jekyll serve:

可以被修改的信息蛮多的，直接修改上面的文件就好。

#### 修改头像

/images/avatar.jpg   这个文件修改成自己的就好，一定要名称后缀完全一样，否则头像显示部出来。

#### 修改主页背景

道理和上面头像是一样的。/images/background-cover.jpg.。修改其他图标也是类似的逻辑。替换掉原来的就好。

#### RSS 打开了一个乱码界面

在_includes/footer.html 中的75到81行左右，不需要就注释掉就好。

```
<!-- RSS -->
<li class="navigation__item_social">
  <a href="/feed.xml" rel="author" title="RSS" target="_blank">
    <div class="footer-social-icon" style="background:url(/images/rss.png);"></div>
    <span class="label">RSS</span>
  </a>
</li>
```

我看他的代码，是没有进行判断逻辑的，和上面其他项不一样，这个应该是一个常驻的功能，如果需要的话就直接改成自己需要的超链接就好，或者加一层判断逻辑。

#### 尾部添加自己想要的图标和字段

既然功能和已有的一样，就是一个图标，然后在config 中配置。

先整一个低配版本:直接在href 中写入完整路径：比如说加一个gitee:

```
<li class="navigation__item_social">
    <a href="https://gitee.com/" rel="author" title="Gitee" target="_blank">
        <div class="footer-social-icon" style="background:url(/images/gitee.png);"></div>
        <span class="label">RSS</span>
    </a>
</li>
```

然后加入config 字段判断：

```
{% if site.social.gitee %}
<li class="navigation__item_social">
    <a href="https://gitee.com/{{site.social.gitee}}" rel="author" title="Gitee" target="_blank">
        <div class="footer-social-icon" style="background:url(/images/gitee.png);"></div>
        <span class="label">RSS</span>
    </a>
</li>
{% endif %}
```

然后在config的social下增加：

```
	gitee: lalalaxiaowifi #这个要改成自己的哦。至于为啥叫这个名字，当初年少，唉，瞎取的一个名字，现在改不了
```

如果不写gitee 那么界面上就不会显示。

尾部的其他功能，也在_includes/footer.html 中，也可以魔改一下的。

#### 修改博客内容

##### 增加标签 

我们在config 中可以找到下面的代码：

```
nav:
    - {title: 所有文章, description: archive, url: '/archive'}
    - {title: 标签, description: tags, url: '/tags'}
    - {title: 技术支持, description: support, url: '/support'}
    - {title: 关于我, description: about, url: '/about'}
```

同理可以得出，我想加一栏。复制一行后修改对应值就好。比如说我想整一个简历。就需要加一行：

```
- {title: 简历, description: resume, url: '/resume'}
```

然后创建一个resume.md 文件，包含下面内容：

```
---
layout: page
title: 个人简历 
---
```

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200929110907.png)

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200929110952.png)

这个主题是自动换行的，如果字数不够就会挤在右边。**同理，删除标签，和更改标签名字也差不多**

##### 博客

这个才是正菜，根据这个主题的文档提供，只需要删除_post目录下的文章就好。源码下的博客：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200929111417.png)

生成的所有文章：

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200929111530.png)

生成的标签:

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200929111639.png)

通过jekyll官网提供的博客相关文档和这个Demo。直接理解：

```
---
layout: post
title: "HEXO搭建个人博客"
date: 2015-08-25 
description: "HEXO配置，HEXO+Github，搭建自己的博客"
tag: hexo
---   
```

- （时间(yyyy-mm-dd-)+博客名称）构成文件名。否否则目录显示不出来(https://www.jekyll.com.cn/docs/posts/ 人家官网提供的就这个样子，<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200923151008.png" style="zoom:50%;" />); 中间不能有空格。
- 需要加上面的标记，title 则是显示的博客名称。tags 标签用于分组。
- layout: post 必须标记，否则主题什么的都没有了，但是可以在目录中识别出来。

好了，今天的就大概到这儿了。

#### 打赏功能

在post.html 中。有点奇怪，我刚刚没有梯子的时候，打赏点击无法弹窗。评论就没有显示，但是我开启翻墙功能之后就显示出来了。

```
{% include new-old.html %}
```

如果不需要打赏功能，直接在post.html 中删除就好。评论也是同样的道理。最好是注释掉吧。如果是需要的话，需要更换支付宝和微信对应的支付图片。 new-old.html 文件中包含打赏区块的效果样式等等。

```
 <div class="shang_payimg">
      <img src="/images/payimg/alipayimg.jpg" alt="扫码支持" title="扫一扫" />
  </div>
<div class="shang_payimg">    
      <img src="/images/payimg/weipayimg.jpg" alt="扫码支持" title="扫一扫" />
  </div>
```

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200929140709.png)

修改成自己的大概长这个样子。

但是从new-old.html  代码中可以看到：

```
 <ul class="pager">
    {% if page.previous.url %}
    <li class="previous">
        <a href="{{ page.previous.url | prepend: site.baseurl | replace: '//', '/' }}" data-toggle="tooltip" data-placement="top" title="{{page.previous.title}}">上一篇：  <span>{{page.previous.title}}</span>
        </a>
    </li>
    {% endif %}
    {% if page.next.url %}
    <li class="next">
        <a href="{{ page.next.url | prepend: site.baseurl | replace: '//', '/' }}" data-toggle="tooltip" data-placement="top" title="{{page.next.title}}">下一篇：  <span>{{page.next.title}}</span>
        </a>
    </li>
    {% endif %}
</ul>
```

作者把上一篇和下一篇的写到了打赏里面。

需要博客总量 大于2 才会又上一篇和下一篇。

#### 评论功能 

在post.html 中

```
{% include comments.html %}
```

在congfig 中需要 对comment 子项进行赋值。因为我一般直接发平台的，所以这个对我感觉没有什么用。

在comments 中对评论功能定义了2套：

- livere
- disqus

如果没有在config中定义这个两个值，就不会显示。评论只需要集成一个就好。具体的自己搜索一下就好。好像需要梯子什么的。

#### 统计功能

这个主题里面有统计功能。分为右下角的```本站总访问量 5931次`` 和![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200929143752.png)  。

- 本站总访问量：

  ```
  <div align="right">
        <link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css">
  
        &lt;!&ndash; 访问统计 &ndash;&gt;
        <span id="busuanzi_container_site_pv">
    本站总访问量
    <span id="busuanzi_value_site_pv"></span>次
  </span>
  
    </div>
  ```

是在footer 中写死了的，并没有配置到config中。<img src="https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20200921111932.png" style="zoom:50%;" /> 

- 阅读量：

  ```` <span id="busuanzi_container_page_pv"> | 阅读：<span id="busuanzi_value_page_pv"></span>次</span>```` 

  好像也是写死在 post.html 中的。

因为我会js 相关的，所以理解可能有问题。



### 更新到Gitee pages 







###  总结下

感觉这个还行，比较简单。和Android DataBinding 很像。找一个主题，然后本地生成一下，更新到Gitee page,还是比较简单的。对于像我这种野路子，标签靠目录。文件没有时间头的，需要修改md 文件。  

## 结束

我感觉我这笔记也就图一乐，看官网还是比较好的，https://www.jekyll.com.cn/docs/usage/  毕竟真的很全面，完全可以按照自己的需求来整，因为我连需求都没有，所以就很迷茫，就很乱。

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

[基于gitee pages的个人主页](http://lalalaxiaowifi.gitee.io/pictures) 这个还是没有整出来。