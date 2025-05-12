package com.example.tourismappfinal.data.repository

import com.example.tourismappfinal.data.dao.UserDao
import com.example.tourismappfinal.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): UserEntity? =
        userDao.login(email, password)

    suspend fun registerUser(user: UserEntity) = userDao.registerUser(user)

    suspend fun getUserByEmail(email: String): UserEntity? =
        userDao.getUserByEmail(email)

    fun getUserById(userId: Long): Flow<UserEntity?> = userDao.getUserById(userId)
} 