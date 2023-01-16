package com.advanced.advanceddragonball.ui.detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.domain.Hero
import com.advanced.advanceddragonball.domain.HeroLocation
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*
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
                    Log.d("Locations", hero.locations.toString())
                }
            }

            viewModelScope.launch(Dispatchers.Main) {
                _state.value = heroListState
            }
        }
    }

    fun getMarker(location: HeroLocation): MarkerOptions {
        return MarkerOptions().position(getCoordinates(location)).title(getTitle(location))
    }
    private fun getTitle(location: HeroLocation): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = LocalDate.parse(location.dateShow, formatter)
        return "Seen the ${date.dayOfMonth} of ${
            Month.values()[date.monthValue].toString().lowercase(
                Locale.getDefault())}"
    }
    private fun getCoordinates(location: HeroLocation): LatLng {
        return LatLng(location.latitude.toDouble(), location.longitude.toDouble())
    }
}