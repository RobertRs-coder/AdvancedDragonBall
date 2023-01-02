package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.request.HeroRequest
import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteDataSource {

    companion object {
        const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InByaXZhdGUifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiIyNjBENjk3My00Njc0LTQyRDQtQjUxRi00MjYwRTBBMUJCOUYiLCJlbWFpbCI6InJyb2pvLnZhQGdtYWlsLmNvbSJ9.lQOqPIfkP0_GJs8lik1PmfacpoQcyDxy3NGJGeflOEc"
    }
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    //Change header with token
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()

            val newRequest = originalRequest.newBuilder()
//                .header("Authorization", "Bearer $TOKEN")
                .header("Content-type", "application/json")
                .build()

            chain.proceed(newRequest)
        }
        .authenticator { _, response ->
            response.request.newBuilder().header("Authorization", "Bearer $TOKEN").build()
        }
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dragonball.keepcoding.education/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private var api: DragonBallAPI = retrofit.create(DragonBallAPI::class.java)

    suspend fun getBootcamps(): List<Bootcamp> {
        return api.getBootcamps()
    }
    suspend fun getHeroes(): List<Hero> {
        return api.getHeros(HeroRequest())
    }
}