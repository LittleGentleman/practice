package com.demo.www.matrixdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/5
 * @类说明:  绘制圆形头像
 */
public class BitmapDrawView extends View {

    private Paint mPaint;
    private Path mClipPath;
    private Bitmap mBitmap;
    private Bitmap newBitmap;
    private int mScreenWidth;

    public BitmapDrawView(Context context) {
        super(context);
        init(context);
    }

    public BitmapDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BitmapDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mScreenWidth = Utils.getScreenWidth(context);
        mClipPath = new Path();
        mBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.yasuo);
        newBitmap = Bitmap.createBitmap(mBitmap.getWidth(),mBitmap.getHeight(),Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap,Utils.dp2px(50),Utils.dp2px(20),mPaint);

        //1.
        newBitmap = processRoundBitmap(mBitmap);
        canvas.drawBitmap(newBitmap,mScreenWidth/2,Utils.dp2px(20),mPaint);

        //2.
        canvas.save();
        mPaint.setAntiAlias(true);
        //切花一块矩形画布
        canvas.clipRect(mScreenWidth/2-mBitmap.getWidth()/2,Utils.dp2px(300),mScreenWidth/2+mBitmap.getWidth()/2,Utils.dp2px(300)+mBitmap.getHeight());
        //在这个画布上绘制图片
        canvas.drawBitmap(mBitmap,mScreenWidth/2-mBitmap.getWidth()/2,Utils.dp2px(300),mPaint);
        //添加原型路径
        mClipPath.addCircle(mScreenWidth/2,Utils.dp2px(300)+mBitmap.getHeight()/2,mBitmap.getWidth()/2,Path.Direction.CCW);
        //按照圆形路径切割画布，但是取与上一层画布不重叠的部分
        canvas.clipPath(mClipPath,Region.Op.DIFFERENCE);
        canvas.drawColor(Color.WHITE);//在没有重叠区域绘制白色
        canvas.restore();

    }

    private Bitmap processRoundBitmap(Bitmap bitmap) {
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Bitmap.Config.ARGB_8888);
        //创建一块正方形画布
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        //绘制一个白色的圆
        canvas.drawCircle(resultBitmap.getWidth()/2,resultBitmap.getHeight()/2,resultBitmap.getWidth()/2,paint);
        paint.setAntiAlias(false);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap,0,0,paint);
        return resultBitmap;
    }
}
