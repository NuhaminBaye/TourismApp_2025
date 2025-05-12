package com.example.tourismapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "wishlists",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Explore::class,
            parentColumns = ["id"],
            childColumns = ["exploreId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Wishlist(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val exploreId: Int
) 