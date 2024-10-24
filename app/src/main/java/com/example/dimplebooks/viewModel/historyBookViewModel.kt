package com.example.dimplebooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.data.dao.bookHistoryDao
import com.example.dimplebooks.data.entity.bookHistoryEntity
import com.example.dimplebooks.model.bookModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class historyBookViewModel(private val bookHistoryDao: bookHistoryDao): ViewModel() {
    val allHistoryBooks: Flow<List<bookHistoryEntity>> = bookHistoryDao.getAllHistorySortedByDate()
    fun addBookToHistory(bookModel: bookModel) {
        val bookHistoryEntity = bookHistoryEntity(
            bookId = bookModel.id,
            title = bookModel.title,
            authors = bookModel.authors.firstOrNull() ?: "",
            publisher = bookModel.publisher,
            price = bookModel.price,
            imageUrl = bookModel.imageUrl,
            description = bookModel.description,
            categories = bookModel.categories.firstOrNull() ?: "",
            saleability = bookModel.saleability,
            publishedDate = bookModel.publishedDate.toString(),
            pageCount = bookModel.pageCount,
            language = bookModel.language,
            buyLink = bookModel.buyLink,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            bookHistoryDao.insertBook(bookHistoryEntity)
        }
    }fun deleteAllBooks() {
        viewModelScope.launch {
            bookHistoryDao.deleteAllBooks()
        }
    }
}