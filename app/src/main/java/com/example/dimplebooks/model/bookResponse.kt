package com.example.dimplebooks.model

data class bookResponse(
    val items: List<BookItem>
)

data class BookItem(
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val imageLinks: ImageLinks?,
    val description: String?,
    val categories: List<String>?
)

data class SaleInfo(
    val listPrice: Price?,
    val saleability: String
)

data class ImageLinks(
    val thumbnail: String
)

data class Price(
    val amount: Double
)

