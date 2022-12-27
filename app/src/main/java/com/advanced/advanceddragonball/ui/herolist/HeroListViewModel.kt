package com.advanced.advanceddragonball.ui.herolist

import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class HeroListViewModel: ViewModel() {
    fun tareaCostosa() {
        Thread.sleep(10000)
    }
}