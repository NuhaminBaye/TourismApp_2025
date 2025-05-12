package com.example.tourismapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "explores")
data class Explore(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val imageUrl: String,
    val rating: Float,
    val createdBy: Int // User ID of the admin who created this
) 