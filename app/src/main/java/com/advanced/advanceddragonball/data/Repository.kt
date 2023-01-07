package com.advanced.advanceddragonball.data


import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero

interface Repository{
    suspend fun getBootcamps(): List<Bootcamp>
    suspend fun getHeroes(): List<Hero>
    suspend fun getHeroesWithCache(): List<Hero>
    suspend fun getHeroesWithException(): HeroListState
}