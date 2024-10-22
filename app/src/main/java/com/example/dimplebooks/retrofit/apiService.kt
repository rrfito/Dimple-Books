package com.example.dimplebooks.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object apiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val bookApi = retrofit.create(bookApi::class.java)
}