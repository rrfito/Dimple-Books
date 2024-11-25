package com.example.dimplebooks.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val bookApi = retrofit.create(com.example.dimplebooks.data.network.bookApi::class.java)
}