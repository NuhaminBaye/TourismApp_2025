package com.example.tourismappfinal.data.repository

import com.example.tourismappfinal.data.dao.ExploreDao
import com.example.tourismappfinal.data.entity.ExploreEntity
import kotlinx.coroutines.flow.Flow

class ExploreRepository(private val exploreDao: ExploreDao) {
    fun getAllExploreItems(): Flow<List<ExploreEntity>> = exploreDao.getAllExploreItems()
    
    fun getFavoriteItems(): Flow<List<ExploreEntity>> = exploreDao.getFavoriteItems()
    
    suspend fun insertExploreItem(exploreItem: ExploreEntity) = exploreDao.insertExploreItem(exploreItem)
    
    suspend fun updateExploreItem(exploreItem: ExploreEntity) = exploreDao.updateExploreItem(exploreItem)
    
    suspend fun deleteExploreItem(exploreItem: ExploreEntity) = exploreDao.deleteExploreItem(exploreItem)
    
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean) = 
        exploreDao.updateFavoriteStatus(id, isFavorite)
} 