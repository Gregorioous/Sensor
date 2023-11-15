package com.example.repository.di

import android.content.Context
import androidx.room.Room
import com.example.repository.database.SensorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SensorDatabase {
        return Room.databaseBuilder(context, SensorDatabase::class.java, "sensor_database").build()
    }

    @Provides
    @Singleton
    fun provideSessionRepo(db: SensorDatabase) = db.sessionDao()
}