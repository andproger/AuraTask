package com.andproger.testtaskaura.presentation.di

import android.content.Context
import androidx.room.Room
import com.andproger.testtaskaura.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext
        context: Context
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()

    @Provides
    fun provideBootEventsDao(database: AppDatabase) = database.getBootEventsDao()
}