package com.example.submission3.model

import com.google.gson.annotations.SerializedName


class MResponse(
    @SerializedName("results")
    val results : List<Movie>
)