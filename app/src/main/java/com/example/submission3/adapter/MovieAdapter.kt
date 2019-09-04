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
import com.example.submission3.interfaces.ItemOnClickListener
import com.example.submission3.model.Movie
import kotlinx.android.synthetic.main.item_data.view.*

class MovieAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private val type: String,
    private val listener: ItemOnClickListener
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poster = "${BuildConfig.BASE_URL_IMG}w185${movies[position].poster_path}"

        Glide.with(context).load(poster).into(holder.itemView.iv_poster)
        holder.itemView.rate_movie.rating = movies[position].vote_average.toFloat()

        if (type == MovieFragment.TYPE) {
            holder.itemView.tv_title.text = movies[position].original_title
            holder.itemView.tv_release_date.text = movies[position].release_date
        } else if (type == TvShowFragment.TYPE) {
            holder.itemView.tv_title.text = movies[position].original_name
            holder.itemView.tv_release_date.text = movies[position].first_air_date
        }

        holder.itemView.cv_movie.setOnClickListener {
            listener.itemClickListener(position, movies)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}