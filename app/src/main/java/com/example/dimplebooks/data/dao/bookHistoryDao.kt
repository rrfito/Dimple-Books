package com.example.dimplebooks.data.dao

import androidx.room.*
import com.example.dimplebooks.data.entity.bookHistory

@Dao
interface bookHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookHistory(bookHistory: bookHistory)

    @Query("SELECT * FROM book_history ORDER BY openedAt DESC")
    suspend fun getAllHistory(): List<bookHistory>

    @Query("DELETE FROM book_history")
    suspend fun clearHistory()
}
