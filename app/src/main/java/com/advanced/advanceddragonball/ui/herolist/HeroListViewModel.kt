package com.advanced.advanceddragonball.ui.herolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.data.RepositoryImpl
import com.advanced.advanceddragonball.data.local.HeroDatabase
import com.advanced.advanceddragonball.data.local.LocalDataSourceImpl
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.DragonBallApi
import com.advanced.advanceddragonball.data.remote.RemoteDataSourceImpl


import com.advanced.advanceddragonball.domain.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HeroListViewModel(
    private val repository: Repository)

    : ViewModel() {

    private val _heroes = MutableLiveData<List<Hero>>()

    val heroes: LiveData<List<Hero>>
        get() =_heroes

    companion object {
        const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InByaXZhdGUifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiIyNjBENjk3My00Njc0LTQyRDQtQjUxRi00MjYwRTBBMUJCOUYiLCJlbWFpbCI6InJyb2pvLnZhQGdtYWlsLmNvbSJ9.lQOqPIfkP0_GJs8lik1PmfacpoQcyDxy3NGJGeflOEc"
        private val TAG = "ListViewModel: "

        // Create ViewModels with dependencies  -> Kotlin way
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application

                val db = Room.databaseBuilder(
                    application,
                    HeroDatabase::class.java, "database-name"
                )
                    .build()

                val dao = db.getDao()

                val localDataSource = LocalDataSourceImpl(dao)

                // REMOTE
                val moshi = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()

                val httpLoggingInterceptor =
                    HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()
                        val newRequest = originalRequest.newBuilder()
//                .header("Authorization", "Bearer $TOKEN")
                            .header("Content-Type", "Application/Text")
                            .build()
                        chain.proceed(newRequest)
                    }
                    .authenticator { _, response ->
                        response.request.newBuilder().header("Authorization", "Bearer $TOKEN").build()
                    }
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl("https://dragonball.keepcoding.education/")
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                val api: DragonBallApi = retrofit.create(DragonBallApi::class.java)

                val remoteDataSource = RemoteDataSourceImpl(api)

                val repository = RepositoryImpl(
                    localDataSource,
                    remoteDataSource,
//                    RemoteToPresentationMapper(),
                    RemoteToLocalMapper(),
                    LocalToPresentationMapper()
                )
                HeroListViewModel(
                    repository
                )
            }
        }
        // Create ViewModels with dependencies
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(
//                modelClass: Class<T>,
//                extras: CreationExtras
//            ): T {
//                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
//
//                // LOCAL
//                val db = Room.databaseBuilder(
//                    application,
//                    HeroDatabase::class.java, "database-name"
//                )
//                    .build()
//
//                val dao = db.getDao()
//
//                val localDataSource = LocalDataSourceImpl(dao)
//
//                // REMOTE
//                val moshi = Moshi.Builder()
//                    .addLast(KotlinJsonAdapterFactory())
//                    .build()
//
//                val httpLoggingInterceptor =
//                    HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
//                        level = HttpLoggingInterceptor.Level.BODY
//                    }
//
//                val okHttpClient = OkHttpClient.Builder()
//                    .addInterceptor { chain ->
//                        val originalRequest = chain.request()
//                        val newRequest = originalRequest.newBuilder()
////                .header("Authorization", "Bearer $TOKEN")
//                            .header("Content-Type", "Application/Text")
//                            .build()
//                        chain.proceed(newRequest)
//                    }
//                    .authenticator { _, response ->
//                        response.request.newBuilder().header("Authorization", "Bearer $TOKEN").build()
//                    }
//                    .addInterceptor(httpLoggingInterceptor)
//                    .build()
//
//                val retrofit: Retrofit = Retrofit.Builder()
//                    .baseUrl("https://dragonball.keepcoding.education/")
//                    .client(okHttpClient)
//                    .addConverterFactory(MoshiConverterFactory.create(moshi))
//                    .build()
//                val api: DragonBallApi = retrofit.create(DragonBallApi::class.java)
//
//                val remoteDataSource = RemoteDataSourceImpl(api)
//
//                val repository = RepositoryImpl(
//                    localDataSource,
//                    remoteDataSource,
////                    RemoteToPresentationMapper(),
//                    RemoteToLocalMapper(),
//                    LocalToPresentationMapper()
//                )
//
//                return HeroListViewModel(
//                    repository
//                ) as T
//            }
//        }
    }

    fun getBootcamps() {
        viewModelScope.launch {
            val bootcamps = withContext(Dispatchers.IO) {
                repository.getBootcamps()
            }
            Log.d(TAG, bootcamps.toString())
        }
    }

    fun getHeroes() {
        viewModelScope.launch {
            val heroes = withContext(Dispatchers.IO) {
                repository.getHeroesWithCache()
            }
            _heroes.value = heroes
            Log.d(TAG, heroes.toString())
        }
    }
}