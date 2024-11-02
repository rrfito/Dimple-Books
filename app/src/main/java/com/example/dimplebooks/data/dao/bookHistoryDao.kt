package com.example.dimplebooks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dimplebooks.data.entity.bookHistoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface bookHistoryDao {

    @Query("SELECT * FROM book_history ORDER BY timestamp DESC")
    fun getAllHistorySortedByDate(): Flow<List<bookHistoryEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(bookHistoryEntity: bookHistoryEntity)

    @Query("DELETE FROM book_history")
    suspend fun deleteAllBooks()

    @Query("SELECT COUNT(*) FROM book_history")
    fun getHistorybookCount(): LiveData<Int?>?


    }




