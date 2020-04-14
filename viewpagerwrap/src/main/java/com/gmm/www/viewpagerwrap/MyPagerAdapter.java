package com.gmm.www.viewpagerwrap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author:gmm
 * @date:2020/4/13
 * @类说明:
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<Integer> images;
    private Context context;

    public MyPagerAdapter(Context context,List<Integer> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        position = position % images.size();
        View view = LayoutInflater.from(context).inflate(R.layout.linear_item,container,false);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(images.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
