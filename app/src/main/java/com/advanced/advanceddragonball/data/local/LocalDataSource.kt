package com.advanced.advanceddragonball.data.local

import com.advanced.advanceddragonball.data.local.model.HeroLocal

interface LocalDataSource {
    fun getHeroes(): List<HeroLocal>
    suspend fun insertHeroes(remoteHeroes: List<HeroLocal>)
    suspend fun updateHero(remoteHero: HeroLocal)
}
