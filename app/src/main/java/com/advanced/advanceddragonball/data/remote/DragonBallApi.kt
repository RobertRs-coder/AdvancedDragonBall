package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.request.HeroRequest
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Bootcamp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DragonBallApi {

    @GET("api/data/bootcamps")
    suspend fun getBootcamps(): List<Bootcamp>

    @POST("api/heros/all")
//    @Headers("Authorization: Bearer ${TOKEN}
    suspend fun getHeroes(@Body heroRequest: HeroRequest): List<HeroRemote>

    @POST("api/heros/all")
//    @Headers("Authorization: Bearer ${TOKEN}
    suspend fun getHeroesWithException(): List<HeroRemote>

    @POST("api/heros/all")
//    @Headers("Authorization: Bearer ${TOKEN}
    suspend fun getHeroDetail(@Body heroRequest: HeroRequest): List<HeroRemote>
}