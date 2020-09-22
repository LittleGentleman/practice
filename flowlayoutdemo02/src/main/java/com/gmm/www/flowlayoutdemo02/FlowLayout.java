package com.gmm.www.flowlayoutdemo02;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:gmm
 * @date:2020/3/30
 * @类说明:
 */
public class FlowLayout extends ViewGroup {
    private List<List<View>> allViews;//所有行的view
    private List<View> lineViews;//一行view
    private List<Integer> lineHeights;//每行的行高

    private float mLastInterceptX = 0;
    private float mLastInterceptY = 0;
    private int mTouchSlop;//dy>mTouchSlop 拦截捕获事件
    private boolean scrollable = false;//如果内容高度没有超出ViewGroup的高度，则不需要滚动
    private float mLastY = 0;

    private int measureHeight;//ViewGroup的高度
    private int realHeight;//所有子view的高度

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;//滑动速度跟踪器
    private int mMinimumVelocity;
    private int mMaximimVelocity;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mScroller = new OverScroller(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximimVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    private void init() {
        allViews = new ArrayList<>();
        lineViews = new ArrayList<>();
        lineHeights = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //FlowLayout的父容器给定的测量模式和尺寸
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureSpecModel = MeasureSpec.getMode(heightMeasureSpec);

        measureHeight = heightMeasureSpecSize;//如果mode是EXACTLY,高度就是dp或match_parent,如果是mode是AT_MOST，高度最高不会超过父容器的高度

        //如果FlowLayout的MeasureSpec的model是非EXACTLY，就需要通过所有子View的尺寸计算宽高，在设置给FlowLayout
        int flowLayoutWidth = 0;
        int flowLayoutHeight = 0;

        //每一行view的宽度和
        int lineWidth = 0;
        //每一行的高度
        int lineHeight = 0;

        //由于会多次测量，所以集合的初始化放在onMeasure内，如果放在构造器里，那么每次重新测量，集合都会持续增加
        init();

        //测量子View
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams layoutParams = child.getLayoutParams();

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //超出一行，进行换行
            if (lineWidth + childWidth > widthMeasureSpecSize) {
                if (lineViews.size() == 1 && lineViews.get(0).getLayoutParams().height == LayoutParams.MATCH_PARENT) {//如果一行只有一个子View，且子View的height为MatchParent
                    lineHeight = Utils.dp2px(150);
                }
                allViews.add(lineViews);
                lineHeights.add(lineHeight);
                flowLayoutWidth = Math.max(flowLayoutWidth, lineWidth);
                flowLayoutHeight += lineHeight;
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = 0;
            }
            lineViews.add(child);
            lineWidth += childWidth;
            if (layoutParams.height != LayoutParams.MATCH_PARENT) {//如果子View的高度是matchParent,则不取此高度作为行高
                lineHeight = Math.max(lineHeight, childHeight);
            }


            if (i == childCount - 1) {//处理最后一行
                allViews.add(lineViews);
                lineHeights.add(lineHeight);
                flowLayoutWidth = Math.max(flowLayoutWidth, lineWidth);
                flowLayoutHeight += lineHeight;
            }
        }

        //如果子view的layout_height = match_parent，需要重新测量，给这个view设置一个常规高度
        for (int i = 0; i < lineHeights.size(); i++) {
            int rowHeight = lineHeights.get(i);
            List<View> views = allViews.get(i);
            for (int j = 0; j < views.size(); j++) {
                View child = views.get(j);
                LayoutParams layoutParams = child.getLayoutParams();

                if (layoutParams.height == LayoutParams.MATCH_PARENT) {//如果子View的height为MatchParent，重新给这个View设置高度，高度则为这行的行高
                    int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, child.getMeasuredWidth());
                    int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, rowHeight);
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                }
            }
        }

