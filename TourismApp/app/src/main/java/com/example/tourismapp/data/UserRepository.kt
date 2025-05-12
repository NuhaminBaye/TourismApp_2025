package com.example.tourismapp.data

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    fun getUserById(userId: Int): Flow<User> {
        return userDao.getUserById(userId)
    }

    suspend fun getAdmin(): User? {
        return userDao.getAdmin()
    }
} 