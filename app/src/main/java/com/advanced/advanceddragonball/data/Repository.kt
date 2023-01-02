package com.advanced.advanceddragonball.data

import android.content.Context
import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero

class Repository {

    private val remoteDataSource = RemoteDataSource()
    private val localDataSource = LocalDataSource()
    private val remoteToPresentationMapper = RemoteToPresentationMapper()


    suspend fun getBootcamps(): List<Bootcamp> {
        return remoteDataSource.getBootcamps()
    }

    suspend fun getHeroes(): List<Hero> {
        return remoteToPresentationMapper.map(remoteDataSource.getHeroes())
    }

    fun initLocalDatabase(context: Context) {
        localDataSource.initDatabase(context)
    }
}