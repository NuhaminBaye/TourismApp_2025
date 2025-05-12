package com.example.tourismapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourismapp.data.Explore
import com.example.tourismapp.data.Wishlist
import com.example.tourismapp.data.WishlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WishlistViewModel(private val wishlistRepository: WishlistRepository) : ViewModel() {
    private val _wishlistExplores = MutableStateFlow<List<Explore>>(emptyList())
    val wishlistExplores: StateFlow<List<Explore>> = _wishlistExplores

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadWishlist(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                wishlistRepository.getWishlistExplores(userId).collect { exploreList ->
                    _wishlistExplores.value = exploreList
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToWishlist(userId: Int, exploreId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                if (!wishlistRepository.isInWishlist(userId, exploreId)) {
                    wishlistRepository.addToWishlist(Wishlist(userId = userId, exploreId = exploreId))
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFromWishlist(userId: Int, exploreId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val wishlist = Wishlist(userId = userId, exploreId = exploreId)
                wishlistRepository.removeFromWishlist(wishlist)
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun isInWishlist(userId: Int, exploreId: Int): Boolean {
        var result = false
        viewModelScope.launch {
            result = wishlistRepository.isInWishlist(userId, exploreId)
        }
        return result
    }
} 