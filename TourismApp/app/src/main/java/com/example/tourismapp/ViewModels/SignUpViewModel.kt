package com.example.tourismapp.ViewModels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var rememberMe by mutableStateOf(false)

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun toggleRememberMe() {
        rememberMe = !rememberMe
    }

    fun signUp(onSuccess: () -> Unit) {
        // TODO: Replace with real sign-up logic
        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            onSuccess()
        }
    }
}
