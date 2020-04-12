package com.gmm.www.swipecardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gmm.www.swipecardview.adapter.UniversalAdapter;
import com.gmm.www.swipecardview.adapter.ViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<SwipeCardBean> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardConfig.initConfig(this);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new SwipeCardLayoutManager());
        mDatas = SwipeCardBean.initDatas();
        UniversalAdapter<SwipeCardBean> adapter = new UniversalAdapter<SwipeCardBean>(this,
                                                                                        mDatas,
                                                                                        R.layout.item_swipe_card) {
            @Override
            public void convert(ViewHolder holder, SwipeCardBean data) {
                holder.setText(R.id.tvName,data.getName());
                holder.setText(R.id.tvPrecent,data.getPosition() + "/" + mDatas.size());
                Glide.with(MainActivity.this)
                        .load(data.getUrl())
                        .into((ImageView) holder.getView(R.id.iv));
            }
        };
        rv.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SwipeCardCallback(rv,adapter,mDatas);
        //触摸处理
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);
    }
}
