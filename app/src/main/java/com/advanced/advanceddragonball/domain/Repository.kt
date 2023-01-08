package com.advanced.advanceddragonball.domain


import com.advanced.advanceddragonball.ui.detail.HeroDetailState
import com.advanced.advanceddragonball.ui.list.HeroListState

interface Repository{
    suspend fun getBootcamps(): List<Bootcamp>
    suspend fun getHeroes(): List<Hero>
    suspend fun getHeroesWithCache(): List<Hero>
    suspend fun getHeroesWithException(): HeroListState
    suspend fun getHeroDetail(name: String): HeroDetailState
}