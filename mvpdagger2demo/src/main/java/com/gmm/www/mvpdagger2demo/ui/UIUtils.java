package com.gmm.www.mvpdagger2demo.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
public class UIUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment,int frameId){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId,fragment);
        transaction.commit();
    }
}
