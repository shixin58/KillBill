package com.yiche.example.newestversiondemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.yiche.example.newestversiondemo.R

import java.util.ArrayList

/**
 *
 * Created by shixin on 2018/3/6.
 */
class StandAdapter : RecyclerView.Adapter<StandAdapter.ViewHolder>() {
    private val fruitList = ArrayList<String>()

    fun setFruitList(fruitList: List<String>?) {
        this.fruitList.clear()
        if (fruitList != null && !fruitList.isEmpty()) {
            this.fruitList.addAll(fruitList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_stand, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = fruitList[position]
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    }
}
