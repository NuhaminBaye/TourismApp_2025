package com.example.tourismapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Insert
    suspend fun insertWishlist(wishlist: Wishlist): Long

    @Delete
    suspend fun deleteWishlist(wishlist: Wishlist)

    @Query("SELECT * FROM wishlists WHERE userId = :userId")
    fun getWishlistsByUser(userId: Int): Flow<List<Wishlist>>

    @Query("SELECT * FROM explores WHERE id IN (SELECT exploreId FROM wishlists WHERE userId = :userId)")
    fun getWishlistExplores(userId: Int): Flow<List<Explore>>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlists WHERE userId = :userId AND exploreId = :exploreId)")
    suspend fun isInWishlist(userId: Int, exploreId: Int): Boolean
} 