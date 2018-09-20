package com.max.thirdparty.Strategy;

import android.util.Log;

import com.max.thirdparty.protocal.IStrategy;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public class RxJavaStrategy implements IStrategy {
    @Override
    public void execute() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>(){
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Good");
                e.onNext("Better");
                e.onComplete();
            }
        });

        observable.subscribe(new Observer<String>() {
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
}
