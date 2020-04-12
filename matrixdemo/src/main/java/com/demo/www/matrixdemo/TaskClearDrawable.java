package com.demo.www.matrixdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.animation.AnimationUtils;

/**
 * @author:gmm
 * @date:2020/4/7
 * @类说明:
 */
public class TaskClearDrawable extends Drawable {
    //animator state
    private final int STATE_ORIGIN = 0;//初始状态
    private final int STATE_ROTATE = 1;//外圈旋转
    private final int STATE_UP = 2;//上移
    private final int STATE_DOWN = 3;//下移
    private final int STATE_FINISH = 4;//结束

    //animator duration time
    private final long DURATION_ROTATE = 1250;//外圈旋转时长
    private final long DURATION_CLEANING = 250;//X 缩小至 0的时长
    private final long DURATION_POINT_UP = 250;//点 往上移动的时长
    private final long DURATION_POINT_DOWN = 350;//点 往下移动的时长
    private final long DURATION_FINISH = 200;//短边缩放的时长
    private final long DURATION_CLEANING_DELAY = 1000;//cleaning 时长
    private final long DURATION_ORIGIN_DELAY = 3000;//返回初始状态时长

    private final float PI_DEGREE = (float) (180.0 / Math.PI);//180度/π是1弧度对应多少度,这里表示一弧度的大小(57.30)
    private final float DRAWABLE_WIDTH = 180.0f;//drawable_width 宽度
    private final float ROTATE_DEGREE_TOTAL = -1080.0f;//总共逆时针旋转1080度 即旋转3圈 6π

    private final float PAINT_WIDHT = 4.0f;//画X的笔的宽度
    private final float CROSS_LENGTH = 62.0f;//X 边 的长度
    private final float CROSS_DEGREE = 45.0f / PI_DEGREE;// π/4
    private final float UP_DISTANCE = 24.0f;// 往上移的距离
    private final float DOWN_DISTANCE = 20.0f;// 往下移的距离
    private final float FORK_LEFT_LEN = 33.0f;// 勾 左短边的长度
    private final float FORK_LEFT_DEGREE = 40.0f / PI_DEGREE;//勾 左短边弧度
    private final float FORK_RIGHT_LEN = 50.0f;//勾 右长边长度
    private final float FORK_RIGHT_DEGREE = 50.0f / PI_DEGREE;//勾 右长边弧度
    private final float CIRCLE_RADIUS = 3.0f;//圆点半径

    private int mWidth;
    private int mHeight;
    private int mAnimateState = STATE_ORIGIN;
    private float mCleaningScale, mRotateDegreeScale;//cleaning 缩放，旋转比例
    private float mScale = 0.0f;
    private float mPaintWidth;//画笔宽度
    private float mViewScale;
    private float mCenterX, mCenterY;
    private float mCrossLen, oldCrossLen;
    private float mPointRadius;
    private float mForkLeftLen, mForkRightLen;
    private float mPointUpLen, mPointDownLen;

    private Paint mPaint;
    private Bitmap mBgBitmap;
    private Bitmap mCircileBitmap;
    private TimeInterpolator fast_out_slow_in;
    private TimeInterpolator fast_out_linear_in;
    private AnimatorSet mAnimatorSet;
    private Matrix mMatrix = new Matrix();

    public TaskClearDrawable(Context context, int width, int height) {
        super();
        init(context, width, height);
    }

