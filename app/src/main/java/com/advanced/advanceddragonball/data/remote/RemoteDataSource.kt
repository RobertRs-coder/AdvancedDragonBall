package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.response.HeroLocationRemote
import com.advanced.advanceddragonball.data.remote.response.HeroRemote

interface RemoteDataSource {
    suspend fun getHeroes(auth: String): Result<List<HeroRemote>?>
    suspend fun getHeroDetail(name: String, auth: String): Result<HeroRemote?>
    suspend fun getHeroLocations(name: String, auth: String): Result<List<HeroLocationRemote>?>
    suspend fun login(email: String, password: String): Result<String?>
}