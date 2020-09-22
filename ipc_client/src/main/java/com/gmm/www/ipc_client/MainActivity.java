package com.gmm.www.ipc_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gmm.www.ipc_service.ITestAidl;
import com.gmm.www.ipc_service.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private Button button;
    private ITestAidl aidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.but_click);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //发送数据
                    aidl.addPerson(new Person("gmm",3));
                    List<Person> persons = aidl.getPersonList();
                    Log.e(TAG, persons.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("com.gmm.www.ipc_service","com.gmm.www.ipc_service.IpcTestService"));
//        bindService(intent,conn, Context.BIND_AUTO_CREATE);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.gmm.www.ipc_service", "com.gmm.www.ipc_service.IpcTestService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    
    private ServiceConnection connection = new ServiceConnection() {
        /**
         *
         * @param name
         * @param service  就是Proxy对象  Proxy是用来发送数据的
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            //获得Proxy对象
            aidl = ITestAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
            aidl = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
