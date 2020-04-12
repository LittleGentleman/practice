package com.demo.www.matrixdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/4
 * @类说明: Canvas 的保存save和回滚restore
 */
public class CanvasStackView extends View {
    private Paint mPaint;

    public CanvasStackView(Context context) {
        super(context);
        init(context);
    }

    public CanvasStackView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasStackView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.RED);

        //保存当前画布大小，即整屏
        canvas.save();

        //切割画布
        canvas.clipRect(100,100,800,800);
        canvas.drawColor(Color.GREEN);
        //还是以原画布的坐标系为坐标系，但是只会在切割出来的画布上绘制
//        canvas.drawCircle(100,100,100,mPaint);

        //恢复整屏画布
        canvas.restore();
        canvas.drawColor(Color.CYAN);

        //------------------------------------------------------
        canvas.drawColor(Color.RED);

        //保存当前画布大小为全屏幕大小
        canvas.save();

        //切割画布
        canvas.clipRect(100,100,800,800);
        canvas.drawColor(Color.GREEN);
        //保存当前切割画布，大小为（100，100，800，800）
        canvas.save();

        //再切割画布
        canvas.clipRect(200,200,700,700);
        canvas.drawColor(Color.BLUE);
        //保存当前切割画布大小为（200，200，700，700）
        canvas.save();

        //切割画布
        canvas.clipRect(300,300,600,600);
        canvas.drawColor(Color.BLACK);
        //保存当前切割画布大小为（300，300，600，600）
        canvas.save();

        //切割画布
        canvas.clipRect(400,400,500,500);
        canvas.drawColor(Color.WHITE);
        //保存当前切割画布大小为（400，400，500，500）
        canvas.save();

        //回滚最后一次保存的画布
        canvas.restore();
        //回滚到时第二次保存的画布
        canvas.restore();
        canvas.restore();
        canvas.restore();
        //回滚第一次保存的画布
        canvas.restore();
        canvas.drawColor(Color.YELLOW);
    }
}
