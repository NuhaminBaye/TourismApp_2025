package com.example.tourismapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourismapp.data.Explore
import com.example.tourismapp.data.ExploreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreViewModel(private val exploreRepository: ExploreRepository) : ViewModel() {

    private val _explores = MutableStateFlow<List<Explore>>(emptyList())
    val explores: StateFlow<List<Explore>> = _explores

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    private val _searchedQuery = MutableStateFlow("")
    val searchedQuery: StateFlow<String> = _searchedQuery

    private val _isSearched = MutableStateFlow(false)
    val isSearched: StateFlow<Boolean> = _isSearched

    init {
        loadExplores()
    }

    private fun loadExplores() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                exploreRepository.getAllExplores().collect { exploreList ->
                    _explores.value = exploreList
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        _isSearched.value = newQuery.isNotEmpty()
    }

    fun toggleSearchActive() {
        _isSearchActive.value = !_isSearchActive.value
    }

    fun performSearch() {
        _searchedQuery.value = _query.value
    }

    fun clearSearch() {
        _query.value = ""
        _searchedQuery.value = ""
        _isSearched.value = false
    }

    fun toggleLike(item: Explore) {
        val updated = _explores.value.map {
            if (it.id == item.id) it.copy(isLiked = !it.isLiked) else it
        }
        _explores.value = updated
    }
}
