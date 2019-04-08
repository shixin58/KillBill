package com.bride.thirdparty.Strategy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bride.baselib.UrlParams;
import com.bride.baselib.Urls;
import com.bride.thirdparty.ThirdPartyApplication;
import com.bride.thirdparty.bean.PhoneNumberModel;
import com.bride.thirdparty.bean.WrapperModel;
import com.bride.thirdparty.protocal.IStrategy;
import com.bride.thirdparty.util.CustomInterceptor;
import com.bride.thirdparty.util.CustomNetworkInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public class RxJavaStrategy implements IStrategy {
    private static final String TAG = RxJavaStrategy.class.getSimpleName();

    private OkHttpClient mOkHttpClient;

    private static class InstanceWrapper {
        static RxJavaStrategy INSTANCE = new RxJavaStrategy();
    }

    private RxJavaStrategy() {
        // retrofit内部集成了okhttp, converter-gson内部集成了GSON
        File file = new File(ThirdPartyApplication.getInstance().getExternalCacheDir(), "rxjava");
        Cache cache = new Cache(file, 24*1024*1024);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CustomInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .addNetworkInterceptor(new CustomNetworkInterceptor())
                .authenticator(new CustomAuthenticator())
                .cookieJar(new CustomCookieJar())
                .dns(new HttpDns())
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .pingInterval(0, TimeUnit.SECONDS)
                .cache(cache)/* 24MB */
                .build();
    }

    public static class CustomAuthenticator implements Authenticator {
        @Nullable
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            String credential = Credentials.basic("13701116418", "house888");
            return response.request().newBuilder().header("Authorization", credential).build();
        }
    }

    public static class HttpDns implements Dns {
        @Override
        public List<InetAddress> lookup(String hostname) throws UnknownHostException {
            return Dns.SYSTEM.lookup(hostname);
        }
    }

    public static class CustomCookieJar implements CookieJar {
        ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && !cookies.isEmpty()) {
                cookieStore.put(url.host(), cookies);
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : Collections.emptyList();
        }
    }

    public static RxJavaStrategy getInstance() {
        return InstanceWrapper.INSTANCE;
    }

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

    public void executeTake() {
        Observable.just(1, 2, 3, 4, 5, 6)
//                .take(5)
                .takeLast(5)
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("executeLift", integer.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void executeLift(ImageView imageView1, ImageView imageView2) {
        // 代理Observable<Integer>, 真实Observable<String>
        // Observable.subscribe(Observer)调用ObservableCreate.subscribeActual，执行Observer.onSubscribe和ObservableOnSubscribe.subscribe
        // Observable.subscribe(Observer)调用ObservableLift.subscribeActual，执行代理Observable.subscribe(代理Observer)
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i(TAG, "executeLift - subscribe");
                e.onNext(Urls.Images.LOGO);
                e.onNext(Urls.Images.BEAUTY);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .lift(new ObservableOperator<Response, String>() {
            @Override
            public Observer<? super String> apply(Observer<? super Response> observer) throws Exception {

                return new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "executeLift - lift - onSubscribe");
                        observer.onSubscribe(d);
                    }

                    @Override
                    public void onNext(String url) {
                        Log.i(TAG, "executeLift - lift - onNext - "+url);
                        Request request = new Request.Builder()
                                .url(url)
                                .get()
                                .build();
                        Call call = mOkHttpClient.newCall(request);
                        try {
                            Response response = call.execute();
                            observer.onNext(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "executeLift - lift - onError");
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "executeLift - lift - onComplete");
                        observer.onComplete();
                    }
                };
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "executeLift - subscribe - onSubscribe");
            }

            @Override
            public void onNext(Response response) {
                Log.i(TAG, "executeLift - subscribe - onNext - "+response);
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                String url = response.request().url().toString();
                if (TextUtils.equals(url, Urls.Images.BEAUTY)) {
                    imageView2.setImageBitmap(bitmap);
                } else {
                    imageView1.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "executeLift - subscribe - onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "executeLift - subscribe - onComplete");
            }
        });
    }

    public void executeMap() {
        // 线程调度+操作符
        Observable.just(new UrlParams(Urls.JUHE_MOBILE).put("phone", "13701116418").put("key", Urls.JUHE_KEY).toString(),
                new UrlParams(Urls.JUHE_MOBILE).put("phone", "18600166830").put("key", Urls.JUHE_KEY).toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, WrapperModel<PhoneNumberModel>>() {
                    @Override
                    public WrapperModel<PhoneNumberModel> apply(String s) throws Exception {
                        Request request = new Request.Builder()
                                .url(s)
                                .get()
                                .build();
                        Call call = mOkHttpClient.newCall(request);
                        Response response = call.execute();
                        Log.i(TAG, "executeMap - apply - "+response.cacheResponse()+" - "+response.networkResponse());
                        return new Gson().fromJson(response.body().string(),
                                new TypeToken<WrapperModel<PhoneNumberModel>>(){}.getType());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WrapperModel<PhoneNumberModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 完成订阅
                        Log.i(TAG, "executeMap - onSubscribe");
                        // 断开连接
//                        d.dispose();
                    }

                    @Override
                    public void onNext(WrapperModel<PhoneNumberModel> model) {
                        Log.i(TAG, "executeMap - "+model.result.toString());
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

    // cold observables：订阅后才发事件
    // 0, 1, 2, 3, ...
    public void executeInterval() {
        Observable.interval(1, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i("executeInterval", ""+aLong);
                        SystemClock.sleep(1000L);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // 发一个0
    public void executeTimer() {
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i("executeTimer", aLong.toString());
                    }
                });
    }

    // 1, 2, 3, ..., 10
    public void executeRange() {
        Observable.range(1, 10)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i("executeRange", integer.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                });
    }

    // reactive pull, backpressure
    public void executeFlowable() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("first");
                e.onNext("second");
                e.onNext("third");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<String>() {
            Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
//                s.cancel();
                s.request(1);
            }

            @Override
            public void onNext(String s) {
                Log.i("onNext", s);
                SystemClock.sleep(1000L);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    // 仅发送单个事件
    public void executeSingle() {
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                e.onSuccess("awesome");
            }
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                Log.i("executeSingle", s);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    // 不发送onNext
    public void executeCompletable() {
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {

            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.i("executeCompletable", "onComplete");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    // 相当于Single或Completable
    public void executeMaybe() {
        Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> e) throws Exception {
                e.onSuccess("ok");
            }
        }).subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                Log.i("executeMaybe", s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    // 组合多个被观察者一起发送事件，串行执行
    public void executeConcat() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i("executeConcat", integer.toString());
                    }
                });
    }

    // 并行执行
    public void executeMerge() {
        /*Observable.merge(Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i("executeMerge", aLong.toString());
                    }
                });*/

        Observable.mergeDelayError(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                e.onError(new IOException());
                e.onNext("B");
                e.onNext("C");
            }
        }), Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("a");
                e.onNext("b");
                e.onNext("c");
            }
        })).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i("executeMerge", s);
            }
        });
    }

    // 合并多个被观察者发送的事件
    public void executeZip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(1000);
                e.onNext(2);
                Thread.sleep(1000);
                e.onNext(3);
            }
        }).subscribeOn(Schedulers.newThread());
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>(){

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                Thread.sleep(1000);
                e.onNext("B");
                Thread.sleep(1000);
                e.onNext("C");
                Thread.sleep(1000);
                e.onNext("D");
            }
        }).subscribeOn(Schedulers.newThread());
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer+s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i("accept", s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.i("onComplete", "OK");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
            }
        });
    }

    public void executeCombineLatest() {
        Observable.combineLatest(Observable.just(1L, 2L, 3L),
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                new BiFunction<Long, Long, String>() {
            @Override
            public String apply(Long aLong, Long aLong2) throws Exception {
                return aLong.toString()+" "+aLong2.toString();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i("executeCombineLatest", s);
            }
        });
    }

    // 前两个数据聚合，结果再跟下一个数据聚合
    public void executeReduce() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer*integer2;
                    }
                }).subscribe(new MaybeObserver<Integer>() {// Maybe
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                Log.i("executeReduce", integer.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i("executeReduce", "onComplete");
            }
        });
    }

    // 将被观察者发送的数据收集到一个集合里
    public void executeCollect() {
        Observable.fromArray(1, 2, 3, 4, 5, 6)
                .collect(new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        integers.add(integer);
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {// Single
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                Log.i("executeCollect", TextUtils.join(",", integers));
            }
        });
    }

    // 追加事件
    public void executeStartWith() {
        Observable.just(4, 5, 6)
                .startWith(0)
                .startWithArray(1, 2, 3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i("executeStartWith", integer.toString());
                    }
                });
    }

    // 统计被观察者发送的事件数量
    public void executeCount() {
        Observable.just(1, 2, 3, 4)
                .count()
                .subscribe(new BiConsumer<Long, Throwable>() {// Single
                    @Override
                    public void accept(Long aLong, Throwable throwable) throws Exception {
                        Log.i("executeCount", aLong.toString());
                    }
                });
    }
}
