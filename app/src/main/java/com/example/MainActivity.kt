package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.ArticleRepository
import com.example.ui.navigation.NewsNavGraph
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodels.NewsViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "news-database"
    ).build()
    val repository = ArticleRepository(db.articleDao())
    val factory = NewsViewModelFactory(repository)

    setContent {
      MyApplicationTheme {
        val viewModel = viewModel<com.example.ui.viewmodels.NewsViewModel>(factory = factory)
        NewsNavGraph(viewModel = viewModel)
      }
    }
  }
}
