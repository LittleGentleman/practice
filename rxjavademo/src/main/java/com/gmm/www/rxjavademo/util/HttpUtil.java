package com.gmm.www.rxjavademo.util;

import android.util.Log;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author:gmm
 * @date:2020/2/23
 * @类说明:
 */
public class HttpUtil {

    public static String BASE_URL = "https://www.wanandroid.com/";

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    static class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            //请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            //以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            boolean isJson = ((message.startsWith("{") && message.endsWith("}"))) ||
                    (message.startsWith("[") && message.endsWith("]"));
            if (isJson) {
                message = JsonUtil.formatJson(message);
            }
            mMessage.append(message.concat("\n"));
            //请求或响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Log.i("GMM", "log: " + mMessage.toString());
            }
        }
    }

    public static Retrofit getRestrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = builder
//                .addInterceptor(logInterceptor)//添加拦截器
//                .addNetworkInterceptor(new StethoInterceptor());
                .readTimeout(10000, TimeUnit.SECONDS)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                //添加一个json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                //添加RxJava处理工具
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
