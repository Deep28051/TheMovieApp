package com.appintuitions.rvkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appintuitions.rvkotlin.R


//import kotlinx.android.synthetic.main.lt_rv.*

class MyAdapter(private val context: Context, private val list: ArrayList<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

        var tv: TextView = containerView.findViewById(R.id.tvRv)
        //var cb: CheckBox = containerView.findViewById(R.id.cbRv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.lt_rv, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tv.text = list[position]

        //holder.cb.isChecked = position%2 == 0

    }

    override fun getItemCount(): Int {
        return list.size
    }


}