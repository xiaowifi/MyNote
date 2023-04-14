## 前言
AIDL是binder的延伸，毕竟binder是一种IPC(跨进程通信的机制)，通常而言，单进程APP是很少有AIDL的需求的。但是Android系统中存在很多系统功能就是通过binder和AIDL进行实现的，比如粘贴板。
学习AIDL也便于我们去实现某些业务诉求上的跨进程通信。
# 正文
学习AIDL需要知道下面几个类：
* iBinder
* IInterface
* Binder
* proxy
* stub

而基于as创建AIDL文件的时候，会提示minSdkVersion 需要大于16.
和常用的通信机制一致，我们将发送请求的称为客户端。处理并反馈信息的为服务端。通常而言，信息交互往往是由客户端发起的，如果两端都需要互相发送信息还是走socket更为合适。
IPC机制主要是跨进程通信，两个不同的APP也属于两个不同的进程，一个APP开启两个不同的进程，也叫跨进程。
在APP 优化的过程中，某些业务诉求拆分到独立的进程中往往更好。比如说，webView的那个界面就可以在其他进程中，比如说，直播的播放，视频的播放等等。
反而，跨APP之前的便和具体业务挂钩的比较强烈，但是因为跨平台的特性，这么反而不是那么常见。当然一些底层的解决方案倒是会出现跨进程通信，比如说推送，都是一个系列的，那么推送成功率会更高，比如说崩溃统计啥的。

## AIDL 的使用教程
比如说，我们需要实现跨两个APP，一个APP找另外一APP拿一个用户列表，和计算一个东西，一个APP作为服务端，提供服务列表和负责计算。
### AIDL
#### IAdd.aidl 文件内容。
````aidl
interface IAdd {
   
    int addNumbers(int num1, int num2);
    List<String> getStringList();
    List<Person> getPersonList();
    void placeCall(String number);
}
````
#### Person.aidl 文件
因为我们需要提供一个自定义数据模型过去，而binder支持的数据模型里面对于自定义的数据模型需要实现 Parcelable
````aidl
package com.chintan.aidlserver;
parcelable Person;

````
#### Person.java
需要注意package的一致性。
````aidl
package com.chintan.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;
public class Person implements Parcelable {
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(age);
    }
}

````

### 服务端
因为需要调用AIDL，所以AIDL使用的相关文件在两个端口都是需要的，所以IAdd.aidl，Person.aidl，Person.java在两个APP中都必须存在且一致。
#### 绑定服务service
我们创建一个类AdditionService继承于service。
````aidl
public class AdditionService extends Service {

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private final IAdd.Stub mBinder = new IAdd.Stub() {
		@Override
		public int addNumbers(int num1, int num2) throws RemoteException {
			return num1 + num2;
		}

		@Override
		public List<String> getStringList() throws RemoteException {
			return MainActivity.getList();
		}

		@Override
		public List<Person> getPersonList() throws RemoteException {
			return MainActivity.getPersons();
		}

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		@Override
		public void placeCall(final String number) throws RemoteException {
			Log.e("TAG", "placeCall: "+number );
		}
	};
}
````
通过上面的代码，我们可以知道，通过IAdd.aidl文件，编译器会给我们生成一些列的IAdd.java 文件。而IBinder就是通过IAdd.aidl生成的模板文件生成的。同时我们需要实现接口的函数。
然后是将service注册到清单文件中：
````aidl
 <service
            android:name=".AdditionService"
            android:enabled="true"
            android:exported="true"
            android:process=":remote">
            <intent-filter>
                <action android:name="service.calc" />
            </intent-filter>
        </service>
````
既然是和service 绑定的，那么和service的使用便没有区别了。
### 客户端
因为需要调用AIDL，所以AIDL使用的相关文件在两个端口都是需要的，所以IAdd.aidl，Person.aidl，Person.java在两个APP中都必须存在且一致。
我们服务端是通过清单文件注册到service，那么我们向服务端发起请求就非常明确了。当然需要判断服务器这个apk 是否安装了,同时需要知道action的名称。
因为我们是两个APP，所以服务APP可能没有运行。
#### 首先定义ServiceConnection
````aidl
IAdd addService;
private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			Log.d(Tag, "Service Connected");
			addService = IAdd.Stub.asInterface((IBinder) iBinder);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			Log.d(Tag, "Service Disconnected");
			addService = null;
		}
	};
````
#### 绑定服务
`````aidl
		if (addService == null) {
			Intent intent = new Intent(IAdd.class.getName());
			intent.setAction("service.calc");
			intent.setPackage("com.chintan.aidlserver");
			bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
		}
`````
#### 基于业务诉求调用函数
````aidl
		addService.addNumbers(1,1);
		addService.getPersonList();
		addService.getStringList();
		addService.placeCall("123456789");
````
#### 取消绑定 
````aidl
		unbindService(serviceConnection);
```` 
## AIDL 解析
整体而言，AIDL是对于binder机制下的service的一种更加简化的写法。主要还是对于service的使用。

