package com.bride.demon.activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.bride.demon.databinding.ActivityBlankBinding
import com.bride.demon.model.City
import com.bride.demon.model.Person
import com.bride.ui_lib.BaseActivity
import timber.log.Timber

/**
 * test lifecycle, ViewHierarchy, Serializable
 * Created by shixin on 2018/4/26.
 */
class BlankActivity : BaseActivity() {

    private lateinit var mBinding: ActivityBlankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 返回的ViewBinding实现类是final
        mBinding = ActivityBlankBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        printViewHierarchy(mBinding.tvTitle, this)
        Timber.tag("lifecycleB").i("onCreate")
    }

    private fun statistics() {
        val index = intent.getIntExtra("index", 0)
        if (index == 1) {
            val city = intent.getParcelableExtra<City>("city")
            Timber.tag(TAG).i("耗时：%sms", SystemClock.elapsedRealtime() - city!!.createTime)
            Toast.makeText(this.applicationContext, city.toString(), Toast.LENGTH_SHORT).show()
            val cities = intent.getParcelableArrayExtra("cityList")
            Timber.tag(TAG).i("getParcelableArrayExtra - %s | %s", cities!![0], cities[1])
        } else if(index == 2) {
            val person = intent.getSerializableExtra("person") as Person
            Timber.tag(TAG).i("耗时：%sms", SystemClock.elapsedRealtime() - person.createTime)
            Toast.makeText(this.applicationContext, person.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("lifecycleB").i("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("lifecycleB").i("onResume")
        statistics()
    }

    override fun onPause() {
        super.onPause()
        Timber.tag("lifecycleB").i("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag("lifecycleB").i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("lifecycleB").i("onDestroy")
    }

    companion object {

        private val TAG: String = BlankActivity::class.java.simpleName

        fun openActivity(context: FragmentActivity?) {
            val intent = Intent(context, BlankActivity::class.java)
            context?.startActivity(intent)
        }

        fun openActivity(context: FragmentActivity?, bundle: Bundle) {
            val intent = Intent(context, BlankActivity::class.java)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }

        fun printViewHierarchy(view: View, baseActivity: BaseActivity) {
            // BlankActivity$Companion
            Timber.tag(baseActivity.javaClass.simpleName).i(BlankActivity.javaClass.name)
            Timber.tag(baseActivity.javaClass.simpleName).i("printViewHierarchy - ${view.javaClass.simpleName}")
            var viewParent = view.parent
            while (viewParent != null) {
                Timber.tag(baseActivity.javaClass.simpleName).i("printViewHierarchy - ${viewParent.javaClass.simpleName}")
                viewParent = viewParent.parent
            }
        }
    }
}
