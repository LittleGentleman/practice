package com.gmm.www.viewpagerwrap;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/13
 * @类说明:
 */
public class MyPageTransform implements ViewPager.PageTransformer {

    private static final float DEFAULT_MIN_ALPHA = 0.3f;
    private float minAlpha = DEFAULT_MIN_ALPHA;

//    private static final float DEFAULT_MAX_ROTATE = 15.0f;
//    private float maxRotate = DEFAULT_MAX_ROTATE;

    private static final float DEFAULT_MIN_SCALE= 0.6f;
    private float minScale = DEFAULT_MIN_SCALE;

    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position < -1) {
            view.setAlpha(minAlpha);
//            view.setRotation(maxRotate*-1);
            view.setScaleX(minScale);
            view.setScaleY(minScale);
            view.setPivotX(view.getWidth());
            view.setPivotY(view.getHeight()*0.5f);
        } else if (position <= 1) {
            if (position < 0) {
                //position是0到-1的变化,p1+position就是从1到0的变化
                //(p1 - mMinAlpha) * (p1 + position)就是(p1 - mMinAlpha)到0的变化
                //再加上一个mMinAlpha，就变为1到mMinAlpha的变化。
                float factor = minAlpha + (1 - minAlpha)*(1 + position);
                view.setAlpha(factor);
                view.setScaleX(factor);
                view.setScaleY(factor);
//                view.setRotation(maxRotate*position);
                //position为width/2到width的变化
                view.setPivotX(view.getWidth()*0.5f*(1-position));
                view.setPivotY(view.getHeight()*0.5f);
            } else {
                //minAlpha到1的变化
                float factor = minAlpha + (1 - minAlpha)*(1 - position);
                view.setAlpha(factor);
                view.setScaleX(factor);
                view.setScaleY(factor);
//                view.setRotation(maxRotate*position);
                view.setPivotX(view.getWidth()*0.5f*(1-position));
                view.setPivotY(view.getHeight()*0.5f);
            }
        } else {
            view.setAlpha(minAlpha);
            view.setScaleX(minScale);
            view.setScaleY(minScale);
//            view.setRotation(maxRotate);
            view.setPivotX(0);
            view.setPivotY(view.getHeight()*0.5f);
        }
    }
}
