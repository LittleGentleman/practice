package com.gmm.flowlayoutdemo;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private int mHorizontalSpacing = dp2px(16);
    private int mVerticalSpacing = dp2px(8);

    //ViewGroup内的所有行
    private List<List<View>> allLines;
    //每一行的行高
    private List<Integer> heightList;

    public FlowLayout(Context context) {
        super(context);
    }

    //必须实现这个构造器函数
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //widthMeasureSpec,heightMeasureSpec：父View传过来的
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        allLines = new ArrayList<>();
        heightList = new ArrayList<>();

        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //每一行的子View
        List<View> viewRows = new ArrayList<>();

        //通过MeasureSpec获取ViewGroup的width
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

        int lineWidthUsed = 0;
        int lineHeight = 0;

        int parentNeedWidth = 0;
        int parentNeedHeight = 0;

        for (int i=0;i<childCount;i++) {
            View childView = getChildAt(i);

            LayoutParams layoutParams = childView.getLayoutParams();
            //根据父View的Spec模式，来确定子View的模式尺寸  子View和父viewGroup商量买房子的事（尺寸） 构建子View的MeasureSpec
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,paddingLeft+paddingRight,layoutParams.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,paddingTop+paddingBottom,layoutParams.height);
            //设置子View的尺寸  只有measure()测量后，getMeasuredWidth()才会生效
            childView.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            //考虑一行放不下的问题
            int childMeasureWidth = childView.getMeasuredWidth();
            int childMeasureHeight = childView.getMeasuredHeight();
            if (lineWidthUsed+childMeasureWidth+mHorizontalSpacing > selfWidth){
                //记录每一行
                allLines.add(viewRows);
                //记录每一行高
                heightList.add(lineHeight);

                //取最宽的一行的宽度作为ViewGroup需要的宽度尺寸
                parentNeedWidth = Math.max(parentNeedWidth,lineWidthUsed);
                parentNeedHeight = parentNeedHeight + lineHeight + mVerticalSpacing;

                //清空viewRows，用于存储新的一行内的ziView
                viewRows = new ArrayList<>();
                //清零lineWidthUsed，准备记录下一行子View的宽度和
                lineWidthUsed = 0;
                //清零lineHeight,准备记录下一行的最高高度
                lineHeight = 0;
            }

            viewRows.add(childView);
            //一行的宽度
            lineWidthUsed = lineWidthUsed + childMeasureWidth + mHorizontalSpacing;
            //一行的高度
            lineHeight = Math.max(lineHeight,childMeasureHeight);
        }

        //最后一行
        allLines.add(viewRows);
        //最后一行
        heightList.add(lineHeight);
        //取最宽的一行的宽度作为ViewGroup需要的宽度尺寸
        parentNeedWidth = Math.max(parentNeedWidth,lineWidthUsed);
        parentNeedHeight = parentNeedHeight + lineHeight + mVerticalSpacing;

        //获取ViewGroup的测量模式 如果在xml指定的是一个确定宽高或者match_parent，那模式就是EXACTLY，测量宽高就是指定的值，如果是wrap_content，那模式是AT_MOST，测量宽高就是由子View测量出来的
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode==MeasureSpec.EXACTLY)?selfWidth:parentNeedWidth;
        int realHeight = (heightMode==MeasureSpec.EXACTLY)?selfHeight:parentNeedHeight;

        setMeasuredDimension(realWidth,realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int rowCount = allLines.size();
        int curL = getLeft();
        int curT = 0;
        //一行一行的为子View布局
        for (int i=0;i<rowCount;i++) {
            List<View> views = allLines.get(i);

            for (int j=0;j<views.size();j++) {
                View view = views.get(j);
                int left = curL;
                int top = curT;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();

                view.layout(left,top,right,bottom);

                curL = right + mHorizontalSpacing;
            }

            //初始化下一行的起始坐标
            curT = curT + heightList.get(i) + mVerticalSpacing;
            curL = getLeft();
        }
    }

    public static int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,Resources.getSystem().getDisplayMetrics());
    }
}
