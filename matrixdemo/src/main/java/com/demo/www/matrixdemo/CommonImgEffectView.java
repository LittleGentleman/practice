package com.demo.www.matrixdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/8
 * @类说明: 使用矩阵控制图片移动、旋转、缩放
 */
public class CommonImgEffectView extends View {
    private Context context;
    private Bitmap mainBmp;
    private Bitmap controlBmp;
    private int mainBmpWidth, mainBmpHeight, controlBmpWidth, controlBmpHeight;
    private Matrix matrix;
    private float[] srcPs, dstPs;
    private RectF srcRect, dstRect;
    private Paint paint, paintRect, paintFrame;
    private float deltaX = 0, deltaY = 0;//位移值
    private float scaleValue = 1;//缩放值
    private Point lastPoint;
    private Point prePivot, lastPivot;
    private float preDegree, lastDegree;
    private short currentSelectedPointIndex;//当前操作点
    private Point symmeticPoint = new Point();//当前操作点的对称点

    //图片操作类型
    public static final int OPER_DEFAULT = -1;//默认
    public static final int OPER_TRANSLATE = 0;//移动
    public static final int OPER_SCALE = 1;//缩放
    public static final int OPER_ROTATE = 2;//旋转
    public static final int OPER_SELECTED = 3;//选择
    public int lastOper = OPER_DEFAULT;

    /**
     * 图片控制点
     * 0---1---2
     * |       |
     * 7   8   3
     * |       |
     * 6---5---4
     */
    public static final int CTR_NONE = -1;
    public static final int CTR_LEFT_TOP = 0;
    public static final int CTR_MID_TOP = 1;
    public static final int CTR_RIGHT_TOP = 2;
    public static final int CTR_RIGHT_MID = 3;
    public static final int CTR_RIGHT_BOTTOM = 4;
    public static final int CTR_MID_BOTTOM = 5;
    public static final int CTR_LEFT_BOTTOM = 6;
    public static final int CTR_LEFT_MID = 7;
    public static final int CTR_MID_MID = 8;
    public int current_ctr = CTR_NONE;

    public CommonImgEffectView(Context context) {
        super(context);
        this.context = context;
        initData();
    }

