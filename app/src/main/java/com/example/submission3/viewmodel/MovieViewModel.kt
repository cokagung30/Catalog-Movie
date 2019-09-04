package com.example.submission3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3.fragment.MovieFragment
import com.example.submission3.fragment.TvShowFragment
import com.example.submission3.model.MResponse
import com.example.submission3.model.Movie
import com.example.submission3.model.MovieDetails
import com.example.submission3.rest.ApiKey
import com.example.submission3.rest.Apirest
import com.example.submission3.rest.common.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private lateinit var apiRest: Apirest
    private var movies = MutableLiveData<List<Movie>>()
    private var movieDetails = MutableLiveData<MovieDetails>()

    fun setMovie() {
        apiRest = Common.apiRest
        val apiKey = ApiKey()

        apiRest.fetchMovie(apiKey.getApiKey()).enqueue(object : Callback<MResponse> {
            override fun onFailure(call: Call<MResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MResponse>, response: Response<MResponse>) {
                if (response.body() != null) {
                    movies.postValue(response.body()!!.results)
                }
            }

        })
    }

    fun setTvShow() {
        apiRest = Common.apiRest
        val apiKey = ApiKey()

        apiRest.fetchTvShow(apiKey.getApiKey()).enqueue(object : Callback<MResponse> {
            override fun onFailure(call: Call<MResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MResponse>, response: Response<MResponse>) {
                if (response.body() != null) {
                    movies.postValue(response.body()!!.results)
                }
            }

        })
    }

    fun setDetailMovie(id: Int, type: String) {
        apiRest = Common.apiRest
        val apiKey = ApiKey()

        if (type == MovieFragment.TYPE) {
            apiRest.fetchDetailMovie(id, apiKey.getApiKey()).enqueue(object : Callback<MovieDetails> {
                override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                    if (response.body() != null) {
                        movieDetails.value = response.body()
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {

                }

            })
        } else if (type == TvShowFragment.TYPE) {
            apiRest.fetchDetailTvShow(id, apiKey.getApiKey()).enqueue(object : Callback<MovieDetails> {
                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                }

                override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                    if (response.body() != null) {
                        movieDetails.value = response.body()
                    }
                }

            })
        }
    }

    fun getDetailMovie(): LiveData<MovieDetails>{
        return movieDetails
    }

    fun getMovie(): LiveData<List<Movie>> {
        return movies
    }

    fun searchMovie(nameMovie: String){
        apiRest = Common.apiRest
        val apiKey = ApiKey()
        apiRest.searchMovie(apiKey.getApiKey(), nameMovie).enqueue(object : Callback<MResponse>{
            override fun onFailure(call: Call<MResponse>, t: Throwable) {
                movies.value = null
            }

            override fun onResponse(call: Call<MResponse>, response: Response<MResponse>) {
                if (response.body() != null){
                    movies.value = response.body()!!.results
                }
            }

        })
    }

    fun searchTvShow(nameMovie: String){
        apiRest = Common.apiRest
        val apiKey = ApiKey()

        apiRest.searchTvShow(apiKey.getApiKey(), nameMovie).enqueue(object : Callback<MResponse>{
            override fun onResponse(call: Call<MResponse>, response: Response<MResponse>) {
                if (response.body() != null){
                    movies.value = response.body()!!.results
                }
            }

            override fun onFailure(call: Call<MResponse>, t: Throwable) {
                movies.value = null
            }

        })
    }

    fun upComingMovie (dateReleased: String){
        apiRest = Common.apiRest
        val apiKey = ApiKey()

        apiRest.getUpComingMovie(apiKey.getApiKey(), dateReleased, dateReleased).enqueue(object : Callback<MResponse>{
            override fun onFailure(call: Call<MResponse>, t: Throwable) {
                movies.value = null
            }

            override fun onResponse(call: Call<MResponse>, response: Response<MResponse>) {
                if (response.body() != null){
                    movies.value = response.body()!!.results
                }
            }

        })
    }

}