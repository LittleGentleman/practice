package com.gmm.www.animationdemo02;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class InterpolatorAnimationActivity extends Activity {
    private Button mButton1 = null;
    private Button mButton2 = null;
    private Button mButton3 = null;
    private Button mButton4 = null;
    private Button mButton5 = null;
    private Button mButton6 = null;
    private Button mButton7 = null;
    private Button mButton8 = null;
    private AnimationSet anim = null;
    private TranslateAnimation anim2 = null;
    private ImageView mImageView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator_animation);
        mImageView = findViewById(R.id.mImageView);
        mImageView.setImageResource(R.drawable.ali);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mButton6 = findViewById(R.id.button6);
        mButton7 = findViewById(R.id.button7);
        mButton8 = findViewById(R.id.button8);

        //1、通过XML实现
        anim = (AnimationSet) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.interpolatoranim);

        //2.通过代码实现
        anim2 = new TranslateAnimation(0.0f,500f,0.0f,0.0f);
        anim2.setDuration(5000);
        anim2.setFillAfter(true);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.加速插值器模型
                mImageView.startAnimation(anim);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2.减速插值器模型
                anim2.setInterpolator(new DecelerateInterpolator());
                mImageView.startAnimation(anim2);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3.加速减速插值器模型
                anim2.setInterpolator(new AccelerateDecelerateInterpolator());
                mImageView.startAnimation(anim2);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3.回弹插值器
                anim2.setInterpolator(new BounceInterpolator());
                mImageView.startAnimation(anim2);
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //4.循环插值器
                anim2.setInterpolator(new CycleInterpolator(0.5f));
                mImageView.startAnimation(anim2);
            }
        });

        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //5.自定义插值器
                anim2.setInterpolator(new CustomInterpolator());
                mImageView.startAnimation(anim2);
            }
        });

        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.先反向后加速插值器
                anim2.setInterpolator(new AnticipateInterpolator());
                mImageView.startAnimation(anim2);
            }
        });

        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //7.过量插值器
                anim2.setInterpolator(new OvershootInterpolator());
                mImageView.startAnimation(anim2);
            }
        });
    }

    static class CustomInterpolator implements Interpolator {
        private int LinearInterpolator = 0;
        private int AccelerateInterpolator = 1;
        private int factorA = 2;
        @Override
        public float getInterpolation(float input) {
            int interpolatorType = AccelerateInterpolator;
            if (interpolatorType == LinearInterpolator) {
                //匀速运动插值器 s = v*t
                return 2 * input;
            } else if (interpolatorType == AccelerateInterpolator) {
                //匀加速插值器 s = a/2 * t * t
                return factorA / 2 * input * input;
            }
            return 0;
        }
    }
}
