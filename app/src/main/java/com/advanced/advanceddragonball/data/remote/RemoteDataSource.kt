package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteDataSource {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dragonball.keepcoding.education/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private var api: DragonBallAPI = retrofit.create(DragonBallAPI::class.java)

    suspend fun getBootcamps(): List<Bootcamp> {
        return api.getBootcamps()
    }
    suspend fun getHeroes(): List<Hero> {
        return api.getHeros()
    }
}