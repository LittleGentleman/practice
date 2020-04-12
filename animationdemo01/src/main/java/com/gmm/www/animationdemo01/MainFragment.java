package com.gmm.www.animationdemo01;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

/**
 * @author:gmm
 * @date:2020/4/1
 * @类说明:
 */
public class MainFragment extends ListFragment {

    ArrayAdapter<String> adapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] array = new String[]{
                "视图动画",//补间动画
                "帧动画",
                "属性动画",
        };

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent gotoAct;
        switch (position) {
            case 0://视图动画-补间动画
                gotoAct = new Intent(getActivity(),ViewAnimationActivity.class);
                startActivity(gotoAct);
                break;

            case 1://帧动画
                gotoAct = new Intent(getActivity(),DrawableAnimationActivity.class);
                startActivity(gotoAct);
                break;

            case 2://属性动画
                gotoAct = new Intent(getActivity(),PropertyAnimationActivity.class);
                startActivity(gotoAct);
                break;

            default:
                break;
        }
    }
}
