package com.gmm.www.processdemo.jobscheduler;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * @author:gmm
 * @date:2020/4/21
 * @类说明:
 */
@SuppressLint("NewApi")
public class MyJobService extends JobService {

    private static final String TAG = "MyJobService";

    public static void startJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        //setPersisted 在设置重启依然执行
        //需要增加权限 RECEIVE_BOOT_COMPLETED
        JobInfo.Builder builder = new JobInfo.Builder(8,new ComponentName(context.getPackageName(),
                MyJobService.class.getName())).setPersisted(true);

        // 小于7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            // 每隔 1s 执行一次 job
            // 版本23 开始 进行了改进，最小周期为 5s
            builder.setPeriodic(5000);
        } else {
            // 延迟执行任务
            builder.setMinimumLatency(5000);
        }

        jobScheduler.schedule(builder.build());
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.e(TAG, "onStartJob");

        // 如果7.0以上 轮询
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startJob(this);
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