    private void init(Context context, int width, int height) {
        mWidth = width;
        mHeight = height;
        mPaint = new Paint();

        Bitmap tempCircleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle);
        Bitmap tempBgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);

        mCircileBitmap = Bitmap.createScaledBitmap(tempCircleBitmap, mWidth, mHeight, true);
        mBgBitmap = Bitmap.createScaledBitmap(tempBgBitmap, mWidth, mHeight, true);

        mViewScale = mWidth / DRAWABLE_WIDTH;

        if (mCircileBitmap != tempCircleBitmap) {
            tempBgBitmap.recycle();
        }

        if (mBgBitmap != tempBgBitmap) {
            tempBgBitmap.recycle();
        }

        mCenterX = mWidth / 2.0f;
        mCenterY = mHeight / 2.0f;
        mPaintWidth = PAINT_WIDHT * mViewScale;
        mCrossLen = CROSS_LENGTH * mViewScale;
        mPointRadius = CIRCLE_RADIUS * mViewScale;
        mForkLeftLen = FORK_LEFT_LEN * mViewScale;
        mForkRightLen = FORK_RIGHT_LEN * mViewScale;
        mPointUpLen = UP_DISTANCE * mViewScale;
        mPointDownLen = DOWN_DISTANCE * mViewScale;

        mCleaningScale = 1.0f;
        mRotateDegreeScale = 0.0f;

        mAnimatorSet = new AnimatorSet();
        fast_out_slow_in = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in);
        fast_out_linear_in = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_linear_in);

    }


    @Override
    public void draw(Canvas canvas) {
        float x1, y1, x2, y2, x3, y3, x4, y4;
        float length;//X 的长度
        float sin_45 = (float) Math.sin(CROSS_DEGREE);
        float cos_40 = (float) Math.cos(FORK_LEFT_DEGREE);
        float sin_40 = (float) Math.sin(FORK_LEFT_DEGREE);

        float cos_50 = (float) Math.cos(FORK_RIGHT_DEGREE);
        float sin_50 = (float) Math.sin(FORK_RIGHT_DEGREE);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //绘制背景
        canvas.drawBitmap(mBgBitmap, 0, 0, mPaint);

        //根据五种不同的状态绘制
        switch (mAnimateState) {
            case STATE_ORIGIN://绘制mCircleBitmap 绘制叉
                length = mCrossLen*sin_45/2.0f;
                x1 = mCenterX - length;
                y1 = mCenterY - length;
                x2 = mCenterX + length;
                y2 = mCenterY + length;

                x3 = mCenterX + length;
                y3 = mCenterY - length;
                x4 = mCenterX - length;
                y4 = mCenterY + length;

                drawPath(canvas,mPaint,x1,y1,x2,y2,x3,y3,x4,y4);//画叉
                canvas.drawBitmap(mCircileBitmap,0,0,mPaint);//画圆圈
                break;

            case STATE_ROTATE://旋转 matrix
                float degree = ROTATE_DEGREE_TOTAL*mRotateDegreeScale;
                mMatrix.setRotate(degree,mCenterX,mCenterY);
                canvas.drawBitmap(mCircileBitmap,mMatrix,mPaint);

                length = mCrossLen*mCleaningScale*sin_45/2.0f;
                x1 = mCenterX - length;
                y1 = mCenterY - length;
                x2 = mCenterX + length;
                y2 = mCenterY + length;

                x3 = mCenterX + length;
                y3 = mCenterY - length;
                x4 = mCenterX - length;
                y4 = mCenterY + length;

                drawPath(canvas,mPaint,x1,y1,x2,y2,x3,y3,x4,y4);//画叉
                break;

            case STATE_UP:
                mPaint.setStyle(Paint.Style.FILL);
                float upLen = mPointUpLen*mScale;
                canvas.drawCircle(mCenterX,mCenterY-upLen,mPointRadius,mPaint);
                canvas.drawBitmap(mCircileBitmap,0,0,mPaint);
                break;

            case STATE_DOWN:
                mPaint.setStyle(Paint.Style.FILL);
                float downLen = (mPointDownLen + mPointUpLen)*mScale;
                canvas.drawCircle(mCenterX,mCenterY-mPointUpLen+downLen,mPointRadius,mPaint);
                canvas.drawBitmap(mCircileBitmap,0,0,mPaint);
                break;

            case STATE_FINISH://画勾
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(mPaintWidth);

                x1 = mCenterX - Math.abs(mForkLeftLen*mScale*cos_40);
                y1 = mCenterY + mPointDownLen - Math.abs(mForkLeftLen*mScale*sin_40);
                x2 = mCenterX;
                y2 = mCenterY + mPointDownLen;
                x3 = mCenterX;
                y3 = mCenterY + mPointDownLen;
                x4 = mCenterX + Math.abs(mForkRightLen*mScale*cos_50);
                y4 = mCenterY + mPointDownLen - Math.abs(mForkRightLen*mScale*sin_50);

                drawPath(canvas,mPaint,x1,y1,x2,y2,x3,y3,x4,y4);//画勾
                canvas.drawBitmap(mCircileBitmap,0,0,mPaint);
                break;

            default:
                break;
        }

    }

    private void drawPath(Canvas canvas,Paint paint,float x1,float y1,float x2,float y2
            ,float x3,float y3,float x4,float y4) {
        Path path = new Path();
        path.moveTo(x1,y1);
        path.lineTo(x2,y2);

        path.moveTo(x3,y3);
        path.lineTo(x4,y4);

        canvas.drawPath(path,paint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    public boolean isRunning() {
        if (null != mAnimatorSet) {
            return mAnimatorSet.isRunning();
        } else {
            return false;
        }
    }

    public void start() {
        if (mAnimatorSet.isStarted() || mAnimatorSet.isRunning()) {
            return;
        }
        mAnimatorSet = new AnimatorSet();

        ValueAnimator valueAnimator01 = ValueAnimator.ofFloat(0.0f,1.0f);
        valueAnimator01.setDuration(1250);
        valueAnimator01.setInterpolator(fast_out_slow_in);
        valueAnimator01.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimateState = STATE_ROTATE;
                mRotateDegreeScale = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });

        ValueAnimator valueAnimator02 = ValueAnimator.ofFloat(1.0f,0.0f);
        valueAnimator02.setDuration(250);
        valueAnimator02.setStartDelay(1000);
        valueAnimator02.setInterpolator(fast_out_linear_in);
        valueAnimator02.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimateState = STATE_ROTATE;
                mCleaningScale = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });

        ValueAnimator valueAnimator03 = ValueAnimator.ofFloat(0,1);
        valueAnimator03.setDuration(250);
        valueAnimator03.setStartDelay(1250);
        valueAnimator03.setInterpolator(fast_out_slow_in);
        valueAnimator03.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimateState = STATE_UP;
                mScale = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });

        ValueAnimator valueAnimator04 = ValueAnimator.ofFloat(0,1);
        valueAnimator04.setDuration(350);
        valueAnimator04.setStartDelay(1500);
        valueAnimator04.setInterpolator(fast_out_slow_in);
        valueAnimator04.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimateState = STATE_DOWN;
                mScale = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });

        ValueAnimator valueAnimator05 = ValueAnimator.ofFloat(0,1);
        valueAnimator05.setDuration(200);
        valueAnimator05.setStartDelay(1850);
        valueAnimator05.setInterpolator(fast_out_slow_in);
        valueAnimator05.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimateState = STATE_FINISH;
                mScale = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });

        ValueAnimator valueAnimator06 = ValueAnimator.ofFloat(0,1);
        valueAnimator06.setStartDelay(DURATION_ORIGIN_DELAY);
        valueAnimator06.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimateState = STATE_ORIGIN;
                mRotateDegreeScale = 0;
                mCleaningScale = 1.0f;
                mScale = 0;
                invalidateSelf();
            }
        });

        mAnimatorSet.playTogether(valueAnimator01,valueAnimator02
                ,valueAnimator03,valueAnimator04,valueAnimator05,
                valueAnimator06);
        mAnimatorSet.start();
    }
}
