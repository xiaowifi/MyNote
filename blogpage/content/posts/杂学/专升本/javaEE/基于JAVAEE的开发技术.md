+++
date = "2020-10-01"
title = "java EE"
description = "java EE"
tags = [ "java EE"]

series = ["专升本"]
featured = false
draft = true 
+++
![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/%E6%97%A5%E5%B8%B8%E6%90%AC%E7%A0%96%E5%A4%B4.png)

1.	
下列哪些表达式所存储的对象可以被servlet或jsp在session里使用
	(A)	response.setAttribute(name，value);
	(B)	getSession().setAttribute(name，value);
	(C)	request.setAttribute(name，value);
	(D)	servlet.getServletContext().setAttribute(name，value);
[参考答案：D]  分值：5

 
2.	
下面哪个jsp隐含对象可以实现多次请求共享（选择两个答案）
	(A)	applicatio
	(B)	request
	(C)	page
	(D)	get
[参考答案：A]  分值：5

 
3.	
看下列HTML标签：

```
<html>

  <body>

      <a href=”/servlet/MyFirstServlet”>Make me say Hello World!</a

  </body>

<html>
``` 

点击超级链接后下列哪一个servlet方法将被调用： 。
	(A)	doPost
	(B)	doGET
	(C)	doPOST
	(D)	doGet
[参考答案：D]  分值：5

 
4.	
关于FORM表单提交的HTTP的GET方法，下列哪个说法是错误的？
	(A)	不能向服务器提交无限长度的数据
	(B)	不能向服务器提交多值参数
	(C)	不能向服务器提交二进制数据
	(D)	参数附在URL后面
[参考答案：B]  分值：5


 
5.	
向客户端发送一个pdf文档，设置文档类型时，下列那个语句是正确的
	(A)	response.setType(“application/pdf”)
	(B)	response.setType(“application/bin”)
	(C)	response.setContentType(“application/bin”)
	(D)	response.setContentType(“application/pdf”)
[参考答案：D]  分值：5


 
6.	
下列哪一个JSP标记是正确的 ？
	(A)	<%@ include page=”notice.html” %><%@ include page=”notice.html” %>
	(B)	<jsp:include file=”notice.html” />
	(C)	<jsp:include page=”notice.html” />
	(D)	<%! include file=”notice.html” %><%! include file=”notice.html” %>
[参考答案：C]  分值：5

 
7.	
下面那个表达式表示会话永不过期
	(A)	setTimeout(0)
	(B)	setMaxInactiveInterval(-1)
	(C)	setMaxInactiveInterval(0)
	(D)	setTimeout(-1)
[参考答案：B]  分值：5

 
8.	
在web.xml中元素表示从servlet的URL的映射关系，它有两个子元素分别表示servlet名称和与其对应的URL映射，下面哪一个子元素表示URL映射
	(A)	<mapping>
	(B)	<url_pattern>
	(C)	<servlet-url>
	(D)	<url_mapping>
[参考答案：B]  分值：5

 
9.	
下列关于Tomcat说法正确的是 。
	(A)	Tomcat是一种编程思想
	(B)	Tomcat是一种开发工具
	(C)	Tomcat是一个免费的开源的Serlvet容器
	(D)	Tomcat是一种编程语言
[参考答案：C]  分值：5

 
10.	
在jsp页面中导入java.util包，使用下列哪个标记
	(A)	<%@ page java =”java.util.*” %><%@ page java =”java.util.*” %>
	(B)	<%@ import =”java.util.*” @%><%@ import =”java.util.*” @%>
	(C)	<%@ import =”java.util.*” %><%@ import =”java.util.*” %>
	(D)	<%@ page import =”java.util.*” %><%@ page import =”java.util.*” %>
[参考答案：A]  分值：5

 
11.	
下面是web.xml中的片段
```` 
<context>

  <param-name>user</param-name>

  <param-value>test</param-name>

</context>  
````
                                                                     
在servlet中要得到上面的参数，下面哪个表达式是正确的

	(A)	getServletConfig().getAttribute(“user”)
	(B)	getServletContext().getInitParameter(“user”)
	(C)	getServletConfig().getInitParameter(“user”)
	(D)	getServletContext().getAttribute(“user”)
[参考答案：B]  分值：5


 
12.	
在浏览器禁用cookie前提下，下列哪些技术能够记住客户端状态
	(A)	Http headers
	(B)	Httpsession
	(C)	隐藏域
	(D)	URL重写
[参考答案：D]  分值：5

 
13.	
下列关于Tomcat个目录说法错误的是
	(A)	Lib目录：包含Tomcat使用的JAR文件
	(B)	bin目录：包含启动/关闭脚本
	(C)	conf目录：包含不同的配置文件
	(D)	work目录：包含web项目示例，当发布web应用时，默认情况下把web文件夹放于此目录下
[参考答案：D]  分值：5

 
14.	
下列哪一个方法用于设置HttpServletResponse的内容类型？
	(A)	setParameter
	(B)	setContentType
	(C)	doPost
	(D)	setAttribute
[参考答案：B]  分值：5

 
15.	
下列哪个方法用于URL重写
	(A)	HttpServletResponse接口的rewriteURL()方法
	(B)	HttpServlet接口的rewriteURL()方法
	(C)	HttpServletRequest接口的encodeURL()方法
	(D)	HttpServletResponse接口的encodeURL()方法
[参考答案：D]  分值：5


 
16.	
看下列jsp标签
````
<html>

  <head>

  </head>

  <body>

<%@ include file=”top.jsp” %>

…..

<jsp:include page=”copyright.jsp”/>

  </body>

</html>
 ````
