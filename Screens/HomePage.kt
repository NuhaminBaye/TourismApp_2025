package com.example.tourismappfinal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourismappfinal.ViewModel.MainViewModel
import com.example.tourismappfinal.ViewModel.AuthViewModel
import com.example.tourismappfinal.ViewModel.ExploreViewModel
import com.example.tourismappfinal.model.NavItem
import com.example.tourismappfinal.navigation.Screen

@Composable
fun HomePage(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    exploreViewModel: ExploreViewModel = viewModel()
) {
    val selectedIndex by mainViewModel.selectedIndex.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    // If user is not logged in, show login screen without bottom navigation
    if (currentUser == null) {
        LoginScreen(
            authViewModel = authViewModel,
            onNavigateToRegister = { navController.navigate(Screen.Register.route) },
            onLoginSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            },
            onNavigateToHome = { mainViewModel.onNavigationItemSelected(0) },
            onNavigateToExplore = { mainViewModel.onNavigationItemSelected(1) },
            onNavigateToFavorites = { mainViewModel.onNavigationItemSelected(2) }
        )
        return
    }

    val navItems = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Explore", Icons.Default.Search),
        NavItem("Wishlist", Icons.Default.FavoriteBorder),
        NavItem("Profile", Icons.Default.Person)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { mainViewModel.onNavigationItemSelected(index) },
                        icon = { Icon(navItem.icon, contentDescription = navItem.label) },
                        label = { Text(navItem.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedIndex) {
                0 -> HomeScreen(
                    onNavigateToExplore = { mainViewModel.onNavigationItemSelected(1) },
                    onNavigateToFavorites = { mainViewModel.onNavigationItemSelected(2) },
                    onNavigateToLogin = { 
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
                1 -> ExploreScreen(
                    exploreViewModel = exploreViewModel,
                    authViewModel = authViewModel,
                    onNavigateToAdd = { navController.navigate(Screen.AddExploreItem.route) },
                    onNavigateToEdit = { id -> navController.navigate(Screen.EditExploreItem.createRoute(id)) },
                    onNavigateToHome = { mainViewModel.onNavigationItemSelected(0) },
                    onNavigateToFavorites = { mainViewModel.onNavigationItemSelected(2) },
                    onNavigateToLogin = { 
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Explore.route) { inclusive = true }
                        }
                    }
                )
                2 -> FavoritesScreen(
                    exploreViewModel = exploreViewModel,
                    onNavigateToEdit = { id -> navController.navigate(Screen.EditExploreItem.createRoute(id)) },
                    onNavigateToHome = { mainViewModel.onNavigationItemSelected(0) },
                    onNavigateToExplore = { mainViewModel.onNavigationItemSelected(1) },
                    onNavigateToLogin = { 
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Favorites.route) { inclusive = true }
                        }
                    }
                )
                3 -> {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            }
        }
    }
} 
