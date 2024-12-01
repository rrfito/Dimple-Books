package com.example.dimplebooks.data.network

import retrofit2.http.GET
import retrofit2.Call
import com.example.dimplebooks.data.model.BookResponse
import retrofit2.http.Query

interface apiService {

    @GET("volumes")
    fun fetchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int
    ): Call<BookResponse>

}