下列那个说法是正确的

	(A)	在jsp被转换编译时，不将top.jsp包含进来
	(B)	在jsp被转换编译时，top.jsp和copyright.jsp都被包含进来
	(C)	在jsp被请求时，top.jsp被包含进来
	(D)	只有copyright.jsp在请求该JSP资源时被包含进来
[参考答案：D]  分值：5


 
17.	
下面关于page指令说法中错误的是
	(A)	page指令用来定义JSP页面中的全局属性
	(B)	language属性用来指示所使用的语言，“java”是当前唯一可用的JSP语言
	(C)	除了import外，其他page指令定义的属性/值只能出现一次。
	(D)	一个JSP页面只能包含一个page指令
[参考答案：D]  分值：5


 
18.	
下列说法中错误的是
	(A)	表达式元素表示的是一个在脚本语言中被定义的表达式。
	(B)	<%-- This comment will not be visible in the page source --%>会在客户端的HTML源代码中产生和上面一样的数据
	(C)	<%! int i = 0; %><%! int i = 0; %>是一个合法的变量声明
	(D)	<!-- This file displays the user login screen -->会在客户端的HTML源代码中产生和上面一样的数据
[参考答案：B]  分值：5

 
19.	
下列关于JSP编译指令说法错误的是
	(A)	编译指令用来设置全局变量、声明类要实现的方法和输出内容的类型等
	(B)	编译指令用于从JSP发送一个信息到容器上
	(C)	编译指令向客户端产生任何输出
	(D)	编译指令所有的指令都在整个JSP页面内有效
[参考答案：C]  分值：5

 
20.	
下列那个jsp标记用于得到一个javabean的属性
	(A)	jsp: getProperty
	(B)	jsp:useBean.property
	(C)	jsp:useBean.getProperty
	(D)	jsp:useBean
