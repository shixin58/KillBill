package com.victor.demon.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.demon.R
import com.victor.demon.activity.RecyclerViewActivity
import com.victor.demon.activity.TestPlatformActivity
import com.victor.demon.utils.DataEncryptionAlgorithm
import com.victor.demon.utils.HashMapTest

class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when {
            v?.id== R.id.button_recycler_view -> RecyclerViewActivity.openActivity(activity)
            v?.id==R.id.button_fragment -> TestPlatformActivity.openActivityForResult(this, 1)
            v?.id==R.id.button_hash_map -> {
                HashMapTest.testCommonApi()
                HashMapTest.testHashtable()
            }
            v?.id==R.id.button_des -> DataEncryptionAlgorithm.test()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<View>(R.id.button_recycler_view)?.setOnClickListener(this)
        activity?.findViewById<View>(R.id.button_fragment)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_hash_map)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_des)?.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 接收返回
        Log.i("Victor", "requestCode-$requestCode")
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
