package com.gmm.www.animationdemo02;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView mTrainListAnim;
    private ArrayAdapter<String> mTrainItemAdapter;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mTrainListAnim = findViewById(R.id.listView_animation);
        mTrainItemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ConstantValues.trainEffectItems);
        mTrainListAnim.setAdapter(mTrainItemAdapter);
    }

    @Override
    protected void initListener() {
        mTrainListAnim.setAdapter(mTrainItemAdapter);
        mTrainListAnim.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intent1 = new Intent(this,SimpleViewAnimation.class);
                startActivity(intent1);
                break;

            case 1:
                Intent intent2 = new Intent(this,ComplicateViewAnimationActivity.class);
                startActivity(intent2);
                break;

            case 2:
                Intent intent3 = new Intent(this,SimplePropertyAnimatoinActivity.class);
                startActivity(intent3);
                break;

            case 3:
                Intent intent4 = new Intent(this,ComplicatePropertyAnimationActivity.class);
                startActivity(intent4);
                break;

            case 4:
                break;

            case 5:
                Intent intent5 = new Intent(this,InterpolatorAnimationActivity.class);
                startActivity(intent5);
                break;

            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            default:
                break;

        }
    }
}
