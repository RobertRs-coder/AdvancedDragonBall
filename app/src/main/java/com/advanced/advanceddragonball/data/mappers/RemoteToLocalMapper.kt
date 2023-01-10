package com.advanced.advanceddragonball.data.mappers

import com.advanced.advanceddragonball.data.local.model.HeroLocal
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import javax.inject.Inject


class RemoteToLocalMapper @Inject constructor(){
    fun map(heroRemoteList: List<HeroRemote>): List<HeroLocal> {
        return heroRemoteList.map {map(it) }
    }

    private fun map(heroRemote: HeroRemote): HeroLocal {
        return HeroLocal(
            heroRemote.id,
            heroRemote.name,
            heroRemote.photo,
            heroRemote.description,
            heroRemote.favorite)
    }
}