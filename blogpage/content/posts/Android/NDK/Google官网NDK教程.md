## 资料
* [android NDK工具常用汇总.md](../android NDK工具常用汇总.md)
* [Google官方NDK 教程](https://developer.android.com/ndk/guides?hl=zh-cn)
* [android NDK原生支持API](https://developer.android.com/ndk/guides/stable_apis?hl=zh-cn)
* [cmake](https://cmake.org/)
* [ndk-build 脚本](https://developer.android.com/ndk/guides/ndk-build?hl=zh-cn)
* [添加C和C++代码](https://developer.android.com/studio/projects/install-ndk?hl=zh-cn)
* [ndk示例代码](https://github.com/android/ndk-samples)
* [ndk-build 示例代码](https://github.com/android/ndk-samples/tree/android-mk)
* [android 开源项目](https://source.android.com/?hl=zh-cn)

原生开发套件(NDK)是一套工具，使得你能够在Android应用中使用C和C++代码，并提供众多平台库。可以使用这些平台库管理原生activity和访问实体设备组件。

ndk:

* 进一步提升设备性能，以降低延迟或运行游戏或物理模拟等计算密集型应用。
* 重复使用你自己或其他开发者的C或者C++库。

JAVA代码通过JAVA原生接口JNI框架调用原生库中的函数。AS编译原生库默认构建工具是CMake,由于很多现有羡慕都使用了NDK-build 构建工具包，因此AS也支持ndk-build，如果要创建新的原生库则应使用Cmake。

# 正文

## 常用的NDK 编译

### Cmake
as创建的native 项目默认的就是这种编译方式，但是大多数项目都是ndk-build。
我们通过as 创建一个简单的项目。发现c代码写在了main 下的cpp 目录中。
同时同时创建了一个Cmakelists.txt 的配置文件，单纯。在app 的build.gradle 中
````java
externalNativeBuild {
        cmake {
            path file('src/main/cpp/CMakeLists.txt')
            version '3.10.2'
        }
    }
````
上面代码 指向了cmakeLists.txt的地址，和ndk版本号。
````java
 externalNativeBuild {
            cmake {
                cppFlags ''
            }
        }
````
这个调调是配置其他内容的。
### ndk-build

> 大多数项目都是ndk-bulid 编译的。所以要看到ndk-build 尤其重要。问题是这个调调如何运行编译啊？

不推荐使用 ndk-build 来构建，因为这样构建源码后，是无法使用方法跳转、方法提示等功能的！如果要改代码，就等于文本编辑器写代码。相反 CMake 是支持这些的，因此更有助于提高开发效率。所以这里就不详细说明 ndk-build 的使用步骤了，如果是新建项目就使用 CMake，如果是使用 ndk-build 的老项目，可以按照以下步骤转为 CMake。

ndk-build脚本使用ndk的基于make的构建系统构建项目。我们针对ndk-build使用android.mk和application.mk配置。

#### 编译

* 通过 cmd切换到包含jni的目录
* 执行ndk的路径/ndk-build。如>F:\SDK\Android\Sdk\ndk-bundle\ndk-build，就会生成so 文件。

> 所以这个调调。得用其他编辑器编写啊。

#### android.mk

android.mk 文件位于jni/目录的子目录中，用于向构建系统描述源文件和共享库。他实际上是一个微小的GNU makefile 片段。构建系统会将其解析一次或者多次，Android.mk 文件用于定义application.mk、构建系统和环境变量所未定义的项目级设置。他还可以替换特定模块的项目级设置。

Android.mk 的语法支持将源文件分组为‘模块’，模块是静态库、共享库或者独立的可执行文件。可以在每个Android.mk文件中定义一个或者多个模块，也可以在多个模块中使用同一个源文件，构建系统只将共享库放入您的应用软件包，静态库可以生成共享文件。

除了封装库之外，构建系统还可以处理各种其他事项，如无需再android.mk文件中列出头文件或生成的文件之间的显示依赖关系，ndk构建系统会自动计算这些关系。

这个文件的语法随着整改Android 开发项目分发的android.mk 文件中的语法非常接近。

* LOCAL_PATH:=$(call -my-dir)：android.mk 文件必须定义的local_path变量，此变量表示源文件再开发树中的位置，在构建系统提供的宏函数my-dir 将返回当前目录(android.mk文件本身所在的目录)的路径。

* include $(clear_vars) : clear_vars 变量指向一个特殊的GUN makeFile,后者会为你清除许多LOCAL_XXX变量，例如LOCAL_MODULE、LOCAL_SRC_FILES和LOCAL_STATIC_LIBRARIES，GUN makefile 不会清除LOCAL_PATH，此变量需要保留其值，因为系统在单一GUN make 执行上下文中解析所有的构建控制文件，在描述每个模块之前，必须生命LOCAL_PATH变量。

* LOCAL_MODULE:=hello-jni ，local_module 变量存储你要构建的模块的名称，每个模块都需要使用一次这个变量，每个模块的名称必须唯一，且不含任何空格，构建细绒在生成最终共享库文件时候，会对设置的local_module 的名称自动添加正确的前置和后缀例如上述生成的就是:libhello-jni.so 的库。

  > 如果已经是lib 开头的，构建系统将不会额外添加前缀。这个调调是兼容Android 平台源文件根据 android.mk 生成的库文件，这些库名称都是以lib开头。

* LOCAL_SRC_FILES ：=hello-jni.c。列举源文件，以空格分隔多个文件。local_src_files 变量必须包含要构建到模块中的C或者C++源文件列表。

* include $(BUILD_SHARED_LIBRARY)  BULID_SHAED_LIBRY变量指向一个GUN makeFile 脚本，该脚本会收集最近依赖 include 一开在local_XXX 变量定义的所有信息，此脚本确定要构建的内容以及构建方式。

* 构建系统提供了许多可以在Android.mk文件中使用的变量，其中许多变量已预先赋值，另外一些变量需要手动赋值，除了这些变量以外，还可以定义任意变量，如果需要定义变量名称(my_)。建议ndk构建细绒保留了下列的变量名称：

  * 以Local_开头的名称
  * 以PRIVATE,NDK 或者APP开头的名称，构建系统在内部使用这些变量名。
  * 小写名称，例如my-dir，构建系统也是在内部使用这些名称。

* CLEAR_CARS：此变量取消定义下文开发者定义的变量，

  

#### application.mk
### 独立工具链

