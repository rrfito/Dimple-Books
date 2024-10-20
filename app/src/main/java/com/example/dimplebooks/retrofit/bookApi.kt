package com.example.dimplebooks.retrofit

import retrofit2.http.GET
import retrofit2.Call
import com.example.dimplebooks.model.bookResponse
import retrofit2.http.Query

interface bookApi {

    @GET("volumes")
    fun searchBooks(
    @Query("q") query: String,
    @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int
    ): Call<bookResponse>
}