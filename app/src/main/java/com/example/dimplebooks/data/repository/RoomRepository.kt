package com.example.dimplebooks.data.repository

import androidx.lifecycle.LiveData
import com.example.dimplebooks.data.database.dao.bookHistoryDao
import com.example.dimplebooks.data.database.entity.bookHistoryEntity
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val bookHistoryDao: bookHistoryDao) {
    fun getAllHistorySortedByDate(userid: String): Flow<List<bookHistoryEntity>> {
        return bookHistoryDao.getAllHistorySortedByDate(userid)
    }


    suspend fun insertBook(bookHistoryEntity: bookHistoryEntity) {
        bookHistoryDao.insertBook(bookHistoryEntity)
    }

    fun getHistorybookCount(userid: String): LiveData<Int?>? {
        return bookHistoryDao.getHistorybookCount(userid)
    }
    suspend fun deleteAllBooks() {
        bookHistoryDao.deleteAllBooks()
    }
}