package com.advanced.advanceddragonball.data


import com.advanced.advanceddragonball.domain.Hero
import com.advanced.advanceddragonball.ui.detail.HeroDetailState
import com.advanced.advanceddragonball.ui.list.HeroListState
import com.advanced.advanceddragonball.ui.login.LoginState

interface Repository{

    suspend fun getHeroes(): HeroListState
    suspend fun getHeroDetail(name: String): HeroDetailState
    suspend fun login(email: String, password: String): LoginState
//    suspend fun getHeroesWithCache(): HeroListState

}