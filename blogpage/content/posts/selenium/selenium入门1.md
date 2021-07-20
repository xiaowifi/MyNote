+++
date = "2020-10-01"
title = "selenium入门1"
description = "selenium入门1"
tags = [ "selenium"]
categories = [
    "自动化测试"
]
series = ["selenium"]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

## 前言

之前逛github的时候，发现了一个Python调用自动化测试工具的教程。然后发现了一个selenium的宝贝，在这位大佬的Demo中，我发现他可以直接操作浏览器。

话说 我一个Android 搬砖工，好像也没有必要整这个自动化web。

顺便推一下[博客主页](http://lalalaxiaowifi.gitee.io/pictures/) 

## 正文

先整需求:

- 基本需求，在线学习中，可以自己隔一段时候点一下下一页。

- 进阶一点的是，可以不吃焦点的点下一页。

- 再进阶一点的，可以最小化浏览器，然后还可以点下一页。

- 基于基本需求可以判断界面是否学习完毕，完毕后才点下一页，或者等待了3分钟没有完成直接点下一页。

- 基于上一条补充，先判断当前页有多少个课，如果有多个，先每一个跳转一下到最后一个，等完毕后再下一页。

  主要还是要用到他这个直接操作界面，我也不爬数据，只是监听获取控件。

#### 介绍

先整一个官网的教程：https://www.selenium.dev/documentation/zh-cn/

这个调调可能需要科学上网。上面有一个中文的，主要是我应该用的是JAVA，所以中文的里面没有，但是对应的教程还是有很多博客的，知识这种东西，还是看原滋原味的，然后结合别人的理解，就可以找到自己的理解，这么快一点，如果直接是别人的理解，就不容易生成自己的理解了，扩展会相对差一点，也不是不能扩展。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201022113120.png)

这个地方可以切换文档语言。

这个调调支持的语言还是蛮多的。![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201022113752.png)

这就导致了不同的语言，接入方式不一样。那么就直接看这个调调支持些什么:从文档上可以看到，支持很多浏览的自动化操作。包括：

- 自动打开浏览器，并且打开一个网页。https://www.selenium.dev/documentation/zh-cn/webdriver/browser_manipulation/
- 通过id ,class,标签等操作获取到控件。https://www.selenium.dev/documentation/zh-cn/webdriver/web_element/
- 支持控件点击等相关事件操作。
- 可以获取到浏览器的各种信息，长宽高。https://www.selenium.dev/documentation/zh-cn/webdriver/browser_manipulation/
- 可以模拟鼠标操作。
- 可以模拟键盘操作。https://www.selenium.dev/documentation/zh-cn/webdriver/keyboard/
- 可以设置等待，https://www.selenium.dev/documentation/zh-cn/webdriver/waits/
- 可以设置加载策略。

反正提供的还是蛮多的，更多的看文档就好。文档也不多，浏览式的看完也要不到20分钟。

既然我应该会JAVA ，那么就直接整JAVA 的调用方式。浏览器也是谷歌浏览器，那么驱动也下载这个调调。

#### Java环境

因为我这把JAVA 环境比较好一点，selenium提供 jar和Maven,国内Maven经常下载不了。所以我还是选择jar比较好。

> <dependency>
>
>   <groupId>org.seleniumhq.selenium</groupId> 
>
>  <artifactId>selenium-java</artifactId>
>
>   <version>3.X</version> 
>
> </dependency>

通过 https://www.selenium.dev/downloads/  这个网站可以知道当前最稳定的是：[3.141.59](https://selenium-release.storage.googleapis.com/3.141/selenium-server-standalone-3.141.59.jar)  点这个版本号就可以直接下载了。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201022160547.png)

然后是浏览器驱动。

需要使用什么浏览器就下载配置什么浏览器驱动就好了。https://www.selenium.dev/documentation/zh-cn/webdriver/driver_requirements/ ，这个区分浏览器和系统了的。需要配置path.由于官网提供的Demo是谷歌浏览器的，所以还是安装谷歌浏览器的驱动吧，主要是他好像没法自动导包。

#### Java项目

我就直接基于idea新建一个JAVA 项目了。主要是考虑到Maven 可能下载不了，所以直接是JAVA 项目，而不是Maven 项目。然后将刚刚下载的jar 整到项目里面去。

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20201022164305.png)

idea对项目添加jar 就不介绍了。

####  打开一个网页

因为我环境是mac，所以直接用Safari:

```
public static void main(String[] args) {
    WebDriver driver = new SafariDriver();
}
```

直接执行main 方法，将会直接打开Safari浏览器。

> driver.get("https://google.com/ncr"); 将打开上面的网站。

话说，这个是有网页打开策略的。因为现在的网页好多都防止爬虫什么的，那么就直接使用eager 策略。

