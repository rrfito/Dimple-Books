package com.example.dimplebooks.data.network

import retrofit2.http.GET
import retrofit2.Call
import com.example.dimplebooks.data.model.BookResponse
import com.example.dimplebooks.data.model.Review
import com.example.dimplebooks.data.model.reviewResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface apiService {

    @GET("volumes")
    fun fetchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int
    ): Call<BookResponse>

    @GET("Review")
    suspend fun getReview(
        @Header("Authorization") token: String,
    ) : reviewResponse

    @POST("Review")
    suspend fun createReview(
        @Header("Authorization") token: String,
        @Body Review: List<Review>
    ) : reviewResponse

    @DELETE("Review/{uuid}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Path("uuid") uuid: String
    ): reviewResponse


}