[参考答案：A]  分值：5

 
 
 1.	
 下列说法正确的是
 	(A)	JSP充当MVC模式的视图
 	(B)	Servlet充当MVC模式的模型
 	(C)	EJB充当MVC模式的模型
 	(D)	JavaBean充当MVC模式的控制器
 	(E)	Servlet充当MVC模式的控制器
 [参考答案：ACE]  分值：5

 2.	
 下列关于CallableStatement对象的说法哪些是正确的？
 	(A)	CallableStatement 对象用于执行对数据库已存储过程的调用。
 	(B)	CallableStatement 对象用于执行对数据库所有的调用。
 	(C)	在数据库调用过程中，可以通过设置IN参数向调用的存储过程提供执行所需的参数。
 	(D)	CallableStatement对象中，有一个通用的成员方法call，这个方法用于以名称的方式调用数据库中的存储过程。
 	(E)	在存储过程的调用中，通过OUT参数获取存储过程的执行结果。
 [参考答案：ACDE]  分值：5

 3.	
 实体Bean是由什么组成的
 	(A)	EntityManager对象。
 	(B)	persistence.xml的简单的XML部署描述文件。
 	(C)	纯粹的Java对象（POJO）。
 	(D)	实体
 	(E)	以上都是。
 [参考答案：BD]  分值：5

 4.	
 下列关于HTTP协议说法正确的是
 	(A)	HTTP是一种请求/响应式的协议。
 	(B)	HTTP请求消息中Accept表示浏览器可接受的MIME类型。
 	(C)	HTTP请求消息中Accept-Encoding表示浏览器能够进行解码的数据编码方式。
 	(D)	HTTP请求消息中Accept-Language表示浏览器所希望的语言种类。
 	(E)	HTTP请求消息中Host表示初始URL中的主机和端口。
 [参考答案：ABCDE]  分值：5

 5.	
 下面的描述正确的是
 	(A)	以上说法均不正确。
 	(B)	CallableStatement继承自PreparedStatement
 	(C)	ResultSet继承自Statement
 	(D)	Statement继承自PreparedStatement
 	(E)	PreparedStatement继承自Statement
 [参考答案：BE]  分值：5

 6.	
 以下关于传统EJB与新一代EJB之间的区别叙述正确的是
 	(A)	传统EJB中需要部署描述符、厂商专有文件、Ejb-jar文件等等，部署复杂，而新一代EJB3.0已经不需要部署描述符了。
 	(B)	新一代EJB3.0同传统EJB相比较，不再需要Home接口和对象接口了。
 	(C)	新一代EJB3.0简化了EJB组件的开发过程，改变了EJB的编程模型，为了适宜EJB3.0，EJB技术也随之改变。
 	(D)	新一代EJB3.0最大的改变是使用了Java元数据注释，大大的简化了EJB组件的开发过程。
 	(E)	以上说法均不正确。
 [参考答案：BD]  分值：5

 7.	
 当我们要在JSP页面中使用自定义标记时需要
 	(A)	引入这个标记的标记库，并指定前缀名
 	(B)	创建一个标记处理器
 	(C)	在JSP页面中使用taglib指令
 	(D)	在JSP页面中使用page指令
 	(E)	在tld文件中定义标记
 [参考答案：ABCE]  分值：5

 8.	
 下列关于ResultSet接口的说法哪些是正确的？
 	(A)	ResultSet接口被用来提供访问查询结果的数据表，查询结果被当作ResultSet对象而返回。
 	(B)	ResultSet接口提供大量的获得数据的方法，这些方法返回数据表中任意位置的数据。
 	(C)	ResultSet对象提供“指针”，指针每次访问数据库表的一行。
 	(D)	ResultSet 的next() 方法用来移动指针到数据表的下一行，如果到达表尾，next() 方法返回假的布尔值-false，否则为真。
 	(E)	以上说法都不对
 [参考答案：ABCD]  分值：5

 9.	
 B组件有几种不同的类型_______
 	(A)	企业Bean
 	(B)	消息驱动Bean
 	(C)	Java Bean
 	(D)	实体Bean
 	(E)	会话Bean
 [参考答案：BDE]  分值：5

 10.	
 以下关于EJB3.0中的依赖注入技术叙述正确的是
 	(A)	
 	(B)	为EJB组件指定依赖的资源可以使用@Resource注释来实现。
 	(C)	EJB3.0规范引入了依赖注入来用于引用资源和EJB相关对象。
 	(D)	以上说法均不正确。
 	(E)	在无状态会话Bean组件被创建后，Bean类可以通过@PostConstruct注册一个回调方法。
 [参考答案：BCE]  分值：5

 11.	
 下面关于pageContext对象说法中正确的是
 	(A)	pageContext对象创建和初始化都是由容器来完成的
 	(B)	pageContext对象为JSP页面包装页面的上下文。
 	(C)	removeAttribute()方法用来删除默认页面范围或特定范围之中的已命名对象。
 	(D)	getSession()方法返回当前页面的session对象。
 	(E)	getRequest()方法返回当前的request对象
 [参考答案：ABCDE]  分值：5

 12.	
 下面关于Tag接口说法中正确的是
 	(A)	setParent()方法用来设置标记的上一级标记
 	(B)	setPageContext()方法为初始化方法
 	(C)	release()方法用来释放标签程序占用的任何资源
 	(D)	doEndTag方法，EVAL_PAGE或者SKIP_PAGE，当返回值为EVAL_PAGE，jsp容器将继续执行jsp页面的内容，否则不执行
 	(E)	doStartTag()方法返回EVAL_BODY_INCLUDE和SKIP_BODY，返回EVAL_BODY_INCLUDE计算正文内容，返回SKIP_BODY不计算body。
 [参考答案：ABCDE]  分值：5

 13.	
 实体Bean与Session Bean有什么区别______
 	(A)	实体存在持久化、客户可见的状态。
 	(B)	对象引用不同，实体存在客户可见的、持久化身份（主键）。
 	(C)	实体的生命周期可能与应用本身的生命周期无关。
 	(D)	不能够直接通过远程访问到实体。
 	(E)	以上说法都不正确。
 [参考答案：ABCD]  分值：5

 14.	
 关于ResultSetMetaData 类的方法有哪些？
 	(A)	以上说法都不对
 	(B)	int getColumnDisplaySize() throws SQLException
 	(C)	String getColumnName(int column) throws SQLException
 	(D)	int getColumnCount() throws SQLException
 	(E)	String getColumnTypeName(int column) throws SQLException
 [参考答案：BCDE]  分值：5

 15.	
 下列说法中正确的是
 	(A)	Taglib指令允许页面使用自定义标记
 	(B)	include指令中file属性指定要包含的文件名
 	(C)	include指令只允许包含动态页面
 	(D)	必须在使用自定义标记之前使用<%@ taglib %>指令
 	(E)	include指令通知容器在当前位置将指定位置上的资源内容包含进来
 [参考答案：ABDE]  分值：5

  
 16.	
 下列有关MVC设计模式正确的是
 	(A)	M代表模型Model，V代表视图View，C代表控制器Controller。
 	(B)	采用MVC设计模式可以使软件的可维护性、可修复性、可扩展性、灵活性以及封装性大大提高。
 	(C)	MVC的“视图”，是代表用户与服务器进行交互的页面，即我们能看到和操作的页面。
 	(D)	Servlet是MVC的“控制器Controller”的典型代表。
 	(E)	MVC的“模型”，负责所有业务流程／状态的处理以及业务规则的制定。
 [参考答案：ABCDE]  分值：5

  
 17.	
 关于DriverManager 类下列哪些程序段是正确的？
 	(A)	Class.forName("org.gjt.mm.mysql.Driver");
 	(B)	以上说法都不对
 	(C)	Class.forName(new org.gjt.mm.mysql.Driver());
 	(D)	DriverManager.registerDriver("org.gjt.mm.mysql.Driver");
 	(E)	DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
 [参考答案：AE]  分值：5

  
 18.	
 以下几个术语正确对应的是
 	(A)	EJB—企业Java Bean
 	(B)	ISV—独立软件开发商
 	(C)	MDB—消息驱动Bean
 	(D)	IDE—集成开发环境
 	(E)	DI—依赖注入
 [参考答案：ABCDE]  分值：5

 19.	
 关于JNDI 和名字空间下列说法正确的是？
 	(A)	JNDI可以应用于EJB、RMI和JDBC等。它是网络上查找事务的标准方法。
 	(B)	Java命名和目录接口(Java Naming and Directory Interface)。
 	(C)	Java命名及目录接口作为 J2EE框架中的一种核心技术。
 	(D)	JNDI是一个通用接口。
 	(E)	以上说法都不对
 [参考答案：ABC]  分值：5

 20.	
 以下选项是EJB3.0中的注释，其中用于会话Bean的生命周期的是_______
 	(A)	@Init
 	(B)	@PrePassivate
 	(C)	@PostConstruct
 	(D)	@Move
 	(E)	@PostDestroy
 [参考答案：ABC]  分值：5


 如果数据库中某个字段为numberic型，可以通过结果集中的哪个方法获取
 	(A)	setNumberic()
 	(B)	getDouble()
 	(C)	getNumberic()
 	(D)	setDouble()
 [参考答案：B]  分值：5


 2.	
 在JDBC中使用事务，想要回滚事务的方法是
 	(A)	Connection的setAutoCommit()
 	(B)	Connection的commit()
 	(C)	Connection的rollback()
 	(D)	Connection的close()
 [参考答案：C]  分值：5


 3.	
 下列说法中错误的是
 	(A)	当Jsp文件被编译，送往浏览器时，<jsp:plugin>元素将会根据浏览器的版本替换成<object>或者<embed>元素。
 	(B)	以上说法全不对
 	(C)	Jsp:param操作被用来以“名——值”对的形式为其它标签提供附加信息
 	(D)	<jsp:plugin>元素用于在浏览器中播放或显示一个对象
 [参考答案：B]  分值：5


 4.	
 假设在helloapp应用中有一个Javabean文件Hello，它位于com.bean包下，那么这个bean的class文件应该放在什么目录下
 	(A)	helloapp/WEB-INF/classes/com/bean/
 	(B)	helloapp/WEB-INF/
 	(C)	helloapp/WEB-INF/classes/
 	(D)	helloapp/
 [参考答案：A]  分值：5


 5.	
 page指令通过 属性指定当前页面的内容类型和字符集。
 	(A)	contentType
 	(B)	buffer
 	(C)	language
 	(D)	isErrorPage
 [参考答案：A]  分值：5

 
 6.	
 下面对out对象说法错误的是
 	(A)	out.newLine()方法用来输出一个换行符
 	(B)	out对象的范围是application。
 	(C)	如果page指令选择了autoflush="true"，那么当出现由于当前的操作不清空缓存而造成缓冲区溢出的情况时，这个类的所有I/O操作会自动清空缓冲区的内容。
 	(D)	out对象用于输出数据
 [参考答案：B]  分值：5


 7.	
 假设在helloapp应用中有一个hello.jsp，它的文件路径如下：%JAVA_HOME%/webapps/helloapp/hello/hello.jsp，那么在浏览器端访问hello.jsp的URL是什么
 	(A)	http://localhost:8080/hello.jsp
 	(B)	http://localhost:8080/helloapp/hello.jsp
 	(C)	http://localhost:8080/helloapp/hello/hello.jsp
 	(D)	http://localhost:8080/webapps/helloapp/hello/dello.jsp
 [参考答案：C]  分值：5


 8.	
 jsp:forward和sendRedirect都是用来做页面跳转的，描述错误的是？
 	(A)	forward地址栏变化，可以跳转到任何页面和机器。
 	(B)	forward之后可以使用原来的request对象，而且效率较高。
 	(C)	forward地址栏不变化，只能在Web应用程序内的页面间跳转。
 	(D)	sendRedirect之后不可以使用原来的request对象，而且效率较低。
 [参考答案：A]  分值：5


 9.	
 对于“<%!”和“%><%!”和“%>”之间声明的变量，以下说法正确的是：
 	(A)	多个用户同时访问该页面时，每个用户对这些变量的操作都是互相独立的，不会互相影响
 	(B)	是JSP页面的局部变量
 	(C)	不是JSP页面的成员变量
 	(D)	多个用户同时访问该页面时，任何一个用户对这些变量的操作，都会影响到其他用户
 [参考答案：D]  分值：5


 10.	
 下面哪一个是正确使用JavaBean的方式？
 	(A)	<jsp:useBean id="address" class="AddressBean" />
 	(B)	<jsp:useBean name="address" class="AddressBean"/>
 	(C)	<jsp:useBean bean="address" class="AddressBean" />
 	(D)	<jsp:useBean beanName="address" class="AddressBean" />
 [参考答案：A]  分值：5


 11.	
 JSP 页面经过编译之后，将创建一个
 	(A)	applet
 	(B)	severlet
 	(C)	application
 	(D)	exe文件
 [参考答案：B]  分值：5


 12.	
 Servlet程序的入口点是： 。
 	(A)	service（）
 	(B)	init（）
 	(C)	main（）
 	(D)	doGet（）
 [参考答案：B]  分值：5

 
 13.	
 下面哪些服务器不能够部署EJB
 	(A)	Weblogic
 	(B)	Tomcat
 	(C)	Jboss
 	(D)	Glassfish
 [参考答案：B]  分值：5


 14.	
 下述选项中不属于JDBC基本功能的是
 	(A)	与数据库建立连接
 	(B)	处理查询结果
 	(C)	数据库维护管理
 	(D)	提交SQL语句
 [参考答案：C]  分值：5

 
 15.	
 以下关于JavaBeans的说法中，错误的是 。
 	(A)	JavaBeans是一种Java类。
 	(B)	JavaBeans是JSP的内置对象之一。
 	(C)	JavaBeans是一个可重复使用的软件组件。
 	(D)	JavaBeans是基于JAVA语言的。
 [参考答案：B]  分值：5


 16.	
 下面的描述错误的是
 	(A)	Statement的executeUpdate()方法会返回是否更新成功的boolean值
 	(B)	Statement的executeQuery()方法会返回一个结果集
 	(C)	使用ResultSet中的getString()可以获得一个对应于数据库中char类型的值
 	(D)	ResultSet中的next()方法会使结果集中的下一行成为当前行
 [参考答案：A]  分值：5


 17.	
 下列JSTL中迭代标签说法错误
 	(A)	JSTL中迭代标签有：<c:forEach>和<c:forToken>
 	(B)	<c:forEach>标签可以迭代固定次数
 	(C)	<c:forEach>标签可以在Collection中迭代
 	(D)	<c:forTokens>标签仅可以指定一个分隔符
 [参考答案：D]  分值：5


 18.	
 配置JSP运行环境，若WEB应用服务器选用TOMCAT，以下说法正确的是
 	(A)	不需安装JDK，安装TOMCAT就可以了
 	(B)	先安装JDK，再安装TOMCAT
 	(C)	JDK和TOMCAT只要都安装就可以了，安装顺序没关系
 	(D)	先安装TOMCAT，再安装JDK
 [参考答案：B]  分值：5

 19.	
 使用下面的Connection 的哪个方法可以建立一个PreparedStatement接口
 	(A)	createPreparedStatement()
 	(B)	createPrepareStatement()
 	(C)	prepareStatement()
 	(D)	preparedStatement()
 [参考答案：C]  分值：5

 20.	
 在Jdbc中可以调用数据库的存储过程的接口是
 	(A)	Statement
 	(B)	PreparedStatement
 	(C)	CallableStatement
 	(D)	PrepareStatement
 [参考答案：C]  分值：5

