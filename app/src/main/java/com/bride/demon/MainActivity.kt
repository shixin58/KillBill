package com.bride.demon

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bride.ui_lib.BaseActivity
import com.bride.baselib.PermissionUtils
import com.bride.demon.callback.MyFragmentLifecycleCallbacks
import com.bride.demon.fragment.DashboardFragment
import com.bride.demon.fragment.HomeFragment
import com.bride.demon.fragment.MineFragment
import com.bride.demon.fragment.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    private val fragments = arrayOfNulls<Fragment>(4)
    private var mIndex: Int = 0
    private val fragmentLifecycleCallbacks = MyFragmentLifecycleCallbacks()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var transaction: FragmentTransaction?
        when (item.itemId) {
            R.id.navigation_home -> {
                transaction = supportFragmentManager.beginTransaction()
                if (fragments[0] == null) {
                    fragments[0] = HomeFragment.newInstance()
                    fragments[0]?.let { transaction!!.add(R.id.container_fragment, it, HomeFragment::class.simpleName) }
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
                    fragments[1]?.let { transaction!!.add(R.id.container_fragment, it, DashboardFragment::class.simpleName) }
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
                    fragments[2]?.let { transaction!!.add(R.id.container_fragment, it, NotificationsFragment::class.simpleName) }
                }
                fragments[2]?.let { transaction!!.show(it) }
                if (mIndex != 2) {
                    fragments[mIndex]?.let { transaction!!.hide(it) }
                    mIndex = 2
                }
                transaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mine -> {
                transaction = supportFragmentManager.beginTransaction()
                if (fragments[3] == null) {
                    fragments[3] = MineFragment.newInstance()
                    fragments[3]?.let { transaction!!.add(R.id.container_fragment, it, MineFragment::class.simpleName) }
                }
                fragments[3]?.let { transaction!!.show(it) }
                if (mIndex != 3) {
                    fragments[mIndex]?.let { transaction!!.hide(it) }
                    mIndex = 3
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

        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("Index")
            fragments[0] = supportFragmentManager.findFragmentByTag(HomeFragment::class.simpleName)
            fragments[1] = supportFragmentManager.findFragmentByTag(DashboardFragment::class.simpleName)
            // kotlin.jvm.KotlinReflectionNotSupportedError: Kotlin reflection implementation is not found at runtime. Make sure you have kotlin-reflect.jar in the classpath
            fragments[2] = supportFragmentManager.findFragmentByTag(NotificationsFragment::class.simpleName)
            fragments[3] = supportFragmentManager.findFragmentByTag(MineFragment::class.simpleName)
        }

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            if (mIndex == 1) {
                navigation.selectedItemId = R.id.navigation_dashboard
            }else if (mIndex == 2) {
                navigation.selectedItemId = R.id.navigation_notifications
            } else if (mIndex == 3){
                navigation.selectedItemId = R.id.navigation_mine
            } else {
                navigation.selectedItemId = R.id.navigation_home
            }
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)

        PermissionUtils.requestAllPermissions(this, 1)
    }

    override fun recreate() {
        super.recreate()
        Log.i(TAG, "recreate")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        mIndex = savedInstanceState.getInt("Index")
    }

    override fun onResume() {
        super.onResume()
    }

    fun onClick(v: View) {
        if (fragments[mIndex] is DashboardFragment
                && fragments[mIndex]!!.requireView().findViewById<View>(v.id) != null) {
            (fragments[mIndex] as DashboardFragment).onClick(v)
        } else if (fragments[mIndex] is NotificationsFragment
                && fragments[mIndex]!!.requireView().findViewById<View>(v.id) != null) {
            (fragments[mIndex] as NotificationsFragment).onClick(v)
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("Index", mIndex)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                for(result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        finish()
                        break
                    }
                }
            }
        }
    }

    companion object {
        private val TAG: String = MainActivity::class.java.simpleName
    }
}
