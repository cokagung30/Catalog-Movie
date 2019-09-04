package com.example.submission3.activity

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.submission3.R
import com.example.submission3.fragment.MovieFragment
import com.example.submission3.fragment.TvShowFragment
import com.example.submission3.model.MovieDetails
import com.example.submission3.sqlite.AppDatabase
import com.example.submission3.sqlite.model.MovieFavorite
import com.example.submission3.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*


class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val id = "id_movie"
        const val type = "type_movie"
    }

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var skeletonScreen: SkeletonScreen
    private var idMovie: Int? = null
    private var typeMovie: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        idMovie = intent.getIntExtra(id, 0)
        typeMovie = intent.getStringExtra(type)
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        showLoading(true)
        movieViewModel.setDetailMovie(idMovie!!, intent.getStringExtra(type))
        movieViewModel.getDetailMovie().observe(this, getDetailMovie)

    }

    private fun favorite(state: Boolean, movie: MovieDetails) {
        iv_favorite.setOnClickListener {
            val messageSave = getString(R.string.save)
            val messageRemove = getString(R.string.remove)
            if (state) {
                val movieFavorite = MovieFavorite(
                    movie.id,
                    movie.vote_average,
                    movie.poster_path,
                    movie.original_title,
                    movie.release_date,
                    movie.original_name,
                    movie.first_air_date,
                    typeMovie
                )
                AppDatabase.getInstance().movieDao().saveFavoriteMovie(movieFavorite)
                if (typeMovie == MovieFragment.TYPE) {
                    Toast.makeText(this, "${movie.original_title} $messageSave", Toast.LENGTH_SHORT)
                        .show()
                } else if (typeMovie == TvShowFragment.TYPE) {
                    Toast.makeText(this, "${movie.original_name} $messageSave", Toast.LENGTH_SHORT)
                        .show()
                }

            } else {
                AppDatabase.getInstance().movieDao().removeFavorite(idMovie.toString())
                if (typeMovie == MovieFragment.TYPE) {
                    Toast.makeText(
                        this,
                        "${movie.original_title} $messageRemove",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (typeMovie == TvShowFragment.TYPE) {
                    Toast.makeText(
                        this,
                        "${movie.original_name} $messageRemove",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun setUpTvShowValue(originalName: String?, firstAirDate: String?) {
        tv_name_movie.text = originalName
        tv_relase_date_details.text = firstAirDate
    }

    private fun setUpMovieTypeValue(title: String, releaseDate: String) {
        tv_name_movie.text = title
        tv_relase_date_details.text = releaseDate
    }

    private val getDetailMovie = Observer<MovieDetails> { movie ->
        val backdrop = "https://image.tmdb.org/t/p/original${movie?.backdrop_path}"
        val poster = "https://image.tmdb.org/t/p/w342${movie?.poster_path}"

        showLoading(false)

        Glide.with(this).load(backdrop).into(iv_thumbnail_detail_big)
        Glide.with(this).load(poster).into(iv_thumbnail_detail)
        tv_rating_details.text = movie?.vote_average.toString()

        tv_description_details.text = movie?.overview

        AppDatabase.getInstance().movieDao().fetchMovieById(idMovie.toString())
            .observe(this, Observer { favorite ->
                if (favorite == null) {
                    Glide.with(this).load(R.drawable.ic_favorite_black_24dp).into(iv_favorite)
                    favorite(true, movie)

                } else {
                    Glide.with(this).load(R.drawable.ic_favorite_red_24dp).into(iv_favorite)
                    favorite(false, movie)
                }
            })

        if (typeMovie == MovieFragment.TYPE) {
            movie?.original_title?.let { setUpMovieTypeValue(it, movie.release_date) }
        } else if (typeMovie == TvShowFragment.TYPE) {
            setUpTvShowValue(movie.original_name, movie.first_air_date)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            skeletonScreen = Skeleton.bind(rootview)
                .load(R.layout.detail_movie_skeleton)
                .show()
        } else {
            skeletonScreen.hide()
        }
    }

}
