package com.example.dimplebooks.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimplebooks.data.Firebase.FirebaseRepository
import com.example.dimplebooks.data.model.userModel
import com.example.dimplebooks.utils.Resource
import kotlinx.coroutines.launch

class FirebaseViewModel(private val repository: FirebaseRepository) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<userModel>>()
    val loginState: LiveData<Resource<userModel>> get() = _loginState

    private val _registerState = MutableLiveData<Resource<Boolean>>()
    val registerState: LiveData<Resource<Boolean>> get() = _registerState

    private val _updateState = MutableLiveData<Resource<Boolean>>()
    val updateState: LiveData<Resource<Boolean>> get() = _updateState

    fun login(email: String, password: String) {
        _loginState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val user = repository.login(email, password)
                if (user != null) {
                    _loginState.value = Resource.Success(user)
                } else {
                    _loginState.value = Resource.Error("Login gagal. Periksa kembali kredensial Anda.")
                }
            } catch (e: Exception) {
                _loginState.value = Resource.Error(e.message ?: "Terjadi kesalahan saat login.")
            }
        }
    }

    fun register(email: String, password: String) {
        _registerState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val result = repository.register(email, password)
                if (result) {
                    _registerState.value = Resource.Success(result)
                } else {
                    _registerState.value = Resource.Error("Gagal membuat akun.")
                }
            } catch (e: Exception) {
                _registerState.value = Resource.Error(e.message ?: "Terjadi kesalahan.")
            }
        }
    }

    fun updatePhoto(photoUrl: String) {
        _updateState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val result = repository.updatePhoto(photoUrl)
                _updateState.value = Resource.Success(result)
            } catch (e: Exception) {
                _updateState.value = Resource.Error(e.message ?: "Terjadi kesalahan saat mengupdate foto.")
            }
        }
    }

    fun updateUsername(username: String) {
        _updateState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val result = repository.updateUsername(username)
                _updateState.value = Resource.Success(result)
            } catch (e: Exception) {
                _updateState.value = Resource.Error(e.message ?: "Terjadi kesalahan saat mengupdate username.")
            }
        }
    }

    fun updateEmail(email: String) {
        _updateState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val result = repository.updateEmail(email)
                _updateState.value = Resource.Success(result)
            } catch (e: Exception) {
                _updateState.value = Resource.Error(e.message ?: "Terjadi kesalahan saat mengupdate email.")
            }
        }
    }

    fun getCurrentUser() = repository.getCurrentUser()
    fun logout() = repository.Logout()
}