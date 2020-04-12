package com.gmm.www.rxjavademo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gmm.www.rxjavademo.api.WanAndroidApi;
import com.gmm.www.rxjavademo.bean.BaseResponse;
import com.gmm.www.rxjavademo.bean.ProjectBean;
import com.gmm.www.rxjavademo.util.HttpUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:gmm
 * @date:2020/3/2
 * @类说明:
 */
public class OpFragment extends ListFragment {

    private static final String TAG = "OpFragment";
    private ArrayAdapter<String> adapter;

    public static OpFragment newInstance() {
        OpFragment fragment = new OpFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] array = new String[]{"map",
                                        "flatMap",
                                        "concatMap",
                                        "buffer",
                                        "retry",
                                        "retryWhen",
                                        "flowableTest"};
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,array);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String item = adapter.getItem(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0:
                map();
                break;

            case 1:
                flatMap();
                break;

            case 2:
                concatMap();
                break;

            case 3:
                buffer();
                break;

            case 4:
                retry();
                break;

            case 5:
                retryWhen();
                break;

            case 6:
                flowableTest();
                break;
        }
    }

    private void map() {//采用RxJava基于流式的链式操作
        //java
        Observable.create(new ObservableOnSubscribe<Integer>() {
            //1.被观察者发射数据，数据的类型为整型 1，2，3
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }

            //2.使用map变换操作符中的Function函数对被观察者发射的数据进行统一变换：整型->字符串
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + "_map";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                //观察者接收的数据是变换后的数据：字符串
                Log.e(TAG, "accept: "+s );
            }
        });

        //lambda
//        Observable.create(emitter -> {
//            emitter.onNext(1);
//            emitter.onNext(2);
//            emitter.onNext(3);
//        }).map(i -> i+"_map")
//          .subscribe(s -> Log.e(TAG, "map: " +s));
    }

    /**
     * flatMap将一个发射数据的Observable变换为多个Observables，然后将它们发射的数据合并后放进一个单独的Observable。
     */
    private void flatMap() {//采用RxJava基于流式的链式操作
        //java
        Observable.create(new ObservableOnSubscribe<Integer>() {
            //被观察者发射数据 类型为整型 1，2，3
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
            //使用fratMap变换操作符，将发射过来的数据进行变换，再发射出去
        }).flatMap(new Function<Integer, ObservableSource<String>>() {

            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 10; i < 14; i++) {
                    list.add(integer + "_flatMap_" + i);
                }
                //通过flatMap将被观察者发射的数据序列先进行拆分（展开），再将每个数据转换为一个新的发送三个string数据
                //最终合并，再逐个发射给观察者
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                //最终接收的数据是通过flatMap变换后的数据，数据个数为12个
                Log.e(TAG, "accept: " + s);
                /**
                 * 1_flatMap_11
                 * 1_flatMap_12
                 * 1_flatMap_13
                 * 2_flatMap_10
                 * 2_flatMap_11
                 * 2_flatMap_12
                 * 2_flatMap_13
                 * 3_flatMap_10
                 * 3_flatMap_11
                 * 3_flatMap_12
                 * 3_flatMap_13
                 */
            }
        });

        //lambda
//        Observable.create(emitter -> {
//            emitter.onNext(1);
//            emitter.onNext(2);
//            emitter.onNext(3);
//        }).flatMap(integer -> {
//            List<String> list = new ArrayList<>();
//            for (int i = 10; i < 14; i++) {
//                list.add(integer + "_flatMap_" + i);
//            }
//            return Observable.fromIterable(list);
//        }).subscribe(s -> Log.e(TAG, "flatMap: " + s));
    }

    private void concatMap() {
        //java
        Observable.create(new ObservableOnSubscribe<Integer>() {
            //被观察者发射数据 类型为整型 1，2，3
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
            //使用concatMap变换操作符，将发射过来的数据进行变换，再发射出去
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 10; i < 14; i++) {
                    list.add(integer + "_concatMap_" + i);
                }
                //通过flatMap将被观察者发射的数据序列先进行拆分（展开），再将每个数据转换为一个新的发送三个string数据
                //最终合并，再逐个发射给观察者
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: " + s );
            }
        });
    }

    /**
     * RxJava buffer操作符：对被观察者发射过来的数据，进行分批缓存处理
     */
    private void buffer() {
        //java
        //被观察者发射5个数字
        Observable.just(1,2,3,4,5)
                //设置缓存区大小、步长
                .buffer(3,1)//缓存区大小：每次从被观察者中获取的事件数量  步长：每次获取新数据的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.e(TAG, "onNext:缓存区里的数据数量："+ integers.size() );
                        for (Integer value : integers) {
                            Log.e(TAG, "onNext: 数据：" + value );
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " + "对Complete事件作出响应" );
                    }
                });

        //lambda
