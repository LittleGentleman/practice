package com.gmm.www.animationdemo01;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PropertyAnimationActivity extends AppCompatActivity {

    private View mTarget;
    private LayoutTransition layoutTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);

        mTarget = findViewById(R.id.view_target);

        initToolBar();
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_property,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_do_byxml:
                doAnimation(getAnimationDrawable(true));
                break;

            case R.id.action_bycode:
                doAnimation(getAnimationDrawable(false));
                break;

            case R.id.action_bycustom:
                doAnimation(getValueAnimatorByCustom());
                break;

            case R.id.action_byviewpropertyanimator:
                doAnimatorByViewPropertyAnimator();
                break;

            case R.id.action_bylayoutanimator:
                doLayoutAnimator();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doAnimation(Animator animator) {
        animator.start();
    }

    private Animator getAnimationDrawable(boolean fromXml) {
        return fromXml ? getAnimatorByXml() : getAnimatorSet();
    }

    private Animator getAnimatorByXml() {
        final int width = mTarget.getLayoutParams().width;
        final int height = mTarget.getLayoutParams().height;
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.animatorset);
        ArrayList<Animator> childAnimators = animatorSet.getChildAnimations();

        //ValueAnimator 需要通过addUpdateListener 执行通话效果
        ((ValueAnimator)childAnimators.get(childAnimators.size()-1))
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatorValue = (float) animation.getAnimatedValue();
                        mTarget.getLayoutParams().width = (int) (width*animatorValue);
                        mTarget.getLayoutParams().height = (int) (height*animatorValue);
                        mTarget.requestLayout();
                    }
                });


        //其他的ObjectAnimator 不用单独处理  因为ObjectAnimator已经指定属性的动画效果
        animatorSet.setTarget(mTarget);

        return animatorSet;
    }

    private Animator getAnimatorSet() {
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(getObjectAnimatorByPropertyValuesHolder(),getValueAnimator());

        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        return animatorSet;
    }

    /**
     * ObjectAnimator 通过PropertyValuesHolder，指定属性执行特定动画
     * @return
     */
    private Animator getObjectAnimatorByPropertyValuesHolder() {
        PropertyValuesHolder bgColorAnimator = PropertyValuesHolder.ofObject("backgroundColor",
                new ArgbEvaluator(),0xff009688,0xff795548);

        PropertyValuesHolder rotationXAnimator = PropertyValuesHolder.ofFloat("rotationX",
                0f,360f);

        //ofPropertyValuesHolder 内部实现了setTarget,把动画和View进行绑定
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mTarget,bgColorAnimator,rotationXAnimator);
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        return objectAnimator;
    }

    /**
     * ValueAnimator 在addUpdateListener内根据animatorValue 改变控件属性的值 来达到动画效果
     * @return
     */
    private ValueAnimator getValueAnimator() {
        final int width = mTarget.getLayoutParams().width;
        final int height = mTarget.getLayoutParams().height;

        ValueAnimator sizeValueAnimator = ValueAnimator.ofFloat(1f,3f);
        sizeValueAnimator.setDuration(3000);
        sizeValueAnimator.setRepeatCount(1);
        sizeValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //ValueAnimator 需要使用addUpdateListener 达到动画效果
        sizeValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float) animation.getAnimatedValue();
                mTarget.getLayoutParams().width = (int) (width*animatorValue);
                mTarget.getLayoutParams().height = (int) (height*animatorValue);
                mTarget.requestLayout();
            }
        });

        return sizeValueAnimator;
    }

    private ValueAnimator getValueAnimatorByCustom() {
        final int width = mTarget.getLayoutParams().width;
        final int height = mTarget.getLayoutParams().height;

        PropertyBean startPropertyBean = new PropertyBean(0xff009688,0f,0f,0f,1f);
        PropertyBean endPropertyBean = new PropertyBean(0xff795548,360f,360f,360f,3.0f);

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setInterpolator(new SpeedUpInterpolator());

        valueAnimator.setObjectValues(startPropertyBean,endPropertyBean);
        //设置自定义 估值器
        valueAnimator.setEvaluator(new MyTypeEvaluator());

        //ValueAnimator  通过addUpdateListener 更改属性 达到动画效果
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PropertyBean propertyBean = (PropertyBean) animation.getAnimatedValue();
                if (propertyBean.getBackgroundColor()!=0&&propertyBean.getBackgroundColor()!=1){
                    mTarget.setBackgroundColor(propertyBean.getBackgroundColor());
                }
                mTarget.setRotationX(propertyBean.getRotationX());
                mTarget.setRotationY(propertyBean.getRotationY());
                mTarget.setRotation(propertyBean.getRotationZ());
                mTarget.getLayoutParams().width = (int) (width*propertyBean.getSize());
                mTarget.getLayoutParams().height = (int) (height*propertyBean.getSize());
                mTarget.requestLayout();
            }
        });

        return valueAnimator;
    }

    private void doAnimatorByViewPropertyAnimator() {
        mTarget.animate()
                .rotationX(360)
                .alpha(0.5f)
                .scaleX(3)
                .scaleY(3)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(3000)
                .setStartDelay(0);

    }

    /**
     * 布局过渡动画
     */
    private void doLayoutAnimator() {
        LayoutTransition layoutTransition = new LayoutTransition();

        layoutTransition.setAnimator(LayoutTransition.APPEARING,getObjectAnimator(false));
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING,getObjectAnimator(true));
        layoutTransition.setDuration(3000);

        ViewGroup contentView = (ViewGroup) ((ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
        contentView.setLayoutTransition(layoutTransition);
        if (contentView.findViewById(R.id.view_target) == null) {
            contentView.addView(mTarget);
        } else {
            contentView.removeView(mTarget);
        }
    }

    private ObjectAnimator getObjectAnimator(boolean b) {
        if (b) {
            ObjectAnimator bgColorAnimator = ObjectAnimator.ofArgb(mTarget,
                    "backgroundColor",0xff009688,0xff795548);
            bgColorAnimator.setRepeatCount(1);
            bgColorAnimator.setRepeatMode(ValueAnimator.REVERSE);
            bgColorAnimator.setDuration(3000);
            bgColorAnimator.setStartDelay(0);
            return bgColorAnimator;
        } else {
            ObjectAnimator rotationXAnimator = ObjectAnimator.ofFloat(mTarget,
                    "rotationX",0f,360f);
            rotationXAnimator.setRepeatCount(1);
            rotationXAnimator.setRepeatMode(ValueAnimator.REVERSE);
            rotationXAnimator.setDuration(3000);
            return rotationXAnimator;
        }
    }


    //-----------------------------------高级用法------------------------------

    static class PropertyBean {
        int backgroundColor;
        float rotationX;
        float rotationY;
        float rotationZ;
        float size;

        public PropertyBean(int backgroundColor, float rotationX, float rotationY, float rotationZ, float size) {
            this.backgroundColor = backgroundColor;
            this.rotationX = rotationX;
            this.rotationY = rotationY;
            this.rotationZ = rotationZ;
            this.size = size;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public float getRotationX() {
            return rotationX;
        }

        public void setRotationX(float rotationX) {
            this.rotationX = rotationX;
        }

        public float getRotationY() {
            return rotationY;
        }

        public void setRotationY(float rotationY) {
            this.rotationY = rotationY;
        }

        public float getRotationZ() {
            return rotationZ;
        }

        public void setRotationZ(float rotationZ) {
            this.rotationZ = rotationZ;
        }

        public float getSize() {
            return size;
        }

        public void setSize(float size) {
            this.size = size;
        }
    }

    /**
     * 自定义 估值器
     */
    static class MyTypeEvaluator implements TypeEvaluator<PropertyBean> {
        ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

        /**
         *
         * @param fraction  插值器getInterpolation（）的返回值
         * @param startValue 动画的初始值
         * @param endValue  动画的结束值
         * @return
         */
        @Override
        public PropertyBean evaluate(float fraction, PropertyBean startValue, PropertyBean endValue) {
            int currentColor = (int) mArgbEvaluator.evaluate(fraction,startValue.backgroundColor,endValue.backgroundColor);
            float currentRotationX = startValue.getRotationX()+(endValue.getRotationX()-startValue.getRotationX())*fraction;
            float currentRotationY = startValue.getRotationY()+(endValue.getRotationY()-startValue.getRotationY())*fraction;
            float currentRotationZ = startValue.getRotationZ()+(endValue.getRotationZ()-startValue.getRotationZ())*fraction;
            float size = startValue.getSize()+(endValue.getSize()-startValue.getSize())*fraction;
            return new PropertyBean(currentColor,currentRotationX,currentRotationY,currentRotationZ,size);
        }
    }

    /**
     * 自定义 插值器
     */
    static class SpeedUpInterpolator implements Interpolator {
        //因子
        private float mFactor;
        private double mDoubleFactor;

        public SpeedUpInterpolator() {
            //初始化因子
            mFactor = 1.0f;
            mDoubleFactor = 2.0;
        }

        /**
         * 改变动画 速度
         * @param input
         * @return
         */
        @Override
        public float getInterpolation(float input) {
            if (mFactor == 1.0f) {
                return input*input;
            } else {
                return (float) Math.pow(input,mDoubleFactor);
            }
        }
    }
}
