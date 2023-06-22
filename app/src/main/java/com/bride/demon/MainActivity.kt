package com.bride.demon

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bride.ui_lib.BaseActivity
import com.bride.baselib.PermissionUtils
import com.bride.demon.callback.MyFragmentLifecycleCallbacks
import com.bride.demon.fragment.DashboardFragment
import com.bride.demon.fragment.HomeFragment
import com.bride.demon.fragment.MineFragment
import com.bride.demon.fragment.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import timber.log.Timber

class MainActivity : BaseActivity() {
    private val fragments = arrayOfNulls<Fragment>(4)
    private var mIndex: Int = 0
    private val fragmentLifecycleCallbacks = MyFragmentLifecycleCallbacks()

    private val mOnNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setTabFragment(0)
                return@OnItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                setTabFragment(1)
                return@OnItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                setTabFragment(2)
                return@OnItemSelectedListener true
            }
            R.id.navigation_mine -> {
                setTabFragment(3)
                return@OnItemSelectedListener true
            }
            else -> return@OnItemSelectedListener false
        }
    }

    private fun setTabFragment(idx: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        if (fragments[idx] == null) {
            when(idx) {
                0 -> {
                    fragments[idx] = HomeFragment.newInstance()
                    fragments[idx]?.let { transaction.add(R.id.container_fragment, it, HomeFragment::class.simpleName) }
                }
                1 -> {
                    fragments[idx] = DashboardFragment.newInstance()
                    fragments[idx]?.let { transaction.add(R.id.container_fragment, it, DashboardFragment::class.simpleName) }
                }
                2 -> {
                    fragments[idx] = NotificationsFragment.newInstance()
                    fragments[idx]?.let { transaction.add(R.id.container_fragment, it, NotificationsFragment::class.simpleName) }
                }
                3 -> {
                    fragments[idx] = MineFragment.newInstance()
                    fragments[idx]?.let { transaction.add(R.id.container_fragment, it, MineFragment::class.simpleName) }
                }
            }
        }
        fragments[idx]?.let { transaction.show(it) }
        if (mIndex != idx) {
            fragments[mIndex]?.let { transaction.hide(it) }
            mIndex = idx
        }
        transaction.commitAllowingStateLoss()
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
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            when (mIndex) {
                1 -> {
                    navigation.selectedItemId = R.id.navigation_dashboard
                }
                2 -> {
                    navigation.selectedItemId = R.id.navigation_notifications
                }
                3 -> {
                    navigation.selectedItemId = R.id.navigation_mine
                }
                else -> {
                    navigation.selectedItemId = R.id.navigation_home
                }
            }
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)

        PermissionUtils.requestAllPermissions(this, 1)
    }

    override fun recreate() {
        super.recreate()
        Timber.i("recreate")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.i("onRestoreInstanceState")
        mIndex = savedInstanceState.getInt("Index")
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
}
