package com.advanced.advanceddragonball.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.advanced.advanceddragonball.domain.Repository


import com.advanced.advanceddragonball.domain.Hero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val repository: Repository
    ) : ViewModel() {

    private val _heroes = MutableLiveData<List<Hero>>()
    val heroes: LiveData<List<Hero>>
        get() =_heroes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() =_error

    private val _state = MutableLiveData<HeroListState>()
    val state: LiveData<HeroListState>
        get() = _state

    companion object {
        const val TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InByaXZhdGUifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiIyNjBENjk3My00Njc0LTQyRDQtQjUxRi00MjYwRTBBMUJCOUYiLCJlbWFpbCI6InJyb2pvLnZhQGdtYWlsLmNvbSJ9.lQOqPIfkP0_GJs8lik1PmfacpoQcyDxy3NGJGeflOEc"
        private val TAG = "ListViewModel: "
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
            _heroes.value =heroes
        }
    }

    fun getHeroesWithException() {
        viewModelScope.launch {
            val heroListState = withContext(Dispatchers.IO) {
                repository.getHeroesWithException()
            }

            _state.value = heroListState
        }
    }
}