package com.gmm.www.animationdemo02;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 复杂的视图动画 ------ 补间动画
 */
public class ComplicateViewAnimationActivity extends Activity {
    private ImageView mImageView;
    private Button mButton;

    private Animation anim;

    private AlphaAnimation alphaAnimation;
    private RotateAnimation rotateAnimation;
    private ScaleAnimation scaleAnimation;
    private TranslateAnimation translateAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complicate_view_animation);

        mImageView = findViewById(R.id.mImageView);
        mImageView.setImageResource(R.drawable.ali);

        //1.View视图动画通过XML实现
        anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.complicateanim);
        //AnimationSet setRepeatCount无效，需要在具体动画内设置
//        anim.setRepeatCount(2);
//        anim.setRepeatMode(Animation.REVERSE);

        mButton = findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.startAnimation(anim);
            }
        });
    }
}
