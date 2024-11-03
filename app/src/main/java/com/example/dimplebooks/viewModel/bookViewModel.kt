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

    fun searchBooks(query: String) {
        val call = apiService.bookApi.searchBooks(query, "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU", maxResults = 40)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
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

    fun getNewestBooks() {
        val randomLetter = getRandomLetter()
        val call = apiService.bookApi.newestBooks(randomLetter, "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU", "newest", maxResults = 40)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
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
    }fun setVisibility(imageVisible: Boolean, textVisible: Boolean) {
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
    fun getRandomLetter(): String {
        val chars = ('a'..'z') + ('A'..'Z')
        return chars.random().toString()
    }




}




