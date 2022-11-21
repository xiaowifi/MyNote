properties 文件与map集合相同，不允许key的重复。根据key的hashCode存储。插入顺序并不是记录的顺序。

````aidl
Properties pps = new Properties();
        try {
            pps.load(new FileInputStream("local.properties"));
            Enumeration fileName = pps.propertyNames();
            while (fileName.hasMoreElements()) {
                String strKey = (String) fileName.nextElement();
                String strValue = pps.getProperty(strKey);
                System.out.println(strKey + "," + strValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
````
如果可以获取到Android 插件对象的话，这个也可以。
````aidl
 /*  AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        System.out.println(appExtension.getSdkDirectory().getAbsolutePath());*/
````