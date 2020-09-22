package com.catchbest.gmm.ipcdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.catchbest.gmm.ipcdemo.location.Location;
import com.catchbest.gmm.ipcdemo.location.LocationManager;
import com.gmm.www.ipclibrary.IPC;

/**
 * @author:gmm
 * @date:2020/4/23
 * @类说明:
 */
public class GpsService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //定位
        LocationManager.getDefault().setLocation(new Location("岳麓区天之道", 1.1d, 2.2d));
        /**
         * 1.在数据/服务提供方法进行服务注册
         */
        IPC.register(LocationManager.class);
    }
}
