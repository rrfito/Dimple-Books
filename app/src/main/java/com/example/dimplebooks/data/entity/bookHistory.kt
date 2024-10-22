package com.example.dimplebooks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_history")
data class bookHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val price: Int?,
    val imageUrl: String,
    val description: String,
    val categories: List<String>,
    val saleability: String,
    val publishedDate: String? = null,
    val pageCount: Int? = null,
    val language: String? = null,
    val buyLink : String? = null,
    val openedAt: Long
)

