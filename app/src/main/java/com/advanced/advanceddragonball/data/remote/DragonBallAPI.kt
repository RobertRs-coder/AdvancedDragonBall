package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero
import retrofit2.http.GET
import retrofit2.http.POST

interface DragonBallAPI {

    @GET("api/data/bootcamps")
    suspend fun getBootcamps(): List<Bootcamp>


    @POST("api/heros/all")
    suspend fun getHeros(): List<Hero>
}