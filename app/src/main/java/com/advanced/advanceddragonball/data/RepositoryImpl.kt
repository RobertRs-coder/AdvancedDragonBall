package com.advanced.advanceddragonball.data

import android.util.Log
import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.local.datastore.PrefsDataStore
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.LocationRemoteToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.domain.HeroLocation
import com.advanced.advanceddragonball.ui.detail.HeroDetailState
import com.advanced.advanceddragonball.ui.list.HeroListState
import com.advanced.advanceddragonball.ui.login.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dataStore: PrefsDataStore,
    private val remoteToPresentationMapper: RemoteToPresentationMapper,
    private val remoteToLocalMapper: RemoteToLocalMapper,
    private val localToPresentationMapper: LocalToPresentationMapper,
    private val locationRemoteToPresentationMapper: LocationRemoteToPresentationMapper,
    ) : Repository {

    companion object {
        const val TOKEN = "TOKEN"
    }

    override suspend fun getHeroes(): HeroListState {
        val localHeroes = localDataSource.getHeroes()

        if (localHeroes.isNotEmpty()) {
            return HeroListState.Success(localToPresentationMapper.map(localDataSource.getHeroes()))

        } else {
            val token = dataStore.getToken(TOKEN)

            val result = remoteDataSource.getHeroes("Bearer $token")
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
        val token = dataStore.getToken(TOKEN)

        val result = remoteDataSource.getHeroDetail(name, "Bearer $token")
        return when {
            result.isSuccess -> {
                result.getOrNull()
                    ?.let { HeroDetailState.Success(remoteToPresentationMapper.map(it)) }
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

     override suspend fun getToken(): String? {
         return dataStore.getToken(TOKEN)
    }

    override suspend fun switchHeroLike(id: String) {

        val token = dataStore.getToken(TOKEN)

        remoteDataSource.switchHeroLike(id, "Bearer $token")

        val heroesLocal = withContext(Dispatchers.IO) {
            localDataSource.getHeroes()
        }

        //Change local favorite attribute in heroes in local database
        var position = 0
        //Look for the position of the heroes in local database
        heroesLocal.forEachIndexed { ind, hero ->
            if (hero.id == id) {
                position = ind
            }
        }
        //Change value of favorite attribute in this position of the heroes in local database
        val favorite = heroesLocal[position].favorite
        heroesLocal[position].favorite = !favorite
        //Insert this new value in database
        withContext(Dispatchers.IO) {
            localDataSource.insertHeroes(heroesLocal)
        }
    }

    override suspend fun login(email: String, password: String): LoginState {

        val token = dataStore.getToken(TOKEN)
        Log.d("TOKEN", "datastore Token $token")

        if (token?.isNotEmpty() == true) {

            return LoginState.Success(token)

        } else {

            val result = remoteDataSource.login(email, password)
            return when {
                result.isSuccess -> {
                    result.getOrNull()?.let {

                        Log.d("TOKEN", "remote Token $it")
                        dataStore.saveToken(TOKEN, it)
                        LoginState.Success(it)
                    }
                        ?: LoginState.Failure(result.exceptionOrNull()?.message)

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

    override suspend fun getHeroLocations(heroId: String): List<HeroLocation>? {

        val token = dataStore.getToken(TOKEN)

        val result = remoteDataSource.getHeroLocations(heroId, "Bearer $token")

        result.onSuccess {
            //Return values of Hero Locations maps to show in Presentation
            return it?.let {  locationRemoteToPresentationMapper.map(it) }
        }
        return emptyList()
    }
}