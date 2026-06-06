package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.navigation.Routes
import com.example.ui.theme.MainRed
import com.example.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تسجيل الدخول للإدارة") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("لوحة التحكم", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MainRed)
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("اسم المستخدم") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("كلمة المرور") },
                modifier = Modifier.fillMaxWidth()
            )
            if (error) {
                Text("بيانات الدخول غير صحيحة", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (username == "admin" && password == "admin") { // Simple mock login
                        navController.navigate(Routes.CMS_DASHBOARD) {
                            popUpTo(Routes.ADMIN_LOGIN) { inclusive = true }
                        }
                    } else {
                        error = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MainRed)
            ) {
                Text("دخول")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMSDashboardScreen(navController: NavController, viewModel: NewsViewModel) {
    val articles by viewModel.allArticles.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إدارة المقالات") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.HOME) { popUpTo(0) } }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Back home")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.CMS_EDIT.replace("{id}", "0")) },
                containerColor = MainRed
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Article", tint = androidx.compose.ui.graphics.Color.White)
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            items(articles) { article ->
                ListItem(
                    headlineContent = { Text(article.title) },
                    supportingContent = { Text(article.category) },
                    trailingContent = {
                        Row {
                            IconButton(onClick = { navController.navigate(Routes.CMS_EDIT.replace("{id}", article.id.toString())) }) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(onClick = { viewModel.deleteArticle(article.id) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = MainRed)
                            }
                        }
                    },
                    modifier = Modifier.clickable { navController.navigate(Routes.ARTICLE.replace("{id}", article.id.toString())) }
                )
                HorizontalDivider()
            }
        }
    }
}
