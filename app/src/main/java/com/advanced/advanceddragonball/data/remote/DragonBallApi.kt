package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.request.HeroRequest
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DragonBallApi {

    @POST("api/heros/all")
    suspend fun getHeroes(@Body heroRequest: HeroRequest, @Header("Authorization") auth: String): List<HeroRemote>

    @POST("api/heros/all")
    suspend fun getHeroDetail(@Body heroRequest: HeroRequest, @Header("Authorization") auth: String): List<HeroRemote>

    @POST("/api/auth/login")
    suspend fun login(@Header("Authorization") auth: String): String
}