    public CommonImgEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    public CommonImgEffectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mainBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
        controlBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.control);
        mainBmpWidth = mainBmp.getWidth();
        mainBmpHeight = mainBmp.getHeight();
        controlBmpWidth = controlBmp.getWidth();
        controlBmpHeight = controlBmp.getHeight();

        srcPs = new float[]{0, 0,
                mainBmpWidth / 2, 0,
                mainBmpWidth, 0,
                mainBmpWidth, mainBmpHeight / 2,
                mainBmpWidth, mainBmpHeight,
                mainBmpWidth / 2, mainBmpHeight,
                0, mainBmpHeight,
                0, mainBmpHeight / 2,
                mainBmpWidth / 2, mainBmpHeight / 2};
        dstPs = srcPs.clone();
        srcRect = new RectF(0, 0, mainBmpWidth, mainBmpHeight);
        dstRect = new RectF();

        matrix = new Matrix();

        prePivot = new Point(mainBmpWidth / 2, mainBmpHeight / 2);
        lastPivot = new Point(mainBmpWidth / 2, mainBmpHeight / 2);

        lastPoint = new Point(0, 0);

        paint = new Paint();

        paintRect = new Paint();
        paintRect.setColor(Color.RED);
        paintRect.setAlpha(100);
        paintRect.setAntiAlias(true);

        paintFrame = new Paint();
        paintFrame.setColor(Color.GREEN);
        paintFrame.setAntiAlias(true);
        paintFrame.setStrokeWidth(4);

        setMatrix(OPER_DEFAULT);
    }

    /**
     * 矩阵变换，达到图形平移的目的
     *
     * @param operationType
     */
    private void setMatrix(int operationType) {
        switch (operationType) {
            case OPER_TRANSLATE:
                matrix.postTranslate(deltaX, deltaY);
                break;

            case OPER_SCALE:
                //以对称点为圆心进行缩放
                matrix.postScale(scaleValue, scaleValue, symmeticPoint.x, symmeticPoint.y);
                break;

            case OPER_ROTATE:
                matrix.postRotate(preDegree - lastDegree, dstPs[CTR_MID_MID * 2], dstPs[CTR_MID_MID * 2 + 1]);
                break;
        }

        //映射点的值到指定的数组中，这个方法可以在矩阵变换以后，给出指定点的值。
        matrix.mapPoints(dstPs, srcPs);
        //把src中指定的矩形的左上角和右下角的两个点的坐标，写入dst中
        matrix.mapRect(dstRect, srcRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);//绘制背景，以便测试矩形的映射
        canvas.drawBitmap(mainBmp, matrix, paint);//绘制主图片
        drawFrame(canvas);//绘制边框，以便测试点的映射
        drawControlPoints(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(dstRect, paintRect);
    }


    private void drawFrame(Canvas canvas) {
        canvas.drawLine(dstPs[0], dstPs[1], dstPs[4], dstPs[5], paintFrame);
        canvas.drawLine(dstPs[4], dstPs[5], dstPs[8], dstPs[9], paintFrame);
        canvas.drawLine(dstPs[8], dstPs[9], dstPs[12], dstPs[13], paintFrame);
        canvas.drawLine(dstPs[0], dstPs[1], dstPs[12], dstPs[13], paintFrame);
        canvas.drawPoint(dstPs[16], dstPs[17], paintFrame);
    }

    private void drawControlPoints(Canvas canvas) {
        for (int i = 0; i < dstPs.length; i += 2) {
            canvas.drawBitmap(controlBmp, dstPs[i] - controlBmpWidth / 2, dstPs[i + 1] - controlBmpHeight / 2, paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int evX = (int) event.getX();
        int evY = (int) event.getY();

        int operType = OPER_DEFAULT;
        operType = getOperationType(event);
        switch (operType) {
            case OPER_TRANSLATE:
                translate(evX, evY);
                break;

            case OPER_SCALE:
                scale(event);
                break;

            case OPER_ROTATE:
                rotate(event);
                break;
        }
        lastPoint.x = evX;
        lastPoint.y = evY;

        lastOper = operType;
        invalidate();//重绘
        return true;

    }

    private int getOperationType(MotionEvent event) {
        int evX = (int) event.getX();
        int evY = (int) event.getY();
        int curOper = lastOper;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                current_ctr = isOnCP(evX, evY);
                if (current_ctr != CTR_NONE || isOnPic(evX, evY)) {
                    curOper = OPER_SELECTED;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (current_ctr > CTR_NONE && current_ctr < CTR_MID_MID) {
                    curOper = OPER_SCALE;
                } else if (current_ctr == CTR_MID_MID) {
                    curOper = OPER_ROTATE;
                } else if (lastOper == OPER_SELECTED) {
                    curOper = OPER_TRANSLATE;
                }
                break;

            case MotionEvent.ACTION_UP:
                curOper = OPER_SELECTED;
                break;

            default:
                break;
        }

        return curOper;
    }

    /**
     * 判断点所在的控制点
     *
     * @param evX
     * @param evY
     * @return
     */
    private int isOnCP(int evX, int evY) {
        Rect rect = new Rect(evX - controlBmpWidth / 2, evY - controlBmpHeight / 2, evX + controlBmpWidth / 2, evY + controlBmpHeight / 2);
        int res = 0;
        for (int i = 0; i < dstPs.length; i += 2) {
            if (rect.contains((int) dstPs[i], (int) dstPs[i + 1])) {
                return res;
            }
            ++res;
        }
        return CTR_NONE;
    }

    private boolean isOnPic(int evX, int evY) {
        if (dstRect.contains(evX, evY)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 旋转
     *
     * @param event
     */
    private void rotate(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            preDegree = computeDegree(new Point((int) event.getX(0), (int) event.getY(0)), new Point((int) event.getX(1), (int) event.getY(1)));
        } else {
            preDegree = computeDegree(new Point((int) event.getX(), (int) event.getY()), new Point((int) dstPs[16], (int) dstPs[17]));
        }
        setMatrix(OPER_ROTATE);
        lastDegree = preDegree;
    }

    /**
     * 缩放
     *
     * @param event
     */
    private void scale(MotionEvent event) {
        int pointIndex = current_ctr * 2;// 点 -> 坐标

        float px = dstPs[pointIndex];
        float py = dstPs[pointIndex + 1];

        float evX = event.getX();
        float evY = event.getY();

        //对称点 做为 原点 进行缩放
        float oppositeX = 0;
        float oppositeY = 0;
        if (current_ctr < 4 && current_ctr >= 0) {
            oppositeX = dstPs[pointIndex + 8];
            oppositeY = dstPs[pointIndex + 9];
        } else if (current_ctr >= 4) {
            oppositeX = dstPs[pointIndex - 8];
            oppositeY = dstPs[pointIndex - 7];
        }
        float temp1 = getDistanceOfTwoPoints(px, py, oppositeX, oppositeY);
        float temp2 = getDistanceOfTwoPoints(evX, evY, oppositeX, oppositeY);

        scaleValue = temp2 / temp1;
        symmeticPoint.x = (int) oppositeX;
        symmeticPoint.y = (int) oppositeY;

        setMatrix(OPER_SCALE);
    }

    /**
     * 移动
     *
     * @param evX
     * @param evY
     */
    private void translate(int evX, int evY) {
        prePivot.x += evX - lastPoint.x;
        prePivot.y += evY - lastPoint.y;

        deltaX = prePivot.x - lastPivot.x;
        deltaY = prePivot.y - lastPivot.y;

        lastPivot.x = prePivot.x;
        lastPivot.y = prePivot.y;

        setMatrix(OPER_TRANSLATE);//设置矩阵
    }

    /**
     * 计算两点之间的距离
     *
     * @param px
     * @param py
     * @param cx
     * @param cy
     * @return
     */
    private float getDistanceOfTwoPoints(float px, float py, float cx, float cy) {
        return (float) Math.sqrt((px - cx) * (px - cx) + (py - cy) * (py - cy));
    }

    /**
     * 计算两点与垂直方向夹角
     *
     * @param point1
     * @param point2
     * @return
     */
    private float computeDegree(Point point1, Point point2) {
        float tran_x = point1.x - point2.x;
        float tran_y = point1.y - point2.y;
        float degree = 0;
        float angle = (float) (Math.asin(tran_x / Math.sqrt(tran_x * tran_x + tran_y * tran_y)) * 180 / Math.PI);
        if (!Float.isNaN(angle)) {
            if (tran_x >= 0 && tran_y <= 0) {//第一象限
                degree = angle;
            } else if (tran_x <= 0 && tran_y <= 0) {//第二象限
                degree = angle;
            } else if (tran_x <= 0 && tran_y >= 0) {//第三象限
                degree = -180 - angle;
            } else if (tran_x >= 0 && tran_y >= 0) {//第四象限
                degree = 180 - angle;
            }
        }
        return degree;
    }
}
