package com.example.mobileapppro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex

    fun onNavigationItemSelected(index: Int) {
        _selectedIndex.value = index
    }
}
