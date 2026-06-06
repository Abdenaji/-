package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.components.ArticleCard
import com.example.ui.components.NewsDrawerContent
import com.example.ui.components.NewsFooter
import com.example.ui.components.NewsTopAppBar
import com.example.ui.navigation.Routes
import com.example.ui.theme.MainRed
import com.example.ui.viewmodels.NewsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: NewsViewModel) {
    val articles by viewModel.allArticles.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { 
            NewsDrawerContent(navController = navController, closeDrawer = { scope.launch { drawerState.close() } }) 
        }
    ) {
        Scaffold(
            topBar = {
                NewsTopAppBar(navController) { scope.launch { drawerState.open() } }
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = { Text("🏠", fontSize = 20.sp) }, // Fallback to emoji since we don't have Material Symbols
                        label = { Text("الرئيسية", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = MainRed, selectedTextColor = MainRed, indicatorColor = Color.White)
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Routes.CATEGORY.replace("{catName}", "مجتمع")) },
                        icon = { Text("📄", fontSize = 20.sp) },
                        label = { Text("المجتمع", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Routes.CATEGORY.replace("{catName}", "رياضة")) },
                        icon = { Text("🏆", fontSize = 20.sp) },
                        label = { Text("الرياضة", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Routes.CATEGORY.replace("{catName}", "شكايات")) },
                        icon = { Text("📢", fontSize = 20.sp) },
                        label = { Text("شكايات", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Routes.ABOUT) },
                        icon = { Text("⚙️", fontSize = 20.sp) },
                        label = { Text("الإعدادات", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(com.example.ui.theme.LightGray)
            ) {
                item {
                    // Breaking News Ticker
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .background(MainRed),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(com.example.ui.theme.DarkRed)
                                .padding(horizontal = 12.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text("عاجل", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Text(
                            text = "رسمياً: انطلاق أشغال تأهيل الملعب البلدي بزكوطة الأسبوع المقبل...",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }
                }

                if (articles.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text("لا توجد أخبار حالياً")
                        }
                    }
                } else {
                    // Hero Article
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        com.example.ui.components.HeroArticleCard(article = articles.first(), onClick = {
                            navController.navigate(Routes.ARTICLE.replace("{id}", articles.first().id.toString()))
                        })
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    
                    // 2-Column Grid for remaining articles
                    val remainingArticles = articles.drop(1)
                    val chunkedArticles = remainingArticles.chunked(2)
                    
                    items(chunkedArticles) { rowArticles ->
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                            rowArticles.forEach { article ->
                                Box(modifier = Modifier.weight(1f)) {
                                    ArticleCard(article = article, onClick = {
                                        navController.navigate(Routes.ARTICLE.replace("{id}", article.id.toString()))
                                    })
                                }
                            }
                            if (rowArticles.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                
                // Citizen Complaints
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .background(Color(0xFFFDE8E8), androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                    ) {
                        // Right border simulation
                        Box(modifier = Modifier.fillMaxHeight().width(4.dp).align(androidx.compose.ui.Alignment.CenterEnd).background(MainRed))
                        Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                Text("شكايات المواطنين", color = MainRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("+ إرسال شكاية", color = com.example.ui.theme.DarkBlack, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("مشكل الإنارة العمومية بحي النهضة لا يزال قائماً...", color = com.example.ui.theme.DarkGray, fontSize = 11.sp, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                        }
                    }
                }

                // Stats Widget
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.LightGray)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                                Text("١٥+", color = MainRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("خبر اليوم", fontSize = 8.sp, color = Color.Gray)
                            }
                            Spacer(modifier = Modifier.width(1.dp).height(32.dp).background(com.example.ui.theme.LightGray))
                            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                                Text("٤٥٠", color = MainRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("شكاية معالجة", fontSize = 8.sp, color = Color.Gray)
                            }
                            Spacer(modifier = Modifier.width(1.dp).height(32.dp).background(com.example.ui.theme.LightGray))
                            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                                Text("١٢٠٠", color = MainRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("زائر الآن", fontSize = 8.sp, color = Color.Gray)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
                
                item {
                    NewsFooter()
                }
            }
        }
    }
}
