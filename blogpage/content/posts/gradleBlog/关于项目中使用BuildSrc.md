
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)
> 顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 
## 正文
> 2021年2月22日。突然发现发现项目根目录下有一个BuildSrc文件夹，而且没有在setting.gradle中使用。抱着不懂就百度的思想，就开整。
> 在百度之前，还是简单的看了下里面的内容，好像是gradle 中的配置。之前一般都写在一个base 中，然后导入base ,或者写到一个特定文件中便于统一管理。
> 但是写到buildScr 中，还是头一次见到，学到了。直接上官网连接。[查看buildSrc配置](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources)

> 复杂的构建逻辑通常很适合作为自定义任务或二进制插件进行封装。自定义任务和插件实现不应存在于构建脚本中。buildSrc只要不需要在多个独立项目之间共享代码，就可以非常方便地使用该代码。
> 该目录buildSrc被视为包含的构建。发现目录后，Gradle会自动编译并测试此代码，并将其放入构建脚本的类路径中。对于多项目构建，只能有一个buildSrc目录，该目录必须位于根项目目录中。 buildSrc应该比脚本插件更可取，因为它更易于维护，重构和测试代码。
> buildSrc使用适用于Java和Groovy项目的相同源代码约定。它还提供对Gradle API的直接访问。其他依赖项可以在专用的build.gradle下声明buildSrc。
    