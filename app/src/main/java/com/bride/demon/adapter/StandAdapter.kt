package com.bride.demon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bride.demon.R

class StandAdapter : RecyclerView.Adapter<StandAdapter.ViewHolder>() {
    private val fruitList = ArrayList<String>()

    fun setFruitList(fruitList: List<String>?) {
        this.fruitList.clear()
        if (!fruitList.isNullOrEmpty()) {
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
