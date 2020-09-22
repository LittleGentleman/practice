package com.gmm.www.hookdemo.hook;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.gmm.www.hookdemo.PlaceHolderActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author:gmm
 * @date:2020/5/7
 * @类说明:
 */
public class HookHelper {

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    /**
     * 偷梁换柱 把占位的Intent替换目标Intent，并融入到framework中
     */
    public static void hookIActivityManager() {
        try {
            //IActivityManager aidl接口 的实现对象
            Field gDefaultField = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//android 8.0 versionSdk 26

                Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
                gDefaultField = activityManagerClass.getDeclaredField("IActivityManagerSingleton");//私有的
            } else {
                Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
                gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            }
            gDefaultField.setAccessible(true);//设置可访

            //这里得到的是Singleton对象，接下载再从Singleton对象中 获得 IActivityManager对象
            Object gDefault = gDefaultField.get(null);//属性是静态属性，所以直接传null

            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            final Object iActivityManager = mInstanceField.get(gDefault);//非静态属性，需要传入所属类的对象

            //动态代理，将动态代理生成的代理对象 替换 系统的 iActivityManager对象，在动态代理中添加我们的逻辑，就能实现hook的功能
            //如果是Interface，可以使用动态代理，如果是Class，可以使用静态代理， 这里IActivityManager 使用 动态代理
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();//直接使用当前线程的ClassLoader
            Class[] interfaces = new Class[]{Class.forName("android.app.IActivityManager")};
            Object proxy = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
                /**
                 *  目的是修改 ActivityManagerService 中 startActivity方法中 的intent 参数
                 * @param proxy  代理对象
                 * @param method 对象中的方法
                 * @param args   方法中的传参
                 * @return
                 * @throws Throwable
                 */
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {//调用IActivityManager中的方法
                    //修改startActivity方法中的Intent参数，把原来的TargetActivity的intent参数替换成我们占位用的PlaceHolderActivity的intent
                    if (method.getName().equals("startActivity")) {
                        Intent targetIntent = null;
                        int index = 0;

                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                index = i;
                                targetIntent = (Intent) args[i];
                                break;
                            }
                        }

                        //偷梁换柱
                        Intent placeholderIntent = new Intent();
                        placeholderIntent.setComponent(new ComponentName("com.gmm.www.hookdemo",PlaceHolderActivity.class.getName()));
                        //需要将目标intent已参数的形式携带，为了在骗过AMS后，能再找回目标的Activity
                        placeholderIntent.putExtra(EXTRA_TARGET_INTENT,targetIntent);

                        //替换intent传参
                        args[index] = placeholderIntent;
                    }
                    return method.invoke(iActivityManager,args);//这里方法才真正的被调用和执行
                }
            });

            //把代理对象融入到framework中
            mInstanceField.set(gDefault,proxy);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绕过ams之后，需要换回之前的目标intent 恢复真身
     */
    public static void hookHandler() {

        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);

            //ActivityThread 一个app进程 只有一个，获取它的mH
            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            final Handler mH = (Handler) mHField.get(sCurrentActivityThread);

            //获取Handler中的mCallback
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(mH, new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {//设置一个callback，这样可以在Handler handlerMessage() 之前 恢复真身

                    switch (msg.what) {
                        case 100:
                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                Intent intent = (Intent) intentField.get(msg.obj);
                                Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
                                //恢复真身
                                intent.setComponent(targetIntent.getComponent());

                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 159:
                            Object obj = msg.obj;
                            try {
                                Field mActivityCallbacksField = obj.getClass().getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksField.setAccessible(true);
                                List mActivityCallbacks = (List) mActivityCallbacksField.get(obj);

                                if (mActivityCallbacks.size() > 0) {
                                    String className = "android.app.servertransaction.LaunchActivityItem";
                                    if (mActivityCallbacks.get(0).getClass().getCanonicalName().equals(className)) {
                                        Object object = mActivityCallbacks.get(0);
                                        Field mIntentField = object.getClass().getDeclaredField("mIntent");
                                        mIntentField.setAccessible(true);
                                        Intent mIntent = (Intent) mIntentField.get(object);
                                        Intent targetIntent = mIntent.getParcelableExtra(EXTRA_TARGET_INTENT);

                                        //恢复真身
                                        mIntent.setComponent(targetIntent.getComponent());
                                    }
                                }

                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    mH.handleMessage(msg);
                    return true;
                }
            });

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