> 这将使Selenium WebDriver保持等待, 直到完全加载并解析了HTML文档, 该策略无关样式表, 图片和subframes的加载.

#### 打开策略

```
 import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
 ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriver driver = new ChromeDriver(chromeOptions);
```

策略有几个，我觉得这个挺好用的。

#### 等待

这个主要用于暂停程序，包含显示等待，隐式等待，流畅等待，用于防止获取某些控件啊，事件的时候抛出空，或者没有到那个界面。https://www.selenium.dev/documentation/zh-cn/webdriver/waits/

区别就需要自己看文档了。

根据上面的需求。打开学习平台的时候，需要登录，登录后需要跳转到在线学习，然后是选择课程，然后开始学习，如果学习成功，就跳下一个视频。学习平台登录是扫码登录，所以登录需要手动操作。还有一个问题，点击开始学习的时候是打开了一个新的标签的。

#### 流畅等待

```
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(Duration.ofSeconds(300))
        .pollingEvery(Duration.ofSeconds(5))
        .ignoring(NoSuchElementException.class);
```

这个和JAVA 的接口回调很像，于是我比较倾向于这个。对于Function 中的apply的返回值是可以自己定义的。

##### 使用流畅等待判断url

```
wait.until(new Function<WebDriver, Boolean>() {
    @Override
    public Boolean apply(WebDriver webDriver) {
        return webDriver.getCurrentUrl().equals("https://student.uestcedu.com/console/main.html");
    }
});
```

##### 使用流畅等待获取控件

```
WebElement left_menu_ul= wait.until(new Function<WebDriver, WebElement>() {
    @Override
    public WebElement apply(WebDriver webDriver) {
        return webDriver.findElement(By.id("left_menu_ul"));
    }
});
```

#### 切换window

这个可能是打开了一个新的标签，需要将WebDriver的对象切换到对应的值标签上面的。通过driver.getWindowHandle(); 获取当前标签的唯一值， webDriver.getWindowHandles() 获得所有标签的唯一值。因为他是set集合存储。所以可能需要循环来甄别标签。切换标签：

```
driver.switchTo().window(h);
```

h 是上面set 中代表标签的唯一值，当然还有其他类型的切换。在文档的窗口管理中：

https://www.selenium.dev/documentation/zh-cn/webdriver/browser_manipulation/

#### 获取控件

先贴教程官网。https://www.selenium.dev/documentation/zh-cn/webdriver/web_element/

因为点击啊，模拟键盘，鼠标等操作好像都需要获取到WebElement 才行。这个相对简单，一般通过id,tagname,linktext,class,css 等直接获取了。比如:

```
webDriver.findElement(By.id("left_menu_ul"))
```

直接调用By里面的方法就行。一个控件没有这些属性，只有标签，那么通过找到他的上级或者上上级，无限拆解也是可以获取到的，同时焦点，是否可用等等也是可以获取到的。

通过和前端大佬的沟通，如果一个界面只是单纯的一个界面，那么通过上面的方法是可以获取到控件的，但是有一个问题，那就是如果一个界面是由多个界面拼接出来的呢？疯狂的套娃，一个html加载另外一个html，这个就需要将WebDriver 切换到对应的html 上了。这个直接通过浏览器的检查可以直接区分出来，还有一些是可以通过url 看出来的。

> ## Frames and Iframes
>
> Frames are a now deprecated means of building a site layout from multiple documents on the same domain. You are unlikely to work with them unless you are working with an pre HTML5 webapp. Iframes allow the insertion of a document from an entirely different domain, and are still commonly used.
>
> If you need to work with frames or iframes, WebDriver allows you to work with them in the same way. Consider a button within an iframe. If we inspect the element using the browser development tools, we might see the following:

这个也在操控浏览器文档页：https://www.selenium.dev/documentation/zh-cn/webdriver/browser_manipulation/ 

比如说 我现在想要跑脚本的网页中就有界面是由iframe拼接的。

> 除非你使用的是 HTML5 之前的 webapp，否则你不太可能与他们合作。内嵌框架允许插入来自完全不同领域的文档，并且仍然经常使用。

感觉学校类型的技术，都是求稳。那么就开整。

http://learning.uestcedu.com/learning3/images/scorm/completed.gif 

#### 键盘

#### 鼠标



## 结束

谢谢光临，若觉得还行麻烦点一下赞，若觉得写得垃圾，欢迎批评指正。笔者知道自己文笔和表达能力很弱，如果您可以提供点宝贵的意见不胜感激。谢谢。

今天也可以是元气满满的一天哦。
更新需要审核。更新通常优先更新到gitee上面。

[博客主页](http://lalalaxiaowifi.gitee.io/pictures/)  

