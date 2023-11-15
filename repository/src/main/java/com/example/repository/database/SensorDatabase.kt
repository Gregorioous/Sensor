package com.example.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repository.dao.SessionDAO
import com.example.repository.entity.SessionEntity

@Database(
    entities = [
        SessionEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class SensorDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDAO
}