package com.victor.demon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.widget.Toast

import com.victor.demon.R

class TestPlatformActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_platform)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action") {
                        val toast = Toast.makeText(this@TestPlatformActivity, "OK", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }.show()
        }
    }

    companion object {

        fun openActivity(context: Context) {
            val intent = Intent(context, TestPlatformActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }

        fun openActivityForResult(activity: FragmentActivity, requestCode: Int) {
            val intent = Intent(activity, TestPlatformActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }

        fun openActivityForResult(fragment: Fragment, requestCode: Int) {
            val intent = Intent(fragment.activity, TestPlatformActivity::class.java)
            fragment.startActivityForResult(intent, requestCode)
        }
    }
}
