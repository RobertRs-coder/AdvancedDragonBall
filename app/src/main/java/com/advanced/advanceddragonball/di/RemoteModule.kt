package com.advanced.advanceddragonball.di

import android.content.Context
import androidx.room.Room
import com.advanced.advanceddragonball.data.local.HeroDao
import com.advanced.advanceddragonball.data.local.HeroDatabase
import com.advanced.advanceddragonball.data.remote.DragonBallApi
import com.advanced.advanceddragonball.ui.herolist.HeroListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

