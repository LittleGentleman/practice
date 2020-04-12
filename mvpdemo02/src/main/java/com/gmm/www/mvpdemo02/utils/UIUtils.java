package com.gmm.www.mvpdemo02.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author:gmm
 * @date:2020/3/12
 * @类说明:
 */
public class UIUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int containerId) {
        //获取事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId,fragment);
        //提交事务
        transaction.commit();
    }
}
