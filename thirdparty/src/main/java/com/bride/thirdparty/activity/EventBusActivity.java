package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * <p>Created by shixin on 2018/9/21.
 */
public class EventBusActivity extends BaseActivity {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, EventBusActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        beautySoftReference = new SoftReference<>(new Beauty());
        beautyWeakReference = new WeakReference<>(new Beauty());

        // 将弱引用注册到引用队列
        beauty = new Beauty();
        weakReference = new CustomWeakReference<>(beauty, referenceQueue, 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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

    static SoftReference<Beauty> beautySoftReference;

    static WeakReference<Beauty> beautyWeakReference;

    final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

    Beauty beauty;
    CustomWeakReference<Beauty> weakReference;

    static class Beauty {

    }

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
}
