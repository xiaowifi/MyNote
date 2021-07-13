+++
date = "2021-2-20"
title = "git使用方式合集"
description = "git使用方式合集"

tags = [ "git"]
featured = true

+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
> 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)
## git 
### 基础配置
下载git软件:
运行git是在任意目录或桌面下,右键选择git base here
任意目录下打开git输入：
````aidl
//初始化个人信息
1.git config --global user.name "你的名字或昵称"	//初始化个人信息
2.git config --global user.email "你的邮箱"	
````

<<第一次提交,把现有项目提交到git仓库：
在当前文件夹下:

````aidl
1:git init   //初始化git仓库
2:git remote add origin xxxxxx.git	//与远程仓库链接
3:git add .				//将本地文件提交到本地仓库
4:git commit -m 'new'			//备注提交信息
5:git push origin master -f		//提交到远程仓库
中间可能需要输入码云的账号和密码,输入即可。>>
````

如果是把现有git仓库中的项目克隆
在需要存放的盘中
````aidl
1:git clone xxxxx.git
````

<<上面的两种情况，以后的每次提交：
````aidl
1:git add .			//将本地文件提交到本地仓库
2:git commit -m 'new'		//备注提交信息
3:git push origin master	//提交到远程仓库
````

github.com已经建立了一个仓库，一个分支，master。现在在本地一个文件中打算初始化一个git项目，并创建两个分支，master对应远程的master，ycl对应远程的ycl。步骤如下：

1、复制远程项目：git clone master的地址，以.git结尾，（另外有种方法是本地git init一个项目，但是尝试最后push失败）然后进入项目内

2、此时本地和远程的分支都是master，输入git branch -a 可以看到本地和远程分支，*代表本地使用的分支，不加-a只显示当前使用分支。



如果增删改查了文件，采用如下方式提交：

（1）选择需要提交的文件：git add .(.代表所有文件，如果只提交某个直接输入文件名称），可以使用git status查看状态

（2）提交至本地仓库：git commit -m "日志"

（3）推送至远程仓库：git push origin master

3、现在远程创建了一个新的分支ycl，想实现的目的是本地编写的代码提交到远程的ycl分支，然后合并到master分支

（1）首先更新项目，这时会拉下所有分支：git pull



（2）创建本地分支，git branch ycl，然后git branch -a查看分支多了



（3）切换分支命令，git checkout xxxx（git checkout ycl）

（4）然后修改代码，git add .，git commit -m "xxxx"

（5）提交至远程ycl分支：git push origin ycl:ycl （这句代表将本地ycl分支推送到远程ycl分支，更多命令行细节不说了）

4、到这里，个人写的代码已经push到自己的分支了，但是我还要把代码合并到master啊，步骤如下：

（1）在本地切换回master 分支：git checkout master

（2）合并分支提交的内容：git merge ycl

（3）将master中的内容push到远程仓库：git push

5、有可能git pull下来会有冲突，在本地解决即可


#### 删除本地分支
命令行 : $ git branch -d <BranchName>

在IDEA中进行分支切换时，出现如此错误，导致无法正常切换：error: The following untracked working tree files would be overwritten by checkout
通过错误提示可知，是由于一些untracked working tree files引起的问题。所以只要解决了这些untracked的文件就能解决这个问题。

解决方式：

打开SourceTree通过命令行，进入本地版本仓库目录下，直接执行git clean -d -fx即可。可能很多人都不明白-d，-fx到底是啥意思，其实git clean -d -fx表示：删除 一些 没有 git add 的 文件；

#### git clean 参数

-n 显示将要删除的文件和目录；

-x -----删除忽略文件已经对git来说不识别的文件

-d -----删除未被添加到git的路径中的文件

-f -----强制运行
#### 强制执行
# git checkout -f  origin/master 强行切换到master 


#### 其他 
git clean -n

git clean -df

git clean -f

#### 强制提交。
$ git push -u origin master -f

先将本地修改存储起来
$ git stash

#### bug:
error: unable to rewind rpc post data - try increasing http.postBuffer
error: RPC failed; curl 56 OpenSSL SSL_read: SSL_ERROR_SYSCALL, errno 10053
fatal: Writing objectsThe : r e1m2o% (10t8/e end8 h9un4), 1.23 MigB  |up  u1n0e.x0pec0t eKdilBy/
Writing objects: 100% (894/894), 9.29 MiB | 82.00 KiB/s, done.
Total 894 (delta 315), reused 0 (delta 0)
fatal: The remote end hung up unexpectedly
Everything up-to-date
解决方式哦：
git config --global http.postBuffer 524288000
