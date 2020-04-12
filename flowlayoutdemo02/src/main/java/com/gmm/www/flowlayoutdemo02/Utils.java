package com.gmm.www.flowlayoutdemo02;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author:gmm
 * @date:2020/3/31
 * @类说明:
 */
public class Utils {

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,Resources.getSystem().getDisplayMetrics());
    }
}
