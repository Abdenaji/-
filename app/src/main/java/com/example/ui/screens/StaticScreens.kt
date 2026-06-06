package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.components.NewsFooter
import com.example.ui.theme.MainRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("من نحن", color = MainRed, fontWeight = FontWeight.Bold) },
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
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.padding(16.dp).weight(1f)) {
                Text("جريدة زكوطة", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MainRed)
                Spacer(modifier = Modifier.height(16.dp))
                Text("نبض الجماعة... صوت المواطن", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "نحن جريدة محلية مستقلة تهتم بأخبار ومشاكل ساكنة زكوطة. نسعى لنقل الحقيقة كما هي وتسليط الضوء على أبرز الأحداث المجتمعية والرياضية والثقافية في منطقتنا.",
                    lineHeight = 28.sp
                )
            }
            NewsFooter()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("اتصل بنا", color = MainRed, fontWeight = FontWeight.Bold) },
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
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.padding(16.dp).weight(1f)) {
                Text("تواصل معنا", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MainRed)
                Spacer(modifier = Modifier.height(16.dp))
                Text("البريد الإلكتروني: contact@zakouta.news", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("الهاتف: +212 5XX XX XX XX", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("العنوان: شارع محمد الخامس، زكوطة، المغرب", fontSize = 16.sp)
            }
            NewsFooter()
        }
    }
}
