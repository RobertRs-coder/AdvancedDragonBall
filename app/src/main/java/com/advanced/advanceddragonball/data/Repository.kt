package com.advanced.advanceddragonball.data

import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero

class Repository {

    private val remoteDataSource = RemoteDataSource()

    suspend fun getBootcamps(): List<Bootcamp> {
        return remoteDataSource.getBootcamps()
    }

    suspend fun getHeroes(): List<Hero> {
        return remoteDataSource.getHeroes()
    }
}