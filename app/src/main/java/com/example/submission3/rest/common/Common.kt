package com.example.submission3.rest.common

import com.example.submission3.rest.Apirest
import com.example.submission3.rest.Client

object Common {
    private var url = "https://api.themoviedb.org/3/"
    val apiRest: Apirest
        get() = Client.getClient(url).create(Apirest::class.java)
}