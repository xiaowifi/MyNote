
## 正文
> 话说，Java 反射还是蛮重要的，但是感觉这个调调破坏了单例模型。但是用着还是很香的，我记得有大佬写过博客说，不建议使用反射来着，找不到了。以后找到了贴出来。
> 先说用反射图啥，比如说，我只想知道一个类的变量啊。或者我只有类的名称想要变量啊，或者是我想直接调用方法啊，等等。
### 利用反射创建fragment
> 话说，这个可能是Android最常见的反射使用了。使用方式: Class.forName(className).newInstance();
````aidl
private void showBottomFragment() {
        String className = ApprovalDetailsBottomFragment.class.getName();
        try {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //隐藏所有的那个啥。
            for (Fragment fra : fragmentManager.getFragments()) {
                fragmentTransaction.hide(fra);
            }
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(className);
            if (fragmentByTag != null) {
                fragmentTransaction.show(fragmentByTag);
            } else {
                bottomFragment = (ApprovalDetailsBottomFragment) Class.forName(className).newInstance();
                bottomFragment.setState(approval.getState());
                bottomFragment.setApproval_json(approval.getApproval_json());
                bottomFragment.setItemClick(this);
                fragmentTransaction.add(R.id.frame_bottom, bottomFragment, className);
            }
            //添加到返回栈中
            //  fragmentTransaction.addToBackStack(className);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
````

### 常见对象创建
````aidl
Class.forName(className).newInstance();//classname 应该是包含包名的。
````
### 通过反射获取对象字段
> 这里有一个问题，反射获取到的无法获取到父类的东西，所以需要遍历获取就好。
```
//这个方法可以解决获取不到父类的情况
private static Field[] getAllFieldsByObject(Object object){
    Class clazz = object.getClass();
    List<Field> fieldList = new ArrayList<>();
    while (clazz != null){
        fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
        clazz = clazz.getSuperclass();
    }
    Field[] fields = new Field[fieldList.size()];
    fieldList.toArray(fields);
    return fields;
}

		/**
     * 获取对象中所有可用字段。
     * @param clazz
     * @return
     */
    private static Field[] getAllFieldsByClass(Class clazz){
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
```
然后遍历获取到的Field数组，就可以获取到字段了。
获取到字段就可以涉及到map和对象转换等等了。
## 反射中的type接口
> 这个主要是设计模式上的，或者是服务器返回数据类型。比如说Android上的activity设计成MVP,MVVM，baseactivity可能就需要写成这个样子。
> [参考资料](https://blog.csdn.net/lkforce/article/details/82466893?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&dist_request_id=028fc587-e065-43fa-8298-92633a922a87&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control)
> 今天看base的时候看到的。之前我都是手动new的，每天学一点就血赚。

mvvm
````aidl
public abstract class DggBaseActivity<V extends ViewDataBinding, VM extends IMvvmBaseViewModel> extends AppCompatActivity {}
````
mvp
````aidl
public abstract class BaseMvpActivity<V extends BaseActivityMvpView, P extends BasePresenter<V>> extends MvpActivity<V, P> implements BaseActivityMvpView, BaseFragment.BaseFragmentCallback {
````
在mvp中P层可能需要手动new一个，在mvvm 中 viewModel 可能也需要手动new,databinding因为是通过layoutID 生成的，这个是必须手动传入的。
如果写activity的时候，设置正确了P或者V,那么我们就可以通过反射直接获得那个对象了。<br>
当然了，比如说获取类型也行。
### Type的获得
有很多场景下我们可以获得Type，比如：
* 当我们拿到一个Class，用Class. getGenericInterfaces()方法得到Type[]，也就是这个类实现接口的Type类型列表。
    > 我们的P层或者view model 都是实现 所以应该用这个吗？
* 当我们拿到一个Class，用Class.getDeclaredFields()方法得到Field[]，也就是类的属性列表，然后用Field. getGenericType()方法得到这个属性的Type类型。
    > 上面取一个对象的所有变量名就是这个调调。
* 当我们拿到一个Method，用Method. getGenericParameterTypes()方法获得Type[]，也就是方法的参数类型列表。
    > 这个是方法需要的传参。
#### getGenericInterfaces

#### getGenericParameterTypes
> 这个是获取方法，所以需要获得class的方法列表，

获取类的方法
````aidl
Method[] methods=TestReflect.class.getMethods(); 
````
通过方法获取需要的参数
````aidl
Type[] types=oneMethod.getGenericParameterTypes();

````
直接复制上面博客的代码:
```aidl
public class TestReflect {
 
    public static void test(TestReflect p0,
    		List<TestReflect> p1,
    		Map<String,TestReflect> p2,
    		List<String>[] p3,
    		Map<String,TestReflect>[] p4,
    		List<? extends TestReflect> p5,
    		Map<? extends TestReflect,? super TestReflect> p6
    		//T p7
    		){
    	
    }
 
	public static void main(String[] args) {
    	
		Method[] methods=TestReflect.class.getMethods();
 
		for(int i=0;i<methods.length;i++){
			Method oneMethod=methods[i];
			
			if(oneMethod.getName().equals("test")){
				Type[] types=oneMethod.getGenericParameterTypes();
				
				//第一个参数，TestReflect p0
				Class type0=(Class)types[0];
				System.out.println("type0:"+type0.getName());
				//type0:com.webx.TestReflect
				
				//第二个参数，List<TestReflect> p1
				Type type1=types[1];
				Type[] parameterizedType1=((ParameterizedType)type1).getActualTypeArguments();
				Class parameterizedType1_0=(Class)parameterizedType1[0];
				System.out.println("parameterizedType1_0:"+parameterizedType1_0.getName());
				//parameterizedType1_0:com.webx.TestReflect
				
				//第三个参数，Map<String,TestReflect> p2
				Type type2=types[2];
				Type[] parameterizedType2=((ParameterizedType)type2).getActualTypeArguments();
				Class parameterizedType2_0=(Class)parameterizedType2[0];
				System.out.println("parameterizedType2_0:"+parameterizedType2_0.getName());
				Class parameterizedType2_1=(Class)parameterizedType2[1];
				System.out.println("parameterizedType2_1:"+parameterizedType2_1.getName());
				//parameterizedType2_0:java.lang.String
                //parameterizedType2_1:com.webx.TestReflect
 
				//第四个参数，List<String>[] p3
				Type type3=types[3];
				Type genericArrayType3=((GenericArrayType)type3).getGenericComponentType();
				ParameterizedType parameterizedType3=(ParameterizedType)genericArrayType3;
				Type[] parameterizedType3Arr=parameterizedType3.getActualTypeArguments();
				Class class3=(Class)parameterizedType3Arr[0];
				System.out.println("class3:"+class3.getName());
				//class3:java.lang.String
				
				//第五个参数，Map<String,TestReflect>[] p4
				Type type4=types[4];
				Type genericArrayType4=((GenericArrayType)type4).getGenericComponentType();
				ParameterizedType parameterizedType4=(ParameterizedType)genericArrayType4;
				Type[] parameterizedType4Arr=parameterizedType4.getActualTypeArguments();
				Class class4_0=(Class)parameterizedType4Arr[0];
				System.out.println("class4_0:"+class4_0.getName());
				Class class4_1=(Class)parameterizedType4Arr[1];
				System.out.println("class4_1:"+class4_1.getName());
				//class4_0:java.lang.String
                //class4_1:com.webx.TestReflect
 
				//第六个参数，List<? extends TestReflect> p5
				Type type5=types[5];
				Type[] parameterizedType5=((ParameterizedType)type5).getActualTypeArguments();
				Type[] parameterizedType5_0_upper=((WildcardType)parameterizedType5[0]).getUpperBounds();
				Type[] parameterizedType5_0_lower=((WildcardType)parameterizedType5[0]).getLowerBounds();
				
				//第七个参数，Map<? extends TestReflect,? super TestReflect> p6
				Type type6=types[6];
				Type[] parameterizedType6=((ParameterizedType)type6).getActualTypeArguments();
				Type[] parameterizedType6_0_upper=((WildcardType)parameterizedType6[0]).getUpperBounds();
				Type[] parameterizedType6_0_lower=((WildcardType)parameterizedType6[0]).getLowerBounds();
				Type[] parameterizedType6_1_upper=((WildcardType)parameterizedType6[1]).getUpperBounds();
				Type[] parameterizedType6_1_lower=((WildcardType)parameterizedType6[1]).getLowerBounds();
								
			}
			
			
		}
		
	}
 
}
```