package com.bride.demon.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bride.demon.R
import com.bride.demon.adapter.LieAdapter
import com.bride.demon.adapter.StandAdapter
import com.bride.demon.repository.RecyclerViewRepository

/**
 *
 * Created by shixin on 2018/3/6.
 */
class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var recyclerViewHorizontal: androidx.recyclerview.widget.RecyclerView
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var recyclerView2: androidx.recyclerview.widget.RecyclerView

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
        recyclerViewHorizontal.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, true)
        val dividerItemDecorationHorizontal = androidx.recyclerview.widget.DividerItemDecoration(this, androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL)
        dividerItemDecorationHorizontal.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_vertical)!!)
        recyclerViewHorizontal.addItemDecoration(dividerItemDecorationHorizontal)
        lieAdapter.setList(RecyclerViewRepository.getCountryList())

        val standAdapter = StandAdapter()
        recyclerView.adapter = standAdapter
        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3, androidx.recyclerview.widget.GridLayoutManager.HORIZONTAL, false)
        /*val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_horizontal)!!)
        recyclerView.addItemDecoration(dividerItemDecoration)*/
        standAdapter.setFruitList(RecyclerViewRepository.getColorList())

        val standAdapter2 = StandAdapter()
        recyclerView2.adapter = standAdapter
        recyclerView2.layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(2, androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL)
        standAdapter2.setFruitList(RecyclerViewRepository.getColorList())
    }

    companion object {

        fun openActivity(context: androidx.fragment.app.FragmentActivity?) {
            val intent = Intent(context, RecyclerViewActivity::class.java)
            context?.startActivity(intent)
        }
    }
}
