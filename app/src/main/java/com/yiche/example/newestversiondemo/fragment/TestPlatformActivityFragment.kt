package com.yiche.example.newestversiondemo.fragment

import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yiche.example.newestversiondemo.R

/**
 * A placeholder fragment containing a simple view.
 */
class TestPlatformActivityFragment : Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        // 为啥把activity换成context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Victor", "context: $context")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_platform, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.underline).visibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) View.GONE else View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Victor", "activity: $activity")
    }
}
