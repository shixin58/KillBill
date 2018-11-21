package com.bride.thirdparty;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

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
        testHeight();
    }

    private void testHeight() {
        TextView tvHeight = findViewById(R.id.tv_height);
        String source = getResources().getString(R.string.get_height);
        tvHeight.setText(source);
        tvHeight.post(new Runnable() {
            @Override
            public void run() {
                Log.i("View#getHeight", tvHeight.getHeight()+"");
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TextPaint textPaint = new TextPaint();
            float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
            textPaint.setTextSize(textSize);
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            StaticLayout staticLayout = StaticLayout.Builder.obtain(source, 0, source.length(), textPaint, (int) width)
                    .build();
            Log.i("Layout#getHeight", staticLayout.getLineCount()+"-"+staticLayout.getHeight());
        }else {
            TextPaint textPaint = new TextPaint();
            float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
            textPaint.setTextSize(textSize);
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            StaticLayout staticLayout = new StaticLayout(source, textPaint, (int) width,
                    Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
            Log.i("Layout#getHeight", staticLayout.getLineCount()+"-"+staticLayout.getHeight());
        }
    }

    private void initData() {
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

        RetrofitStrategy retrofitStrategy = new RetrofitStrategy();
//        retrofitStrategy.execute();
//        retrofitStrategy.executeRxJava();
//        retrofitStrategy.testAsyncTask();

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
