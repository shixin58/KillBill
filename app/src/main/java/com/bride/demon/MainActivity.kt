package com.bride.demon

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bride.baselib.BaseActivity
import com.bride.demon.callback.MyFragmentLifecycleCallbacks
import com.bride.demon.callback.MyLifecycleObserver
import com.bride.demon.fragment.DashboardFragment
import com.bride.demon.fragment.HomeFragment
import com.bride.demon.fragment.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    private val fragments = arrayOfNulls<Fragment>(3)
    private var mIndex: Int = 0
    private val fragmentLifecycleCallbacks = MyFragmentLifecycleCallbacks()
    private val lifecycleObserver = MyLifecycleObserver()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var transaction: FragmentTransaction?
        when (item.itemId) {
            R.id.navigation_home -> {
                transaction = supportFragmentManager.beginTransaction()
                if (fragments[0] == null) {
                    fragments[0] = HomeFragment.newInstance()
                    fragments[0]?.let { transaction!!.add(R.id.container_fragment, it) }
                }
                fragments[0]?.let { transaction!!.show(it) }
                if (mIndex != 0) {
                    fragments[mIndex]?.let { transaction!!.hide(it) }
                    mIndex = 0
                }
                transaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                transaction = supportFragmentManager.beginTransaction()
                if (fragments[1] == null) {
                    fragments[1] = DashboardFragment.newInstance()
                    fragments[1]?.let { transaction!!.add(R.id.container_fragment, it) }
                }
                fragments[1]?.let { transaction!!.show(it) }
                if (mIndex != 1) {
                    fragments[mIndex]?.let{ transaction!!.hide(it) }
                    mIndex = 1
                }
                transaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                transaction = supportFragmentManager.beginTransaction()
                if (fragments[2] == null) {
                    fragments[2] = NotificationsFragment.newInstance()
                    fragments[2]?.let { transaction!!.add(R.id.container_fragment, it) }
                }
                fragments[2]?.let { transaction!!.show(it) }
                if (mIndex != 2) {
                    fragments[mIndex]?.let { transaction!!.hide(it) }
                    mIndex = 2
                }
                transaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
        lifecycle.addObserver(lifecycleObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
        lifecycle.removeObserver(lifecycleObserver)
    }
}
