package com.example.dimplebooks.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.dimplebooks.data.entity.UserEntity



@Dao
interface userEntitiyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(User : UserEntity) : Long

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM USER")
    suspend fun getUser(): List<UserEntity>

}