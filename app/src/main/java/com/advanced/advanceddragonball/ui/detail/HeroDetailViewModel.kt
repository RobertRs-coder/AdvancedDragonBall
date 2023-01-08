package com.advanced.advanceddragonball.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.advanceddragonball.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _state = MutableLiveData<HeroDetailState>()
    val state: LiveData<HeroDetailState>
        get() = _state

    companion object {
        private const val TAG = "DetailViewModel: "
    }

    fun getHeroDetail(name: String) {
        viewModelScope.launch {
            val heroListState = withContext(Dispatchers.IO) {
                repository.getHeroDetail(name)
            }
            _state.value = heroListState
        }
    }
}