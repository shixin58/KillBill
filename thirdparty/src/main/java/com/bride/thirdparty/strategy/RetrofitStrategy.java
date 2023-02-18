package com.bride.thirdparty.strategy;

import android.os.AsyncTask;
import android.util.Log;

import com.bride.baselib.net.Urls;
import com.bride.baselib.bean.PhoneNumberModel;
import com.bride.baselib.bean.WrapperModel;
import com.bride.thirdparty.protocal.IService;
import com.bride.thirdparty.util.RetrofitClient;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <p>Created by shixin on 2018/9/20.
 */
public class RetrofitStrategy {
    private static final String TAG = RetrofitStrategy.class.getSimpleName();

    private final IService mService;
    private CustomAsyncTask mAsyncTask;

    public RetrofitStrategy() {
        // 返回代理对象 Proxy#newProxyInstance
        mService = RetrofitClient.getRetrofit().create(IService.class);
    }

    public void execute() {
        // InvocationHandler#invoke()
        Call<WrapperModel<PhoneNumberModel>> call = mService.getPhoneInfo("13701116418", Urls.JUHE_KEY);
        call.enqueue(new Callback<WrapperModel<PhoneNumberModel>>() {
            @Override
            public void onResponse(Call<WrapperModel<PhoneNumberModel>> call, Response<WrapperModel<PhoneNumberModel>> response) {
                Log.i(TAG, "execute - onResponse - "+response.body().result.toString());
            }

            @Override
            public void onFailure(Call<WrapperModel<PhoneNumberModel>> call, Throwable t) {
                Log.i(TAG, "execute - onFailure - "+t.getMessage());
            }
        });
    }

    public void executeRxJava() {
        // 调用InvocationHandler.invoke -> ServiceMethod.adapt()将Call转化为Observable
        Observable<WrapperModel<PhoneNumberModel>> observable = mService.getPhoneInfo2("13701116418", Urls.JUHE_KEY);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WrapperModel<PhoneNumberModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "executeRxJava - onSubscribe - what");
                    }

                    @Override
                    public void onNext(WrapperModel<PhoneNumberModel> model) {
                        Log.i(TAG, "executeRxJava - onNext - "+model.result.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "executeRxJava - onError - "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "executeRxJava - onComplete - what");
                    }
                });
    }

    // 封装了Handler和线程池
    public void testAsyncTask() {
        Call<WrapperModel<PhoneNumberModel>> call = mService.getPhoneInfo("13701116418", Urls.JUHE_KEY);
        mAsyncTask = new CustomAsyncTask(call);
        mAsyncTask.execute("A", "B", "C");
    }

    public static class CustomAsyncTask extends AsyncTask<String, Integer, String> {
        Call<WrapperModel<PhoneNumberModel>> mCall;

        CustomAsyncTask(Call<WrapperModel<PhoneNumberModel>> call) {
            mCall = call;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                publishProgress(1, 2, 3);
                Response<WrapperModel<PhoneNumberModel>> response = mCall.execute();
                if(response.body() != null && response.body().result != null)
                    return response.body().result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i(TAG, "testAsyncTask - onPostExecute - "+result);
        }
    }

    public void onDestroy() {
        if (mAsyncTask != null) {
            mAsyncTask.cancel(true);
            mAsyncTask = null;
        }
        runGc();
    }

    public static void runGc() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Runtime.getRuntime().gc();
                try {
                    Thread.sleep(99L, 1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.runFinalization();
            }
        }.start();
    }
}
