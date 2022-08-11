## 资料
# 正文



## 常用工具

主要是对于GCC提供的工具中常用工具进行描述。

#### 利用readeif命令输出动态so 库中的所有函数

````
./arm-linux-androideabi-readelf -a libMemoryleak.so > func.txt
````

上述代码的意思是，将libMenoryLeak.so 文件中的函数输出到func.txt 中。如果运行Android设备上的应用报错说找不到这个JNI方法，就可以使用这行命令将so 库中的方法全部导出来，然后搜索对应的JNI 方法。这里输入的so 包一定要说obj目录下的so 库，而不应该是libs目录下的so 库。

#### 利用objdump 命令将so包反编译为实际的代码

````
./arm-linux-androideabi-objdump -x libMemoryLeak.so >stacktrace.txt
````

#### 利用nm指令查看静态库中的符号表

```
./arm-linux-androideabi-nm libaudio.a >symbol.file
```

#### 利用g++指令编译程序

````
SYS_ROOT=$NDKROOT/platforms/android-21/arch-arm/arm-linux-androideabi-g++ -02-DNDEBUG --sysroot=$SYS_ROOT -0 libMemoryLeak.so jni/text_memory.cpp -lm -lstdc++
````

无论是a的静态库还是so的动态库，甚至是可执行的命令行工具，都可以使用g++编译，而常使用的nkd-build命令实际上是对android.mk 以及g++的一种封装。这种封装将g++的细节封装起来，让开发者更加方便。

#### 利用aadr2line将调用地址转换成代码行数

```
./arm-linux-androideabi-addr2line -e libMemoryLeak.so oxcf9c > file.line
```

#### 利用ndk-stack还原堆栈信息

````
ndk-stack -sym libMemoryLeak.so -dump tombstone_01 >log.txt
````

当程序出现Native层的Crash时，系统会拦截并将crash的堆栈信息放到/data/tomb-stones 目录下，存储成为一个文件，系统会自动循环覆盖，并且只会保留最近的10个文件，如何将这里面的信息转换为实际上的代码文件可以使用ndk-stack 工具，这个工具必须和ndk-build在同一个目录。
