package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val category: String,
    val imageUrl: String,
    val author: String,
    val timestamp: Long = System.currentTimeMillis()
)
