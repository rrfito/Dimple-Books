package com.example.dimplebooks.retrofit

import com.example.dimplebooks.model.BookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface bookApi {
    @GET("volumes")
    fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apiKey: String
    ): Call<BookResponse>
}