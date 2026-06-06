package com.example.data

import kotlinx.coroutines.flow.Flow

class ArticleRepository(private val articleDao: ArticleDao) {
    val allArticles: Flow<List<Article>> = articleDao.getAllArticles()

    fun getArticlesByCategory(category: String): Flow<List<Article>> = articleDao.getArticlesByCategory(category)
    
    fun getArticleById(id: Int): Flow<Article?> = articleDao.getArticleById(id)

    suspend fun insert(article: Article) = articleDao.insertArticle(article)
    
    suspend fun update(article: Article) = articleDao.updateArticle(article)

    suspend fun deleteById(id: Int) = articleDao.deleteArticleById(id)
}
