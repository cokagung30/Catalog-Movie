package com.example.submission3.sqlite.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
class MovieFavorite(

    @PrimaryKey
    @ColumnInfo(name = "id_movie")
    val id_movie: Int?,

    @ColumnInfo(name = "vote_average")
    val vote_average: Double?,

    @ColumnInfo(name = "poster_path")
    val poster_path: String?,

    @ColumnInfo(name = "original_title")
    val original_title: String?,

    @ColumnInfo(name = "release_date")
    val release_date: String?,

    @ColumnInfo(name = "original_name")
    val original_name: String?,

    @ColumnInfo(name = "first_air_date")
    val first_air_date: String?,

    @ColumnInfo(name = "type")
    val type: String?
)