package com.bride.thirdparty.strategy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bride.baselib.net.UrlParams;
import com.bride.baselib.net.Urls;
import com.bride.thirdparty.ThirdPartyApplication;
import com.bride.baselib.bean.PhoneNumberModel;
import com.bride.baselib.bean.WrapperModel;
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
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeEmitter;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableOperator;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
 * OkHttpClient RealCall分发器维护ArrayDeque, 调用size(), add(Object), remove(Object)
 * OkHttpClient控制缓存相关类：CacheControl, CacheStrategy, Cache, InternalCache, DiskLruCache
 * <p>Created by shixin on 2018/9/7.
 */
public class RxJavaStrategy {
    private static final String TAG = RxJavaStrategy.class.getSimpleName();

    private final OkHttpClient mOkHttpClient;

    private static class InstanceWrapper {
        private static final RxJavaStrategy INSTANCE = new RxJavaStrategy();
    }

    private RxJavaStrategy() {
        // retrofit内部集成了okhttp, converter-gson内部集成了GSON
        // /storage/emulated/0/Android/data/package/cache/rxjava, 开启24MB大小的磁盘缓存
        File file = new File(ThirdPartyApplication.getInstance().getExternalCacheDir(), "rxjava");
        Cache cache = new Cache(file, 24 * 1024 * 1024L); // 24MB
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
                .cache(cache)
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
        // ConcurrentHashMap只在必要的代码块synchronized加锁，使用锁分段技术Segment[]。
        // 实际应用：EventBus#stickyEvents
        final ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
        @Override
        public void saveFromResponse(@NonNull HttpUrl url, List<Cookie> cookies) {
            if (!cookies.isEmpty()) {
                cookieStore.put(url.host(), cookies);
            }
        }

        @NonNull
        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : Collections.emptyList();
        }
    }

    public static RxJavaStrategy getInstance() {
        return InstanceWrapper.INSTANCE;
    }

    public void executeJust() {
        // Flowable#fromArray(变长参数varargs) -> FlowableFromArray
        Flowable.just("Try again!", "Hello World!", "May I ask you out?")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i(TAG, "Flowable#just onSubscribe");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "Flowable#just onNext "+s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "Flowable#just onError", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Flowable#just onComplete");
                    }
                });
    }

    public void executeObservable() {
        // Java8 Lambda表达式
        // Observable#create() -> ObservableCreate
        Observable.create((ObservableOnSubscribe<String>) e -> {
            Log.i(TAG, "executeObservable() - ObservableOnSubscribe#subscribe");
            e.onNext("Good");
            e.onNext("Better");
            e.onNext("Best");
            e.onComplete();
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "executeObservable() - Observer#onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "executeObservable() - Observer#onNext "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "executeObservable() - Observer#onError "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "executeObservable() - Observer#onComplete");
            }
        });
    }

    public void executeTake() {
        // Observable#fromArray() -> ObservableFromArray
        Observable.just(1, 2, 3, 4, 5, 6)
                .take(5)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "executeTake - onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "executeTake - onNext "+integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "executeTake - onError "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "executeTake - onComplete");
                    }
                });
    }

    public void executeLift(ImageView imageView1, ImageView imageView2) {
        // 代理Observable<Integer>, 真实Observable<String>
        // Observable.subscribe(Observer)调用ObservableCreate.subscribeActual，执行Observer.onSubscribe和ObservableOnSubscribe.subscribe
        // Observable.subscribe(Observer)调用ObservableLift.subscribeActual，执行代理Observable.subscribe(代理Observer)
        Observable.fromArray(Urls.Images.LOGO, Urls.Images.LADY)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .lift(new CustomOperator<>())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Response>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.i(TAG, "executeLift - subscribe - Observer#onSubscribe");
                }

                @Override
                public void onNext(Response response) {
                    Log.i(TAG, "executeLift - subscribe - Observer#onNext - "+response);
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    String url = response.request().url().toString();
                    if (TextUtils.equals(url, Urls.Images.LADY)) {
                        imageView2.setImageBitmap(bitmap);
                    } else {
                        imageView1.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, "executeLift - subscribe - Observer#onError");
                }

                @Override
                public void onComplete() {
                    Log.i(TAG, "executeLift - subscribe - Observer#onComplete");
                }
            });
    }

    public final class CustomObserver<T> implements Observer<T>, Disposable {

        final Observer<? super Response> downstream;

        Disposable upstream;

        public CustomObserver(Observer<? super Response> downstream) {
            this.downstream = downstream;
        }

        @Override
        public void onSubscribe(Disposable d) {
            Log.i(TAG, "CustomObserver#onSubscribe");
            if (upstream != null) {
                d.dispose();
            } else {
                upstream = d;
                downstream.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T o) {
            Log.i(TAG, "CustomObserver#onNext - "+o.toString());
            Request request = new Request.Builder()
                    .url(o.toString())
                    .get()
                    .tag("RxJava")
                    .build();

            Call call = mOkHttpClient.newCall(request);

            try {
                Response response = call.execute();
                downstream.onNext(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            downstream.onError(e);
            Log.i(TAG, "CustomObserver#onError");
        }

        @Override
        public void onComplete() {
            downstream.onComplete();
            Log.i(TAG, "CustomObserver#onComplete");
        }

        @Override
        public void dispose() {
            Log.i(TAG, "CustomObserver#dispose");
            upstream.dispose();
        }

        @Override
        public boolean isDisposed() {
            return upstream.isDisposed();
        }
    }

    final class CustomOperator<T> implements ObservableOperator<Response, T> {
        @Override
        public Observer<T> apply(Observer<? super Response> downstream) {
            return new CustomObserver<>(downstream);
        }
    }

    public void executeMap() {
        String url1 = new UrlParams(Urls.JUHE_MOBILE)
                .put("phone", "13701116418")
                .put("key", Urls.JUHE_KEY)
                .toString();
        String url2 = new UrlParams(Urls.JUHE_MOBILE)
                .put("phone", "18600166830")
                .put("key", Urls.JUHE_KEY)
                .toString();
        String url3 = new UrlParams(Urls.JUHE_MOBILE)
                .put("phone", "17319311840")
                .put("key", Urls.JUHE_KEY)
                .toString();
        Observable.just(url1, url2, url3)// ObservableFromArray
                .observeOn(Schedulers.computation())// ObservableObserveOn
                .map((Function<String, WrapperModel<PhoneNumberModel>>) s -> {// ObservableMap
                    Request request = new Request.Builder()
                            .url(s)
                            .get()
                            .tag("RxJava")
                            .build();

                    Call call = mOkHttpClient.newCall(request);

                    Response response = call.execute();

                    Log.i(TAG, "map - Function#apply "+response.cacheResponse()+" - "+response.networkResponse());
                    Thread.sleep(5000L);
                    return new Gson()
                            .fromJson(response.body().string(), new TypeToken<WrapperModel<PhoneNumberModel>>(){}.getType());
                })
                .observeOn(Schedulers.computation())
                .map(new Function<WrapperModel<PhoneNumberModel>, String>() {
                    @Override
                    public String apply(WrapperModel<PhoneNumberModel> model) throws Exception {
                        Log.i(TAG, "map - Function#apply "+model.result);
                        return model.reason;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "map - Observer#onSubscribe");
                    }

                    @Override
                    public void onNext(String model) {
                        Log.i(TAG, "map - Observer#onNext "+model);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "map - Observer#onError "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "map - Observer#onComplete");
                    }
                });
    }

    public void executeFlatMap() {
        Map<String, List<String>> listMap = getListMap();
        Observable.fromIterable(listMap.keySet())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<String, ObservableSource<String>>) s -> {
                    Log.i(TAG, "flatMap - apply "+s);
                    Thread.sleep(5000L);
                    return Observable.fromIterable(Objects.requireNonNull(listMap.get(s)));
                }).observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "flatMap - onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "flatMap - onNext "+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "flatMap - onError "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "flatMap - onComplete");
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
        Observable.interval(1, 1, TimeUnit.MILLISECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.newThread())
            .subscribe(new Observer<Long>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.i(TAG, "Observable#interval onSubscribe");
                }

                @Override
                public void onNext(Long aLong) {
                    Log.i(TAG, "Observable#interval onNext - "+aLong);
                    SystemClock.sleep(10000L);
                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, "Observable#interval onError", e);
                }

                @Override
                public void onComplete() {
                    Log.i(TAG, "Observable#interval onComplete");
                }
            });
    }

    // 发一个0
    public void executeTimer() {
        Observable.timer(1, TimeUnit.SECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> Log.i(TAG, "Observable#timer accept "+aLong.toString())).dispose();
    }

    // 1, 2, 3, ..., 10
    public void executeRange() {
        Observable.range(1, 10)
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "Observable#range onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "Observable#range onNext "+integer.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Observable#range onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Observable#range onComplete");
                    }
                });
    }

    // reactive pull, backpressure
    // MissingBackpressureException: Can't deliver value 128 due to lack of requests
    // MissingBackpressureException: Buffer is full
    public void executeFlowable() {
        Flowable.interval(100, 100, TimeUnit.MILLISECONDS, Schedulers.computation())
                .onBackpressureBuffer(256)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long l) {
                        Log.i(TAG, "Flowable#create onNext "+l);
                        SystemClock.sleep(1000L);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "Flowable#create onError", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Flowable#create onComplete");
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
                Log.i(TAG, "Single#create onSubscribe");
            }

            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "Single#create onSuccess "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Single#create onError", e);
            }
        });
    }

    // 不发送onNext
    public void executeCompletable() {
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                e.onError(new IllegalStateException());
            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Completable#create onSubscribe");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Completable#create onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Completable#create onError", e);
            }
        });
    }

    // 相当于Single或Completable
    public void executeMaybe() {
        Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> e) throws Exception {
                e.onSuccess("maybe");
            }
        }).subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Maybe#create onSubscribe");
            }

            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "Maybe#create onSuccess "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Maybe#create onError", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Maybe#create onComplete");
            }
        });
    }

    // 组合多个被观察者一起发送事件，串行执行
    public void executeConcat() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "Observable#concat onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "Observable#concat onNext "+integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Observable#concat onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Observable#concat onComplete");
                    }
                });
    }

    // 并行执行
    public void executeMerge() {
        /*Observable.merge(Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(3, 3, 1, 2, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "Observable#merge onSubscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i(TAG, "Observable#merge onNext "+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Observable#merge onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Observable#merge onComplete");
                    }
                });*/

        Observable.mergeDelayError(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                e.onError(new IOException());
                e.onNext("B");
                e.onNext("C");
                e.onComplete();
            }
        }), Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("a");
                e.onNext("b");
                e.onNext("c");
                e.onComplete();
            }
        })).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Observable.mergeDelayError onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "Observable.mergeDelayError onNext "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Observable.mergeDelayError onError", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Observable.mergeDelayError onComplete");
            }
        });
    }

    // 合并多个被观察者发送的事件
    public void executeZip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                SystemClock.sleep(1000L);
                e.onError(new IOException());
                SystemClock.sleep(1000L);
                e.onNext(2);
                SystemClock.sleep(1000L);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>(){

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                SystemClock.sleep(1000L);
                e.onNext("B");
                SystemClock.sleep(1000L);
                e.onNext("C");
                SystemClock.sleep(1000L);
                e.onNext("D");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer+s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Observable#zip onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "Observable#zip onNext "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Observable#zip onError", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Observable#zip onComplete");
            }
        });
    }

    public void executeCombineLatest() {
        Observable.combineLatest(Observable.just(1L, 2L, 3L),
                Observable.intervalRange(4, 4, 1, 1, TimeUnit.SECONDS),
                new BiFunction<Long, Long, String>() {
            @Override
            public String apply(Long aLong, Long aLong2) throws Exception {
                return String.valueOf(aLong)+" "+String.valueOf(aLong2);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Observable#combineLatest onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "Observable#combineLatest onNext "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Observable#combineLatest onError", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Observable#combineLatest onComplete");
            }
        });
    }

    // 前两个数据聚合，结果再跟下一个数据聚合
    public void executeReduce() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer1, Integer integer2) throws Exception {
                        return integer1 * integer2;
                    }
                }).subscribe(new MaybeObserver<Integer>() {// Maybe
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "reduce onSubscribe");
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.i(TAG, "reduce onSuccess "+integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "reduce onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "reduce onComplete");
                    }
                });
    }

    // 将被观察者发送的数据收集到一个集合里
    public void executeCollect() {
        Observable.fromArray(1, 2, 3, 4, 5, 6)
                .collect(new Supplier<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> get() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        integers.add(integer);
                    }
                }).subscribe(new SingleObserver<ArrayList<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "collect onSubscribe");
                    }

                    @Override
                    public void onSuccess(ArrayList<Integer> integers) {
                        Log.i(TAG, "collect onSuccess "+TextUtils.join(",", integers));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "collect onError", e);
                    }
                });
    }

    // 统计被观察者发送的事件数量
    public void executeCount() {
        Observable.just(1, 2, 3, 4)
                .count()
                .subscribe(new SingleObserver<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "count onSubscribe");
                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        Log.i(TAG, "count onSuccess "+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "count onError", e);
                    }
                });
    }

    // 追加事件
    public void executeStartWith() {
        Observable.just(4, 5, 6)
                .startWithArray(1, 2, 3)
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "startWith onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "startWith onNext "+integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "startWith onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "startWith onComplete");
                    }
                });
    }

    public void onDestroy(@NonNull String tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
