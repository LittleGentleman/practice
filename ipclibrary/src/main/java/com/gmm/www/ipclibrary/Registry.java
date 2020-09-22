package com.gmm.www.ipclibrary;

import com.gmm.www.ipclibrary.annotation.ServiceId;
import com.gmm.www.ipclibrary.model.Parameters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:gmm
 * @date:2020/4/23
 * @类说明: 负责记录服务端注册的信息
 */
public class Registry {
    //线程安全的hashMap    服务表(serviceID,Service.class)
    private ConcurrentHashMap<String,Class<?>> mServices = new ConcurrentHashMap<>();
    //方法表 (Service.class，Method[])
    private ConcurrentHashMap<Class<?>,ConcurrentHashMap<String,Method>> mMethods = new ConcurrentHashMap<>();
    //类id：实例对象
    private Map<String,Object> mObjects = new HashMap<>();

    private static volatile Registry mInstance;

    public static Registry getInstance() {
        if (null == mInstance) {
            synchronized (Registry.class) {
                if (null == mInstance) {
                    mInstance = new Registry();
                }
            }
        }

        return mInstance;
    }

    public void register(Class<?> clazz) {
        //1.服务id 与 class的表  服务表
        //通过反射获得类的注解
        ServiceId serviceId = clazz.getAnnotation(ServiceId.class);
        if (null == serviceId) {
            throw new RuntimeException("必须使用ServiceId注解的服务才能注册");
        }
        String value = serviceId.value();
        mServices.put(value,clazz);

        //2.class 与 方法的表
        ConcurrentHashMap<String, Method> methods = mMethods.get(clazz);
        if (null == methods) {
            methods = new ConcurrentHashMap<String, Method>();
            mMethods.put(clazz,methods);
        }
        for (Method method : clazz.getMethods()) {
            //因为有重载方法的存在，所以不能以方法名作为key。需要加上所有的参数作为key
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName()).append("(");

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length > 0) {
                sb.append(parameterTypes[0].getName());
            }
            for (int i = 1; i < parameterTypes.length; i++) {
                sb.append(",").append(parameterTypes[i].getName());
            }

            sb.append(")");//key 生成

            methods.put(sb.toString(),method);
        }

        Set<Map.Entry<String, Class<?>>> entries = mServices.entrySet();
        for (Map.Entry<String, Class<?>> entry : entries) {
            System.out.println("服务表:" + entry.getKey() + " = " + entry.getValue());
        }

        Set<Map.Entry<Class<?>, ConcurrentHashMap<String, Method>>> entrySet = mMethods.entrySet();
        for (Map.Entry<Class<?>, ConcurrentHashMap<String, Method>> m : entrySet) {
            System.out.println("方法表：" + m.getKey());
            ConcurrentHashMap<String, Method> value1 = m.getValue();
            for (Map.Entry<String, Method> stringMethodEntry : value1.entrySet()) {
                System.out.println(" " + stringMethodEntry.getKey());
            }
        }
    }

    public Class<?> getService(String serviceId) {
        Class<?> clazz = mServices.get(serviceId);
        return clazz;
    }

    public Method getMethod(Class<?> clazz, String methodName, Parameters[] parameters) {
        ConcurrentHashMap<String, Method> methods = mMethods.get(clazz);

        StringBuilder sb = new StringBuilder();
        sb.append(methodName).append("(");
        if (parameters.length > 0) {
            sb.append(parameters[0].getType());
        }
        for (int i = 1; i < parameters.length; i++) {
            sb.append(",").append(parameters[i].getType());
        }
        sb.append(")");

        Method method = methods.get(sb.toString());
        return method;
    }

    public void putObject(String serviceId, Object object) {
        mObjects.put(serviceId,object);
    }

    public Object getObject(String serviceId) {
        return mObjects.get(serviceId);
    }
}
