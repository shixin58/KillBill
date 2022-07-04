package com.bride.demon.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.bride.demon.IMessageService;
import com.bride.demon.IServiceManager;
import com.bride.demon.MessageReceiveListener;
import com.bride.demon.model.Form;
import com.bride.demon.IMyService;
import com.bride.demon.model.Message;
import com.bride.demon.model.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在主进程或外部进程bindService，在子进程执行Service
 * <p>Created by shixin on 2018/9/8.
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();

    private boolean isConnected = false;

    // 方法跑在binder线程池中，用handler切换主线程
    // 接收客户端进程通过Messenger代理传递过来的消息
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            super.handleMessage(msg);
            Messenger clientMessenger = msg.replyTo;
            Bundle bundle = msg.getData();
            bundle.setClassLoader(Message.class.getClassLoader());
            Message message = bundle.getParcelable("message");
            Toast.makeText(MyService.this, message.getContent(), Toast.LENGTH_SHORT).show();

            try {
                android.os.Message reply = android.os.Message.obtain();
                Message data = new Message();
                data.setContent("Message sent from remote by Messenger");
                Bundle replyBundle = new Bundle();
                replyBundle.putParcelable("message", data);
                reply.setData(replyBundle);
                clientMessenger.send(reply);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    // 跨进程传递经过了序列化和反序列化，已经不是同个对象，ArrayList#remove(Object)无效
    private final RemoteCallbackList<MessageReceiveListener> mListenerRemoteCallbackList = new RemoteCallbackList<>();

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private ScheduledFuture scheduledFuture;

    private final Messenger messenger = new Messenger(handler);

    private final IMyService myService = new IMyService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("MyServiceImpl", Thread.currentThread().getName()
                            +" basicTypes() "+anInt+"_"+aLong+"_"+aBoolean+"_"+aFloat+"_"+aDouble+"_"+aString);
                }
            });
        }

        @Override
        public String getValue() throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" getValue()");
            return "终于等到你";
        }

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" add()");
            return a+b;
        }

        @Override
        public User getUser() throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" getUser() ");
            return new User("Max", "137", Process.myPid()+"");
        }

        @Override
        public void sendForm(Form f) throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" sendForm() "+f.toString());
        }

        @Override
        public void connect() throws RemoteException {
            try {
                Thread.sleep(5000L);
                isConnected = true;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyService.this, "connect", Toast.LENGTH_SHORT).show();
                    }
                });
                scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        int size = mListenerRemoteCallbackList.beginBroadcast();
                        for (int i=0; i<size; i++) {
                            final Message msg = new Message();
                            msg.setContent("Message sent from remote");
                            try {
                                mListenerRemoteCallbackList.getBroadcastItem(i).onReceiveMessage(msg);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        mListenerRemoteCallbackList.finishBroadcast();
                    }
                }, 5000L, 5000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void disconnect() throws RemoteException {
            isConnected = false;
            scheduledFuture.cancel(true);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyService.this, "disconnect", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public boolean isConnected() throws RemoteException {
            return isConnected;
        }
    };

    private final IMessageService messageService = new IMessageService.Stub() {
        @Override
        public void sendMessage(Message msg) throws RemoteException {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyService.this, msg.getContent(), Toast.LENGTH_SHORT).show();
                }
            });
            msg.setSendSuccess(isConnected);
        }

        @Override
        public void registerMessageReceiveListener(MessageReceiveListener listener) throws RemoteException {
            if (listener != null) {
                mListenerRemoteCallbackList.register(listener);
            }
        }

        @Override
        public void unregisterMessageReceiveListener(MessageReceiveListener listener) throws RemoteException {
            if (listener != null) {
                mListenerRemoteCallbackList.unregister(listener);
            }
        }
    };

    private final IServiceManager serviceManager = new IServiceManager.Stub() {
        @Override
        public IBinder getService(String srvName) throws RemoteException {
            if (IMyService.class.getSimpleName().equals(srvName))
                return myService.asBinder();
            if (IMessageService.class.getSimpleName().equals(srvName))
                return messageService.asBinder();
            if (Messenger.class.getSimpleName().equals(srvName))
                return messenger.getBinder();
            return null;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind()");
        return serviceManager.asBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
    }
}
