package com.gmm.www.swipecardview;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.gmm.www.swipecardview.adapter.UniversalAdapter;

import java.util.List;

/**
 * @author:gmm
 * @date:2020/4/10
 * @类说明:
 */
public class SwipeCardCallback extends ItemTouchHelper.SimpleCallback {
    private RecyclerView mRecyclerView;
    private UniversalAdapter<SwipeCardBean> adapter;
    private List<SwipeCardBean> data;


    public SwipeCardCallback(RecyclerView recyclerView,
                             UniversalAdapter<SwipeCardBean> adapter,
                             List<SwipeCardBean> data) {
        super(0,ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                                    ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
        this.mRecyclerView = recyclerView;
        this.adapter = adapter;
        this.data = data;
    }

    /**
     * drag
     * @param recyclerView
     * @param viewHolder
     * @param viewHolder1
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    /**
     * item 滑出去的回调
     * @param viewHolder
     * @param i
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SwipeCardBean bean = data.remove(viewHolder.getLayoutPosition());//最上面的
        data.add(0,bean);
        adapter.notifyDataSetChanged();
    }

    /**
     * 绘制
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        double maxDistance = recyclerView.getWidth()*0.5f;
        double distance = Math.sqrt(dX*dX+dY*dY);
        double fraction = distance / maxDistance;

        if (fraction > 1) {
            fraction = 1;
        }

        //显示的个数 4个
        int itemCount = recyclerView.getChildCount();

        for (int i = 0; i < itemCount; i++) {
            View view = recyclerView.getChildAt(i);

            int level = itemCount - i - 1;

            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT-1) {
                    //最多上升一级  在原来的基础上会向上移动fraction*CardConfig.TRANS_Y_GAP这些距离
                    view.setTranslationY((float) (level*CardConfig.TRANS_Y_GAP - fraction*CardConfig.TRANS_Y_GAP));
                    //在原来的基础上会放大fraction*CardConfig.SCALE_GAP
                    view.setScaleX((float) (1-CardConfig.SCALE_GAP*level + fraction*CardConfig.SCALE_GAP));
                    view.setScaleY((float) (1-CardConfig.SCALE_GAP*level + fraction*CardConfig.SCALE_GAP));
                }
            }
        }
    }

//    /**
//     * 设置手指离开后ViewHolder的动画时间
//     * @param recyclerView
//     * @param animationType
//     * @param animateDx
//     * @param animateDy
//     * @return
//     */
//    @Override
//    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
//        return 1000;
//    }
//
//
//    /**
//     * 返回值滑动消失的距离, 这里是相对于RecycleView的宽度，0.5f表示为RecycleView的宽度的一半，取值为0~1f之间
//     * @param viewHolder
//     * @return
//     */
//    @Override
//    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
//        return 0.2f;
//    }
}
