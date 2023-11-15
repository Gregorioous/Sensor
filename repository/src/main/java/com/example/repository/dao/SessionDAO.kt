package com.example.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.repository.entity.SessionEntity

@Dao
interface SessionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SessionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<SessionEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: SessionEntity)

    @Delete
    suspend fun delete(entity: SessionEntity)

    @Query("DELETE FROM session WHERE session.id = :id")
    suspend fun delete(id: Long)

}