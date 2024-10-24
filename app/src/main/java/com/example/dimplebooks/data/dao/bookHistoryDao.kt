package com.example.dimplebooks.data.dao

import androidx.room.*
import com.example.dimplebooks.data.entity.bookHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface bookHistoryDao {

    @Query("SELECT * FROM book_history ORDER BY timestamp DESC")
    fun getAllHistorySortedByDate(): Flow<List<bookHistoryEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(bookHistoryEntity: bookHistoryEntity)

    @Query("DELETE FROM book_history") // Replace with your actual table name
    suspend fun deleteAllBooks()

    }




