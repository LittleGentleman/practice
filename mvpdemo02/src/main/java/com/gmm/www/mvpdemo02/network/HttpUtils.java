package com.gmm.www.mvpdemo02.network;

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
 * @date:2020/3/13
 * @类说明:
 */
public class HttpUtils {

    public static final String TAG = "HttpUtils";

    public static String BASE_URL = "https://www.wanandroid.com/";

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    static class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();
        @Override
        public void log(String message) {
            //请求或者响应开始
            if (message.startsWith("--> POST")){
                mMessage.setLength(0);
            }
            //以{}或者[]形式的说明是响应结构的json数据，需要进行格式化
            boolean isJson = (message.startsWith("{")&&message.equals("}"))||
                    (message.startsWith("[")&&message.endsWith("]"));
            if (isJson){
               message = JsonUtils.formatJson(message);
            }
            mMessage.append(message.concat("\n"));
            //请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Log.d(TAG, "log: " + mMessage.toString());
            }
        }
    }

    public static Retrofit getOnlineCookieRetrofit() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpBuilder
                .addInterceptor(loggingInterceptor)
                .readTimeout(10000,TimeUnit.SECONDS)
                .connectTimeout(10000,TimeUnit.SECONDS)
                .writeTimeout(10000,TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                //添加一个json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                //添加一个RxJava处理工具
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
