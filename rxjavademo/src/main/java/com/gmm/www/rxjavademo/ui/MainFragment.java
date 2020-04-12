package com.gmm.www.rxjavademo.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author:gmm
 * @date:2020/2/23
 * @类说明:
 */
public class MainFragment extends ListFragment {
    private ArrayAdapter<String> adapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] array = new String[]{
            "retrofit网络测试",
            "注册/登录",
            "ButtonClicksActivity",
            "LoginActivity",
            "RegisterActivity",
            "FilterActivity",
            "OpActivity"
        };

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,array);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(),RetrofitTestActivity.class));
                break;

            case 1:
                startActivity(new Intent(getActivity(),RetrofitLoginActivity.class));
                break;

            case 2:
                startActivity(new Intent(getActivity(),ButtonClicksActivity.class));
                break;

            case 3:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;

            case 4:
                startActivity(new Intent(getActivity(),RegisterActivity.class));
                break;

            case 5:
                startActivity(new Intent(getActivity(),FilterActivity.class));
                break;

            case 6:
                startActivity(new Intent(getActivity(),OpActivity.class));
                break;
        }
    }
}
