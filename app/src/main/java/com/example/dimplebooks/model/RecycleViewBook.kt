package com.example.dimplebooks.model

data class RecycleViewBook (
    val nameBook : String ,
    val author : String ,
    val rating : Double ,
    val image : Int
)
data class RecycleViewBookHistory (
    val nameBook : String ,
    val author : String ,
    val publisher : String ,
    val genre : String ,
    val page : Int ,
    val image : Int
)

data class BookResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<Book> )


data class Book(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo,
    val accessInfo: AccessInfo,
    val searchInfo: SearchInfo?,
    val price: Price
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val readingModes: ReadingModes,
    val pageCount: Int?,
    val printType: String?,
    val categories: List<String>?,
    val maturityRating: String?,
    val allowAnonLogging: Boolean,
    val contentVersion: String?,
    val panelizationSummary: PanelizationSummary?,
    val imageLinks: ImageLinks?,
    val language: String?,
    val previewLink: String?,
    val infoLink: String?,
    val canonicalVolumeLink: String?
)

data class ReadingModes(
    val text: Boolean,
    val image: Boolean
)

data class PanelizationSummary(
    val containsEpubBubbles: Boolean,
    val containsImageBubbles: Boolean
)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)

data class SaleInfo(
    val country: String,
    val saleability: String,
    val isEbook: Boolean,
    val listPrice: Price?,
    val retailPrice: Price?,
    val buyLink: String?,
    val offers: List<Offer>?
)

data class Price(
    val amount: Double?,
    val currencyCode: String?
)

data class Offer(
    val finskyOfferType: Int,
    val listPrice: PriceInMicros,
    val retailPrice: PriceInMicros
)

data class PriceInMicros(
    val amountInMicros: Long,
    val currencyCode: String
)

data class AccessInfo(
    val country: String,
    val viewability: String,
    val embeddable: Boolean,
    val publicDomain: Boolean,
    val textToSpeechPermission: String,
    val epub: Epub,
    val pdf: Pdf,
    val webReaderLink: String,
    val accessViewStatus: String,
    val quoteSharingAllowed: Boolean
)

data class Epub(
    val isAvailable: Boolean
)

data class Pdf(
    val isAvailable: Boolean
)

data class SearchInfo(
    val textSnippet: String?
)
