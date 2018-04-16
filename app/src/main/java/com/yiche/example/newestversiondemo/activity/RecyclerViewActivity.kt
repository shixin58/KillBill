package com.yiche.example.newestversiondemo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.yiche.example.newestversiondemo.R
import com.yiche.example.newestversiondemo.adapter.LieAdapter
import com.yiche.example.newestversiondemo.adapter.StandAdapter
import com.yiche.example.newestversiondemo.repository.RecyclerViewRepository

/**
 *
 * Created by shixin on 2018/3/6.
 */
class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var recyclerViewHorizontal: RecyclerView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initView()
    }

    private fun initView() {
        recyclerViewHorizontal = findViewById(R.id.recycler_view_horizontal)
        recyclerView = findViewById(R.id.recycler_view)

        val lieAdapter = LieAdapter()
        recyclerViewHorizontal.adapter = lieAdapter
        recyclerViewHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        val dividerItemDecorationHorizontal = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        dividerItemDecorationHorizontal.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_vertical)!!)
        recyclerViewHorizontal.addItemDecoration(dividerItemDecorationHorizontal)
        lieAdapter.setList(RecyclerViewRepository.getCountryList())

        val standAdapter = StandAdapter()
        recyclerView.adapter = standAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_horizontal)!!)
        recyclerView.addItemDecoration(dividerItemDecoration)
        standAdapter.setFruitList(RecyclerViewRepository.getColorList())
    }

    companion object {

        fun openActivity(context: FragmentActivity?) {
            val intent = Intent(context, RecyclerViewActivity::class.java)
            context?.startActivity(intent)
        }
    }
}
