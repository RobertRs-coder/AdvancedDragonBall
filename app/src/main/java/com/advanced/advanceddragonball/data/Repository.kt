package com.advanced.advanceddragonball.data

import android.content.Context
import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero

class Repository {

    private val remoteDataSource = RemoteDataSource()
    private val localDataSource = LocalDataSource()
    private val remoteToPresentationMapper = RemoteToPresentationMapper()
    private val remoteToLocalMapper = RemoteToLocalMapper()
    private val localToPresentationMapper = LocalToPresentationMapper()



    suspend fun getBootcamps(): List<Bootcamp> {
        return remoteDataSource.getBootcamps()
    }

    suspend fun getHeroes(): List<Hero> {
        return remoteToPresentationMapper.map(remoteDataSource.getHeroes())
    }

    suspend fun getHeroesWithCache(): List<Hero> {
        //TODO-1:Get data from local
        val localHeroes = localDataSource.getHeroes()
        //TODO-2:Check if there is data
        if(localHeroes.isEmpty()) {
            //TODO-3a:Get data from remote
            val remoteHeroes = remoteDataSource.getHeroes()
            //TODO-3b:Save data to local
            localDataSource.insertHeroes(remoteToLocalMapper.map(remoteHeroes))
        }
        //TODO-4: Return local from data
        return localToPresentationMapper.map(localDataSource.getHeroes())
    }

    fun initLocalDatabase(context: Context) {
        localDataSource.initDatabase(context)
    }
}