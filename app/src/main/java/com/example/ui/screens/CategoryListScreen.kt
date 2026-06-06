package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.components.ArticleCard
import com.example.ui.components.NewsFooter
import com.example.ui.navigation.Routes
import com.example.ui.theme.MainRed
import com.example.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(navController: NavController, viewModel: NewsViewModel, categoryName: String) {
    val articles by viewModel.getArticlesByCategory(categoryName).collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName, color = MainRed, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (articles.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text("لا توجد أخبار في هذا القسم")
                    }
                }
            } else {
                items(articles) { article ->
                    ArticleCard(article = article, onClick = {
                        navController.navigate(Routes.ARTICLE.replace("{id}", article.id.toString()))
                    })
                }
            }
            item {
                NewsFooter()
            }
        }
    }
}
