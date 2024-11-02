package com.example.dimplebooks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userid: Int = 0,
    val username: String,
    val password: String,
    val email: String
) {

}
