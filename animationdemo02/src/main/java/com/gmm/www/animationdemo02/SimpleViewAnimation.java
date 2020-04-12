package com.gmm.www.animationdemo02;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 视图动画----补间动画
 */
public class SimpleViewAnimation extends Activity {
    private ImageView mImageView;
    private Button mAlphaButton;
    private Button mRotateButton;
    private Button mScaleButton;
    private Button mTranslation;
    private Animation anim1;
    private Animation anim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_view_animation);
        mImageView = findViewById(R.id.mImageView);
        mImageView.setImageResource(R.drawable.ali);

        mAlphaButton = findViewById(R.id.button1);
        mRotateButton = findViewById(R.id.button2);
        mScaleButton = findViewById(R.id.button3);
        mTranslation = findViewById(R.id.button4);

        //1.View动画通过XML实现
        anim1 = AnimationUtils.loadAnimation(getApplication(),R.anim.simpleanim);


        //2.View动画通过代码实现
        anim2 = new ScaleAnimation(1.0f,2.0f,1.0f,2.0f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        anim2.setDuration(5000);
        anim2.setFillAfter(true);
        anim2.setFillBefore(false);
        anim2.setRepeatCount(2);
        anim2.setRepeatMode(Animation.REVERSE);
//        mImageView.startAnimation(anim1);

        mAlphaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
                //给View设置动画并随即开启动画
                mImageView.startAnimation(anim1);
            }
        });

        mRotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
                mImageView.startAnimation(anim1);
            }
        });

        mScaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);
                anim1.setFillAfter(true);
                anim1.setRepeatCount(2);
                anim1.setRepeatMode(Animation.REVERSE);
                mImageView.startAnimation(anim1);
            }
        });

        mTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translation);
                anim1.setRepeatCount(2);
                anim1.setRepeatMode(Animation.REVERSE);
                anim1.setFillAfter(true);
                mImageView.startAnimation(anim1);
            }
        });
    }
}
