package com.catchbest.gmm.ipcdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.catchbest.gmm.ipcdemo.location.ILocatoinManager;
import com.gmm.www.ipclibrary.IPC;
import com.gmm.www.ipclibrary.IPCService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this,GpsService.class));

        IPC.connect(this, IPCService.IPCService0.class);
    }

    public void showLocation(View view) {
        //代理对象
        ILocatoinManager location = IPC.getInstanceWithName(IPCService.IPCService0.class,
                ILocatoinManager.class,"getDefault");


        Toast.makeText(this, "当前位置:" + location.getLocation(), Toast.LENGTH_LONG).show();
    }
}
