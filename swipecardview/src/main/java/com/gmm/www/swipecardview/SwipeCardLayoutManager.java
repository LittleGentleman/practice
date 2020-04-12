package com.gmm.www.swipecardview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import static com.gmm.www.swipecardview.CardConfig.MAX_SHOW_COUNT;
import static com.gmm.www.swipecardview.CardConfig.SCALE_GAP;
import static com.gmm.www.swipecardview.CardConfig.TRANS_Y_GAP;

/**
 * @author:gmm
 * @date:2020/4/10
 * @类说明:
 */
public class SwipeCardLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new  RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT
                ,RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        super.onLayoutChildren(recycler, state);
        //回收
        detachAndScrapAttachedViews(recycler);

        //假设 item 的数量 为8
        int itemCount = getItemCount();
        //肉眼能展示看到的item个数是3个，但是其实会隐藏一个item，所有提前准备4个item
        //由于最上面的是最后一个item，所以这4个item对应的索引是[4,5,6,7]这4个叠加放在界面上
        //所以初始状态是，最上面的是7，最下面的是4 （注：考虑非正常状态）
        int bottomPosition;

        if (itemCount < MAX_SHOW_COUNT) {//如果item的个数小于设置的最大数，那么bottomPosition就是第一个item的位置，也就是0
            bottomPosition = 0;
        } else {
            bottomPosition = itemCount-MAX_SHOW_COUNT;
        }

        //从bottomPosition开始，一层叠一层的布局，布局后，最后一个item在最上层
        for (int i = bottomPosition; i < itemCount; i++) {
            //复用
            View view = recycler.getViewForPosition(i);
            addView(view);

            //测量child
            measureChildWithMargins(view,0,0);

            //计算padding大小
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

            //布局子View
            layoutDecoratedWithMargins(view,
                                widthSpace/2,
                                heightSpace/2,
                                widthSpace/2+getDecoratedMeasuredWidth(view),
                                heightSpace/2+getDecoratedMeasuredHeight(view));

            //定义层级，最上面的显示的最完整（不用处理），后面的会依次缩小一点点，最后一层看不到
            int level = itemCount - i - 1;

            if (level > 0) {//对第一层后面的item进行层缩放和平移操作
                if (level < MAX_SHOW_COUNT-1) {//这是除了顶层和最底层，肉眼可看到的item
                    //level越大，缩放比例越大，平移距离越大
                    view.setTranslationY(level*TRANS_Y_GAP);
                    view.setScaleX(1-level*SCALE_GAP);
                    view.setScaleY(1-level*SCALE_GAP);
                } else {//最底层 与 倒数第二层 重叠即可
                    view.setTranslationY((level-1)*TRANS_Y_GAP);
                    view.setScaleX((1-(level-1)*SCALE_GAP));
                    view.setScaleY(1-(level-1)*SCALE_GAP);
                }
            }
        }
    }
}
