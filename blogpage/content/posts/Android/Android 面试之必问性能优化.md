# Android 面试之必问性能优化

对于Android开发者来说，懂得基本的应用开发技能往往是不够，因为不管是工作还是面试，都需要开发者懂得大量的性能优化，这对提升应用的体验是非常重要的。对于Android开发来说，性能优化主要围绕如下方面展开：启动优化、渲染优化、内存优化、网络优化、卡顿检测与优化、耗电优化、安装包体积优化、安全问题等。

# 1，启动优化

一个应用的启动快慢是能够直接影响用户的使用体验的，如果启动较慢可能会导致用户卸载放弃该应用程序。

## 1.1 冷启动、热启动和温启动的优化

### 1.1.1 概念

对于Android应用程序来说，根据启动方式可以分为冷启动，热启动和温启动三种。

- 冷启动：系统不存在App进程（如APP首次启动或APP被完全杀死）时启动App称为冷启动。
- 热启动：按了Home键或其它情况app被切换到后台，再次启动App的过程。
- 温启动：温启动包含了冷启动的一些操作，不过App进程依然存在，这代表着它比热启动有更多的开销。

可以看到，热启动是启动最快的，温启动则是介于冷启动和热启动之间的一种启动方式。下而冷启动则是最慢的，因为它会涉及很多进程的创建，下面是冷启动相关的任务流程： ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/24ba93d109f047638b7f82c5b5a29d7f~tplv-k3u1fbpfcp-zoom-1.image)

### 1.1.2 视觉优化

在冷启动模式下，系统会启动三个任务：

- 加载并启动应用程序。
- 启动后立即显示应用程序空白的启动窗口。
- 创建应用程序进程。

一旦系统创建应用程序进程，应用程序进程就会进入下一阶段，并完成如下的一些事情。

- 创建app对象
- 启动主线程(main thread)
- 创建应用入口的Activity对象
- 填充加载布局View
- 在屏幕上执行View的绘制过程.measure -> layout -> draw

应用程序进程完成第一次绘制后，系统进程会交换当前显示的背景窗口，将其替换为主活动。此时，用户可以开始使用该应用程序了。因为App应用进程的创建过程是由手机的软硬件决定的，所以我们只能在这个创建过程中进行一些视觉优化。

### 1.1.3 启动主题优化

在冷启动的时候，当应用程序进程被创建后，就需要设置启动窗口的主题。目前，大部分的 应用在启动会都会先进入一个闪屏页(LaunchActivity) 来展示应用信息，如果在 Application 初始化了其它第三方的服务，就会出现启动的白屏问题。

为了更顺滑无缝衔接我们的闪屏页，可以在启动 Activity 的 Theme中设置闪屏页图片，这样启动窗口的图片就会是闪屏页图片，而不是白屏。

```cpp
    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="android:windowBackground">@drawable/lunch</item>  //闪屏页图片
        <item name="android:windowFullscreen">true</item>
        <item name="android:windowDrawsSystemBarBackgrounds">false</item>
    </style>
复制代码
```

## 1.2 代码方面的优化

设置主题的方式只能应用在要求不是很高的场景，并且这种优化治标不治本，关键还在于代码的优化。为了进行优化，我们需要掌握一些基本的数据。

### 1.2.1 冷启动耗时统计

**ADB命令方式** 在Android Studio的Terminal中输入以下命令可以查看页面的启动的时间，命令如下：

```cpp
adb shell am start  -W packagename/[packagename].首屏Activity
复制代码
```

执行完成之后，会在控制台输出如下的信息：

```cpp
Starting: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=com.optimize.performance/.MainActivity }
Status: ok
Activity: com.optimize.performance/.MainActivity
ThisTime: 563
TotalTime: 563
WaitTime: 575
Complete
复制代码
```

在上面的日志中有三个字段信息，即ThisTime、TotalTime和WaitTime。

- **ThisTime**：最后一个Activity启动耗时
- **TotalTime**：所有Activity启动耗时
- **WaitTime**：AMS启动Activity的总耗时

**日志方式** 埋点方式是另一种统计线上时间的方式，这种方式通过记录启动时的时间和结束的时间，然后取二者差值即可。首先，需要定义一个统计时间的工具类：

```cpp
class LaunchRecord {

    companion object {

        private var sStart: Long = 0

        fun startRecord() {
            sStart = System.currentTimeMillis()
        }

        fun endRecord() {
            endRecord("")
        }

        fun endRecord(postion: String) {
            val cost = System.currentTimeMillis() - sStart
            println("===$postion===$cost")
        }
    }
}
复制代码
```

启动时埋点我们直接在Application的attachBaseContext中进行打点。那么启动结束应该在哪里打点呢？结束埋点建议是在页面数据展示出来进行埋点。可以使用如下方法：

```cpp
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView.viewTreeObserver.addOnDrawListener {
            LaunchRecord.endRecord("onDraw")
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        LaunchRecord.endRecord("onWindowFocusChanged")
    }
}
复制代码
```

### 1.2.2 优化检测工具

在做启动优化的时候，可以借助三方工具来帮助我们理清各个阶段的方法或者线程、CPU的执行耗时等情况。这里主要介绍以下TraceView和SysTrace两款工具。

**TraceView**

TraceView是以图形的形式展示执行时间、调用栈等信息，信息比较全面，包含所有线程，如下图所示。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0aaadc6f17714fca912c82e0c3c2aac1~tplv-k3u1fbpfcp-zoom-1.image) 使用TraceView检测生成生成的结果会放在Andrid/data/packagename/files路径下。因为Traceview收集的信息比较全面，所以会导致运行开销严重，整体APP的运行会变慢，因此我们无法区分是不是Traceview影响了我们的启动时间。

**SysTrace** Systrace是结合Android内核数据，生成HTML报告，从报告中我们可以看到各个线程的执行时间以及方法耗时和CPU执行时间等。

