package com.gmm.www.rxjavademo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gmm.www.rxjavademo.R;
import com.gmm.www.rxjavademo.api.WanAndroidApi;
import com.gmm.www.rxjavademo.bean.BaseResponse;
import com.gmm.www.rxjavademo.bean.ProjectBean;
import com.gmm.www.rxjavademo.bean.ProjectItem;
import com.gmm.www.rxjavademo.util.HttpUtil;
import com.gmm.www.rxjavademo.util.RxUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RetrofitTestActivity extends AppCompatActivity {

    private static final String TAG = "RetrofitTestActivity";

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;

    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);
        ButterKnife.bind(this);
        setTitle(getClass().getSimpleName());

        //获取网络api  内部使用动态代理返回代理对象
        WanAndroidApi api = HttpUtil.getRestrofit().create(WanAndroidApi.class);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                api.getProducts()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<BaseResponse<List<ProjectBean>>>() {
//                            @Override
//                            public void accept(BaseResponse<List<ProjectBean>> listBaseResponse) throws Exception {
//                                Log.d(TAG, "accept: " + listBaseResponse);
//                                Log.d(TAG, "0: " + listBaseResponse.getData().get(0));
//                            }
//                        });
//            }
//        });

        //lambda表达式
        btn1.setOnClickListener(v ->
                api.getProjects()
                .compose(RxUtil.io_main())//组成、构成 传入一个转化器
                .subscribe(listBaseResponse ->
                        Log.d(TAG, "accept: " + listBaseResponse)
                )
        );

        btn2.setOnClickListener(v ->
                disposable = api.getProjectItem(1,294)
                .compose(RxUtil.io_main())
                .subscribe(projectItem ->
                        Log.d(TAG, "projectItem: " + projectItem)
                )
        );

        //使用RxJava的flatMap操作符实现请求嵌套（lambda表达式）
//        btn3.setOnClickListener(v ->
//                api.getProducts()
//                .subscribeOn(Schedulers.io())
//                .flatMap(listBaseResponse ->//传参  响应结果
//                        Observable.fromIterable(listBaseResponse.getData())//fromIterable操作符：传入一个集合，将集合中的每个元素发射出来，这里就是每个ProjectBean
//                )
//                .flatMap(projectBean ->//传参   发射过来的数据
//                        api.getProjectItem(1, projectBean.getId())//发射响应结果
//                )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(projectItem -> //传参  观察者接受响应结果
//                        Log.d(TAG, "projectItem: " + projectItem)
//                )
//        );


//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                api.getProducts()//发射响应结果
//                        .subscribeOn(Schedulers.io())
//                        .flatMap(new Function<BaseResponse<List<ProjectBean>>, ObservableSource<?>>() {//接受响应结果并进行处理，再发射
//                            @Override
//                            public ObservableSource<?> apply(BaseResponse<List<ProjectBean>> listBaseResponse) throws Exception {
//
//                                return Observable.fromIterable(listBaseResponse.getData());//发射集合中的每一个元素
//                            }
//                        })
//                        .flatMap(new Function<Object, ObservableSource<ProjectItem>>() {//接受集合中发过来的每一个元素，进行处理
//                            @Override
//                            public ObservableSource<ProjectItem> apply(Object projectBean) throws Exception {
//                                return api.getProjectItem(1,((ProjectBean)projectBean).getId());//调用接口，将响应结果发射出去
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<ProjectItem>() {
//                            @Override
//                            public void accept(ProjectItem projectItem) throws Exception {
//                                Log.d(TAG, "accept: " + projectItem);
//                            }
//                        });
//
//            }
//        });

//        //功能防抖  使用RxJava的throttleFirst操作符 实现防抖
        disposable = RxView.clicks(btn3)//将view的点击事件包装成observable，每次点击，就会发射数据
                .throttleFirst(1,TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
//                .delay(2,TimeUnit.SECONDS)
                .flatMap(o ->
                    api.getProjects()//发射这个接口返回的数据
                )
                .flatMap(listBaseResponse ->
                    Observable.fromIterable(listBaseResponse.getData())//把发射过来的集合内的每一个元素再发射出去
                )
                .flatMap(projectBean ->
                    api.getProjectItem(1,projectBean.getId())//接收数据后，发射下一个接口返回的数据
                )
                .observeOn(AndroidSchedulers.mainThread())//切换主线程，更新UI
                .subscribe(projectItem ->
                        Log.d(TAG, "projectItem: " + projectItem)//接收发射过来的数据
                );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();//中断请求
        }
    }
}
