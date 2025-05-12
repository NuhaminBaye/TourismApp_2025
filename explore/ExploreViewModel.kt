package com.example.mobileapppro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExploreViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    private val _searchedQuery = MutableStateFlow("")
    val searchedQuery: StateFlow<String> = _searchedQuery

    private val _isSearched = MutableStateFlow(false)
    val isSearched: StateFlow<Boolean> = _isSearched

    private val _items = MutableStateFlow(getHomeList().toMutableList())
    val items: StateFlow<List<ExploreLists>> = _items

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun toggleSearchActive(active: Boolean) {
        _isSearchActive.value = active
    }

    fun performSearch() {
        _searchedQuery.value = _query.value
        _isSearchActive.value = false
        _isSearched.value = true
    }

    fun clearSearch() {
        _query.value = ""
        _searchedQuery.value = ""
        _isSearchActive.value = false
        _isSearched.value = false
    }

    fun toggleLike(item: ExploreLists) {
        val updated = _items.value.map {
            if (it.id == item.id) it.copy(isLiked = !it.isLiked) else it
        }
         _items.value = updated.toMutableList()

    }
}
