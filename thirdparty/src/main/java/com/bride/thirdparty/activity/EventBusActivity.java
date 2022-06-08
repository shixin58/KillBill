package com.bride.thirdparty.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bride.ui_lib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * EventBus为双检查单例模式。Double Checked Locking Singleton Pattern, lazy initialization。
 * stickyEvents采用ConcurrentHashMap, subscriptionsByEventType采用Map<Class, CopyOnWriteArrayList<Subscription>>。
 * <p>Created by shixin on 2018/9/21.
 */
public class EventBusActivity extends BaseActivity {
    private static final String TAG = EventBusActivity.class.getSimpleName();
    static SoftReference<Beauty> beautySoftReference;

    static WeakReference<Beauty> beautyWeakReference;

    final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

    Beauty beauty;
    CustomWeakReference<Beauty> weakReference;

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, EventBusActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void openActivity(Application application) {
        Intent intent = new Intent(application, EventBusActivity.class);
        // android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        application.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        Log.i(TAG, "onCreate "+getTaskId()+" "+hashCode());

        beautySoftReference = new SoftReference<>(new Beauty());
        beautyWeakReference = new WeakReference<>(new Beauty());

        // 将弱引用注册到引用队列
        beauty = new Beauty();
        weakReference = new CustomWeakReference<>(beauty, referenceQueue, 1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent "+getTaskId()+" "+hashCode());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy "+getTaskId()+" "+hashCode());
    }

    public void onMessageClick(View v) {
        // 在View构造器中拿到AttributeSet，解析布局文件中为View设置的属性，根据属性值对View做相应设置
        // View实例化时解析android:onClick属性，自动为View设置点击监听器，利用属性值通过反射找到Method并绑定，点击时执行
        EventBus.getDefault().post(new MessageEvent("I hate liars!"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, event.info, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().removeStickyEvent(event);
    }

    static class Beauty {}

    class CustomWeakReference<T> extends WeakReference<T> {
        final int key;
        CustomWeakReference(T t, ReferenceQueue<? super T> q, int key) {
            super(t, q);
            this.key = key;
        }
    }

    public void onReferenceClick(View v) {
        Toast.makeText(this, "beautySoftReference.get() "+beautySoftReference.get()
                +"\nbeautyWeakReference.get() "+beautyWeakReference.get(), Toast.LENGTH_SHORT).show();
        this.beauty = null;
    }

    public void onReferenceQueueClick(View v) {
        // 如果弱引用引用的对象弱可达，弱引用会加入到引用队列。
        Toast.makeText(this, ""+referenceQueue.poll(), Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_landscape:
                LandscapeActivity.openActivity(this);
                break;
        }
    }
}