![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/08c676411d024e96afc8db392cf770ae~tplv-k3u1fbpfcp-zoom-1.image) 再API 18以上版本，可以直接使用TraceCompat来抓取数据，因为这是兼容的API。

```cpp
开始：TraceCompat.beginSection("tag ")
结束：TraceCompat.endSection()
复制代码
```

然后，执行如下脚本。

```cpp
python systrace.py -b 32768 -t 10 -a packagename -o outputfile.html sched gfx view wm am app
复制代码
```

这里可以大家普及下各个字端的含义：

- b： 收集数据的大小
- t：时间
- a：监听的应用包名
- o： 生成文件的名称

Systrace开销较小，属于轻量级的工具，并且可以直观反映CPU的利用率。

# 2，UI渲染优化

Android系统每隔16ms就会重新绘制一次Activity，因此，我们的应用必须在16ms内完成屏幕刷新的全部逻辑操作，每一帧只能停留16ms，否则就会出现掉帧现象。Android应用卡顿与否与UI渲染有直接的关系。

## 2.1CPU、GPU

对于大多数手机的屏幕刷新频率是60hz，也就是如果在1000/60=16.67ms内没有把这一帧的任务执行完毕，就会发生丢帧的现象，丢帧是造成界面卡顿的直接原因，渲染操作通常依赖于两个核心组件：CPU与GPU。CPU负责包括Measure，Layout等计算操作，GPU负责Rasterization（栅格化）操作。

所谓栅格化，就是将矢量图形转换为位图的过程，手机上显示是按照一个个像素来显示的，比如将一个Button、TextView等组件拆分成一个个像素显示到手机屏幕上。而UI渲染优化的目的就是减轻CPU、GPU的压力，除去不必要的操作，保证每帧16ms以内处理完所有的CPU与GPU的计算、绘制、渲染等等操作，使UI顺滑、流畅的显示出来。

## 2.2 过度绘制

UI渲染优化的第一步就是找到Overdraw（过度绘制），即描述的是屏幕上的某个像素在同一帧的时间内被绘制了多次。在重叠的UI布局中，如果不可见的UI也在做绘制的操作或者后一个控件将前一个控件遮挡，会导致某些像素区域被绘制了多次，从而增加了CPU、GPU的压力。

那么如何找出布局中Overdraw的地方呢？很简单，就是打开手机里开发者选项，然后将调试GPU过度绘制的开关打开即可，然后就可以看到应用的布局是否被Overdraw，如下图所示。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/390a5862ee634eae9235024f4dc809fa~tplv-k3u1fbpfcp-zoom-1.image) 蓝色、淡绿、淡红、深红代表了4种不同程度的Overdraw情况，1x、2x、3x和4x分别表示同一像素上同一帧的时间内被绘制了多次，1x就表示一次(最理想情况)，4x表示4次(最差的情况)，而我们需要消除的就是3x和4x。

## 2.3 解决自定义View的OverDraw

我们知道，自定义View的时候有时会重写onDraw方法，但是Android系统是无法检测onDraw里面具体会执行什么操作，从而系统无法为我们做一些优化。这样对编程人员要求就高了，如果View有大量重叠的地方就会造成CPU、GPU资源的浪费，此时我们可以使用canvas.clipRect()来帮助系统识别那些可见的区域。

这个方法可以指定一块矩形区域，只有在这个区域内才会被绘制，其他的区域会被忽视。下面我们通过谷歌提供的一个小的Demo进一步说明OverDraw的使用。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4d65c8b39909426c9e4928e3c8e746d5~tplv-k3u1fbpfcp-zoom-1.image) 在下面的代码中，DroidCard类封装的是卡片的信息，代码如下。

```cpp
public class DroidCard {

public int x;//左侧绘制起点
public int width;
public int height;
public Bitmap bitmap;

public DroidCard(Resources res,int resId,int x){
this.bitmap = BitmapFactory.decodeResource(res,resId);
this.x = x;
this.width = this.bitmap.getWidth();
this.height = this.bitmap.getHeight();
 }
}
复制代码
```

自定义View的代码如下：

```cpp
public class DroidCardsView extends View {
//图片与图片之间的间距
private int mCardSpacing = 150;
//图片与左侧距离的记录
private int mCardLeft = 10;

private List<DroidCard> mDroidCards = new ArrayList<DroidCard>();

private Paint paint = new Paint();

public DroidCardsView(Context context) {
super(context);
initCards();
}

public DroidCardsView(Context context, AttributeSet attrs) {
super(context, attrs);
initCards();
}
/**
* 初始化卡片集合
*/
protected void initCards(){
Resources res = getResources();
mDroidCards.add(new DroidCard(res,R.drawable.alex,mCardLeft));

mCardLeft+=mCardSpacing;
mDroidCards.add(new DroidCard(res,R.drawable.claire,mCardLeft));

mCardLeft+=mCardSpacing;
mDroidCards.add(new DroidCard(res,R.drawable.kathryn,mCardLeft));
}

@Override
protected void onDraw(Canvas canvas) {
super.onDraw(canvas);
for (DroidCard c : mDroidCards){
drawDroidCard(canvas, c);
}
invalidate();
}

/**
* 绘制DroidCard
*/
private void drawDroidCard(Canvas canvas, DroidCard c) {
canvas.drawBitmap(c.bitmap,c.x,0f,paint);
}
}
复制代码
```

然后，我们运行代码，打开手机的overdraw开关，效果如下： ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/eea98c9f56af400499088c03ad05b7f8~tplv-k3u1fbpfcp-zoom-1.image) 可以看到，淡红色区域明显被绘制了三次，是因为图片的重叠造成的。那怎么解决这种问题呢？其实，分析可以发现，最下面的图片只需要绘制三分之一即可，保证最下面两张图片只需要回执其三分之一最上面图片完全绘制出来就可。优化后的代码如下：

