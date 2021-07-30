package com.appintuitions.themovieapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appintuitions.themovieapp.R
import com.appintuitions.themovieapp.view.DetailsActivity
import com.appintuitions.themovieapp.viewmodel.models.Movie
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class MoviesAdapter(private val context: Context, private val list: ArrayList<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    class MyViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

        var tv: TextView = containerView.findViewById(R.id.tvRv)
        var tvOverview: TextView = containerView.findViewById(R.id.tv_overview)
        var ivDp: CircleImageView = containerView.findViewById(R.id.iv_dp)
        var lt_root: View = containerView.findViewById(R.id.lt_root)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.lt_rv, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tv.text = list[position].title

        holder.lt_root.setOnClickListener{
            context.startActivity(Intent(context,DetailsActivity::class.java).putExtra("movie_id",list[position].id))
        }

        Glide
            .with(context)
            .load("https://www.themoviedb.org/t/p/w50_and_h50_face"+list[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.tmd)
            .into(holder.ivDp)

        holder.tvOverview.text = list[position].overview



    }

    override fun getItemCount(): Int {
        return list.size
    }


}