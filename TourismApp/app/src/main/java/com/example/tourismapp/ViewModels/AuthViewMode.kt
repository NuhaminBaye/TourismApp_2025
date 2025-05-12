//package com.example.tourismapp.ViewModels
//
//
//import androidx.lifecycle.ViewModel
//import com.example.tourismapp.data.UserRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//class AuthViewModel(provideUserRepository: UserRepository) : ViewModel() {
//    private val _username = MutableStateFlow("john122") // Default for testing
//    val username: StateFlow<String> = _username
//
//    private val _name=MutableStateFlow("john")
//    val names:StateFlow<String> = _name
//
//    fun setUsername(newUsername: String) {
//        _username.value = newUsername
//    }
//    fun setName(newName:String){
//        _name.value= newName
//    }
//}
