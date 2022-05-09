# Navigation
* 导航图 xml 文件 
* NavHost 显示目标都空白容器。
* NavController 管理

优势
* 处理fragment事物
* 默认情况下，正常处理往返操作。
* 为动画和转换提供标准资源。
* 实现和处理深层链接。DeepLink 技术,主要应用场景是通过web 界面直接调用Android 原生app,并且把需要对参数通过Uri的形式传递过来。
* 包含导航模式（抽屉，底部导航 等）
* safe Args 可在目标之间导航和传递数据时提供类安排的Gradle 插件。
* viewModel 支持
 
### 导航图
> xml 文件
* startDestination="显示的第一个fragment的id"
* fragment 标签 对应一个fragment.class  
  * id fragment的id
  * name 包名+类名称
  * layout 指定的xml 文件地址 存在一个问题，如果我这个地方设置的和fragment的不一样，显示fragment的还是配置的。
  * label 名称
  * action 事件，用于打开fragment 
    * id 
    * destination 目的fragment 的id 
    * enterAnim 进场动画
    * exitAnim 退出动画
  * argument fragment启动参数。放到fragment中。
  * deepLink url=""  
### NavHost 
> activity xml中 包裹 一个 fragment 标签
* fragment 
  * name="androidx.navigation.fragment.NavHostFragment"
  * navGraph="导航图"
  * defaultNavHost="true" 代表回退栈由NavHost 进行管理。默认是false 

> JAVA 代码中，Navigation.findNavController(this,fragment的id) 获取到 NavController
#### action 跳转
Navigation.findNavController(getView).navigate(action的id,bundle);
bundle 会存放到fragment argument 中。
#### 配合 BottomNavigationView
* 需要一个 menu,menu item 中的id是 需要跳转fragment中的id
* 在BottomNavigationView 中设置menu
  * setOnNavigationItemSelectedListener 可以做点击事件拦截。如果需要登录。可以通过NavController 自己跳转。
  * 多次点击会有回退栈的问题，因为action 写到了fragment里面，所以所有action都需要标记进去。
  * 可以对action 放到导航图直接子类中，用于解决fragment存在相同的action 
* NavigationUI 绑定 NavController 
#### 深层链接 
* 外部进入app
* app内部web 访问app
* app导航访问app 

#### BottomNavigationView  
 
 
## 原理 
###  HavHostFragment
> HavHostFragment是fragment的子类。 在onCreate 中创建了一个 NavHostController。
* 创建NavController 的时候，添加了两个导航器 NavGraohNavigator(xml 文件被实例化到这个里面了)和 ActivityNavigator 
* 在onCreate 中对于navController 添加了一个 dialogFragmentNavigator 和一个fragmentNavigator
  ![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/%20image/20220509170942.png)
## 优化

 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 