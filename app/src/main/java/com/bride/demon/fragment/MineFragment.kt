package com.bride.demon.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bride.demon.IMessageService
import com.bride.demon.IMyService
import com.bride.demon.IServiceManager
import com.bride.demon.MessageReceiveListener
import com.bride.demon.MessageReceiveListener.Stub
import com.bride.demon.activity.CodecActivity
import com.bride.demon.activity.JobSchedulerActivity
import com.bride.demon.activity.LoginActivity
import com.bride.demon.databinding.FragmentMineBinding
import com.bride.demon.model.Form
import com.bride.demon.service.MyService
import com.bride.ui_lib.BaseFragment
import timber.log.Timber

class MineFragment : BaseFragment() {
    companion object {
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }

    private lateinit var mBinding: FragmentMineBinding

    private var mServiceManagerProxy: IServiceManager? = null
    private var mMyServiceProxy: IMyService? = null
    private var mMessageServiceProxy: IMessageService? = null
    private var mMessengerProxy: Messenger? = null

    // 执行Message.callback; or Handler.mCallback; or Handler.handleMessage
    private val myHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val bundle = msg.data
            bundle.classLoader = com.bride.demon.model.Message::class.java.classLoader
            val message = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                bundle.getParcelable("message", com.bride.demon.model.Message::class.java)
            else
                bundle.getParcelable("message")
            postDelayed({
                val ac = activity ?: return@postDelayed
                Toast.makeText(ac, message!!.content, Toast.LENGTH_SHORT).show()
            }, 3000L)
        }
    }

    private val clientMessenger = Messenger(myHandler)

    private val messageReceiveListener: MessageReceiveListener = object : Stub() {
        @Throws(RemoteException::class)
        override fun onReceiveMessage(msg: com.bride.demon.model.Message) {
            myHandler.post {
                val ac = activity ?: return@post
                Toast.makeText(ac, msg.content, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentMineBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.fragment = this
    }

    fun onClick(v: View) {
        val ac = activity ?: return
        when(v) {
            mBinding.btnLogin -> LoginActivity.openActivity(ac)
            mBinding.btnCodec -> CodecActivity.openActivity(ac)
            mBinding.btnJobScheduler -> JobSchedulerActivity.openActivity(ac)
        }
    }

    fun onServiceClick(v: View) {
        if (v === mBinding.tvSwitch) {
            val ac = activity ?: return
            try { // 启动服务并拿到服务代理。
                // 1）客户进程发起bindService请求，AMS发socket消息给Zygote、启动服务所在服务进程
                // 2）启动Application、启动服务，
                // 3）将binder对象发布到AMS，AMS将binder返给客户进程
                val intent = Intent(ac, MyService::class.java)
                ac.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (v === mBinding.tvUseService) {
            if (mMyServiceProxy == null) return
            try {
                // oneway非阻塞，只能返回void
                mMyServiceProxy!!.basicTypes(1, 10L, true, 2.3f, 9.99, "Jacob")
                mMyServiceProxy!!.sendForm(Form("洛杉矶", "1988"))
                Timber.tag("IMyService").i(mMyServiceProxy!!.user.toString())
                mMyServiceProxy!!.add(1, 2)
                mMyServiceProxy!!.value
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    fun onConnectClick(v: View) {
        if (mMyServiceProxy == null) return
        if (v === mBinding.tvConnectRemote) {
            try {
                mMyServiceProxy!!.connect()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        } else if (v === mBinding.tvDisconnectRemote) {
            try {
                mMyServiceProxy!!.disconnect()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        } else if (v === mBinding.tvIsConnected) {
            try {
                val ac = activity ?: return
                Toast.makeText(ac, mMyServiceProxy!!.isConnected.toString(), Toast.LENGTH_SHORT)
                    .show()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    fun onMessageClick(v: View) {
        if (mMessageServiceProxy == null) return
        if (v === mBinding.tvSendMsg) {
            try {
                val msg = com.bride.demon.model.Message()
                msg.content = "Message sent from main"
                mMessageServiceProxy!!.sendMessage(msg)
                Timber.i(msg.isSendSuccess.toString())
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        } else if (v === mBinding.tvRegister) {
            try {
                mMessageServiceProxy!!.registerMessageReceiveListener(messageReceiveListener)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        } else if (v === mBinding.tvUnregister) {
            try {
                mMessageServiceProxy!!.unregisterMessageReceiveListener(messageReceiveListener)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    fun onMessengerClick(v: View) {
        if (mMessengerProxy == null) return
        if (v === mBinding.tvMessenger) {
            try {
                val message = com.bride.demon.model.Message()
                message.content = "Message sent from main by Messenger"
                val data = Message.obtain()
                data.replyTo = clientMessenger
                val bundle = Bundle()
                bundle.putParcelable("message", message)
                data.data = bundle
                mMessengerProxy!!.send(data)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            try {
                mServiceManagerProxy = IServiceManager.Stub.asInterface(service)
                // BinderProxy -> Proxy
                mMyServiceProxy = IMyService.Stub.asInterface(
                    mServiceManagerProxy!!.getService(
                        IMyService::class.java.simpleName
                    )
                )
                mMessageServiceProxy = IMessageService.Stub.asInterface(
                    mServiceManagerProxy!!.getService(
                        IMessageService::class.java.simpleName
                    )
                )
                // Messenger#mTarget = IMessenger.Stub.asInterface(BinderProxy)
                mMessengerProxy = Messenger(mServiceManagerProxy!!.getService(Messenger::class.java.simpleName))
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            Toast.makeText(activity, "服务已连接", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            // 服务所在进程被杀死
            mServiceManagerProxy = null
            Toast.makeText(activity, "服务已断开", Toast.LENGTH_SHORT).show()
        }
    }
}