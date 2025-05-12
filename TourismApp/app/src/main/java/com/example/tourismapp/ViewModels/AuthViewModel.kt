package com.example.tourismapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourismapp.data.User
import com.example.tourismapp.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _currentUser

    private val _username = MutableStateFlow("john122") // Default for testing
    val username: StateFlow<String> = _username

    private val _name = MutableStateFlow("john")
    val names: StateFlow<String> = _name

    fun setUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun setName(newName: String) {
        _name.value = newName
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            // Implement login logic here
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            // Implement registration logic here
        }
    }

    fun logout() {
        _currentUser.value = null
    }
}