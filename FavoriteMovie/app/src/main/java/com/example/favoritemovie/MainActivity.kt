package com.example.favoritemovie

import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favoritemovie.adapter.MovieAdapter
import com.example.favoritemovie.model.Movie
import com.example.favoritemovie.utils.DatabaseContract
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayoutManager
    private lateinit var movieAdapter: MovieAdapter
    private var listMovie: MutableList<Movie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayout = LinearLayoutManager(this)
        rv_favorite_movie.layoutManager = linearLayout
        rv_favorite_movie.setHasFixedSize(true)

        supportLoaderManager.initLoader(1, null, loaderCallsback)
    }

    private val loaderCallsback = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                applicationContext,
                DatabaseContract.NoteColumns.CONTENT_URI,
                null,
                null,
                null,
                null
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            if (data != null) {
                while (data.moveToNext()) {
                    val movieId = data.getString(data.getColumnIndex("id_movie"))
                    val voteAverage = data.getString(data.getColumnIndex("vote_average"))
                    val posterPath = data.getString(data.getColumnIndex("poster_path"))
                    val title = data.getString(data.getColumnIndex("original_title"))
                    val releaseDate = data.getString(data.getColumnIndex("release_date"))
                    val originalName = data.getString(data.getColumnIndex("original_name"))
                    val firstAirDate = data.getString(data.getColumnIndex("first_air_date"))
                    val type = data.getString(data.getColumnIndex("type"))

                    val movies = Movie(
                        movieId.toInt(),
                        voteAverage.toDouble(),
                        posterPath,
                        title,
                        releaseDate,
                        originalName,
                        firstAirDate,
                        type
                    )
                    listMovie.add(movies)

                    loadedData(listMovie)
                }
            }

        }

        override fun onLoaderReset(loader: Loader<Cursor>) {

        }

    }

    private fun loadedData(listMovie: MutableList<Movie>) {
        if (listMovie.isNotEmpty()) {
            line_data_not_found.visibility = View.GONE
            rv_favorite_movie.visibility = View.VISIBLE
            movieAdapter = MovieAdapter(this, listMovie)
            movieAdapter.notifyDataSetChanged()
            rv_favorite_movie.adapter = movieAdapter
        } else {
            line_data_not_found.visibility = View.VISIBLE
            rv_favorite_movie.visibility = View.GONE
        }

    }
}
