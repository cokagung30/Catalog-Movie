package com.example.submission3.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.example.submission3.R
import com.example.submission3.activity.DetailMovieActivity
import com.example.submission3.adapter.MovieFavoriteAdapter
import com.example.submission3.interfaces.FavoriteMovieClick
import com.example.submission3.sqlite.AppDatabase
import com.example.submission3.sqlite.model.MovieFavorite
import kotlinx.android.synthetic.main.fragment_tv_show.view.*

class FavoriteTvShowFragment : Fragment(), FavoriteMovieClick {

    private var favoriteAdapter: MovieFavoriteAdapter? = null
    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val listTvShowFavorite: List<MovieFavorite> = ArrayList()
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = TvShowFragment.TYPE

        favoriteAdapter = MovieFavoriteAdapter(view.context, listTvShowFavorite, type, this)
        showLoading(true, view)

        linearLayoutManager = LinearLayoutManager(view.context)
        view.rv_tv_show.layoutManager = linearLayoutManager
        view.rv_tv_show.setHasFixedSize(true)

        AppDatabase.getInstance().movieDao().fetchFavoriteMovie(TvShowFragment.TYPE)
            .observe(this, getTvShow)
    }

    private val getTvShow = Observer<List<MovieFavorite>> { favorite ->
        if (favorite.isNotEmpty()) {
            view?.rv_tv_show?.visibility = View.VISIBLE
            view?.line_data_tv_not_found?.visibility = View.GONE
            showLoading(false, view)
            favoriteAdapter = view?.context?.let { MovieFavoriteAdapter(it, favorite, type, this) }
            favoriteAdapter?.notifyDataSetChanged()
            view?.rv_tv_show?.adapter = favoriteAdapter
        } else {
            view?.line_data_tv_not_found?.visibility = View.VISIBLE
            view?.rv_tv_show?.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean, view: View?) {
        if (state) {
            skeletonScreen =
                Skeleton.bind(view?.rv_tv_show).adapter(favoriteAdapter).duration(1000)
                    .load(R.layout.item_data_skeleton).show()
        } else {
            skeletonScreen.hide()
        }
    }

    override fun itemClickListener(position: Int, movies: List<MovieFavorite>) {
        val intent = Intent(view?.context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.id, movies[position].id_movie)
        intent.putExtra(DetailMovieActivity.type, movies[position].type)
        startActivity(intent)
    }

}