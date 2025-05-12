package com.example.tourismapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExploreDao {
    @Insert
    suspend fun insertExplore(explore: Explore): Long

    @Update
    suspend fun updateExplore(explore: Explore)

    @Delete
    suspend fun deleteExplore(explore: Explore)

    @Query("SELECT * FROM explores")
    fun getAllExplores(): Flow<List<Explore>>

    @Query("SELECT * FROM explores WHERE id = :exploreId")
    suspend fun getExploreById(exploreId: Int): Explore?

    @Query("SELECT * FROM explores WHERE createdBy = :adminId")
    fun getExploresByAdmin(adminId: Int): Flow<List<Explore>>
} 