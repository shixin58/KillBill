package com.bride.demon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bride.demon.R
import com.bride.demon.databinding.FragmentBombBinding
import com.bride.ui_lib.BaseFragment

/**
 * <p>Created by shixin on 2019-11-05.
 */
class BombFragment : BaseFragment() {

    private lateinit var mBinding: FragmentBombBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentBombBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.tv_start_bomb)?.setOnClickListener {
            mBinding.viewBomb.startBomb()
        }
    }

    companion object {
        fun newInstance() : BombFragment {
            return BombFragment()
        }
    }
}