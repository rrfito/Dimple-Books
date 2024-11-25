package com.example.dimplebooks.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.data.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.example.dimplebooks.data.model.BookResponse
import kotlin.random.Random

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




    var hasloadData = false
    private fun fetchData(query: String, liveData: MutableLiveData<List<bookModel>>, maxBooks : Int) {
        hasloadData = false
        if (hasloadData) {
            return
        }
        hasloadData = true
        val call = RetrofitInstance.bookApi.searchBooks(query, "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU", maxBooks
        )
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items?.map { item ->
                        bookModel(
                            id = item.id,
                            title = item.volumeInfo.title,
                            subtitle = item.volumeInfo.subtitle ?: "Brave Yourself",
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
                            buyLink = item.saleInfo.buyLink ?: "No buy link",
                            previewLink = (item.volumeInfo.previewLink ?: "No preview link").replace("http:","https:").replace(Regex("pg=PA\\d+"), "printsec=frontcover") + "#v=onepage&q&f=true",
                            rating = (Random.nextDouble(3.0, 5.0) * 10).toInt() / 10.0
                        )
                    } ?: emptyList()
                    liveData.postValue(books)
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", "Error fetching search books", t)
            }
        })
    }
    fun searchBooks(query: String) {
        fetchData(query, _searchBooks, 40)
    }

    fun setVisibility(imageVisible: Boolean, textVisible: Boolean) {
        isImageVisible = imageVisible
        isTextVisible = textVisible
    }
    fun CategoriesBooks(category: String) {
        fetchData("subject:$category", _categoriesBooks,40)
    }
    fun GetdailyGetBooks() {
        val randomLetter = ('A'..'Z').random()
        fetchData("$randomLetter", _dailyBooks,6)
    }
    fun GetBusinessBooks() {
        fetchData("Subject:Business", _businessBooks,40)
    }
    fun GetEntertainmentBooks() {
        fetchData("Subject:entertainment", _entertainmentBooks,40)
    }
    fun GetBannerBooks() {
        fetchData("quantum", _bannerBooks,6)
    }
    fun GetRecommendBooks() {
        fetchData("school", _recommBooks,40)
    }
    fun getNewestBooks() {
        fetchData("orderBy=newest", _newestBooks,40)
    }
    fun refreshAPI(){
        hasloadData = false
    }
}