```cpp
public class DroidCardsView extends View {

//图片与图片之间的间距
private int mCardSpacing = 150;
//图片与左侧距离的记录
private int mCardLeft = 10;

private List<DroidCard> mDroidCards = new ArrayList<DroidCard>();

private Paint paint = new Paint();

public DroidCardsView(Context context) {
super(context);
initCards();
}

public DroidCardsView(Context context, AttributeSet attrs) {
super(context, attrs);
initCards();
}
/**
* 初始化卡片集合
*/
protected void initCards(){
Resources res = getResources();
mDroidCards.add(new DroidCard(res, R.drawable.alex,mCardLeft));

mCardLeft+=mCardSpacing;
mDroidCards.add(new DroidCard(res, R.drawable.claire,mCardLeft));

mCardLeft+=mCardSpacing;
mDroidCards.add(new DroidCard(res, R.drawable.kathryn,mCardLeft));
}

@Override
protected void onDraw(Canvas canvas) {
super.onDraw(canvas);
for (int i = 0; i < mDroidCards.size() - 1; i++){
drawDroidCard(canvas, mDroidCards,i);
}
drawLastDroidCard(canvas,mDroidCards.get(mDroidCards.size()-1));
invalidate();
}

/**
* 绘制最后一个DroidCard
* @param canvas
* @param c
*/
private void drawLastDroidCard(Canvas canvas,DroidCard c) {
canvas.drawBitmap(c.bitmap,c.x,0f,paint);
}

/**
* 绘制DroidCard
* @param canvas
* @param mDroidCards
* @param i
*/
private void drawDroidCard(Canvas canvas,List<DroidCard> mDroidCards,int i) {
DroidCard c = mDroidCards.get(i);
canvas.save();
canvas.clipRect((float)c.x,0f,(float)(mDroidCards.get(i+1).x),(float)c.height);
canvas.drawBitmap(c.bitmap,c.x,0f,paint);
canvas.restore();
 }
}

复制代码
```

在上面的代码中，我们使用Canvas的clipRect方法，绘制之前裁剪出一个区域，这样绘制的时候只在这区域内绘制，超出部分不会绘制出来。重新运行上面的代码，效果如下图所示。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/9d4ae67402ca436aaf7c3da80b519d1c~tplv-k3u1fbpfcp-zoom-1.image)

## 2.4 Hierarchy Viewer

Hierarchy Viewer 是 Android Device Monitor 中内置的一种工具，可让开发者测量布局层次结构中每个视图的布局速度，以及帮助开发者查找视图层次结构导致的性能瓶颈。Hierarchy Viewer可以通过红、黄、绿三种不同的颜色来区分布局的Measure、Layout、Executive的相对性能表现情况。

打开

1. 将设备连接到计算机。如果设备上显示对话框提示您允许 USB 调试吗？，请点按确定。
2. 在 Android Studio 中打开您的项目，在您的设备上构建并运行项目。
3. 启动 Android Device Monitor。Android Studio 可能会显示 Disable adb integration 对话框，因为一次只能有一个进程可以通过 adb 连接到设备，并且 Android Device Monitor 正在请求连接。因此，请点击 Yes。
4. 在菜单栏中，依次选择 Window > Open Perspective，然后点击 Hierarchy View。
5. 在左侧的 Windows 标签中双击应用的软件包名称。这会使用应用的视图层次结构填充相关窗格。

![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/54bbac59cede448196b87f5e686de022~tplv-k3u1fbpfcp-zoom-1.image) 提升布局性能的关键点是尽量保持布局层级的扁平化，避免出现重复的嵌套布局。如果我们写的布局层级比较深会严重增加CPU的负担，造成性能的严重卡顿，关于Hierarchy Viewer的使用可以参考：[使用 Hierarchy Viewer 分析布局](https://developer.android.google.cn/studio/profile/hierarchy-viewer)。

## 2.5 内存抖动

在我们优化过view的树形结构和overdraw之后，可能还是感觉自己的app有卡顿和丢帧，或者滑动慢等问题，我们就要查看一下是否存在内存抖动情况了。所谓内存抖动，指的是内存频繁创建和GC造成的UI线程被频繁阻塞的现象。

Android有自动管理内存的机制，但是对内存的不恰当使用仍然容易引起严重的性能问题。在同一帧里面创建过多的对象是件需要特别引起注意的事情，在同一帧里创建大量对象可能引起GC的不停操作，执行GC操作的时候，所有线程的任何操作都会需要暂停，直到GC操作完成。大量不停的GC操作则会显著占用帧间隔时间。如果在帧间隔时间里面做了过多的GC操作，那么就会造成页面卡顿。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ff7a13742c7047f4be8d91d3eaeba55d~tplv-k3u1fbpfcp-zoom-1.image) 在Android开发中，导致GC频繁操作有两个主要原因：

- 内存抖动，所谓内存抖动就是短时间产生大量对象又在短时间内马上释放。
- 短时间产生大量对象超出阈值，内存不够，同样会触发GC操作。

Android的内存抖动可以使用Android Studio的Profiler进行检测。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/801c3ff9787447e3b374b48471b7d538~tplv-k3u1fbpfcp-zoom-1.image) 然后，点击record记录内存信息，查找发生内存抖动位置，当然也可直接通过Jump to Source定位到代码位置。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b226cd0a337c4758aeb95092e9eea586~tplv-k3u1fbpfcp-zoom-1.image)

为了避免发生内存抖动，我们需要避免在for循环里面分配对象占用内存，需要尝试把对象的创建移到循环体之外，自定义View中的onDraw方法也需要引起注意，每次屏幕发生绘制以及动画执行过程中，onDraw方法都会被调用到，避免在onDraw方法里面执行复杂的操作，避免创建对象。对于那些无法避免需要创建对象的情况，我们可以考虑对象池模型，通过对象池来解决频繁创建与销毁的问题，但是这里需要注意结束使用之后，需要手动释放对象池中的对象。

