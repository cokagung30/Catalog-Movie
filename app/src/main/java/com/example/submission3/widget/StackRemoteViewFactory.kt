package com.example.submission3.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.submission3.BuildConfig
import com.example.submission3.R
import com.example.submission3.sqlite.AppDatabase
import com.example.submission3.sqlite.model.MovieFavorite

class StackRemoteViewFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var movieFavorite: List<MovieFavorite>? = null

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(p0: Int): Long = 1

    override fun onDataSetChanged() {
        movieFavorite = AppDatabase.getInstance().movieDao().getAllMovieFavorite()
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.widget_item)
        val imageUrl = "${BuildConfig.BASE_URL_IMG}w185${movieFavorite?.get(position)?.poster_path}"

        val bitmap = Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .submit(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .get()

        remoteView.setImageViewBitmap(R.id.movie_poster, bitmap)

        return remoteView
    }

    override fun getCount(): Int = movieFavorite?.size!!

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}

}