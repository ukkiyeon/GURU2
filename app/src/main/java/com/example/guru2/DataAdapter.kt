package com.example.guru2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.community_writing.view.*

class DataAdapter (list: ArrayList<DataModel>) : RecyclerView.Adapter<CustomViewHolder>() {
    var mList : ArrayList<DataModel> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.community_writing,parent)
        /*val view = LayoutInflater.from(parent.context).inflate(R.layout.community_writing,parent,false)*/
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val p = mList.get(position)
        holder.setHolder(p)
    }

}

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setHolder(data: DataModel) {
        itemView.title_text_form.text = data.title
        itemView.content_text_form.text = data.contents
    }
}