package com.bride.thirdparty.util;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * <p>Created by shixin on 2018/9/27.
 */
public class RxBus {

    private static volatile RxBus instance;

    private final ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    private RxBus(){}

    public static RxBus getInstance() {
        if(instance == null) {
            synchronized (RxBus.class) {
                if(instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public <T> Subject<T> register(@NonNull Class<T> tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if(subjectList == null) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }
        Subject<T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    public void unregister(@NonNull Class<?> tag, @NonNull Subject subject) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if(subjectList != null) {
            boolean removed = subjectList.remove(subject);
            if (!removed) {
                throw new IllegalStateException("还未注册，不能注销!");
            }
            subject.onComplete();
            if(subjectList.isEmpty()) {
                subjectMapper.remove(tag);
            }
        }
    }

    // Observer.onNext调用Consumer.accept
    public void post(@NonNull Object event) {
        List<Subject> subjectList = subjectMapper.get(event.getClass());
        if(subjectList != null) {
            for(Subject subject : subjectList) {
                subject.onNext(event);
            }
        }
    }
}
