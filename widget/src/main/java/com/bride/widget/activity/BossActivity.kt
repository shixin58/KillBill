package com.bride.widget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bride.widget.ui.main.BossFragment

class BossActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, BossFragment.newInstance())
                    .commitNow()
        }
    }
}
