package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.navigation.Routes
import com.example.ui.theme.MainRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(navController: NavController, onMenuClick: () -> Unit) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "جريدة زكوطة",
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                    color = MainRed,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "نبض الجماعة... صوت المواطن",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = com.example.ui.theme.BrandGold
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick, modifier = Modifier.background(MainRed, androidx.compose.foundation.shape.CircleShape).size(40.dp)) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate(Routes.ADMIN_LOGIN) }, modifier = Modifier.background(com.example.ui.theme.LightGray, androidx.compose.foundation.shape.CircleShape).size(40.dp)) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Admin Login", tint = com.example.ui.theme.DarkBlack)
            }
            Spacer(modifier = Modifier.width(8.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White
        )
    )
}

@Composable
fun NewsDrawerContent(navController: NavController, closeDrawer: () -> Unit) {
    val categories = listOf("أخبار زكوطة", "مجتمع", "رياضة", "شكايات", "تقارير")
    
    ModalDrawerSheet(
        modifier = Modifier.width(280.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            "القائمة الرئيسية",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MainRed
        )
        HorizontalDivider()
        
        NavigationDrawerItem(
            label = { Text("الرئيسية") },
            selected = false,
            onClick = { navController.navigate(Routes.HOME); closeDrawer() }
        )
        
        categories.forEach { cat ->
            NavigationDrawerItem(
                label = { Text(cat) },
                selected = false,
                onClick = { navController.navigate(Routes.CATEGORY.replace("{catName}", cat)); closeDrawer() }
            )
        }
        
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text("من نحن") },
            selected = false,
            onClick = { navController.navigate(Routes.ABOUT); closeDrawer() }
        )
        NavigationDrawerItem(
            label = { Text("اتصل بنا") },
            selected = false,
            onClick = { navController.navigate(Routes.CONTACT); closeDrawer() }
        )
    }
}

@Composable
fun NewsFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF111111))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("جريدة زكوطة", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("جميع الحقوق محفوظة © 2026", color = Color.Gray, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(32.dp))
    }
}
