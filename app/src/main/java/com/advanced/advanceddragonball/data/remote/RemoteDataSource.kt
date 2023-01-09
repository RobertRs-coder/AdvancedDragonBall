package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Bootcamp

interface RemoteDataSource {
    suspend fun getBootcamps(): List<Bootcamp>
    suspend fun getHeroes(): List<HeroRemote>
    suspend fun getHeroesWithException(): Result<List<HeroRemote>>
    suspend fun getHeroDetail(name: String): Result<HeroRemote?>
    suspend fun getToken(): Result<String?>
}