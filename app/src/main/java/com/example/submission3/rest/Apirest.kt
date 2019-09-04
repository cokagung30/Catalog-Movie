package com.example.submission3.rest

import com.example.submission3.model.MResponse
import com.example.submission3.model.MovieDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Apirest {

    @GET("movie/popular")
    fun fetchMovie(@Query("api_key") apiKey: String): Call<MResponse>

    @GET("tv/popular")
    fun fetchTvShow(@Query("api_key") apiKey: String): Call<MResponse>

    @GET("movie/{movie_id}")
    fun fetchDetailMovie(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<MovieDetails>

    @GET("tv/{movie_id}")
    fun fetchDetailTvShow(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<MovieDetails>

    @GET("search/movie")
    fun searchMovie(@Query("api_key") apiKey: String, @Query("query") nama: String): Call<MResponse>

    @GET("search/tv")
    fun searchTvShow(@Query("api_key") apiKey: String, @Query("query") nama: String): Call<MResponse>

    @GET("discover/movie")
    fun getUpComingMovie(
        @Query("api_key") apiKey: String, @Query("primary_release_date.gte") primaryReleaseDateGte: String, @Query(
            "primary_release_date.lte"
        ) primaryReleaseDateLte: String
    ): Call<MResponse>
}