1.	
看下列jsp标签

<html>

  <head>

  </head>

  <body>

<%@ include file=”top.jsp” %>

…..

<jsp:include page=”copyright.jsp”/>

  </body>

</html>

下列那个说法是正确的

	(A)	在jsp被转换编译时，不将top.jsp包含进来
	(B)	只有copyright.jsp在请求该JSP资源时被包含进来
	(C)	在jsp被转换编译时，top.jsp和copyright.jsp都被包含进来
	(D)	在jsp被请求时，top.jsp被包含进来
[参考答案：B]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
2.	
下列哪些表达式所存储的对象可以被servlet或jsp在session里使用
	(A)	request.setAttribute(name，value);
	(B)	response.setAttribute(name，value);
	(C)	getSession().setAttribute(name，value);
	(D)	servlet.getServletContext().setAttribute(name，value);
[参考答案：D]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
3.	
向客户端发送一个pdf文档，设置文档类型时，下列那个语句是正确的
	(A)	response.setType(“application/bin”)
	(B)	response.setType(“application/pdf”)
	(C)	response.setContentType(“application/pdf”)
	(D)	response.setContentType(“application/bin”)
[参考答案：C]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
4.	
下列哪一个JSP标记是正确的 ？
	(A)	<jsp:include page=”notice.html” />
	(B)	<%@ include page=”notice.html” %><%@ include page=”notice.html” %>
	(C)	<jsp:include file=”notice.html” />
	(D)	<%! include file=”notice.html” %><%! include file=”notice.html” %>
