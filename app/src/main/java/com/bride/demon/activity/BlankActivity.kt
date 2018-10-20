package com.bride.demon.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.util.Log
import android.view.View
import com.bride.baselib.BaseActivity
import com.bride.demon.R

/**
 *
 * Created by shixin on 2018/4/26.
 */
class BlankActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)
        printViewHierarchy(findViewById(R.id.tv_title))
        Log.i("lifecycleB", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("lifecycleB", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("lifecycleB", "onResume")
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

        fun openActivity(context: androidx.fragment.app.FragmentActivity?) {
            val intent = Intent(context, BlankActivity::class.java)
            context?.startActivity(intent)
        }

        fun printViewHierarchy(view: View?) {
            Log.i("printViewHierarchy", view?.toString())
            var viewParent = view?.parent
            while (viewParent != null) {
                Log.i("printViewHierarchy", viewParent.toString())
                viewParent = viewParent.parent
            }
        }
    }
}
