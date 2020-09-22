package com.gmm.www.processdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author:gmm
 * @date:2020/4/21
 * @类说明:利用Service的粘性，实现进程拉活
 * START_STICKY：
 * 	“粘性”。如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。
 * 	随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会
 * 	调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传
 * 	递到service，那么参数Intent将为null。
 */
public class StickyService  extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //START_STICKY
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
