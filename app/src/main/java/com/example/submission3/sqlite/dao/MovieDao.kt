package com.example.submission3.sqlite.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission3.sqlite.model.MovieFavorite

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteMovie(movieFavorite: MovieFavorite)

    @Query("Delete From Favorite Where id_movie =:id")
    fun removeFavorite(id: String)

    @Query("Select * From Favorite Where type =:type")
    fun fetchFavoriteMovie(type: String): LiveData<List<MovieFavorite>>

    @Query("Select * From Favorite Where id_movie =:id")
    fun fetchMovieById(id: String): LiveData<MovieFavorite>

    @Query("Select * From Favorite")
    fun getAllMovieFavorite() : List<MovieFavorite>

    @Query("Select * From Favorite")
    fun fetchAllMovie() : Cursor
}