package com.example.tourismapp.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tourismapp.ui.theme.HomeLists
import com.example.tourismapp.ui.theme.getHomesLists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    private val _searchedQuery = MutableStateFlow("")
    val searchedQuery: StateFlow<String> = _searchedQuery

    private val _isSearched = MutableStateFlow(false)
    val isSearched: StateFlow<Boolean> = _isSearched

    private val _items = mutableStateListOf<HomeLists>().apply {
        addAll(getHomesLists())
    }
    val items: List<HomeLists> get() = _items

    fun updateQuery(value: String) {
        _query.value = value
    }

    fun toggleSearchActive(active: Boolean) {
        _isSearchActive.value = active
    }

    fun performSearch() {
        _searchedQuery.value = _query.value
        _isSearched.value = true
        _isSearchActive.value = false
    }

    fun resetSearch() {
        _query.value = ""
        _searchedQuery.value = ""
        _isSearched.value = false
        _isSearchActive.value = false
    }

    fun toggleLike(item: HomeLists) {
        val index = _items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            _items[index] = item.copy(isLiked = !item.isLiked)
        }
    }
}