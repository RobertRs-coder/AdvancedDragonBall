package com.advanced.advanceddragonball.fakes

import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import retrofit2.HttpException
import retrofit2.Response

class FakeRemoteDataSource: RemoteDataSource {
    override suspend fun getHeroes(auth: String): Result<List<HeroRemote>?> {
        TODO("Not yet implemented")
    }

    override suspend fun getHeroDetail(name: String, auth: String): Result<HeroRemote?> {
        return when(name){
            "SUCCESS" -> Result.success(HeroRemote("id", "Maestro Roshi", "photo", "description", false))
            "NETWORK_ERROR" -> Result.failure(HttpException(Response.success(204) {}))
            "NULL"-> Result.failure(NullPointerException("Null pointer error"))
            "SUCCESS_BUT_NULL"-> Result.success(null)
            else -> {Result.failure(Exception())}
        }
    }

    override suspend fun login(email: String, password: String): Result<String?> {
        TODO("Not yet implemented")
    }
}