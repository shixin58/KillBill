package com.bride.thirdparty

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.bride.baselib.PermissionUtils
import com.bride.baselib.toast
import com.bride.thirdparty.activity.*
import com.bride.thirdparty.bean.MessageEvent
import com.bride.thirdparty.bean.User
import com.bride.thirdparty.databinding.ActivityMainBinding
import com.bride.thirdparty.util.ObjectBox
import com.bride.thirdparty.util.RxBus
import com.bride.ui_lib.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.Subject

/**
 * thirdparty入口
 * Created by shixin on 2018/9/7.
 */
class MainActivity : BaseActivity(), OnClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private var mSubject: Subject<MessageEvent>? = null
    private var mDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
        initData()
    }

    private fun initView() {
        mBinding.tvEventbus.setOnClickListener(this)
        mBinding.tvLandscape.setOnClickListener(this)
        mBinding.tvFullscreen.setOnClickListener(this)
        mBinding.tvPush.setOnClickListener(this)
        mBinding.tvRetrofit.setOnClickListener(this)
        mBinding.tvRxjava.setOnClickListener(this)
        mBinding.tvDirectory.setOnClickListener(this)
        mBinding.tvRxBus.setOnClickListener(this)
        mBinding.tvUrlConnection.setOnClickListener(this)
        mBinding.tvObjectBox.setOnClickListener(this)
    }

    private fun initData() {
        // 1、测试RxBus
        mSubject = RxBus.getInstance().register(MessageEvent::class.java)
        // Consumer和Observer均可使用
        mDisposable = mSubject?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { messageEvent: MessageEvent -> toast(messageEvent.info) }

        // 2、请求系统权限
        PermissionUtils.requestAllPermissions(this, 3)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable?.dispose()
        mSubject?.let { RxBus.getInstance().unregister(MessageEvent::class.java, it) }
    }

    override fun onClick(v: View) {
        if (v === mBinding.tvEventbus) {
            EventBusActivity.openActivity(application)
        } else if (v === mBinding.tvLandscape) {
            LandscapeActivity.openActivity(this)
        } else if (v === mBinding.tvFullscreen) {
            FullscreenActivity.openActivity(this)
        } else if (v === mBinding.tvPush) {
            PushActivity.openActivity(this)
        } else if (v === mBinding.tvRetrofit) {
            RetrofitActivity.openActivity(this)
        } else if (v === mBinding.tvRxjava) {
            RxJavaActivity.openActivity(this)
        } else if (v === mBinding.tvDirectory) {
            startActivity(Intent(this, DirectoryActivity::class.java))
        } else if (v === mBinding.tvRxBus) {
            RxBusActivity.openActivity(this)
        } else if (v === mBinding.tvUrlConnection) {
            UrlConnectionActivity.openActivity(this)
        } else if (v === mBinding.tvObjectBox) {
            val intent = Intent(this, ObjectBoxActivity::class.java)
            val users: List<User> = ObjectBox.store.boxFor(User::class.java).all
            if (users.isNotEmpty()) {
                intent.putExtra("user", users[0])
            }
            startActivity(intent)
        }
    }
}