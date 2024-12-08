package com.example.dimplebooks.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"
    private const val BASE_URL_REVIEW = "https://crudapi.co.uk/api/v1/"

    val bookApi: apiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiService::class.java)
    }
    val ReviewApi: apiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_REVIEW)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiService::class.java)
    }
}

