package com.example.dimplebooks.data.repository

import com.example.dimplebooks.data.model.ProductPostRequest
import com.example.dimplebooks.data.model.ProductResponse
import com.example.dimplebooks.data.network.apiService


class ProductRepository(
    private val api: apiService
) {
    private val tokenBearer = "Bearer x6WxpZW2lCyQWgXubWOCgGgdB1lle01b64-wbDBu4L6asNJeSA"

    suspend fun fetchProduct(): ProductResponse {
        return api.getProducts(tokenBearer)
    }
    suspend fun createProduct(products: List<ProductPostRequest>): ProductResponse {
        return api.createProduct(tokenBearer, products)
    }


}