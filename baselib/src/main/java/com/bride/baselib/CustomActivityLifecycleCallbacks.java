package com.bride.baselib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过Application注册注销，监控所有Activity生命周期
 * <p>Created by shixin on 2018/10/1.
 */
public class CustomActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private int mVisibleActivityCount = 0;
    private int mActivityCount = 0;

    public List<FrontBackListener> mFrontBackListeners = new ArrayList<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtils.INSTANCE.v(LogUtils.TAG_LIFECYCLE_ACTIVITY, "onActivityCreated - "+activity.getClass().getSimpleName()+"|"+savedInstanceState);
        mActivityCount++;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtils.INSTANCE.v(LogUtils.TAG_LIFECYCLE_ACTIVITY, "onActivityStarted - "+activity.getClass().getSimpleName());
        mVisibleActivityCount++;
        if (mVisibleActivityCount == 1) {
            for (FrontBackListener listener : mFrontBackListeners) {
                listener.onFront();
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtils.INSTANCE.v(LogUtils.TAG_LIFECYCLE_ACTIVITY, "onActivityResumed - "+activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtils.INSTANCE.v(LogUtils.TAG_LIFECYCLE_ACTIVITY, "onActivityPaused - "+activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtils.INSTANCE.v(LogUtils.TAG_LIFECYCLE_ACTIVITY, "onActivityStopped - "+activity.getClass().getSimpleName());
        mVisibleActivityCount--;
        if (mVisibleActivityCount == 0) {
            for (FrontBackListener listener : mFrontBackListeners) {
                listener.onBack();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        LogUtils.INSTANCE.v(LogUtils.TAG_LIFECYCLE_ACTIVITY, "onActivitySaveInstanceState - "+activity.getClass().getSimpleName()+"|"+outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.INSTANCE.v(LogUtils.TAG_LIFECYCLE_ACTIVITY, "onActivityDestroyed - "+activity.getClass().getSimpleName());
        mActivityCount--;
    }

    public boolean isDaemon() {
        return mVisibleActivityCount == 0;
    }

    public boolean isEmptyApp() {
        return mActivityCount == 0;
    }

    interface FrontBackListener {

        void onFront();

        void onBack();
    }
}
