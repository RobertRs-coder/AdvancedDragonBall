package com.advanced.advanceddragonball.data.mappers

import com.advanced.advanceddragonball.data.local.model.HeroLocal
import com.advanced.advanceddragonball.domain.Hero
import javax.inject.Inject

class LocalToPresentationMapper @Inject constructor() {
    fun map(heroLocalList: List<HeroLocal>): List<Hero> {
        return heroLocalList.map {map(it) }
    }

    private fun map(heroRemote: HeroLocal): Hero {
        return Hero(
            heroRemote.id,
            heroRemote.name,
            heroRemote.photo,
           )
    }
}
