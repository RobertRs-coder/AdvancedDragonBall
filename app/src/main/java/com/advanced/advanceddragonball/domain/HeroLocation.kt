package com.advanced.advanceddragonball.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeroLocation (
    val id: String,
    val longitude: String,
    val latitude: String,
    val dateShow: String
): Parcelable