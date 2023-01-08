package com.advanced.advanceddragonball.ui.list

import com.advanced.advanceddragonball.domain.Hero

sealed class HeroListState {
    data class Success(val heroes: List<Hero>) : HeroListState()
    data class Failure(val error: String?): HeroListState()
    data class NetworkFailure(val code: Int): HeroListState()
}