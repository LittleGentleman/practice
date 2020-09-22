package com.gmm.www.processdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmm.www.processdemo.Activity.KeepManager;
import com.gmm.www.processdemo.account.AccountHelper;
import com.gmm.www.processdemo.jobscheduler.MyJobService;
import com.gmm.www.processdemo.service.ForegroundService;

/**
 * 进程保活：1.Activity1像素保活  2.前台服务保活
 * 进程拉活：1.广播拉活，"全家桶"拉活   2.Sticky拉活/Service系统机制拉活   3.账户同步拉活  4.JobScheduler拉活
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.Activity1像素保活
        KeepManager.getInstance().registerKeep(this);

        //2.前台服务保活
        startService(new Intent(this,ForegroundService.class));

        //进程拉活
        //Sticky拉活
        startService(new Intent(this,StickyService.class));

        //账户同步拉活
        AccountHelper.addAccount(this);
        AccountHelper.autoSync();

        //JobScheduler拉活
        MyJobService.startJob(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepManager.getInstance().unregisterKeep(this);
    }
}
