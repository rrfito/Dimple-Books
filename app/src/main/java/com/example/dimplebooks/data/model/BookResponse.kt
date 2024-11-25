package com.example.dimplebooks.data.model

data class BookResponse(
    val items: List<BookItem>
)
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
)

data class VolumeInfo(
    val title: String,
    val subtitle: String?,
    val authors: List<String>?,
    val publisher: String?,
    val imageLinks: ImageLinks?,
    val description: String?,
    val publishedDate: String?,
    val pageCount: Int?,
    val language: String?,
    val categories: List<String>?,
    val previewLink : String?,
    val rating : String?
)
data class SaleInfo(
    val listPrice: Price?,
    val saleability: String,
    val buyLink : String?
)

data class ImageLinks(
    val thumbnail: String
)

data class Price(
    val amount: Double
)