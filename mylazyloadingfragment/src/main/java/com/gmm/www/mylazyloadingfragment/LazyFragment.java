package com.gmm.www.mylazyloadingfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author:gmm
 * @date:2020/4/13
 * @类说明: 懒加载 Fragment
 *  Fragment 的生命周期：
 *  onAttach -> onCreate -> onCreatedView -> onActivityCreated -> onStart -> onResume -> onPause -> onStop ->onDestroyView -> onDestroy -> onDetach
 *  懒加载 需要关注的生命周期
 *  onCreatedView -> onActivityCreated -> onResume -> onPause -> onDestroyView
 *
 *  需要关注的非生命周期函数
 *  setUserVisibleHint
 */
public abstract class LazyFragment extends Fragment {
    private static final String TAG = "LazyFragment";
    protected View rootView = null;

    //视图是否已创建
    private boolean isViewCreated = false;
    //Fragment当前可见状态
    private boolean currentVisibleState = false;
    //Fragment是否是第一次可见
    private boolean isFirstVisible = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(),container,false);
        }
        Log.e(TAG, getClass().getSimpleName() + "->onCreateView: ");
        initView(rootView);
        isViewCreated = true;
        //初始化的时候，判断当前Fragment的可见状态
        //1.当Fragment由不可见状态 -> 可见状态 时，分发可见状态
        //由于setUserVisibleHint会在onCreatedView之前调用，所以当不可见 -> 可见 变化时，主调分发一下可见状态
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, getClass().getSimpleName() + "->onResume: " );
//        在滑动或者跳转的过程中，第一次创建Fragment的时候会调用onResume方法，类似于tab1 滑到 tab2，此时tab3会缓存，这个时候会调用tab3 对应的Fragment的
//        onResume，所以，此时是不需要去调用 dispatchUserVisibleHint(true) 的，因而出现了下面的if
        if (!isFirstVisible) {
            //由于Activity1中如果有多个Fragment，然后从Activity1 跳转到 Activity2，此时会有多个Fragment会在Activity1缓存，此时，如果再从Activity2跳转回
            //Activity1，这个时候会将所有的缓存的Fragment进行onResume生命周期的重复，这个时候我们无需对所有的Fragment 调用dispatchUserVisibleHint(true)
            //分发可见状态，我们只需要对可见的Fragment进行加载，因此就有下面的if
            if (!isHidden()&&!currentVisibleState&&getUserVisibleHint()){//当Activity重新唤醒时(后台->前台)，此时的Fragment是不可见-> 可见，需要分发可见状态来重新获取数据
                Log.e(TAG, getClass().getSimpleName() + "->onResume: " + "所属Activity从后台恢复到前台" );
                dispatchUserVisibleHint(true);
            }
        }
   }

    /**
     * 修改Fragment的可见状态
     * setUserVisibleHint 被调用有两种情况：①在切换tab的时候 ，会先于fragment所有的生命周期函数，会先调这个函数
     * ②对于之前已经调用过setUserVisibleHint方法的fragment，当fragment从可见到不可见的时候会再次调用
     *
     * 由于setUserVisibleHint会在onCreatedView之前调用，所以在 不可见 -> 可见 变化时，虽然会调用这个方法，但是rootView还没有创建，
     * 所以不能在rootView没有创建的时候就获取、终止数据加载和显示
     * *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, getClass().getSimpleName() + "->setUserVisibleHint: " + isVisibleToUser );
        //对于情况① 不予处理，用 isViewCreated 进行判断，如果isViewCreated = false,说明还没有创建
        if (isViewCreated) { //不会再回调onCreateView声明周期函数，所以要在这里分发可见状态
            //对于情况② 要分情况考虑，如果是由 不可见 -> 可见，那么isVisibleToUser = true，之前的可见状态是currentVisibleState = false;
            //如果是 可见 -> 不可见，那么isVisibleToUser = false，之前的可见状态是currentVisibleState = true;
            if (isVisibleToUser && !currentVisibleState) {//不可见 -> 可见
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && currentVisibleState) { //可见 -> 不可见
                dispatchUserVisibleHint(false);
            }
        }
    }

    /**
     * 用FragmentTransaction来控制Fragment的hide和show时，
     * 那么这个方法就会被调用。每当对某个Fragment使用hid或者show的
     * 时候，那么这个Fragment就会自动调用这个方法
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, getClass().getSimpleName() + "->onHiddenChanged: " + hidden);
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    protected abstract int getLayoutRes();

    protected abstract void initView(View rootView);

    /**
     *只有当当前页面由可见状态转变到不可见状态时才需要调用 dispatchUserVisibleHint
     * currentVisibleState && getUserVisibleHint 能够限定式当前可见的 Fragment
     * 当前 Fragment 包含子Fragment 的时候 dispatchUserVisibleHint内部本身就会通知子Fragment 不可见
     * 子Fragment 走到这里的时候自身会调用一遍
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, getClass().getSimpleName() + "->onPause: ");
        if (currentVisibleState && getUserVisibleHint()) {//当Activity不可见时，会回调Fragment的onPause，此时Fragment的currentVisibleState和getUserVisibleHint()都是true，需要终止耗时操作
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, getClass().getSimpleName() + "->onDestroyView: ");
        isViewCreated = false;
        isFirstVisible = true;
    }

    /**
     * 分发可见状态 只要满足响应的条件，才让Fragment进行网络请求等好事操作
     * @param isVisible
     */
    private void dispatchUserVisibleHint(boolean isVisible) {
        Log.e(TAG, getClass().getSimpleName() + "->dispatchUserVisibleHint: " + isVisible );
        //事实上作为父Fragment的BottomTabFragment2 并没有分发可见状态。
        //他通过getUserVisibleHint() 得到的结果false，首先想到的是
        //能在负责分发状态的方法中判断一下当前父Fragment 是否可见。
        //如果父Fragment 不可见我们就不进行可见状态的分发
        if (isVisible && !isParentVisible()) {//当前Fragment可见，但是父Fragment不可见，则不处理
            return;
        }
        //为了代码严谨
        if (currentVisibleState == isVisible) {//状态未改变
            return;
        }
        //更新Fragment当前可见状态
        currentVisibleState = isVisible;
        if (isVisible) {
            if (isFirstVisible) {//Fragment是第一次可见，可以从网络获取数据
                isFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentResume();//可以从缓存、db获取数据
            //在双重ViewPager嵌套的情况下，第一次滑到Fragment 嵌套ViewPager(fragment)的场景的时候
            //此时只会加载外层Fragment的数据，而不会加载内嵌ViewPager中的Fragment的数据，因此，我们
            //需要在此增加一个当外层Fragment可见的时候，分发可见状态给自己内嵌的所有Fragment显示
            dispatchChildVisibleState(true);
        } else {
            onFragmentPause();//终止耗时操作
            dispatchChildVisibleState(false);
        }
    }

    private boolean isParentVisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof LazyFragment) {
            LazyFragment fragment = (LazyFragment) parentFragment;
            return fragment.isSupportVisible();
        }
        return true;//可见
    }

    private boolean isSupportVisible() {
        return currentVisibleState;
    }

    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager manager = getChildFragmentManager();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof LazyFragment &&
                        !fragment.isHidden() &&
                        fragment.getUserVisibleHint()) {
                    ((LazyFragment) fragment).dispatchUserVisibleHint(visible);
                }
            }
        }
    }

    //第一次可见时，数据可能需要从网络获取
    protected abstract void onFragmentFirstVisible();

    //当缓存有数据了，直接从缓存获取数据
    protected void onFragmentResume() {
        Log.e(TAG, getClass().getSimpleName() + "->onFragmentResume: " + "开始相关耗时操作" );
    }

    //中断耗时操作
    protected void onFragmentPause() {
        Log.e(TAG, getClass().getSimpleName() + "->onFragmentPause: " + "终止正在进行的耗时操作" );
    }
}
