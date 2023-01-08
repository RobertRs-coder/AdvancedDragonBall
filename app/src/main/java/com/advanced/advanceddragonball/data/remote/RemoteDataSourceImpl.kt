package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.request.HeroRequest
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Bootcamp
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: DragonBallApi
    ): RemoteDataSource {

    override suspend fun getBootcamps(): List<Bootcamp> {
        return api.getBootcamps()
    }

    override suspend fun getHeroes(): List<HeroRemote> {
        return api.getHeroes(HeroRequest())
    }
    override suspend fun getHeroesWithException(): Result<List<HeroRemote>>  {
        return runCatching {  api.getHeroesWithException() }
    }

    override suspend fun getHeroDetail(name: String): Result<HeroRemote?> {
        return runCatching { api.getHeroDetail(HeroRequest(name)).firstOrNull()  }
    }
}