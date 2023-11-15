package com.example.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "sensor_type") val sensorType: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)