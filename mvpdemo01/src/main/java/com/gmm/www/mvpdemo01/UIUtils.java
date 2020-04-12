package com.gmm.www.mvpdemo01;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import io.reactivex.annotations.NonNull;

/**
 * @author:gmm
 * @date:2020/3/11
 * @类说明:
 */
public class UIUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId){
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启事务
        transaction.replace(frameId,fragment);
        transaction.commit();//提交事务
    }
}
