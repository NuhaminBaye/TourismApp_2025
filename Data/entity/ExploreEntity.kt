package com.example.tourismappfinal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "explore_items")
data class ExploreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val region: String,
    val price: String,
    val imageUrl: String,
    val imageResId: Int? = null,
    val rating: Float,
    val isFavorite: Boolean = false
)