> svg 可以转为 path 坐标。
* 定义一个输入流 加载 svg 文件，不能是转为xml的svg。inputStream
* 定义一个path 存储对象。path,颜色等需要存储的对象。
* DocumentBuilderFactory 获取DocumentBuilder 对象
* 通过DocumentBuilder获取到 document 
* document.getDocumentElement 获取所有节点。
* PathParser.createPathFromPathData() 将string类型的path 转化为path 对象。
* path.computeBounds()// 获取路径上的边界。
* region 可以设置path，然后通过 region.contains 判断点击位置是否在区域内部。












