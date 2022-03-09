package com.example.flow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flow.data.features.movie.MovieDao
import com.example.flow.data.features.movie.models.Movie

@Database(
    entities = [Movie::class],
    version = RoomDaoDatabase.DB_VERSION
)
abstract class RoomDaoDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "room-dao-data"
    }
}
