package com.example.tourismapp.ViewModels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun resetFields() {
        username = ""
        email = ""
        password = ""
        passwordVisible = false
    }

    fun login(onSuccess: () -> Unit) {

        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            onSuccess()
        }
    }
}
