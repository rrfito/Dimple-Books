package com.example.dimplebooks.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.example.dimplebooks.data.dao.bookHistoryDao
import com.example.dimplebooks.data.dao.userEntitiyDao
import com.example.dimplebooks.data.entity.UserEntity
import com.example.dimplebooks.data.entity.bookHistoryEntity
@Database(entities = [bookHistoryEntity::class, UserEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookHistoryDao(): bookHistoryDao
    abstract fun userEntitiyDao(): userEntitiyDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "book_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
