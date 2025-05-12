package com.example.tourismapp.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourismapp.ViewModels.AuthViewModel
import com.example.tourismapp.data.User

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(), // ViewModel holding user data
    currentUser: User
) {
    val username by authViewModel.username.collectAsState() // e.g., from signup
    val name by authViewModel.names.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Your Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dummy user image (use your actual image logic here)
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray, RectangleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text("@$username", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Settings", color = Color.Gray, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        ProfileItem("Account management") {  }
        ProfileItem("Notification") { }

        Spacer(modifier = Modifier.height(24.dp))

        ProfileItem("Login") { navController.navigate(Screen.Login.route) }
        Spacer(modifier = Modifier.height(8.dp))
        ProfileItem("Logout", color = Color.Red) {
            // Handle logout logic
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Support", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        ProfileItem("Help center") {}
        ProfileItem("Privacy policy") {}
        ProfileItem("About") {}
        ProfileItem("Feedback") { navController.navigate(Screen.Feedback.route) }
    }
}

@Composable
fun ProfileItem(
    text: String,
    color: Color = Color.Black,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = color,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    )
}
