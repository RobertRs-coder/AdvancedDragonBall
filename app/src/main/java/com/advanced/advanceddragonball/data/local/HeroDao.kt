package com.advanced.advanceddragonball.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.advanced.advanceddragonball.data.local.model.HeroLocal

@Dao
interface HeroDao {
    @Query("SELECT * FROM heroes")
    fun getAllHeroes(): List<HeroLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(superHero: List<HeroLocal>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSuperhero(superHero: HeroLocal)
}