package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.Article
import com.example.data.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: ArticleRepository) : ViewModel() {
    val allArticles: StateFlow<List<Article>> = repository.allArticles
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getArticlesByCategory(category: String): StateFlow<List<Article>> {
        return repository.getArticlesByCategory(category)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun getArticleById(id: Int): StateFlow<Article?> {
        return repository.getArticleById(id)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }

    fun addArticle(title: String, content: String, category: String, imageUrl: String, author: String) {
        viewModelScope.launch {
            repository.insert(Article(title = title, content = content, category = category, imageUrl = imageUrl, author = author))
        }
    }

    fun updateArticle(article: Article) {
        viewModelScope.launch {
            repository.update(article)
        }
    }

    fun deleteArticle(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}

class NewsViewModelFactory(private val repository: ArticleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
