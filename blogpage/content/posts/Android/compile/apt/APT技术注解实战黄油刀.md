
# 前言
> APT即为Annotation Processing Tool，它是javac的一个工具，中文意思为编译时注解处理器。APT可以用来在编译时扫描和处理注解。通过APT可以获取到注解和被注解对象的相关信息，在拿到这些信息后我们可以根据需求来自动的生成一些代码，省去了手动编写。
> 注解 APT技术，就是编译时技术，通过注解的方式，在编译的时候生成对应的想要的模板代码，用于便于开发。划重点(只能创建模板类，创建)
>或者在某些情景中限制用户输入。增加框架的兼容性。
> [java 8中移出apt相关](http://openjdk.java.net/jeps/117)
>  新版中使用 插件化注解处理(Pluggable Annotation Processing)APIJSR 269 
## 资料
* [auto.service apt服务](https://github.com/google/auto/tree/master/service)
* [javapoet java文件生成帮助类](https://github.com/square/javapoet)
* [javapoet详解](https://blog.csdn.net/qq_34681580/article/details/121483450)
# 正文
## 创建对应的module
我们知道注解是包含位置和生命周期的。而在注解处理器的开发过程中，需要申明当前注解处理器需要处理什么注解。
为了在注解处理器和代码调用上均可访问到相同的注解处理器。我们需要创建几个module，注解处理器的module 应该是一个JAVA lib 工程 
### 注解module
这个module 很单纯，主要是提供注解。所以这个只需要是是一个JAVA lib 工程即可。
### 注解处理器的module
注册一个注解处理器，然后通过注解去生成代码。所以这个也只是一个JAVA lib 工程。
#### 导包
````java
    annotationProcessor 'com.google.auto.service:auto-service:1.0-rc4'
    compileOnly 'com.google.auto.service:auto-service:1.0-rc4'
        implementation "com.squareup:javapoet:1.13.0"
````
#### 日志打印
因为他是处于编译时，所以Android 的log是无法使用的，如何打印呢？
````java
        System.out.println("System init ");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"init");
````
#### 设置需要处理的注解 
```java
@Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set=new HashSet<>();
        set.add(BindView.class.getCanonicalName());
        return set;
    }
```
#### 其他设置
* 初始化：init
* 设置支持版本：
````java
   @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

````
#### 完整代码
````kotlin

@AutoService(Processor.class)
public class ButterKnifeAnnotation extends AbstractProcessor {
    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.out.println("System init ");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "init");
        filer = processingEnv.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(BindView.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //todo 处理逻辑，并且生成一个文件
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(BindView.class);
        Map<String, List<VariableElement>> map = new HashMap<>();
        //把一个class中的节点整到一个列表中。
        for (Element element : elementsAnnotatedWith) {
            VariableElement variableElement = (VariableElement) element;
            String activityName = variableElement.getEnclosingElement().getSimpleName().toString();
            //Class<? extends Element> aClass = variableElement.getEnclosingElement().getClass();
            if (!map.containsKey(activityName)){
                map.put(activityName,new ArrayList<VariableElement>());
            }
            map.get(activityName).add(variableElement);
        }
        if (map.size()<=0){
            return false;
        }
        // 说明有注解使用。
        Writer writer=null;
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            // 获取到activity的名称
            String activityName = iterator.next();
            // 获取到当前class的所有标记节点
            List<VariableElement> variableElements = map.get(activityName);
            TypeElement enclosingElement = (TypeElement) variableElements.get(0).getEnclosingElement();
            // 获取到当前class的包名
            String packageName = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
            // 开始写文件
            try {
                JavaFileObject sourceFile=filer.createSourceFile(packageName+"."+activityName+"_viewBinding");
                writer=sourceFile.openWriter();
                writer.write("package "+packageName+";\n");
                writer.write("import com.nuoye.butterknife.IBinder;\n");
                writer.write("public class "+activityName+"_viewBinding implements IBinder<"+packageName+"."+activityName+">{\n");
                writer.write("@Override\n");
                writer.write("public void bind("+packageName+"."+activityName+" target){\n");
                // 处理函数的内容。
                for (VariableElement variableElement: variableElements){
                    String variableName = variableElement.getSimpleName().toString();
                    int id= variableElement.getAnnotation(BindView.class).value();
                    TypeMirror typeMirror = variableElement.asType();
                    writer.write("target."+variableName+"=("+typeMirror+")target.findViewById("+id+");\n");
                }
                writer.write("     }");
                writer.write("}");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                if (writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
````
#### javapoet 写法 
> //todo 这个调调太复杂了，放弃了。OJBK 
### 导包
````java
        implementation project(path: ':ButterKnife')
        annotationProcessor project(path: ':ButterKnifeAnnotation')
````
## 结束
老是遇到一个问题，处理器的注释不打印。网络上说处理器的module应该是JAVA lib,但是我创建的时候就是java lib。我以为和gradle 和build tools 版本号相关。
但是我重新创建了一个工程他又行了，人都麻了。需要注意的一点是，需要JAVA 版本经量保持统一。


