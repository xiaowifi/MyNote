# 前言
> 受限于webview 的性能，在某些时候，可能有需求在webview 中嵌套一个原生控件。
# 正文
## 实现逻辑
Android webview 提供了一个函数：
```java
 web.addView(textView);
```
这个函数就是往webview 中添加一个原生view，同时webview 又支持 直接调用js 方法。<br>
那么就剩下一个问题，我们的在开发原生应用的过程中，需要定位view的位置，那么我们怎么获取到需要展示的原生view的位置呢？
我们知道Html中，dev用于布局，我们是否可以获取到dev的位置，然后把view添加到对应的位置呢？<br>
答案是肯定的。基于这个问题，会衍生出另外一个问题，原生view高度如果和dev的高度不一致会出现什么情况？<br>
Html的高度计算并不会随着原生view的添加而改变，如果添加了一个view,那么需要改变dev的高宽。
## 实现
### webview 开启JS能力
````java
 WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
````
### 提供一个能获取dev位置和改变dev高宽的Html
````html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <title></title>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
        }
        h1 {
            font-size: 40px;
        }
        div {
            margin: 0 auto;
            padding: 0;
            font-size: 30px;
            text-align: justify;
        }
        #section1 {
            color: #ff550d;
        }
        #section2 {
            color: #ffd351;
        }
    
    </style>
</head>
<body>
<h1>标题</h1>
<div id="advertisement"></div>
<div id="section1">
    section1 　　
</div>

<div id="section2">
    section2 　
</div>
<script type="text/javascript">
    function getAdPosition() {
        var advertisement = document.getElementById("advertisement");
        return advertisement.offsetTop;
    }
    function setAdHeight(height) {
        var advertisement = document.getElementById("advertisement");
        advertisement.style.height=height+"px";
    }

</script>
</body>
</html>
````
### 加载上诉Html并设置监听
````java
 web.loadUrl("file:///android_asset/html_mix.html");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showToast("web 已经准备好了");
            }
        });
````
### 添加view到webview 并且设置监听
````html
 TextView textView=getTextView();
        web.evaluateJavascript("javaScript:getAdPosition()",
                value -> {
//                            Log.i("getAdPosition:",value);
                    textView.setTranslationY(DensityUtil.dp2px(MainActivity.this,Float.parseFloat(value)));
                    web.addView(textView);
                    web.loadUrl("javaScript:setAdHeight("+TV_HEIGHT+")");
                });
````