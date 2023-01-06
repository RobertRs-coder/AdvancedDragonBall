package com.advanced.advanceddragonball.di

import android.content.Context
import androidx.room.Room
import com.advanced.advanceddragonball.data.local.HeroDao
import com.advanced.advanceddragonball.data.local.HeroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HeroDatabase {
        return Room.databaseBuilder(
            context,
            HeroDatabase::class.java, "database-name"
        ).build()
    }
    @Provides
    fun provideDao(database: HeroDatabase): HeroDao {
        return database.getDao()
    }
}
