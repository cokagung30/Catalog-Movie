package com.example.submission3.interfaces

import com.example.submission3.model.Movie

interface ItemOnClickListener {
    fun itemClickListener(position: Int, movies: List<Movie>)
}