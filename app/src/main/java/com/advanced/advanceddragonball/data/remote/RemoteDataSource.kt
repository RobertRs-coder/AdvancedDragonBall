package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Bootcamp

interface RemoteDataSource {
    suspend fun getHeroes(auth: String): Result<List<HeroRemote>?>
    suspend fun getHeroDetail(name: String, auth: String): Result<HeroRemote?>
    suspend fun login(email: String, password: String): Result<String?>
}