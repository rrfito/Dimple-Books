package com.example.dimplebooks.data.repository

import com.example.dimplebooks.BuildConfig
import com.example.dimplebooks.data.model.Review
import com.example.dimplebooks.data.model.reviewResponse
import com.example.dimplebooks.data.network.apiService

class reviewRepository(private val apiService: apiService) {

    private val tokenBearer = "Bearer 3ewBn5S_BwjZ45-RFfHafPv6moU8Ikb6ab5IVnrsTcobLtLHTg"
    suspend fun fetchReview(): reviewResponse {
        return apiService.getReview(tokenBearer)
    }
    suspend fun creatReview(reviewItem: List<Review>): reviewResponse {
        return apiService.createReview(tokenBearer,reviewItem)
    }
    suspend fun deleteReview(uuid: String): reviewResponse {
        return apiService.deleteTask(tokenBearer, uuid =uuid)
    }

}