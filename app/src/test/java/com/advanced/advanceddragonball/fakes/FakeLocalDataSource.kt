package com.advanced.advanceddragonball.fakes

import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.local.model.HeroLocal
import com.advanced.advanceddragonball.utils.generateHeroesLocal

class FakeLocalDataSource: LocalDataSource {
    private var firstCall = true

    override fun getHeroes(): List<HeroLocal> {
        return if (firstCall){
            firstCall = false
            emptyList()
        } else {
            generateHeroesLocal()
        }
    }

    override fun insertHeroes(remoteHeroes: List<HeroLocal>) {

    }
}