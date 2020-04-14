package com.gmm.www.mylazyloadingfragment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager01);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment1.newInstance());
        fragments.add(Fragment2.newInstance());
        fragments.add(Fragment3.newInstance());
        fragments.add(Fragment4.newInstance());
        fragments.add(Fragment5.newInstance());

        FragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int itemId = R.id.fragment_1;
                switch (i) {
                    case 0:
                        itemId = R.id.fragment_1;
                        break;

                    case 1:
                        itemId = R.id.fragment_2;
                        break;

                    case 2:
                        itemId = R.id.fragment_3;
                        break;

                    case 3:
                        itemId = R.id.fragment_4;
                        break;

                    case 4:
                        itemId = R.id.fragment_5;
                        break;
                }
                bottomNavigationView.setSelectedItemId(itemId);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.fragment_1:
                        viewPager.setCurrentItem(0,true);
                        break;

                    case R.id.fragment_2:
                        viewPager.setCurrentItem(1,true);
                        break;

                    case R.id.fragment_3:
                        viewPager.setCurrentItem(2,true);
                        break;

                    case R.id.fragment_4:
                        viewPager.setCurrentItem(3,true);
                        break;

                    case R.id.fragment_5:
                        viewPager.setCurrentItem(4,true);
                        break;
                }
                return true;
            }
        });
    }

}
