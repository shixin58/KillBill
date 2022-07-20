package com.bride.demon.activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bride.demon.databinding.ActivityBlankBinding
import com.bride.demon.model.City
import com.bride.demon.model.Person
import com.bride.ui_lib.BaseActivity

/**
 * test lifecycle, ViewHierarchy, Serializable
 * Created by shixin on 2018/4/26.
 */
@Route(path = "/demon/blank")
class BlankActivity : BaseActivity() {

    private lateinit var mBinding: ActivityBlankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 返回的ViewBinding实现类是final
        mBinding = ActivityBlankBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        printViewHierarchy(mBinding.tvTitle, this)
        Log.i("lifecycleB", "onCreate")
    }

    private fun statistics() {
        val index = intent.getIntExtra("index", 0)
        if (index == 1) {
            val city = intent.getParcelableExtra<City>("city")
            Log.i(TAG, "耗时："+(SystemClock.elapsedRealtime()-city!!.createTime)+"ms")
            Toast.makeText(this.applicationContext, city.toString(), Toast.LENGTH_SHORT).show()
            val cities = intent.getParcelableArrayExtra("cityList")
            Log.i(TAG, "getParcelableArrayExtra - "+ cities!![0]+" | "+cities[1])
        } else if(index == 2) {
            val person = intent.getSerializableExtra("person") as Person
            Log.i(TAG, "耗时："+(SystemClock.elapsedRealtime()-person.createTime)+"ms")
            Toast.makeText(this.applicationContext, person.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("lifecycleB", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("lifecycleB", "onResume")
        statistics()
    }

    override fun onPause() {
        super.onPause()
        Log.i("lifecycleB", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("lifecycleB", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("lifecycleB", "onDestroy")
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
            Log.i(baseActivity.javaClass.simpleName, BlankActivity.javaClass.name)
            Log.i(baseActivity.javaClass.simpleName, "printViewHierarchy - ${view.javaClass.simpleName}")
            var viewParent = view.parent
            while (viewParent != null) {
                Log.i(baseActivity.javaClass.simpleName, "printViewHierarchy - ${viewParent.javaClass.simpleName}")
                viewParent = viewParent.parent
            }
        }
    }
}
