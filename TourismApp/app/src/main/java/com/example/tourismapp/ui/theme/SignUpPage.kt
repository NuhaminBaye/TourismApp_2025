package com.example.tourismapp.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
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
import com.example.tourismapp.R
import com.example.tourismapp.ViewModels.AuthViewModel
import com.example.tourismapp.ViewModels.SignUpViewModel
import com.example.tourismapp.data.User
import androidx.compose.material3.Icon as Icon


@Composable
fun SignUp(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel(),
    authViewModel: AuthViewModel,
    currentUser: User
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.wels),
            contentDescription = "welcome image",
            modifier = Modifier.fillMaxWidth().size(250.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Create An Account", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(16.dp))

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
                    val icon = if (viewModel.passwordVisible)
                        painterResource(id = R.drawable.baseline_auto_fix_high_24)
                    else
                        painterResource(id = R.drawable.baseline_account_box_24)

                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(
                            painter = icon,
                            contentDescription = if (viewModel.passwordVisible) "Hide password" else "Show password",
                            tint = if (viewModel.passwordVisible) Color.Blue else Color.Gray
                        )
                    }
                },
                shape = RoundedCornerShape(25.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(2.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .clickable { viewModel.toggleRememberMe() }
                ) {
                    if (viewModel.rememberMe) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Checked",
                            tint = Color.Red,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                Text(
                    "Remember Me",
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.signUp {
                        navController.navigate("home") {
                            popUpTo("signup") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier.padding(25.dp).width(150.dp).height(45.dp),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text("Sign Up", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Signs {
                navController.navigate("login") {
                    popUpTo("signup") { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
fun Signs(controls:()->Unit){
    var newtext= buildAnnotatedString {
        append("Already Signed Up?")
        withStyle(style= SpanStyle(color = Color.Red)){
            append("LogIn")
        }
    }
    TextButton(onClick = controls) {
        Text(text = newtext,
            fontSize = 18.sp,)
//        modifier = Modifier.clickable { })
    }
}



