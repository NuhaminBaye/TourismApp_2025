package com.example.mobileapppro
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel for managing the main screen's state, specifically the selected navigation item.
 *
 * This ViewModel is responsible for holding and updating the index of the currently
 * selected item in the bottom navigation. It uses `StateFlow` to allow the UI to
 * observe changes in the selected item.
 */
class MainViewModel : ViewModel() {
    private val _selectedIndex = MutableStateFlow(0)

    /**
     * A `StateFlow` representing the index of the currently selected navigation item.
     * * The UI observes this property to update the displayed content when the user
     * selects a different navigation item.
     */
    val selectedIndex: StateFlow<Int> = _selectedIndex

    fun onNavigationItemSelected(index: Int) {
        _selectedIndex.value = index
    }
}
