package com.advanced.advanceddragonball.data.mappers

import com.advanced.advanceddragonball.data.remote.response.HeroLocationRemote
import com.advanced.advanceddragonball.domain.HeroLocation
import javax.inject.Inject

class LocationRemoteToPresentationMapper @Inject constructor(){

    fun map(heroLocationsRemote: List<HeroLocationRemote>): List<HeroLocation> {
        return heroLocationsRemote.map { map(it) }
    }

    private fun map(heroLocationRemote: HeroLocationRemote): HeroLocation {
        return HeroLocation(
            heroLocationRemote.id,
            heroLocationRemote.latitude,
            heroLocationRemote.longitude,
            heroLocationRemote.dateShow,
        )
    }
}