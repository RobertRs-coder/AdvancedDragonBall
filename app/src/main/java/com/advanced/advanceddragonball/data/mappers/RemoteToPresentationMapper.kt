package com.advanced.advanceddragonball.data.mappers

import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Hero
import com.advanced.advanceddragonball.domain.HeroDetail
import javax.inject.Inject

class RemoteToPresentationMapper @Inject constructor(){
    fun map(heroRemoteList: List<HeroRemote>): List<Hero> {
        return heroRemoteList.map {map(it) }
    }

    fun map(heroRemote: HeroRemote): HeroDetail {
        return HeroDetail(
            heroRemote.id,
            heroRemote.name,
            heroRemote.photo,
            heroRemote.description,
            false)
        )
    }
}