# 3，内存优化

## 3.1 内存管理

在前面Java基础环节，我们对Java的内存管理模型也做了基本的介绍，参考链接：[Android 面试之必问Java基础](https://blog.csdn.net/xiangzhihong8/article/details/115309574?spm=1001.2014.3001.5501)

### 3.1.1 内存区域

在Java的内存模型中，将内存区域划分为方法区、堆、程序计数器、本地方法栈、虚拟机栈五个区域，如下图。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3305e841c6b8470989bb0b649aa766a3~tplv-k3u1fbpfcp-zoom-1.image) **方法区**

- 线程共享区域，用于存储类信息、静态变量、常量、即时编译器编译出来的代码数据。
- 无法满足内存分配需求时会发生OOM。

**堆**

- 线程共享区域，是JAVA虚拟机管理的内存中最大的一块，在虚拟机启动时创建。
- 存放对象实例，几乎所有的对象实例都在堆上分配，GC管理的主要区域。

**虚拟机栈**

- 线程私有区域，每个java方法在执行的时候会创建一个栈帧用于存储局部变量表、操作数栈、动态链接、方法出口等信息。方法从执行开始到结束过程就是栈帧在虚拟机栈中入栈出栈过程。
- 局部变量表存放编译期可知的基本数据类型、对象引用、returnAddress类型。所需的内存空间会在编译期间完成分配，进入一个方法时在帧中局部变量表的空间是完全确定的，不需要运行时改变。
- 若线程申请的栈深度大于虚拟机允许的最大深度，会抛出SatckOverFlowError错误。
- 虚拟机动态扩展时，若无法申请到足够内存，会抛出OutOfMemoryError错误。

**本地方法栈**

- 为虚拟机中Native方法服务，对本地方法栈中使用的语言、数据结构、使用方式没有强制规定，虚拟机可自有实现。
- 占用的内存区大小是不固定的，可根据需要动态扩展。

**程序计数器**

- 一块较小的内存空间，线程私有，存储当前线程执行的字节码行号指示器。
- 字节码解释器通过改变这个计数器的值来选取下一条需要执行的字节码指令：分支、循环、跳转等。
- 每个线程都有一个独立的程序计数器
- 唯一一个在java虚拟机中不会OOM的区域

### 3.1.2 垃圾回收

**标记清除算法** 标记清除算法主要分为有两个阶段，首先标记出需要回收的对象，然后咋标记完成后统一回收所有标记的对象； 缺点：

- 效率问题：标记和清除两个过程效率都不高。
- 空间问题：标记清除之后会导致很多不连续的内存碎片，会导致需要分配大对象时无法找到足够的连续空间而不得不触发GC的问题。

**复制算法** 将可用内存按空间分为大小相同的两小块，每次只使用其中的一块，等这块内存使用完了将还存活的对象复制到另一块内存上，然后将这块内存区域对象整体清除掉。每次对整个半区进行内存回收，不会导致碎片问题，实现简单且效率高效。 缺点： 需要将内存缩小为原来的一半，空间代价太高。

**标记整理算法** 标记整理算法标记过程和标记清除算法一样，但清除过程并不是对可回收对象直接清理，而是将所有存活对象像一端移动，然后集中清理到端边界以外的内存。

**分代回收算法** 当代虚拟机垃圾回收算法都采用分代收集算法来收集，根据对象存活周期不同将内存划分为新生代和老年代，再根据每个年代的特点采用最合适的回收算法。

- 新生代存活对象较少，每次垃圾回收都有大量对象死去，一般采用复制算法，只需要付出复制少量存活对象的成本就可以实现垃圾回收；
- 老年代存活对象较多，没有额外空间进行分配担保，就必须采用标记清除算法和标记整理算法进行回收；

## 3.2 内存泄漏

所谓内存泄露，指的是内存中存在的没有用的确无法回收的对象。表现的现象是会导致内存抖动，可用内存减少，进而导致GC频繁、卡顿、OOM。

下面是一段模拟内存泄漏的代码：

```cpp
/**
 * 模拟内存泄露的Activity
 */
public class MemoryLeakActivity extends AppCompatActivity implements CallBack{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoryleak);
        ImageView imageView = findViewById(R.id.iv_memoryleak);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.splash);
        imageView.setImageBitmap(bitmap);
        
        // 添加静态类引用
        CallBackManager.addCallBack(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        CallBackManager.removeCallBack(this);
    }
    @Override
    public void dpOperate() {
        // do sth
    }
复制代码
```

当我们使用Memory Profiler工具查看内存曲线，发现内存在不断的上升，如下图所示。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/137450ada78141dab8f9bf2fa8c63e5c~tplv-k3u1fbpfcp-zoom-1.image) 如果想分析定位具体发生内存泄露位置，我们可以借助MAT工具。首先，使用MAT工具生成hprof文件，点击dump将当前内存信息转成hprof文件，需要对生成的文件转换成MAT可读取文件。执行一下转换命令即可完成转换，生成的文件位于Android/sdk/platorm-tools路径下。

```cpp
hprof-conv 刚刚生成的hprof文件 memory-mat.hprof
复制代码
```

使用mat打开刚刚转换的hprof文件，然后使用Android Studio打开hprof文件，如下图所示。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f8c5754488e343d7a1796a82d3d1191b~tplv-k3u1fbpfcp-zoom-1.image) 然后点击面板的【Historygram】，搜索MemoryLeakActivity，即可查看对应的泄漏文件的相关信息。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e53dbf25707f45fabcefd85c68ec039e~tplv-k3u1fbpfcp-zoom-1.image) 然后，查看所有引用对象，并得到相关的引用链，如下图。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4f647dbbbc544488b2a6a47ce533f98b~tplv-k3u1fbpfcp-zoom-1.image) ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8d7eed43eda5444babefca90bef66f6f~tplv-k3u1fbpfcp-zoom-1.image) 可以看到GC Roots是CallBackManager ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0796de4a39a64acd9d54ac96fc2d28e0~tplv-k3u1fbpfcp-zoom-1.image) 所以，我们在Activity销毁时将CallBackManager引用移除即可。

