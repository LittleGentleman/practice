package com.gmm.www.changeskindemo;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflater.from(this).setFactory2(new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                if (TextUtils.equals("TextView",name)) {
                    Button button = new Button(MainActivity.this);
                    button.setText("Hello Button");
                    return button;
                }
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
//                if (TextUtils.equals("TextView",name)) {
//                    Button button = new Button(MainActivity.this);
//                    button.setText("Hello Button!");
//                    return button;
//                }
                return null;
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
