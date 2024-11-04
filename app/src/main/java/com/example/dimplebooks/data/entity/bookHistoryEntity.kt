package com.example.dimplebooks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_history")
data class bookHistoryEntity(
    @PrimaryKey val bookId: String,
    val userid: String,
    val title: String,
    val authors: String,
    val publisher: String?,
    val price: Int?,
    val imageUrl: String,
    val description: String,
    val categories: String,
    val saleability: String,
    val publishedDate: String,
    val pageCount: Int?,
    val language: String?,
    val buyLink: String?,
    val timestamp: Long
)



