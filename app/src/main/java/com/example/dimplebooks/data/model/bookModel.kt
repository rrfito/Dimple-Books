package com.example.dimplebooks.data.model



data class bookModel(
    val id : String,
    val title: String,
    val subtitle: String? = null,
    val authors: List<String>,
    val publisher: String,
    val price: Int?,
    val imageUrl: String,
    val description: String,
    val categories: List<String>,
    val saleability: String,
    val publishedDate: String? = null,
    val pageCount: Int? = null,
    val language: String? = null,
    val buyLink : String? = null,
    val previewLink : String? = null,
    val rating : Double)
