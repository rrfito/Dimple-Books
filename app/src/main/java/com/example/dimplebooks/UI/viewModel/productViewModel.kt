package com.example.dimplebooks.UI.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimplebooks.data.model.ProductPostRequest
import com.example.dimplebooks.data.model.ProductResponse
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.data.repository.ProductRepository
import com.example.dimplebooks.utils.NetworkUtils
import com.example.dimplebooks.utils.Resource
import kotlinx.coroutines.launch

class productViewModel(private val repository: ProductRepository): ViewModel() {
    private val _data = MutableLiveData<Resource<ProductResponse>>()
    val data : LiveData<Resource<ProductResponse>> = _data

    private val _createStatus = MutableLiveData<Resource<Unit>>()
    val createStatus : LiveData<Resource<Unit>> = _createStatus

    fun getProducts(context: Context, forceRefresh: Boolean = false) {
        if (_data.value == null || forceRefresh) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                viewModelScope.launch {
                    try {
                        //delay(3000)
                        _data.value = Resource.Loading()
                        val response = repository.fetchProduct()
                        if (response.items.isEmpty()) {
                            _data.postValue(Resource.Empty("No users found"))
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

    fun createProduct(context: Context, product: List<ProductPostRequest>) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            viewModelScope.launch {
                try {
                    _createStatus.value = Resource.Loading()

                    val response = repository.createProduct(product)
                    _createStatus.postValue(Resource.Success(Unit))

                    // Refresh data setelah create sukses
                    getProducts(context, forceRefresh = true)
                } catch (e: Exception) {
                    _data.postValue(Resource.Error("Unknown error: ${e.message}"))
                }
            }
        } else {
            _createStatus.postValue(Resource.Error("No internet connection"))
        }
    }
}
