> 这个主要是整task 相关
## 资料
* [gradle TASK](https://docs.gradle.org/current/dsl/org.gradle.api.Task.html#org.gradle.api.Task)
# 正文
## 基础
### 在build.gradle创建使用task
创建一个task
```aidl
task mytask{

}
```
action 含义：默认的task 有两个action。
* doFirst: task 执行时最新执行的操作。
* doLast: task 执行时 最后执行的操作。
    > 这个有几个等价操作。比如task.leftShift和task<<{}

创建task 的参数介绍：

在 Gradle 中定义 Task 的时候，可以指定更多的参数，如下所示：

| 参数名          | 含义                       | 默认值                             |
| --------------- | -------------------------- | ---------------------------------- |
| name            | task的名字                 | 必须指定，不能为空                 |
| type            | task的父类                 | 默认值为org.gradle.api.DefaultTask |
| overwrite       | 是否替换已经存在的同名task | false                              |
| group           | task所属的分组名           | null                               |
| description     | task的描述                 | null                               |
| dependsOn       | task依赖的task集合         | 无                                 |
| constructorArgs | 构造函数参数               | 无                                 |

## 自定义task 

通常而言，我们会写一个类去实现task。或者通过创建task 去执行某个类。

| 注解名             | 属性类型                    | 描述                     |
| ------------------ | --------------------------- | ------------------------ |
| @Input             | 任意Serializable类型        | 一个简单的输入值         |
| @InputFile         | File                        | 一个输入文件，不是目录   |
| @InputDirectory    | File                        | 一个输入目录，不是文件   |
| @InputFiles        | Iterable                    | File列表，包含文件和目录 |
| @OutputFile        | File                        | 一个输出文件，不是目录   |
| @OutputDirectory   | File                        | 一个输出目录，不是文件   |
| @OutputFiles       | Map<String, File>或Iterable | 输出文件列表             |
| @OutputDirectories | Map<String, File>或Iterable | 输出目录列表             |

@TaskAction 作为这个task 执行函数的标记。

> dependsOn 用于标记在什么任务后执行。如果是在多个任务后都执行的话。传递数组。数组执行顺序 在没有指定依赖顺序的时候是按照名称顺序去执行的。
> 因为task DefaultTask ,DefaultTask是 AbstractTask的子类 所以task 中很多方法 也可以直接使用 比如 taskDemo.mustRunAfter(taskDemo2) 也可以指定依赖关系。
> task.finalizedBy task 用于指定某个任务执行完成后 再执行什么任务。  
> 因为task 包含优化，当输入输出没有发生变更的时候，将不会执行task,所以可以对输入输出进行设置为每次都执行。如：outputs.upToDateWhen{false}
