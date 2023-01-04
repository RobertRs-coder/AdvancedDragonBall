package com.advanced.advanceddragonball.data.remote

import com.advanced.advanceddragonball.data.remote.request.HeroRequest
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Bootcamp

class RemoteDataSourceImpl(private val api: DragonBallApi): RemoteDataSource {

    override suspend fun getBootcamps(): List<Bootcamp> {
        return api.getBootcamps()
    }
    override suspend fun getHeroes(): List<HeroRemote> {
        return api.getHeroes(HeroRequest())
    }
}

////    companion object {
//        const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InByaXZhdGUifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiIyNjBENjk3My00Njc0LTQyRDQtQjUxRi00MjYwRTBBMUJCOUYiLCJlbWFpbCI6InJyb2pvLnZhQGdtYWlsLmNvbSJ9.lQOqPIfkP0_GJs8lik1PmfacpoQcyDxy3NGJGeflOEc"
//    }
//    private val moshi = Moshi.Builder()
//        .addLast(KotlinJsonAdapterFactory())
//        .build()
//
//    private val httpLoggingInterceptor =
//        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//    //Change header with token
//    private val okHttpClient = OkHttpClient.Builder()
//        .authenticator { _, response ->
//            response.request.newBuilder().header("Authorization", "Bearer $TOKEN").build()
//        }
//        .build()
//
//    private val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl("https://dragonball.keepcoding.education/")
//        .client(okHttpClient)
//        .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .build()
//
//    private var api: DragonBallAPI = retrofit.create(DragonBallAPI::class.java)