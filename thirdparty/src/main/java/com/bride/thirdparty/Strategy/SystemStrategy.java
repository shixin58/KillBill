package com.bride.thirdparty.Strategy;

import android.os.SystemClock;

import com.bride.thirdparty.protocal.IStrategy;

/**
 * <p>Created by shixin on 2018/10/7.
 */
public class SystemStrategy implements IStrategy {
    @Override
    public void execute() {
        // UTC时间，依赖系统时钟
        System.currentTimeMillis();
        System.out.println("System.currentTimeMillis() "+System.currentTimeMillis());
        // 0代表设备启动时间，不包含deep sleep
        System.nanoTime();
        System.out.println("System.nanoTime() "+System.nanoTime());

        // 当前线程启动时间
        SystemClock.currentThreadTimeMillis();
        System.out.println("SystemClock.currentThreadTimeMillis() "+SystemClock.currentThreadTimeMillis());
        // 自设备启动时间
        SystemClock.elapsedRealtime();
        System.out.println("SystemClock.elapsedRealtime() "+SystemClock.elapsedRealtime());
        SystemClock.elapsedRealtimeNanos();
        System.out.println("SystemClock.elapsedRealtimeNanos() "+SystemClock.elapsedRealtimeNanos());
        // 设备启动时间，不包含deep sleep
        SystemClock.uptimeMillis();
        System.out.println("SystemClock.uptimeMillis() "+SystemClock.uptimeMillis());
    }
}