```
@Override
protected void onDestroy() {
    super.onDestroy();
    CallBackManager.removeCallBack(this);
}

复制代码
```

当然，上面只是一个MAT分析工具使用的示例，其他的内存泄露都可以借助MAT分析工具解决。

## 3.3 大图内存优化

在Android开发中，经常会遇到加载大图导致内存泄露的问题，对于这种场景，有一个通用的解决方案，即使用ARTHook对不合理图片进行检测。我们知道，获取Bitmap占用的内存主要有两种方式：

- 通过getByteCount方法，但是需要在运行时获取
- width `*` height `*` 一个像素所占内存 `*` 图片所在资源目录压缩比

通过ARTHook方法可以优雅的获取不合理图片，侵入性低，但是因为兼容性问题一般在线下使用。使用ARTHook需要安装以下依赖：

```
implementation 'me.weishu:epic:0.3.6'
复制代码
```

然后自定义实现Hook方法，如下所示。

```
public class CheckBitmapHook extends XC_MethodHook {
    @Override protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        ImageView imageView = (ImageView)param.thisObject;
        checkBitmap(imageView,imageView.getDrawable());
    }
    private static void checkBitmap(Object o,Drawable drawable) {
        if(drawable instanceof BitmapDrawable && o instanceof View) {
            final Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if(bitmap != null) {
                final View view = (View)o;
                int width = view.getWidth();
                int height = view.getHeight();
                if(width > 0 && height > 0) {
                    if(bitmap.getWidth() > (width <<1) && bitmap.getHeight() > (height << 1)) {
                        warn(bitmap.getWidth(),bitmap.getHeight(),width,height,
                                new RuntimeException("Bitmap size is too large"));
                    }
                } else {
                    final Throwable stacktrace = new RuntimeException();
                    view.getViewTreeObserver().addOnPreDrawListener(
                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override public boolean onPreDraw() {
                                    int w = view.getWidth();
                                    int h = view.getHeight();
                                    if(w > 0 && h > 0) {
                                        if (bitmap.getWidth() >= (w << 1)
                                                && bitmap.getHeight() >= (h << 1)) {
                                            warn(bitmap.getWidth(), bitmap.getHeight(), w, h, stacktrace);
                                        }
                                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                                    }
                                    return true;
                                }
                            });
                }
            }
        }
    }
    private static void warn(int bitmapWidth, int bitmapHeight, int viewWidth, int viewHeight, Throwable t) {
        String warnInfo = new StringBuilder("Bitmap size too large: ")
                .append("\n real size: (").append(bitmapWidth).append(',').append(bitmapHeight).append(')')
                .append("\n desired size: (").append(viewWidth).append(',').append(viewHeight).append(')')
                .append("\n call stack trace: \n").append(Log.getStackTraceString(t)).append('\n')
                .toString();
        LogUtils.i(warnInfo);

复制代码
```

最后，在Application初始化时注入Hook。

```
DexposedBridge.hookAllConstructors(ImageView.class, new XC_MethodHook() {
    @Override protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        DexposedBridge.findAndHookMethod(ImageView.class,"setImageBitmap", Bitmap.class,
                new CheckBitmapHook());
    }
});
复制代码
```

## 3.4 线上监控

#### 3.4.1 常规方案

**方案一** 在特定场景中获取当前占用内存大小，如果当前内存大小超过系统最大内存80%，对当前内存进行一次Dump（Debug.dumpHprofData()），选择合适时间将hprof文件进行上传，然后通过MAT工具手动分析该文件。

缺点：

- Dump文件比较大，和用户使用时间、对象树正相关。
- 文件较大导致上传失败率较高，分析困难。

**方案二** 将LeakCannary带到线上，添加预设怀疑点，对怀疑点进行内存泄露监控，发现内存泄露回传到服务端。

缺点：

- 通用性较低，需要预设怀疑点，对没有预设怀疑点的地方监控不到。
- LeakCanary分析比较耗时、耗内存，有可能会发生OOM。

#### 3.4.2 LeakCannary改造

改造主要涉及以下几点：

- 将需要预设怀疑点改为自动寻找怀疑点，自动将前内存中所占内存较大的对象类中设置怀疑点。
- LeakCanary分析泄露链路比较慢，改造为只分析Retain size大的对象。
- 分析过程会OOM，是因为LeakCannary分析时会将分析对象全部加载到内存当中，我们可以记录下分析对象的个数和占用大小，对分析对象进行裁剪，不全部加载到内存当中。

完成的改造步骤如下：

1. 监控常规指标：待机内存、重点模块占用内存、OOM率
2. 监控APP一个生命周期内和重点模块界面的生命周期内的GC次数、GC时间等
3. 将定制的LeakCanary带到线上，自动化分析线上的内存泄露

# 4，网络优化

## 4.1 网络优化的影响

App的网络连接对于用户来说, 影响很多, 且多数情况下都很直观, 直接影响用户对这个App的使用体验. 其中较为重要的几点： **流量** ：App的流量消耗对用户来说是比较敏感的, 毕竟流量是花钱的嘛. 现在大部分人的手机上都有安装流量监控的工具App, 用来监控App的流量使用. 如果我们的App这方面没有控制好, 会给用户不好的使用体验。 **电量** ：电量相对于用户来说, 没有那么明显. 一般用户可能不会太注意. 但是如电量优化中的那样, 网络连接(radio)是对电量影响很大的一个因素. 所以我们也要加以注意。 **用户等待** ：也就是用户体验, 良好的用户体验, 才是我们留住用户的第一步. 如果App请求等待时间长, 会给用户网络卡, 应用反应慢的感觉, 如果有对比, 有替代品, 我们的App很可能就会被用户无情抛弃。

