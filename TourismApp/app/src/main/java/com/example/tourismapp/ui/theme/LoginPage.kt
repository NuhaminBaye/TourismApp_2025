package com.example.tourismapp.ui.theme


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tourismapp.ViewModels.LoginViewModel
import com.example.tourismapp.R
import com.example.tourismapp.ViewModels.AuthViewModel
import com.example.tourismapp.data.User


@Composable
fun LogIn(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    authViewModel: AuthViewModel,
    currentUser: User
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(150.dp),
            imageVector = Icons.Default.Person,
            contentDescription = "Profile Icon"
        )

        Text("User Login", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(35.dp))

        OutlinedTextField(
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = { Text("User Name") },
            shape = RoundedCornerShape(25.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("E-mail") },
            shape = RoundedCornerShape(25.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password") },
            visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val iconRes = if (viewModel.passwordVisible)
                    R.drawable.baseline_auto_fix_high_24
                else
                    R.drawable.baseline_auto_fix_high_24

                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = if (viewModel.passwordVisible) "Hide password" else "Show password",
                        tint = if (viewModel.passwordVisible) Color.Blue else Color.Gray
                    )
                }
            },
            shape = RoundedCornerShape(25.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.login {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier
                .padding(25.dp)
                .width(150.dp)
                .height(45.dp),
            shape = RoundedCornerShape(40.dp)
        ) {
            Text("LogIn", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Signins {
            navController.navigate("signup") {
                popUpTo("login") { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun Signins(control:()->Unit){
    var newtext= buildAnnotatedString {
        append("Don't Have An Account? ")
        withStyle(style= SpanStyle(color = Color.Red)){
            append("SignUp")
        }
    }
    TextButton(onClick = control) {
        Text(text = newtext,
            fontSize = 18.sp,)
//        modifier = Modifier.clickable { })
    }
}
