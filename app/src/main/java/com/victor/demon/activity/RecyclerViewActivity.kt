package com.victor.demon.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.MotionEvent
import com.victor.demon.R
import com.victor.demon.adapter.LieAdapter
import com.victor.demon.adapter.StandAdapter
import com.victor.demon.repository.RecyclerViewRepository

/**
 *
 * Created by shixin on 2018/3/6.
 */
class RecyclerViewActivity : AppCompatActivity(), RecyclerView.OnItemTouchListener {
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return true
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    private lateinit var recyclerViewHorizontal: RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initView()
    }

    private fun initView() {
        recyclerViewHorizontal = findViewById(R.id.recycler_view_horizontal)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView2 = findViewById(R.id.recycler_view2)

        val lieAdapter = LieAdapter()
        recyclerViewHorizontal.adapter = lieAdapter
        recyclerViewHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        val dividerItemDecorationHorizontal = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        dividerItemDecorationHorizontal.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_vertical)!!)
        recyclerViewHorizontal.addItemDecoration(dividerItemDecorationHorizontal)
        lieAdapter.setList(RecyclerViewRepository.getCountryList())
        recyclerViewHorizontal.addOnItemTouchListener(this)

        val standAdapter = StandAdapter()
        recyclerView.adapter = standAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        /*val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_horizontal)!!)
        recyclerView.addItemDecoration(dividerItemDecoration)*/
        standAdapter.setFruitList(RecyclerViewRepository.getColorList())

        val standAdapter2 = StandAdapter()
        recyclerView2.adapter = standAdapter
        recyclerView2.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        standAdapter2.setFruitList(RecyclerViewRepository.getColorList())
    }

    companion object {

        fun openActivity(context: FragmentActivity?) {
            val intent = Intent(context, RecyclerViewActivity::class.java)
            context?.startActivity(intent)
        }
    }
}
