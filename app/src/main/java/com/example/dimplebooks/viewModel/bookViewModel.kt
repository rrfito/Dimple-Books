package com.example.dimplebooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dimplebooks.model.bookModel
import com.example.dimplebooks.retrofit.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.example.dimplebooks.model.BookResponse

class BookViewModel : ViewModel() {
    var isImageVisible: Boolean = true
    var isTextVisible: Boolean = true

    private val _searchBooks = MutableLiveData<List<bookModel>>()
    val searchBooks: LiveData<List<bookModel>> get() = _searchBooks
    private val _newestBooks = MutableLiveData<List<bookModel>>()
    val newestBooks: LiveData<List<bookModel>> get() = _newestBooks
    private val _categoriesBooks = MutableLiveData<List<bookModel>>()
    val categoriesBooks: LiveData<List<bookModel>> get() = _categoriesBooks
    private val _dailyBooks = MutableLiveData<List<bookModel>>()
    val dailyBooks: LiveData<List<bookModel>> get() = _dailyBooks
    private val _businessBooks = MutableLiveData<List<bookModel>>()
    val businessBooks: LiveData<List<bookModel>> get() = _businessBooks
    private val _entertainmentBooks = MutableLiveData<List<bookModel>>()
    val entertainmentBooks: LiveData<List<bookModel>> get() = _entertainmentBooks
    private val _bannerBooks = MutableLiveData<List<bookModel>>()
    val bannerBooks: LiveData<List<bookModel>> get() = _bannerBooks
    private val _recommBooks = MutableLiveData<List<bookModel>>()
    val recommBooks: LiveData<List<bookModel>> get() = _recommBooks


