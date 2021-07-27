+++
date = "2021-4-12"
title = "fragment嵌套生命周期走查（懒加载有问题）"
description = "fragment嵌套生命周期走查（懒加载有问题）"
tags = [ "fragment"]
categories = [
"android基础"
]

featured = true
+++

## 前言
> 话说，搬砖过程中fragment+viewpager相当常见。按照道理讲不应该出现这个bug的。主要是太久没有写代码了。emmmmm? 变菜了，能怪我？
> 还有一个问题。感觉viewpager+fragment 导致的生命周期就很奇怪，最近有大佬提供的懒加载fragment，so。
## 正文
> 话说，viewpager主要是通过adapter设置预加载数量。
* setOffscreenPageLimit()
> 然后是通过adapter设置相关的东西
* FragmentPagerAdapter
> 我们知道viewpager 默认加载3个，就是当前的和左右一边一个。其他的item都会默认销毁。所以我们的懒加载fragment的实现就尤为重要了。
### 懒加载fragment
> 懒加载的实现模式大概就这么一个样子，都很成熟了。
* 记录一个fragment中onCreateView 的创建状态。
* 在onViewCreated中对!isHidden() && getUserVisibleHint() 进行事件分发。
* 实现setUserVisibleHint 相关逻辑。
* 实现onHiddenChanged 可见不可见逻辑。
* 实现 判断子类可见与否的逻辑。
* onResume 实现第一次可见。
> 整个大概就这个样子:
````aidl
public abstract class BaseLazyFragment extends Fragment {

    /**
     * mFragmentTag
     */
    public String TAG = "";
    /**
     * mLoadService
     */
    String title;
    public BaseLazyFragment setShow(String title){
        this.title=title;
        TAG=title;
        return this;
    }

    /**
     * Fragment生命周期 onAttach -> onCreate -> onCreatedView -> onActivityCreated
     * -> onStart -> onResume -> onPause -> onStop -> onDestroyView -> onDestroy
     * -> onDetach 对于 ViewPager + Fragment 的实现我们需要关注的几个生命周期有： onCreatedView +
     * onActivityCreated + onResume + onPause + onDestroyView
     */

    protected View rootView = null;

    /**
     * 布局是否创建完成
     */
    protected boolean isViewCreated = false;

    /**
     * 当前可见状态
     */
    protected boolean currentVisibleState = false;

