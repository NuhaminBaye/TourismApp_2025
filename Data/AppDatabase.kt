package com.example.tourismappfinal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tourismappfinal.R
import com.example.tourismappfinal.data.dao.ExploreDao
import com.example.tourismappfinal.data.dao.UserDao
import com.example.tourismappfinal.data.entity.ExploreEntity
import com.example.tourismappfinal.data.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE explore_items ADD COLUMN imageResId INTEGER DEFAULT NULL")
    }
}

@Database(
    entities = [ExploreEntity::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exploreDao(): ExploreDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tourism_app_database"
                )
                .addMigrations(MIGRATION_1_2)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val exploreDao = INSTANCE?.exploreDao()
                            exploreDao?.let { dao ->
                                // Add initial data
                                val initialItems = listOf(
                                    ExploreEntity(
                                        title = "Lodge du Chateau",
                                        description = "Luxury lodge with mountain views",
                                        region = "Somali, Jigjiga",
                                        price = "$180",
                                        imageUrl = "",
                                        imageResId = R.drawable.hu,
                                        rating = 5f
                                    ),
                                    ExploreEntity(
                                        title = "Hilton",
                                        description = "International luxury hotel",
                                        region = "Addis Ababa",
                                        price = "$190",
                                        imageUrl = "",
                                        imageResId = R.drawable.h2,
                                        rating = 4f
                                    ),
                                    ExploreEntity(
                                        title = "Haile Resort",
                                        description = "Lakeside resort with spa",
                                        region = "Sidama, Hawassa",
                                        price = "$170",
                                        imageUrl = "",
                                        imageResId = R.drawable.h3,
                                        rating = 3f
                                    ),
                                    ExploreEntity(
                                        title = "Planet Hotel",
                                        description = "Modern city hotel",
                                        region = "Tigray, Mekele",
                                        price = "$190",
                                        imageUrl = "",
                                        imageResId = R.drawable.h4,
                                        rating = 2f
                                    ),
                                    ExploreEntity(
                                        title = "Inn of the Four Sisters",
                                        description = "Historic hotel in Gondar",
                                        region = "Amhara, Gonder",
                                        price = "$160",
                                        imageUrl = "",
                                        imageResId = R.drawable.h5,
                                        rating = 2f
                                    )
                                )
                                initialItems.forEach { dao.insertExploreItem(it) }
                            }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 