package com.example.repository.repositiry

import com.example.repository.dao.SessionDAO
import com.example.repository.entity.SessionEntity
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val sessionDAO: SessionDAO
) {

    suspend fun insert(sensor: SessionEntity): Long {
        return sessionDAO.insert(sensor)
    }

    suspend fun deleteById(id: Long) {
        sessionDAO.delete(id)
    }

}