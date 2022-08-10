package com.bride.demon.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bride.demon.IMessageService;
import com.bride.demon.IServiceManager;
import com.bride.demon.MessageReceiveListener;
import com.bride.demon.model.Form;
import com.bride.demon.IMyService;
import com.bride.demon.activity.CodecActivity;
import com.bride.demon.activity.JobSchedulerActivity;
import com.bride.demon.activity.LoginActivity;
import com.bride.demon.databinding.FragmentMineBinding;
import com.bride.demon.model.Message;
import com.bride.demon.service.MyService;
import com.bride.ui_lib.BaseFragment;

/**
 * <p>Created by shixin on 2019-08-19.
 */
public class MineFragment extends BaseFragment {
    private IServiceManager mServiceManagerProxy;
    private IMyService mMyServiceProxy;
    private IMessageService mMessageServiceProxy;
    private Messenger mMessengerProxy;

    // 执行Message.callback; or Handler.mCallback; or Handler.handleMessage
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            bundle.setClassLoader(Message.class.getClassLoader());
            final Message message = bundle.getParcelable("message");
            postDelayed(() -> {
                final Activity ac = getActivity();
                if (ac == null) return;
                Toast.makeText(ac, message.getContent(), Toast.LENGTH_SHORT).show();
            }, 3000L);
        }
    };
    private final Messenger clientMessenger = new Messenger(handler);

    private final MessageReceiveListener messageReceiveListener = new MessageReceiveListener.Stub() {
        @Override
        public void onReceiveMessage(Message msg) throws RemoteException {
            getHandler().post(() -> {
                final Activity ac = getActivity();
                if (ac == null) return;
                Toast.makeText(ac, msg.getContent(), Toast.LENGTH_SHORT).show();
            });
        }
    };

    private FragmentMineBinding mBinding;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMineBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setFragment(this);
    }

    public void onClick(View v) {
        final Activity ac = getActivity();
        if (ac == null) return;
        if (v==mBinding.btnLogin) {
            LoginActivity.openActivity(ac);
        } else if (v==mBinding.btnCodec) {
            CodecActivity.openActivity(ac);
        } else if (v==mBinding.btnJobScheduler) {
            JobSchedulerActivity.Companion.openActivity(ac);
        }
    }

    public void onServiceClick(View v) {
        if (v==mBinding.tvSwitch) {
            final Activity ac = getActivity();
            if (ac == null) return;
            try {// 启动服务并拿到服务代理。
                // 1）客户进程发起bindService请求，AMS发socket消息给Zygote、启动服务所在服务进程
                // 2）启动Application、启动服务，
                // 3）将binder对象发布到AMS，AMS将binder返给客户进程
                Intent intent = new Intent(ac, MyService.class);
                ac.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v==mBinding.tvUseService) {
            if(mMyServiceProxy == null) return;
            try {
                // oneway非阻塞，只能返回void
                mMyServiceProxy.basicTypes(1, 10L, true, 2.3f, 9.99, "Jacob");
                mMyServiceProxy.sendForm(new Form("洛杉矶", "1988"));

                Log.i("IMyService", mMyServiceProxy.getUser().toString());
                mMyServiceProxy.add(1, 2);
                mMyServiceProxy.getValue();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onConnectClick(View v) {
        if(mMyServiceProxy == null) return;
        if (v==mBinding.tvConnectRemote) {
            try {
                mMyServiceProxy.connect();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (v==mBinding.tvDisconnectRemote) {
            try {
                mMyServiceProxy.disconnect();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (v==mBinding.tvIsConnected) {
            try {
                final Activity ac = getActivity();
                if (ac == null) return;
                Toast.makeText(ac, String.valueOf(mMyServiceProxy.isConnected()), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onMessageClick(View v) {
        if(mMessageServiceProxy == null) return;
        if (v==mBinding.tvSendMsg) {
            try {
                Message msg = new Message();
                msg.setContent("Message sent from main");
                mMessageServiceProxy.sendMessage(msg);
                Log.i(TAG, String.valueOf(msg.isSendSuccess()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (v==mBinding.tvRegister) {
            try {
                mMessageServiceProxy.registerMessageReceiveListener(messageReceiveListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (v==mBinding.tvUnregister) {
            try {
                mMessageServiceProxy.unregisterMessageReceiveListener(messageReceiveListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onMessengerClick(View v) {
        if (mMessengerProxy == null) return;
        if (v==mBinding.tvMessenger) {
            try {
                final Message message = new Message();
                message.setContent("Message sent from main by Messenger");

                android.os.Message data = android.os.Message.obtain();
                data.replyTo = clientMessenger;
                Bundle bundle = new Bundle();
                bundle.putParcelable("message", message);
                data.setData(bundle);
                mMessengerProxy.send(data);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                mServiceManagerProxy = IServiceManager.Stub.asInterface(service);
                // BinderProxy -> Proxy
                mMyServiceProxy = IMyService.Stub.asInterface(mServiceManagerProxy.getService(IMyService.class.getSimpleName()));
                mMessageServiceProxy = IMessageService.Stub.asInterface(mServiceManagerProxy.getService(IMessageService.class.getSimpleName()));
                // Messenger#mTarget = IMessenger.Stub.asInterface(BinderProxy)
                mMessengerProxy = new Messenger(mServiceManagerProxy.getService(Messenger.class.getSimpleName()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Toast.makeText(getActivity(), "服务已连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 服务所在进程被杀死
            mServiceManagerProxy = null;
            Toast.makeText(getActivity(), "服务已断开", Toast.LENGTH_SHORT).show();
        }
    };
}
