package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.ArticleDetailScreen
import com.example.ui.screens.CMSDashboardScreen
import com.example.ui.screens.CMSEditArticleScreen
import com.example.ui.screens.CategoryListScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.AboutScreen
import com.example.ui.viewmodels.NewsViewModel

object Routes {
    const val HOME = "home"
    const val CATEGORY = "category/{catName}"
    const val ARTICLE = "article/{id}"
    const val ADMIN_LOGIN = "admin_login"
    const val CMS_DASHBOARD = "cms_dashboard"
    const val CMS_EDIT = "cms_edit/{id}" // id=0 for new
    const val CONTACT = "contact"
    const val ABOUT = "about"
}

@Composable
fun NewsNavGraph(viewModel: NewsViewModel) {
    val navController = rememberNavController()

    // Force RTL layout direction for the entire app content to support Arabic
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        NavHost(navController = navController, startDestination = Routes.HOME) {
            composable(Routes.HOME) {
                HomeScreen(navController, viewModel)
            }
            composable(
                route = Routes.CATEGORY,
                arguments = listOf(navArgument("catName") { type = NavType.StringType })
            ) { backStackEntry ->
                val catName = backStackEntry.arguments?.getString("catName") ?: ""
                CategoryListScreen(navController, viewModel, catName)
            }
            composable(
                route = Routes.ARTICLE,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                ArticleDetailScreen(navController, viewModel, id)
            }
            composable(Routes.ADMIN_LOGIN) {
                LoginScreen(navController)
            }
            composable(Routes.CMS_DASHBOARD) {
                CMSDashboardScreen(navController, viewModel)
            }
            composable(
                route = Routes.CMS_EDIT,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                CMSEditArticleScreen(navController, viewModel, id)
            }
            composable(Routes.CONTACT) {
                ContactScreen(navController)
            }
            composable(Routes.ABOUT) {
                AboutScreen(navController)
            }
        }
    }
}
