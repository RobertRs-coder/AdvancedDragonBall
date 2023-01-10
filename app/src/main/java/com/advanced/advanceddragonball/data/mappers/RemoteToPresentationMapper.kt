package com.advanced.advanceddragonball.data.mappers

import com.advanced.advanceddragonball.data.remote.response.HeroRemote

import com.advanced.advanceddragonball.domain.Hero
import javax.inject.Inject

class RemoteToPresentationMapper @Inject constructor(){
    fun map(heroRemoteList: List<HeroRemote>): List<Hero> {
        return heroRemoteList.map {map(it) }
    }

    fun map(heroRemote: HeroRemote): Hero {
        return Hero(
            heroRemote.id,
            heroRemote.name,
            heroRemote.photo,
            heroRemote.description,
            heroRemote.favorite
        )
    }
}
