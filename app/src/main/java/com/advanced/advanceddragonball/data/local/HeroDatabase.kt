package com.advanced.advanceddragonball.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.advanced.advanceddragonball.data.local.model.HeroLocal

@Database(entities = [HeroLocal::class], version = 1)
abstract class HeroDatabase : RoomDatabase() {
    abstract fun getDao(): HeroDao
}