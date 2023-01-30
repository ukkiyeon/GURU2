package com.example.guru2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter(private val context: Context) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>(){

    var datas = mutableListOf<WalkData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.app_main,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val walks_num: TextView = itemView.findViewById(R.id.walks_num1)

        fun bind(item: WalkData) {
            walks_num.text = item.walk_addr

        }
    }

}