package com.example.dimplebooks.UI.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
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

    private val _checkpassword = MutableLiveData<Resource<Boolean>>()
    val checkpassword: LiveData<Resource<Boolean>> get() = _checkpassword

    private val _googleLoginState = MutableLiveData<Resource<userModel>>()
    val googleLoginState: LiveData<Resource<userModel>> get() = _googleLoginState

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



    fun updateUsernameAndEmail(username: String, email: String) {
        _updateState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = repository.updateUsernameAndEmail(username, email)
                if (result) {
                    _updateState.value = Resource.Success(true)
                } else {
                    _updateState.value = Resource.Error("Please verify your email.")
                }
            } catch (e: Exception) {
                _updateState.value = Resource.Error(e.message ?: "Terjadi kesalahan saat mengupdate.")
            }
        }
    }
    fun getCurrentUser() = repository.getCurrentUser()
     fun logout(context : Context){
        repository.Logout(context)
    }
    fun checkpassword(email: String){
        _checkpassword.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = repository.checkpassword(email)
                if (result) {
                    _checkpassword.value = Resource.Success(true)
                } else {
                    _checkpassword.value = Resource.Error("Please verify your email.")
                }
            } catch (e: Exception) {
                _checkpassword.value = Resource.Error(e.message ?: "Terjadi kesalahan saat mengupdate.")
            }
        }
    }

    fun getGoogleSignInClient(context: Context) = repository.getGoogleSignInClient(context)


    fun loginWithGoogle(data: Intent?) {
        _googleLoginState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val user = repository.loginWithGoogle(data)
                if (user != null) {
                    _googleLoginState.value = Resource.Success(user)
                } else {
                    _googleLoginState.value = Resource.Error("Login Google gagal.")
                }
            } catch (e: Exception) {
                _googleLoginState.value = Resource.Error(e.message ?: "Terjadi kesalahan saat login Google.")
            }
        }
    }

}