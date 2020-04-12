package com.demo.www.matrixdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/5
 * @类说明: Matrix 矩阵操作 (变换图片效果)
 */
public class MatrixView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private Matrix mMatrix;

    public MatrixView(Context context) {
        super(context);
        init(context);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.jie);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 定义矩阵对象
         */
        float[] values = {
                0.707f,-0.707f,300f,
                0.707f,0.707f ,300f,
                0.0f  ,0.0f   ,1.0f,
        };
        mMatrix.setValues(values);

//        mMatrix.reset();
        //set方法一旦调用即会清空之前matrix中的所有变换
//        mMatrix.setTranslate(200f,200f);
//        //顺时针为正，逆时针为负
//        mMatrix.setRotate(45);
//
//        mMatrix.setRotate(45,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//
//        mMatrix.setScale(2.0f,2.0f);
//
//        mMatrix.setScale(2.0f,2.0f,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//
//        mMatrix.setSkew(-0.707f,0.707f);
//
//        mMatrix.setSkew(0.5f,0.5f,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//
//        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
//
//
//
//        //先旋转后平移  post是在队列最后面插入  pre是在队列最前面插入
//        mMatrix.reset();
//        mMatrix.postRotate(45);
//        mMatrix.postTranslate(300,200);
//        mMatrix.reset();
//        mMatrix.preTranslate(300,200);
//        mMatrix.preRotate(45);
//        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
//
//        //先平移或旋转
//        mMatrix.reset();
//        mMatrix.postTranslate(300,200);
//        mMatrix.postRotate(45);
//        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
//
//        //平移-旋转-缩放-仿射
//        mMatrix.reset();
//        mMatrix.postTranslate(200,200);
//        mMatrix.postRotate(-45,300,300);
//        mMatrix.postScale(2.0f,2.0f,0,0);
//        mMatrix.postSkew(0.2f,0.2f,200,200);
//        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
//
//        mMatrix.reset();
//        mMatrix.preTranslate(300,500);
//        mMatrix.preRotate(45,0,0);
//        canvas.drawBitmap(mBitmap,mMatrix,mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(600,600,20,mPaint);

        mMatrix.reset();
        mMatrix.postTranslate(600,600);
        mMatrix.postRotate(30,300,600);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1,0,0,0,100,//R
                0,1,0,0,100,//G
                0,0,1,0,200,//B
                0,0,0,1,0,//A
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        mMatrix.reset();
        mMatrix.postRotate(30);
        mMatrix.postTranslate(300,600);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
    }
}
