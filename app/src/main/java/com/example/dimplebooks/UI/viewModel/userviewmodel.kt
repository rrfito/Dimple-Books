//package com.example.dimplebooks.UI.viewModel
//
//import com.example.dimplebooks.data.repository.bookRepository
//
//class userviewmodel(private val repository: UserRepository) : viewmodel{
//    fun getUser() = LiveData(dispatchers.IO){
//        try {
//            val response = repository.fetcUsers()
//            emit(response)
//        }catch (e:Exception){
//            emit(null)
//        }
//    }
//}