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
 * @类说明:Canvas 变换操作 ：平移、缩放、旋转、错切
 */
public class CanvasTransformationView extends View {
    private Paint mPaint;

    public CanvasTransformationView(Context context) {
        super(context);
        init(context);
    }

    public CanvasTransformationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasTransformationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

        //Canvas平移变换
        canvas.translate(100,100);
        canvas.drawRect(0,0,200,200,mPaint);

        canvas.translate(0,300);
        //Canvas缩放变换
        canvas.scale(0.5f,0.5f);
        canvas.drawRect(0,0,200,200,mPaint);

        canvas.translate(0,300);
        //Canvas旋转变换
        canvas.rotate(30);
        canvas.drawRect(0,0,200,200,mPaint);

        canvas.translate(0,300);
        //Canvas错切变换
        canvas.skew(0.5f,0.5f);
        canvas.drawRect(0,0,200,200,mPaint);
    }
}
