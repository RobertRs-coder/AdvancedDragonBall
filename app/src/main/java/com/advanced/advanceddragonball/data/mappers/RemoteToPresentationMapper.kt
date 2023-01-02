package com.advanced.advanceddragonball.data.mappers

import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Hero

class RemoteToPresentationMapper {
    fun map(heroList: List<HeroRemote>): List<Hero> {
        return heroList.map {map(it) }
    }

    fun map(hero: HeroRemote): Hero {
        return Hero(hero.id, hero.name, hero.photo)
    }
}
