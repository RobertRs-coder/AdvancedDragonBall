package com.advanced.advanceddragonball.data

import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero
import com.advanced.advanceddragonball.domain.Repository
import com.advanced.advanceddragonball.ui.detail.HeroDetailState
import com.advanced.advanceddragonball.ui.list.HeroListState
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val remoteToPresentationMapper: RemoteToPresentationMapper,
    private val remoteToLocalMapper: RemoteToLocalMapper,
    private val localToPresentationMapper: LocalToPresentationMapper
    ) : Repository {

    override suspend fun getBootcamps(): List<Bootcamp> {
        return remoteDataSource.getBootcamps()
    }

    override suspend fun getHeroes(): List<Hero> {
        return remoteToPresentationMapper.map(remoteDataSource.getHeroes())
    }

    override suspend fun getHeroesWithCache(): List<Hero> {
        //TODO-1:Get data from local
        val localHeroes = localDataSource.getHeroes()
        //TODO-2:Check if there isn't data
        if(localHeroes.isEmpty()) {
            //TODO-3a:Get data from remote
            val remoteHeroes = remoteDataSource.getHeroes()
            //TODO-3b:Save data to local
            localDataSource.run {
                //TODO-3b:Save data to local
                insertHeroes(remoteToLocalMapper.map(remoteHeroes))
            }
        }
        //TODO-4: Return local from data
        return localToPresentationMapper.map(localDataSource.getHeroes())
    }

    override suspend fun getHeroesWithException(): HeroListState {
        val result = remoteDataSource.getHeroesWithException()
        return when {
            result.isSuccess -> HeroListState.Success(remoteToPresentationMapper.map(result.getOrThrow()))
            else -> {
                when (val exception = result.exceptionOrNull()) {
                    is HttpException -> HeroListState.NetworkFailure(
                        exception.code()
                    )
                    else -> {
                        HeroListState.Failure(
                            result.exceptionOrNull()?.message)
                    }
                }
            }
        }
    }

//    override suspend fun getHeroDetail(name: String): Hero {
//        return remoteToPresentationMapper.map(remoteDataSource.getHeroDetail(name))
//    }


    override suspend fun getHeroDetail(name: String): HeroDetailState {
        val result = remoteDataSource.getHeroDetail(name)
        return when {
            result.isSuccess -> {
                result.getOrNull()?.let { HeroDetailState.Success(remoteToPresentationMapper.map(it)) }
                    ?: HeroDetailState.Failure(
                        result.exceptionOrNull()?.message
                    )
            }
            else -> {
                when (val exception = result.exceptionOrNull()) {
                    is HttpException -> HeroDetailState.NetworkFailure(
                        exception.code()
                    )
                    else -> {
                        HeroDetailState.Failure(
                            result.exceptionOrNull()?.message
                        )
                    }
                }
            }
        }
    }
}