## 4.2 网络分析工具

网络分析可以借助的工具有Monitor、代理工具等。

### 4.2.1 Network Monitor

Android Studio内置的Monitor工具提供了一个Network Monitor，可以帮助开发者进行网络分析，下面是一个典型的Network Monitor示意图。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3c2cb4c10fa2450cbb6d44b130d7f36a~tplv-k3u1fbpfcp-zoom-1.image)

- Rx --- R(ecive) 表示下行流量，即下载接收。
- Tx --- T(ransmit) 表示上行流量，即上传发送。

Network Monitor实时跟踪选定应用的数据请求情况。 我们可以连上手机，选定调试应用进程, 然后在App上操作我们需要分析的页面请求。

### 4.2.2 代理工具

网络代理工具有两个作用，一个是截获网络请求响应包, 分析网络请求；另一个设置代理网络, 移动App开发中一般用来做不同网络环境的测试, 例如Wifi/4G/3G/弱网等。

现在，可以使用的代理工具有很多, 诸如Wireshark, Fiddler, Charles等。

## 4.3 网络优化方案

对于网络优化来说，主要从两个方面进行着手进行优化：

1. **减少活跃时间**：减少网络数据获取的频次，从而就减少了radio的电量消耗以及控制电量使用。
2. **压缩数据包的大小**：压缩数据包可以减少流量消耗，也可以让每次请求更快, 。

基于上面的方案，可以得到以下一些常见的解决方案：

### 4.3.1 接口设计

**1，API设计** App与服务器之间的API设计要考虑网络请求的频次，资源的状态等。以便App可以以较少的请求来完成业务需求和界面的展示。

例如, 注册登录. 正常会有两个API, 注册和登录, 但是设计API时我们应该给注册接口包含一个隐式的登录. 来避免App在注册后还得请求一次登录接口。

**2，使用Gzip压缩**

使用Gzip来压缩request和response, 减少传输数据量, 从而减少流量消耗。使用Retrofit等网络请求框架进行网络请求时，默认进行了Gzip的压缩。

**3，使用Protocol Buffer** 以前，我们传输数据使用的是XML, 后来使用JSON代替了XML, 很大程度上也是为了可读性和减少数据量。而在游戏开发中，为了保证数据的准确和及时性，Google推出了Protocol Buffer数据交换格式。

**4，依据网络情况获取不同分辨率的图片** 我们使用淘宝或者京东的时候，会看到应用会根据网络情况，获取不同分辨率的图片，避免流量的浪费以及提升用户的体验。

### 4.3.2 合理使用网络缓存

适当的使用缓存, 不仅可以让我们的应用看起来更快, 也能避免一些不必要的流量消耗，带来更好的用户体验。

**1，打包网络请求**

当接口设计不能满足我们的业务需求时。例如，可能一个界面需要请求多个接口，或是网络良好，处于Wifi状态下时我们想获取更多的数据等。这时就可以打包一些网络请求, 例如请求列表的同时, 获取Header点击率较高的的item项的详情数据。

**2，监听设备状态** 为了提升用户体验，我们可以对设备的使用状态进行监听，然后再结合JobScheduler来执行网络请求.。比方说Splash闪屏广告图片, 我们可以在连接到Wifi时下载缓存到本地; 新闻类的App可以在充电，Wifi状态下做离线缓存。

### 4.3.3 弱网测试&优化

**1，弱网测试** 有几种方式来模拟弱网进行测试：

**Android Emulator** 通常，我们创建和启动Android模拟器可以设置网络速度和延迟，如下图所示。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b0db8912a63540afaa555b9b1725ed79~tplv-k3u1fbpfcp-zoom-1.image) 然后，我们在启动时使用的emulator命令如下。

```
$emulator -netdelay gprs -netspeed gsm -avd Nexus_5_API_22
复制代码
```

**2，网络代理工具** 使用网络代理工具也可以模拟网络情况。以Charles为例，保持手机和PC处于同一个局域网, 在手机端wifi设置高级设置中设置代理方式为手动, 代理ip填写PC端ip地址, 端口号默认8888。

![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6be38c1a98ab4e68af025a99da6f1d6b~tplv-k3u1fbpfcp-zoom-1.image)

# 5，耗电优化

## 

事实上，如果我们的应用需要播放视频、需要获取 GPS 信息，亦或者是游戏应用，耗电都是比较严重的。如何判断哪些耗电是可以避免，或者是需要去优化的呢？我们可以打开手机自带的耗电排行榜，发现“王者荣耀”使用了 7 个多小时，这时用户对“王者荣耀”的耗电是有预期的。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7c8fcf6aa27147399f268f066f913d92~tplv-k3u1fbpfcp-zoom-1.image)

## 5.1 优化方向

假设这个时候发现某个应用他根本没怎么使用，但是耗电却非常多，那么就会被系统无情的杀掉。**所以耗电优化的第一个方向是优化应用的后台耗电。**

知道了系统是如何计算耗电的，我们也就可以知道应用在后台不应该做什么，例如长时间获取 WakeLock、WiFi 和蓝牙的扫描等，以及后台服务。为什么说耗电优化第一个方向就是优化应用后台耗电，因为大部分厂商预装项目要求最严格的正是应用后台待机耗电。

![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2959bf28f3df4049bd49b3b73d67623b~tplv-k3u1fbpfcp-zoom-1.image)

![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/91aadf66dc394157837e3874e8cdbb09~tplv-k3u1fbpfcp-zoom-1.image) 当然前台耗电我们不会完全不管，但是标准会放松很多。再来看看下面这张图，如果系统对你的应用弹出这个对话框，可能对于微信来说，用户还可以忍受，但是对其他大多数的应用来说，可能很多用户就直接把你加入到后台限制的名单中了。

**耗电优化的第二个方向是符合系统的规则，让系统认为你耗电是正常的。**

