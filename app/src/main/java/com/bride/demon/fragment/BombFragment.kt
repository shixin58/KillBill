package com.bride.demon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bride.demon.R
import com.bride.ui_lib.BaseFragment
import kotlinx.android.synthetic.main.fragment_bomb.*

/**
 * <p>Created by shixin on 2019-11-05.
 */
class BombFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bomb, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<View>(R.id.tv_start_bomb)?.setOnClickListener {
            view_bomb.startBomb()
        }
    }

    companion object {
        fun newInstance() : BombFragment {
            return BombFragment()
        }
    }
}