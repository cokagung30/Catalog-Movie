package com.example.submission3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3.BuildConfig
import com.example.submission3.R
import com.example.submission3.fragment.MovieFragment
import com.example.submission3.fragment.TvShowFragment
import com.example.submission3.interfaces.FavoriteMovieClick
import com.example.submission3.sqlite.model.MovieFavorite
import kotlinx.android.synthetic.main.item_data.view.*

class MovieFavoriteAdapter(
    private val context: Context,
    private val favorite: List<MovieFavorite>,
    private val type: String,
    private val listener : FavoriteMovieClick
) : RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poster = "${BuildConfig.BASE_URL_IMG}w185${favorite[position].poster_path}"

        Glide.with(context).load(poster).into(holder.itemView.iv_poster)
        holder.itemView.rate_movie.rating = favorite[position].vote_average?.toFloat()!!

        if (type == MovieFragment.TYPE) {
            holder.itemView.tv_title.text = favorite[position].original_title
            holder.itemView.tv_release_date.text = favorite[position].release_date
        } else if (type == TvShowFragment.TYPE) {
            holder.itemView.tv_title.text = favorite[position].original_name
            holder.itemView.tv_release_date.text = favorite[position].first_air_date
        }

        holder.itemView.cv_movie.setOnClickListener {
            listener.itemClickListener(position, favorite)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}