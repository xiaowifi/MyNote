![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

顺便推一下[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 。之前写过一个基于静态html的笔记。

## 正文

### Jekyll

既然自己写静态网页对于我这种小白而言太复杂了，那么用博客主页生成工具就可以了，毕竟鲁迅曾经说过"成年人最大的优点就是及时止损"。

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

## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客目录](https://gitee.com/lalalaxiaowifi/pictures) 

[基于gitee pages的个人主页](http://lalalaxiaowifi.gitee.io/pictures) 这个还是没有整出来。
