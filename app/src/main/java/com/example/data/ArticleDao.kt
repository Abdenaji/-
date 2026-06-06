package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY timestamp DESC")
    fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE category = :category ORDER BY timestamp DESC")
    fun getArticlesByCategory(category: String): Flow<List<Article>>
    
    @Query("SELECT * FROM articles WHERE id = :id")
    fun getArticleById(id: Int): Flow<Article?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Update
    suspend fun updateArticle(article: Article)

    @Query("DELETE FROM articles WHERE id = :id")
    suspend fun deleteArticleById(id: Int)
}