[参考答案：A]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
5.	
下列关于Tomcat说法正确的是 。
	(A)	Tomcat是一种编程语言
	(B)	Tomcat是一种开发工具
	(C)	Tomcat是一种编程思想
	(D)	Tomcat是一个免费的开源的Serlvet容器
[参考答案：D]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
6.	
下列关于JSP编译指令说法错误的是
	(A)	编译指令所有的指令都在整个JSP页面内有效
	(B)	编译指令用来设置全局变量、声明类要实现的方法和输出内容的类型等
	(C)	编译指令向客户端产生任何输出
	(D)	编译指令用于从JSP发送一个信息到容器上
[参考答案：C]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
7.	
下面是web.xml中的片段
<context>

  <param-name>user</param-name>

  <param-value>test</param-name>

</context>                                                                       

在servlet中要得到上面的参数，下面哪个表达式是正确的

	(A)	getServletConfig().getInitParameter(“user”)
	(B)	getServletContext().getAttribute(“user”)
	(C)	getServletConfig().getAttribute(“user”)
	(D)	getServletContext().getInitParameter(“user”)
[参考答案：D]  分值：5
得分：
0
 分	系统自动批改于2021年1月11日 10点38分
 
8.	
下面那个表达式表示会话永不过期
	(A)	setMaxInactiveInterval(0)
	(B)	setTimeout(0)
	(C)	setTimeout(-1)
	(D)	setMaxInactiveInterval(-1)
[参考答案：D]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
9.	
下面关于page指令说法中错误的是
	(A)	一个JSP页面只能包含一个page指令
	(B)	除了import外，其他page指令定义的属性/值只能出现一次。
	(C)	page指令用来定义JSP页面中的全局属性
	(D)	language属性用来指示所使用的语言，“java”是当前唯一可用的JSP语言
[参考答案：A]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
10.	
下列说法中错误的是
	(A)	<!-- This file displays the user login screen -->会在客户端的HTML源代码中产生和上面一样的数据
	(B)	<%-- This comment will not be visible in the page source --%>会在客户端的HTML源代码中产生和上面一样的数据
	(C)	<%! int i = 0; %><%! int i = 0; %>是一个合法的变量声明
	(D)	表达式元素表示的是一个在脚本语言中被定义的表达式。
[参考答案：B]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
11.	
在jsp页面中导入java.util包，使用下列哪个标记
	(A)	<%@ page java =”java.util.*” %><%@ page java =”java.util.*” %>
	(B)	<%@ import =”java.util.*” @%><%@ import =”java.util.*” @%>
	(C)	<%@ import =”java.util.*” %><%@ import =”java.util.*” %>
	(D)	<%@ page import =”java.util.*” %><%@ page import =”java.util.*” %>
[参考答案：A]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
12.	
看下列HTML标签：

<html>

  <body>

      <a href=”/servlet/MyFirstServlet”>Make me say Hello World!</a

  </body>

<html>


点击超级链接后下列哪一个servlet方法将被调用： 。
	(A)	doGet
	(B)	doGET
	(C)	doPost
	(D)	doPOST
[参考答案：A]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
13.	
下列关于Tomcat个目录说法错误的是
	(A)	bin目录：包含启动/关闭脚本
	(B)	conf目录：包含不同的配置文件
	(C)	Lib目录：包含Tomcat使用的JAR文件
	(D)	work目录：包含web项目示例，当发布web应用时，默认情况下把web文件夹放于此目录下
