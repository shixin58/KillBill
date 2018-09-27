package com.max.thirdparty;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * <p>Created by shixin on 2018/9/27.
 */
public class RxBus {
    private static volatile RxBus instance;
    // 采用分段锁，效率比Hashtable高16倍
    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    private RxBus(){}

    public static RxBus getInstance() {
        if(instance==null) {
            synchronized (RxBus.class) {
                if(instance==null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public <T> Observable<T> register(@NonNull Class<T> tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if(subjectList == null) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }
        Subject<T> subject;
        subjectList.add(subject=PublishSubject.create());
        return subject;
    }

    public void unregister(@NonNull Class<?> tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if(subjectList!=null) {
            subjectMapper.remove(tag);
        }
    }

    public void unregister(@NonNull Class<?> tag, @NonNull Observable observable) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if(subjectList != null) {
            subjectList.remove(observable);
            if(subjectList.isEmpty()) {
                subjectMapper.remove(tag);
            }
        }
    }

    public void post(@NonNull Object event) {
        List<Subject> subjectList = subjectMapper.get(event.getClass());
        if(subjectList!=null) {
            for(Subject subject:subjectList) {
                subject.onNext(event);
            }
        }
    }
}
