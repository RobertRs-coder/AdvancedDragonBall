package com.advanced.advanceddragonball.data

import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.ui.detail.HeroDetailState
import com.advanced.advanceddragonball.ui.list.HeroListState
import com.advanced.advanceddragonball.ui.login.LoginState
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val remoteToPresentationMapper: RemoteToPresentationMapper,
    private val remoteToLocalMapper: RemoteToLocalMapper,
    private val localToPresentationMapper: LocalToPresentationMapper
    ) : Repository {

    companion object {
        const val TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InByaXZhdGUifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiIyNjBENjk3My00Njc0LTQyRDQtQjUxRi00MjYwRTBBMUJCOUYiLCJlbWFpbCI6InJyb2pvLnZhQGdtYWlsLmNvbSJ9.lQOqPIfkP0_GJs8lik1PmfacpoQcyDxy3NGJGeflOEc"    }


//    override fun getHerosFromLocal() {
//        localDataSource.getHeroes()
//    }
//
//    override fun getHerosToLocal(remoteHeroes: List<HeroLocal>){
//        localDataSource.insertHeroes(remoteHeroes)
//    }

//    override suspend fun getHeroesWithCache(): LoginState {
//        // 1º Pido los datos a local
//        val localHeroes = localDataSource.getHeroes()
//        // 2º Compruebo si hay datos
//        if (localHeroes.isEmpty()) { getHeroes() }
////            // 3º Si no hay datos
////            //3a Pido datos a remoto
////            val remoteHeroes = remoteDataSource.getHeroes("Bearer $TOKEN")
////            //3b Guardo datos en local
////            localDataSource.insertHeroes(remoteToLocalMapper.map(remoteHeroes))
////        }
////        // 4º Devuelvo datos local
////        return localToPresentationMapper.map(localDataSource.getHeroes()
//
//    }


    override suspend fun getHeroes(): HeroListState {
        val localHeroes = localDataSource.getHeroes()

        if (localHeroes.isNotEmpty()) {
            return HeroListState.Success(localToPresentationMapper.map(localDataSource.getHeroes()))

        } else {
            val result = remoteDataSource.getHeroes("Bearer $TOKEN")
            return when {
                result.isSuccess -> {
                    result.getOrNull()?.let {
                        localDataSource.insertHeroes(remoteToLocalMapper.map(it))
                        HeroListState.Success(localToPresentationMapper.map(localDataSource.getHeroes()))
                    }
                        ?: HeroListState.Failure(result.exceptionOrNull()?.message)
                }
                else -> {
                    when (val exception = result.exceptionOrNull()) {
                        is HttpException -> HeroListState.NetworkError(
                            exception.code()
                        )
                        else -> {
                            HeroListState.Failure(result.exceptionOrNull()?.message)
                        }
                    }
                }
            }
        }

    }


    override suspend fun getHeroDetail(name: String): HeroDetailState {
        val result = remoteDataSource.getHeroDetail(name, "Bearer $TOKEN")
        return when {
            result.isSuccess -> {
                result.getOrNull()?.let { HeroDetailState.Success(remoteToPresentationMapper.map(it)) }
                    ?: HeroDetailState.Failure(result.exceptionOrNull()?.message)
            }
            else -> {
                when (val exception = result.exceptionOrNull()) {
                    is HttpException -> HeroDetailState.NetworkError(
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

    override suspend fun login(email: String, password: String): LoginState {
        val result = remoteDataSource.login(email, password)
        return when {
            result.isSuccess -> {
                result.getOrNull()?.let { LoginState.Success(it) }
                    ?: LoginState.Failure(
                        result.exceptionOrNull()?.message
                    )
            }
            else -> {
                when (val exception = result.exceptionOrNull()) {
                    is HttpException -> LoginState.NetworkError(
                        exception.code()
                    )
                    else -> {
                        LoginState.Failure(
                            result.exceptionOrNull()?.message
                        )
                    }
                }
            }
        }
    }
}