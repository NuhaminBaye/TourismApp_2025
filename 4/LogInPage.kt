
package com.example.tourismappfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tourismappfinal.R
import com.example.tourismappfinal.ViewModel.AuthViewModel
import com.example.tourismappfinal.util.ValidationUtils
import com.example.tourismappfinal.ui.components.AppBottomNavigation
import com.example.tourismappfinal.ui.components.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val authError by authViewModel.authError.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let {
            onLoginSuccess()
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                currentRoute = BottomNavItem.PROFILE,
                onNavigate = { route ->
                    when (route) {
                        BottomNavItem.HOME -> onNavigateToHome()
                        BottomNavItem.EXPLORE -> onNavigateToExplore()
                        BottomNavItem.FAVORITES -> onNavigateToFavorites()
                        else -> {} // Already on PROFILE
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                label = { Text(stringResource(id = R.string.email)) },
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                label = { Text(stringResource(id = R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError != null,
                supportingText = { passwordError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            authError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    var isValid = true

                    if (!ValidationUtils.isValidEmail(email)) {
                        emailError = context.getString(R.string.error_invalid_email)
                        isValid = false
                    }

                    if (!ValidationUtils.isValidPassword(password)) {
                        passwordError = context.getString(R.string.error_invalid_password)
                        isValid = false
                    }

                    if (isValid) {
                        authViewModel.login(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.login))
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.register))
            }
        }
    }
}
