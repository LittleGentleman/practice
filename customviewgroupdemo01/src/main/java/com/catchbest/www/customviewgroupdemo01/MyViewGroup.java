package com.catchbest.www.customviewgroupdemo01;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author:gmm
 * @date:2020/3/28
 * @类说明:
 */
public class MyViewGroup extends ViewGroup {
    public static final int OFFSET = 100;
    public static final int PADDING = 32;

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //得到自定义ViewGroup自身的MeasureSpecMode 和 MeasureSpecSize
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //根据ViewGroup的 MeasureSpec 和 子View的 LayoutParam 的宽高，确定所有子View的MeasureSpec
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //通过LayoutParam得到子View的width和height
            LayoutParams params = child.getLayoutParams();
            //根据父ViewGroup的MeasureSpec信息和子View自己的宽高，确定子View的 MeasureSpec
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, params.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, params.height);
            //测量子View
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }


        //所有的子View已测量完毕，现在轮到ViewGroup计算自身的宽高，需要根据ViewGroup的不同MeasureSpecMode 分别处理
        int width = 0;
        int height = 0;
        //计算ViewGroup的宽度
        switch (widthMeasureSpecMode) {
            case MeasureSpec.EXACTLY://android:layout_width = "100dp" /  android:layout_width = "match_parent"
                width = widthMeasureSpecSize;
                break;

            case MeasureSpec.AT_MOST://android:layout_width = "wrap_content"
            case MeasureSpec.UNSPECIFIED:
                //把所有子View中宽度最宽的作为ViewGroup的宽度
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    //为什么不使用getWidth()? 因为getWidth()是通过right-left来计算View的宽度的，由于left，right是在layout之后才会有值
                    //所以，在测量环节，此时left，top，right，bottom都是0，getWidth肯定也是0，所以需要通过getMeasuredWidth获取view的测量宽度
                    int offsetAndChildWidth = OFFSET * i + child.getMeasuredWidth() + PADDING*2;
                    width = Math.max(width, offsetAndChildWidth);
                }
                break;

            default:
                break;
        }

        //计算ViewGroup的高度
        switch (heightMeasureSpecMode) {
            case MeasureSpec.EXACTLY:
                height = heightMeasureSpecSize;
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                //所有子View的高度和 作为ViewGroup的高度
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    height = height + child.getMeasuredHeight() + PADDING;
                }
                height+=PADDING;
                break;

            default:
                break;
        }

        //把计算后的宽高，重新设置给ViewGroup
        setMeasuredDimension(width,height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = 0;
        int top = PADDING;
        int right = 0;
        int bottom = 0;


        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            left = OFFSET*i + PADDING;
            right = left + child.getMeasuredWidth();
            bottom = top + child.getMeasuredHeight();
            child.layout(left,top,right,bottom);
            top = top + child.getMeasuredHeight() + PADDING;
        }
    }
}
