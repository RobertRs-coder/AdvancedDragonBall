package com.advanced.advanceddragonball.ui.herolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.domain.Bootcamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class HeroListViewModel: ViewModel() {

    private val repository = Repository()

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
                repository.getHeroes()
            }
            Log.d(TAG, heroes.toString())
        }
    }
}