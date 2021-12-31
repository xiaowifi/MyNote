 



![在这里插入图片描述](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20211231153553.png)
我们需要做的，就是“画”出AB、CD这2条二阶贝塞尔曲线。而ABCD这个不规则多边形，就是“气泡”，根据贝塞尔曲线的定义，可以发现，O点、P点为已知点，G点作为AB、CD这2条贝塞尔曲线的控制点，而A和B、C和D分别是AB、CD贝塞尔曲线的数据点。因此求出A、B、C、D、G5个点的坐标就可以画出这2条贝塞尔曲线了！

关于坐标的求解，一个个来：
G：坐标很简单，直接O点与P点的x,y相加除以2就可以算出来。
ABCD点，根据高中的数学相关知识：

PE = O的y坐标-P的y坐标
OE = P的x坐标-O的x坐标

sin∠POE = PE / OP
cos∠POE = OE / OP

A坐标：
x = O的x坐标 - sin∠POE * 固定圆半径
y = O的y坐标 - cos∠POE * 固定圆半径

B坐标：
x = P的x坐标 - sin∠POE * 动圆半径
y = P的y坐标 - cos∠POE * 动圆半径

C坐标：
x = P的x坐标 + sin∠POE * 动圆半径
y = P的y坐标 + cos∠POE * 动圆半径

D坐标：
x = O的x坐标 + sin∠POE * 固定圆半径
y = O的y坐标 + cos∠POE * 固定圆半径