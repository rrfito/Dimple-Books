package com.example.dimplebooks.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.dimplebooks.UI.viewModel.ReviewViewModel

import com.example.dimplebooks.data.repository.reviewRepository

class ViewModelFactoryReview(private val repository: reviewRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            return ReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
