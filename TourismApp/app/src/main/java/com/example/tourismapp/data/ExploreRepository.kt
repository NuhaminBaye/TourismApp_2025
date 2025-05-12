package com.example.tourismapp.data

import kotlinx.coroutines.flow.Flow

class ExploreRepository(private val exploreDao: ExploreDao) {
    suspend fun createExplore(explore: Explore): Long {
        return exploreDao.insertExplore(explore)
    }

    suspend fun updateExplore(explore: Explore) {
        exploreDao.updateExplore(explore)
    }

    suspend fun deleteExplore(explore: Explore) {
        exploreDao.deleteExplore(explore)
    }

    fun getAllExplores(): Flow<List<Explore>> {
        return exploreDao.getAllExplores()
    }

    suspend fun getExploreById(exploreId: Int): Explore? {
        return exploreDao.getExploreById(exploreId)
    }

    fun getExploresByAdmin(adminId: Int): Flow<List<Explore>> {
        return exploreDao.getExploresByAdmin(adminId)
    }
} 