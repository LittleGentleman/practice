package com.gmm.www.mvpdemo03.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author:gmm
 * @date:2020/3/14
 * @类说明:
 */
public class UIUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment,int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId,fragment);
        transaction.commit();
    }
}