[参考答案：D]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
14.	
在web.xml中元素表示从servlet的URL的映射关系，它有两个子元素分别表示servlet名称和与其对应的URL映射，下面哪一个子元素表示URL映射
	(A)	<mapping>
	(B)	<servlet-url>
	(C)	<url_mapping>
	(D)	<url_pattern>
[参考答案：D]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
15.	
下列哪个方法用于URL重写
	(A)	HttpServletResponse接口的rewriteURL()方法
	(B)	HttpServletRequest接口的encodeURL()方法
	(C)	HttpServlet接口的rewriteURL()方法
	(D)	HttpServletResponse接口的encodeURL()方法
[参考答案：D]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
16.	
下列那个jsp标记用于得到一个javabean的属性
	(A)	jsp:useBean
	(B)	jsp:useBean.property
	(C)	jsp: getProperty
	(D)	jsp:useBean.getProperty
[参考答案：C]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
17.	
下面哪个jsp隐含对象可以实现多次请求共享（选择两个答案）
	(A)	page
	(B)	request
	(C)	get
	(D)	applicatio
[参考答案：D]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
18.	
下列哪一个方法用于设置HttpServletResponse的内容类型？
	(A)	setParameter
	(B)	setAttribute
	(C)	doPost
	(D)	setContentType
[参考答案：D]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
19.	
在浏览器禁用cookie前提下，下列哪些技术能够记住客户端状态
	(A)	Http headers
	(B)	Httpsession
	(C)	URL重写
	(D)	隐藏域
[参考答案：C]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
20.	
关于FORM表单提交的HTTP的GET方法，下列哪个说法是错误的？
	(A)	不能向服务器提交二进制数据
	(B)	不能向服务器提交多值参数
	(C)	不能向服务器提交无限长度的数据
	(D)	参数附在URL后面
[参考答案：B]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点38分
 
 	
下列有关MVC设计模式正确的是
	(A)	M代表模型Model，V代表视图View，C代表控制器Controller。
	(B)	Servlet是MVC的“控制器Controller”的典型代表。
	(C)	MVC的“视图”，是代表用户与服务器进行交互的页面，即我们能看到和操作的页面。
	(D)	MVC的“模型”，负责所有业务流程／状态的处理以及业务规则的制定。
	(E)	采用MVC设计模式可以使软件的可维护性、可修复性、可扩展性、灵活性以及封装性大大提高。
