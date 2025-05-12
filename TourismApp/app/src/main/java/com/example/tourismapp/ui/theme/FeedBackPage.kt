package com.example.tourismapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourismapp.ViewModels.FeedBackViewModel
import com.example.tourismapp.data.User

@Composable
fun FeedbackScreen(viewModel: FeedBackViewModel = viewModel(),navController: NavController, currentUser: User) {
    val rating by viewModel.rating.collectAsState()
    val comment by viewModel.comment.collectAsState()

    val (emoji, label) = when (rating) {
        1 -> "😞" to "Bad"
        2 -> "😐" to "Better"
        3 -> "🙂" to "Okay"
        4 -> "😊" to "Good"
        5 -> "😍" to "Excellent"
        else -> "" to ""
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // ✅ Set background here
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp) // Added vertical padding
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Changed from SpaceEvenly
        ) {
            // Title
            Row(){ Text("Feedback", fontSize = 28.sp, fontWeight = FontWeight.Bold) }
            Spacer(modifier = Modifier.height(40.dp))
            Text("💬", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(35.dp))

            // Rating Section
            Text("How Would You Rate Our App ?", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(20.dp))

            Text(emoji, fontSize = 64.sp)
            Text(label, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = if (index < rating) Color(0xFFFFC107) else Color.LightGray,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { viewModel.setRating(index + 1) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(45.dp))

            // Comment Section
            Text("Any Additional comments ?", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text("Let Us Know", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = comment,
                onValueChange = viewModel::setComment,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Write your comment...") }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            Button(
                onClick = { /* Submit logic */ },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp), // Increased height for better visibility
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB33BF6))
            ) {
                Text("Submit", color = Color.White)
            }

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}
