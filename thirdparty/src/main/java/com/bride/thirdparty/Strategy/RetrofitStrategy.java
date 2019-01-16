package com.bride.thirdparty.Strategy;

import android.os.AsyncTask;
import android.util.Log;

import com.bride.thirdparty.protocal.IService;
import com.bride.thirdparty.protocal.IStrategy;
import com.bride.thirdparty.bean.PhoneNumberModel;
import com.bride.thirdparty.bean.WrapperModel;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>Created by shixin on 2018/9/20.
 */
public class RetrofitStrategy implements IStrategy {

    private IService mService;

    public RetrofitStrategy() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://apis.juhe.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 返回代理对象
        mService = retrofit.create(IService.class);
    }

    @Override
    public void execute() {
        Call<WrapperModel<PhoneNumberModel>> call = mService.getPhoneInfo("13701116418", "9a4329bdf84fa69d193ce601c22b949d");
        call.enqueue(new Callback<WrapperModel<PhoneNumberModel>>() {
            @Override
            public void onResponse(Call<WrapperModel<PhoneNumberModel>> call, Response<WrapperModel<PhoneNumberModel>> response) {
                Log.i("onResponse", response.body().result.toString());
            }

            @Override
            public void onFailure(Call<WrapperModel<PhoneNumberModel>> call, Throwable t) {
                Log.i("onFailure", t.getMessage());
            }
        });
    }

    public void executeRxJava() {
        // 调用InvocationHandler.invoke -> ServiceMethod.adapt()将Call转化为Observable
        Observable<WrapperModel<PhoneNumberModel>> observable = mService.getPhoneInfo2("13701116418", "9a4329bdf84fa69d193ce601c22b949d");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WrapperModel<PhoneNumberModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("onSubscribe", "what");
                    }

                    @Override
                    public void onNext(WrapperModel<PhoneNumberModel> model) {
                        Log.i("onNext", model.result.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("onError", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("onComplete", "what");
                    }
                });
    }

    // 封装了Handler和线程池
    public void testAsyncTask() {
        Call<WrapperModel<PhoneNumberModel>> call = mService.getPhoneInfo("13701116418", "9a4329bdf84fa69d193ce601c22b949d");
        AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    publishProgress(1, 2, 3);
                    Response<WrapperModel<PhoneNumberModel>> response = call.execute();
                    if(response!=null&&response.body()!=null&&response.body().result!=null)
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
                Log.i("onPostExecute", result);
                cancel(true);
            }
        };
        asyncTask.execute("A", "B", "C");
    }
}
