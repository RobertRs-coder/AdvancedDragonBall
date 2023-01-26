package com.advanced.advanceddragonball.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.advanced.advanceddragonball.data.Repository


import com.advanced.advanceddragonball.domain.Hero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _state = MutableLiveData<HeroListState>()
    val state: LiveData<HeroListState>
        get() = _state

    companion object {

        private val TAG = "ListViewModel: "
    }

     fun getHeroes() {
        viewModelScope.launch {
            val heroes = withContext(Dispatchers.IO) {
                repository.getHeroes()
            }
            _state.value = heroes
        }
        state.value
    }
}