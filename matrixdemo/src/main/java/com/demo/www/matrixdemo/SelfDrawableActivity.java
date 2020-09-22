package com.demo.www.matrixdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelfDrawableActivity extends Activity {
    private ImageView imageView;

    private TaskClearDrawable taskClearDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_drawable);

        imageView = findViewById(R.id.imageView);

        taskClearDrawable = new TaskClearDrawable(this,Utils.dp2px(100),Utils.dp2px(100));
        imageView.setImageDrawable(taskClearDrawable);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!taskClearDrawable.isRunning()) {
                    taskClearDrawable.start();
                }
            }
        });
    }
}
