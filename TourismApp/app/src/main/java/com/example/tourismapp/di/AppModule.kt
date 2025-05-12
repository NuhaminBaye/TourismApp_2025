package com.example.tourismapp.di

import android.content.Context
import com.example.tourismapp.data.AppDatabase
import com.example.tourismapp.data.ExploreRepository
import com.example.tourismapp.data.UserRepository
import com.example.tourismapp.data.WishlistRepository
import com.example.tourismapp.data.LanguageManager // Make sure to import this
import com.example.tourismapp.ViewModels.AuthViewModel
import com.example.tourismapp.ViewModels.ExploreViewModel
import com.example.tourismapp.ViewModels.WishlistViewModel
import com.example.tourismapp.ViewModels.LanguageViewModel // Import the LanguageViewModel

object AppModule {
    private var database: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return database ?: AppDatabase.getDatabase(context).also { database = it }
    }

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepository(provideDatabase(context).userDao())
    }

    fun provideExploreRepository(context: Context): ExploreRepository {
        return ExploreRepository(provideDatabase(context).exploreDao())
    }

    fun provideWishlistRepository(context: Context): WishlistRepository {
        return WishlistRepository(provideDatabase(context).wishlistDao())
    }

    fun provideAuthViewModel(context: Context): AuthViewModel {
        return AuthViewModel(provideUserRepository(context))
    }

    fun provideExploreViewModel(context: Context): ExploreViewModel {
        return ExploreViewModel(provideExploreRepository(context))
    }

    fun provideWishlistViewModel(context: Context): WishlistViewModel {
        return WishlistViewModel(provideWishlistRepository(context))
    }

    // Add this method
    fun provideLanguageManager(context: Context): LanguageManager {
        return LanguageManager(context)
    }

    fun provideLanguageViewModel(context: Context): LanguageViewModel {
        return LanguageViewModel(provideLanguageManager(context))
    }
}