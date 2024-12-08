package com.example.dimplebooks.UI.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimplebooks.data.model.Review

import com.example.dimplebooks.data.model.reviewResponse
import com.example.dimplebooks.data.repository.reviewRepository
import com.example.dimplebooks.utils.NetworkUtils
import com.example.dimplebooks.utils.Resource
import kotlinx.coroutines.launch


class ReviewViewModel(private val repository: reviewRepository): ViewModel() {
    private val _data = MutableLiveData<Resource<reviewResponse>>()
    val data: LiveData<Resource<reviewResponse>> = _data

    private val _createReview = MutableLiveData<Resource<Unit>>()
    val createReview: LiveData<Resource<Unit>> = _createReview

    private val _deleteReview = MutableLiveData<Resource<Unit>>()
    val deleteReview: LiveData<Resource<Unit>> = _deleteReview
    fun getReviews(context: Context, forceRefresh: Boolean = false) {
        if (_data.value == null || forceRefresh) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                viewModelScope.launch {
                    try {
                        //delay(3000)
                        _data.value = Resource.Loading()
                        val response = repository.fetchReview()
                        if (response.items.isEmpty()) {
                            _data.postValue(Resource.Empty("No Reviews found"))
                        } else {
                            _data.postValue(Resource.Success(response))
                        }
                    } catch (e: Exception) {
                        _data.postValue(Resource.Error("Unknown error: ${e.message}"))
                    }
                }
            } else {
                _data.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun createReviews(context: Context, Review: List<Review>) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            viewModelScope.launch {
                try {
                    _data.value = Resource.Loading()
                    val response = repository.creatReview(Review)
                    _createReview.postValue(Resource.Success(Unit))

                    Log.d("responsesss", "createReview : ${Review.toString()}")

                    getReviews(context, forceRefresh = true)
                } catch (e: Exception) {
                    _createReview.postValue(Resource.Error("Unknown error: ${e.message}"))
                    Log.d("errrorr", "errrorr : ${e.message}")
                }
            }
        } else {
            _createReview.postValue(Resource.Error("No internet connection"))

        }
    }

    fun deleteReviews(context: Context, uuid: String) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            viewModelScope.launch {
                try {
                    _deleteReview.postValue(Resource.Loading())
                    repository.deleteReview(uuid)
                    _deleteReview.postValue(Resource.Success(Unit))
                    getReviews(context, forceRefresh = true)
                } catch (e: Exception) {
                    _deleteReview.postValue(Resource.Error("Unknown error: ${e.message}"))
                }
            }
        } else {
            _deleteReview.postValue(Resource.Error("No internet connection"))
        }
    }
}



