package com.gmm.www.swipecardview.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author:gmm
 * @date:2020/4/9
 * @类说明:
 */
public interface OnItemClickListener<T> {

    void onItemClick(ViewGroup parent, View view,T data,int position);

    boolean onItemLongClick(ViewGroup parent,View view,T data,int position);
}
