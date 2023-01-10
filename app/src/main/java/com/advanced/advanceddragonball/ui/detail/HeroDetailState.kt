package com.advanced.advanceddragonball.ui.detail

import com.advanced.advanceddragonball.domain.Hero

sealed class HeroDetailState {
    data class Success(val hero: Hero) : HeroDetailState()
    data class Failure(val error: String?): HeroDetailState()
    data class NetworkError(val code: Int): HeroDetailState()
}