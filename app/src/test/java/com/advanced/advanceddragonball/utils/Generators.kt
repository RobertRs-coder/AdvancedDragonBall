package com.advanced.advanceddragonball.utils

import com.advanced.advanceddragonball.data.local.model.HeroLocal
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero

fun generateBootcamps(): List<Bootcamp> {
    return (0 until 10).map { Bootcamp("ID: $it", "Name $it") }
}

fun generateHeroesRemote(): List<HeroRemote> {
    return (0 until 10).map {
        HeroRemote(
            "ID: $it",
            "Name $it",
            "Photo $it",
            "Description $it",
            favorite = false
        )
    }
}

fun generateHeroesLocal(): List<HeroLocal> {
    return (0 until 10).map {
        HeroLocal(
            "ID: $it",
            "Name $it",
            "Photo $it",
            "Description $it",
            false
        )
    }
}


fun generateHeroes(): List<Hero> {
    return (0 until 10).map {
        Hero(
            "ID: $it",
            "Name $it",
            "Photo $it",
            "Description $it",
            false
        )
    }
}