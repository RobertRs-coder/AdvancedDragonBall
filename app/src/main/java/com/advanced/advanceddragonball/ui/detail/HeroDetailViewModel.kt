package com.advanced.advanceddragonball.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.domain.Hero
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

    fun getHeroDetail(hero: Hero) {
        viewModelScope.launch {
            val heroListState = withContext(Dispatchers.IO) {
                repository.getHeroDetail(hero.name)
            }
            when(heroListState) {
                is HeroDetailState.Failure -> Log.d("LOCATIONS","Error al buscar localizaciones")
                is HeroDetailState.NetworkError -> Log.d("LOCATIONS","Error Network")
                is HeroDetailState.Success -> {
                    val hero = heroListState.hero
                    val locations = withContext(Dispatchers.IO) {
                        //LLAMAR A LAS LOCALIZACIONES EN EL REPOSITORIO
                        repository.getHeroLocations(hero.id)
                    }
                    hero.locations = locations
                }
            }


            _state.value = heroListState



        }
    }
}