[参考答案：ABCDE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
2.	
当我们要在JSP页面中使用自定义标记时需要
	(A)	在tld文件中定义标记
	(B)	引入这个标记的标记库，并指定前缀名
	(C)	在JSP页面中使用taglib指令
	(D)	在JSP页面中使用page指令
	(E)	创建一个标记处理器
[参考答案：ABCE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
3.	
下面的描述正确的是
	(A)	PreparedStatement继承自Statement
	(B)	ResultSet继承自Statement
	(C)	Statement继承自PreparedStatement
	(D)	CallableStatement继承自PreparedStatement
	(E)	以上说法均不正确。
[参考答案：AD]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
4.	
以下关于EJB3.0中的依赖注入技术叙述正确的是
	(A)	以上说法均不正确。
	(B)	为EJB组件指定依赖的资源可以使用@Resource注释来实现。
	(C)	在无状态会话Bean组件被创建后，Bean类可以通过@PostConstruct注册一个回调方法。
	(D)	
	(E)	EJB3.0规范引入了依赖注入来用于引用资源和EJB相关对象。
[参考答案：BCE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
5.	
下列说法正确的是
	(A)	Servlet充当MVC模式的模型
	(B)	JSP充当MVC模式的视图
	(C)	Servlet充当MVC模式的控制器
	(D)	JavaBean充当MVC模式的控制器
	(E)	EJB充当MVC模式的模型
[参考答案：BCE]  分值：5
得分：
0
 分	系统自动批改于2021年1月11日 10点58分
 
6.	
下列关于HTTP协议说法正确的是
	(A)	HTTP请求消息中Host表示初始URL中的主机和端口。
	(B)	HTTP请求消息中Accept表示浏览器可接受的MIME类型。
	(C)	HTTP请求消息中Accept-Encoding表示浏览器能够进行解码的数据编码方式。
	(D)	HTTP是一种请求/响应式的协议。
	(E)	HTTP请求消息中Accept-Language表示浏览器所希望的语言种类。
[参考答案：ABCDE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
7.	
下列关于ResultSet接口的说法哪些是正确的？
	(A)	ResultSet接口被用来提供访问查询结果的数据表，查询结果被当作ResultSet对象而返回。
	(B)	ResultSet 的next() 方法用来移动指针到数据表的下一行，如果到达表尾，next() 方法返回假的布尔值-false，否则为真。
	(C)	ResultSet对象提供“指针”，指针每次访问数据库表的一行。
	(D)	以上说法都不对
	(E)	ResultSet接口提供大量的获得数据的方法，这些方法返回数据表中任意位置的数据。
[参考答案：ABCE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
8.	
以下选项是EJB3.0中的注释，其中用于会话Bean的生命周期的是_______
	(A)	@Move
	(B)	@Init
	(C)	@PrePassivate
	(D)	@PostDestroy
	(E)	@PostConstruct
[参考答案：BCE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
9.	
实体Bean是由什么组成的
	(A)	EntityManager对象。
	(B)	persistence.xml的简单的XML部署描述文件。
	(C)	纯粹的Java对象（POJO）。
	(D)	实体
	(E)	以上都是。
[参考答案：BD]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
10.	
以下关于传统EJB与新一代EJB之间的区别叙述正确的是
	(A)	新一代EJB3.0最大的改变是使用了Java元数据注释，大大的简化了EJB组件的开发过程。
	(B)	以上说法均不正确。
	(C)	传统EJB中需要部署描述符、厂商专有文件、Ejb-jar文件等等，部署复杂，而新一代EJB3.0已经不需要部署描述符了。
	(D)	新一代EJB3.0简化了EJB组件的开发过程，改变了EJB的编程模型，为了适宜EJB3.0，EJB技术也随之改变。
	(E)	新一代EJB3.0同传统EJB相比较，不再需要Home接口和对象接口了。
[参考答案：AE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
11.	
下列关于CallableStatement对象的说法哪些是正确的？
	(A)	在数据库调用过程中，可以通过设置IN参数向调用的存储过程提供执行所需的参数。
	(B)	CallableStatement 对象用于执行对数据库所有的调用。
	(C)	CallableStatement 对象用于执行对数据库已存储过程的调用。
	(D)	在存储过程的调用中，通过OUT参数获取存储过程的执行结果。
	(E)	CallableStatement对象中，有一个通用的成员方法call，这个方法用于以名称的方式调用数据库中的存储过程。
[参考答案：ACDE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
12.	
下面关于Tag接口说法中正确的是
	(A)	setPageContext()方法为初始化方法
	(B)	release()方法用来释放标签程序占用的任何资源
	(C)	setParent()方法用来设置标记的上一级标记
	(D)	doEndTag方法，EVAL_PAGE或者SKIP_PAGE，当返回值为EVAL_PAGE，jsp容器将继续执行jsp页面的内容，否则不执行
	(E)	doStartTag()方法返回EVAL_BODY_INCLUDE和SKIP_BODY，返回EVAL_BODY_INCLUDE计算正文内容，返回SKIP_BODY不计算body。
[参考答案：ABCDE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
13.	
以下几个术语正确对应的是
	(A)	EJB—企业Java Bean
	(B)	IDE—集成开发环境
	(C)	DI—依赖注入
	(D)	MDB—消息驱动Bean
	(E)	ISV—独立软件开发商
[参考答案：ABCDE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
14.	
关于ResultSetMetaData 类的方法有哪些？
	(A)	以上说法都不对
	(B)	int getColumnDisplaySize() throws SQLException
	(C)	String getColumnName(int column) throws SQLException
	(D)	int getColumnCount() throws SQLException
	(E)	String getColumnTypeName(int column) throws SQLException
[参考答案：BCDE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
15.	
实体Bean与Session Bean有什么区别______
	(A)	对象引用不同，实体存在客户可见的、持久化身份（主键）。
	(B)	实体存在持久化、客户可见的状态。
	(C)	实体的生命周期可能与应用本身的生命周期无关。
	(D)	以上说法都不正确。
	(E)	不能够直接通过远程访问到实体。
[参考答案：ABCE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
16.	
B组件有几种不同的类型_______
	(A)	会话Bean
	(B)	Java Bean
	(C)	实体Bean
	(D)	消息驱动Bean
	(E)	企业Bean
[参考答案：ACD]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
17.	
关于JNDI 和名字空间下列说法正确的是？
	(A)	以上说法都不对
	(B)	Java命名及目录接口作为 J2EE框架中的一种核心技术。
	(C)	JNDI可以应用于EJB、RMI和JDBC等。它是网络上查找事务的标准方法。
	(D)	Java命名和目录接口(Java Naming and Directory Interface)。
	(E)	JNDI是一个通用接口。
[参考答案：BCD]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
18.	
关于DriverManager 类下列哪些程序段是正确的？
	(A)	以上说法都不对
	(B)	DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
	(C)	Class.forName(new org.gjt.mm.mysql.Driver());
	(D)	DriverManager.registerDriver("org.gjt.mm.mysql.Driver");
	(E)	Class.forName("org.gjt.mm.mysql.Driver");
[参考答案：BE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
19.	
下列说法中正确的是
	(A)	必须在使用自定义标记之前使用<%@ taglib %>指令
	(B)	include指令通知容器在当前位置将指定位置上的资源内容包含进来
	(C)	include指令只允许包含动态页面
	(D)	Taglib指令允许页面使用自定义标记
	(E)	include指令中file属性指定要包含的文件名
[参考答案：ABDE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分
 
20.	
下面关于pageContext对象说法中正确的是
	(A)	pageContext对象为JSP页面包装页面的上下文。
	(B)	pageContext对象创建和初始化都是由容器来完成的
	(C)	getRequest()方法返回当前的request对象
	(D)	getSession()方法返回当前页面的session对象。
	(E)	removeAttribute()方法用来删除默认页面范围或特定范围之中的已命名对象。
[参考答案：ABCDE]  分值：5
得分：
5
 分	系统自动批改于2021年1月11日 10点58分

 1.	
 如果数据库中某个字段为numberic型，可以通过结果集中的哪个方法获取
 	(A)	getNumberic()
 	(B)	getDouble()
 	(C)	setNumberic()
 	(D)	setDouble()
 [参考答案：B]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 2.	
 在JDBC中使用事务，想要回滚事务的方法是
 	(A)	Connection的commit()
 	(B)	Connection的setAutoCommit()
 	(C)	Connection的rollback()
 	(D)	Connection的close()
 [参考答案：C]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 3.	
 下述选项中不属于JDBC基本功能的是
 	(A)	提交SQL语句
 	(B)	与数据库建立连接
 	(C)	处理查询结果
 	(D)	数据库维护管理
 [参考答案：D]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 4.	
 假设在helloapp应用中有一个Javabean文件Hello，它位于com.bean包下，那么这个bean的class文件应该放在什么目录下
 	(A)	helloapp/
 	(B)	helloapp/WEB-INF/
 	(C)	helloapp/WEB-INF/classes/com/bean/
 	(D)	helloapp/WEB-INF/classes/
 [参考答案：C]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 5.	
 下面的描述错误的是
 	(A)	Statement的executeQuery()方法会返回一个结果集
 	(B)	ResultSet中的next()方法会使结果集中的下一行成为当前行
 	(C)	Statement的executeUpdate()方法会返回是否更新成功的boolean值
 	(D)	使用ResultSet中的getString()可以获得一个对应于数据库中char类型的值
 [参考答案：C]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 6.	
 下列JSTL中迭代标签说法错误
 	(A)	<c:forEach>标签可以在Collection中迭代
 	(B)	<c:forTokens>标签仅可以指定一个分隔符
 	(C)	JSTL中迭代标签有：<c:forEach>和<c:forToken>
 	(D)	<c:forEach>标签可以迭代固定次数
 [参考答案：B]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 7.	
 Servlet程序的入口点是： 。
 	(A)	service（）
 	(B)	main（）
 	(C)	init（）
 	(D)	doGet（）
 [参考答案：C]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 8.	
 下面哪一个是正确使用JavaBean的方式？
 	(A)	<jsp:useBean beanName="address" class="AddressBean" />
 	(B)	<jsp:useBean id="address" class="AddressBean" />
 	(C)	<jsp:useBean bean="address" class="AddressBean" />
 	(D)	<jsp:useBean name="address" class="AddressBean"/>
 [参考答案：B]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 9.	
 下面哪些服务器不能够部署EJB
 	(A)	Weblogic
 	(B)	Jboss
 	(C)	Tomcat
 	(D)	Glassfish
 [参考答案：C]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 10.	
 JSP 页面经过编译之后，将创建一个
 	(A)	applet
 	(B)	severlet
 	(C)	exe文件
 	(D)	application
 [参考答案：B]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 11.	
 在Jdbc中可以调用数据库的存储过程的接口是
 	(A)	CallableStatement
 	(B)	PreparedStatement
 	(C)	Statement
 	(D)	PrepareStatement
 [参考答案：A]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 12.	
 下列说法中错误的是
 	(A)	<jsp:plugin>元素用于在浏览器中播放或显示一个对象
 	(B)	以上说法全不对
 	(C)	Jsp:param操作被用来以“名——值”对的形式为其它标签提供附加信息
 	(D)	当Jsp文件被编译，送往浏览器时，<jsp:plugin>元素将会根据浏览器的版本替换成<object>或者<embed>元素。
 [参考答案：B]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 13.	
 假设在helloapp应用中有一个hello.jsp，它的文件路径如下：%JAVA_HOME%/webapps/helloapp/hello/hello.jsp，那么在浏览器端访问hello.jsp的URL是什么
 	(A)	http://localhost:8080/webapps/helloapp/hello/dello.jsp
 	(B)	http://localhost:8080/helloapp/hello/hello.jsp
 	(C)	http://localhost:8080/helloapp/hello.jsp
 	(D)	http://localhost:8080/hello.jsp
 [参考答案：B]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 14.	
 下面对out对象说法错误的是
 	(A)	out.newLine()方法用来输出一个换行符
 	(B)	out对象的范围是application。
 	(C)	out对象用于输出数据
 	(D)	如果page指令选择了autoflush="true"，那么当出现由于当前的操作不清空缓存而造成缓冲区溢出的情况时，这个类的所有I/O操作会自动清空缓冲区的内容。
 [参考答案：B]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 15.	
 page指令通过 属性指定当前页面的内容类型和字符集。
 	(A)	contentType
 	(B)	buffer
 	(C)	language
 	(D)	isErrorPage
 [参考答案：A]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 16.	
 jsp:forward和sendRedirect都是用来做页面跳转的，描述错误的是？
 	(A)	forward之后可以使用原来的request对象，而且效率较高。
 	(B)	sendRedirect之后不可以使用原来的request对象，而且效率较低。
 	(C)	forward地址栏不变化，只能在Web应用程序内的页面间跳转。
 	(D)	forward地址栏变化，可以跳转到任何页面和机器。
 [参考答案：D]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 17.	
 对于“<%!”和“%><%!”和“%>”之间声明的变量，以下说法正确的是：
 	(A)	多个用户同时访问该页面时，任何一个用户对这些变量的操作，都会影响到其他用户
 	(B)	不是JSP页面的成员变量
 	(C)	多个用户同时访问该页面时，每个用户对这些变量的操作都是互相独立的，不会互相影响
 	(D)	是JSP页面的局部变量
 [参考答案：A]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 18.	
 使用下面的Connection 的哪个方法可以建立一个PreparedStatement接口
 	(A)	createPreparedStatement()
 	(B)	preparedStatement()
 	(C)	createPrepareStatement()
 	(D)	prepareStatement()
 [参考答案：D]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 19.	
 配置JSP运行环境，若WEB应用服务器选用TOMCAT，以下说法正确的是
 	(A)	先安装JDK，再安装TOMCAT
 	(B)	先安装TOMCAT，再安装JDK
 	(C)	不需安装JDK，安装TOMCAT就可以了
 	(D)	JDK和TOMCAT只要都安装就可以了，安装顺序没关系
 [参考答案：A]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  
 20.	
 以下关于JavaBeans的说法中，错误的是 。
 	(A)	JavaBeans是一个可重复使用的软件组件。
 	(B)	JavaBeans是一种Java类。
 	(C)	JavaBeans是JSP的内置对象之一。
 	(D)	JavaBeans是基于JAVA语言的。
 [参考答案：C]  分值：5
 得分：
 5
  分	系统自动批改于2021年1月11日 14点29分
  



