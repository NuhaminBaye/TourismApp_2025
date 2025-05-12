package com.example.tourismappfinal.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tourismappfinal.data.entity.UserEntity
import com.example.tourismappfinal.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.login(email, password)
                if (user != null) {
                    _currentUser.value = user
                    _authError.value = null
                } else {
                    _authError.value = "Invalid email or password"
                }
            } catch (e: Exception) {
                _authError.value = e.message ?: "Login failed"
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                if (repository.getUserByEmail(email) != null) {
                    _authError.value = "Email already registered"
                    return@launch
                }
                
                val user = UserEntity(
                    name = name,
                    email = email,
                    password = password
                )
                repository.registerUser(user)
                _currentUser.value = user
                _authError.value = null
            } catch (e: Exception) {
                _authError.value = e.message ?: "Registration failed"
            }
        }
    }

    fun logout() {
        _currentUser.value = null
    }

    fun clearError() {
        _authError.value = null
    }

    class Factory(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
