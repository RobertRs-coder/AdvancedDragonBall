package com.advanced.advanceddragonball.ui.herolist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.domain.Hero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroListViewModel: ViewModel() {

    private val repository = Repository(RemoteDataSource(), LocalDataSource())

    private val _heroes = MutableLiveData<List<Hero>>()

    val heroes: LiveData<List<Hero>>
        get() =_heroes


    companion object {
        private val TAG = "ListViewModel"
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

    fun getLocalHeroes() {
        viewModelScope.launch {
            val heroes = withContext(Dispatchers.IO) {
                repository.getHeroesWithCache()
            }
            _heroes.value = heroes
            Log.d(TAG, heroes.toString())
        }
    }

    fun initDatabase(context: Context) {
        repository.initLocalDatabase(context)
    }
}