package com.example.dimplebooks.model

data class BookResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<bookModel>?
)

data class bookModel(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val price: Int?,
    val imageUrl: String,
    val description: String,
    val categories: List<String>,
    val saleability: String

)
