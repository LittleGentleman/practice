package com.gmm.www.animationdemo01;


import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class DrawableAnimationActivity extends AppCompatActivity {
    private ImageView mTarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_animation);
        initToolBar();
        mTarget = findViewById(R.id.img_target);
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_off,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i==android.R.id.home) {
            finish();
        } else if (i == R.id.action_start) {
            doAnimation(getAnimationDrawable(false),true);
        } else if (i == R.id.action_stop) {
            doAnimation(getAnimationDrawable(false),false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void doAnimation(AnimationDrawable animationDrawable,boolean doIt) {
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        if (doIt) {
            animationDrawable.start();
        }
    }

    private AnimationDrawable getAnimationDrawable(boolean fromXml) {
        if (fromXml) {
            //通过定义xml
            AnimationDrawable animationDrawable = (AnimationDrawable) mTarget.getDrawable();
            return animationDrawable;
        } else {
            //通过代码
            AnimationDrawable animationDrawable = new AnimationDrawable();
            for (int i = 1; i < 8; i++) {
                int id = getResources().getIdentifier("run" + i,"drawable",getPackageName());
                Drawable drawable = getDrawable(id);
                if (drawable != null) {
                    animationDrawable.addFrame(drawable,10);
                }
            }
            mTarget.setImageDrawable(animationDrawable);
            return animationDrawable;
        }
    }
}