    fun searchBooks(query: String) {

        val call = apiService.bookApi.searchBooks(query, "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU", maxResults = 40)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
                            subtitle = item.volumeInfo.subtitle,
                            authors = item.volumeInfo.authors?.take(1) ?: listOf("Unknown Author"),
                            publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                            price = item.saleInfo.listPrice?.amount?.toInt(),
                            imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "")
                                .replace("http:", "https:")
                                .replace("&edge=curl", "")
                                .replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English")
                                .replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link"
                        )
                    } ?: emptyList()

                    _searchBooks.postValue(books)
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching search books", t)
            }
        })
    }
    private var hasLoadedNewestBooks = false
    fun getNewestBooks() {
        if (hasLoadedNewestBooks) return
        hasLoadedNewestBooks = true
        val randomLetter = getRandomLetter()
        val call = apiService.bookApi.newestBooks(randomLetter, "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU", "newest", maxResults = 40)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
                            subtitle = item.volumeInfo.subtitle,
                            authors = item.volumeInfo.authors?.take(1) ?: listOf("Unknown Author"),
                            publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                            price = item.saleInfo.listPrice?.amount?.toInt(),
                            imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "")
                                .replace("http:", "https:")
                                .replace("&edge=curl", "")
                                .replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English")
                                .replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link"
                        )
                    } ?: emptyList()

                    _newestBooks.postValue(books)
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching newest books", t)
            }
        })
    }


    fun setVisibility(imageVisible: Boolean, textVisible: Boolean) {
        isImageVisible = imageVisible
        isTextVisible = textVisible
    }
    fun CategoriesBooks(query: String) {
        val call = apiService.bookApi.searchBooks("Subject: $query","AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU", maxResults = 40)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
                            subtitle = item.volumeInfo.subtitle,
                            authors = item.volumeInfo.authors?.take(1) ?: listOf("Unknown Author"),
                            publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                            price = item.saleInfo.listPrice?.amount?.toInt(),
                            imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "")
                                .replace("http:", "https:")
                                .replace("&edge=curl", "")
                                .replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English")
                                .replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link"
                        )
                    } ?: emptyList()

                    _categoriesBooks.postValue(books)
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching search books", t)
            }
        })
    }
    private var hasLoadedDailyBooks = false
    fun GetdailyGetBooks() {
        if(hasLoadedDailyBooks) return
        hasLoadedDailyBooks = true
        val randomLetter = getRandomLetter()
        val call = apiService.bookApi.searchBooks(
            query = randomLetter,
            apiKey = "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU",
            maxResults = 6
        )

        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
                            subtitle = item.volumeInfo.subtitle,
                            authors = item.volumeInfo.authors?.take(1) ?: listOf("Unknown Author"),
                            publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                            price = item.saleInfo.listPrice?.amount?.toInt(),
                            imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "")
                                .replace("http:", "https:")
                                .replace("&edge=curl", "")
                                .replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English")
                                .replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link"
                        )
                    } ?: emptyList()

                    _dailyBooks.postValue(books)
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching daily books", t)
            }
        })
    }
    private var hasLoadBannerBooks = false
    fun GetBannerBooks() {
        if(hasLoadBannerBooks) return
        hasLoadBannerBooks = true
        val call = apiService.bookApi.searchBooks(
            query = "quantum",
            apiKey = "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU",
            maxResults = 6
        )
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful){
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,

                            subtitle = item.volumeInfo.subtitle,
                            authors = item.volumeInfo.authors?.take(1) ?: listOf("Unknown Author"),
                            publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                            price = item.saleInfo.listPrice?.amount?.toInt(),
                            imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "")
                                .replace("http:", "https:")
                                .replace("&edge=curl", "")
                                .replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English")
                                .replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link"
                        )
                    } ?: emptyList()

                    _bannerBooks.postValue(books)

                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching daily books", t)
            }


        })
        

        
    }
    private var hasLoadedBusinesBooks = false
    fun GetBusinessBooks() {
        if(hasLoadedBusinesBooks) return
        hasLoadedBusinesBooks = true
        val call = apiService.bookApi.searchBooks(
            query = "business",
            apiKey = "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU",
            maxResults = 40
        )
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
                            subtitle = item.volumeInfo.subtitle,
                            authors = item.volumeInfo.authors?.take(1) ?: listOf("Unknown Author"),
                            publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                            price = item.saleInfo.listPrice?.amount?.toInt(),
                            imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "")
                                .replace("http:", "https:")
                                .replace("&edge=curl", "")
                                .replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English")
                                .replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link"
                        )
                    } ?: emptyList()

                    _businessBooks.postValue(books)
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching daily books", t)
            }
        })
    }
    private var hasLoadedEntertainmentBooks = false
    fun GetEntertainmentBooks() {
        if(hasLoadedEntertainmentBooks) return
        hasLoadedEntertainmentBooks = true

        val call = apiService.bookApi.searchBooks(
            query = "Subject: entertainment",
            apiKey = "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU",
            maxResults = 40
        )
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
                            subtitle = item.volumeInfo.subtitle,
                            authors = item.volumeInfo.authors?.take(1) ?: listOf("Unknown Author"),
                            publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                            price = item.saleInfo.listPrice?.amount?.toInt(),
                            imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "")
                                .replace("http:", "https:")
                                .replace("&edge=curl", "")
                                .replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English")
                                .replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link"
                        )
                    } ?: emptyList()

                    _entertainmentBooks.postValue(books)
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching daily books", t)
            }
        })
    }
    private var hasLoadRecommend = false
    fun GetRecommendBooks() {
        if(hasLoadRecommend) return
        hasLoadRecommend = true
        val randomLetter = getRandomLetter()
        val call = apiService.bookApi.searchBooks(
            query = randomLetter,
            apiKey = "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU",
            maxResults = 40
        )
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
                            subtitle = item.volumeInfo.subtitle,
                            authors = item.volumeInfo.authors?.take(1) ?: listOf("Unknown Author"),
                            publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                            price = item.saleInfo.listPrice?.amount?.toInt(),
                            imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "")
                                .replace("http:", "https:")
                                .replace("&edge=curl", "")
                                .replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English")
                                .replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link"
                        )
                    } ?: emptyList()

                    _recommBooks.postValue(books)
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching daily books", t)
            }
        })
    }


    fun getRandomLetter(): String {
        val chars = ('a'..'z') + ('A'..'Z')
        return chars.random().toString()
    }
    fun refreshAPI(){
       hasLoadedNewestBooks = false
        hasLoadedDailyBooks = false
        hasLoadBannerBooks = false
        hasLoadedBusinesBooks = false
        hasLoadedEntertainmentBooks = false
        hasLoadRecommend = false
    }




}




