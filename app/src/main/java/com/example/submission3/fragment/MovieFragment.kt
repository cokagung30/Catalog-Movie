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
import kotlinx.android.synthetic.main.fragment_movie.view.*


class MovieFragment : Fragment(), ItemOnClickListener {

    companion object {
        const val TYPE = "Movie"
    }

    private var movieAdapter: MovieAdapter? = null
    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var movieViewModel: MovieViewModel
    private val listMovie: List<Movie> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = ViewModelProviders.of(this@MovieFragment).get(MovieViewModel::class.java)

        movieAdapter = MovieAdapter(view.context, listMovie, TYPE, this)
        linearLayoutManager = LinearLayoutManager(view.context)
        view.rv_movie.layoutManager = linearLayoutManager
        view.rv_movie.setHasFixedSize(true)

        showLoading(true, view)
        movieViewModel.setMovie()
        movieViewModel.getMovie().observe(this, getMovie)
    }

    override fun itemClickListener(position: Int, movies: List<Movie>) {
        val intent = Intent(view?.context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.id, movies[position].id)
        intent.putExtra(DetailMovieActivity.type, TYPE)
        startActivity(intent)
    }

    private val getMovie = Observer<List<Movie>> { movie ->
        if (movie != null) {
            view?.rv_movie?.visibility = View.VISIBLE
            view?.line_data_not_found?.visibility = View.GONE
            movieAdapter = view?.context?.let { MovieAdapter(it, movie, TYPE, this) }
            movieAdapter?.notifyDataSetChanged()
            view?.rv_movie?.adapter = movieAdapter
        }  else {
            view?.line_data_not_found?.visibility = View.VISIBLE
            view?.rv_movie?.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean, view: View) {
        if (state) {
            skeletonScreen = Skeleton.bind(view.rv_movie).adapter(movieAdapter).load(R.layout.item_data_skeleton).show()
        } else{
            skeletonScreen.hide()
        }
    }

}