而 Android P 及以上版本是通过 Android Vitals 监控后台耗电，所以我们需要符合 Android Vitals 的规则，目前它的具体规则如下。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/903491c11ead42cd8b6b57f30aeef5eb~tplv-k3u1fbpfcp-zoom-1.image) 可以看到，Android系统目前比较关心是后台 Alarm 唤醒、后台网络、后台 WiFi 扫描以及部分长时间 WakeLock 阻止系统后台休眠，因为这些都有可能导致耗电问题。

## 5.2 耗电监控

### 5.2.1 Android Vitals

Android Vitals 的几个关于电量的监控方案与规则，可以帮助我们进行耗电监测。

- [Alarm Manager wakeup 唤醒过多](https://developer.android.com/topic/performance/vitals/wakeup)
- [频繁使用局部唤醒锁](https://developer.android.google.cn/topic/performance/vitals/wakelock)
- [后台网络使用量过高](https://developer.android.com/topic/performance/vitals/bg-network-usage)
- [后台 WiFi Scans 过多](https://developer.android.com/topic/performance/vitals/bg-wifi)

在使用了一段时间之后，我发现它并不是那么好用。以 Alarm wakeup 为例，Vitals 以每小时超过 10 次作为规则。由于这个规则无法做修改，很多时候我们可能希望针对不同的系统版本做更加细致的区分。其次跟 Battery Historian 一样，我们只能拿到 wakeup 的标记的组件，拿不到申请的堆栈，也拿不到当时手机是否在充电、剩余电量等信息。 下图是wakeup拿到的信息。

![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/656c1549fcf2433b8f513b2f4a5d1e9b~tplv-k3u1fbpfcp-zoom-1.image) 对于网络、WiFi scans 以及 WakeLock 也是如此。虽然 Vitals 帮助我们缩小了排查的范围，但是依然没办法确认问题的具体原因。

## 5.3 如何监控耗电

前面说过，Android Vitals并不是那么好用，而且对于国内的应用来说其实也根本无法使用。那我们的耗电监控系统应该监控哪些内容，又应该如何做呢？首先，我们看一下耗电监控具体应该怎么做呢？

- **监控信息**：简单来说系统关心什么，我们就监控什么，而且应该以后台耗电监控为主。类似 Alarm wakeup、WakeLock、WiFi scans、Network 都是必须的，其他的可以根据应用的实际情况。如果是地图应用，后台获取 GPS 是被允许的；如果是计步器应用，后台获取 Sensor 也没有太大问题。
- **现场信息**：监控系统希望可以获得完整的堆栈信息，比如哪一行代码发起了 WiFi scans、哪一行代码申请了 WakeLock 等。还有当时手机是否在充电、手机的电量水平、应用前台和后台时间、CPU 状态等一些信息也可以帮助我们排查某些问题。
- **提炼规则**：最后我们需要将监控的内容抽象成规则，当然不同应用监控的事项或者参数都不太一样。 由于每个应用的具体情况都不太一样，可以用来参考的简单规则。

![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/03270cb474b0435e9e87f2c0ada127e3~tplv-k3u1fbpfcp-zoom-1.image) ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e0c31e7b1470489b9aac3c6f1c30a1e3~tplv-k3u1fbpfcp-zoom-1.image)

### 5.3.2 Hook方案

明确了我们需要监控什么以及具体的规则之后，接下来我们来看一下电量监控的技术方案。这里首先来看一下Hook 方案。Hook 方案的好处在于使用者接入非常简单，不需要去修改代码，接入的成本比较低。下面我以几个比较常用的规则为例，看看如何使用 Java Hook 达到监控的目的。

**1，WakeLock** WakeLock 用来阻止 CPU、屏幕甚至是键盘的休眠。类似 Alarm、JobService 也会申请 WakeLock 来完成后台 CPU 操作。WakeLock 的核心控制代码都在PowerManagerService中，实现的方法非常简单，如下所示。

```
// 代理 PowerManagerService
ProxyHook().proxyHook(context.getSystemService(Context.POWER_SERVICE), "mService", this)；

@Override
public void beforeInvoke(Method method, Object[] args) {
    // 申请 Wakelock
    if (method.getName().equals("acquireWakeLock")) {
        if (isAppBackground()) {
            // 应用后台逻辑，获取应用堆栈等等     
         } else {
            // 应用前台逻辑，获取应用堆栈等等
         }
    // 释放 Wakelock
    } else if (method.getName().equals("releaseWakeLock")) {
       // 释放的逻辑    
    }
}
复制代码
```

**2，Alarm** Alarm 用来做一些定时的重复任务，它一共有四个类型，其中ELAPSED_REALTIME_WAKEUP和RTC_WAKEUP类型都会唤醒设备。同样，Alarm 的核心控制逻辑都在AlarmManagerService中，实现如下。

```
// 代理 AlarmManagerService
new ProxyHook().proxyHook(context.getSystemService
(Context.ALARM_SERVICE), "mService", this)；

public void beforeInvoke(Method method, Object[] args) {
    // 设置 Alarm
    if (method.getName().equals("set")) {
        // 不同版本参数类型的适配，获取应用堆栈等等
    // 清除 Alarm
    } else if (method.getName().equals("remove")) {
        // 清除的逻辑
    }
}
复制代码
```

除了WakeLock和Alarm外，对于后台 CPU，我们可以使用卡顿监控相关的方法；对于后台网络，同样我们可以通过网络监控相关的方法；对于 GPS 监控，我们可以通过 Hook 代理LOCATION_SERVICE；对于 Sensor，我们通过 Hook SENSOR_SERVICE中的“mSensorListeners”，可以拿到部分信息。

最后，我们将申请资源到的堆栈信息保存起来。当我们触发某个规则上报问题的时候，可以将收集到的堆栈信息、电池是否充电、CPU 信息、应用前后台时间等辅助信息上传到后台即可。

### 5.3.3 插桩法

使用 Hook 方式虽然简单，但是某些规则可能不太容易找到合适的 Hook 点，而且在 Android P 之后，很多的 Hook 点都不支持了。出于兼容性考虑，我首先想到的是插桩法。以 WakeLock 为例：

```
public class WakelockMetrics {
    // Wakelock 申请
    public void acquire(PowerManager.WakeLock wakelock) {
        wakeLock.acquire();
        // 在这里增加 Wakelock 申请监控逻辑
    }
    // Wakelock 释放
    public void release(PowerManager.WakeLock wakelock, int flags) {
        wakelock.release();
        // 在这里增加 Wakelock 释放监控逻辑
    }
}
复制代码
```

如果你对电量消耗又研究，那么肯定知道Facebook 的耗电监控的开源库Battery-Metrics，它监控的数据非常全，包括 Alarm、WakeLock、Camera、CPU、Network 等，而且也有收集电量充电状态、电量水平等信息。不过，遗憾的是Battery-Metrics 只是提供了一系列的基础类，在实际使用时开发者仍然需要修改大量的源码。

# 6，安装包优化

现在市面上的App，小则几十M，大则上百M。安装包越小，下载时省流量，用户好的体验，下载更快，安装更快。那么对于安装包，我们可以从哪些方面着手进行优化呢？

## 6，1 常用的优化策略

**1，清理无用资源** 在android打包过程中，如果代码有涉及资源和代码的引用，那么就会打包到App中，为了防止将这些废弃的代码和资源打包到App中，我们需要及时地清理这些无用的代码和资源来减小App的体积。清理的方法是，依次点击android Studio的【Refactor】->【Remove unused Resource】，如下图所示。 ![在这里插入图片描述](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7cecf28385104eee9224802016f6db90~tplv-k3u1fbpfcp-zoom-1.image) **2，使用Lint工具**

Lint工具还是很有用的，它给我们需要优化的点：

- 检测没有用的布局并且删除
- 把未使用到的资源删除
- 建议String.xml有一些没有用到的字符也删除掉

**3，开启shrinkResources去除无用资源** 在build.gradle 里面配置shrinkResources true，在打包的时候会自动清除掉无用的资源，但经过实验发现打出的包并不会，而是会把部分无用资源用更小的东西代替掉。注意，这里的“无用”是指调用图片的所有父级函数最终是废弃代码，而shrinkResources true 只能去除没有任何父函数调用的情况。

```
    android {
        buildTypes {
            release {
                shrinkResources true
            }
        }
    }
复制代码
```

除此之外，大部分应用其实并不需要支持几十种语言的国际化支持，还可以删除语言支持文件。

## 6.2 资源压缩

在android开发中，内置的图片是很多的，这些图片占用了大量的体积，因此为了缩小包的体积，我们可以对资源进行压缩。常用的方法有：

1. 使用压缩过的图片：使用压缩过的图片，可以有效降低App的体积。
2. 只用一套图片：对于绝大对数APP来说，只需要取一套设计图就足够了。
3. 使用不带alpha值的jpg图片：对于非透明的大图，jpg将会比png的大小有显著的优势，虽然不是绝对的，但是通常会减小到一半都不止。
4. 使用tinypng有损压缩：支持上传PNG图片到官网上压缩，然后下载保存，在保持alpha通道的情况下对PNG的压缩可以达到1/3之内，而且用肉眼基本上分辨不出压缩的损失。
5. 使用webp格式：webp支持透明度，压缩比比，占用的体积比JPG图片更小。从Android 4.0+开始原生支持，但是不支持包含透明度，直到Android 4.2.1+才支持显示含透明度的webp，使用的时候要特别注意。
6. 使用svg：矢量图是由点与线组成,和位图不一样,它再放大也能保持清晰度，而且使用矢量图比位图设计方案能节约30～40%的空间。
7. 对打包后的图片进行压缩：使用7zip压缩方式对图片进行压缩，可以直接使用微信开源的AndResGuard压缩方案。

```
    apply plugin: 'AndResGuard'
    buildscript {
        dependencies {
            classpath 'com.tencent.mm:AndResGuard-gradle-plugin:1.1.7'
        }
    }
    andResGuard {
        mappingFile = null
        use7zip = true
        useSign = true
        keepRoot = false
        // add <your_application_id>.R.drawable.icon into whitelist.
        // because the launcher will get thgge icon with his name
        def packageName = <your_application_id>
                whiteList = [
        //for your icon
        packageName + ".R.drawable.icon",
                //for fabric
                packageName + ".R.string.com.crashlytics.*",
                //for umeng update
                packageName + ".R.string.umeng*",
                packageName + ".R.string.UM*",
                packageName + ".R.string.tb_*",
                packageName + ".R.layout.umeng*",
                packageName + ".R.layout.tb_*",
                packageName + ".R.drawable.umeng*",
                packageName + ".R.drawable.tb_*",
                packageName + ".R.anim.umeng*",
                packageName + ".R.color.umeng*",
                packageName + ".R.color.tb_*",
                packageName + ".R.style.*UM*",
                packageName + ".R.style.umeng*",
                packageName + ".R.id.umeng*"
        ]
        compressFilePattern = [
        "*.png",
                "*.jpg",
                "*.jpeg",
                "*.gif",
                "resources.arsc"
        ]
        sevenzip {
            artifact = 'com.tencent.mm:SevenZip:1.1.7'
            //path = "/usr/local/bin/7za"
        }
    }

复制代码
```

## 6.3 资源动态加载

在前端开发中，动态加载资源可以有效减小apk的体积。除此之外，只提供对主流架构的支持，比如arm，对于mips和x86架构可以考虑不支持，这样可以大大减小APK的体积。

当然，除了上面提到的场景的优化场景外，Android App的优化还包括存储优化、多线程优化以及奔溃处理等方面。