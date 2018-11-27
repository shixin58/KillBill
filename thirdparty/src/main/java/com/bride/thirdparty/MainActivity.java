package com.bride.thirdparty;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.Strategy.RetrofitStrategy;
import com.bride.thirdparty.Strategy.RxJavaStrategy;
import com.bride.thirdparty.Strategy.SystemStrategy;
import com.bride.thirdparty.bean.MessageEvent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    Observable<MessageEvent> mObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.tv_volley).setOnClickListener(this);
        findViewById(R.id.tv_eventbus).setOnClickListener(this);
        findViewById(R.id.tv_landscape).setOnClickListener(this);
    }

    private void initData() {
        // 1、测试RxBus
        mObservable = RxBus.getInstance().register(MessageEvent.class);
        // Consumer和Observer均可使用
        mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MessageEvent messageEvent) {
                        Log.i("MessageEvent", ""+messageEvent.info);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 2、测试Retrofit
        RetrofitStrategy retrofitStrategy = new RetrofitStrategy();
//        retrofitStrategy.execute();
//        retrofitStrategy.executeRxJava();
//        retrofitStrategy.testAsyncTask();

        // 3、测试RxJava
        RxJavaStrategy rxJavaStrategy = new RxJavaStrategy();
//        rxJavaStrategy.execute();
//        rxJavaStrategy.executeMap();
//        rxJavaStrategy.executeFlatMap();
//        rxJavaStrategy.executeInterval();
//        rxJavaStrategy.executeTimer();
//        rxJavaStrategy.executeRange();
//        rxJavaStrategy.executeZip();
//        rxJavaStrategy.executeConcat();
//        rxJavaStrategy.executeMerge();
//        rxJavaStrategy.executeCombineLatest();
//        rxJavaStrategy.executeReduce();
//        rxJavaStrategy.executeCollect();
//        rxJavaStrategy.executeStartWith();
//        rxJavaStrategy.executeCount();
//        rxJavaStrategy.executeTake();
//        rxJavaStrategy.executeLift();

        // 4、测试请求系统权限
        new SystemStrategy().execute();
        System.out.println("getDeviceId "+SystemStrategy.getDeviceId(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_volley:
                VolleyTestActivity.Companion.openActivity(this);
                break;
            case R.id.tv_eventbus:
                EventBusTestActivity.openActivity(this);
                break;
            case R.id.tv_landscape:
                LandscapeActivity.openActivity(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(MessageEvent.class, mObservable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("getDeviceId "+SystemStrategy.getDeviceId(this));
                }
                break;
        }
    }
}
