package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.request.HeroFavoriteRequest
import com.advanced.advanceddragonball.data.remote.request.HeroLocationRequest
import com.advanced.advanceddragonball.data.remote.request.HeroRequest
import com.advanced.advanceddragonball.data.remote.response.HeroLocationRemote
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import okhttp3.Credentials
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: DragonBallApi
    ): RemoteDataSource {

    override suspend fun getHeroes(auth: String): Result<List<HeroRemote>?> {
        return runCatching{ api.getHeroes(HeroRequest(), auth) }
    }

    override suspend fun getHeroDetail(name: String, auth: String): Result<HeroRemote?> {
        return runCatching { api.getHeroDetail(HeroRequest(name), auth).firstOrNull() }
    }

    override suspend fun getHeroLocations(name: String, auth: String): Result<List<HeroLocationRemote>?> {
        return runCatching{ api.getHeroLocations(HeroLocationRequest(name), auth) }
    }


    override suspend fun login(email: String, password: String): Result<String?> {
        return runCatching { api.login(Credentials.basic(email, password)) }
    }

    override suspend fun switchHeroLike(id: String, auth: String) {
        kotlin.runCatching { api.switchHeroLike(HeroFavoriteRequest(id), auth) }
    }
}