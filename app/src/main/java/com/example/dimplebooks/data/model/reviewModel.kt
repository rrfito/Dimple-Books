package com.example.dimplebooks.data.model


data class Review(
    val userid: String,
    val username: String? = null,
    val bookTitle: String,
    val rating: Float,
    val comment: String,
    val imageUrl: String? = null,
    val dateTime: String
)
data class ShowsReview(
    val _uuid: String,
    val bookTitle: String,
    val username: String?,
    val rating: Float,
    val comment: String,
    val imageUrl: String? = null,
    val dateTime: String
)

data class reviewResponse(
    val cursor: String? = null,
    val items: List<reviewItems>,
    val next_page: String? = null
)

data class reviewItems(
    val _created: Double,
    val _data_type: String,
    val _is_deleted: Boolean,
    val _modified: Double,
    val _self_link: String,
    val _user: String,
    val _uuid: String,
    val userid : String,
    val username : String? = null,
    val bookTitle: String,
    val rating : Float,
    val comment : String,
    val imageUrl: String? = null,
    val dateTime: String
)
