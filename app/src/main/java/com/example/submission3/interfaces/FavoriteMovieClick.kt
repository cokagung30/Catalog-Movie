package com.example.submission3.interfaces

import com.example.submission3.sqlite.model.MovieFavorite

interface FavoriteMovieClick {
    fun itemClickListener(position: Int, movies: List<MovieFavorite>)
}