package com.gmm.www.hookdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gmm.www.hookdemo.hook.HookHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toTargetActivity(View view) {
        HookHelper.hookIActivityManager();
        HookHelper.hookHandler();
        Intent intent = new Intent(this,TargetActivity.class);
        startActivity(intent);
    }

    public void toPlaceholderActivity(View view) {
        Intent intent = new Intent(this,TargetActivity.class);
        startActivity(intent);

        Button button = new Button(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
