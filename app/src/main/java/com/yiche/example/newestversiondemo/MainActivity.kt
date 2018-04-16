package com.yiche.example.newestversiondemo

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.yiche.example.newestversiondemo.fragment.DashboardFragment
import com.yiche.example.newestversiondemo.fragment.HomeFragment
import com.yiche.example.newestversiondemo.fragment.NotificationsFragment

class MainActivity : AppCompatActivity() {

    private val fragments = arrayOfNulls<Fragment>(3)
    private var mIndex: Int = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var transaction: FragmentTransaction?
        when (item.itemId) {
            R.id.navigation_home -> {
                transaction = supportFragmentManager.beginTransaction()
                if (fragments[0] == null) {
                    fragments[0] = HomeFragment.newInstance()
                    transaction!!.add(R.id.container_fragment, fragments[0])
                }
                transaction!!.show(fragments[0])
                if (mIndex != 0) {
                    transaction.hide(fragments[mIndex])
                    mIndex = 0
                }
                transaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                transaction = supportFragmentManager.beginTransaction()
                if (fragments[1] == null) {
                    fragments[1] = DashboardFragment.newInstance()
                    transaction!!.add(R.id.container_fragment, fragments[1])
                }
                transaction!!.show(fragments[1])
                if (mIndex != 1) {
                    transaction.hide(fragments[mIndex])
                    mIndex = 1
                }
                transaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                transaction = supportFragmentManager.beginTransaction()
                if (fragments[2] == null) {
                    fragments[2] = NotificationsFragment.newInstance()
                    transaction!!.add(R.id.container_fragment, fragments[2])
                }
                transaction!!.show(fragments[2])
                if (mIndex != 2) {
                    transaction.hide(fragments[mIndex])
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
    }
}
