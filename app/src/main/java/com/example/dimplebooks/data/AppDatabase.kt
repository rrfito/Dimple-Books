package com.example.dimplebooks.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.example.dimplebooks.data.dao.bookHistoryDao
import com.example.dimplebooks.data.entity.bookHistoryEntity
@Database(entities = [bookHistoryEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookHistoryDao(): bookHistoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "book_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
