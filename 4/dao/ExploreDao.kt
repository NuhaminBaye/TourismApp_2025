package com.example.tourismappfinal.data.dao

import androidx.room.*
import com.example.tourismappfinal.data.entity.ExploreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExploreDao {
    @Query("SELECT * FROM explore_items")
    fun getAllExploreItems(): Flow<List<ExploreEntity>>

    @Query("SELECT * FROM explore_items WHERE isFavorite = 1")
    fun getFavoriteItems(): Flow<List<ExploreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExploreItem(exploreItem: ExploreEntity)

    @Update
    suspend fun updateExploreItem(exploreItem: ExploreEntity)

    @Delete
    suspend fun deleteExploreItem(exploreItem: ExploreEntity)

    @Query("UPDATE explore_items SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)
} 