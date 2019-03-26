package com.bride.demon.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.bride.demon.R
import com.bride.demon.adapter.LieAdapter
import com.bride.demon.adapter.StandAdapter
import com.bride.demon.repository.RecyclerViewRepository

/**
 * Adapter, LayoutManager, ViewHolder, ItemDecoration, ItemAnimator, LayoutParams
 * Created by shixin on 2018/3/6.
 */
class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var recyclerViewHorizontal: RecyclerView
    private lateinit var recyclerViewGrid: RecyclerView
    private lateinit var recyclerViewStaggered: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initView()
    }

    private fun initView() {
        recyclerViewHorizontal = findViewById(R.id.recycler_view_horizontal)
        recyclerViewGrid = findViewById(R.id.recycler_view)
        recyclerViewStaggered = findViewById(R.id.recycler_view2)

        val lieAdapter = LieAdapter()
        recyclerViewHorizontal.adapter = lieAdapter
        recyclerViewHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        val dividerItemDecorationHorizontal = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        dividerItemDecorationHorizontal.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_vertical)!!)
        recyclerViewHorizontal.addItemDecoration(dividerItemDecorationHorizontal)
        lieAdapter.setList(RecyclerViewRepository.getCountryList())

        val gridAdapter = StandAdapter()
        recyclerViewGrid.adapter = gridAdapter
        recyclerViewGrid.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_horizontal)!!)
        recyclerViewGrid.addItemDecoration(dividerItemDecoration)
        gridAdapter.setFruitList(RecyclerViewRepository.getColorList())

        val staggeredAdapter = StandAdapter()
        recyclerViewStaggered.adapter = gridAdapter
        recyclerViewStaggered.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredAdapter.setFruitList(RecyclerViewRepository.getColorList())
    }

    companion object {

        fun openActivity(context: androidx.fragment.app.FragmentActivity?) {
            val intent = Intent(context, RecyclerViewActivity::class.java)
            context?.startActivity(intent)
        }
    }
}
