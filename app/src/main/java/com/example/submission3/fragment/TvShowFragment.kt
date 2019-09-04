package com.example.submission3.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.example.submission3.R
import com.example.submission3.activity.DetailMovieActivity
import com.example.submission3.adapter.MovieAdapter
import com.example.submission3.interfaces.ItemOnClickListener
import com.example.submission3.model.Movie
import com.example.submission3.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.view.*

class TvShowFragment : Fragment(), ItemOnClickListener {

    companion object {
        const val TYPE = "Tv Show"
    }

    private var movieAdapter: MovieAdapter? = null
    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var movieViewModel: MovieViewModel
    private val listTvShow: List<Movie> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initialisation View Model
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        //Setup Recyclerview and adapter for skeleton screen
        movieAdapter = MovieAdapter(view.context, listTvShow, TYPE, this)
        linearLayoutManager = LinearLayoutManager(view.context)
        view.rv_tv_show.layoutManager = linearLayoutManager
        view.rv_tv_show.setHasFixedSize(true)

        showLoading(true, view)
        movieViewModel.setTvShow()
        movieViewModel.getMovie().observe(this, getTvShow)

    }

    private val getTvShow = Observer<List<Movie>> { tvShow ->
        if (tvShow != null) {
            movieAdapter = view?.context?.let { MovieAdapter(it, tvShow, TYPE, this) }
            movieAdapter?.notifyDataSetChanged()
            view?.rv_tv_show?.adapter = movieAdapter
        }
    }

    private fun showLoading(state: Boolean, view: View?) {
        if (state) {
            skeletonScreen =
                Skeleton.bind(view?.rv_tv_show).adapter(movieAdapter).load(R.layout.item_data_skeleton).show()
        } else {
            skeletonScreen.hide()
        }
    }

    override fun itemClickListener(position: Int, movies: List<Movie>) {
        val intent = Intent(view?.context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.id, movies[position].id)
        intent.putExtra(DetailMovieActivity.type, TYPE)
        startActivity(intent)
    }
}