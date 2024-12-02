package com.example.dimplebooks.UI.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimplebooks.data.model.bookModel

import com.example.dimplebooks.data.repository.ApiRepository
import com.example.dimplebooks.utils.NetworkUtils
import com.example.dimplebooks.utils.Resource
import kotlinx.coroutines.launch

class BookViewModel(private val repository: ApiRepository) : ViewModel() {
    var isImageVisible: Boolean = true
    var isTextVisible: Boolean = true

//    private val _data = MutableLiveData<Resource<List<bookModel>>>()
//     val data : LiveData<Resource<List<bookModel>>> = _data
//
//    fun getNewest(context: Context,forceRefresh : Boolean = false){
//        if (_data.value == null || forceRefresh){
//            if (NetworkUtils.isNetworkAvailable(context)){
//                viewModelScope.launch {
//                    try {
//                        _data.value = Resource.Loading()
//                        val response = repository.fetchBooks()
//                        if (response.isEmpty()){
//                            _data.postValue(Resource.Empty("No data found"))
//                        }else{
//                            _data.postValue(Resource.Success(response))
//                        }
//                    }catch (e: Exception){
//                        _data.postValue(Resource.Error("UNKNOWN ERROR : ${e.message}"))
//                    }
//                }
//
//            }else{
//                _data.postValue(Resource.Error("No internet connection"))
//            }
//        }
//    }



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
       repository.fetchBooks(query,_searchBooks,40)
    }

    fun setVisibility(imageVisible: Boolean, textVisible: Boolean) {
        isImageVisible = imageVisible
        isTextVisible = textVisible
    }
    fun CategoriesBooks(category: String) {
        repository.fetchBooks("subject:$category",_categoriesBooks,40)
    }
    fun GetdailyGetBooks() {
        val randomLetter = ('A'..'Z').random()
        repository.fetchBooks(randomLetter.toString(),_dailyBooks,6)
    }
    fun GetBusinessBooks() {
        repository.fetchBooks("subject:business",_businessBooks,40)
    }
    fun GetEntertainmentBooks() {
        repository.fetchBooks("entertainment",_entertainmentBooks,40)

    }
    fun GetBannerBooks() {
        repository.fetchBooks("quantum",_bannerBooks,6)
    }
    fun GetRecommendBooks() {
        repository.fetchBooks("school",_recommBooks,40)
    }
    fun getNewestBooks() {
        repository.fetchBooks("orderBy=newest",_newestBooks,40)
    }

}