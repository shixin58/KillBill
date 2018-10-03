package com.max.thirdparty.Strategy;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.max.thirdparty.bean.PhoneNumberModel;
import com.max.thirdparty.bean.WrapperModel;
import com.max.thirdparty.protocal.IStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public class RxJavaStrategy implements IStrategy {
    @Override
    public void execute() {
//        Observable.just("Good", "Better", "Best");
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>(){
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i("Observable", "subscribe");
                e.onNext("Good");
                e.onNext("Better");
                e.onNext("Best");
                e.onComplete();
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("Observer", "onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("Observer", "onNext "+s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("Observer", "onComplete");
                    }
                });
    }

    public void executeMap() {
        // 线程调度+操作符
        Observable.just("http://apis.juhe.cn/mobile/get?phone=13701116418&key=9a4329bdf84fa69d193ce601c22b949d",
                "http://apis.juhe.cn/mobile/get?phone=18600166830&key=9a4329bdf84fa69d193ce601c22b949d")
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, WrapperModel<PhoneNumberModel>>() {
                    @Override
                    public WrapperModel<PhoneNumberModel> apply(String s) throws Exception {
                        Log.i("executeMap", "apply");
                        // retrofit内部集成了okhttp, converter-gson内部集成了GSON
                        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                .readTimeout(5, TimeUnit.SECONDS)
                                .build();
                        Request request = new Request.Builder()
                                .url(s)
                                .get()
                                .build();
                        Call call = okHttpClient.newCall(request);
                        return new Gson().fromJson(call.execute().body().string(),
                                new TypeToken<WrapperModel<PhoneNumberModel>>(){}.getType());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WrapperModel<PhoneNumberModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 完成订阅
                        Log.i("executeMap", "onSubscribe");
                        // 断开连接
//                        d.dispose();
                    }

                    @Override
                    public void onNext(WrapperModel<PhoneNumberModel> model) {
                        Log.i("executeMap", model.result.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void executeFlatMap() {
        Map<String, List<String>> listMap = getListMap();
        Observable.fromIterable(listMap.keySet())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        Log.i("executeFlatMap", "apply - "+s);
                        return Observable.fromIterable(listMap.get(s));
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("executeFlatMap", "onNext - "+s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Map<String, List<String>> getListMap() {
        Map<String, List<String>> map = new TreeMap<>();
        List<String> listA = new ArrayList<>();
        listA.add("Apple");
        listA.add("Arm");
        map.put("A", listA);
        List<String> listB = new ArrayList<>();
        listB.add("Banana");
        listB.add("Bag");
        map.put("B", listB);
        List<String> listC = new ArrayList<>();
        listC.add("Cat");
        listC.add("Call");
        listC.add("Candle");
        map.put("C", listC);
        return map;
    }
}
