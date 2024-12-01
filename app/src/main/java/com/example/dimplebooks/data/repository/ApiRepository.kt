package com.example.dimplebooks.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dimplebooks.BuildConfig
import com.example.dimplebooks.data.model.BookResponse
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.data.network.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random
class ApiRepository(private val apiService: apiService) {
    val apikey = BuildConfig.API_KEY

    fun fetchBooks(query: String,livedata : MutableLiveData<List<bookModel>>,max : Int)  {
        apiService.fetchBooks(query, apikey, max).enqueue(object : Callback<BookResponse> {
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
                                .replace("http:", "https:").replace("&edge=curl", "").replace("zoom=1", "zoom=0"),
                            description = item.volumeInfo.description ?: "No description",
                            categories = item.volumeInfo.categories?.take(1) ?: listOf("Unknown"),
                            saleability = item.saleInfo.saleability,
                            publishedDate = item.volumeInfo.publishedDate ?: "Unknown",
                            pageCount = item.volumeInfo.pageCount ?: 0,
                            language = (item.volumeInfo.language ?: "Unknown")
                                .replace("en", "English").replace("id", "Indonesia"),
                            buyLink = item.saleInfo.buyLink ?: "No buy link",
                            previewLink = (item.volumeInfo.previewLink ?: "No preview link")
                                .replace("http:", "https:").replace(Regex("pg=PA\\d+"), "printsec=frontcover") + "#v=onepage&q&f=true",
                            rating = (Random.nextDouble(3.0, 5.0) * 10).toInt() / 10.0
                        )
                    } ?: emptyList()
                    livedata.postValue(books)
                } else {
                    Log.e("ApiRepository", "Error: ${response.code()}")
                    livedata.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("ApiRepository", "Error fetching books", t)
                livedata.postValue(emptyList())
            }
        })


    }
}




