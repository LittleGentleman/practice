package com.gmm.lazyfragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private ViewPager viewPager;
    private RadioGroup bottomGruop;
    private RadioButton homeBtn;
    private RadioButton toolBtn;
    private RadioButton findBtn;
    private RadioButton messageBtn;
    private RadioButton mineBtn;

    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager);
//        viewPager.setOffscreenPageLimit(2);
        bottomGruop = findViewById(R.id.rg_bottom);
        homeBtn = findViewById(R.id.rb_home);
        toolBtn = findViewById(R.id.rb_tool);
        findBtn = findViewById(R.id.rb_find);
        messageBtn = findViewById(R.id.rb_message);
        mineBtn = findViewById(R.id.rb_mine);
//        homeBtn.setOnCheckedChangeListener(this);
//        toolBtn.setOnCheckedChangeListener(this);
//        findBtn.setOnCheckedChangeListener(this);
//        messageBtn.setOnCheckedChangeListener(this);
//        mineBtn.setOnCheckedChangeListener(this);
        bottomGruop.setOnCheckedChangeListener(this);



        HomeFragment homeFragment = new HomeFragment();
        ToolFragment toolFragment = new ToolFragment();
        FindFragment findFragment = new FindFragment();
        MessageFragment messageFragment = new MessageFragment();
        MineFragment mineFragment = new MineFragment();
        fragments.add(homeFragment);
        fragments.add(toolFragment);
        fragments.add(findFragment);
        fragments.add(messageFragment);
        fragments.add(mineFragment);

        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                viewPager.setCurrentItem(0,false);
                break;
            case R.id.rb_tool:
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.rb_find:
                viewPager.setCurrentItem(2,false);
                break;
            case R.id.rb_message:
                viewPager.setCurrentItem(3,false);
                break;
            case R.id.rb_mine:
                viewPager.setCurrentItem(4,false);
                break;

        }
    }



    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
