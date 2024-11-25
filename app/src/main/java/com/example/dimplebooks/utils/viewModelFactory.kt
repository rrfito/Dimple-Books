package com.example.dimplebooks.utils

//class ViewModelFactory<T : ViewModel>(
//    private val viewModelClass: Class<T>,
//    private val creator: () -> T
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(viewModelClass)) {
//            @Suppress("UNCHECKED_CAST")
//            return creator() as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}