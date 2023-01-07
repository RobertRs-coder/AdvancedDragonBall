package com.advanced.advanceddragonball.di

import android.util.Log
import com.advanced.advanceddragonball.data.remote.DragonBallApi
import com.advanced.advanceddragonball.ui.herolist.HeroListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
//    // Practica
//    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, dataStore: DataStore): OkHttpClient {
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
//                .header("Authorization", "Bearer $TOKEN")
                    .header("Content-Type", "Application/Text")
                    .build()
                chain.proceed(newRequest)
            }
            .authenticator { _, response ->
//                // Practica --> endpoint login vacio
//                response.request.newBuilder().header("Authorization", "Bearer ${dataStore.getToken()}").build()
                //See errors in the request
                Log.d("Hola", "${response.request.url} ${response.code}")

                response.request.newBuilder().header("Authorization", "Bearer ${HeroListViewModel.TOKEN}").build()

//                //To login pass user & password inside the constructor and put into this function
//                val credentials: String = Credentials.basic("username", "pass")
//                response.request.newBuilder().header("Authorization", credentials).build()
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dragonball.keepcoding.education/")
            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): DragonBallApi {
        return retrofit.create(DragonBallApi::class.java)
    }
}