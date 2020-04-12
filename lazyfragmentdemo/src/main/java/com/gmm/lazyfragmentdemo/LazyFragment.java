package com.gmm.lazyfragmentdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

//懒加载Fragment  处理声明周期函数 onCreateView  onResume onPause onDestroyView 和 控制可见不可见函数 setUserVisibleHint

/**
 * Fragment 创建过程，setUserVisibleHint要优先于生命周期函数
 */
public abstract class LazyFragment extends Fragment {

//    private String TAG = "LAZY FRAGMENT";
    //界面被创建并初始化
    private boolean isViewCreated = false;
    //捕捉不可见到可见的瞬间
    private boolean isFragmentVisible = false;

    private View rootView = null;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(getT(),"onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(getRes(),container,false);
        }

        initView(rootView);
        isViewCreated = true;
        //当前fragment为可见状态 , 主动去调用setUserVisibleHint)去加载数据
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getT(),"onResume");
        if (getUserVisibleHint() && !isFragmentVisible) {//不可见-》可见
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(getT(),"onPause");
        if (isFragmentVisible && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    /**
     * fragment是否可见  问题① setUserVisibleHint优先于声明周期函数调用，所以在处理不可见fragment内的控件更新时是会报nullPointException，因为还没
     *                          有执行声明周期函数，控件没有被初始化，所以在控制数据加载和停止加载的前提是 界面已经被创建isViewCreated
     *                  问题② 当解决问题①之后，dispatchUserVisibleHint就一直不会被调用了，原因还是因为setUserVisibleHint在生命周期之前调用的，等到
     *                          界面创建和初始化后，没有调用dispatchUserVisibleHint，只有主动调用dispatchUserVisibleHint 才会去根据可见、不可见状态来
     *                          处理数据的加载和停止加载
     *                  问题③  精确数据加载和停止加载的时机，不是 fragment被setUserVisibleHint可见或不可见就触发数据加载或停止加载，而是有不可见-》可见、
     *                          可见-》不可见的这个过程，才去触发数据的加载或停止加载，  减少无用的重复的数据处理
     *                  问题④  当Activity的声明周期发生变化时 onPause<=>onResume,Fragment的onResume和onPause也会被调用，这时没有进行数据处理， 所以在fragment中的onResume 和 onPause 中
     *                          主动触发数据处理
     *
     *
     * fragment在切换时， 划出缓存区的（startPos,endPos）fragment会被销毁onDestroyView，划入缓存区的fragment会被创建onCreateView
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(getT(),"setUserVisibleHint:" + isVisibleToUser);
        if (isViewCreated) {//当fragment被创建后，再处理可见和不可见切换
            if (isVisibleToUser && !isFragmentVisible) {//fragment从不可见-》可见，才去加载数据
                dispatchUserVisibleHint(true);
            } else if (isFragmentVisible && !isVisibleToUser) {//fragment可见-》不可见，才去停止加载
                dispatchUserVisibleHint(false);
            }
        }


    }

    /**
     * 通过判断可见不可见状态，来控制fragment是否加载数据
     * @param isVisible
     */
    private void dispatchUserVisibleHint(boolean isVisible) {
        Log.e(getT(),"dispatchUserVisibleHint:" + isVisible);
        isFragmentVisible = isVisible;
        if (isVisible) {
            onFragmentLoad();
        } else  {
            onFragmentLoadStop();
        }
    }

    //抽象方法，子类都需要实现
    public abstract int getRes();

    //初始化控件
    public abstract void initView(View view);

    public abstract String getT();

    //子类按需实现
    public void onFragmentLoad(){

    }

    public void onFragmentLoadStop(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getT(),"onDestroyView");
    }
}
