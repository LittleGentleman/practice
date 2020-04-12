package com.demo.www.matrixdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/4
 * @类说明: 1  canvas基本图形的绘制
 */
public class CanvasDrawView extends View {
    private Paint mPaint;
    //density : 指屏幕上每平方英寸（2.54 ^ 2 平方厘米）中含有的像素点数量
    private float mDensity;

    public CanvasDrawView(Context context) {
        super(context);
    }

    public CanvasDrawView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasDrawView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mDensity = displayMetrics.density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //把整张画布绘制成白色
        canvas.drawColor(Color.WHITE);

        //设置Paint的方法
        //去锯齿
        mPaint.setAntiAlias(true);
        //画线
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔颜色
        mPaint.setColor(Color.GREEN);
        //设置画笔宽度
        mPaint.setStrokeWidth(15*mDensity);

        //绘制圆形
        canvas.drawCircle(40*mDensity,40*mDensity,30*mDensity,mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(1*mDensity);
        canvas.drawCircle(40*mDensity,40*mDensity,30*mDensity,mPaint);

        //绘制正方形
        canvas.drawRect(10*mDensity,80*mDensity,70*mDensity,140*mDensity,mPaint);

        //绘制矩形
        canvas.drawRect(10*mDensity,150*mDensity,70*mDensity,190*mDensity,mPaint);

        //绘制圆角矩形
        canvas.drawRoundRect(10*mDensity,200*mDensity,70*mDensity,230*mDensity,15*mDensity,15*mDensity,mPaint);

        //绘制椭圆
        canvas.drawOval(10*mDensity,240*mDensity,70*mDensity,270*mDensity,mPaint);

        //根据Path进行绘制，绘制三角形
        Path path = new Path();
        path.moveTo(10*mDensity,340*mDensity);
        path.lineTo(70*mDensity,340*mDensity);
        path.lineTo(40*mDensity,290*mDensity);
        //将Path的起点和终点 连接起来 使Path封闭
        path.close();
        canvas.drawPath(path,mPaint);

        //绘制五边形
        path.moveTo(25*mDensity,360*mDensity);
        path.lineTo(54*mDensity,360*mDensity);
        path.lineTo(70*mDensity,392*mDensity);
        path.lineTo(40*mDensity,420*mDensity);
        path.lineTo(10*mDensity,392*mDensity);
        path.close();
        canvas.drawPath(path,mPaint);

        //绘制圆弧
        canvas.drawRect(10*mDensity,430*mDensity,110*mDensity,530*mDensity,mPaint);
        canvas.drawArc(10*mDensity,430*mDensity,110*mDensity,530*mDensity,0,135,true,mPaint);


        //设置Paint填充风格   FILL填充风格，strokeWidth是无效的
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(15*mDensity);
        mPaint.setColor(Color.RED);

        //绘制圆形
        canvas.drawCircle(120*mDensity,40*mDensity,30*mDensity,mPaint);

        //绘制正方形
        canvas.drawRect(90*mDensity,80*mDensity,150*mDensity,140*mDensity,mPaint);

        //绘制矩形
        canvas.drawRect(90*mDensity,150*mDensity,150*mDensity,190*mDensity,mPaint);

        //绘制圆角矩形
        canvas.drawRoundRect(90*mDensity,200*mDensity,150*mDensity,230*mDensity,15*mDensity,15*mDensity,mPaint);

        //绘制椭圆
        canvas.drawOval(90*mDensity,240*mDensity,150*mDensity,270*mDensity,mPaint);

        //绘制三角形  这里创建一个新的Path，如果用同一个Path，之前的Path的画笔风格也会变成填充风格
        Path path2 = new Path();
        path2.moveTo(90*mDensity,340*mDensity);
        path2.lineTo(150*mDensity,340*mDensity);
        path2.lineTo(120*mDensity,290*mDensity);
        path2.close();
        canvas.drawPath(path2,mPaint);
//
//        //绘制五角形
        path2.moveTo(106*mDensity,360*mDensity);
        path2.lineTo(134*mDensity,360*mDensity);
        path2.lineTo(150*mDensity,392*mDensity);
        path2.lineTo(120*mDensity,420*mDensity);
        path2.lineTo(90*mDensity,392*mDensity);
        path2.close();
        canvas.drawPath(path2,mPaint);

        //---------------------设置渐变器后绘制------------------------
        //为Paint设置渐变
        Shader mShader = new LinearGradient(0,0,60*mDensity,60*mDensity,new int[]{
                Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},null,Shader.TileMode.REPEAT);

        //环形渐变
        mShader = new RadialGradient(200*mDensity,40*mDensity,30*mDensity,new int[]{
                Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},null,Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);

        // 绘制圆形
        canvas.drawCircle(200 * mDensity, 40 * mDensity, 30 * mDensity, mPaint);


        //扇形渐变
        mShader = new SweepGradient(200*mDensity,110*mDensity,new int[]{ Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},
                null);
        mPaint.setShader(mShader);

        //绘制正方形
        canvas.drawRect(170*mDensity,80*mDensity,230*mDensity,140*mDensity,mPaint);

        //线性渐变
        mShader = new LinearGradient(0,0,60*mDensity,60*mDensity,new int[]{
                Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},null,Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);

        //绘制矩形
        canvas.drawRect(170*mDensity,150*mDensity,230*mDensity,190*mDensity,mPaint);

        //绘制圆角矩形
        canvas.drawRoundRect(170*mDensity,200*mDensity,230*mDensity,230*mDensity,5*mDensity,5*mDensity,mPaint);

        //绘制椭圆
        canvas.drawOval(170*mDensity,240*mDensity,230*mDensity,270*mDensity,mPaint);

        Path path3 = new Path();
        path3.moveTo(170 * mDensity, 340 * mDensity);
        path3.lineTo(230 * mDensity, 340 * mDensity);
        path3.lineTo(200 * mDensity, 290 * mDensity);
        path3.close();
        canvas.drawPath(path3, mPaint);

        path3.moveTo(186 * mDensity, 360 * mDensity);
        path3.lineTo(214 * mDensity, 360 * mDensity);
        path3.lineTo(230 * mDensity, 392 * mDensity);
        path3.lineTo(200 * mDensity, 420 * mDensity);
        path3.lineTo(170 * mDensity, 392 * mDensity);
        path3.close();
        canvas.drawPath(path3, mPaint);



        //----------------------设置字符大小---------------------------
        mPaint.setTextSize(24*mDensity);
//        mPaint.setShader(null);

        //绘制7个字符串
        canvas.drawText("圆形",240*mDensity,50*mDensity,mPaint);
        canvas.drawText("正方形",240*mDensity,120*mDensity,mPaint);
        canvas.drawText("矩形",240*mDensity,175*mDensity,mPaint);
        canvas.drawText("圆角矩形",240*mDensity,220*mDensity,mPaint);
        canvas.drawText("椭圆",240*mDensity,260*mDensity,mPaint);
        canvas.drawText("三角形",240*mDensity,325*mDensity,mPaint);
        canvas.drawText("五边形",240*mDensity,390*mDensity,mPaint);
    }


}
