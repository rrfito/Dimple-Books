package com.example.dimplebooks.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dimplebooks.UI.viewModel.historyBookViewModel
import com.example.dimplebooks.data.database.dao.bookHistoryDao

class historyBookViewModelFactory(private val bookHistoryDao: bookHistoryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(historyBookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return historyBookViewModel(bookHistoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