//        Observable.just(1,2,3,4,5,6)
//                .buffer(3,3)
//                .subscribe(integers -> {
//                    Log.e(TAG, "缓存区里的数据数量："+integers.size() );
//                    for (Integer value : integers) {
//                        Log.e(TAG, "数据："+value );
//                    }
//                },e -> Log.e(TAG, "对Error事件作出响")
//                ,() -> Log.e(TAG, "对Complete事件作出响应"));
    }

    int retryCount = 0;
    private void retry() {
        retryCount = 0;
        WanAndroidApi api = HttpUtil.getRestrofit().create(WanAndroidApi.class);

        api.getProjects()
                .retry(new Predicate<Throwable>() {
                    @Override
                    public boolean test(Throwable throwable) throws Exception {
                        if (retryCount < 4) {
                            Log.e(TAG, "重试：" + retryCount );
                            retryCount++;
                            return true;
                        }
                        return false;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ProjectBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<ProjectBean>> listBaseResponse) {
                        Log.e(TAG, "onNext: " + listBaseResponse.getData() );
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });

//        api.getProjects()
//                .retry(throwable -> {
//                   if (retryCount < 4) {
//                       Log.e(TAG, "重试：" + retryCount );
//                       retryCount++;
//                       return true;
//                   }
//                   return false;
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(listBaseResponse -> Log.e(TAG, "onNext: " + listBaseResponse.getData()),
//                            e -> e.printStackTrace(),
//                            () -> Log.e(TAG, "onComplete: "));
    }

    //可重试次数
    int maxRetryCount = 10;
    //当前已重试次数
    int currentRetryCount = 0;
    //重试等待时间
    int waitRetryTime = 0;
    private void retryWhen() {
        WanAndroidApi api = HttpUtil.getRestrofit().create(WanAndroidApi.class);

        Observable<BaseResponse<List<ProjectBean>>> observable = api.getProjects();
        //发送网络请求 & 通过retryWhen() 进行重试
        //注：主要异常才会回调retryWhen() 进行重试
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                //参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                //***通过flatMap操作符，将throwableObservable进行转换处理，转换成新的Observable，再发射数据
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        Log.e(TAG, "发生异常 = " + throwable.toString());
                        /**
                         * 需求1：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常 = IO异常  才选择重试
                         */
                        if (throwable instanceof IOException) {
                            Log.e(TAG, "属于IO异常，需要重试" );

                            /**
                             * 需求2：限制重试次数
                             * 即，当已重试次数 < 设置的最大重试次数，才选择重试
                             */
                            if (currentRetryCount < maxRetryCount) {
                                //记录重试次数
                                currentRetryCount++;
                                Log.e(TAG, "重试次数 = "+ currentRetryCount);

                                /**
                                 * 实现重试：
                                 * 通过返回的Observable发送的事件=Next事件， 从而使得retryWhen()重订阅，最终实现重试功能
                                 *
                                 * 需求3 延迟一段时间再重试：
                                 * 采用delay操作符=延迟一段时间再发送  以实现重试间隔设置
                                 *
                                 * 需求4 遇到的异常越多，时间越长
                                 * 在delay操作符的等待时间内设置=每重试1次，增多延迟重试时间1s
                                 */
                                waitRetryTime = 1000 + currentRetryCount*1000;
                                Log.e(TAG, "等待重试时间=" + waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime,TimeUnit.MILLISECONDS);
                            } else {
                                //若重试次数已 > 设置的最大重试次数，则不再重试
                                //通过发射error来停止重试（可在观察者的onError()中获取信息）
                                return Observable.error(new Throwable("尝试次数已超过 = " + currentRetryCount + "，不再重试"));
                            }
                        } else {
                            //若发射的异常不属于IO异常，则不重试
                            //通过返回的Observable发送的时间=Error事件 实现（可在观察者的onError()中获取信息）
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }

                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ProjectBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<ProjectBean>> listBaseResponse) {
                        //接收到服务器返回的数据
                        Log.e(TAG, "onNext: " + listBaseResponse.getData() );
                    }

                    @Override
                    public void onError(Throwable e) {
                        //获取停止重试的信息
                        Log.e(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

//        Disposable disposable = observable.retryWhen(throwableObservable -> {
//            return throwableObservable.flatMap(throwable -> {
//                Log.e(TAG, "发生异常 = " + throwable.toString());
//                if (throwable instanceof IOException) {
//                    Log.e(TAG, "属于IO异常，需要重试" );
//                    if (currentRetryCount < maxRetryCount) {
//                        currentRetryCount++;
//                        Log.e(TAG, "重试次数 = "+ currentRetryCount);
//                        waitRetryTime = 1000 + currentRetryCount * 1000;
//                        Log.e(TAG, "等待重试时间=" + waitRetryTime);
//                        return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);
//                    } else {
//                        return (Observable)Observable.error(new Throwable("尝试次数已超过 = " + currentRetryCount + "，不再重试"));
//                    }
//                } else {
//                    return (Observable)Observable.error(new Throwable("发生了非网络异常（非IO异常）"));
//                }
//            });
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(listBaseResponse -> Log.e(TAG, "onNext:" + listBaseResponse)
//                        , e -> Log.e(TAG, e.toString())
//                        , () -> Log.e(TAG, "onComplete"));


    }

    /**
     * 背压：Flowable
     * 不设置Subscription的情况
     * 查看源码可知RxJava2.0以上，Flowable的背压大小为128
     * 测试结果：
     * 1.使用Flowable不设置subscription.request();的情况下，下游无法收到数据。
     * 2.使用Flowable不设置subscription.request();的情况下，上游发送数据超过128，报出MissingBackpressureException
     */
    private void flowableTest() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 129; i++) {
                    Log.e(TAG, "emitter="+i);
                    emitter.onNext(i);
                }
            }
        },BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
//                        s.request(Long.MAX_VALUE);
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError:" + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }
}
