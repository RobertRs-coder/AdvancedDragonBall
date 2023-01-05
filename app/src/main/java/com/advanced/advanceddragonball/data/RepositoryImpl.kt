package com.advanced.advanceddragonball.data

import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.local.LocalDataSourceImpl
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.data.remote.RemoteDataSourceImpl
import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSourceImpl,
    private val remoteDataSource: RemoteDataSourceImpl,
//    private val remoteToPresentationMapper: RemoteToPresentationMapper,
    private val remoteToLocalMapper: RemoteToLocalMapper,
    private val localToPresentationMapper: LocalToPresentationMapper
    ) : Repository {

    override suspend fun getBootcamps(): List<Bootcamp> {
        return remoteDataSource.getBootcamps()
    }

    override suspend fun getHeroesWithCache(): List<Hero> {
        //TODO-1:Get data from local
        val localHeroes = localDataSource.getHeroes()
        //TODO-2:Check if there isn't data
        if(localHeroes.isEmpty()) {
            //TODO-3a:Get data from remote
            val remoteHeroes = remoteDataSource.getHeroes()
            //TODO-3b:Save data to local
            localDataSource.insertHeroes(remoteToLocalMapper.map(remoteHeroes))
        }
        //TODO-4: Return local from data
        return localToPresentationMapper.map(localDataSource.getHeroes())
    }
}