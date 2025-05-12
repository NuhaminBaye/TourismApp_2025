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
import com.example.tourismappfinal.model.NavItem

@Composable
fun HomePage(navController: NavController, mainViewModel: MainViewModel = viewModel()) {
    val selectedIndex by mainViewModel.selectedIndex.collectAsState()
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
                    onNavigateToLogin = { navController.navigate("login") }
                )
                1 -> ExploreScreen(
                    exploreViewModel = viewModel(),
                    authViewModel = viewModel(),
                    onNavigateToAdd = { navController.navigate("add_explore") },
                    onNavigateToEdit = { id -> navController.navigate("edit_explore/$id") },
                    onNavigateToLogin = { navController.navigate("login") }
                )
                2 -> FavoritesScreen(
                    exploreViewModel = viewModel(),
                    onNavigateToEdit = { id -> navController.navigate("edit_explore/$id") }
                )
                3 -> ProfileScreen(navController)
            }
        }
    }
} 