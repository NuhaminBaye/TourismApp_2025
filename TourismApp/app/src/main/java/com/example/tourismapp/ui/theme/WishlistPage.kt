package com.example.tourismapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourismapp.data.User

@Composable
fun WishList(modifier: Modifier = Modifier, currentUser: User){
    Column(
        modifier = modifier.fillMaxSize()
            .background(Color(0xFF197602)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "WishList page",
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold, color=Color.White)
    }

}