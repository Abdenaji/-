package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.Article
import com.example.ui.theme.MainRed
import com.example.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMSEditArticleScreen(navController: NavController, viewModel: NewsViewModel, articleId: Int) {
    val article by viewModel.getArticleById(articleId).collectAsState(initial = null)
    
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("أخبار زكوطة") }
    var imageUrl by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    
    val categories = listOf("أخبار زكوطة", "مجتمع", "رياضة", "شكايات", "تقارير")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(article) {
        if (article != null && articleId != 0) {
            title = article!!.title
            content = article!!.content
            category = article!!.category
            imageUrl = article!!.imageUrl
            author = article!!.author
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (articleId == 0) "إضافة مقال جديد" else "تعديل المقال") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("عنوان المقال") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("القسم") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                category = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("رابط الصورة") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("الكاتب") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("محتوى المقال") },
                modifier = Modifier.fillMaxWidth().height(200.dp),
                maxLines = 10
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        if (articleId == 0) {
                            viewModel.addArticle(title, content, category, imageUrl, author)
                        } else {
                            viewModel.updateArticle(
                                Article(
                                    id = articleId,
                                    title = title,
                                    content = content,
                                    category = category,
                                    imageUrl = imageUrl,
                                    author = author,
                                    timestamp = article?.timestamp ?: System.currentTimeMillis()
                                )
                            )
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MainRed)
            ) {
                Text("حفظ")
            }
        }
    }
}
