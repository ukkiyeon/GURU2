package com.example.guru2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
// 에러 import kotlinx.android.synthetic.main.community_writing.view.*
/* 에러
class DataAdapter (list: ArrayList<DataModel>) : RecyclerView.Adapter<CustomViewHolder>() {
    var mList : ArrayList<DataModel> = list
    /* 에러
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.community_writing,parent)
        /*val view = LayoutInflater.from(parent.context).inflate(R.layout.community_writing,parent,false)*/
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    /* 에러
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val p = mList.get(position)
        holder.setHolder(p)
    }

}
/* 에러
class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setHolder(data: DataModel) {
        itemView.title_text_form.text = data.title
        itemView.content_text_form.text = data.contents
    }
}*/