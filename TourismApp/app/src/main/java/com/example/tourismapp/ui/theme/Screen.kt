package com.example.tourismapp.ui.theme


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object EditProfile : Screen("edit_profile")
    object Settings : Screen("settings")
    object Feedback : Screen("feedback")
}
