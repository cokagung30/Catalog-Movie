package com.example.favoritemovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favoritemovie.BuildConfig
import com.example.favoritemovie.R
import com.example.favoritemovie.model.Movie
import kotlinx.android.synthetic.main.item_data.view.*

class MovieAdapter(private val context: Context, private val movie: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_data, parent, false))
    }

    override fun getItemCount(): Int = movie.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val type = movie[position].type
        Glide.with(context).load("${BuildConfig.BASE_URL_IMG}w185/${movie[position].poster_path}")
            .into(holder.itemView.iv_poster)

        holder.itemView.rate_movie.rating = movie[position].vote_average?.toFloat()!!

        if (type == context.resources.getString(R.string.tv_show_title)) {
            holder.itemView.tv_title.text = movie[position].original_name
            holder.itemView.tv_release_date.text = movie[position].first_air_date
        } else {
            holder.itemView.tv_title.text = movie[position].original_title
            holder.itemView.tv_release_date.text = movie[position].release_date
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}