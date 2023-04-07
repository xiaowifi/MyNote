转自：blog.csdn.net/hy_coming/article/details/80741717

最近在开发的过程当中，对于已有的代码，想将相关类绘制成UML类图，虽然现在有很多UML类图的优秀软件，比如ProcessOn（可视化编辑）、draw.io（可视化编辑）、PlantUML(代码生成)，其实看到这里我就想IDEA中有没有像PlantUML一样的自动生成的插件，本着怀疑的态度百度了一下，果然IDEA还是很强大的，这个插件都是自带的，接下稍微讲述如何利用IDEA生成UML类图的教程。

说之前先说一下Diagram这个单词，意思是图表; 示意图; 图解; [数] 线图的意思。

- 打开设置 `File->Setting`或windows下按`Ctrl+Alt+S`
- `在搜索框中输入``Diagram，`如下图：

![img](https://s2.loli.net/2022/05/14/UnagcKjVCvPMd9W.png)

如上所示，我们主要关心的只有Java Class Diagrams下面的几个单选框，分别对应红字部分，一般的UML类图只需要知道成员变量、构造器和方法（前面三个），其他的随意，设置好了之后我们就来演示一下，

- 选择需要的类文件，在编辑器中打开它
- 按`Ctrl + Shift + Alt + U`或`Ctrl + Alt + U或右键选择，``生成类Uml关联图，如下图：`

![img](https://s2.loli.net/2022/05/14/JnvRi4oDjBkWXTV.png)

![img](https://s2.loli.net/2022/05/14/a5z8tPv9OowSreh.png)

上面的是类的UML图，下面的support包的UML图：

![img](https://s2.loli.net/2022/05/14/W1XFCYbprV9zNkd.jpg)

局限性：虽然这个很是方便，但是也有他自己的局限性，首先这个功能只能是根据类来自动生成的，所以对于设计类的时候就不行了，还是需要正规的UML图软件，还有就是对于专业的UML软件来说，这种自动生成的东西可能表达并不是非常的准确直观，特别是对于UML图有严格要求的人来说，显得格外重要。