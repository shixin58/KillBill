package com.victor.demon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.max.baselib.BaseFragment
import com.victor.demon.R

/**
 * A placeholder fragment containing a simple view.
 */
class TestPlatformActivityFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_platform, container, false)
    }
}
