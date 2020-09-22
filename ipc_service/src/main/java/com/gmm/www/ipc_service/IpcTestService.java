package com.gmm.www.ipc_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * @author:gmm
 * @date:2020/4/30
 * @类说明:  接收数据的Service
 */
public class IpcTestService extends Service {

    private ArrayList<Person> personList;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("LeoAidlService", "success onBind");
        personList = new ArrayList<>();
        return iBinder;
    }

    private IBinder iBinder = new ITestAidl.Stub() {
        @Override
        public void addPerson(Person person) throws RemoteException {
            personList.add(person);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return personList;
        }
    };
}
