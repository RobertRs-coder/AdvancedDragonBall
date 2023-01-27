package com.advanced.advanceddragonball.data.local

import androidx.room.*
import com.advanced.advanceddragonball.data.local.model.HeroLocal

@Dao
interface HeroDao {
    @Query("SELECT * FROM heroes")
    fun getAllHeroes(): List<HeroLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(superHero: List<HeroLocal>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSuperhero(superHero: HeroLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHero(hero: HeroLocal)
}