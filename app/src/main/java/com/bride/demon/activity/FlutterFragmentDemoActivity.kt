package com.bride.demon.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.bride.demon.R
import com.bride.demon.databinding.ActivityFlutterFragmentDemoBinding
import com.bride.ui_lib.BaseActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.android.TransparencyMode

class FlutterFragmentDemoActivity : BaseActivity() {
    companion object {
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    }

    private var flutterFragment: FlutterFragment? = null

    private lateinit var mBinding: ActivityFlutterFragmentDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFlutterFragmentDemoBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        addFlutterFragment()
    }

    private fun addFlutterFragment() {
        val fragmentManager: FragmentManager = supportFragmentManager
        flutterFragment = fragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?
        if (flutterFragment == null) {
//            val newFlutterFragment = FlutterFragment.createDefault()

            /*val newFlutterFragment = FlutterFragment.withNewEngine()
                .initialRoute("/")// 指定 Flutter 运行的初始路由
                .dartEntrypoint("main")// 指定 Flutter 运行的入口
                .build<FlutterFragment>()*/

            val newFlutterFragment = FlutterFragment.withCachedEngine("my_engine_id")
                .shouldAttachEngineToActivity(false)// false防止Flutter与所属的Activity交互，默认true
                .renderMode(RenderMode.texture)// 选择使用TextureView替换SurfaceView来渲染FlutterFragment
                .transparencyMode(TransparencyMode.transparent)// 启动一个透明的FlutterFragment
                .build<FlutterFragment>()
            flutterFragment = newFlutterFragment
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFlutterFragment, TAG_FLUTTER_FRAGMENT)
                .commitAllowingStateLoss()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        flutterFragment!!.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        flutterFragment!!.onNewIntent(intent)
    }

    override fun onBackPressed() {
        flutterFragment!!.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        flutterFragment!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onUserLeaveHint() {
        flutterFragment!!.onUserLeaveHint()
    }

    @SuppressLint("MissingSuperCall")
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        flutterFragment!!.onTrimMemory(level)
    }
}