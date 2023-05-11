package com.bride.demon.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.*
import com.bride.demon.R
import com.bride.demon.adapter.LieAdapter
import com.bride.demon.adapter.StandAdapter
import com.bride.demon.databinding.ActivityRecyclerViewBinding
import com.bride.demon.repository.RecyclerViewRepository

/**
 * Adapter, LayoutManager, ViewHolder, ItemDecoration, ItemAnimator, LayoutParams
 * Created by shixin on 2018/3/6.
 */
class RecyclerViewActivity : AppCompatActivity() {
    companion object {
        fun openActivity(context: FragmentActivity) {
            val intent = Intent(context, RecyclerViewActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var mBinding: ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        val lieAdapter = LieAdapter()
        mBinding.recyclerViewHorizontal.adapter = lieAdapter
        mBinding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        val itemDecorationH = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        itemDecorationH.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_vertical)!!)
        mBinding.recyclerViewHorizontal.addItemDecoration(itemDecorationH)
        lieAdapter.setList(RecyclerViewRepository.getCountryList())

        val gridAdapter = StandAdapter()
        mBinding.recyclerView.adapter = gridAdapter
        mBinding.recyclerView.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        val itemDecorationV = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorationV.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_horizontal)!!)
        mBinding.recyclerView.addItemDecoration(itemDecorationV)
        gridAdapter.setFruitList(RecyclerViewRepository.getColorList())

        val staggeredAdapter = StandAdapter()
        mBinding.recyclerView2.adapter = gridAdapter
        mBinding.recyclerView2.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredAdapter.setFruitList(RecyclerViewRepository.getColorList())
    }
}
