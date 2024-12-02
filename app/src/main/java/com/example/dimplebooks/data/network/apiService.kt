package com.example.dimplebooks.data.network

import retrofit2.http.GET
import retrofit2.Call
import com.example.dimplebooks.data.model.BookResponse
import com.example.dimplebooks.data.model.ProductPostRequest
import com.example.dimplebooks.data.model.ProductResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface apiService {

    @GET("volumes")
    fun fetchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int
    ): Call<BookResponse>

    @POST("product")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body products: List<ProductPostRequest>,
    ): ProductResponse

    @GET("product")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): ProductResponse

}