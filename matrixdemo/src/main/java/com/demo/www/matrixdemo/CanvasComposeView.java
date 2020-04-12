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
 * @类说明:Canvas合成  画布是一层一层的
 */
public class CanvasComposeView extends View {
    private Paint mRedPaint;
    private Paint mGreenPaint;
    public CanvasComposeView(Context context) {
        super(context);
        init(context);
    }

    public CanvasComposeView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasComposeView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRedPaint = new Paint();
        mRedPaint.setAntiAlias(true);
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStyle(Paint.Style.STROKE);
        mRedPaint.setStrokeWidth(10);

        mGreenPaint = new Paint();
        mGreenPaint.setAntiAlias(true);
        mGreenPaint.setColor(Color.GREEN);
        mGreenPaint.setStyle(Paint.Style.STROKE);
        mGreenPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //在平移画布前用绿色画笔画下边框
        canvas.drawRect(0,0,400,220,mGreenPaint);

        //画布移动，不会影响之前绘制内容的位置
        canvas.translate(100,100);
        //平移画布后，再用红色画笔重新画下边框
        canvas.drawRect(0,0,400,220,mRedPaint);
    }
}
