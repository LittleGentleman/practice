package com.gmm.www.viewpagerwrap;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager viewPager;
    private RadioGroup radioGroup;

    private List<Integer> images;
    private int index;
    private int preIndex;
    private Timer timer = new Timer();
    private boolean isContinue = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    index++;
                    viewPager.setCurrentItem(index);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        radioGroup = findViewById(R.id.radio_group);
        images = new ArrayList<>();
        images.add(R.mipmap.p1);
        images.add(R.mipmap.p2);
        images.add(R.mipmap.p3);
        images.add(R.mipmap.girl5);

        MyPagerAdapter adapter = new MyPagerAdapter(this,images);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setPageMargin(30);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setPageTransformer(true,new MyPageTransform());
        viewPager.setCurrentItem(images.size()*100);

        for (int i = 0; i < images.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.rg_selector);
            radioGroup.addView(imageView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.getChildAt(i).setEnabled(false);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isContinue) {
                    handler.sendEmptyMessage(1);
                }
            }
        },2000,2000);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        setCurrentDot(index % images.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setCurrentDot(int position) {
        if (radioGroup.getChildAt(position) != null) {
            //当前按钮不可改变
            radioGroup.getChildAt(position).setEnabled(false);
        }
        if (radioGroup.getChildAt(preIndex) != null) {
            //上个按钮可以改变
            radioGroup.getChildAt(preIndex).setEnabled(true);
            //当前位置变为上一个，继续下次轮播
            preIndex = position;
        }
    }
}