//        if (heightMeasureSpecModel == MeasureSpec.EXACTLY) {
//            flowLayoutHeight = Math.max(flowLayoutHeight,heightMeasureSpecSize);
//        }

        realHeight = flowLayoutHeight;
        scrollable = realHeight > measureHeight;

        //如果FlowLayout的限制模式是EXACTLY，那就使用父容器规定的尺寸，如果是非EXACTLY，就使用根据子View计算出来的尺寸
        setMeasuredDimension(widthMeasureSpecMode == MeasureSpec.EXACTLY ? widthMeasureSpecSize : flowLayoutWidth,
                heightMeasureSpecModel == MeasureSpec.EXACTLY ? heightMeasureSpecSize : flowLayoutHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lineCount = lineHeights.size();
        //当前layout的位置
        int curX = 0;
        int curY = 0;

        for (int i = 0; i < lineCount; i++) {
            List<View> views = allViews.get(i);
            int size = views.size();
            int lineHeight = lineHeights.get(i);

            for (int j = 0; j < size; j++) {
                View child = views.get(j);

                int left = curX;
                int top = curY;
                int right = left + child.getMeasuredWidth();
                int bottom = curY + child.getMeasuredHeight();

                child.layout(left, top, right, bottom);

                curX += child.getMeasuredWidth();
            }
            curX = 0;
            curY += lineHeight;
        }

    }


    @Override
    public boolean  onInterceptTouchEvent(MotionEvent ev) {

        boolean intercept = false;
        Log.e("TAG", "onInterceptTouchEvent: " + "ACTION_START");
        //touch事件的坐标。getX()是以当前View作为坐标系的坐标，getRawX()是以屏幕为原始坐标系的坐标
        float curInterceptX = ev.getX();
        float curInterceptY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG", "onInterceptTouchEvent: " + "ACTION_DOWN");
                mLastInterceptX = curInterceptX;
                mLastInterceptY = curInterceptY;
                intercept = false;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e("TAG", "onInterceptTouchEvent: " + "ACTION_MOVE");
                float dx = curInterceptX - mLastInterceptX;
                float dy = curInterceptY - mLastInterceptY;
                Log.e("TAG", "Math.abs(dy): "+Math.abs(dy)+",Math.abs(dx): "+Math.abs(dx) +",mTouchSlop: "+mTouchSlop );
                if (Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > mTouchSlop) {
                    Log.e("TAG", "onInterceptTouchEvent: " + "ACTION_MOVE");
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                Log.e("TAG", "onInterceptTouchEvent: " + "ACTION_UP");
                intercept = false;
                break;
        }
        Log.e("TAG", "onInterceptTouchEvent: " + "ACTION_END");
        mLastInterceptX = curInterceptX;
        mLastInterceptY = curInterceptY;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!scrollable) {
            return super.onTouchEvent(event);
        }
        Log.e("TAG", "onTouchEvent: " + "ACTION_START" );
        initVelocityTrackerIfNotExists();//初始化滑动速度跟踪器
        mVelocityTracker.addMovement(event);//把事件添加到跟踪器里
        float curY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {//down事件发生时，如果scroller还在滑动，终止滑动
                    mScroller.abortAnimation();
                }
                Log.e("TAG", "onTouchEvent: " + "ACTION_DOWN" );
                mLastY = curY;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e("TAG", "onTouchEvent: " + "ACTION_MOVE" );
                float dy = mLastY - curY;//使用scrollTo做滚动，接收的正数是向上滑，负数向下滑
//                int oldScrollY = getScrollY();//已经偏移的距离
//                int scrollY = (int) (oldScrollY + dy);
//                if (scrollY < 0) {
//                    scrollY = 0;
//                }
//                if (scrollY > realHeight - measureHeight) {
//                    scrollY = realHeight - measureHeight;
//                }
//                scrollTo(0,scrollY);//滑动到指定的位置
                mScroller.startScroll(0,mScroller.getFinalY(),0, (int) dy);
                invalidate();//手动刷新重新绘制 invalidate是在UI线程  而postInvalidate是在非UI线程，内部会通过handler再切换到UI线程
                mLastY = curY;
                break;

            case MotionEvent.ACTION_UP:
                Log.e("TAG", "onTouchEvent: " + "ACTION_UP" );
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000,mMaximimVelocity);
                int initialVelocity = (int) velocityTracker.getYVelocity();

                if (Math.abs(initialVelocity) > mMinimumVelocity) {
                    fling(-initialVelocity);
                } else if (mScroller.springBack(getScrollX(),getScrollY(),0,0,0,
                        (realHeight-measureHeight))) {//设置界限
                    postInvalidateOnAnimation();
                }
                break;
        }

        Log.e("TAG", "onTouchEvent: " + "ACTION_END" );

        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0,mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    public void fling(int velocityY) {
        if (getChildCount() > 0) {
            int height = measureHeight;
            int bottom = realHeight;

            mScroller.fling(getScrollX(),getScrollY(),0,velocityY,0,0,0,
                    Math.max(0,bottom-height),0,height/2);

            postInvalidateOnAnimation();
        }
    }


}
