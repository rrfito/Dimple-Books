package com.example.dimplebooks.data.model

data class ProductPostRequest(
    val name : String,
    val desription : String,
    val price : Int
)

data class ProductResponse(
    val cursor: String,
    val items: List<ProductItems>,
    val next_page: String,
)

data class ProductItems(
    val _created: Double,
    val _data_type: String,
    val _is_deleted: Boolean,
    val _modified: Double,
    val _self_link: String,
    val _user: String,
    val _uuid: String,
    val description: String,
    val name: String,
    val price: Int,
)
