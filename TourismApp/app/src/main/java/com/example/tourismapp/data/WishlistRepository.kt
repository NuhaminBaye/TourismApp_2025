package com.example.tourismapp.data

import kotlinx.coroutines.flow.Flow

class WishlistRepository(private val wishlistDao: WishlistDao) {
    suspend fun addToWishlist(wishlist: Wishlist): Long {
        return wishlistDao.insertWishlist(wishlist)
    }

    suspend fun removeFromWishlist(wishlist: Wishlist) {
        wishlistDao.deleteWishlist(wishlist)
    }

    fun getWishlistsByUser(userId: Int): Flow<List<Wishlist>> {
        return wishlistDao.getWishlistsByUser(userId)
    }

    fun getWishlistExplores(userId: Int): Flow<List<Explore>> {
        return wishlistDao.getWishlistExplores(userId)
    }

    suspend fun isInWishlist(userId: Int, exploreId: Int): Boolean {
        return wishlistDao.isInWishlist(userId, exploreId)
    }
} 