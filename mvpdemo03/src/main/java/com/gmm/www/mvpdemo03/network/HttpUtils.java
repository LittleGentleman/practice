package com.gmm.www.mvpdemo03.network;

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
 * @date:2020/3/14
 * @类说明:
 */
public class HttpUtils {
    public static String BASE_URL= "https://www.wanandroid.com/";

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    static class HttpLogger implements HttpLoggingInterceptor.Logger {
        StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            if (message.startsWith("--> POST"))
                mMessage.setLength(0);

            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            boolean isJson = (message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"));
            if (isJson) {
                message = JsonUtils.formatJson(message);
            }
            mMessage.append(message.concat("\n"));
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Log.d( "Zero",mMessage.toString());
            }
        }
    }

    public static Retrofit getOnlineCookieRetrofit() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpBuilder
                .addInterceptor(loggingInterceptor)
                .readTimeout(1000,TimeUnit.SECONDS)
                .writeTimeout(1000,TimeUnit.SECONDS)
                .connectTimeout(1000,TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

    }
}
