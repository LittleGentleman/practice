package com.gmm.www.animationdemo02;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SimplePropertyAnimatoinActivity extends Activity {
    private ImageView mImageView;
    private Button mButton1;
    private Button mButton2;

    private AnimatorSet set;

    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    private static final int GREEN = 0xff80ff80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_property_animatoin);

        mImageView = findViewById(R.id.mImageView);
        mImageView.setImageResource(R.drawable.ali);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);

        //1.属性动画通过XML实现
        set = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.simplepropertyanim);
        set.setTarget(mImageView);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.start();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgColorAnim(mImageView);
            }
        });
    }

    //2.属性动画通过代码实现
    public void scaleAnim(View view) {
        ObjectAnimator.ofFloat(view,"scaleX",1f,2f)
                .setDuration(2000)
                .start();
    }

    //3.属性动画替换背景颜色
    public void bgColorAnim(View view) {

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(view,"backgroundColor",
                GREEN,RED,BLUE);
        colorAnim.setTarget(view);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.setDuration(3000);
        colorAnim.start();
    }
}