    /**
     * 是否第一次可见
     */
    protected boolean mIsFirstVisible = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
        Log.e(TAG, "onCreate: ");
    }

    /**
     * 初始化参数
     */
    protected void initParameters() {
        Log.e(TAG, "initParameters: ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(getLayoutResId(), container, false);
        }
        isViewCreated = true;
        Log.e(TAG, "onCreateView: ");
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isHidden() && getUserVisibleHint()) {
            // 可见状态,进行事件分发
            dispatchUserVisibleHint(true);
        }
        Log.e(TAG, "onViewCreated: ");
    }

    /**
     * 修改Fragment的可见性 setUserVisibleHint 被调用有两种情况：
     * 1）在切换tab的时候，会先于所有fragment的其他生命周期，先调用这个函数，可以看log 2)
     * 对于之前已经调用过setUserVisibleHint方法的fragment后，让fragment从可见到不可见之间状态的变化
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 对于情况1）不予处理，用 isViewCreated 进行判断，如果isViewCreated false，说明它没有被创建
        if (isViewCreated) {
            // 对于情况2,需要分情况考虑,如果是不可见 -> 可见 2.1
            // 如果是可见 -> 不可见 2.2
            // 对于2.1）我们需要如何判断呢？首先必须是可见的（isVisibleToUser
            // 为true）而且只有当可见状态进行改变的时候才需要切换，否则会出现反复调用的情况
            // 从而导致事件分发带来的多次更新
            if (isVisibleToUser && !currentVisibleState) {
                // 从不可见 -> 可见
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                dispatchUserVisibleHint(false);
            }
        }
        Log.e(TAG, "setUserVisibleHint: ");
    }


    /**
     * 用FragmentTransaction来控制fragment的hide和show时，
     * 那么这个方法就会被调用。每当你对某个Fragment使用hide 或者是show的时候，那么这个Fragment就会自动调用这个方法。
     */

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // 这里的可见返回为false
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
        Log.e(TAG, "onHiddenChanged: ");
    }

    /**
     * 统一处理用户可见事件分发
     */
    public void dispatchUserVisibleHint(boolean isVisible) {
        // 首先考虑一下fragment嵌套fragment的情况(只考虑2层嵌套)
        if (isVisible && isParentInvisible()) {
            // 父Fragmnet此时不可见,直接return不做处理
            return;
        }
        // 为了代码严谨,如果当前状态与需要设置的状态本来就一致了,就不处理了
        if (currentVisibleState == isVisible) {
            return;
        }
        currentVisibleState = isVisible;
        if (isVisible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                // 第一次可见,进行全局初始化
                onFragmentFirstVisible();
            }
            onFragmentResume();
            // 分发事件给内嵌的Fragment
            dispatchChildVisibleState(true);
        } else {
            onFragmentPause();
            dispatchChildVisibleState(false);
        }
        Log.e(TAG, "dispatchUserVisibleHint: ");

    }

    /**
     * 在双重ViewPager嵌套的情况下，第一次滑到Frgment 嵌套ViewPager(fragment)的场景的时候
     * 此时只会加载外层Fragment的数据，而不会加载内嵌viewPager中的fragment的数据，因此，我们
     * 需要在此增加一个当外层Fragment可见的时候，分发可见事件给自己内嵌的所有Fragment显示
     */
    private void dispatchChildVisibleState(boolean visible) {

        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (null != fragments) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseLazyFragment && !fragment.isHidden()
                        && fragment.getUserVisibleHint()) {
                    ((BaseLazyFragment) fragment).dispatchUserVisibleHint(visible);
                }
            }
        }
        Log.e(TAG, "dispatchChildVisibleState: ");
    }

    /**
     * Fragment真正的Pause,暂停一切网络耗时操作
     */
    protected void onFragmentPause() {
        Log.e(TAG, "onFragmentPause: ");
    }

    /**
     * Fragment真正的Resume,开始处理网络加载等耗时操作
     */
    protected void onFragmentResume() {
        Log.e(TAG, "onFragmentResume: ");
    }

    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof BaseLazyFragment) {
            BaseLazyFragment fragment = (BaseLazyFragment) parentFragment;
            return !fragment.isSupportVisible();
        }
        Log.e(TAG, "isParentInvisible: ");
        return false;
    }

    private boolean isSupportVisible() {
        return currentVisibleState;
    }

    /**
     * 在滑动或者跳转的过程中，第一次创建fragment的时候均会调用onResume方法
     */
    @Override
    public void onResume() {
        super.onResume();
        // 如果不是第一次可见
        if (!mIsFirstVisible) {
            // 如果此时进行Activity跳转,会将所有的缓存的fragment进行onResume生命周期的重复
            // 只需要对可见的fragment进行加载,
            if (!isHidden() && !currentVisibleState && getUserVisibleHint()) {

                dispatchUserVisibleHint(true);
            }
        }
        Log.e(TAG, "onResume: ");

    }

    /**
     * 只有当当前页面由可见状态转变到不可见状态时才需要调用 dispatchUserVisibleHint currentVisibleState &&
     * getUserVisibleHint() 能够限定是当前可见的 Fragment 当前 Fragment 包含子 Fragment 的时候
     * dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见 子 fragment 走到这里的时候自身又会调用一遍
     */
    @Override
    public void onPause() {
        super.onPause();
        if (currentVisibleState && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        Log.e(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach: ");
    }

    /**
     * 第一次可见,根据业务进行初始化操作
     */
    protected void onFragmentFirstVisible() {
        initView();
        initData();
        initListener();
        Log.e(TAG, "onFragmentFirstVisible: ");
    }

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化Data
     */
    protected abstract void initData();

    /**
     * 初始化Listener
     */
    protected abstract void initListener();


    @LayoutRes
    protected abstract int getLayoutResId();

}

````
> 重点来了，这个懒加载作用是防止重复加载，这个很重要。所以你viewpager不停的回收创建，是没有办法使用懒加载的。
### adapter
我们知道可以设置setOffscreenPageLimit() 设置预加载数量。但是好像也没有啥用，因为他该走删除还是会走删除，因为这个需要搭配 destroyItem一起使用。
所以destroyItem 就是删除item的罪魁祸首。所以把他注释掉就行？但是这么就会出现一个问题，万一有很多fragment加载进来呢？如果fragment少的话，使用懒加载是没有问题。所以要真正解决这个情况，就需要对viewpager+fragment生命周期详细透彻的了解。同时对adapter支持的方法进行了解了。
#### 不做任何处理默认展示第0个
> 当前展示第0个
* 0: onCreate:
* 1: onCreate:
* 0: onCreateView:
* 0: onViewCreated:
* 0: onStart:
* 0: onResume:
* 1: onCreateView:
* 1: onViewCreated:
* 1: onStart:
* 1: onResume:
>很符合设定模式，直接加载两个。
#### 不做任何处理，显示第1个。
> 当前展示第1个
* 1: onCreate:
* 0: onCreate:
* 2: onCreate:
* 1: onCreateView:
* 1: onViewCreated:
* 1: onStart:
* 1: onResume:
* 0: onCreateView:
* 0: onViewCreated:
* 2: onCreateView:
* 2: onViewCreated:
* 0: onStart:
* 0: onResume:
* 2: onStart:
* 2: onResume:
> 和上面类似。直接加载第一个到显示，然后是加载左边的创建流程，然后是右边的创建流程，然后是左边的显示流程，然后是右边的显示流程。
#### 向右滑动一个
> 当前显示1，滑动后显示2
* 3: onCreate:
* 0: onPause:
* 0: onDestroyView:
* 3: onCreateView:
* 3: onViewCreated:
* 3: onStart:
* 3: onResume:
> 滑动前显示的是1，因为2是加载出来的，滑动到2，所以0 被销毁了，3被创建了，这很符合逻辑。
#### 滑回去一次
> 当前显示2，滑动后显示1
* 0: onCreateView:
* 0: onViewCreated:
* 3: onPause:
* 3: onDestroyView:
* 0: onStart:
* 0: onResume:
> 从上面可以看出，onCreate对于已经创建的只会执行一次。而onDestroy 没有执行。
#### 关闭界面
> 当前显示1，关闭界面
* 1: onPause:
* 2: onPause:
* 0: onPause:
* 1: onDestroyView:
* 2: onDestroyView:
* 0: onDestroyView:
* 1: onDestroy:
* 2: onDestroy:
* 0: onDestroy:
* 3: onDestroy:
> activity界面关闭后，走过创建后的都会走 onDestroy。
#### 设置缓存为fragment个数
* setOffscreenPageLimit()
> 生命周期执行。

* 0: onCreate:
* 1: onCreate:
* 2: onCreate:
* 3: onCreate:
* 4: onCreate:
* 5: onCreate:
* 0: onCreateView:
* 0: onViewCreated:
* 0: onStart:
* 0: onResume:
* 1: onCreateView:
* 1: onViewCreated:
* 2: onCreateView:
* 2: onViewCreated:
* 3: onCreateView:
* 3: onViewCreated:
* 4: onCreateView:
* 4: onViewCreated:
* 5: onCreateView:
* 5: onViewCreated:
* 1: onStart:
* 1: onResume:
* 5: onStart:
* 5: onResume:
* 4: onStart:
* 4: onResume:
* 2: onStart:
* 2: onResume:
* 3: onStart:
* 3: onResume:
> emmmm? 卧槽怎么全部加载出来了，这很符合逻辑嘛。所有的都执行到onResume了 那我滑动一下会出现什么问题？事实证明没有任何的生命周期变动。emmmm?

#### 那我注释掉destroyItem 不设置缓存呢？
> 创建肯定还是和上面默认创建一样的。关键在于销毁上。当前第0个，滑动到第一个。
* 2: onCreate:
* 2: onCreateView:
* 2: onViewCreated:
* 2: onStart:
* 2: onResume:
> 明显就没有上面的0 
* 0: onPause:
* 0: onDestroyView:
> 所以逻辑很正确，创建后不销毁。
### 懒加载？
> 众所皆知，懒加载是为了防止重复创建和调用网络请求而生的。而viewpager 默认的加载模式和销毁模式和懒加载是有冲突的。因为viewpager 只是删除了view，没有删除对象。就导致对象存在，view不存在。view中如果包含生命周期绑定，那么懒加载肯定会出现问题的。所以是不能走onDestroyView的，而大多数的fragment监听是在onDestroyView中注销的，泪了。
> 那么既然 setOffscreenPageLimit()和destroyItem注释后都是不走onDestroyView。
> 懒加载主要处理:

* onFragmentPause
* onFragmentResume
* onFragmentFirstVisible
* 

### 外层fragment不用懒加载 子fragment使用viewpager懒加载。
#### 情景1
> 外层fragment不是懒加载fragment，包裹fragment是懒加载fragment，使用viewpager 默认加载。默认加载第0个。

* 0: setUserVisibleHint:
* 1: setUserVisibleHint:
* 0: setUserVisibleHint:
* 0: onAttach:
* 0: initParameters:
* 0: onCreate:
* 1: onAttach:
* 1: initParameters:
* 1: onCreate:
* 0: onCreateView:
* 0: isParentInvisible:
* 0: onFragmentFirstVisible:
* 0: onFragmentResume:
* 0: dispatchChildVisibleState:
* 0: dispatchUserVisibleHint:
* 0: onViewCreated:
* 0: onStart:
* 0: onResume:
* 1: onCreateView:
* 1: onViewCreated:
* 1: onStart:
* 1: onResume:
> 从上面可以看出，只有加载出来的0，才执行了onFragmentFirstVisible。和onFragmentResume

> 当从0 滑动到1。

* 2: setUserVisibleHint:
* 0: onFragmentPause:
* 0: dispatchChildVisibleState:
* 0: dispatchUserVisibleHint:
* 0: setUserVisibleHint:
* 1: isParentInvisible:
* 1: onFragmentFirstVisible:
* 1: onFragmentResume:
* 1: dispatchChildVisibleState:
* 1: dispatchUserVisibleHint:
* 1: setUserVisibleHint:
* 2: onAttach:
* 2: initParameters:
* 2: onCreate:
* 2: onCreateView:
* 2: onViewCreated:
* 2: onStart:
* 2: onResume:

> 按照逻辑显示的1，调用了onFragmentFirstVisible，onFragmentResume，将0 设置为onFragmentPause

> 将 1滑动到2。

* 3: setUserVisibleHint:
* 1: onFragmentPause:
* 1: dispatchChildVisibleState:
* 1: dispatchUserVisibleHint:
* 1: setUserVisibleHint:
* 2: isParentInvisible:
* 2: onFragmentFirstVisible:
* 2: onFragmentResume:
* 2: dispatchChildVisibleState:
* 2: dispatchUserVisibleHint:
* 2: setUserVisibleHint:
* 3: onAttach:
* 3: initParameters:
* 3: onCreate:
* 0: onPause:
* 0: onDestroyView:
* 3: onCreateView:
* 3: onViewCreated:
* 3: onStart:
* 3: onResume:
> 可以看出来，关于0的销毁和3的创建是没有区别的。

> 返回到1，将3销毁，将1复活。

* 0: setUserVisibleHint:
* 2: onFragmentPause:
* 2: dispatchChildVisibleState:
* 2: dispatchUserVisibleHint:
* 2: setUserVisibleHint:
* 1: isParentInvisible:
* 1: onFragmentResume:
* 1: dispatchChildVisibleState:
* 1: dispatchUserVisibleHint:
* 1: setUserVisibleHint:
* 0: onCreateView:
* 0: onViewCreated:
* 3: onPause:
* 3: onDestroyView:
* 0: onStart:
* 0: onResume:
> 2处于暂停，1处于显示，也没有问题。很符合逻辑。0重新调用onViewCreated。和正常的fragment调用是一样的。所以懒加载也是需要设置fragment的销毁和缓存的。
#### 情景2
> 和情景1类似，直接设置adapter的缓存最大的个数。

* 0: setUserVisibleHint:
* 1: setUserVisibleHint:
* 2: setUserVisibleHint:
* 3: setUserVisibleHint:
* 4: setUserVisibleHint:
* 5: setUserVisibleHint:
* 0: setUserVisibleHint:
* 0: onAttach:
* 0: initParameters:
* 0: onCreate:
* 1: onAttach:
* 1: initParameters:
* 1: onCreate:
* 2: onAttach:
* 2: initParameters:
* 2: onCreate:
* 3: onAttach:
* 3: initParameters:
* 3: onCreate:
* 4: onAttach:
* 4: initParameters:
* 4: onCreate:
* 5: onAttach:
* 5: initParameters:
* 5: onCreate:
* 0: onCreateView:
* 0: isParentInvisible:
* 0: onFragmentFirstVisible:
* 0: onFragmentResume:
* 0: dispatchChildVisibleState:
* 0: dispatchUserVisibleHint:
* 0: onViewCreated:
* 0: onStart:
* 0: onResume:
* 1: onCreateView:
* 1: onViewCreated:
* 2: onCreateView:
* 2: onViewCreated:
* 3: onCreateView:
* 3: onViewCreated:
* 4: onCreateView:
* 4: onViewCreated:
* 5: onCreateView:
* 5: onViewCreated:
* 3: onStart:
* 3: onResume:
* 3: onResume:
* 2: onStart:
* 2: onResume:
* 2: onResume:
* 1: onStart:
* 1: onResume:
* 1: onResume:
* 4: onStart:
* 4: onResume:
* 4: onResume:
* 5: onStart:
* 5: onResume:
> 和正常的fragment 设置全部缓存类似，也是直接加载了全部。
> * 0: onFragmentFirstVisible:
> * 0: onFragmentResume:</br>
> 这个方法调用也很合懒加载的逻辑。

> 切换fragment

* 2: onFragmentPause:
* 2: dispatchChildVisibleState:
* 2: dispatchUserVisibleHint:
* 2: setUserVisibleHint:
* 3:  :
* 3: onFragmentFirstVisible:
* 3: onFragmentResume:
* 3: dispatchChildVisibleState:
* 3: dispatchUserVisibleHint:
* 3: setUserVisibleHint:
> 只有切换显示到的fragment，调用了onFragmentResume。这个也很符合逻辑，没有调用销毁，和正常的fragment的全部缓存切换类似。

#### 情景3
> 和情景1类型，只是注释掉adapter 中的destroyItem

> 加载0。

* 0: setUserVisibleHint:
* 1: setUserVisibleHint:
* 0: setUserVisibleHint:
* 0: onAttach:
* 0: initParameters:
* 0: onCreate:
* 1: onAttach:
* 1: initParameters:
* 1: onCreate:
* 0: onCreateView:
* 0: isParentInvisible:
* 0: onFragmentFirstVisible:
* 0: onFragmentResume:
* 0: dispatchChildVisibleState:
* 0: dispatchUserVisibleHint:
* 0: onViewCreated:
* 0: onStart:
* 0: onResume:
* 1: onCreateView:
* 1: onViewCreated:
* 1: onStart:
* 1: onResume:

> 初始化和不设置参数的时候类似。

> 滑动到1

* 2: setUserVisibleHint:
* 0: onFragmentPause:
* 0: dispatchChildVisibleState:
* 0: dispatchUserVisibleHint:
* 0: setUserVisibleHint:
* 1: isParentInvisible:
* 1: onFragmentFirstVisible:
* 1: onFragmentResume:
* 1: dispatchChildVisibleState:
* 1: dispatchUserVisibleHint:
* 1: setUserVisibleHint:
* 2: onAttach:
* 2: initParameters:
* 2: onCreate:
* 2: onCreateView:
* 2: onViewCreated:
* 2: onStart:
* 2: onResume:
> 创建类似。
> 滑动到2，监听0的销毁情况。

* 3: setUserVisibleHint:
* 1: onFragmentPause:
* 1: dispatchChildVisibleState:
* 1: dispatchUserVisibleHint:
* 1: setUserVisibleHint:
* 2: isParentInvisible:
* 2: onFragmentFirstVisible:
* 2: onFragmentResume:
* 2: dispatchChildVisibleState:
* 2: dispatchUserVisibleHint:
* 2: setUserVisibleHint:
* 3: onAttach:
* 3: initParameters:
* 3: onCreate:
* 3: onCreateView:
* 3: onViewCreated:
* 3: onStart:
* 3: onResume:
> 从上面可以看出，因为注释掉销毁方法，所以根本就没有执行销毁逻辑。而创建逻辑是正常的。

### 套娃 
> 因为fragment的套娃加载设计，可能出现4种，
* 外层使用FrameLayout，内层也使用FrameLayout。
* 外层使用FrameLayout，内层也使用viewpager。
* 外层使用viewpager,内层也使用FrameLayout。
* 外层使用viewpager,内层也使用viewpager。
> 具体哪种写法好，别问，问就是不知道。<br>
> 这种套娃现象很容易出现在多聚合功能的首页上面，比如说主页4个tab,tab内部又是tab  emmmmm? 反正这种界面设计蛮常见的。所以是不建议直接设置viewpager adapter的缓存的为最大的。
> 一次性加载全部，可能性能上有点问题，同时启动的时候，请求的东西太多。个人建议直接注释掉销毁代码，当然也可以根据情况，自己写销毁这边的代码。可能根据业务情况，销毁不常用的功能点是很符合逻辑的，粗暴的注释掉销毁也不是不能解决问题，就怕有杠精把所有tab加载一遍，然后提bug。为啥这是bug?我也不知道，萌新搞不懂测试大佬们的想法。
><br> 反正个人觉得，不常用的功能点，是可以销毁掉的，或者比较吃资源保留着，或者吃资源然后还不常用的直接销毁掉吧，emmmm?数据缓存了解下。


#### 外层使用FrameLayout，内层使用FrameLayout，使用正常fragment
>  外层用group,内层是child.外层默认加载第一个，内层默认加载第2个。

Group1: onCreate:
Group1: onCreateView:
Group1: onViewCreated:
child1: onCreate:
child1: onCreateView:
child1: onViewCreated:
Group1: onStart:
child1: onStart:
Group1: onResume:
child1: onResume:
> 从上面的生命周期调用可以知道，只是加载了外层第0个和内层的第0个。

 外层0保持不变，内层切到到1.

* child2: onCreate:
* child2: onCreateView:
* child2: onViewCreated:
* child2: onStart:
* child2: onResume:
> 内层1个加载出来，内层0因为已经加载出来，所以没有调用生命周期，1显示出来直接覆盖掉0.切到0，生命周期也未变动。

内层保存不变，切换外层，0切换到1
* Group2: onCreate:
* Group2: onCreateView:
* Group2: onViewCreated:
* child1: onCreate:
* child1: onCreateView:
* child1: onViewCreated:
* Group2: onStart:
* child1: onStart:
* Group2: onResume:
* child1: onResume:
> 这个和外层0创建生命周期走的一致。切换到0的时候，0的生命周期也未变动。
##### 总结
> 使用FrameLayout 嵌套FrameLayout ，对于已经加载出来的fragment是通过覆盖，和隐藏已经显示的fragment达到效果的，切换的时候不影响生命周期。只有没有创建的fragment会走创建的生命周期。而退出的时候，会统一走销毁。
> 所以不建议直接加载全部的fragment，需要的时候才会创建。因为需要自己实现fragment的添加逻辑，所以可以在添加的时候，选择移除或者隐藏已有的fragment。通常是隐藏fragment。

fragment在FrameLayout中的显示代码示例:
````aidl
public Fragment showFragment(String className, FragmentManager fragmentManager, int id) {
        Fragment fragment = null;
        try {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //隐藏所有的那个啥。
            for (Fragment fra : fragmentManager.getFragments()) {
                fragmentTransaction.hide(fra);
            }
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(className);
            if (fragmentByTag != null) {
                fragmentTransaction.show(fragmentByTag);
            } else {
                fragment = (Fragment) Class.forName(className).newInstance();
                fragmentTransaction.add(id, fragment, className);
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
        return fragment;
    }
````
但是这么写有一个问题，他并不是上一行代码显示了fragment，下一行代码Fragment fragmentByTag = fragmentManager.findFragmentByTag(className);就可以获取到已经添加进去了。
所以要操作显示的fragment，可能需要监听fragment的生命周期。监听代码和生命周期监听一样。
#### 外层使用FrameLayout，内层使用FrameLayout，使用LazyFragment
默认外层加载0，内层加载0，生命周期类似。
外层保持0，内层切到到1.

* child2: onCreate:
* child1: onFragmentPause:
* child2: onCreateView:
* child2: isParentInvisible:
* child2: onFragmentFirstVisible:
* child2: onFragmentResume:
* child2: onViewCreated:
* child2: onStart:
* child2: onResume:

外层保持0，内层再切换到0。
  
* child2: onFragmentPause:
* child1: onFragmentResume:
内层保持0，外层切换到1。
* Group2: onCreate:
* Group2: onCreateView:
* Group2: onViewCreated:
* Group2-child1: onAttach:
* Group2-child1: initParameters:
* Group2-child1: onCreate:
* Group2-child1: onCreateView:
* Group2-child1: isParentInvisible:
* Group2-child1: onFragmentFirstVisible:
* Group2-child1: onFragmentResume:
* Group2-child1: dispatchChildVisibleState:
* Group2-child1: dispatchUserVisibleHint:
* Group2-child1: onViewCreated:
* Group2: onStart:
* Group2-child1: onStart:
* Group2: onResume:
* Group2-child1: onResume:
内层保持0，外层切换到0。<br>
内层切换到1，外层切换到1。<br>
内层切换到0，外层切换到0 。 <br>
> 基于上面的流程，是没有什么周期变动的。默认使用FrameLayout和fragment对于已经加载的fragment是不进行生命周期调用了。所以外层的fragment的更改无法影响到内层的懒加载fragment。只有内层的懒加载fragment进行切换才有生命周期变动。
#### 外层使用FrameLayout，使用lazyFragment，内层使用FrameLayout，使用LazyFragment
> 从上面的实践，我们知道lazyFragment 的模式，会在FrameLayout中对于已经存在的fragment进行方法调用。而创建流程走的生命周期是一致的，而有区别的在于lazyFragment 切换的方法调用。
><br> 逻辑意义上，父fragment处于隐藏，子fragment也会调用隐藏代码。

* Group2: onFragmentPause:
* Group2-child1: onFragmentPause:
* Group2-child1: dispatchChildVisibleState:
* Group2-child1: dispatchUserVisibleHint:
* Group2: dispatchChildVisibleState:
* Group2: dispatchUserVisibleHint:
* Group2: onHiddenChanged:
* Group1: isParentInvisible:
* Group1: onFragmentResume:
* Group1-child1: onFragmentResume:
* Group1-child1: dispatchChildVisibleState:
* Group1-child1: dispatchUserVisibleHint:
* Group1: dispatchChildVisibleState:
* Group1: dispatchUserVisibleHint:
* Group1: onHiddenChanged:
> 从上面的方法调度可以看出，这个是外层2，切换到1。2调用隐藏方法，2的child也调用了暂停方法。
#### 外层使用FrameLayout+fragment，内层viewpager+fragment
> 默认创建0的时候，0走创建流程，内部加载0和1，内部切换的时候和单viewpager 切换类似。
> 按照FrameLayout 切换逻辑，外层切换是不影响内部的生命周期调用的。
> <br> 通过上面的demo运行，可以得出结论。
 * 无论内部fragment设置全部缓存还是修改adapter中item的删除代码，外层调用将不受影响。
 * 内部加载lazyFragment在外部切换后会调用对应方法。
 * 外部使用LazyFragment 和内部使用LazyFragment 存在相同的情景，外部切换会调用显示隐藏对应的方法。 外部也会影响到内部。

#### 外层使用viewpager,内层也使用FrameLayout。
逻辑意义上会出现下面情景：
* 外层使用fragment，内层也使用fragment。因为外层存在删除，缓存相关定义，会直接影响内部的创建，因为内部是frameLayou，内部切换不进行生命周期方法调度。但是外部fragment的显示切换和基础设置会影响到内部fragment的创建。建议注释掉adapter 的删除代码。
* 无论使用lazyFragment 还是 fragment都存在设置直接影响到子fragment的相关情况。
#### 外层使用viewpager,内层也使用viewpager。
* 外层的fragment的生命周期 直接影响内部的fragment的生命周期。
### 结论
> 如果viewpager 存在外层，建议处理缓存个数或者重写adapter的删除逻辑。避免内部的fragment存在重复创建的问题。相对于全部缓存，更加建议使用重写adapter的删除逻辑。因为adapter是需要new对象的，所以对于那些不需要左右滑动切换的功能，建议直接用FrameLayout,而不是adapter。
## 结束


