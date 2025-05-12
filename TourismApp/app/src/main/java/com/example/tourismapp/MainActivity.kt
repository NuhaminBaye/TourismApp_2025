package com.example.tourismapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tourismapp.ViewModels.AuthViewModel
import com.example.tourismapp.ViewModels.ExploreViewModel
import com.example.tourismapp.ViewModels.LanguageViewModel
import com.example.tourismapp.ViewModels.WishlistViewModel
import com.example.tourismapp.di.AppModule
import com.example.tourismapp.ui.theme.FeedbackScreen
import com.example.tourismapp.ui.theme.HomePage
import com.example.tourismapp.ui.theme.LogIn
import com.example.tourismapp.ui.theme.Screen
import com.example.tourismapp.ui.theme.SignUp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel {
                AppModule.provideAuthViewModel(this@MainActivity)
            }
            val exploreViewModel: ExploreViewModel = viewModel {
                AppModule.provideExploreViewModel(this@MainActivity)
            }
            val wishlistViewModel: WishlistViewModel = viewModel {
                AppModule.provideWishlistViewModel(this@MainActivity)
            }
            val languageViewModel: LanguageViewModel = viewModel {
                AppModule.provideLanguageViewModel(this@MainActivity)
            }

            val currentUser by authViewModel.currentUser.collectAsState(initial = null)

            NavHost(navController = navController, startDestination = Screen.Login.route) {
                composable(Screen.Login.route) {
                    LogIn(navController = navController, authViewModel = authViewModel)
                }
                composable(Screen.SignUp.route) {
                    SignUp(navController = navController, authViewModel = authViewModel)
                }
                composable(Screen.Home.route) {
                    if (currentUser != null) {
                        HomePage(
                            navController = navController,
                            exploreViewModel = exploreViewModel,
                            wishlistViewModel = wishlistViewModel,
                            currentUser = currentUser!!
                        )
                    } else {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                }
                composable(Screen.Feedback.route) {
                    if (currentUser != null) {
                        FeedbackScreen(
                            navController = navController,
                            currentUser = currentUser!!
                        )
                    } else {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Feedback.route) { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}