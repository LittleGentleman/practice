package com.gmm.www.animationdemo02;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ComplicatePropertyAnimationActivity extends Activity {

    private ImageView mImageView = null;
    private Button mButton1 = null;
    private Button mButton2 = null;
    private Button mButton3 = null;

    private AnimatorSet set;
    private ObjectAnimator objectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complicate_property_animation);
        mImageView = findViewById(R.id.mImageView);
        mImageView.setImageResource(R.drawable.ali);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complicatePropertyAnim(mImageView);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propertyValuesHolderAnim(mImageView);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyFrameAnim(mImageView);
            }
        });
    }

    //3.属性动画的关键类的使用 KeyFrames
    private void keyFrameAnim(ImageView mImageView) {
        Keyframe kf0 = Keyframe.ofFloat(0,0.0f);
        Keyframe kf1 = Keyframe.ofFloat(0.25f,45.0f);
        Keyframe kf2 = Keyframe.ofFloat(0.5f,90.0f);
        Keyframe kf3 = Keyframe.ofFloat(0.75f,135.0f);
        Keyframe kf4 = Keyframe.ofFloat(1f,180.0f);

        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation",kf0,
                kf1,kf2,kf3,kf4);

        ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(mImageView,pvhRotation);
        rotationAnim.setDuration(2000);
        rotationAnim.start();
    }

    //2.属性动画通过PropertyValueHolder实现
    private void propertyValuesHolderAnim(ImageView mImageView) {
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha",1.0f,0f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofFloat("rotation",0.0f,180.0f);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX",1.0f,2.0f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY",1.0f,2.0f);
        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofFloat("x",0.0f,100.0f);
        PropertyValuesHolder pvhTranslateY = PropertyValuesHolder.ofFloat("y",0.0f,100.0f);

        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mImageView,pvhAlpha,
                pvhRotation,pvhScaleX,pvhScaleY,pvhTranslateX,pvhTranslateY);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    //属性动画通过XML实现
    private void complicatePropertyAnim(ImageView mImageView) {
        set = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.complicatepropertyanim);
        set.setTarget(mImageView);
        set.start();
    }
}
