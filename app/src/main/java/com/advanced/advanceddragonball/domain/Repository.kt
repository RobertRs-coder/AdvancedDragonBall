package com.advanced.advanceddragonball.domain


import com.advanced.advanceddragonball.data.HeroListState

interface Repository{
    suspend fun getBootcamps(): List<Bootcamp>
    suspend fun getHeroes(): List<Hero>
    suspend fun getHeroesWithCache(): List<Hero>
    suspend fun getHeroesWithException(): HeroListState
}