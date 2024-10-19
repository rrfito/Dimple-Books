package com.example.dimplebooks.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object libraryService {
    val BASE_URL = "https://www.googleapis.com/books/v1/"
    val api: bookApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(bookApi::class.java)
    }
}



