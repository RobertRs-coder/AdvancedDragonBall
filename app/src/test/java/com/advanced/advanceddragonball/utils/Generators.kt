package com.advanced.advanceddragonball.utils

import com.advanced.advanceddragonball.data.local.model.HeroLocal
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Hero
import com.advanced.advanceddragonball.domain.HeroLocation

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


fun generateHero(): Hero {
   return Hero(
        "ID",
        "Name",
        "Photo",
        "Description",
        false
    )
}


fun generateLocations(): List<HeroLocation> {
    return (0 until 10).map {
        HeroLocation(
            "ID: $it",
            "Longitude: $it",
            "Latitude: $it",
            "DateShow: $it"
        )
    }
}
